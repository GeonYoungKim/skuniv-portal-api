package com.skuniv.cs.geonyeong.portal.service;

import static com.skuniv.cs.geonyeong.portal.constant.PortalConstant.DAY_OF_WEEK_MAP;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture;
import com.skuniv.cs.geonyeong.portal.domain.entity.LectureDetail;
import com.skuniv.cs.geonyeong.portal.domain.entity.Professor;
import com.skuniv.cs.geonyeong.portal.domain.entity.Semester;
import com.skuniv.cs.geonyeong.portal.repository.LectureDetailRepository;
import com.skuniv.cs.geonyeong.portal.repository.LectureRepository;
import com.skuniv.cs.geonyeong.portal.repository.ProfessorRepository;
import com.skuniv.cs.geonyeong.portal.repository.SemesterRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final ProfessorRepository professorRepository;
    private final LectureDetailRepository lectureDetailRepository;
    private final SemesterRepository semesterRepository;

    private final String REPLACE_DAY = "요일";

    public List<Lecture> getLectures(Long semesterId, String professorId) {
        Professor professor = professorRepository.findById(professorId).get();
        Semester semester = semesterRepository.findById(semesterId).get();
        return lectureRepository.findBySemesterAndProfessor(semester, professor);
    }

    private List<Integer> makeDayNumList(String days) {
        return Arrays.stream(days.split(","))
            .map(item -> DAY_OF_WEEK_MAP.get(item))
            .collect(Collectors.toList());
    }

    private Long makeDiffDays (Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        return Math.abs(diff / (24 * 60 * 60 * 1000));
    }

    private void addLectureDetailList(Long diffDays, List<Integer> dayNumList, Lecture lecture, Date startDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        for (int i = 1; i <= diffDays; i++) {
            cal.add(Calendar.DATE, 1);
            int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;
            if(dayNumList.contains(dayNum)) {
                LectureDetail lectureDetail = LectureDetail.builder()
                    .canceled(false)
                    .lecture(lecture)
                    .lectureDetailTime(lecture.getLectureTime()/2)
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
        // 요일 제거.
        lecture.setLectureDay(lecture.getLectureDay().replaceAll(REPLACE_DAY, ""));
        // lecture 추가
        Professor professor = professorRepository.findById(professorId).get();
        professor.addLecture(lecture);
        // 1학기 날짜 차이 생성.
        Long diffDays = makeDiffDays(semester.getStartDate(), semester.getEndDate());
        // 강좌 요일 정보를 integer로 변환.
        List<Integer> dayNumList =  makeDayNumList(lecture.getLectureDay());
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
