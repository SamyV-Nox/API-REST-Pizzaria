drop table if exists notes ;
drop table if exists maj ;


CREATE TABLE notes
(
    mat text,
    ncont integer,
    netu integer,
    groupe text,
    valeur integer,
    primary key(mat,ncont,netu)
);


COPY notes FROM stdin;
bdd	1	10	K	5
bdd	1	11	K	6
bdd	1	12	L	7
bdd	1	13	L	8
bdd	2	10	K	9
bdd	2	12	L	11
bdd	2	13	L	12
sys	1	10	K	10
sys	1	11	K	9
sys	1	12	L	8
sys	1	13	L	7
bdd	2	11	K	20
sys	2	11	K	\N
sys	2	10	K	8
sys	2	12	L	14
sys	2	13	L	17
\.


-- colonnes dans un ordre different : c'est expres ;-)
CREATE TEMP TABLE maj 
(      netu int,
       groupe text,
       mat text,
       ncont int,
       valeur int);

\copy maj from stdin
10	K	bdd	2	14
11	K	bdd	1	18
12	L	gestion	2	9
13	L	gestion	2	\N
10	K	gestion	2	16
11	K	gestion	2	\N
\.
