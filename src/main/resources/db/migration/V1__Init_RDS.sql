-- PostgreSQL database dump
--

-- Dumped from database version 14.17 (Debian 14.17-1.pgdg120+1)
-- Dumped by pg_dump version 14.17 (Debian 14.17-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: admins; Type: TABLE; Schema: public;
--

CREATE TABLE public.admins (
    id uuid NOT NULL,
    full_name character varying(150) NOT NULL,
    permissions text,
    hire_date date
);

--
-- Name: clients; Type: TABLE; Schema: public;
--

CREATE TABLE public.clients (
    id uuid NOT NULL,
    full_name character varying(150) NOT NULL,
    phone character varying(30),
    address text,
    loyalty_points integer DEFAULT 0
);

--
-- Name: drivers; Type: TABLE; Schema: public;
--

CREATE TABLE public.drivers (
    id uuid NOT NULL,
    full_name character varying(150) NOT NULL,
    license_number character varying(50),
    vehicle_plate character varying(20),
    available boolean DEFAULT true,
    hire_date date
);

--
-- Name: inventory_managers; Type: TABLE; Schema: public;
--

CREATE TABLE public.inventory_managers (
    id uuid NOT NULL,
    full_name character varying(150) NOT NULL,
    assigned_warehouse_code character varying(30),
    pending_audits integer DEFAULT 0,
    hire_date date
);

--
-- Name: roles; Type: TABLE; Schema: public;
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public;
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public;
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;

--
-- Name: sales_managers; Type: TABLE; Schema: public;
--

CREATE TABLE public.sales_managers (
    id uuid NOT NULL,
    full_name character varying(150) NOT NULL,
    region character varying(50),
    goal_achieved boolean DEFAULT false,
    hire_date date
);

--
-- Name: users; Type: TABLE; Schema: public;
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    email character varying(100) NOT NULL,
    username character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    enabled boolean DEFAULT true,
    role_id integer NOT NULL
);

--
-- Name: roles id; Type: DEFAULT; Schema: public;
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);

--
-- Name: admins admins_pkey; Type: CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.admins
    ADD CONSTRAINT admins_pkey PRIMARY KEY (id);

--
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);

--
-- Name: drivers drivers_pkey; Type: CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT drivers_pkey PRIMARY KEY (id);

--
-- Name: inventory_managers inventory_managers_pkey; Type: CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.inventory_managers
    ADD CONSTRAINT inventory_managers_pkey PRIMARY KEY (id);

--
-- Name: roles roles_name_key; Type: CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_name_key UNIQUE (name);

--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);

--
-- Name: sales_managers sales_managers_pkey; Type: CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.sales_managers
    ADD CONSTRAINT sales_managers_pkey PRIMARY KEY (id);

--
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);

--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);

--
-- Name: admins admins_id_fkey; Type: FK CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.admins
    ADD CONSTRAINT admins_id_fkey FOREIGN KEY (id) REFERENCES public.users(id) ON DELETE CASCADE;

--
-- Name: clients clients_id_fkey; Type: FK CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_id_fkey FOREIGN KEY (id) REFERENCES public.users(id) ON DELETE CASCADE;

--
-- Name: drivers drivers_id_fkey; Type: FK CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT drivers_id_fkey FOREIGN KEY (id) REFERENCES public.users(id) ON DELETE CASCADE;

--
-- Name: inventory_managers inventory_managers_id_fkey; Type: FK CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.inventory_managers
    ADD CONSTRAINT inventory_managers_id_fkey FOREIGN KEY (id) REFERENCES public.users(id) ON DELETE CASCADE;

--
-- Name: sales_managers sales_managers_id_fkey; Type: FK CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.sales_managers
    ADD CONSTRAINT sales_managers_id_fkey FOREIGN KEY (id) REFERENCES public.users(id) ON DELETE CASCADE;

--
-- Name: users users_role_id_fkey; Type: FK CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id);

--
-- PostgreSQL database dump complete
--