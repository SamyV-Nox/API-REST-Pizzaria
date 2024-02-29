SELECT i.ino,
i.name as "Iname",
i.price as "Iprice",
p.pno,
p.name as "Pname",
p.dough as "Pdough",
p.price as "Pprice"
FROM pizzas as "p"
JOIN contient USING (pno) 
JOIN ingredients as "i" USING (ino);



SELECT i.ino, i.name as "Iname", i.price as "Iprice", p.pno, p.name as "Pname", p.dough as "Pdough", p.price as "Pprice" FROM pizzas as "p" JOIN contient USING (pno) JOIN ingredients as "i" USING (ino) WHERE p.pno = 1