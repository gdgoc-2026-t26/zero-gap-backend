package com.gdgoc.t26.zero_gap.challenge.repository;

import com.gdgoc.t26.zero_gap.challenge.domain.Challenge;
import com.gdgoc.t26.zero_gap.challenge.domain.DurationCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, UUID> {
    List<Challenge> findByDurationCategory(DurationCategory durationCategory);
    List<Challenge> findByTitle(String title);
}
