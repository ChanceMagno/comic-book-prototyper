--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.2
-- Dumped by pg_dump version 9.6.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: books; Type: TABLE; Schema: public; Owner: nathanielmeyer
--

CREATE TABLE books (
    id integer NOT NULL,
    title character varying,
    user_id integer
);


ALTER TABLE books OWNER TO nathanielmeyer;

--
-- Name: books_id_seq; Type: SEQUENCE; Schema: public; Owner: nathanielmeyer
--

CREATE SEQUENCE books_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE books_id_seq OWNER TO nathanielmeyer;

--
-- Name: books_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nathanielmeyer
--

ALTER SEQUENCE books_id_seq OWNED BY books.id;


--
-- Name: pages; Type: TABLE; Schema: public; Owner: nathanielmeyer
--

CREATE TABLE pages (
    id integer NOT NULL,
    book_id integer,
    layout character varying
);


ALTER TABLE pages OWNER TO nathanielmeyer;

--
-- Name: pages_id_seq; Type: SEQUENCE; Schema: public; Owner: nathanielmeyer
--

CREATE SEQUENCE pages_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pages_id_seq OWNER TO nathanielmeyer;

--
-- Name: pages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nathanielmeyer
--

ALTER SEQUENCE pages_id_seq OWNED BY pages.id;


--
-- Name: panels; Type: TABLE; Schema: public; Owner: nathanielmeyer
--

CREATE TABLE panels (
    id integer NOT NULL,
    page_id integer,
    sequence integer,
    image_path character varying
);


ALTER TABLE panels OWNER TO nathanielmeyer;

--
-- Name: panels_id_seq; Type: SEQUENCE; Schema: public; Owner: nathanielmeyer
--

CREATE SEQUENCE panels_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE panels_id_seq OWNER TO nathanielmeyer;

--
-- Name: panels_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nathanielmeyer
--

ALTER SEQUENCE panels_id_seq OWNED BY panels.id;


--
-- Name: texts; Type: TABLE; Schema: public; Owner: nathanielmeyer
--

CREATE TABLE texts (
    id integer NOT NULL,
    panel_id integer,
    sequence integer,
    box_style character varying,
    font character varying,
    orientation character varying,
    speaker character varying
);


ALTER TABLE texts OWNER TO nathanielmeyer;

--
-- Name: texts_id_seq; Type: SEQUENCE; Schema: public; Owner: nathanielmeyer
--

CREATE SEQUENCE texts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE texts_id_seq OWNER TO nathanielmeyer;

--
-- Name: texts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nathanielmeyer
--

ALTER SEQUENCE texts_id_seq OWNED BY texts.id;


--
-- Name: books id; Type: DEFAULT; Schema: public; Owner: nathanielmeyer
--

ALTER TABLE ONLY books ALTER COLUMN id SET DEFAULT nextval('books_id_seq'::regclass);


--
-- Name: pages id; Type: DEFAULT; Schema: public; Owner: nathanielmeyer
--

ALTER TABLE ONLY pages ALTER COLUMN id SET DEFAULT nextval('pages_id_seq'::regclass);


--
-- Name: panels id; Type: DEFAULT; Schema: public; Owner: nathanielmeyer
--

ALTER TABLE ONLY panels ALTER COLUMN id SET DEFAULT nextval('panels_id_seq'::regclass);


--
-- Name: texts id; Type: DEFAULT; Schema: public; Owner: nathanielmeyer
--

ALTER TABLE ONLY texts ALTER COLUMN id SET DEFAULT nextval('texts_id_seq'::regclass);


--
-- Data for Name: books; Type: TABLE DATA; Schema: public; Owner: nathanielmeyer
--

COPY books (id, title, user_id) FROM stdin;
\.


--
-- Name: books_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nathanielmeyer
--

SELECT pg_catalog.setval('books_id_seq', 1, false);


--
-- Data for Name: pages; Type: TABLE DATA; Schema: public; Owner: nathanielmeyer
--

COPY pages (id, book_id, layout) FROM stdin;
\.


--
-- Name: pages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nathanielmeyer
--

SELECT pg_catalog.setval('pages_id_seq', 1, false);


--
-- Data for Name: panels; Type: TABLE DATA; Schema: public; Owner: nathanielmeyer
--

COPY panels (id, page_id, sequence, image_path) FROM stdin;
\.


--
-- Name: panels_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nathanielmeyer
--

SELECT pg_catalog.setval('panels_id_seq', 1, false);


--
-- Data for Name: texts; Type: TABLE DATA; Schema: public; Owner: nathanielmeyer
--

COPY texts (id, panel_id, sequence, box_style, font, orientation, speaker) FROM stdin;
\.


--
-- Name: texts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nathanielmeyer
--

SELECT pg_catalog.setval('texts_id_seq', 1, false);


--
-- Name: books books_pkey; Type: CONSTRAINT; Schema: public; Owner: nathanielmeyer
--

ALTER TABLE ONLY books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);


--
-- Name: pages pages_pkey; Type: CONSTRAINT; Schema: public; Owner: nathanielmeyer
--

ALTER TABLE ONLY pages
    ADD CONSTRAINT pages_pkey PRIMARY KEY (id);


--
-- Name: panels panels_pkey; Type: CONSTRAINT; Schema: public; Owner: nathanielmeyer
--

ALTER TABLE ONLY panels
    ADD CONSTRAINT panels_pkey PRIMARY KEY (id);


--
-- Name: texts texts_pkey; Type: CONSTRAINT; Schema: public; Owner: nathanielmeyer
--

ALTER TABLE ONLY texts
    ADD CONSTRAINT texts_pkey PRIMARY KEY (id);


--
-- Name: pages pages_book_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nathanielmeyer
--

ALTER TABLE ONLY pages
    ADD CONSTRAINT pages_book_id_fkey FOREIGN KEY (book_id) REFERENCES books(id);


--
-- Name: panels panels_page_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nathanielmeyer
--

ALTER TABLE ONLY panels
    ADD CONSTRAINT panels_page_id_fkey FOREIGN KEY (page_id) REFERENCES pages(id);


--
-- PostgreSQL database dump complete
--
