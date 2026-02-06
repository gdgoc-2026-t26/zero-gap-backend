-- Update user_challenges table to match api.md requirements
ALTER TABLE user_challenges MODIFY user_id BIGINT NOT NULL;
ALTER TABLE user_challenges ADD COLUMN date DATE NOT NULL;
ALTER TABLE user_challenges ADD COLUMN accomplished BOOLEAN NOT NULL DEFAULT FALSE;
