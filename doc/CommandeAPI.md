# API Commandes

| URI                        | Opération |    Requête    | Réponse                                            |
|:---------------------------|:---------:|:-------------:|:---------------------------------------------------|
| /commandes                 |    GET    |               | liste des commandes (C1) disponibles ou 404 ou 500 |
| /commandes/{id}            |    GET    |               | une commande (C1) ou 404 ou 500                    |
| /commandes/{id}/name       |    GET    |               | le nom de la commande ou 400 ou 500                |
| /commandes/{id}/id         |    GET    |               | l'id de la commande ou 400 ou 500                  |
| /commandes/{id}/date       |    GET    |               | la date de livraison de la commande ou 400 ou 500  |
| /commandes/{id}/pizzas     |    GET    |               | le liste des pizzas commandées ou 400 ou 500       |
| /commandes/{id}/price      |    GET    |               | le prix final de la commande ou 400 ou 500         |
| /commandes                 |   POST    | Commande (C2) | 200 ou 400                                         |
| /commandes/{id}            |  DELETE   |               | 200 ou 400 ou 404 ou 500                           |
| /commandes/{id}/{idPizzas} |  DELETE   |               | 200 ou 400 ou 404 ou 500                           |
| /commandes/{id}            |   PATCH   | Commande (C2) | 200 ou 400 ou 404                                  |

# Corps des requêtes

## C1

Une commande est constituée d'un identifiant, d'un nom, d'une date de livraison, d'un prix final et d'une liste de pizzas.

Voici sa représentation JSON :

```JSON
A compléter
```

## P2

Pour la création d'une commande, on a besoin de son nom, de sa date de livraison et de sa liste de pizzas car l'id et le prix finale sont générés automatiquement.

Voici sa représentation JSON :
```JSON
A compléter
```

# Exemples

## Lister toutes les commandes connues par la base de données :

```shell
GET /api/commandes
```

Requête vers le serveur

```shell
GET /api/commandes
```
Réponse du serveur

```JSON
A compléter
```

Code de status HTTP

| Status                    | Description                      |
|:--------------------------|:---------------------------------|
| 200 OK                    | La commande a bien été retournée |
| 404 NOT FOUND             | La commande est nulle            |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur        |

## Récupérer une commande de la base de donnée :

```shell
GET /api/commandes/{id}
```

Requête vers le serveur

```shell
GET /api/commandes/2
```
Réponse du serveur

```JSON
A compléter
```

Code de status HTTP

| Status                    | Description                      |
|:--------------------------|:---------------------------------|
| 200 OK                    | La commande a bien été retournée |
| 404 NOT FOUND             | La commande n'existe pas         |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur        |

## Récupérer le nom d'une commande de la base de donnée :

```shell
GET /api/commandes/{id}/name
```

Requête vers le serveur

```shell
GET /api/commandes/2/name
```
Réponse du serveur

```JSON
A compléter
```

Code de status HTTP

| Status                    | Description                                |
|:--------------------------|:-------------------------------------------|
| 200 OK                    | Le nom de la commande a bien été retournée |
| 400 BAD REQUEST           | Mauvaise requête                           |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur                  |

## Récupérer l'id d'une commande de la base de donnée :

```shell
GET /api/commandes/{id}/id
```

Requête vers le serveur

```shell
GET /api/commandes/2/id
```
Réponse du serveur

```JSON
A compléter
```

Code de status HTTP

| Status                    | Description                              |
|:--------------------------|:-----------------------------------------|
| 200 OK                    | L'id de la commande a bien été retournée |
| 400 BAD REQUEST           | Mauvaise requête                         |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur                |

## Récupérer la date de livraison d'une commande de la base de donnée :

```shell
GET /api/commandes/{id}/pate
```

Requête vers le serveur

```shell
GET /api/commandes/2/pate
```
Réponse du serveur

```JSON
A compléter
```

Code de status HTTP

| Status                    | Description                                              |
|:--------------------------|:---------------------------------------------------------|
| 200 OK                    | La date de livraison de la commande a bien été retournée |
| 400 BAD REQUEST           | Mauvaise requête                                         |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur                                |

## Récupérer le prix final d'une commande de la base de donnée :

```shell
GET /api/commandes/{id}/price
```

Requête vers le serveur

```shell
GET /api/commandes/2/price
```
Réponse du serveur

```JSON
A compléter
```

Code de status HTTP

| Status                    | Description                                       |
|:--------------------------|:--------------------------------------------------|
| 200 OK                    | Le prix final de la commande a bien été retournée |
| 400 BAD REQUEST           | Mauvaise requête                                  |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur                         |

## Récupérer la liste de pizzas d'une commande de la base de donnée :

```shell
GET /api/commandes/{id}/pizzas
```

Requête vers le serveur

```shell
GET /api/commandes/2/pizzas
```
Réponse du serveur

```JSON
A compléter
```

Code de status HTTP

| Status                    | Description                                            |
|:--------------------------|:-------------------------------------------------------|
| 200 OK                    | La liste de pizzas de la commande a bien été retournée |
| 400 BAD REQUEST           | Mauvaise requête                                       |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur                              |


## Enregistre une nouvelle commande dans la base de donnée :

```shell
POST /api/commandes
```

Requête vers le serveur

```shell
POST /api/commandes
```

Code de status HTTP

| Status                    | Description                    |
|:--------------------------|:-------------------------------|
| 200 OK                    | La commande a bien été ajoutée |
| 400 BAD REQUEST           | Mauvaise requête               |


## Supprime une commande de la base de donnée :

```shell
DELETE /api/commandes/{id}
```

Requête vers le serveur

```shell
DELETE /api/commandes/2
```

Code de status HTTP

| Status                      | Description                          |
|:----------------------------|:-------------------------------------|
| 200 OK                      | La commande a bien été supprimée     |
| 400 BAD REQUEST             | Mauvaise requête                     |
| 404 NOT FOUND               | La commande à supprimer n'existe pas |
| 500 INTERNAL SERVOR ERROR   | Erreur interne du serveur            |

## Supprime une pizza d'une commande de la base de donnée :

```shell
DELETE /api/commandes/{id}/{idPizza}
```

Requête vers le serveur

```shell
DELETE /api/commandes/2/3
```

Code de status HTTP

| Status                      | Description                                      |
|:----------------------------|:-------------------------------------------------|
| 200 OK                      | La pizza de la commande a bien été supprimée     |
| 400 BAD REQUEST             | Mauvaise requête                                 |
| 404 NOT FOUND               | La pizza de la commande à supprimer n'existe pas |
| 500 INTERNAL SERVOR ERROR   | Erreur interne du serveur                        |

## Met à jour une commande de la base de donnée :

```shell
PATCH /api/commandes/{id}
```

Requête vers le serveur

```shell
PATCH /api/commandes/3
```

Code de status HTTP

| Status                      | Description                         |
|:----------------------------|:------------------------------------|
| 200 OK                      | La commande a bien été modifiée     |
| 400 BAD REQUEST             | Mauvaise requête                    |
| 404 NOT FOUND               | La commande à modifier n'existe pas |