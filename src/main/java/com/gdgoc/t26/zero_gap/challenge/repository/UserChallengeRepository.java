package com.gdgoc.t26.zero_gap.challenge.repository;

import com.gdgoc.t26.zero_gap.challenge.domain.ChallengeStatus;
import com.gdgoc.t26.zero_gap.challenge.domain.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserChallengeRepository extends JpaRepository<UserChallenge, UUID> {
    List<UserChallenge> findByUserId(UUID userId);
    List<UserChallenge> findByUserIdAndStatus(UUID userId, ChallengeStatus status);
}
