package com.gdgoc.t26.zero_gap.emotion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmotionRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    private String description;
}
