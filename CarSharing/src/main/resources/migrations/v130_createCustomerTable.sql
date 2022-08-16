CREATE TABLE IF NOT EXISTS CUSTOMER (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255) UNIQUE NOT NULL ,
    RENTED_CAR_ID INT,
    FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)
);