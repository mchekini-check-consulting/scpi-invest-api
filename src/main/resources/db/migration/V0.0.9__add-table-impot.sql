CREATE TABLE tax
(
    id              integer PRIMARY KEY,
    min_range_value BIGINT,
    max_range_value BIGINT,
    rate            integer
);

INSERT into tax (id, min_range_value, max_range_value, rate)
values (1, 0, 11294, 0),
       (2, 11295, 28797, 11),
       (3, 28798, 82341, 30),
       (4, 82342, 177106, 41),
       (5, 177107, 9223372036854775807, 45)
