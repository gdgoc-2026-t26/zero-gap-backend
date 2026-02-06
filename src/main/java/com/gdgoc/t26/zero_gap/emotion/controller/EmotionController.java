package com.gdgoc.t26.zero_gap.emotion.controller;

import com.gdgoc.t26.zero_gap.emotion.dto.EmotionCreateResponse;
import com.gdgoc.t26.zero_gap.emotion.dto.EmotionListResponse;
import com.gdgoc.t26.zero_gap.emotion.dto.EmotionRequest;
import com.gdgoc.t26.zero_gap.emotion.service.EmotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/emotions")
@RequiredArgsConstructor
public class EmotionController {

    private final EmotionService emotionService;

    @GetMapping
    public EmotionListResponse getEmotions(
            @AuthenticationPrincipal String email,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return new EmotionListResponse(emotionService.getEmotionsByDateRange(email, startDate, endDate));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmotionCreateResponse registerEmotion(
            @AuthenticationPrincipal String email,
            @RequestBody @Valid EmotionRequest request) {
        return new EmotionCreateResponse(emotionService.registerEmotion(email, request));
    }
}
