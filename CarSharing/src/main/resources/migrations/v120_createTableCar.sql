CREATE TABLE IF NOT EXISTS CAR
(
    ID         INT PRIMARY KEY AUTO_INCREMENT,
    NAME       VARCHAR(40) UNIQUE NOT NULL,
    COMPANY_ID INT                NOT NULL,
    FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY (ID)
);