# API Token

| URI                | Opération |    Requête    | Réponse                                                          |
|:-------------------|:---------:|:-------------:|:-----------------------------------------------------------------|
| /users/token       |    GET    |               | récupère le token d'identification (T1) ou 404 ou 500            |

# Corps des requêtes

## T1

Un token est permet de vérifier que l'utilisateur est bien identifié sur le site.

Voici sa représentation JSON :

```JSON
A compléter
```

# Exemples

## Récupérer le token d'authentification de l'utilisateur :

```shell
GET /users/token 
```

Requête vers le serveur

```shell
GET /users/token 
```
Réponse du serveur

```JSON
A compléter
```

Code de status HTTP

| Status                    | Description                  |
|:--------------------------|:-----------------------------|
| 200 OK                    | Le token a bien été retourné |
| 404 NOT FOUND             | Le token n'existe pas        |
| 500 INTERNAL SERVOR ERROR | Erreur interne du serveur    |
