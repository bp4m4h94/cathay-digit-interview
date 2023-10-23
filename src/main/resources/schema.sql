-- Create a Currency table with an auto-generated UUID as the primary key
CREATE TABLE Currency
(
    ID          int not null AUTO_INCREMENT,
    CODE        VARCHAR(3),
    SYMBOL      VARCHAR(10),
    RATE        DECIMAL(10, 4),
    DESCRIPTION VARCHAR(255),
    RATE_FLOAT  DECIMAL(10, 4)
);
