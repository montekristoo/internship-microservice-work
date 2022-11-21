CREATE TABLE databases
(
    id       SERIAL  NOT NULL,
    name     VARCHAR NOT NULL,
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    jdbc_url VARCHAR NOT NULL
);

SELECT * FROM databases;

ALTER TABLE databases
    ADD CONSTRAINT unique_name UNIQUE (name);

ALTER TABLE databases
    ALTER COLUMN password SET DEFAULT encode(gen_random_bytes(32), 'hex');

ALTER SEQUENCE databases_id_seq
    RESTART WITH 1;

TRUNCATE TABLE databases;
ALTER TABLE databases
ADD COLUMN driver_class_name varchar;

UPDATE databases
    SET driver_class_name = 'org.postgresql.Driver';

INSERT INTO databases (name, username, password, jdbc_url)
VALUES ('db_1', 'postgres', 'internship', 'jdbc:postgresql://localhost:5432/db_1'),
       ('db_2', 'postgres', 'internship', 'jdbc:postgresql://localhost:5432/db_2'),
       ('db_3', 'postgres', 'internship', 'jdbc:postgresql://localhost:5432/db_3');

INSERT INTO databases (name, username, password, jdbc_url)
VALUES ('main_db', 'postgres', 'internship', 'jdbc:postgresql://localhost:5432/main_db');

DELETE FROM databases
WHERE name = 'main_db';

UPDATE databases
SET password = crypt(password, gen_salt('bf'))
WHERE name = 'main_db';

SELECT name, username, password, jdbc_url FROM databases;

SELECT *
FROM databases;

SELECT * FROM pg_stat_activity;

DELETE FROM databases
WHERE name = 'db_2';

SELECT * FROM databases;

SELECT COUNT(*)
FROM pg_stat_activity;


CREATE DATABASE db_4;

SELECT COUNT(*) FROM pg_stat_activity
WHERE datname = 'db_3';

---------- PROCEDURES

CREATE OR REPLACE PROCEDURE add_datasource(name varchar, username varchar, password varchar, jdbc_url varchar, driver_class_name varchar)
    LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO databases (name, username, password, jdbc_url, driver_class_name)
    VALUES (name, username, crypt(password, gen_salt('bf')), jdbc_url, driver_class_name);
END;
$$;

call add_datasource('db_4', 'postgres', 'internship', 'jdbc:postgresql://localhost:5432/db_4');

