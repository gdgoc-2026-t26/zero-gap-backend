# Zero-Gap Backend Project Guide

This guide is designed for AI agents to quickly understand the Zero-Gap backend codebase, architecture, and tech stack.

## 1. Project Overview
**Zero-Gap** is a burnout recovery and career preparation assistance app for job seekers. It focuses on mental care and spec improvement through small actions and emotion tracking.

- **Objective**: Help job seekers escape burnout and directionless states through data-driven action logs and AI-powered strategy.
- **Key Concepts**: 
  - **Challenge**: Small actions (5/20/60+ mins) recommended by AI.
  - **Emotion Diary**: Tracking mental state along with actions.
  - **Recovery Trigger**: Re-engagement system for inactive users.

## 2. Tech Stack
- **Framework**: Spring Boot 3.5.10 (Java 21)
- **Database**: MySQL (with Flyway for migrations)
- **Security**: Spring Security + JWT
- **AI Integration**: Spring AI (Google GenAI - Gemini)
- **Deployment**: Google Cloud (Google Container Registry), Jib for containerization.
- **Testing**: JUnit 5, Mockito, H2 (for unit tests).

## 3. Project Structure
```text
src/main/java/com/gdgoc/t26/zero_gap
├── challenge/        # Core domain: Action challenges and user participation
│   ├── controller/   # API Endpoints
│   ├── domain/       # JPA Entities (Challenge, UserChallenge)
│   ├── dto/          # Data Transfer Objects
│   ├── repository/   # JPA Repositories
│   └── service/      # Business Logic
├── common/           # Cross-cutting concerns
│   └── exception/    # Global exception handling
└── config/           # Infrastructure config (Security, JPA, AI)
```

## 4. Domain & Data Model
Core tables defined in Flyway migrations (`src/main/resources/db/migration`):
- `challenges`: Master list of available actions/challenges.
- `user_challenges`: Tracks user participation, status (TODO, COMPLETED), and progress logs.

## 5. API Endpoints (Snapshot)
See `api.md` for the full, live API specification.
- `/auth/**`: Login and registration.
- `/users/**`: Profile management.
- `/missions/**`: Challenge recommendation and tracking.
- `/emotions/**`: Emotion logging.
- `/summary/**`: AI-generated status summaries.

## 6. Development Workflow
- **Build**: `./gradlew build`
- **Run**: `./gradlew bootRun`
- **Test**: `./gradlew test` (Uses H2 in-memory DB)
- **Database Migrations**: Add new SQL scripts to `src/main/resources/db/migration/` following the `V{N}__Description.sql` pattern.
