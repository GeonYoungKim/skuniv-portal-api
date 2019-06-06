package com.skuniv.cs.geonyeong.portal.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenExpireException extends RuntimeException {

    public TokenExpireException(String message) {
        super(message);
    }

    public TokenExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenExpireException(Throwable cause) {
        super(cause);
    }
}
