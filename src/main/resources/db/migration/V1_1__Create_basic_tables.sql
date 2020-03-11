CREATE TABLE IF NOT EXISTS users
(
    id           bigserial primary key,
    first_name   varchar(40) NOT NULL,
    last_name    varchar(40) NOT NULL,
    age          int         not null,
    last_updated timestamp   NOT NULL DEFAULT now()
);

CREATE EXTENSION moddatetime;

CREATE TRIGGER user_moddatetime
    BEFORE UPDATE
    ON users
    FOR EACH ROW
EXECUTE PROCEDURE moddatetime(last_updated);
