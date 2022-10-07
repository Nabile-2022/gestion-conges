# Gestion congés

## Installation

Installer les dépendences:

### Client
- Node.js
- npm

### Serveur
- Java
- Maven

```
pushd client
npm install
popd
```

## Utilisation

A partir de la racine du projet, éxécuter la commande `./start`, qui s'occupe de lancer le client et le serveur.

## Structure

La structure du projet est la suivante:

- server:
    - config: La configuration de Spring (la sécurité).
    - controller: L'accès à l'API, c'est par là que les requêtes passent, et elles sont traitées par les services, puis on retourne les résultat sous forme de DTO.
    - dto: Les objets exposés aux clients via l'API. En général, c'est une version légère des entités métier.
    - entities: Les objets métiers, qui appartiennent en général à un repository (une table de base de données).
    - enums: C'est les énumérations métier.
    - helpers: Des classes qui permettent de réutiliser du code.
    - repositories: C'est les tables de base de données, via JPA, donc ça contient optionnellement des méthodes de requête.
    - security: Implémentation de l'authentification Spring via un Salarie.
    - services: Traite les requêtes venant du contrôleur, en s'aidant des repositories. C'est là où repose le coeur du métier.
- client:
    - guards: Gère qui peut accéder à certains chemins de l'application (si l'utilisateur est connecté ou a les droits pour).
    - localisation: Traduction du texte de l'application.
    - models: Equivalent aux entités du côté serveur, c'est les objets métier.
    - services: Gère la communication avec le serveur, effectue les requêtes pour obtenir et transmettre les informations (ça touche aux contrôleurs du serveur).
    - site: Les pages et composants du site, en général ça injecte des services pour communiquer avec le serveur. Ces pages contiennent un module qui spécifie la route vers celle-ci, et un composant qui définit la logique (.ts) et le contenu de la page (.html/.css).
        - shared: Les componsants partagés entre les pages, y compris la barre de navigation et les validateurs de formulaire.
