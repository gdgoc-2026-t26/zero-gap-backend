package com.gdgoc.t26.zero_gap.summarize.controller;

import com.gdgoc.t26.zero_gap.summarize.dto.SummaryResponse;
import com.gdgoc.t26.zero_gap.summarize.service.SummarizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/summary")
@RequiredArgsConstructor
public class SummarizeController {

    private final SummarizeService summarizeService;

    @GetMapping
    public SummaryResponse getSummary(
            @AuthenticationPrincipal String username,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        String summary = summarizeService.generateSummary(username, startDate, endDate);
        return SummaryResponse.from(summary);
    }
}
