DROP TABLE IF EXISTS "user";
CREATE TABLE IF NOT EXISTS "user"
(
    id           bigserial primary key,
    first_name   varchar(40) NOT NULL,
    second_name  varchar(40) NOT NULL,
    age          int         not null,
    last_updated timestamp   NOT NULL DEFAULT now()
);


DROP TABLE IF EXISTS product_category;
CREATE TABLE IF NOT EXISTS product_category
(
    id           bigserial primary key,
    name         varchar(40) NOT NULL,
    last_updated timestamp   NOT NULL default now()
);

DROP TABLE IF EXISTS product;
CREATE TABLE IF NOT EXISTS product
(
    id           bigserial primary key,
    name         varchar(30) NOT NULL,
    price        bigint      NOT NULL,
    category     integer     NOT NULL REFERENCES product_category (id),
    last_updated timestamp   NOT NULL default now()
);

DROP TABLE IF EXISTS user_product;
CREATE TABLE IF NOT EXISTS user_product
(
    user_id      bigint REFERENCES "user" (id) ON UPDATE CASCADE ON DELETE CASCADE,
    product_id   bigint REFERENCES product (id) ON UPDATE CASCADE ON DELETE CASCADE,
    amount       integer   NOT NULL DEFAULT 1,
    last_updated timestamp NOT NULL default now(),
    CONSTRAINT user_product_pkey PRIMARY KEY (user_id, product_id)
);

CREATE EXTENSION moddatetime;

CREATE TRIGGER product_moddatetime
    BEFORE UPDATE
    ON product
    FOR EACH ROW
EXECUTE PROCEDURE moddatetime(last_updated);

CREATE TRIGGER user_moddatetime
    BEFORE UPDATE
    ON "user"
    FOR EACH ROW
EXECUTE PROCEDURE moddatetime(last_updated);

CREATE TRIGGER product_category_moddatetime
    BEFORE UPDATE
    ON product_category
    FOR EACH ROW
EXECUTE PROCEDURE moddatetime(last_updated);

CREATE TRIGGER user_product_moddatetime
    BEFORE UPDATE
    ON user_product
    FOR EACH ROW
EXECUTE PROCEDURE moddatetime(last_updated);
