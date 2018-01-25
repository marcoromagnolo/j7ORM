-- Stored Procedure

CREATE OR REPLACE TRIGGER brand_on_insert
BEFORE INSERT ON test_brand
FOR EACH ROW
WHEN (new.id is null)
  BEGIN
    SELECT brand_id_seq.nextval
    INTO :new.id
    FROM dual;
  END;
/
CREATE OR REPLACE TRIGGER category_on_insert
BEFORE INSERT ON test_category
FOR EACH ROW
WHEN (new.id is null)
  BEGIN
    SELECT category_id_seq.nextval
    INTO :new.id
    FROM dual;
  END;
/
CREATE OR REPLACE TRIGGER category_prod_on_insert
BEFORE INSERT ON test_product_category
FOR EACH ROW
WHEN (new.id is null)
  BEGIN
    SELECT product_category_id_seq.nextval
    INTO :new.id
    FROM dual;
  END;
/
CREATE OR REPLACE TRIGGER product_on_insert
BEFORE INSERT ON test_product
FOR EACH ROW
WHEN (new.id is null)
  BEGIN
    SELECT product_id_seq.nextval
    INTO :new.id
    FROM dual;
  END;
