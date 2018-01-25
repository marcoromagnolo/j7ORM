--Drop Sequences

DROP SEQUENCE brand_id_seq;
DROP SEQUENCE product_id_seq;
DROP SEQUENCE category_id_seq;
DROP SEQUENCE product_category_id_seq;

--Drop Tables

DROP TABLE test_brand;
DROP TABLE test_category;
DROP TABLE test_product_category;
DROP TABLE test_product;

-- Drop Trigger

DROP TRIGGER brand_on_insert;
DROP TRIGGER category_on_insert;
DROP TRIGGER category_prod_on_insert;
DROP TRIGGER product_on_insert;

-- Drop Database

-- DROP DATABASE advantorm;