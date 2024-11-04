CREATE TABLE user_preference
(
    email          varchar PRIMARY KEY,
    income         BIGINT,
    family_status  varchar,
    birth_date     DATE,
    children_count integer,
    profile_type   varchar,
    profession     varchar

)