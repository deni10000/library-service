CREATE TABLE account
(
    id      bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    name    varchar NOT NULL,
    surname varchar NOT NULL,
    age     int NOT NULL
);