package com.gdgoc.t26.zero_gap.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissionListResponse {
    private List<MissionResponse> missions;

    public static MissionListResponse from(List<MissionResponse> missions) {
        return new MissionListResponse(missions);
    }
}
