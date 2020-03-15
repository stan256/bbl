CREATE TABLE IF NOT EXISTS users
(
    id           bigserial primary key,
    first_name   varchar(40) NOT NULL,
    last_name    varchar(40) NOT NULL,
    email        varchar(50) NOT NULL,
    password     varchar(40) NOT NULL,
    age          int         not null,
    last_updated timestamp   NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS roles
(
    id   bigserial primary key,
    role varchar(12) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id    bigint REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    product_id bigint REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT user_product_pkey PRIMARY KEY (user_id, product_id)
);

CREATE EXTENSION moddatetime;

CREATE TRIGGER user_moddatetime
    BEFORE UPDATE
    ON users
    FOR EACH ROW
EXECUTE PROCEDURE moddatetime(last_updated);
