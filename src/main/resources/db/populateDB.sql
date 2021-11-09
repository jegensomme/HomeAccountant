DELETE FROM expenses;
DELETE FROM categories;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, enabled, monthly_limit, default_currency)
VALUES ('User',  'user@yandex.ru',  'password', true, 30000, 'RUB'),
       ('Admin', 'admin@gmail.com', 'admin', true, 30000, 'USD');

INSERT INTO user_roles (role, user_id)
VALUES ('USER',  100000),
       ('USER', 100001),
       ('ADMIN', 100001);

INSERT INTO categories (user_id, name, "limit", period_number, period_unit, currency)
VALUES (100000, 'Food',      120000, 1, 'MONTHS', 'RUB'),
       (100000, 'Household', 10000,  1,  'MONTHS', 'RUB'),
       (100001, 'Food',      10000,  1,  'MONTHS', 'USD'),
       (100001, 'Household', 8000,   1,  'MONTHS', 'USD');

INSERT INTO expenses (user_id, category_id, date_time, amount, currency, description)
VALUES (100000, 100002, '2021-01-30 10:00:00', 5000,  'RUB', 'Лента'),
       (100000, 100002, '2021-02-02 11:00:00', 2000,  'RUB', 'Пятеречка'),
       (100000, 100002, '2021-02-11 12:00:00', 5000,  'RUB', 'Лента'),
       (100000, 100003, '2021-02-26 13:00:00', 10000, 'RUB', 'Максидом'),
       (100000, null,   '2021-02-27 14:00:00', 8000,   'RUB',  null),

       (100001, 100004, '2021-01-30 10:00:00', 13000, 'USD', 'Лента'),
       (100001, 100005, '2021-02-15 12:00:00', 11000, 'USD', 'Максидом'),
       (100001, null,   '2021-02-25 14:00:00',  7000,  'USD',  null);