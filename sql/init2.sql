
DROP TABLE IF EXISTS recettes CASCADE;
DROP TABLE IF EXISTS ingredients CASCADE;
DROP TABLE IF EXISTS pizzas CASCADE;
DROP TABLE IF EXISTS pates;

-- PATES

CREATE TABLE pates(
    dno SERIAL PRIMARY KEY,
    d_name VARCHAR(20) NOT NULL
);

INSERT INTO pates (d_name) VALUES 
('Classique'),
('Fine'),
('Épaisse'),
('Blé entier'),
('Sans gluten');

-- INGREDIENTS

CREATE TABLE ingredients(
    ino SERIAL PRIMARY KEY,
    i_nom VARCHAR(20) NOT NULL,
    i_prix FLOAT  NOT NULL
);

INSERT INTO ingredients(i_nom, i_prix) VALUES
('Sauce tomate', 1.50),
('Mozzarella', 2.00),
('Jambon', 2.50),
('Champignons', 1.75),
('Olives', 1.25),
('Cœurs d''artichaut', 2.00),
('Ricotta', 2.50),
('Poivrons', 1.50),
('Oignons', 1.25),
('Aubergine', 2.00),
('Ananas', 1.50),
('Pepperoni', 2.50),
('Anchois', 2.00),
('Câpres', 1.50),
('Origan', 1.00),
('Ananas', 1.50),
('Fromage de chèvre', 3.00),
('Miel', 2.00),
('Gorgonzola', 2.50),
('Chèvre', 2.50),
('Parmesan', 2.00);

-- PIZZA

CREATE TABLE pizzas(
    pno SERIAL PRIMARY KEY,
    p_nom VARCHAR(20) NOT NULL,
    dno INT DEFAULT 1,
    p_prix FLOAT NOT NULL,

    FOREIGN KEY (dno) references pates(dno) ON DELETE CASCADE
);

INSERT INTO pizzas(p_nom, dno, p_prix) VALUES 
('Margherita', 1, 8.99),        
('Quattro Stagioni', 2, 10.99), 
('Calzone', 3, 9.99),           
('Végétarienne', 4, 11.99),     
('Sans Gluten', 5, 12.99),      
('Pepperoni', 1, 9.49),         
('Napolitaine', 2, 11.49),      
('Hawaïenne', 3, 10.99),        
('Fromage de chèvre', 4, 12.99),
('Quatre fromages', 5, 13.49);

-- PIZZAS / INGREDIENTS

CREATE TABLE recettes(
    pno INT,
    ino INT,

    PRIMARY KEY (pno, ino),
    FOREIGN KEY (pno) references pizzas(pno) ON DELETE CASCADE,
    FOREIGN KEY (ino) references ingredients(ino) ON DELETE CASCADE
);

-- Associer les ingrédients avec les pizzas

-- Margherita
INSERT INTO recettes(pno, ino) VALUES 
(1, 1), -- Sauce tomate
(1, 2), -- Mozzarella
-- Quattro Stagioni
(2, 1), -- Sauce tomate
(2, 2), -- Mozzarella
(2, 3), -- Jambon
(2, 4), -- Champignons
(2, 5), -- Olives
(2, 6), -- Cœurs d'artichaut
-- Calzone 
(3, 1), -- Sauce tomate
(3, 2), -- Mozzarella
(3, 7), -- Ricotta
(3, 3), -- Jambon
-- Végétarienne
(4, 1), -- Sauce tomate
(4, 2), -- Mozzarella
(4, 4), -- Champignons
(4, 8), -- Poivrons
(4, 9), -- Oignons
(4, 5), -- Olives
(4, 10), -- Aubergine
-- Sans Gluten
(5, 1), -- Sauce tomate
(5, 2), -- Mozzarella
(5, 3), -- Jambon
(5, 4), -- Champignons
(5, 5), -- Olives
(5, 6), -- Cœurs d'artichaut
(5, 11), -- Ananas
-- Pepperoni
(6, 1), -- Sauce tomate
(6, 2), -- Mozzarella
(6, 12), -- Pepperoni
-- Napolitaine
(7, 1), -- Sauce tomate
(7, 2), -- Mozzarella
(7, 13), -- Anchois
(7, 5), -- Olives
(7, 14), -- Câpres
(7, 15), -- Origan
-- Hawaïenne
(8, 1), -- Sauce tomate
(8, 2), -- Mozzarella
(8, 3), -- Jambon
(8, 11), -- Ananas
-- Fromage de chèvre
(9, 1), -- Sauce tomate
(9, 2), -- Mozzarella
(9, 16), -- Fromage de chèvre
(9, 17), -- Miel
-- Quatre fromages 
(10, 1), -- Sauce tomate
(10, 2), -- Mozzarella
(10, 18), -- Gorgonzola
(10, 19), -- Chèvre
(10, 20); -- Parmesan
