--liquibase formatted sql

--changeset naivez:1
ALTER TABLE users RENAME COLUMN is_blacklisted TO blacklisted;
--rollback ALTER TABLE users RENAME COLUMN blacklisted TO is_blacklisted;


--changeset naivez:2
ALTER TABLE users ALTER COLUMN blacklisted SET DEFAULT FALSE;
--rollback ALTER TABLE users ALTER COLUMN blacklisted DROP DEFAULT;
