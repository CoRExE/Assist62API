# Documentation de l'API Assist62

## 1. Vue d'ensemble du projet

Assist62 est une API RESTful conçue pour une application de gestion d'assistance. Elle permet de guider les agents à travers des arbres de décision pour résoudre des problèmes, tout en fournissant un système d'actualités et de notifications.

### Technologies

- **Framework :** Spring Boot 3.5.4
- **Langage :** Java 21
- **Gestion de projet :** Gradle
- **Base de données :** PostgreSQL
- **Sécurité :** Spring Security avec JWT

### Architecture

Le projet suit une architecture en couches classique :
- **Controllers :** Gèrent les requêtes HTTP et exposent les endpoints.
- **Services :** Contiennent la logique métier.
- **Repositories :** Gèrent l'accès aux données via Spring Data JPA.
- **Models :** Définissent les entités de la base de données.
- **DTOs :** Objets de transfert de données pour les requêtes et réponses de l'API.
- **Mappers :** Convertissent les entités en DTOs.
- **Factories & Seeders :** Permettent de peupler la base de données avec des données de test.

---

## 2. Authentification

L'API utilise l'authentification par token JWT. Pour accéder aux endpoints protégés, un utilisateur doit d'abord s'authentifier.

### Connexion

- **Endpoint :** `POST /api/auth/login`
- **Description :** Authentifie un utilisateur avec son nom (`name`) et son mot de passe.
- **Requête :**
  ```json
  {
    "name": "username",
    "password": "userpassword"
  }
  ```
- **Réponse (Succès) :**
  ```json
  {
    "token": "ey..."
  }
  ```

Une fois le token obtenu, il doit être inclus dans l'en-tête `Authorization` de chaque requête vers un endpoint protégé : `Authorization: Bearer <token>`.

---

## 3. Modèles de Données

### User
Représente un utilisateur du système.
- `id` (Long) : Identifiant unique.
- `name` (String) : Nom d'utilisateur (unique).
- `email` (String) : Adresse e-mail (unique).
- `password` (String) : Mot de passe (stocké de manière chiffrée).
- `role` (Enum) : Rôle de l'utilisateur (`USER`, `MODO`, `ADMIN`).
- `subscriptions` (Set<Category>) : Catégories auxquelles l'utilisateur est abonné.
- `favorites` (Set<Category>) : Catégories favorites de l'utilisateur.

### Category
Représente une catégorie de problèmes. Les catégories sont hiérarchiques.
- `id` (Long) : Identifiant unique.
- `name` (String) : Nom de la catégorie (unique).
- `parent` (Category) : Catégorie parente (relation hiérarchique).
- `children` (List<Category>) : Liste des sous-catégories.

### Problem
Représente un problème ou une procédure à résoudre.
- `id` (Long) : Identifiant unique.
- `title` (String) : Titre du problème.
- `description` (String) : Description détaillée.
- `category` (Category) : Catégorie à laquelle le problème est associé.
- `firstStep` (DecisionStep) : Première étape de l'arbre de décision pour résoudre ce problème.

### DecisionStep
Représente une étape dans un arbre de décision.
- `id` (Long) : Identifiant unique.
- `text` (String) : Texte ou question de l'étape.
- `imageUrl` (String) : URL d'une image illustrative.
- `isFinal` (boolean) : Indique si c'est une étape finale.
- `choices` (List<Choice>) : Liste des choix possibles à cette étape.

### Choice (Abstrait)
Représente un choix possible à une `DecisionStep`. C'est une classe de base pour des choix plus spécifiques.
- `id` (Long) : Identifiant unique.
- `text` (String) : Texte du choix.
- `decisionStep` (DecisionStep) : Étape à laquelle ce choix appartient.

#### Sous-types de Choice :
- **NavigationChoice :** Mène à une autre `DecisionStep`.
  - `nextStep` (DecisionStep) : La prochaine étape.
- **UrlChoice :** Redirige vers une URL.
  - `url` (String) : L'URL de destination.
- **FinalChoice :** Termine le parcours.
  - `conclusionText` (String) : Texte de conclusion.

### News
Représente une actualité, une alerte ou une mise à jour.
- `id` (Long) : Identifiant unique.
- `title` (String) : Titre de l'actualité.
- `content` (String) : Contenu de l'actualité.
- `type` (Enum) : Type d'actualité (`INFO`, `ALERT`, `UPDATE`).
- `creationDate` (LocalDateTime) : Date de création.
- `author` (User) : Auteur de l'actualité.
- `category` (Category) : Catégorie concernée (optionnel).

### Notification
Représente une notification envoyée à un utilisateur.
- `id` (Long) : Identifiant unique.
- `message` (String) : Contenu de la notification.
- `creationDate` (LocalDateTime) : Date de création.
- `isRead` (boolean) : Statut de lecture.
- `user` (User) : Utilisateur destinataire.
- `news` (News) : Actualité qui a déclenché la notification.

### Image
Représente une image téléversée.
- `id` (Long) : Identifiant unique.
- `filename` (String) : Nom du fichier sur le serveur.
- `url` (String) : URL d'accès à l'image.
- `uploadedAt` (LocalDateTime) : Date de téléversement.

---

## 4. Endpoints de l'API

### Authentification (`/api/auth`)
| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| `POST` | `/login` | Authentifie un utilisateur et retourne un token JWT. | Public |

### Utilisateurs (`/api/user`)
| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| `GET` | `/all` | Récupère la liste de tous les utilisateurs. | `ADMIN` |
| `GET` | `/{id}` | Récupère un utilisateur par son ID. | `ADMIN` ou l'utilisateur lui-même |
| `GET` | `/email/{email}` | Récupère un utilisateur par son email. | `ADMIN` |
| `POST` | `/` | Crée un nouvel utilisateur. | `ADMIN` |
| `PUT` | `/{id}` | Met à jour un utilisateur. | `ADMIN` ou l'utilisateur lui-même |
| `DELETE` | `/{id}` | Supprime un utilisateur. | `ADMIN` |

### Favoris (`/api/user/{userId}/favorites`)
| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| `GET` | `/` | Récupère les favoris d'un utilisateur. | `ADMIN` ou l'utilisateur lui-même |
| `POST` | `/{categoryId}` | Ajoute une catégorie aux favoris. | `ADMIN` ou l'utilisateur lui-même |
| `DELETE` | `/{categoryId}` | Retire une catégorie des favoris. | `ADMIN` ou l'utilisateur lui-même |

### Abonnements (`/api/user/{userId}/subscriptions`)
| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| `GET` | `/` | Récupère les abonnements d'un utilisateur. | `ADMIN` ou l'utilisateur lui-même |
| `POST` | `/{categoryId}` | Abonne l'utilisateur à une catégorie. | `ADMIN` ou l'utilisateur lui-même |
| `DELETE` | `/{categoryId}` | Désabonne l'utilisateur d'une catégorie. | `ADMIN` ou l'utilisateur lui-même |

### Catégories (`/api/category`)
| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| `GET` | `/root` | Récupère les catégories racines. | Public |
| `GET` | `/all` | Récupère toutes les catégories. | Public |
| `GET` | `/{id}` | Récupère une catégorie par son ID. | Public |
| `GET` | `/name/{name}` | Récupère une catégorie par son nom. | Public |
| `GET` | `/{id}/children` | Récupère les enfants d'une catégorie. | Public |
| `POST` | `/` | Crée une nouvelle catégorie. | `ADMIN`, `MODO` |
| `PUT` | `/{id}` | Met à jour une catégorie. | `ADMIN`, `MODO` |
| `DELETE` | `/{id}` | Supprime une catégorie (si sans enfants). | `ADMIN`, `MODO` |
| `DELETE` | `/{id}/cascade` | Supprime une catégorie et ses enfants. | `ADMIN`, `MODO` |

### Problèmes (`/api/problem`)
| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| `GET` | `/all` | Récupère tous les problèmes. | Public |
| `GET` | `/{id}` | Récupère un problème par son ID (avec son arbre de décision). | Public |
| `GET` | `/category/{categoryId}` | Récupère les problèmes d'une catégorie. | Public |
| `GET` | `/title/{title}` | Récupère les problèmes par titre. | Public |
| `POST` | `/` | Crée un nouveau problème. | `ADMIN`, `MODO` |
| `PUT` | `/{id}` | Met à jour un problème. | `ADMIN`, `MODO` |
| `DELETE` | `/{id}` | Supprime un problème. | `ADMIN`, `MODO` |

### Étapes de Décision (`/api/step`)
| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| `GET` | `/{id}` | Récupère une étape de décision par son ID. | Public |
| `POST` | `/` | Crée une nouvelle étape. | `ADMIN`, `MODO` |
| `PUT` | `/{id}` | Met à jour une étape. | `ADMIN`, `MODO` |
| `DELETE` | `/{id}` | Supprime une étape. | `ADMIN`, `MODO` |

### Choix (`/api/choice`)
| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| `GET` | `/{id}` | Récupère un choix par son ID. | Public |
| `GET` | `/type/{type}` | Récupère les choix par type (`NAVIGATION`, `URL`, `FINAL`). | Public |
| `POST` | `/` | Crée un nouveau choix (polymorphique). | `ADMIN`, `MODO` |
| `PUT` | `/{id}` | Met à jour un choix. | `ADMIN`, `MODO` |
| `DELETE` | `/{id}` | Supprime un choix. | `ADMIN`, `MODO` |

### Actualités (`/api/news`)
| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| `GET` | `/all` | Récupère toutes les actualités. | Public |
| `GET` | `/{id}` | Récupère une actualité par son ID. | Public |
| `POST` | `/` | Crée une nouvelle actualité. | `ADMIN`, `MODO` |
| `PUT` | `/{id}` | Met à jour une actualité. | `ADMIN`, `MODO` |
| `DELETE` | `/{id}` | Supprime une actualité. | `ADMIN`, `MODO` |

### Notifications (`/api`)
| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| `GET` | `/user/{userId}/notifications` | Récupère les notifications d'un utilisateur. | `ADMIN` ou l'utilisateur lui-même |
| `POST` | `/notifications/{notificationId}/read` | Marque une notification comme lue. | `ADMIN` ou le propriétaire de la notification |
| `DELETE` | `/notifications/{notificationId}` | Supprime une notification. | `ADMIN` ou le propriétaire de la notification |

### Images (`/api/images`)
| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| `GET` | `/` | Liste les URL de toutes les images téléversées. | Public |
| `GET` | `/{filename}` | Affiche une image. | Public |
| `POST` | `/upload` | Téléverse une nouvelle image. | `ADMIN`, `MODO` |
| `DELETE` | `/{filename}` | Supprime une image. | `ADMIN`, `MODO` |
