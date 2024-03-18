[<- Retour](./../README.md)

# Documentation de l'API Pate

Cette documentation décrit les différentes requêtes possibles pour interagir avec notre API dédiée aux pates, ainsi que les retours attendus pour chaque demande.

| URI                     | Opération     |     Requête     | Réponse                   | Code de retour                           |
|:------------------------|:---------:    |:---------------:|:--------------------------|------------------------------------------|
| /pates                  |`GET`          |                 |Liste des pates            |`HTTP 200 OK`, `HTTP 404 Not Found`       |
| /pates/{id}             |`GET`          |                 |Une pâte                   |`HTTP 200 OK`, `HTTP 404 Not Found`       |   
| /pates/{id}/{attribut}  |`GET`          |                 |L'attribut de la pâte     |`HTTP 200 OK`, `HTTP 404 Not Found`       |
| /pates                  |`POST`         | Pate            |                           |`HTTP 201 Created`, `HTTP 400 Bad Request`|
| /pates/{id}             |`DELETE`       |                 |                           |`HTTP 200 OK`, `HTTP 404 Not Found`       |
| /pates                  |`PATCH`        | Pate            |                           |`HTTP 200 OK`, `HTTP 404 Not Found`, `HTTP 400 Bad Request`|

## Méthodes d'appel

La classe `IngredientAPI` étend la classe abstraite `API` et définit six méthodes `doGet`, `doPatch`, `doPost`, `doDelete` et un constructeur par défaut. Ces méthodes correspondent aux méthodes HTTP GET, PATCH, POST et DELETE respectivement.

## Requêtes GET

### Obtenir toutes les pates

```bash
GET /pates
```

Exemple de retour : 
```json
[
  {
    "id": 1,
    "name": "Classique"
  },
  {
    "id": 2,
    "name": "Fine"
  }
]
```

Cette requête renvoie un statut de réponse : 
- `HTTP 200 OK` : Retourne une liste de pates dans le corps de la réponse, au format JSON.
- `HTTP 404 Not Found` : Aucune pate n'a été trouvée dans la base de données.

### Obtenir une pate spécifique par son ID

```bash
GET /pates/<id>
```

Paramètres :
- `{id}` : L'ID de la pâte à récupérer

Cette requête renvoie un statut de réponse : 
- `HTTP 200 OK` : Retourne une pate au format JSON dans le corps de la réponse.
- `HTTP 404 Not Found` : Aucune pâte ayant cet ID n'a été trouvée dans la base de données.

### Obtenir un attribut de pâte par son ID

```bash
GET /pates/{id}/{attribut}
```

Exemple de retour : 
```json
"Classique"
```

Paramètres :
- `{id}` : L'ID de la pâte à modifier
- `{attribut}` : Liste des attributs possibles


Cette requête renvoie un statut de réponse : 
- `HTTP 200 OK` : Retourne le nom de la pâte dans le corps de la réponse.
- `HTTP 404 Not Found` : Aucune pâte ayant cet ID n'a été trouvée dans la base de données.

## Requêtes POST

```bash
POST /pates
```

Paramètres requis :
- `name` : Sous forme d'une chaîne de caractères

Exemple de body : 
```json
{
  "name": "Classique"
}
```

Cette requête renvoie un statut de réponse : 
- `HTTP 201 Created` : La nouvelle pâte a été sauvegarde dans la base de données et un ID lui est attribué.
- `HTTP 400 Bad Request` : Erreur lors de la sauvegarde de la nouvelle pâte.

## Requêtes PATCH

```bash
PATCH /pates/{id}
```

Paramètres :
- `{id}` : L'ID de la pâte à mettre à jour

Exemple de body : 
```json
{
  "name": "Classique"
}
```

> Attention le nom d'une pâte doit être unique . !

Cette requête renvoie un statut de réponse : 
- `HTTP 200 OK` : La pâte a été mise à jour dans la base de données et un ID lui reste inchangé.
- `HTTP 400 Bad Request` : Erreur lors de la mise à jour de la pâte, Par exemple une pate portant le même nom existe déjà.
- `HTTP 404 Not Found` : Aucune pâte ayant cet ID n'a été trouvée dans la base de données.

## Requêtes DELETE

```bash
DELETE /pates/{id}
```

Paramètres :
- `{id}` : L'ID de la pâte à supprimer

Cette requête renvoie un statut de réponse : 
- `HTTP 200 OK` : La pâte a été supprimée de la base de données.
- `HTTP 400 Bad Request` : Erreur lors de la suppression de la pâte, exemple l'ID n'est pas un nombre . 
- `HTTP 404 Not Found` : Aucune pâte ayant cet ID n'a été trouvée dans la base de données.





