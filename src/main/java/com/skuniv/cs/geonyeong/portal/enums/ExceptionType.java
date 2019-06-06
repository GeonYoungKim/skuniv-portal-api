package com.skuniv.cs.geonyeong.portal.enums;

import com.skuniv.cs.geonyeong.portal.domain.vo.ExceptionResponse;
import com.skuniv.cs.geonyeong.portal.exception.ProfessorSignInException;
import com.skuniv.cs.geonyeong.portal.exception.TokenExpireException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;


@Getter
@AllArgsConstructor
public enum ExceptionType {
    PROFESSOR_SIGNIN_EXCEPTION(ProfessorSignInException.class, 80801, "Professor Sign In Exception"),
    TOKEN_EXPIRE_EXCEPTION(TokenExpireException.class, 80802, "Token Expire")
    ;

    private Class<? extends Exception> exception;
    private int status;
    private String message;

    public static Optional<ExceptionResponse> getExceptionResponse(RuntimeException exception) {
        for(ExceptionType item : ExceptionType.values()) {
            if(StringUtils.equals(exception.getClass().getName(), item.getException().getName())){
                return Optional.ofNullable(
                    ExceptionResponse.builder().status(item.getStatus()).message(item.getMessage())
                        .build());
            }
        }
        return Optional.empty();
    }
}
