DROP TABLE IF EXISTS ingredients, pizzas, contient, commandes, contientPizza;

CREATE TABLE ingredients(
    ino Serial primary key,
    i_name varchar(20),
    i_price Integer 
);

CREATE TABLE pizzas (
    pno Serial primary key,
    p_name varchar(20),
    p_dough varchar(20),
    p_price Float
);

CREATE TABLE contient(
    pno int,
    ino int,
    constraint fk_pizza foreign key(pno) references pizzas(pno) ON DELETE CASCADE,
    constraint fk_ingredient foreign key(ino) references ingredients(ino) ON DELETE CASCADE
);

INSERT INTO ingredients (d_name, d_price) values
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

INSERT INTO pizzas (p_name, p_dough, p_price) values
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

CREATE TABLE commandes (
    cno SERIAL PRIMARY KEY,
    c_name VARCHAR(100) NOT NULL,
    c_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE contientPizza(
    cno int,
    pno int,
    constraint fk_pizza foreign key(pno) references pizzas(pno) ON DELETE CASCADE,
    constraint fk_commandes foreign key(cno) references commandes(cno) ON DELETE CASCADE
);

INSERT INTO commandes(c_name) VALUES ('samy');
INSERT INTO contientPizza VALUES (1,1);
INSERT INTO contientPizza VALUES (1,2);