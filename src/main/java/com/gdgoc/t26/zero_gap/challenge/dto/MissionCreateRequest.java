package com.gdgoc.t26.zero_gap.challenge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MissionCreateRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;
}
