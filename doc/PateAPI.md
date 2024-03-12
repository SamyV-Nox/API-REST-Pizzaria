# API Pâtes

| URI              | Opération |  Requête  | Réponse                                        |
|:-----------------|:---------:|:---------:|:-----------------------------------------------|
| /pates           |    GET    |           | liste des pâtes (P1) disponibles ou 404 ou 500 |
| /pates/{id}      |    GET    |           | une pâte (P1) ou 404 ou 500                    |
| /pates/{id}/name |    GET    |           | le nom de la pâte ou 400 ou 500                |
| /pates/{id}/id   |    GET    |           | l'id de la pâte ou 400 ou 500                  |
| /pates           |   POST    | Pâte (P2) | 200 ou 400                                     |
| /pates/{id}      |  DELETE   |           | 200 ou 400 ou 404 ou 500                       |
| /pâtes/{id}      |   PATCH   | Pâte (P2) | 200 ou 400 ou 404                              |

# Corps des requêtes

## P1

Une pâte est constituée d'un identifiant et d'un nom.

Voici sa représentation JSON :

```JSON
{
  "id": 1,
  "name": "Classique"
}
```

## P2

Pour la création d'une pâte, on a juste besoin de son nom car l'id est généré automatiquement.

Voici sa représentation JSON :
```JSON
{
  "name": "Classique"
}
```

# Exemples

## Lister toutes les pâtes connues par la base de données :

```shell
GET /api/pates
```

Requête vers le serveur

```shell
GET /api/pates
```
Réponse du serveur

```JSON
[
  {
    "id": 1,
    "name": "Classique"
  },
  {
    "id": 2,
    "name": "Fine"
  },
  {
    "id": 3,
    "name": "épaisse"
  },
  {
    "id": 4,
    "name": "Blé entier"
  },
  {
    "id": 5,
    "name": "Sans gluten"
  }
]
```

Code de status HTTP

| Status                    | Description                             |
|:--------------------------|:----------------------------------------|
| 200 OK                    | La liste des pâtes a bien été retournée |
| 404 NOT FOUND             | La liste des pâtes est nulle            |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur               |

## Récupérer une pâte de la base de donnée :

```shell
GET /api/pates/{id}
```

Requête vers le serveur

```shell
GET /api/pates/2
```
Réponse du serveur

```JSON
{
  "id": 2,
  "name": "Fine"
}
```

Code de status HTTP

| Status                    | Description                  |
|:--------------------------|:-----------------------------|
| 200 OK                    | La pâte a bien été retournée |
| 404 NOT FOUND             | La pâte n'existe pas         |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur    |

## Récupérer le nom d'une pâte de la base de donnée :

```shell
GET /api/pates/{id}/name
```

Requête vers le serveur

```shell
GET /api/pates/2/name
```
Réponse du serveur

```JSON
{
  "name": "Fine"
}
```

Code de status HTTP

| Status                    | Description                            |
|:--------------------------|:---------------------------------------|
| 200 OK                    | Le nom de la pâte a bien été retournée |
| 400 BAD REQUEST           | Mauvaise requête                       |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur              |


## Récupérer l'id d'une pâte de la base de donnée :

```shell
GET /api/pates/{id}/id
```

Requête vers le serveur

```shell
GET /api/pates/2/id
```
Réponse du serveur

```JSON
{
  "id": 2
}
```

Code de status HTTP

| Status                    | Description                          |
|:--------------------------|:-------------------------------------|
| 200 OK                    | L'id de la pâte a bien été retournée |
| 400 BAD REQUEST           | Mauvaise requête                     |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur            |


## Enregistre une nouvelle pâte dans la base de donnée :

```shell
POST /api/pates
```

Requête vers le serveur

```shell
POST /api/pates
```

Code de status HTTP

| Status                    | Description                 |
|:--------------------------|:----------------------------|
| 200 OK                    | La pâte a bien été ajoutée  |
| 400 BAD REQUEST           | Mauvaise requête            |

## Supprime une pâte de la base de donnée :

```shell
DELETE /api/pates/{id}
```

Requête vers le serveur

```shell
DELETE /api/pates/2
```

Code de status HTTP

| Status                      | Description                      |
|:----------------------------|:---------------------------------|
| 200 OK                      | La pâte a bien été supprimée     |
| 400 BAD REQUEST             | Mauvaise requête                 |
| 404 NOT FOUND               | La pâte à supprimer n'existe pas |
| 500 INTERNAL SERVOR ERROR   | Erreur interne du serveur        |

## Met à jour une pâte de la base de donnée :

```shell
PATCH /api/pates/{id}
```

Requête vers le serveur

```shell
PATCH /api/pates/3
```

Code de status HTTP

| Status                      | Description                     |
|:----------------------------|:--------------------------------|
| 200 OK                      | La pâte a bien été modifiée     |
| 400 BAD REQUEST             | Mauvaise requête                |
| 404 NOT FOUND               | La pâte à modifier n'existe pas |