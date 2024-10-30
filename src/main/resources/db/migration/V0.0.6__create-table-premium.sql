CREATE TABLE premium_plan
(
    id             SERIAL PRIMARY KEY,
    functionality  VARCHAR(255) NOT NULL,
    standard       BOOLEAN,
    premium        BOOLEAN,
    description    TEXT,
    code_reference INTEGER
);


INSERT INTO premium_plan (functionality, standard, premium, description, code_reference)
VALUES ('', TRUE, TRUE, 'Root path accessible to all users', 1001),
       ('scpi', TRUE, TRUE, 'SCPI route accessible with authentication', 1002),
       ('scpi/:id', TRUE, TRUE, 'SCPI detail route accessible with authentication', 1003),
       ('invest', TRUE, TRUE, 'Investment page accessible with authentication', 1004),
       ('simulation', FALSE, TRUE, 'Simulation page accessible with authentication', 1005),
       ('versement', FALSE, TRUE, 'Planned investment page accessible with authentication', 1006),
       ('comparateur', FALSE, TRUE, 'Comparator page accessible with authentication', 1008)
