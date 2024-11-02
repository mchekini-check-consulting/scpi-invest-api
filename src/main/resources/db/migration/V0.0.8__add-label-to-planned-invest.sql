ALTER TABLE planified_investment
    ADD COLUMN label varchar,
    ADD COLUMN reason varchar,
    ADD COLUMN decision_date TIMESTAMP;
