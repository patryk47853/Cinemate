ALTER TABLE category
    ADD CONSTRAINT unique_category_name UNIQUE (category_name);