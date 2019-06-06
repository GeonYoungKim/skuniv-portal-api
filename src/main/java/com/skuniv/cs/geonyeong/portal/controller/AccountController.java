package com.skuniv.cs.geonyeong.portal.controller;

import com.skuniv.cs.geonyeong.portal.domain.entity.Professor;
import com.skuniv.cs.geonyeong.portal.domain.vo.AccountResponse;
import com.skuniv.cs.geonyeong.portal.enums.AccountType;
import com.skuniv.cs.geonyeong.portal.service.ProfessorService;
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

    private final ProfessorService professorService;

    @RequestMapping(value = "/professor/signUp", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Professor signUp(@RequestBody Professor professor) {
        return professorService.signUp(professor);
    }

    @RequestMapping(value = "/professor/signIn", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountResponse signIn(@RequestBody Professor professor) {
        String token = professorService.signIn(professor);
        return AccountResponse.builder().token(token).accountType(AccountType.PROFESSOR).build();
    }

}
