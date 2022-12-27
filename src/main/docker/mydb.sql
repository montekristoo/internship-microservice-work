--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Drop databases (except postgres and template1)
--

DROP DATABASE IF EXISTS cr;
DROP DATABASE IF EXISTS md;
DROP DATABASE IF EXISTS pt;
DROP DATABASE IF EXISTS ua;
DROP DATABASE IF EXISTS uk;




--
-- Drop roles
--

-- DROP ROLE postgres;
--
--
-- --
-- -- Roles
-- --
--
-- CREATE ROLE postgres;
-- ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS;






--
-- Databases
--

--
-- Database "template1" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.6
-- Dumped by pg_dump version 14.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

UPDATE pg_catalog.pg_database SET datistemplate = false WHERE datname = 'template1';
DROP DATABASE template1;
--
-- Name: template1; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE template1 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';


ALTER DATABASE template1 OWNER TO postgres;

\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE template1 IS 'default template for new databases';


--
-- Name: template1; Type: DATABASE PROPERTIES; Schema: -; Owner: postgres
--

ALTER DATABASE template1 IS_TEMPLATE = true;


\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: ACL; Schema: -; Owner: postgres
--

REVOKE CONNECT,TEMPORARY ON DATABASE template1 FROM PUBLIC;
GRANT CONNECT ON DATABASE template1 TO PUBLIC;


--
-- PostgreSQL database dump complete
--

--
-- Database "cr" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.6
-- Dumped by pg_dump version 14.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: cr; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE cr WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';


ALTER DATABASE cr OWNER TO postgres;

\connect cr

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    first_name character varying,
    last_name character varying,
    genre character(1),
    date_of_birth date,
    nationality character(2),
    username character varying,
    password character varying
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, first_name, last_name, genre, date_of_birth, nationality, username, password) FROM stdin;
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, false);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

--
-- Database "main_db" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.6
-- Dumped by pg_dump version 14.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: main_db; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE main_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';


ALTER DATABASE main_db OWNER TO postgres;

\connect main_db

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: add_database(character varying, character varying, character varying, character varying, character varying, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.add_database(IN name character varying, IN username character varying, IN password character varying, IN jdbc_url character varying, IN driver_class_name character varying, IN password_salt character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    INSERT INTO databases (name, username, password, jdbc_url, driver_class_name, password_salt)
    VALUES (name, username, password, jdbc_url, driver_class_name, password_salt);
END;
$$;


ALTER PROCEDURE public.add_database(IN name character varying, IN username character varying, IN password character varying, IN jdbc_url character varying, IN driver_class_name character varying, IN password_salt character varying) OWNER TO postgres;

--
-- Name: get_all_databases(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.get_all_databases() RETURNS TABLE(id integer, name character varying, username character varying, password character varying, jdbc_url character varying, driver_class_name character varying, salt character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
        SELECT * FROM databases;
END;
$$;


ALTER FUNCTION public.get_all_databases() OWNER TO postgres;

--
-- Name: get_database(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.get_database(db_name character varying) RETURNS TABLE(id integer, name character varying, username character varying, password character varying, jdbc_url character varying, driver_class_name character varying, password_salt character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
        SELECT db.id, db.name, db.username, db.password, db.jdbc_url, db.driver_class_name, db.password_salt
        FROM databases db
        WHERE db.name ~~* db_name;
END;
$$;


ALTER FUNCTION public.get_database(db_name character varying) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: databases; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.databases (
    id integer NOT NULL,
    name character varying,
    username character varying,
    password character varying,
    password_salt character varying,
    jdbc_url character varying,
    driver_class_name character varying
);


ALTER TABLE public.databases OWNER TO postgres;

--
-- Name: databases_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.databases_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.databases_id_seq OWNER TO postgres;

--
-- Name: databases_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.databases_id_seq OWNED BY public.databases.id;


--
-- Name: databases id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.databases ALTER COLUMN id SET DEFAULT nextval('public.databases_id_seq'::regclass);


--
-- Data for Name: databases; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.databases (id, name, username, password, password_salt, jdbc_url, driver_class_name) FROM stdin;
2	md	postgres	UcII8Llo/HAV/WT38dfYiA==	w+qCaa4tfDsw99UqKsW9m+0pEhl6B1qsx5B/DfuYSEY=	jdbc:postgresql://localhost:5432/md	org.postgresql.Driver
3	uk	postgres	PQQxjU3kzJqVaUXUs3vWQw==	nHTPOZwNDjNv5HmYhLHqv6FZ2ay14Ro3Qp6RX76U6S8=	jdbc:postgresql://localhost:5432/uk	org.postgresql.Driver
4	cr	postgres	2BEk3LjquMVDR4q3MzLZ6g==	b+iNScXqm0h6OhLn7uh+GYEnmrK5Uvb2+tfnOEG1Xt0=	jdbc:postgresql://localhost:5432/cr	org.postgresql.Driver
5	pt	postgres	QswF6rEcYr+ejfre9O+/HA==	MUeD64hMnoW9yB5P6TAZjlPK33qYNTbN6KDA8RXWQRI=	jdbc:postgresql://localhost:5432/pt	org.postgresql.Driver
6	fr	postgres	Bz3kVTwS/U6bJEiygfITjA==	u//WZmzZSN0t/Wru52pqImDU2EgxgikOXhKafkjj7hc=	jdbc:postgresql://localhost:5432/fr	org.postgresql.Driver
7	ua	postgres	hlosSllrzfqkY++u43nbOQ==	WlCqJZ2v2xZ60UGTK0pvMQCi0r8NL007Eeed3qP6Ock=	jdbc:postgresql://localhost:5432/ua	org.postgresql.Driver
\.


--
-- Name: databases_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.databases_id_seq', 7, true);


--
-- Name: databases databases_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.databases
    ADD CONSTRAINT databases_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

--
-- Database "md" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.6
-- Dumped by pg_dump version 14.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: md; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE md WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';


ALTER DATABASE md OWNER TO postgres;

\connect md

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    first_name character varying,
    last_name character varying,
    genre character(1),
    date_of_birth date,
    nationality character(2),
    username character varying,
    password character varying
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, first_name, last_name, genre, date_of_birth, nationality, username, password) FROM stdin;
12	Izzy	Chaffin	M	2022-07-19	MD	ichaffin3	a1gZZJljQ
13	Doretta	Swettenham	F	2022-07-02	MD	dswettenham0	aG2NcqsWm
14	Romain	Preon	M	2022-10-28	MD	rpreon1	g3Q3HQtlyp5f
15	Nanette	Todeo	F	2022-09-04	MD	ntodeo2	XQW62xDWugX
16	Dewain	Arderne	M	2022-10-22	MD	darderne3	t2eWpk
17	Nicolina	Thireau	F	2022-05-18	MD	nthireau4	OySvpBI
18	Leandra	Flemyng	F	2022-04-09	MD	lflemyng5	BWZHhP
19	Lou	Yerson	M	2022-11-14	MD	lyerson6	kfDJGZcD
20	Anita	Avon	F	2022-02-23	MD	aavon7	1rUhoGgs
21	Rosmunda	Maris	F	2022-03-26	MD	rmaris8	R2TdRmDjE4c4
22	Syman	Axon	M	2022-02-24	MD	saxon9	5JMBj50
23	Izzy	Chaffin	M	2022-07-19	MD	ichaffin3	a1gZZJljQ
24	Doretta	Swettenham	F	2022-07-02	MD	dswettenham0	aG2NcqsWm
25	Romain	Preon	M	2022-10-28	MD	rpreon1	g3Q3HQtlyp5f
26	Nanette	Todeo	F	2022-09-04	MD	ntodeo2	XQW62xDWugX
27	Dewain	Arderne	M	2022-10-22	MD	darderne3	t2eWpk
28	Nicolina	Thireau	F	2022-05-18	MD	nthireau4	OySvpBI
29	Leandra	Flemyng	F	2022-04-09	MD	lflemyng5	BWZHhP
30	Lou	Yerson	M	2022-11-14	MD	lyerson6	kfDJGZcD
31	Anita	Avon	F	2022-02-23	MD	aavon7	1rUhoGgs
32	Rosmunda	Maris	F	2022-03-26	MD	rmaris8	R2TdRmDjE4c4
33	Syman	Axon	M	2022-02-24	MD	saxon9	5JMBj50
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 33, true);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.6
-- Dumped by pg_dump version 14.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE postgres;
--
-- Name: postgres; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';


ALTER DATABASE postgres OWNER TO postgres;

\connect postgres

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- PostgreSQL database dump complete
--

--
-- Database "pt" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.6
-- Dumped by pg_dump version 14.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: pt; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE pt WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';


ALTER DATABASE pt OWNER TO postgres;

\connect pt

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    first_name character varying,
    last_name character varying,
    genre character(1),
    date_of_birth date,
    nationality character(2),
    username character varying,
    password character varying
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, first_name, last_name, genre, date_of_birth, nationality, username, password) FROM stdin;
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, false);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

--
-- Database "ua" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.6
-- Dumped by pg_dump version 14.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: ua; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE ua WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';


ALTER DATABASE ua OWNER TO postgres;

\connect ua

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    first_name character varying,
    last_name character varying,
    genre character(1),
    date_of_birth date,
    nationality character(2),
    username character varying,
    password character varying
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, first_name, last_name, genre, date_of_birth, nationality, username, password) FROM stdin;
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, false);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

--
-- Database "uk" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.6
-- Dumped by pg_dump version 14.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: uk; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE uk WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';


ALTER DATABASE uk OWNER TO postgres;

\connect uk

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    first_name character varying,
    last_name character varying,
    genre character(1),
    date_of_birth date,
    nationality character(2),
    username character varying,
    password character varying
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, first_name, last_name, genre, date_of_birth, nationality, username, password) FROM stdin;
10	Andrea	Perello	M	2022-01-18	UK	aperello0	3bwQnlE
11	Allen	Nairne	M	2022-06-06	UK	anairne1	nubAts
12	Gussie	Stirton	F	2022-07-21	UK	gstirton2	0kPGXTQiOwq9
13	Christophorus	Rawdales	M	2022-08-27	UK	crawdales4	tMkoqxq
14	Alejandrina	Light	F	2022-08-09	UK	alight5	4du43nzy8L
15	Mureil	Bowker	F	2022-09-20	UK	mbowker6	EMaRc10w
16	Blair	Wickett	M	2022-06-13	UK	bwickett7	KPDAXJOm6dhG
17	Garreth	Minear	M	2022-09-17	UK	gminear8	YzKCcDz
18	Wilone	Blacktin	F	2022-05-29	UK	wblacktin9	tOZH52b
19	Andrea	Perello	M	2022-01-18	UK	aperello0	3bwQnlE
20	Allen	Nairne	M	2022-06-06	UK	anairne1	nubAts
21	Gussie	Stirton	F	2022-07-21	UK	gstirton2	0kPGXTQiOwq9
22	Christophorus	Rawdales	M	2022-08-27	UK	crawdales4	tMkoqxq
23	Alejandrina	Light	F	2022-08-09	UK	alight5	4du43nzy8L
24	Mureil	Bowker	F	2022-09-20	UK	mbowker6	EMaRc10w
25	Blair	Wickett	M	2022-06-13	UK	bwickett7	KPDAXJOm6dhG
26	Garreth	Minear	M	2022-09-17	UK	gminear8	YzKCcDz
27	Wilone	Blacktin	F	2022-05-29	UK	wblacktin9	tOZH52b
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 27, true);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database cluster dump complete
--

