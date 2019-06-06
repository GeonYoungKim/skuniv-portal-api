package com.skuniv.cs.geonyeong.portal.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MissingAccountTypeException extends RuntimeException {

    public MissingAccountTypeException(String message) {
        super(message);
    }

    public MissingAccountTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingAccountTypeException(Throwable cause) {
        super(cause);
    }
}
