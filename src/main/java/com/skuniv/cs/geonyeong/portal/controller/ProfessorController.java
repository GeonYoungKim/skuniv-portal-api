package com.skuniv.cs.geonyeong.portal.controller;


import static com.skuniv.cs.geonyeong.portal.constant.PortalConstant.PROFESSOR_ID_KEY;

import com.skuniv.cs.geonyeong.portal.domain.entity.Assignment;
import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture;
import com.skuniv.cs.geonyeong.portal.domain.entity.LectureDetail;
import com.skuniv.cs.geonyeong.portal.domain.vo.ProfessorAssignmentDetail;
import com.skuniv.cs.geonyeong.portal.service.ProfessorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/portal/professor")
public class ProfessorController {

    private final ProfessorService professorService;

    @RequestMapping(value = "/semester/{semesterId}/lecture", method = {RequestMethod.GET})
    public List<Lecture> getLectures(
        @PathVariable(value = "semesterId") Long semesterId,
        @RequestAttribute(name = PROFESSOR_ID_KEY) String professorId) {
        log.info("lectures");
        return professorService.getLectures(semesterId, professorId);
    }

    @RequestMapping(value = "/semester/{semesterId}/lecture", method = {RequestMethod.POST})
    public Lecture createLecture(
        @PathVariable(value = "semesterId") Long semesterId,
        @RequestAttribute(name = PROFESSOR_ID_KEY) String professorId,
        @RequestBody Lecture lecture) {
        log.info("lecture => {}", lecture);
        return professorService.createLecture(semesterId, professorId, lecture);
    }

    @RequestMapping(value = "/lecture/{lectureId}", method = {RequestMethod.GET})
    public List<LectureDetail> getLectureDetails(
        @PathVariable(value = "lectureId") Long lectureId) {
        return professorService.getLectureDetails(lectureId);
    }

    @RequestMapping(value = "/lecture/detail/{lectureDetailId}", method = {RequestMethod.PUT})
    public LectureDetail updateLectureDetailCanceled(
        @PathVariable(value = "lectureDetailId") Long lectureDetailId) {
        return professorService.updateLectureDetailCanceled(lectureDetailId);
    }

    @RequestMapping(value = "/lecture/{lectureId}/assignment", method = {RequestMethod.POST})
    public Assignment createLectureAssignment(
        @PathVariable(value = "lectureId") Long lectureId
        , @RequestBody Assignment assignment) {
        return professorService.createLectureAssignment(lectureId, assignment);
    }

    @RequestMapping(value = "/lecture/{lectureId}/assignment", method = {RequestMethod.GET})
    public List<Assignment> getLectureAssignments(
        @PathVariable(value = "lectureId") Long lectureId) {
        log.info("lectureId => {}", lectureId);
        return professorService.getLectureAssignments(lectureId);
    }

    @RequestMapping(value = "/lecture/assignment/{assignmentId}", method = {RequestMethod.GET})
    public List<ProfessorAssignmentDetail> getProfessorAssignmentDetail(
        @PathVariable(value = "assignmentId") Long assignmentId) {
        return professorService.getProfessorAssignmentDetail(assignmentId);
    }
}
