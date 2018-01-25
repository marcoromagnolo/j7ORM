-- Create Database and User

-- CREATE USER advantorm IDENTIFIED BY advantorm;
-- GRANT ALL PRIVILEGES TO advantorm;

-- Create Sequences

CREATE SEQUENCE brand_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE product_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE category_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE product_category_id_seq START WITH 1 INCREMENT BY 1;

-- Create Tables

CREATE TABLE test_brand
(
  id NUMBER(19) NOT NULL PRIMARY KEY,
  version NUMBER(19) DEFAULT NULL,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE test_category
(
  id NUMBER(19) NOT NULL PRIMARY KEY,
  version NUMBER(19) DEFAULT NULL,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(1000)
);


CREATE TABLE test_product_category
(
  id NUMBER(19) NOT NULL PRIMARY KEY,
  version NUMBER(19) DEFAULT NULL,
  product_id NUMBER(19) NOT NULL,
  category_id NUMBER(19) NOT NULL
);


CREATE TABLE test_product
(
  id NUMBER(19) NOT NULL PRIMARY KEY,
  version NUMBER(19) DEFAULT NULL,
  brand_id NUMBER(19) NOT NULL,
  blocked NUMBER(1) NOT NULL,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  create_date DATE NOT NULL,
  modify_date DATE
);
