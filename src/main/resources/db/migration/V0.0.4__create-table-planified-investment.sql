CREATE SEQUENCE planified_investment_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE planified_investment
(
    id                 SERIAL PRIMARY KEY,
    user_email         VARCHAR(255) NOT NULL,
    scpi_id            INT          NOT NULL,
    frequency          VARCHAR(255),
    number_of_shares   INT          NOT NULL,
    debit_day_of_month INT          NOT NULL,
    amount             FLOAT,
    status             VARCHAR(50),
    request_date       TIMESTAMP    NOT NULL,
    CONSTRAINT fk_scpi FOREIGN KEY (scpi_id) REFERENCES scpi (id) ON DELETE CASCADE
);
