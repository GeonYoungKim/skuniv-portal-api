package com.skuniv.cs.geonyeong.portal.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LectureDay {
    private String lectureDay;
    private String startTime;
    private Double detailTime;

    @Builder
    public LectureDay(String lectureDay, String startTime, Double detailTime) {
        this.lectureDay = lectureDay;
        this.startTime = startTime;
        this.detailTime = detailTime;
    }
}
