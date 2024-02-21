DROP TABLE IF EXISTS ingredients;

CREATE TABLE ingredients(
    id Serial primary key,
    name varchar(20),
    price Integer 
);