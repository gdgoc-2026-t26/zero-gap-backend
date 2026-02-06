# Specification: Implement Action Challenges API

## 1. Introduction
This document specifies the requirements for the Action Challenges API, a core component of the Zero-Gap application. This API will enable the frontend to retrieve, manage, and track user engagement with various AI-recommended challenges.

## 2. Goals
- Provide endpoints for fetching AI-recommended challenges.
- Allow users to start, complete, or log progress for challenges.
- Support filtering of challenges based on duration (short, medium, long).
- Integrate with the AI API (Gemini 3) for challenge generation.
- Ensure data persistence for user challenge progress.

## 3. API Endpoints

### 3.1 Get Recommended Challenges
- **Method:** GET
- **Path:** `/challenges`
- **Query Parameters:**
    - `duration`: Optional. Filters challenges by duration (e.g., `short`, `medium`, `long`).
- **Response:** A list of challenge objects, each containing:
    - `id`: Unique challenge identifier.
    - `title`: Short description of the challenge.
    - `description`: Detailed instructions for the challenge.
    - `duration_category`: `short`, `medium`, or `long`.
    - `ai_generated`: Boolean indicating if the challenge was AI-generated.

### 3.2 Start a Challenge
- **Method:** POST
- **Path:** `/challenges/{challenge_id}/start`
- **Request Body:** None
- **Response:**
    - `status`: `started`
    - `start_time`: Timestamp of when the challenge was started.

### 3.3 Complete a Challenge
- **Method:** POST
- **Path:** `/challenges/{challenge_id}/complete`
- **Request Body:** None
- **Response:**
    - `status`: `completed`
    - `completion_time`: Timestamp of when the challenge was completed.
    - `duration_taken`: How long it took to complete the challenge.

### 3.4 Log Challenge Progress (Optional for future expansion)
- **Method:** POST
- **Path:** `/challenges/{challenge_id}/progress`
- **Request Body:**
    - `progress_details`: Text or structured data describing progress.
- **Response:** Confirmation of progress logged.

## 4. Data Model

### Challenge
- `id` (UUID)
- `title` (String)
- `description` (String)
- `duration_category` (Enum: `SHORT`, `MEDIUM`, `LONG`)
- `ai_generated` (Boolean)
- `created_at` (Timestamp)
- `updated_at` (Timestamp)

### UserChallenge
- `id` (UUID)
- `user_id` (UUID)
- `challenge_id` (UUID)
- `status` (Enum: `PENDING`, `STARTED`, `COMPLETED`, `ABANDONED`)
- `start_time` (Timestamp, nullable)
- `completion_time` (Timestamp, nullable)
- `progress_log` (JSONB or Text, nullable, for detailed progress notes)
- `created_at` (Timestamp)
- `updated_at` (Timestamp)

## 5. Non-Functional Requirements
- **Performance:** API responses should be swift, aiming for <100ms for typical requests.
- **Security:** All endpoints must be authenticated and authorized. Data must be encrypted in transit and at rest.
- **Scalability:** The API should be designed to handle a growing number of users and challenges.
- **Reliability:** High availability with appropriate error handling and retry mechanisms.
- **Maintainability:** Clear, well-documented code with unit and integration tests.

## 6. Integration Points
- **Frontend Application:** Consumes all defined API endpoints.
- **Gemini 3 API:** Called by the backend to generate challenge suggestions.
- **MySQL Database:** Persists all challenge and user challenge data.
