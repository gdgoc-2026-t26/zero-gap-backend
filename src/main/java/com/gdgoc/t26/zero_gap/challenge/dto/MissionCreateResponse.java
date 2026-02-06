package com.gdgoc.t26.zero_gap.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionCreateResponse {
    private UUID id;
    private String cheerMessage;

    public static MissionCreateResponse of(UUID id, String cheerMessage) {
        return MissionCreateResponse.builder()
                .id(id)
                .cheerMessage(cheerMessage)
                .build();
    }
}
