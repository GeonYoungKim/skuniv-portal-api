package com.skuniv.cs.geonyeong.portal.service;

import static com.skuniv.cs.geonyeong.portal.constant.PortalConstant.DAY_OF_WEEK_MAP;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skuniv.cs.geonyeong.portal.domain.entity.Assignment;
import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture;
import com.skuniv.cs.geonyeong.portal.domain.entity.LectureDetail;
import com.skuniv.cs.geonyeong.portal.domain.entity.Professor;
import com.skuniv.cs.geonyeong.portal.domain.entity.Semester;
import com.skuniv.cs.geonyeong.portal.domain.vo.LectureDay;
import com.skuniv.cs.geonyeong.portal.domain.vo.ProfessorAssignmentDetail;
import com.skuniv.cs.geonyeong.portal.repository.AssignmentRepository;
import com.skuniv.cs.geonyeong.portal.repository.LectureDetailRepository;
import com.skuniv.cs.geonyeong.portal.repository.LectureRepository;
import com.skuniv.cs.geonyeong.portal.repository.ProfessorRepository;
import com.skuniv.cs.geonyeong.portal.repository.SemesterRepository;
import com.skuniv.cs.geonyeong.portal.repository.StudentAssignmentRepository;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final static Gson gson = new Gson();

    private final AssignmentRepository assignmentRepository;
    private final StudentAssignmentRepository studentAssignmentRepository;
    private final LectureRepository lectureRepository;
    private final ProfessorRepository professorRepository;
    private final LectureDetailRepository lectureDetailRepository;
    private final SemesterRepository semesterRepository;
    private final Type listType = new TypeToken<ArrayList<LectureDay>>() {
    }.getType();

    private final String REPLACE_DAY = "요일";
    private final String DAY_DELEMETER = ",";

    public List<Assignment> getLectureAssignments(Long lectureId) {
        return assignmentRepository.findByLecture(lectureRepository.findById(lectureId).get());
    }

    public List<ProfessorAssignmentDetail> getProfessorAssignmentDetail(Long assignmentId) {
        List<ProfessorAssignmentDetail> professorAssignmentDetailList = studentAssignmentRepository
            .findByAssignmentId(assignmentId);
        log.info("professorAssignmentDetailList => {}", professorAssignmentDetailList);
        return professorAssignmentDetailList;
    }

    public Assignment createLectureAssignment(Long lectureId, Assignment assignment) {
        Lecture lecture = lectureRepository.findById(lectureId).get();
        lecture.addAssignment(assignment);
        lectureRepository.save(lecture);
        return assignment;
    }

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

    private List<Integer> makeDayNumList(String lectureDay) {
        List<LectureDay> lectureDetailList = gson.fromJson(lectureDay, listType);
        return lectureDetailList
            .stream()
            .map(item -> DAY_OF_WEEK_MAP.get(item.getLectureDay()))
            .collect(Collectors.toList());
    }

    private Long makeDiffDays(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        return Math.abs(diff / (24 * 60 * 60 * 1000));
    }

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

    public Lecture createLecture(Long semesterId, String professorId, Lecture lecture) {
        // 학기 조회
        Semester semester = semesterRepository.findById(semesterId).get();
        // lecture 추가
        Professor professor = professorRepository.findById(professorId).get();
        professor.addLecture(lecture);
        // 1학기 날짜 차이 생성.
        Long diffDays = makeDiffDays(semester.getStartDate(), semester.getEndDate());
        // 강좌 요일 정보를 integer로 변환.
        List<Integer> dayNumList = makeDayNumList(lecture.getLectureDay());
        addLectureDetailList(diffDays, dayNumList, lecture, semester.getStartDate());
        semester.addLecture(lecture);
        semesterRepository.save(semester);
        return lecture;
    }

    public List<LectureDetail> getLectureDetails(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).get();
        return lectureDetailRepository.findByLecture(lecture);
    }

    public LectureDetail updateLectureDetailCanceled(Long lectureDetailId) {
        LectureDetail lectureDetail = lectureDetailRepository.findById(lectureDetailId).get();
        lectureDetail.setCanceled(!lectureDetail.getCanceled());
        return lectureDetailRepository.save(lectureDetail);
    }
}
