INSERT INTO Currency (code, code_ch, symbol, description, rate_float, updated_date)
VALUES ('USD', '美金',  '&#36;', 'United States Dollar', 30686.9923, FORMATDATETIME(current_timestamp, 'yyyy/MM/dd HH:mm:ss')),
       ('EUR', '歐元', '&pound;', 'Euro', 35123.4567, FORMATDATETIME(current_timestamp, 'yyyy/MM/dd HH:mm:ss')),
       ('GBP', '英鎊', '&euro;', 'British Pound', 41987.6543, FORMATDATETIME(current_timestamp, 'yyyy/MM/dd HH:mm:ss'));