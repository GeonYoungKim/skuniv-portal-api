package com.skuniv.cs.geonyeong.portal.controller;

import com.skuniv.cs.geonyeong.portal.domain.entity.Professor;
import com.skuniv.cs.geonyeong.portal.domain.vo.AccountResponse;
import com.skuniv.cs.geonyeong.portal.enums.AccountType;
import com.skuniv.cs.geonyeong.portal.service.AccountService;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    private final AccountService professorService;

    @RequestMapping(value = "/professor/signUp", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Professor signUp(@RequestBody Professor professor) {
        log.info("professor => {}", professor);
        return professorService.signUp(professor);
    }

    @RequestMapping(value = "/professor/signIn", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountResponse signIn(@RequestBody Professor professor) {
        return professorService.signIn(professor);
    }
}
