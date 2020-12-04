CREATE TABLE IF NOT EXISTS tours
(
    id                bigserial primary key,
    creator_id        int          NOT NULL,
    tour_name         varchar(100) NOT NULL,
    min_people_number int          NOT NULL,
    max_people_number int          NOT NULL,
    created_at        TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at        TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS tour_steps
(
    id                        bigserial primary key,
    location                  varchar(200) NOT NULL,
    description               varchar(300),
    show_route_to_next_point  boolean      NOT NULL,
    travel_mode_to_next_point varchar(20),
    location_lat              float        NOT NULL,
    location_lng              float        NOT NULL,
    created_at                TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at                TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS tour_tags
(
    id      bigserial primary key,
    tag     varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS tour_restrictions
(
    id          bigserial primary key,
    restriction varchar(100) NOT NULL
);

-- many tags for many tours
CREATE  TABLE IF NOT EXISTS tours_tour_tags
(
    tour_id bigint REFERENCES tours (id) ON UPDATE CASCADE ON DELETE CASCADE,
    tour_tag_id bigint REFERENCES tour_tags (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT tours_tour_tags_pkey PRIMARY KEY (tour_id, tour_tag_id)
);

-- many restrictions for many tours
CREATE TABLE IF NOT EXISTS tours_tour_restrictions
(
    tour_id bigint REFERENCES tours (id) ON UPDATE CASCADE ON DELETE CASCADE,
    tour_restriction_id bigint REFERENCES tour_restrictions ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT tours_tour_restrictions_pkey PRIMARY KEY (tour_id, tour_restriction_id)
);

INSERT INTO tour_tags(tag) VALUES ('City');
INSERT INTO tour_tags(tag) VALUES ('Outdoor');
INSERT INTO tour_tags(tag) VALUES ('Nature');
INSERT INTO tour_tags(tag) VALUES ('Park');
INSERT INTO tour_tags(tag) VALUES ('Art');

INSERT INTO tour_restrictions(restriction) VALUES ('Adults only');
INSERT INTO tour_restrictions(restriction) VALUES ('Strong fit');
