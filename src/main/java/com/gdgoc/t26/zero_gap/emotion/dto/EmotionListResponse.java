package com.gdgoc.t26.zero_gap.emotion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmotionListResponse {
    private List<EmotionResponse> emotions;
}
