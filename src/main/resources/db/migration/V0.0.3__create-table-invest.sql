CREATE TABLE investment
(
    id                 SERIAL PRIMARY KEY,
    user_email         VARCHAR(255) NOT NULL,
    scpi_id            INT          NOT NULL,
    property_type      VARCHAR(255),
    number_of_shares   INT          NOT NULL,
    total_amount       FLOAT,
    investment_status  VARCHAR(50),
    request_date       TIMESTAMP    NOT NULL,
    status_change_date TIMESTAMP,
    notified           BOOLEAN,
    stripping          INT          NULL,
        CONSTRAINT fk_scpi FOREIGN KEY (scpi_id) REFERENCES scpi(id) ON DELETE CASCADE
);
