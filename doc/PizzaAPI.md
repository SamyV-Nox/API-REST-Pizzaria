# API Pizzas

| URI                         | Opération |  Requête   | Réponse                                          |
|:----------------------------|:---------:|:----------:|:-------------------------------------------------|
| /pizzas                     |    GET    |            | liste des pizzas (P1) disponibles ou 404 ou 500  |
| /pizzas/{id}                |    GET    |            | une pizza (P1) ou 404 ou 500                     |
| /pizzas/{id}/name           |    GET    |            | le nom de la pizza ou 400 ou 500                 |
| /pizzas/{id}/id             |    GET    |            | l'id de la pizza ou 400 ou 500                   |
| /pizzas/{id}/pate           |    GET    |            | la pâte de la pizza ou 400 ou 500                |
| /pizzas/{id}/price          |    GET    |            | le prix final de la pizza ou 400 ou 500          |
| /pizzas/{id}/ingredients    |    GET    |            | la liste d'ingrédients de la pizza ou 400 ou 500 |
| /pizzas                     |   POST    | Pizza (P2) | 200 ou 400                                       |
| /pizzas/{id}                |  DELETE   |            | 200 ou 400 ou 404 ou 500                         |
| /pizzas/{id}/{idIngredient} |  DELETE   |            | 200 ou 400 ou 404 ou 500                         |
| /pizzas/{id}                |   PATCH   | Pizza (P2) | 200 ou 400 ou 404                                |

# Corps des requêtes

## P1

Une pizza est constituée d'un identifiant, d'un nom, d'un type de pâte, d'un prix final et d'une liste d'ingrédient.

Voici sa représentation JSON :

```JSON
A compléter
```

## P2

Pour la création d'une pizza, on a besoin de son nom, de son type de pâte et de ses ingrédients car l'id et le prix finale sont générés automatiquement.

Voici sa représentation JSON :
```JSON
A compléter
```

# Exemples

## Lister toutes les pizzas connues par la base de données :

```shell
GET /api/pizzas
```

Requête vers le serveur

```shell
GET /api/pizzas
```
Réponse du serveur

```JSON
A compléter
```

Code de status HTTP

| Status                    | Description                              |
|:--------------------------|:-----------------------------------------|
| 200 OK                    | La liste des pizzas a bien été retournée |
| 404 NOT FOUND             | La liste des pizzas est nulle            |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur                |

## Récupérer une pizza de la base de donnée :

```shell
GET /api/pizzas/{id}
```

Requête vers le serveur

```shell
GET /api/pizzas/2
```
Réponse du serveur

```JSON
A compléter
```

Code de status HTTP

| Status                    | Description                   |
|:--------------------------|:------------------------------|
| 200 OK                    | La pizza a bien été retournée |
| 404 NOT FOUND             | La pizza n'existe pas         |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur     |

## Récupérer le nom d'une pizza de la base de donnée :

```shell
GET /api/pizzas/{id}/name
```

Requête vers le serveur

```shell
GET /api/pizzas/2/name
```
Réponse du serveur

```JSON
A compléter
```

Code de status HTTP

| Status                    | Description                             |
|:--------------------------|:----------------------------------------|
| 200 OK                    | Le nom de la pizza a bien été retournée |
| 400 BAD REQUEST           | Mauvaise requête                        |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur               |

## Récupérer l'id d'une pizza de la base de donnée :

```shell
GET /api/pizzas/{id}/id
```

Requête vers le serveur

```shell
GET /api/pizzas/2/id
```
Réponse du serveur

```JSON
A compléter
```

Code de status HTTP

| Status                    | Description                           |
|:--------------------------|:--------------------------------------|
| 200 OK                    | L'id de la pizza a bien été retournée |
| 400 BAD REQUEST           | Mauvaise requête                      |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur             |

## Récupérer le type de pâte d'une pizza de la base de donnée :

```shell
GET /api/pizzas/{id}/pate
```

Requête vers le serveur

```shell
GET /api/pizzas/2/pate
```
Réponse du serveur

```JSON
A compléter
```

Code de status HTTP

| Status                    | Description                                      |
|:--------------------------|:-------------------------------------------------|
| 200 OK                    | Le type de pâte de la pizza a bien été retournée |
| 400 BAD REQUEST           | Mauvaise requête                                 |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur                        |

## Récupérer le prix final d'une pizza de la base de donnée :

```shell
GET /api/pizzas/{id}/price
```

Requête vers le serveur

```shell
GET /api/pizzas/2/price
```
Réponse du serveur

```JSON
A compléter
```

Code de status HTTP

| Status                    | Description                                    |
|:--------------------------|:-----------------------------------------------|
| 200 OK                    | Le prix final de la pizza a bien été retournée |
| 400 BAD REQUEST           | Mauvaise requête                               |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur                      |

## Récupérer la liste d'ingrédients d'une pizza de la base de donnée :

```shell
GET /api/pizzas/{id}/ingredients
```

Requête vers le serveur

```shell
GET /api/pizzas/2/ingredients
```
Réponse du serveur

```JSON
A compléter
```

Code de status HTTP

| Status                    | Description                                             |
|:--------------------------|:--------------------------------------------------------|
| 200 OK                    | La liste d'ingrédients de la pizza a bien été retournée |
| 400 BAD REQUEST           | Mauvaise requête                                        |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur                               |


## Enregistre une nouvelle pizza dans la base de donnée :

```shell
POST /api/pizzas
```

Requête vers le serveur

```shell
POST /api/pizzas
```

Code de status HTTP

| Status                    | Description                 |
|:--------------------------|:----------------------------|
| 200 OK                    | La pizza a bien été ajoutée |
| 400 BAD REQUEST           | Mauvaise requête            |


## Supprime une pizza de la base de donnée :

```shell
DELETE /api/pizzas/{id}
```

Requête vers le serveur

```shell
DELETE /api/pizzas/2
```

Code de status HTTP

| Status                      | Description                       |
|:----------------------------|:----------------------------------|
| 200 OK                      | La pizza a bien été supprimée     |
| 400 BAD REQUEST             | Mauvaise requête                  |
| 404 NOT FOUND               | La pizza à supprimer n'existe pas |
| 500 INTERNAL SERVOR ERROR   | Erreur interne du serveur         |

## Supprime un ingrédient d'une pizza de la base de donnée :

```shell
DELETE /api/pizzas/{id}/{idIngredient}
```

Requête vers le serveur

```shell
DELETE /api/pizzas/2/3
```

Code de status HTTP

| Status                      | Description                                       |
|:----------------------------|:--------------------------------------------------|
| 200 OK                      | L'ingrédient de la pizza a bien été supprimée     |
| 400 BAD REQUEST             | Mauvaise requête                                  |
| 404 NOT FOUND               | L'ingrédient de la pizza à supprimer n'existe pas |
| 500 INTERNAL SERVOR ERROR   | Erreur interne du serveur                         |

## Met à jour une pizza de la base de donnée :

```shell
PATCH /api/pizzas/{id}
```

Requête vers le serveur

```shell
PATCH /api/pizzas/3
```

Code de status HTTP

| Status                      | Description                      |
|:----------------------------|:---------------------------------|
| 200 OK                      | La pizza a bien été modifiée     |
| 400 BAD REQUEST             | Mauvaise requête                 |
| 404 NOT FOUND               | La pizza à modifier n'existe pas |