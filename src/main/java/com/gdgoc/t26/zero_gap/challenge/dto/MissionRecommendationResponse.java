package com.gdgoc.t26.zero_gap.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissionRecommendationResponse {
    private List<String> missionRecommendations;

    public static MissionRecommendationResponse from(List<String> recommendations) {
        return new MissionRecommendationResponse(recommendations);
    }
}
