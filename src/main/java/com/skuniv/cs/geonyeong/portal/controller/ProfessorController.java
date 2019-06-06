package com.skuniv.cs.geonyeong.portal.controller;


import com.skuniv.cs.geonyeong.portal.domain.entity.Professor;
import com.skuniv.cs.geonyeong.portal.domain.vo.AccountResponse;
import com.skuniv.cs.geonyeong.portal.enums.AccountType;
import com.skuniv.cs.geonyeong.portal.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final ProfessorService professorService;

    @RequestMapping(value = "/signUp", method = {RequestMethod.POST})
    public Professor signUp(@RequestBody Professor professor) {
        return professorService.signUp(professor);
    }

    @RequestMapping(value = "/signIn", method = {RequestMethod.POST})
    public AccountResponse signIn(@RequestBody Professor professor) {
        String token = professorService.signIn(professor);
        return AccountResponse.builder().token(token).accountType(AccountType.PROFESSOR).build();
    }
}
