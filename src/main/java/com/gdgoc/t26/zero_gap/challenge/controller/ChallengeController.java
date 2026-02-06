package com.gdgoc.t26.zero_gap.challenge.controller;

import com.gdgoc.t26.zero_gap.challenge.domain.DurationCategory;
import com.gdgoc.t26.zero_gap.challenge.dto.ChallengeResponse;
import com.gdgoc.t26.zero_gap.challenge.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping
    public List<ChallengeResponse> getChallenges(@RequestParam(required = false) DurationCategory duration) {
        // If duration is null, we might want to default to something or return all.
        // For now, let's assume it's required or handled by service.
        return challengeService.getRecommendedChallenges(duration != null ? duration : DurationCategory.SHORT)
                .stream()
                .map(ChallengeResponse::from)
                .collect(Collectors.toList());
    }
}
