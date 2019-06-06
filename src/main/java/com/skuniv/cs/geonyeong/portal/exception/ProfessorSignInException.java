package com.skuniv.cs.geonyeong.portal.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProfessorSignInException extends RuntimeException{

    public ProfessorSignInException(String message) {
        super(message);
    }

    public ProfessorSignInException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProfessorSignInException(Throwable cause) {
        super(cause);
    }
}
