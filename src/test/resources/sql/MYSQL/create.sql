-- Create Database and User

-- CREATE USER 'j7orm'@'localhost' IDENTIFIED BY 'j7orm';
-- CREATE DATABASE IF NOT EXISTS j7orm;
-- GRANT ALL PRIVILEGES ON j7orm . * TO 'test'@'localhost';
-- FLUSH PRIVILEGES;

-- Create Tables

CREATE TABLE test_brand
(
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  version BIGINT DEFAULT NULL,
  name varchar(255) NOT NULL
);


CREATE TABLE test_category
(
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  version BIGINT DEFAULT NULL,
  name varchar(255) NOT NULL,
  description varchar(1000)
);


CREATE TABLE test_product_category
(
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  version BIGINT DEFAULT NULL,
  product_id BIGINT NOT NULL,
  category_id BIGINT NOT NULL
);


CREATE TABLE test_product
(
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  version BIGINT DEFAULT NULL,
  brand_id BIGINT NOT NULL,
  blocked BOOLEAN NOT NULL,
  name varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  create_date DATE NOT NULL,
  modify_date DATE
);



