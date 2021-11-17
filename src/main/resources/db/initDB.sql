DROP TABLE IF EXISTS expenses;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    enabled          BOOLEAN                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    monthly_limit    INTEGER,
    currency         VARCHAR                           NOT NULL
);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role    VARCHAR NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role)
);

CREATE UNIQUE INDEX users_unique_email_idx on users(email);

CREATE TABLE categories
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id       INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name          VARCHAR NOT NULL,
    "limit"       INTEGER CHECK ( "limit" > 0 ),
    period_number INTEGER CHECK ( period_number > 0 ),
    period_unit   VARCHAR
);

CREATE UNIQUE INDEX categories_unique_user_name_idx on categories(user_id, name);

CREATE TABLE expenses
(
    id          INTEGER   PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id     INTEGER   NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    category_id INTEGER            REFERENCES categories(id) ON DELETE SET NULL,
    date_time   TIMESTAMP NOT NULL,
    amount      INTEGER   NOT NULL CHECK ( expenses.amount >= 0 ),
    description VARCHAR   NOT NULL
);

CREATE UNIQUE INDEX expenses_unique_user_datetime_idx on expenses(user_id, date_time);
