package com.skuniv.cs.geonyeong.portal.controller;


import static com.skuniv.cs.geonyeong.portal.constant.PortalConstant.PROFESSOR_ID_KEY;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skuniv.cs.geonyeong.portal.domain.entity.Assignment;
import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture;
import com.skuniv.cs.geonyeong.portal.domain.entity.LectureDetail;
import com.skuniv.cs.geonyeong.portal.domain.vo.ProfessorAssignmentDetail;
import com.skuniv.cs.geonyeong.portal.service.AssignmentService;
import com.skuniv.cs.geonyeong.portal.service.LectureService;
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

    private final LectureService lectureService;
    private final AssignmentService assignmentService;

    @RequestMapping(value = "/semester/{semesterId}/lecture", method = {RequestMethod.GET})
    public List<Lecture> getLectures(
        @PathVariable(value = "semesterId") Long semesterId,
        @RequestAttribute(name = PROFESSOR_ID_KEY) String professorId) {
        log.info("lectures");
        return lectureService.getLectures(semesterId, professorId);
    }

    @RequestMapping(value = "/semester/{semesterId}/lecture", method = {RequestMethod.POST})
    public Lecture createLecture(
        @PathVariable(value = "semesterId") Long semesterId,
        @RequestAttribute(name = PROFESSOR_ID_KEY) String professorId,
        @RequestBody Lecture lecture) throws JsonProcessingException {
        log.info("createLecture");
        return lectureService.createLecture(semesterId, professorId, lecture);
    }

    @RequestMapping(value = "/lecture/{lectureId}", method = {RequestMethod.GET})
    public List<LectureDetail> getLectureDetails(
        @PathVariable(value = "lectureId") Long lectureId) {
        return lectureService.getLectureDetails(lectureId);
    }

    @RequestMapping(value = "/lecture/detail/{lectureDetailId}", method = {RequestMethod.PUT})
    public LectureDetail updateLectureDetailCanceled(
        @PathVariable(value = "lectureDetailId") Long lectureDetailId) {
        return lectureService.updateLectureDetailCanceled(lectureDetailId);
    }

    @RequestMapping(value = "/lecture/{lectureId}/assignment", method = {RequestMethod.POST})
    public Assignment createLectureAssignment(
        @PathVariable(value = "lectureId") Long lectureId
        , @RequestBody Assignment assignment) {
        return assignmentService.createLectureAssignment(lectureId, assignment);
    }

    @RequestMapping(value = "/lecture/{lectureId}/assignment", method = {RequestMethod.GET})
    public List<Assignment> getLectureAssignments(
        @PathVariable(value = "lectureId") Long lectureId) {
        return assignmentService.getLectureAssignments(lectureId);
    }

    @RequestMapping(value = "/lecture/assignment/{assignmentId}", method = {RequestMethod.GET})
    public List<ProfessorAssignmentDetail> getProfessorAssignmentDetail(
        @PathVariable(value = "assignmentId") Long assignmentId) {
        return assignmentService.getProfessorAssignmentDetail(assignmentId);
    }
}
