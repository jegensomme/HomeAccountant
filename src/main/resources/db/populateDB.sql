DELETE FROM expenses;
DELETE FROM categories;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, monthly_limit)
VALUES ('User',  'user@yandex.ru',  'password', 30000),
       ('Admin', 'admin@gmail.com', 'admin',    30000);

INSERT INTO user_roles (role, user_id)
VALUES ('USER',  100000),
       ('ADMIN', 100001);

INSERT INTO categories (user_id, name, "limit", period_number, period_unit)
VALUES (100000, 'Food',      120000, 1, 'MONTHS'),
       (100000, 'Household', 10000,  1, 'MONTHS'),
       (100001, 'Food',      10000,  1, 'MONTHS'),
       (100001, 'Household', 8000,   1, 'MONTHS');

INSERT INTO expenses (user_id, category_id, date_time, amount, description)
VALUES (100000, 100002, '2021-01-30 10:00:00', 5000,  'Лента'),
       (100000, 100002, '2021-02-02 11:00:00', 2000,  'Пятеречка'),
       (100000, 100002, '2021-02-11 12:00:00', 5000,  'Лента'),
       (100000, 100003, '2021-02-26 13:00:00', 10000, 'Максидом'),
       (100000, null,   '2021-02-27 14:00:00', 8000,  null),

       (100001, 100004, '2021-01-30 10:00:00', 13000, 'Лента'),
       (100001, 100005, '2021-02-15 12:00:00', 11000, 'Максидом'),
       (100001, null,   '2021-02-25 14:00:00', 7000,  null);