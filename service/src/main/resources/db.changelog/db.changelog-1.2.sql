--liquibase formatted sql

--changeset naivez:1
ALTER TABLE review ALTER COLUMN description SET NOT NULL;
--rollback ALTER TABLE review ALTER COLUMN description DROP NOT NULL;
