DROP TABLE IF EXISTS ingredients, pizza, contient;

CREATE TABLE ingredients(
    id Serial primary key,
    name varchar(20),
    price Integer 
);

CREATE TABLE pizza(
    id Serial primary key,
    name varchar(20),
    pate varchar(20),
    price Float
);

CREATE TABLE contient(
    idPizza int,
    idIngredient int,
    constraint fk_pizza foreign key(idPizza) references pizza(id),
    constraint fk_ingredient foreign key(idIngredient) references ingredients(id)
);

INSERT INTO ingredients (name, price) values
    ('sauce tomate', 3),
    ('crème fraiche', 2),
    ('mozzarella', 2),
    ('lardons', 3),
    ('saumon', 5),
    ('raviole', 4),
    ('pomme de terre', 2),
    ('reblochon', 3),
    ('jambon', 3),
    ('emental', 2),
    ('parmesan', 3),
    ('noix de Grenoble', 1);

INSERT INTO pizza (name, pate, price) values
    ('jambon fromage', 'épaisse', 3),
    ('tartiflette', 'fine', 2),
    ('raviole', 'épaisse', 3),
    ('raviole saumon', 'fine', 2),
    ('saumon', 'épaisse', 3);

INSERT INTO contient values
    (1,1),
    (1,3),
    (1,9),
    (1,10),
    (2,2),
    (2,3),
    (2,4),
    (2,7),
    (2,8),
    (3,2),
    (3,3),
    (3,6),
    (3,11),
    (3,12),
    (4,2),
    (4,3),
    (4,5),
    (4,6),
    (4,11),
    (5,2),
    (5,3),
    (5,5);