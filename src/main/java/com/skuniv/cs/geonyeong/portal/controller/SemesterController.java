package com.skuniv.cs.geonyeong.portal.controller;

import static com.skuniv.cs.geonyeong.portal.constant.PortalConstant.PROFESSOR_ID_KEY;

import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture;
import com.skuniv.cs.geonyeong.portal.domain.entity.Semester;
import com.skuniv.cs.geonyeong.portal.repository.SemesterRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
@RequestMapping("/api/v1/portal/semester")
public class SemesterController {
    private static final SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd");
    private final String START_DATE = "20190301";
    private final String END_DATE = "20190620";

    private final String START_DATE2 = "20190901";
    private final String END_DATE2 = "20191220";

    private final SemesterRepository semesterRepository;

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    public void craeteSemester() throws ParseException {
            Semester semester = Semester.builder()
                .name("1학기")
                .startDate(dt.parse(START_DATE))
                .endDate(dt.parse(END_DATE))
                .build();
            semesterRepository.save(semester);
        semester = Semester.builder()
            .name("2학기")
            .startDate(dt.parse(START_DATE2))
            .endDate(dt.parse(END_DATE2))
            .build();
        semesterRepository.save(semester);

        semesterRepository.flush();


    }
}
