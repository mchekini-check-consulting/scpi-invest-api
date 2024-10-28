ALTER TABLE scpi
    ADD COLUMN is_stripping bool,
    ADD COLUMN cashback float,
    ADD COLUMN is_planed_investment bool,
    ADD COLUMN advertising varchar;


CREATE TABLE discount_stripping
(
    scpi_id        integer,
    year           integer,
    discount          float,
    PRIMARY KEY (scpi_id, year)
);

ALTER TABLE discount_stripping
    ADD FOREIGN KEY (scpi_id) REFERENCES scpi (id);