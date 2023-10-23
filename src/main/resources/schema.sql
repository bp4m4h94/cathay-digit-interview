CREATE TABLE Currency
(
    ID          int not null AUTO_INCREMENT,
    CODE        VARCHAR(3),
    CODE_CH     VARCHAR(50),
    SYMBOL      VARCHAR(10),
    DESCRIPTION VARCHAR(255),
    RATE_FLOAT  DECIMAL(10, 4)
);
