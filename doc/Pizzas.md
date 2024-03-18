[<- Retour](./../README.md)

# Documentation de l'API Pizza

Cette documentation décrit les différentes requêtes que l'on peut effectuer vers notre API Pizza, ainsi que les réponses attendues.

| URI                     | Opération     |     Requête     | Réponse                   | Code de retour                           |
|:------------------------|:---------:    |:---------------:|:--------------------------|------------------------------------------|
| /pizza                  |`GET`          |                 |Liste des pizzas           |`HTTP 200 OK`, `HTTP 404 Not Found`       |
| /pizza/{id}             |`GET`          |                 |Une pizza                  |`HTTP 200 OK`, `HTTP 404 Not Found`       |   
| /pizza/{id}/{attribut}  |`GET`          |                 |L'attribut de la pizza     |`HTTP 200 OK`, `HTTP 404 Not Found`       |
| /pizza                  |`POST`         | Pizza           |                           |`HTTP 201 Created`, `HTTP 400 Bad Request`|
| /pizza/{id}/{attribut}  |`POST`         | Pizza           |                           |`HTTP 201 Created`, `HTTP 400 Bad Request`, `HTTP 404 Not Found`|
| /pizza/{id}             |`DELETE`       |                 |                           |`HTTP 200 OK`, `HTTP 404 Not Found`       |
| /pizza/{id}/{attribut}  |`DELETE`       |                 |                           |`HTTP 200 OK`, `HTTP 404 Not Found`,  `HTTP 404 Not Found`|
| /pizza                  |`PATCH`        | Pizza           |                           |`HTTP 200 OK`, `HTTP 404 Not Found`, `HTTP 400 Bad Request`|

## Méthodes d'appel

La classe `PizzaAPI` étend la classe abstraite `API` et définit six méthodes `doGet`, `doPatch`, `doPost`, `doDelete` et un constructeur par défaut. Ces méthodes correspondent aux méthodes HTTP GET, PATCH, POST et DELETE respectivement.

## Requêtes GET

### Obtenir toutes les pizzas

```bash
GET /pizza
```

Exemple de retour : 
```json
[
  {
    "id": 1,
    "name": "Margherita",
    "pate": {
      "id": 1,
      "name": "Classique"
    },
    "price": 8.99,
    "ingredients": [
      {
        "id": 1,
        "name": "Sauce tomate",
        "price": 1.5
      },
      {
        "id": 2,
        "name": "Mozzarella",
        "price": 2
      }
    ],
    "finalPrice": 12.49
  },
  {
    "id": 2,
    "name": "Quattro Stagioni",
    "pate": {
      "id": 2,
      "name": "Fine"
    },
    "price": 10.99,
    "ingredients": [
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
      },
      {
        "id": 4,
        "name": "Champignons",
        "price": 1.75
      },
      {
        "id": 5,
        "name": "Olives",
        "price": 1.25
      },
      {
        "id": 6,
        "name": "CÅ“urs d'artichaut",
        "price": 2
      }
    ],
    "finalPrice": 21.99
  }
]
```

Cette requête renvoie un statut de réponse : 
- `HTTP 200 OK` : Retourne les pizza dans le corps de la réponse.
- `HTTP 404 Not Found` : Aucune pizza n'a été trouvée dans la base de données.

### Obtenir une pizza par son ID

```bash
GET /pizza/{id}
```

Exemple de retour : 
```json
{
  "id": 1,
  "name": "Margherita",
  "pate": {
    "id": 1,
    "name": "Classique"
  },
  "price": 8.99,
  "ingredients": [
    {
      "id": 1,
      "name": "Sauce tomate",
      "price": 1.5
    },
    {
      "id": 2,
      "name": "Mozzarella",
      "price": 2
    }
  ],
  "finalPrice": 12.49
}
```

Cette requête renvoie un statut de réponse : 
- `HTTP 200 OK` : Retourne la pizza dans le corps de la réponse.
- `HTTP 404 Not Found` : Aucune pizza ayant cet ID n'a été trouvée dans la base de données.

### Obtenir un attribut de pizza par son ID

```bash
GET /pizza/{id}/{attribut}
```

Exemple de retour : 
```json
"Margherita"
```

Paramètres :
- `{id}` : L'ID de la pizza à modifier
- `{attribut}` : Liste des attributs possibles :
    - `id`, `nom`, `pate`, `prix`, `ingredients`, `prixFinal`


Cette requête renvoie un statut de réponse : 
- `HTTP 200 OK` : Retourne l'attribut de la pizza dans le corps de la réponse.
- `HTTP 404 Not Found` : Aucune pizza ayant cet ID n'a été trouvée dans la base de données.

## Requêtes DELETE

### Supprimer une pizza

```bash
DELETE /pizzas/{id}
```

Paramètres :
- `{id}` : L'ID de la pizza à supprimer

Cette requête renvoie un statut de réponse : 
- `HTTP 200 OK` : La pizza a été supprimée de la base de données.
- `HTTP 400 Bad Request` : Erreur lors de la suppression de la pizza, exemple l'ID n'est pas un nombre. 
- `HTTP 404 Not Found` : Aucune pizza ayant cet ID n'a été trouvée dans la base de données.

### Supprimer l'ingrédient d'une pizza

```bash
DELETE /pizzas/{id}/{ingredientId}
```

Paramètres :
- `{id}` : L'ID de la pizza
- `{ingredientId}` : L'ID de l'ingrédient à supprimer de la pizza

Cette requête renvoie un statut de réponse : 
- `HTTP 200 OK` : La pizza a été supprimée de la base de données.
- `HTTP 400 Bad Request` : Erreur lors de la suppression de la pizza, exemple l'ID n'est pas un nombre. 
- `HTTP 404 Not Found` : Aucune pizza ayant cet ID n'a été trouvée dans la base de données.

## Requêtes POST

### Ajouter une nouvelle pizza

```bash
POST /pizzas
```

Exemple de body : 
```json
{
  "name": "test",
  "pate": {
    "id": 1
  },
  "ingredients": [
    {
      "id": 1
    }
  ]
}
```

Cette requête renvoie un statut de réponse : 
- `HTTP 201 Created` : La nouvelle pizza a été sauvegarde dans la base de données et un ID lui est attribué.
- `HTTP 400 Bad Request` : Erreur lors de la sauvegarde de la nouvelle pizza.

### Ajouter un nouvel ingrédient a une pizza

```bash
POST /pizzas/{id}/{idIngredient}
```

Paramètres :
- `{id}` : L'ID de la pizza à modifier
- `{idIngredient}` : L'ID de l'ingrédient à ajouter

Cette requête renvoie un statut de réponse : 
- `HTTP 201 Created` : La nouvelle pizza a été sauvegarde dans la base de données et un ID lui est attribué.
- `HTTP 400 Bad Request` : Erreur lors de la sauvegarde de la nouvelle pizza.
- `HTTP 404 Not Found` : Aucune pizza ayant cet ID n'a été trouvée dans la base de données.

## Requêtes PATCH

```bash
PATCH /pates/{id}
```

Paramètres :
- `{id}` : L'ID de la pizza à mettre à jour

Exemple de body : 
```json
{
  "id": 1,
  "name": "Huits fromages",
  "pate": {
    "id": 2
  },
  "price": 5.00,
  "ingredients": [
    {
      "id": 1
    }
  ]
}
```

> Attention le nom d'une pizza doit être unique !

Cette requête renvoie un statut de réponse : 
- `HTTP 200 OK` : La pizza a été mise à jour dans la base de données et un ID lui reste inchangé.
- `HTTP 400 Bad Request` : Erreur lors de la mise à jour de la pizza, Par exemple une pizza portant le même nom existe déjà.
- `HTTP 404 Not Found` : Aucune pizza ayant cet ID n'a été trouvée dans la base de données.

