package com.skuniv.cs.geonyeong.portal.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ExceptionResponse {
    private int status;
    private String message;
}
