/*
SELECT i.ino,
i.name as "Iname",
i.price as "Iprice",
p.pno,
p.name as "Pname",
p.dough as "Pdough",
p.price as "Pprice"
FROM pizzas as "p"
LEFT JOIN contient USING (pno) 
LEFT JOIN ingredients as "i" USING (ino) 
WHERE pno = '6';
*/

SELECT * FROM pizzas as "p" LEFT JOIN contient USING (pno) LEFT JOIN ingredients as "i" USING (ino) WHERE pno = '6';