-- Create Database and User

-- CREATE DATABASE j7orm;

-- Create Tables

CREATE TABLE test_brand
(
  id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1),
  version INTEGER DEFAULT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE test_category
(
  id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1),
  version INTEGER DEFAULT NULL,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(1000),
  PRIMARY KEY (id)
);


CREATE TABLE test_product_category
(
  id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1),
  version INTEGER DEFAULT NULL,
  product_id INTEGER NOT NULL,
  category_id INTEGER NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE test_product
(
  id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1),
  version INTEGER DEFAULT NULL,
  brand_id INTEGER NOT NULL,
  blocked SMALLINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  create_date DATE NOT NULL,
  modify_date DATE,
  PRIMARY KEY (id)
);
