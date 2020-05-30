ALTER TABLE user_device RENAME notification_token TO access_token;
ALTER TABLE user_device ALTER COLUMN access_token SET NOT NULL;