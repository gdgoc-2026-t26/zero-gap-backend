package com.gdgoc.t26.zero_gap.challenge.controller;

import com.gdgoc.t26.zero_gap.challenge.domain.UserChallenge;
import com.gdgoc.t26.zero_gap.challenge.dto.*;
import com.gdgoc.t26.zero_gap.challenge.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping
    public MissionListResponse getMissions(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        Long userId = 1L; // Placeholder
        List<MissionResponse> missions = challengeService.getMissionsByDate(userId, startDate, endDate)
                .stream()
                .map(MissionResponse::from)
                .collect(Collectors.toList());
        return MissionListResponse.from(missions);
    }

    @GetMapping("/today")
    public MissionRecommendationResponse getTodayRecommendations(
            @RequestParam(required = false) Integer durationInSeconds) {
        return MissionRecommendationResponse.from(challengeService.getTodayRecommendations(durationInSeconds));
    }

    @PostMapping
    public MissionCreateResponse createMission(@RequestBody MissionCreateRequest request) {
        Long userId = 1L; // Placeholder
        return challengeService.createMission(userId, request.getName(), request.getDate());
    }

    @PatchMapping("/{missionId}")
    public MissionPatchResponse patchMission(
            @PathVariable UUID missionId,
            @RequestBody MissionPatchRequest request) {
        Long userId = 1L; // Placeholder
        return challengeService.patchMission(userId, missionId, request.getAccomplished(), request.getDescription());
    }

    // Keep old start/complete if needed, but api.md doesn't specify them in this format.
    // Given the request is to fix based on api.md, I will replace them or comment them out.
    /*
    @PostMapping("/{id}/start")
    public UserChallengeResponse startChallenge(@PathVariable UUID id, @RequestBody StartChallengeRequest request) {
        Long userId = 1L;
        return UserChallengeResponse.from(challengeService.startChallenge(userId, id, request.getDescription()));
    }
    */
}
