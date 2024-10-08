CREATE TABLE scpi
(
    id                   integer PRIMARY KEY,
    name                 varchar,
    minimum_subscription integer,
    capitalization       BIGINT,
    manager              varchar,
    subscription_fees    float,
    management_fees      float,
    delay_benefit        integer,
    rent_frequency       varchar
);

CREATE TABLE localization
(
    scpi_id integer,
    country varchar,
    percent float,
    PRIMARY KEY (scpi_id, country)
);

CREATE TABLE distribution_rate
(
    scpi_id           integer,
    year              integer,
    distribution_rate float,
    PRIMARY KEY (scpi_id, year)
);

CREATE TABLE sector
(
    scpi_id integer,
    sector  varchar,
    percent float,
    PRIMARY KEY (scpi_id, sector)
);

CREATE TABLE price
(
    scpi_id        integer,
    year           integer,
    price          float,
    reconstitution float,
    PRIMARY KEY (scpi_id, year)
);

ALTER TABLE localization
    ADD FOREIGN KEY (scpi_id) REFERENCES scpi (id);

ALTER TABLE distribution_rate
    ADD FOREIGN KEY (scpi_id) REFERENCES scpi (id);

ALTER TABLE sector
    ADD FOREIGN KEY (scpi_id) REFERENCES scpi (id);

ALTER TABLE price
    ADD FOREIGN KEY (scpi_id) REFERENCES scpi (id);


CREATE SEQUENCE scpi_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
