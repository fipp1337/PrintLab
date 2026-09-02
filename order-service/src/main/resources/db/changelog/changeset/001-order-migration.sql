--liquibase formatted sql

--changeset fipp1337:1

CREATE SCHEMA IF NOT EXISTS orders;
SET search_path TO orders;

create table students (
  id          BIGSERIAL PRIMARY KEY,
  email       VARCHAR(255) NOT NULL UNIQUE,
  name        VARCHAR(255) NOT NULL,
  role        VARCHAR(255) NOT NULL
);

create table print_orders (
  id             BIGSERIAL PRIMARY KEY,
  student_id     BIGINT NOT NULL REFERENCES students(id),
  model_name     VARCHAR(255) NOT NULL,   -- file name or link, no file upload in v1
  material       VARCHAR(50)  NOT NULL,   -- PLA | PETG | ABS
  status         VARCHAR(20)  NOT NULL,   -- NEW | QUEUED | PRINTING | DONE | FAILED | CANCELLED
  failure_reason VARCHAR(255),
  created_at     TIMESTAMP NOT NULL DEFAULT now(),
  updated_at     TIMESTAMP NOT NULL DEFAULT now()
);