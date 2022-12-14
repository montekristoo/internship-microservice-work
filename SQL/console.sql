CREATE TABLE databases
(
    id       SERIAL  NOT NULL,
    name     VARCHAR NOT NULL,
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    jdbc_url VARCHAR NOT NULL
);

SELECT *
FROM databases;

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

DELETE
FROM databases
WHERE name = 'main_db';

UPDATE databases
SET password = crypt(password, gen_salt('bf'))
WHERE name = 'main_db';

SELECT name, username, password, jdbc_url
FROM databases;

SELECT *
FROM databases;

SELECT *
FROM pg_stat_activity;

DELETE
FROM databases
WHERE name = 'db_2';

SELECT *
FROM databases;

SELECT *
FROM pg_stat_activity;


CREATE DATABASE db_4;

SELECT *
FROM current_database();

TRUNCATE test_table;

SELECT *
FROM pg_stat_activity;

ALTER TABLE databases
    ADD COLUMN password_salt varchar DEFAULT NULL;

ALTER TABLE databases
    DROP COLUMN password_salt;

---------- PROCEDURES

CREATE OR REPLACE PROCEDURE add_database(name varchar, username varchar, password varchar, jdbc_url varchar,
                                         driver_class_name varchar, password_salt varchar)
    LANGUAGE plpgsql
AS
$$
BEGIN
    INSERT INTO databases (name, username, password, jdbc_url, driver_class_name, password_salt)
    VALUES (name, username, password, jdbc_url, driver_class_name, password_salt);
END;
$$;

DROP PROCEDURE add_database(name varchar, username varchar, password varchar, jdbc_url varchar);

CREATE OR REPLACE FUNCTION get_database(db_name varchar)
    RETURNS TABLE
            (
                id                int,
                name              varchar,
                username          varchar,
                password          varchar,
                jdbc_url          varchar,
                driver_class_name varchar,
                password_salt     varchar
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT db.id, db.name, db.username, db.password, db.jdbc_url, db.driver_class_name, db.password_salt
        FROM databases db
        WHERE db.name ~~* db_name;
END;
$$;

DROP FUNCTION get_database(db_name varchar);


CREATE OR REPLACE FUNCTION get_all_databases()
    RETURNS TABLE
            (
                id                int,
                name              varchar,
                username          varchar,
                password          varchar,
                jdbc_url          varchar,
                driver_class_name varchar,
                salt              varchar
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT * FROM databases;
END;
$$;

DROP FUNCTION get_all_databases();

SELECT *
FROM get_all_databases();

CREATE DATABASE test_db;
DELETE FROM databases
WHERE name = 'test_db';

select datname
from pg_database
WHERE length(datname) = 2;

BEGIN TRANSACTION;
INSERT INTO test_table (description)
VALUES ('testTT');
 BEGIN TRANSACTION;
INSERT INTO test_table_2 (value)
VALUES (2);
END WORK;
END WORK;

SELECT * FROM pg_prepared_xacts;

SHOW config_file;
ALTER system SET max_prepared_transactions = 100;


ALTER SYSTEM SET log_statement = 'all';
SELECT set_config('log_destination', 'stderr', true);

SHOW data_directory;



