package com.gdgoc.t26.zero_gap.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissionPatchResponse {
    private String cheerMessage;

    public static MissionPatchResponse from(String cheerMessage) {
        return new MissionPatchResponse(cheerMessage);
    }
}
