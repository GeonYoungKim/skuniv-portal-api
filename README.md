## 서경대학교 포탈 서버(교수용)

### 기능

#### 1. 인증 기능

- 회원가입
    ```
    requestUrl = /api/v1/portal/account/professor/signUp
    Method = POST
    ```
    비밀번호의 경우 암호화 후 
    Base64 인코딩하여 DB에 저장하도록 하였습니다.
    
    아래는 비밀번호를 암호화하는 부분입니다.
    ```java
    professor.setPassword(cryptorService.encryptBase64(password));
    ```
    아래는 실제로 암호화하는 로직입니다.
     ```java
  public String encryptBase64(String source) {
        byte[] raw = encrypt(source.getBytes());
        String encryptSource = null;
        try {
            encryptSource = new String(Base64.encodeBase64(raw), encode);
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException => {}", e);
            throw new RuntimeException();
        }
        return encryptSource;
    }
    ```
    <br>
    <hr>
    <br>
- 로그인
    ```
    requestUrl = /api/v1/portal/account/professor/signIn
    Method = POST
    ```
    요청 받은 비밀번호를 암호화 후 DB 조회하여 존재한다면
    <br>jwt 토큰을 발급하여 넘겨줍니다.
    <br>토큰의 유효기간은 1시간으로 만들었습니다.
    
    아래는 비밀번호를 암호화하여 DB를 조회 후
    존재한다면 jwt 토큰을 발급하여 반환하는 코드입니다.
    ```java
      String password = cryptorService.encryptBase64(professor.getPassword());
      Optional<Professor> findProfessorOptional = professorRepository.findByIdAndPassword(professor.getId(), password);
      if(findProfessorOptional.isPresent()) {
          Professor findProfessor = findProfessorOptional.get();
          String token = jwtService.makeJwt(findProfessor.getId(), findProfessor.getPassword());
          return AccountResponse.builder().token(token).name(findProfessor.getName()).accountType(
              AccountType.PROFESSOR).build();
      }    
      throw new SignInException();
    ```
    아래는 jwt 토큰 생성 코드입니다.
    
    ```java
    public String makeJwt(String id, String password) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Map<String, Object> map = new HashMap<String, Object>() {{
            put(ID_FIELD, id);
            put(PASSWORD_FIELD, password);
        }};

        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + EXPIRE_TIME);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
            .setClaims(map)
            .setExpiration(expireTime)
            .signWith(signingKey, signatureAlgorithm);

        return builder.compact();
    }
    ```
    <br>
    <hr>
    <br>
#### 2. 각 교수님에 따른 강좌 추가, 조회
- 추가
```
requestUrl = /api/v1/portal/professor/semester/{semesterId}/lecture
Method = POST
```
interceptor에서 토큰의 유효성을 판단 후 유저 Id를 attribute에 담아 controller로 넘겨줍니다.
아래는 interceptor의 중요 코드 입니다.
 ```java
        try {
            AccountType accountType = AccountType
                .valueOf(request.getHeader(HEADER_ACCOUNT_TYPE_KEY));
            boolean isCheck = false;
            String jwt;
            switch (accountType) {
                case PROFESSOR:
                    log.info("accountType is PROFESSOR");
                    jwt = request.getHeader(HEADER_TOKEN_KEY);
                    isCheck = jwtService.checkProcessor(jwt);
                    checkAuthentication(isCheck, request, PROFESSOR_ID_KEY,
                        jwtService.getJwtId(jwt));
                    break;
                case STUDENT:
                    jwt = request.getHeader(HEADER_TOKEN_KEY);
                    isCheck = jwtService.checkStudent(jwt);
                    checkAuthentication(isCheck, request, STUDENT_ID_KEY,
                        jwtService.getJwtId(jwt));
                    break;
            }
        } catch (ExpiredJwtException e) {
            log.error("토큰이 만료되었음.");
            throw new TokenExpireException();
        } catch (NullPointerException e) {
            log.error("Missing Account-Type");
            throw new MissingAccountTypeException();
        }
        log.info("pass interceptor");
        return true;
```
추가 시 먼저 학기를 조회하여 해당하는 학기에 강좌를 추가합니다.
또한 해당 학기의 개강~종강 날짜까지 지정한 요일이 해당하는 날짜에대해
추가 적으로 DB에 추가합니다.(lecture_detail 테이블)

아래는 추가하는 전체 코드입니다.
```java
// 학기 조회
Semester semester = semesterRepository.findById(semesterId).get();
// lecture 추가
Professor professor = professorRepository.findById(professorId).get();
professor.addLecture(lecture);
// 1학기 날짜 차이 생성.
Long diffDays = makeDiffDays(semester.getStartDate(), semester.getEndDate());
// 강좌 요일 정보를 integer로 변환.
List<Integer> dayNumList = makeDayNumList(lecture.getLectureDay());
// 강좌 상세 데이터 추가.
addLectureDetailList(diffDays, dayNumList, lecture, semester.getStartDate());
semester.addLecture(lecture);
semesterRepository.save(semester);
```
강좌 요일정보를 integer로 변환하는 방법으로는 아래와 같은 양방향 map을 사용하였습니다.

```java
  public static final BiMap<String, Integer> DAY_OF_WEEK_MAP = HashBiMap.create();
  static {
      DAY_OF_WEEK_MAP.put("일", 1);
      DAY_OF_WEEK_MAP.put("월", 2);
      DAY_OF_WEEK_MAP.put("화", 3);
      DAY_OF_WEEK_MAP.put("수", 4);
      DAY_OF_WEEK_MAP.put("목", 5);
      DAY_OF_WEEK_MAP.put("금", 6);
      DAY_OF_WEEK_MAP.put("토", 7);
  }
```
아래는 개강~종강 사이 지정한 요일이 걸리면 데이터를 추가하는 로직입니다.
```java
    private void addLectureDetailList(Long diffDays, List<Integer> dayNumList, Lecture lecture,
        Date startDate) {
        List<LectureDay> lectureDetailList = gson.fromJson(lecture.getLectureDay(), listType);

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        for (int i = 1; i <= diffDays; i++) {
            cal.add(Calendar.DATE, 1);
            int dayNum = cal.get(Calendar.DAY_OF_WEEK);
            if (dayNumList.contains(dayNum)) {
                LectureDay lectureDay = lectureDetailList
                    .stream()
                    .filter(item -> DAY_OF_WEEK_MAP.get(item.getLectureDay()) ==  dayNum)
                    .findFirst().get();
                LectureDetail lectureDetail = LectureDetail.builder()
                    .canceled(false)
                    .lecture(lecture)
                    .lectureDetailTime(lectureDay.getDetailTime())
                    .lectureDay(DAY_OF_WEEK_MAP.inverse().get(dayNum))
                    .lectureDate(new Date(cal.getTimeInMillis()))
                    .build();
                lecture.addLectureDetail(lectureDetail);
            }
        }
    }
```
<br>
<hr>
<br>

- 조회
```
requestUrl = /api/v1/portal/professor/semester/{semesterId}/lecture
Method = GET
```
학기와 교수님정보를 받아 강좌를 조회합니다.
조회 시 요일은 정렬하여 보여주도록 하였습니다.
```java
    public List<Lecture> getLectures(Long semesterId, String professorId) {
        Professor professor = professorRepository.findById(professorId).get();
        Semester semester = semesterRepository.findById(semesterId).get();
        List<Lecture> lectureList = lectureRepository
            .findBySemesterAndProfessor(semester, professor);
        lectureList.forEach(item -> {
            List<LectureDay> lectureDetailList = gson.fromJson(item.getLectureDay(), listType);
            List<Integer> dayNumList = lectureDetailList.stream()
                .map(lectureDay -> DAY_OF_WEEK_MAP.get(lectureDay.getLectureDay()))
                .collect(Collectors.toList());
            Collections.sort(dayNumList);
            String sortedLectureDay = dayNumList.stream()
                .map(dayNum -> DAY_OF_WEEK_MAP.inverse().get(dayNum)).collect(
                    Collectors.joining(","));
            item.setLectureDay(sortedLectureDay);
        });
        return lectureList;
    }
``` 
   <br>
   <hr>
   <br>
   
#### 3.세부 강좌에 조회 및 휴강 선택.
- 조회
```
requestUrl = /api/v1/portal/professor/lecture/{lectureId}
Method = GET
```
강좌 정보를 받아 강좌 세부정보를 반환합니다.
위의 개강~종강까지의 정보를 의미합니다.

```java
  public List<LectureDetail> getLectureDetails(Long lectureId) {
      Lecture lecture = lectureRepository.findById(lectureId).get();
      return lectureDetailRepository.findByLecture(lecture);
  }
```
   <br>
   <hr>
   <br>
   
- 휴강 선택

```
requestUrl = /api/v1/portal/professor/lecture/detail/{lectureDetailId}
Method = PUT
```
세부 강좌 정보를 받아
휴강 필드를 업데이트합니다.
```java
  public LectureDetail updateLectureDetailCanceled(Long lectureDetailId) {
      LectureDetail lectureDetail = lectureDetailRepository.findById(lectureDetailId).get();
      lectureDetail.setCanceled(!lectureDetail.getCanceled());
      return lectureDetailRepository.save(lectureDetail);
  }
```
   <br>
   <hr>
   <br>
   
#### 4.과제 추가, 조회, 참여 학생정보 조회

- 추가
```
requestUrl = /api/v1/portal/professor/lecture/{lectureId}/assignment
Method = POST
```
강좌 정보를 받아 과제를 추가합니다.
```java
    public Assignment createLectureAssignment(Long lectureId, Assignment assignment) {
        Lecture lecture = lectureRepository.findById(lectureId).get();
        lecture.addAssignment(assignment);
        lectureRepository.save(lecture);
        return assignment;
    }
```
   <br>
   <hr>
   <br>
   
- 조회
```
requestUrl = /api/v1/portal/professor/lecture/{lectureId}/assignment
Method = GET
```
강좌정보를 받아 해당 강좌의 과제 정보를 List로 반환합니다.

```java
    public List<Assignment> getLectureAssignments(Long lectureId) {
        return assignmentRepository.findByLecture(lectureRepository.findById(lectureId).get());
    }
```
   <br>
   <hr>
   <br>
   
- 과제에 참여중인 학생 정보 조회
```
requestUrl = /api/v1/portal/professor/lecture/assignment/{assignmentId}
Method = GET
```
과제 정보를 받아 과제에 참여중인 학생정보를 반환합니다.

```java
    public List<ProfessorAssignmentDetail> getProfessorAssignmentDetail(Long assignmentId) {
        List<ProfessorAssignmentDetail> professorAssignmentDetailList = studentAssignmentRepository
            .findByAssignmentId(assignmentId);
        log.info("professorAssignmentDetailList => {}", professorAssignmentDetailList);
        return professorAssignmentDetailList;
    }
```

이때 반환해야하는 데이터를 가져오기 위해서는 join이 필요하여
queryDsl을 사용하여 좀 더 간편하게 sql을 만들어 사용하였습니다.

```java
    @Override
    public List<ProfessorAssignmentDetail> findByAssignmentId(Long assignmentId) {
        return queryFactory
            .select(Projections.fields(ProfessorAssignmentDetail.class,
                student.name.as("name"),
                studentAssignment.part.as("part"),
                student.phone.as("phone")
            )).from(studentAssignment)
            .where(studentAssignment.assignmentId.eq(assignmentId))
            .join(student).on(studentAssignment.studentId.eq(student.id))
            .fetch()
            ;
    }
```
   <br>
   <hr>
   <br>
   
### 예외 종류

#### 1. MissingAccountTypeException
  - header에 담아줘야하는 유저 타입(PROFESSOR or STUDENT)을 안주었을때 내뱉는 예외입니다. => header key = "accountType" 
  
#### 2. SignInException
  - 로그인 요청 시 DB에 없다면 내뱉는 예외입니다.
  
#### 3. TokenExpireException
  - 요청시 받은 토큰의 유효기간이 지났을때 내뱉는 예외입니다.
  
#### 4. TokenInvalidException
  - 토큰이 유효하지 않았을때 내뱉는 예외입니다.
  
   <br>
   <hr>
   <br>
   
### 예외 처리

### ExceptionHandlerAdvice
  - 지정한 예외 발생 시 catch하여 원하는 response를 클라이언트에 반환하는 역할입니다.

### 
지정한 예외는 enum을 사용하여 정의하도록 하였습니다.
```java
@Getter
@AllArgsConstructor
public enum ExceptionType {
    SIGNIN_EXCEPTION(SignInException.class, 80801, "Sign In Exception"),
    TOKEN_EXPIRE_EXCEPTION(TokenExpireException.class, 80802, "Token Expire"),
    TOKEN_INVALID_EXCEPTION(TokenInvalidException.class, 80803, "Token Invalid"),
    MISS_ACCOUNT_TYPE_EXCEPTION(MissingAccountTypeException.class, 10001, "request header's missing Account-Type Key")
    ;

    private Class<? extends Exception> exception;
    private int status;
    private String message;

    public static Optional<ExceptionResponse> getExceptionResponse(RuntimeException exception) {
        for(ExceptionType item : ExceptionType.values()) {
            if(StringUtils.equals(exception.getClass().getName(), item.getException().getName())){
                return Optional.ofNullable(
                    ExceptionResponse.builder().status(item.getStatus()).message(item.getMessage())
                        .build());
            }
        }
        return Optional.empty();
    }
}
```
   <br>
   <hr>
   <br>
   
### Request Response 명세

- swagger를 참조하시면 됩니다.
  http://localhost:9090/swagger-ui.html