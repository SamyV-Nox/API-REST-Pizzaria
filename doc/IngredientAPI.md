# API Ingrédients 

| URI                     | Opération |     Requête     | Réponse                                              |
|:------------------------|:---------:|:---------------:|:-----------------------------------------------------|
| /ingredients            |    GET    |                 | liste des ingrédients (I1) disponibles ou 404 ou 500 |
| /ingredients/{id}       |    GET    |                 | un ingrédient (I1) ou 404 ou 500                     |
| /ingredients/{id}/name  |    GET    |                 | le nom d'un ingrédient ou 400                        |
| /ingredients/{id}/price |    GET    |                 | le prix d'un ingrédient ou 400                       |
| /ingredients/{id}/id    |    GET    |                 | l'id d'un ingrédient ou 400                          |
| /ingredients            |   POST    | Ingrédient (I2) |                                                      |
| /ingredients/{id}       |  DELETE   |                 |                                                      |
|                         |   PATCH   |                 |                                                      |

# Corps des requêtes

## I1

Un ingrédient est constitué d'un identifiant, d'un nom et d'un prix. 

Voici sa représentation JSON :

```JSON
{
  "id": 21,
  "name": "Parmesan",
  "price": 2
}
```

## I2

Pour la création d'un ingrédient, on a juste besoin de son nom et de son prix car l'id est généré automatiquement.

Voici sa représentation JSON :
```JSON
{
  "name": "Parmesan",
  "price": 2
}
```

# Exemples

## Lister tous les ingrédients connus par la base de données :

```shell
GET /api/ingredients
```

Requête vers le serveur

```shell
GET /api/ingredients
```
Réponse du serveur

```JSON
[
  {
    "id": 1,
    "name": "Sauce tomate",
    "price": 1.5
  },
  {
    "id": 2,
    "name": "Mozzarella",
    "price": 2
  },
  {
    "id": 3,
    "name": "Jambon",
    "price": 2.5
  }
]
```

Code de status HTTP

| Status                    | Description                                   |
|:--------------------------|:----------------------------------------------|
| 200 OK                    | La liste des ingrédients a bien été retournée |
| 404 NOT FOUND             | La liste des ingrédients est nulle            |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur                     |

## Récupérer un ingrédient de la base de donnée :

```shell
GET /api/ingredients/{id}
```

Requête vers le serveur

```shell
GET /api/ingredients/2
```
Réponse du serveur

```JSON
  {
    "id": 2,
    "name": "Mozzarella",
    "price": 2
  }
```

Code de status HTTP

| Status                    | Description                      |
|:--------------------------|:---------------------------------|
| 200 OK                    | L'ingrédient a bien été retourné |
| 404 NOT FOUND             | L'ingrédient n'existe pas        |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur        |

## Récupérer le nom d'un ingrédient de la base de donnée :

```shell
GET /api/ingredients/{id}/name
```

Requête vers le serveur

```shell
GET /api/ingredients/2/name
```
Réponse du serveur

```JSON
  {
  "name": "Mozzarella"
  }
```

Code de status HTTP

| Status                    | Description                                |
|:--------------------------|:-------------------------------------------|
| 200 OK                    | Le nom de l'ingrédient a bien été retourné |
| 400 BAD REQUEST           | Mauvaise requête                           |

## Récupérer le prix d'un ingrédient de la base de donnée :

```shell
GET /api/ingredients/{id}/price
```

Requête vers le serveur

```shell
GET /api/ingredients/2/price
```
Réponse du serveur

```JSON
  {
  "price": 2
  }
```

Code de status HTTP

| Status                    | Description                                 |
|:--------------------------|:--------------------------------------------|
| 200 OK                    | Le prix de l'ingrédient a bien été retourné |
| 400 BAD REQUEST           | Mauvaise requête                            |

## Récupérer l'id d'un ingrédient de la base de donnée :

```shell
GET /api/ingredients/{id}/id
```

Requête vers le serveur

```shell
GET /api/ingredients/2/id
```
Réponse du serveur

```JSON
  {
  "id": 2
  }
```

Code de status HTTP

| Status                    | Description                              |
|:--------------------------|:-----------------------------------------|
| 200 OK                    | L'id de l'ingrédient a bien été retourné |
| 400 BAD REQUEST           | Mauvaise requête                         |


# Reste le post, le delete et le patch à faire