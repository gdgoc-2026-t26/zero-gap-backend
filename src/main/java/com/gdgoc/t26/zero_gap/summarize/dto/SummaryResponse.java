package com.gdgoc.t26.zero_gap.summarize.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResponse {
    private String summary;

    public static SummaryResponse from(String summary) {
        return new SummaryResponse(summary);
    }
}
