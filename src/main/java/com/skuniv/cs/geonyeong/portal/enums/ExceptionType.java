package com.skuniv.cs.geonyeong.portal.enums;

import com.skuniv.cs.geonyeong.portal.domain.vo.ExceptionResponse;
import com.skuniv.cs.geonyeong.portal.exception.MissingAccountTypeException;
import com.skuniv.cs.geonyeong.portal.exception.SignInException;
import com.skuniv.cs.geonyeong.portal.exception.TokenExpireException;
import com.skuniv.cs.geonyeong.portal.exception.TokenInvalidException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;


@Getter
@AllArgsConstructor
public enum ExceptionType {
    SIGNIN_EXCEPTION(SignInException.class, 80801, "Sign In Exception"),
    TOKEN_EXPIRE_EXCEPTION(TokenExpireException.class, 80802, "Token Expire"),
    TOKEN_INVALID_EXCEPTION(TokenInvalidException.class, 80803, "Token Invalid"),
    MISS_ACCOUNT_TYPE_EXCEPTION(MissingAccountTypeException.class, 10001, "request header's missing Account-Type Key")
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
