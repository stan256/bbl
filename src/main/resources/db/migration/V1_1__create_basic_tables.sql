CREATE TABLE IF NOT EXISTS users
(
    id             bigserial primary key,
    first_name     varchar(40),
    last_name      varchar(40),
    email          varchar(50) NOT NULL,
    password       varchar(60) NOT NULL,
    age            int         not null,
    email_verified BOOLEAN     not null DEFAULT false,
    active         BOOLEAN     not null DEFAULT true,
    created_at     TIMESTAMP   NOT NULL DEFAULT now(),
    updated_at     TIMESTAMP   NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS user_device
(
    id                 bigserial primary key,
    user_id            varchar(40) NOT NULL,
    device_type        varchar(15) NOT NULL,
    notification_token varchar(40) NOT NULL,
    device_id          varchar(40) NOT NULL,
    refresh_token_id   bigint      not null,
    refresh_active     boolean     not null,
    active             BOOLEAN     not null DEFAULT true,
    created_at         TIMESTAMP   NOT NULL DEFAULT now(),
    updated_at         TIMESTAMP   NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS roles
(
    id         bigserial primary key,
    role       varchar(12) NOT NULL ,
    created_at TIMESTAMP   NOT NULL DEFAULT now(),
    updated_at TIMESTAMP   NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS email_verification_token
(
    id              bigserial primary key,
    token           VARCHAR(40) NOT NULL,
    user_id         BIGINT      NOT NULL,
    token_status    varchar(10) NOT NULL,
    expiration_date TIMESTAMP   not null,
    created_at      TIMESTAMP   NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP   NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS refresh_token
(
    id              bigserial primary key,
    token           VARCHAR(40) NOT NULL,
    refresh_count   BIGINT      NOT NULL,
    expiration_date TIMESTAMP   not null,
    created_at      TIMESTAMP   NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP   NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS password_reset_token
(
    id              bigserial primary key,
    token           VARCHAR(40) NOT NULL,
    user_id         BIGINT      NOT NULL,
    expiration_date TIMESTAMP   not null
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id bigint REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    role_id bigint REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS refresh_token_user_devices
(
    refresh_token_id bigint REFERENCES refresh_token (id) ON UPDATE CASCADE ON DELETE CASCADE,
    user_device_id   bigint REFERENCES user_device (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT refresh_token_user_device_pkey PRIMARY KEY (refresh_token_id, user_device_id)
);
