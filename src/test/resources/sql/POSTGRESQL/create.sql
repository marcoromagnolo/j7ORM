-- Create Database

-- CREATE USER j7orm WITH PASSWORD 'j7orm' CREATEDB;
-- CREATE DATABASE j7orm WITH OWNER = j7orm TEMPLATE = template0 ENCODING = 'UTF8' CONNECTION LIMIT = -1;

--Create Sequences

CREATE SEQUENCE brand_id_seq INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE product_id_seq INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE category_id_seq INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE product_category_id_seq INCREMENT BY 1 START WITH 1;

--Create Tables

CREATE TABLE test_brand
(
  id bigint NOT NULL DEFAULT nextval('brand_id_seq') PRIMARY KEY,
  version bigint DEFAULT NULL,
  name character varying(255) NOT NULL
);


CREATE TABLE test_category
(
  id bigint NOT NULL DEFAULT nextval('category_id_seq') PRIMARY KEY,
  version bigint DEFAULT NULL,
  name character varying(255) NOT NULL,
  description character varying(1000) NOT NULL
);


CREATE TABLE test_product_category
(
  id bigint NOT NULL DEFAULT nextval('product_category_id_seq') PRIMARY KEY,
  version bigint DEFAULT NULL,
  product_id bigint NOT NULL,
  category_id bigint NOT NULL
);


CREATE TABLE test_product
(
  id bigint NOT NULL DEFAULT nextval('product_id_seq') PRIMARY KEY,
  version bigint DEFAULT NULL,
  brand_id integer NOT NULL,
  blocked boolean NOT NULL DEFAULT false,
  name character varying(255) NOT NULL,
  description character varying(255) NOT NULL,
  create_date timestamp without time zone NOT NULL,
  modify_date timestamp without time zone
);
