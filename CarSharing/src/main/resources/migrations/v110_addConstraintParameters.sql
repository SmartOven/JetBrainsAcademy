ALTER TABLE COMPANY
    ALTER COLUMN ID INT AUTO_INCREMENT;

ALTER TABLE COMPANY
    ADD CONSTRAINT IF NOT EXISTS pr_key_id PRIMARY KEY (ID);

ALTER TABLE COMPANY
    ALTER COLUMN NAME VARCHAR(40) NOT NULL;

ALTER TABLE COMPANY
    ADD UNIQUE (NAME);