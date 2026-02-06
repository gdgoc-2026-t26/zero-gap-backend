package com.gdgoc.t26.zero_gap.challenge.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_challenges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    @NotNull(message = "Challenge cannot be null")
    private Challenge challenge;

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    private String description;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @Builder.Default
    private Boolean accomplished = false;

    private LocalDateTime startTime;

    private LocalDateTime completionTime;

    @Column(columnDefinition = "TEXT")
    private String progressLog;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
