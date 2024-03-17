[<- Retour](./../README.md)

# Documentation de l'API Commande

Cette documentation décrit les différentes requêtes possibles pour interagir avec notre API dédiée aux commandes, ainsi que les retours attendus pour chaque demande.

| URI                         | Opération |     Requête     | Réponse                   | Code de retour                           |
|:----------------------------|:---------:|:---------------:|:--------------------------|------------------------------------------|
| /commandes                  |`GET`      |                 |Liste des commandes        |`HTTP 200 OK`, `HTTP 404 Not Found`       |
| /commandes/{id}             |`GET`      |                 |Une commande               |`HTTP 200 OK`, `HTTP 404 Not Found`       |   
| /commandes/{id}/{attribut}  |`GET`      |                 |L'attribut de la commande  |`HTTP 200 OK`, `HTTP 404 Not Found`       |
| /commandes                  |`POST`     | Commande        |                           |`HTTP 201 Created`, `HTTP 400 Bad Request`|
| /commandes/{id}/{attribut}  |`POST`     |                 |                           |`HTTP 201 Created`, `HTTP 400 Bad Request`, `HTTP 404 Not Found`|
| /commandes/{id}/{attribut}  |`DELETE`   |                 |                           |`HTTP 200 OK`, `HTTP 400 Bad Request`, `HTTP 404 Not Found`       |

## Méthodes d'appel

La classe `CommandeAPI` étend la classe abstraite `API` et définit six méthodes `doGet`, `doPatch`, `doPost`, `doDelete` et un constructeur par défaut. Ces méthodes correspondent aux méthodes HTTP GET, PATCH, POST et DELETE respectivement.


## Requêtes GET

### Obtenir tous les commandes

```bash
GET /commandes
```

Exemple de retour : 
```json
[
  {
    "name": "Commande 1",
    "date": [
      2023,
      6,
      1
    ],
    "panier": [
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
            "id": 2,
            "name": "Mozzarella",
            "price": 2
          },
          {
            "id": 1,
            "name": "Sauce tomate",
            "price": 1.5
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
            "id": 6,
            "name": "CÅ“urs d'artichaut",
            "price": 2
          },
          {
            "id": 5,
            "name": "Olives",
            "price": 1.25
          },
          {
            "id": 4,
            "name": "Champignons",
            "price": 1.75
          },
          {
            "id": 3,
            "name": "Jambon",
            "price": 2.5
          },
          {
            "id": 2,
            "name": "Mozzarella",
            "price": 2
          },
          {
            "id": 1,
            "name": "Sauce tomate",
            "price": 1.5
          }
        ],
        "finalPrice": 21.990000000000002
      }
    ],
    "id": 1,
    "finalPrice": 34.480000000000004
  },
  {
    "name": "Commande 2",
    "date": [
      2023,
      6,
      2
    ],
    "panier": [
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
            "id": 6,
            "name": "CÅ“urs d'artichaut",
            "price": 2
          },
          {
            "id": 5,
            "name": "Olives",
            "price": 1.25
          },
          {
            "id": 4,
            "name": "Champignons",
            "price": 1.75
          },
          {
            "id": 3,
            "name": "Jambon",
            "price": 2.5
          },
          {
            "id": 2,
            "name": "Mozzarella",
            "price": 2
          },
          {
            "id": 1,
            "name": "Sauce tomate",
            "price": 1.5
          }
        ],
        "finalPrice": 21.990000000000002
      }
    ],
    "id": 2,
    "finalPrice": 21.990000000000002
  }
]
```

Cette requête renvoie un statut de réponse : 
- `HTTP 200 OK` Si des commandes existent dans la base de donnés
- `HTTP 404 Not Found` Et une liste vide.

### Obtenir une commande par son ID

```bash
GET /commandes/{id}
```

Paramètres :
- `{id}` : L'ID de la commandes a affiché

Exemple de retours : 
```json
{
  "name": "Commande 1",
  "date": [
    2023,
    6,
    1
  ],
  "panier": [
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
      "finalPrice": 21.990000000000002
    }
  ],
  "id": 1,
  "finalPrice": 34.480000000000004
}
```

Cette requête renvoie un statut de réponse : 
- `HTTP 200 OK` Si des commandes existent dans la base de
- `HTTP 404 Not Found` Et une liste vide.

### Obtenir une propriété d'une commande par son ID

```bash
GET /commande/{id}/{attribute}
```

Paramètres :
- `{id}` : L'ID de la commandes à afficher
- `{attribute}` : L'attribut à afficher
    - `nom`, `date`, `panier`, `id`, `prixfinal`

Exemple de retours :
```json
"Commande 1"
```

Cette requête renvoie un statut de réponse : 
- `HTTP 200 OK` Si l'attribut a été trouvé
- `HTTP 404 Not Found` Si la commandes n'a pas été trouvée

## Requêtes POST

### Ajouter une nouvelle commande

```bash
POST /commandes
```

Paramètres requis :
- `name` : Sous forme d'une chaîne de caractères (Le nom doit être unique dans la base de données)
- `date` : Sous forme d'une chaîne de caractères
- `panier` : Sous forme d'un tableau contenant les idées des pizza

Exemple de body : 
```json
{
  "name": "Samy",
  "date": "2024-03-10",
  "panier": [
    {
      "id": 1
    },
    {
      "id": 2
    },
    {
      "id": 3
    }
  ]
}
```

Cette requête renvoie un statut de réponse : 
- `HTTP 201 Created` : La nouvelle pâte a été sauvegarde dans la base de données et un ID lui est attribué.
- `HTTP 400 Bad Request` : Erreur lors de la sauvegarde de la nouvelle pâte.

### Ajouter une pizza dans une commande (bientôt)

## Requêtes DELETE

### Supprimer une commande (bientôt)

### Supprimer une pizza dans une commande (bientôt)
