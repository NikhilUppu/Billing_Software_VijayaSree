ALTER TABLE user_account ADD COLUMN IF NOT EXISTS company_id VARCHAR(50);
ALTER TABLE user_account
	ADD CONSTRAINT fk_user_company FOREIGN KEY (company_id) REFERENCES company(id);
CREATE INDEX IF NOT EXISTS idx_user_account_company ON user_account(company_id);
