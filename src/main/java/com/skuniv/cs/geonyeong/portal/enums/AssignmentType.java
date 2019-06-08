package com.skuniv.cs.geonyeong.portal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssignmentType {
    ASSIGNMENT("과제"),
    EXAM("시험")
        ;
    private String type;
}
