package com.gdgoc.t26.zero_gap.challenge.dto;

import com.gdgoc.t26.zero_gap.challenge.domain.UserChallenge;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class MissionResponse {
    private UUID id;
    private String name;
    private LocalDate date;
    private Boolean accomplished;
    private String description;

    public static MissionResponse from(UserChallenge userChallenge) {
        return MissionResponse.builder()
                .id(userChallenge.getId())
                .name(userChallenge.getChallenge().getTitle())
                .date(userChallenge.getDate())
                .accomplished(userChallenge.getAccomplished())
                .description(userChallenge.getDescription())
                .build();
    }
}
