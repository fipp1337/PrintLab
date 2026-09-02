--liquibase formatted sql

--changeset fipp1337:1

CREATE SCHEMA IF NOT EXISTS notify;
SET search_path TO notify;

create table sent_notifications (
  id        BIGSERIAL PRIMARY KEY,
  event_id  VARCHAR(36) NOT NULL UNIQUE,
  order_id  BIGINT NOT NULL,
  channel   VARCHAR(20) NOT NULL,        -- CONSOLE | EMAIL | TELEGRAM
  sent_at   TIMESTAMP NOT NULL DEFAULT now()
);