--liquibase formatted sql

--changeset fipp1337:1

CREATE SCHEMA IF NOT EXISTS workshop;
SET search_path TO workshop;

create table printers (
  id       BIGSERIAL PRIMARY KEY,
  name     VARCHAR(100) NOT NULL,        -- "Prusa-1", "Ender-2"
  material VARCHAR(50)  NOT NULL,        -- which material this printer supports
  status   VARCHAR(20)  NOT NULL         -- FREE | BUSY
);

create table jobs (
  id           BIGSERIAL PRIMARY KEY,
  order_id     BIGINT NOT NULL,          -- id from order-service; NOT a foreign key
  printer_id   BIGINT REFERENCES printers(id),
  status       VARCHAR(20) NOT NULL,     -- WAITING | PRINTING | DONE | FAILED
  created_at   TIMESTAMP NOT NULL DEFAULT now(),
  started_at   TIMESTAMP,
  finished_at  TIMESTAMP
);