ALTER TABLE user_account ADD COLUMN IF NOT EXISTS mobile VARCHAR(10);
CREATE UNIQUE INDEX IF NOT EXISTS ux_user_account_mobile ON user_account(mobile);
