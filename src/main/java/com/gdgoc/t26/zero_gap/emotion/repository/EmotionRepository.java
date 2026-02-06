package com.gdgoc.t26.zero_gap.emotion.repository;

import com.gdgoc.t26.zero_gap.emotion.domain.Emotion;
import com.gdgoc.t26.zero_gap.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {
    List<Emotion> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
