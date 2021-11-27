INSERT INTO users (name, email, password, monthly_limit, currency)
VALUES ('User',  'user@yandex.ru',  '{noop}password', 30000.0, 'RUB'),
       ('Admin', 'admin@gmail.com', '{noop}admin', 30000.0, 'USD');

INSERT INTO user_roles (role, user_id)
VALUES ('USER',  1),
       ('USER', 2),
       ('ADMIN', 2);

INSERT INTO categories (user_id, name, "limit", period_number, period_unit)
VALUES (1, 'Food',      12000.0, 1,   'MONTHS'),
       (1, 'Household', 10000.0,  1,  'MONTHS'),
       (2, 'Food',      10000.0,  1,  'MONTHS'),
       (2, 'Household', 8000.0,   1,  'MONTHS');

INSERT INTO expenses (user_id, category_id, date_time, amount, description)
VALUES (1, 1, '2021-01-30 10:00:00', 5000.0,  'Лента'),
       (1, 1, '2021-02-02 11:00:00', 2000.0,  'Пятеречка'),
       (1, 1, '2021-02-11 12:00:00', 5000.0,  'Лента'),
       (1, 2, '2021-02-26 13:00:00',   9000.0, 'Максидом'),
       (1, 2, '2021-02-26 16:00:00',   4000.0, 'Домовой'),
       (1, null,   '2021-02-27 14:00:00',  8000.0, 'Потерял'),

       (2, 3, '2021-01-30 10:00:00', 13000.0, 'Лента'),
       (2, 4, '2021-02-15 12:00:00', 11000.0, 'Максидом'),
       (2, null,   '2021-02-25 14:00:00',  7000.0, 'Потерял');