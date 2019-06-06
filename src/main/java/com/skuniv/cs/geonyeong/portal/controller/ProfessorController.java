package com.skuniv.cs.geonyeong.portal.controller;


import static com.skuniv.cs.geonyeong.portal.constant.PortalConstant.PROFESSOR_ID_KEY;

import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture;
import com.skuniv.cs.geonyeong.portal.service.LectureService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/portal/professor")
public class ProfessorController {
    private final LectureService lectureService;

    @RequestMapping(value = "/lecture", method = {RequestMethod.GET})
    public List<Lecture> getLectures(@RequestAttribute(name = PROFESSOR_ID_KEY) String professorId) {
        log.info("lectures");
        return lectureService.getLectures(professorId);
    }

    @RequestMapping(value = "/lecture", method = {RequestMethod.POST})
    public Lecture createLecture(@RequestAttribute(name = PROFESSOR_ID_KEY) String professorId, @RequestBody Lecture lecture) {
        return lectureService.createLecture(professorId, lecture);
    }
}
