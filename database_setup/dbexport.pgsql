--
-- PostgreSQL database dump
--

-- Dumped from database version 10.3
-- Dumped by pg_dump version 10.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
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


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: line_item; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.line_item (
    created_at timestamp without time zone NOT NULL,
    deleted_at timestamp without time zone,
    updated_at timestamp without time zone,
    quantity bigint DEFAULT 1 NOT NULL,
    order_id bigint NOT NULL,
    product_id bigint NOT NULL
);


ALTER TABLE public.line_item OWNER TO admin;

--
-- Name: order_table; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.order_table (
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    deleted_at timestamp without time zone,
    updated_at timestamp without time zone,
    name character varying(255) DEFAULT ''::character varying NOT NULL,
    shop_id bigint
);


ALTER TABLE public.order_table OWNER TO admin;

--
-- Name: order_table_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.order_table_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_table_id_seq OWNER TO admin;

--
-- Name: order_table_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.order_table_id_seq OWNED BY public.order_table.id;


--
-- Name: product; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.product (
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    deleted_at timestamp without time zone,
    updated_at timestamp without time zone,
    description character varying(255) DEFAULT NULL::character varying,
    name character varying(255) DEFAULT ''::character varying NOT NULL,
    price bigint DEFAULT 0 NOT NULL,
    shop_id bigint
);


ALTER TABLE public.product OWNER TO admin;

--
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_id_seq OWNER TO admin;

--
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.product_id_seq OWNED BY public.product.id;


--
-- Name: shop; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.shop (
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    deleted_at timestamp without time zone,
    updated_at timestamp without time zone,
    address character varying(255) DEFAULT ''::character varying,
    name character varying(255) DEFAULT ''::character varying NOT NULL
);


ALTER TABLE public.shop OWNER TO admin;

--
-- Name: shop_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.shop_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.shop_id_seq OWNER TO admin;

--
-- Name: shop_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.shop_id_seq OWNED BY public.shop.id;


--
-- Name: order_table id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.order_table ALTER COLUMN id SET DEFAULT nextval('public.order_table_id_seq'::regclass);


--
-- Name: product id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.product ALTER COLUMN id SET DEFAULT nextval('public.product_id_seq'::regclass);


--
-- Name: shop id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.shop ALTER COLUMN id SET DEFAULT nextval('public.shop_id_seq'::regclass);


--
-- Data for Name: line_item; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.line_item (created_at, deleted_at, updated_at, quantity, order_id, product_id) FROM stdin;
2018-09-23 12:16:08.274	\N	\N	3	3	1
2018-09-23 12:16:08.276	\N	\N	4	3	2
2018-09-23 12:16:21.117	\N	2018-09-23 12:19:32.722	30	4	1
2018-09-23 12:59:02.169	\N	\N	30	1	1
\.


--
-- Data for Name: order_table; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.order_table (id, created_at, deleted_at, updated_at, name, shop_id) FROM stdin;
1	2018-09-23 12:15:41.959	\N	\N	another-another-empty-order	\N
2	2018-09-23 12:15:52.199	\N	\N	empty-order	\N
3	2018-09-23 12:16:08.266	\N	\N	order-with-two-item	\N
4	2018-09-23 12:16:21.113	\N	\N	order-with-one-item	\N
\.


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.product (id, created_at, deleted_at, updated_at, description, name, price, shop_id) FROM stdin;
1	2018-09-22 21:00:34.241	\N	\N	expensive mobile phone	iphone	1000	\N
2	2018-09-22 21:01:20.502	\N	\N	bigger iPhone	iPad	500	\N
3	2018-09-22 21:01:45.169	\N	\N	ancient good stuff	iPhone 3GS	300	\N
5	2018-09-22 21:04:13.48	\N	\N	cool stuff	Tesla	100000	1
4	2018-09-22 21:03:00.364	2018-09-22 21:06:05.711	2018-09-22 21:06:05.716	delicious fruit	apple	2	\N
\.


--
-- Data for Name: shop; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.shop (id, created_at, deleted_at, updated_at, address, name) FROM stdin;
3	2018-09-22 21:09:59.903	\N	\N	Vancouver	EGame-shop
4	2018-09-22 21:10:14.246	\N	\N	fake address 4	mobile-shop
2	2018-09-22 21:09:32.495	2018-09-23 12:10:17.41	2018-09-23 12:10:17.424	fake-address2	toy-shop
1	2018-09-22 21:03:30.245	\N	2018-09-23 12:52:29.124	this is new address	car-shop
\.


--
-- Name: order_table_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.order_table_id_seq', 4, true);


--
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.product_id_seq', 5, true);


--
-- Name: shop_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.shop_id_seq', 4, true);


--
-- Name: line_item line_item_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.line_item
    ADD CONSTRAINT line_item_pkey PRIMARY KEY (order_id, product_id);


--
-- Name: order_table order_table_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.order_table
    ADD CONSTRAINT order_table_pkey PRIMARY KEY (id);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: shop shop_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.shop
    ADD CONSTRAINT shop_pkey PRIMARY KEY (id);


--
-- Name: line_item fk237t8tbj9haibqe7wafj4t54x; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.line_item
    ADD CONSTRAINT fk237t8tbj9haibqe7wafj4t54x FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: product fk94hgg8hlqfqfnt3dag950vm7n; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk94hgg8hlqfqfnt3dag950vm7n FOREIGN KEY (shop_id) REFERENCES public.shop(id);


--
-- Name: order_table fkd080omh3pk5p963thc52hcwiy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.order_table
    ADD CONSTRAINT fkd080omh3pk5p963thc52hcwiy FOREIGN KEY (shop_id) REFERENCES public.shop(id);


--
-- Name: line_item fkt53p7xtndp9wma879d8u5iuxt; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.line_item
    ADD CONSTRAINT fkt53p7xtndp9wma879d8u5iuxt FOREIGN KEY (order_id) REFERENCES public.order_table(id);


--
-- PostgreSQL database dump complete
--

