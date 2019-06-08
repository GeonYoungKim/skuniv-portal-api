package com.skuniv.cs.geonyeong.portal.controller;

import com.skuniv.cs.geonyeong.portal.domain.entity.Professor;
import com.skuniv.cs.geonyeong.portal.domain.entity.Student;
import com.skuniv.cs.geonyeong.portal.domain.vo.AccountResponse;
import com.skuniv.cs.geonyeong.portal.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/portal/account")
public class AccountController {

    private final AccountService accountService;

    @RequestMapping(value = "/professor/signUp", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Professor professorSignUp(@RequestBody Professor professor) {
        log.info("professor => {}", professor);
        return accountService.signUp(professor);
    }

    @RequestMapping(value = "/professor/signIn", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountResponse professorSignIn(@RequestBody Professor professor) {
        return accountService.signIn(professor);
    }

    @RequestMapping(value = "/student/signUp", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Student studentSignUp(@RequestBody Student student) {
        return accountService.signUp(student);
    }

    @RequestMapping(value = "/student/signIn", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountResponse studentSignIn(@RequestBody Student student) {
        return accountService.signIn(student);
    }
}
