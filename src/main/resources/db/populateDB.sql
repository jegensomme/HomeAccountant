DELETE FROM expenses;
DELETE FROM categories;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, monthly_limit, currency)
VALUES ('User',  'user@yandex.ru',  '{noop}password', 30000.00, 'RUB'),
       ('Admin', 'admin@gmail.com', '{noop}admin', 30000.00, 'USD');

INSERT INTO user_roles (role, user_id)
VALUES ('USER',  100000),
       ('USER', 100001),
       ('ADMIN', 100001);

INSERT INTO categories (user_id, name, "limit", period_number, period_unit)
VALUES (100000, 'Food',      12000.00, 1,   'MONTHS'),
       (100000, 'Household', 10000.00,  1,  'MONTHS'),
       (100001, 'Food',      10000.00,  1,  'MONTHS'),
       (100001, 'Household', 8000.00,   1,  'MONTHS');

INSERT INTO expenses (user_id, category_id, date_time, amount, description)
VALUES (100000, 100002, '2021-01-30 10:00:00', 5000.00,  'Лента'),
       (100000, 100002, '2021-02-02 11:00:00', 2000.00,  'Пятеречка'),
       (100000, 100002, '2021-02-11 12:00:00', 5000.00,  'Лента'),
       (100000, 100003, '2021-02-26 13:00:00',   9000.00, 'Максидом'),
       (100000, 100003, '2021-02-26 16:00:00',   4000.00, 'Домовой'),
       (100000, null,   '2021-02-27 14:00:00',  8000.00, 'Потерял'),

       (100001, 100004, '2021-01-30 10:00:00', 13000.00, 'Лента'),
       (100001, 100005, '2021-02-15 12:00:00', 11000.00, 'Максидом'),
       (100001, null,   '2021-02-25 14:00:00',  7000.00, 'Потерял');