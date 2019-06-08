package com.skuniv.cs.geonyeong.portal.handler;

import com.skuniv.cs.geonyeong.portal.domain.vo.ExceptionResponse;
import com.skuniv.cs.geonyeong.portal.enums.ExceptionType;
import com.skuniv.cs.geonyeong.portal.exception.MissingAccountTypeException;
import com.skuniv.cs.geonyeong.portal.exception.SignInException;
import com.skuniv.cs.geonyeong.portal.exception.TokenExpireException;
import com.skuniv.cs.geonyeong.portal.exception.TokenInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {
    "com.skuniv.cs.geonyeong.portal.controller"
})
public class ExceptionHandlerAdvice {

    private final ExceptionResponse DEFAULT_EXCEPTION_RESPONSE = ExceptionResponse.builder()
        .status(80800).message("Unknown Exception").build();

    @ExceptionHandler(value = SignInException.class)
    public ExceptionResponse handleParsingException(SignInException e) {
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

    @ExceptionHandler(value = TokenInvalidException.class)
    public ExceptionResponse handleMissingAccountTypeException(TokenInvalidException e) {
        return ExceptionType.getExceptionResponse(e).orElse(DEFAULT_EXCEPTION_RESPONSE);
    }
}
