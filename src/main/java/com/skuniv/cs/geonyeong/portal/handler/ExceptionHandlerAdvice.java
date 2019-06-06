package com.skuniv.cs.geonyeong.portal.handler;

import com.skuniv.cs.geonyeong.portal.domain.vo.ExceptionResponse;
import com.skuniv.cs.geonyeong.portal.enums.ExceptionType;
import com.skuniv.cs.geonyeong.portal.exception.MissingAccountTypeException;
import com.skuniv.cs.geonyeong.portal.exception.ProfessorSignInException;
import com.skuniv.cs.geonyeong.portal.exception.TokenExpireException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {
    "com.skuniv.cs.geonyeong.portal.controller",
    "com.skuniv.cs.geonyeong.portal.filter"
})
public class ExceptionHandlerAdvice {

    private final ExceptionResponse DEFAULT_EXCEPTION_RESPONSE = ExceptionResponse.builder()
        .status(80800).message("Unknown Exception").build();

    @ExceptionHandler(value = ProfessorSignInException.class)
    public ExceptionResponse handleParsingException(ProfessorSignInException e) {
        return ExceptionType.getExceptionResponse(e).orElse(DEFAULT_EXCEPTION_RESPONSE);
    }

    @ExceptionHandler(value = TokenExpireException.class)
    public ExceptionResponse handleTokenExpireException(TokenExpireException e) {
        return ExceptionType.getExceptionResponse(e).orElse(DEFAULT_EXCEPTION_RESPONSE);
    }

    @ExceptionHandler(value = MissingAccountTypeException.class)
    public ExceptionResponse handleMissingAccountTypeException(MissingAccountTypeException e) {
        return ExceptionType.getExceptionResponse(e).orElse(DEFAULT_EXCEPTION_RESPONSE);
    }
}
