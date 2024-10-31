CREATE TABLE plan
(
    id             SERIAL PRIMARY KEY,
    functionality  VARCHAR(255) NOT NULL,
    standard       BOOLEAN,
    premium        BOOLEAN,
    description    TEXT
);


INSERT INTO plan (functionality, standard, premium, description)
VALUES ('list-scpi', TRUE, TRUE, 'Récupération des informations des SCPI Partenaires'),
       ('my-investments', TRUE, TRUE, 'Suivi en temps réel de mes investissements'),
       ('my-planned-investment', TRUE, TRUE, 'Prise en charge des versements programmées'),
       ('simulation', FALSE, TRUE, 'Simulation avancée des investissements'),
       ('comparator', FALSE, TRUE, 'Comparateur complet de SCPI')
