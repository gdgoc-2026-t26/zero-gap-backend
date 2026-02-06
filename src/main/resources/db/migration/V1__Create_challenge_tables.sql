CREATE TABLE challenges (
    id BINARY(16) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    duration_category VARCHAR(50) NOT NULL,
    ai_generated BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_challenges (
    id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    challenge_id BINARY(16) NOT NULL,
    status VARCHAR(50) NOT NULL,
    description TEXT,
    start_time DATETIME,
    completion_time DATETIME,
    progress_log TEXT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_challenges_challenge FOREIGN KEY (challenge_id) REFERENCES challenges(id)
);
