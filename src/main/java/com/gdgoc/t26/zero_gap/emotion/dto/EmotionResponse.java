package com.gdgoc.t26.zero_gap.emotion.dto;

import com.gdgoc.t26.zero_gap.emotion.domain.Emotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmotionResponse {
    private Long id;
    private Integer score;
    private LocalDate date;
    private String description;

    public static EmotionResponse from(Emotion emotion) {
        return EmotionResponse.builder()
                .id(emotion.getId())
                .score(emotion.getScore())
                .date(emotion.getDate())
                .description(emotion.getDescription())
                .build();
    }
}
