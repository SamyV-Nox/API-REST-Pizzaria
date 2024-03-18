[<- Retour](./../README.md)

# Documentation de l'API Token

Cette documentation décrit les différentes requêtes possibles pour interagir avec notre API dédiée aux tokens, ainsi que les retours attendus pour chaque demande.

| URI                         | Opération |     Requête     | Réponse                   | Code de retour                           |
|:----------------------------|:---------:|:---------------:|:--------------------------|------------------------------------------|
| /token                  |`GET`      |                 |Liste des commandes        |`HTTP 200 OK`, `HTTP 404 Not Found`       |

## Méthodes d'appel

La classe `APIToken` étend la classe abstraite `API` et définit six méthodes `doGet`, `doPatch`, `doPost`, `doDelete` et un constructeur par défaut. Ces méthodes correspondent aux méthodes HTTP GET, PATCH, POST et DELETE respectivement.

## Requêtes GET

### Obtenir les tokens

```bash
GET /tokens
```

Exemple de retour : 
```json
[
  {
    "user": "admin",
    "password": "admin"
  }
]
```

Cette requête renvoie un statut de réponse : 
- `HTTP 200 OK` Si des tokens existent dans la base de donnés
- `HTTP 404 Not Found` Si aucun token est enregistré dans la base de données

## Requêtes POST

### Ajouter un token (bientôt)

## Requêtes DELETE

### Supprimer un token (bientôt)

## Requêtes PATCH

### Modifier un token (bientôt)
