package com.skuniv.cs.geonyeong.portal.service;

import com.skuniv.cs.geonyeong.portal.domain.entity.Assignment;
import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture;
import com.skuniv.cs.geonyeong.portal.domain.vo.ProfessorAssignmentDetail;
import com.skuniv.cs.geonyeong.portal.repository.AssignmentRepository;
import com.skuniv.cs.geonyeong.portal.repository.LectureRepository;
import com.skuniv.cs.geonyeong.portal.repository.StudentAssignmentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final StudentAssignmentRepository studentAssignmentRepository;
    private final LectureRepository lectureRepository;

    public List<Assignment> getLectureAssignments(Long lectureId) {
        return assignmentRepository.findByLecture(lectureRepository.findById(lectureId).get());
    }

    public List<ProfessorAssignmentDetail> getProfessorAssignmentDetail(Long assignmentId) {
        return studentAssignmentRepository.findByAssignmentId(assignmentId);
    }

    public Assignment createLectureAssignment(Long lectureId, Assignment assignment) {
        Lecture lecture = lectureRepository.findById(lectureId).get();
        lecture.addAssignment(assignment);
        lectureRepository.save(lecture);
        return assignment;
    }
}
