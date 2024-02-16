CREATE TABLE Currency
(
    ID          int not null AUTO_INCREMENT,
    CODE        VARCHAR(3),
    CODE_CH     VARCHAR(50),
    RATE_FLOAT  DECIMAL(10, 4),
    UPDATED_DATE VARCHAR(50)
);
