# Candidat Service API

## Description

Candidat Service est un microservice pour la gestion des candidats. Il permet de gérer la création, la récupération, la mise à jour, la suppression (CRUD) des candidats, ainsi que la gestion des fichiers CV. Ce service implémente une architecture hexagonale, en se concentrant sur les principes SOLID, le clean code et les meilleures pratiques en matière de développement Java et Spring Boot.

## Fonctionnalités

- Gestion des candidats : CRUD
- Pagination et filtres sur la liste des candidats
- Upload et téléchargement des CV des candidats
- Interface RESTful avec Swagger OpenAPI pour la documentation

## Technologies utilisées

- Java 21
- Spring Boot 3.9.9
- PostgreSQL (base de données relationnelle)
- Spring Data JPA
- Spring WebFlux pour l'API réactive
- Lombok pour la gestion des getters, setters, et autres boilerplates
- MapStruct pour la transformation des DTO
- Swagger/OpenAPI pour la documentation de l'API
- JUnit 5, Mockito pour les test

## Architecture Hexagonale

Pour ce microservice, j'ai adopté une architecture hexagonale.

## Implémentation du Contract First

Le contrat OpenAPI est défini dans openapi/candidat-service.yaml. L'API est générée à partir de ce fichier grâce au plugin openapi-generator-maven-plugin.

## Contrat OpenAPI

Le service utilise un contrat OpenAPI pour documenter les différentes opérations disponibles. Voici un résumé des endpoints définis dans le contrat Swagger :

### Endpoints

- **GET /candidats** : Récupère une liste paginée de candidats avec filtres.
- **POST /candidats** : Crée un nouveau candidat.
- **GET /candidats/{candidatId}** : Récupère un candidat spécifique par son ID.
- **PUT /candidats/{candidatId}** : Met à jour un candidat existant.
- **DELETE /candidats/{candidatId}** : Supprime un candidat.
- **POST /candidats/{candidatId}/cv** : Upload un fichier CV pour un candidat.
- **GET /candidats/{candidatId}/cv** : Télécharge le fichier CV d'un candidat.

### Exemple de Swagger

```yaml
openapi: 3.0.3
info:
  title: Candidat Service API
  description: API pour la gestion des candidats avec pagination, recherche et gestion des fichiers CV.
  version: 1.2.0
servers:
  - url: http://localhost:8082
    description: Serveur local
