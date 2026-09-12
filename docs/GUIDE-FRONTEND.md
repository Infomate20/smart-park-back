# Guide d'intégration front-end — Smart Park

Comportement du back-end, point par point, pour aligner le front dessus.

Tout ce qui suit a été relevé dans le code, pas reconstitué de mémoire. Quand un
comportement est surprenant ou incohérent, c'est signalé comme tel plutôt que
lissé : mieux vaut coder contre la réalité que contre l'intention.

- **Base URL** : `http://localhost:8082`
- **Swagger** : `http://localhost:8082/swagger-ui.html` — fait foi pour les
  payloads exhaustifs champ par champ ; ce document couvre le comportement.

---

## 1. À corriger en priorité côté front

Ces points **cassent** le front actuel ou le laissent à côté de fonctionnalités
existantes.

| # | Changement | Impact | Action |
|---|---|---|---|
| 1 | `POST /api/categories` exige désormais un champ `code` | **400** à chaque création de catégorie | Ajouter le champ au formulaire (2–10 caractères alphanumériques) |
| 2 | `numeroSerie` d'une immobilisation est devenu **optionnel** | Le front bloque probablement encore la saisie | Retirer la contrainte « obligatoire » |
| 3 | `GET /api/vehicules` renvoie une **`Page`**, pas un tableau | `.map()` direct sur la réponse échoue | Lire `response.content` |
| 4 | Nouveau : satellite véhicule | Fonctionnalité absente du front | §7.6 |
| 5 | Nouveau : module d'amortissement | Fonctionnalité absente du front | §7.7 |
| 6 | Catégories : `dureeAmortissementMois` et `methodeAmortissement` | Champs non exposés | Ajouter au formulaire de catégorie |

---

## 2. Authentification

### 2.1 Le piège principal

`LoginRequest.identifier` **est résolu uniquement par adresse e-mail**, alors que
le champ s'appelle `identifier` et que le service en amont s'appelle
`usernameOrEmailOrPhone`.

```java
utilisateurRepository.findByEmail(usernameOrEmailOrPhone)   // AuthServiceImpl:44
```

Envoyer un nom d'utilisateur ou un téléphone donne un échec d'authentification.
**Le champ de connexion doit être étiqueté « E-mail » et validé comme tel.**

À noter, pour ne pas s'y perdre : ailleurs dans l'application, l'identité est
résolue par `nomUtilisateur` (c'est le `subject` du JWT). Seul le login est
sur l'e-mail.

### 2.2 Le parcours, en trois appels

La connexion **ne renvoie pas de jeton**. Il faut trois requêtes.

```
1. POST /api/auth/login      { identifier, password }     -> { message, userId }
2. POST /api/auth/send-otp   { userId }                   -> { message }   (mail envoyé)
3. POST /api/auth/verify-otp { userId, code }             -> AuthResponse  (jetons)
```

`AuthResponse` :

```json
{
  "accessToken": "...",
  "refreshToken": "...",
  "tokenType": "Bearer",
  "userId": 1,
  "nomUtilisateur": "admin",
  "nom": null,
  "prenom": "Admin",
  "email": "...",
  "role": "ADMIN",
  "premiereConnexion": false
}
```

Entre l'étape 1 et l'étape 3, conserver `userId` en mémoire de composant — pas en
`localStorage` : ce n'est pas un jeton, mais c'est une session de connexion en
cours.

### 2.3 Durées et rafraîchissement

- Jeton d'accès : **1 heure** (`3600000` ms)
- Jeton de rafraîchissement : **24 heures** (`86400000` ms)
- `POST /api/auth/refresh` avec `{ refreshToken }` → nouvel `AuthResponse`

### 2.4 Déconnexion

`POST /api/auth/logout` **ne révoque rien** : il n'existe pas de liste noire de
jetons. Un jeton reste valide jusqu'à son expiration naturelle, même après
déconnexion.

Conséquence : le front doit effacer les jetons localement, et ne pas considérer
la déconnexion comme une garantie de sécurité côté serveur.

### 2.5 Verrouillage de compte

**5 échecs** consécutifs verrouillent le compte (`MAX_FAILED_ATTEMPTS = 5`).
Un login réussi remet le compteur à zéro. Le déverrouillage est une action ADMIN
(`PUT /api/utilisateurs/{id}/deverrouiller`).

Messages distincts à restituer à l'utilisateur :
- compte verrouillé → « trop de tentatives de connexion échouées »
- compte désactivé (`actif = false`) → « compte désactivé »

### 2.6 Première connexion

`AuthResponse.premiereConnexion = true` doit rediriger vers un changement de mot
de passe, puis appeler
`PUT /api/utilisateurs/{id}/marquer-premiere-connexion-terminee`.

### 2.7 En-tête

```
Authorization: Bearer <accessToken>
```

Seuls `Authorization` et `Content-Type` sont acceptés en en-têtes CORS. Tout
en-tête personnalisé (`X-Requested-With`, en-tête de traçage…) sera **rejeté au
preflight**.

---

## 3. CORS — état réel

`SecurityConfig` est la **seule** autorité en matière de CORS : plus aucun
contrôleur ne porte de `@CrossOrigin`. L'origine autorisée par défaut est
`http://localhost:4200`, et se configure par la variable d'environnement
`CORS_ALLOWED_ORIGINS` (liste séparée par des virgules) :

```
CORS_ALLOWED_ORIGINS=https://parc.example.com,https://admin.example.com
```

Les requêtes portant des identifiants (`allowCredentials`), la valeur `*` est
interdite : les origines doivent être énumérées. Ne construisez donc rien qui
suppose une API ouverte à toutes les origines.

---

## 4. Conventions transverses

### 4.1 Pagination

Les endpoints paginés acceptent les paramètres Spring standard :

```
?page=0&size=20&sort=designation,asc
```

Réponse : enveloppe Spring `Page`.

```json
{
  "content": [ ... ],
  "totalElements": 143,
  "totalPages": 8,
  "number": 0,
  "size": 20,
  "first": true,
  "last": false,
  "empty": false
}
```

**Tailles par défaut** : 10 pour la plupart des ressources, **20** pour
`/api/vehicules` et `/api/amortissements/synthese`.

Attention : de nombreuses ressources exposent **à la fois** une variante liste
et une variante paginée. Une réponse tableau et une réponse `Page` coexistent
donc sur la même ressource — vérifier l'endroit exact dans les tableaux du §7.

### 4.2 Format d'erreur

Toutes les erreurs métier passent par `GlobalExceptionHandler` et partagent la
même enveloppe :

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Une catégorie avec le code VEH existe déjà",
  "path": "/api/categories",
  "timestamp": "2026-08-05 22:41:03",
  "validationErrors": { "code": "Le code de la catégorie est obligatoire" },
  "errors": null
}
```

- `message` est rédigé en français et **affichable tel quel** à l'utilisateur.
- `validationErrors` n'est renseigné que sur les erreurs de validation de
  payload : `{ nomDuChamp: message }`, à brancher directement sur les erreurs
  de formulaire.

Correspondance des statuts :

| Statut | Origine | Signification pour le front |
|---|---|---|
| 400 | `BusinessException`, `ValidationException`, payload invalide | Règle métier violée ou saisie incorrecte — afficher `message` |
| 401 | `UnauthorizedException` | Jeton absent, invalide ou expiré — tenter `refresh`, sinon déconnecter |
| 403 | `ForbiddenException`, `AccessDeniedException` | Rôle insuffisant — masquer l'action plutôt que de la laisser échouer |
| 404 | `ResourceNotFoundException` | Ressource absente |
| 405 | Méthode HTTP non supportée | Erreur d'appel côté front |
| 500 | Panne réelle | Message générique, et remonter au back |

Toutes les règles métier passent désormais par les exceptions du domaine :
**un 500 signale une vraie panne**, jamais une règle violée. Un refus métier est
toujours un 400, un refus d'habilitation toujours un 403, avec un `message`
exploitable.

### 4.3 Dates

- `LocalDate` → `"2026-08-05"`
- `LocalDateTime` → format ISO, sauf `timestamp` des erreurs qui est en
  `"yyyy-MM-dd HH:mm:ss"`
- Paramètres de requête de type date : format ISO (`?date=2026-08-05`)

### 4.4 Énumérations et libellés

Les énumérations transitent par leur **nom technique** (`EN_SERVICE`), et
plusieurs réponses ajoutent un champ `...Libelle` déjà traduit
(`etatLibelle`, `typeCarburantLibelle`, `methodeAmortissementLibelle`).

**Utiliser ces libellés plutôt que de maintenir une table de traduction dans le
front** — ils viennent de l'énumération elle-même et ne se désynchroniseront pas.

Endpoints de référentiel, pour alimenter les listes déroulantes sans coder les
valeurs en dur :

```
GET /api/immobilisations/etats-avec-libelles
GET /api/vehicules/carburants
GET /api/utilisateurs/roles-avec-libelles
```

Valeurs actuelles :

| Énumération | Valeurs |
|---|---|
| `EtatImmobilisation` | `EN_SERVICE`, `EN_PANNE`, `EN_REPARATION`, `MISE_AU_REBUS` |
| `Role` | `ADMIN`, `TECHNICIEN`, `AGENT` |
| `TypeTransaction` | `TRANSFERT`, `AFFECTATION`, `DESAFFECTATION` |
| `EtatTransaction` | `EN_ATTENTE`, `VALIDEE`, `REJETEE` |
| `EtatTicket` | `NOUVEAU`, `VALIDE`, `REJETE`, `EN_COURS_DE_TRAITEMENT`, `RESOLU` |
| `TypeIntervention` | `MAINTENANCE_PREVENTIVE`, `MAINTENANCE_CORRECTIVE`, `INSTALLATION`, `DESINSTALLATION` |
| `EtatIntervention` | `EN_COURS`, `PLANIFIER`, `TERMINER`, `ANNULER` |
| `TypeCarburant` | `ESSENCE`, `DIESEL`, `HYBRIDE`, `ELECTRIQUE`, `GPL` |
| `MethodeAmortissement` | `LINEAIRE`, `DEGRESSIF`, `NON_AMORTISSABLE` |

### 4.5 Contrôles de disponibilité

Plusieurs ressources exposent des endpoints de validation à brancher sur la
saisie (avec anti-rebond), tous de la forme :

```
GET /api/immobilisations/validate/numero-serie?numeroSerie=X&excludeId=12
-> { "available": true }
```

`excludeId` sert en modification : il exclut l'enregistrement courant de la
recherche de doublon. **L'oublier fait échouer toute modification** qui ne change
pas le champ contrôlé.

Disponibles sur : `immobilisations` (numéro de série, code), `utilisateurs`
(nom d'utilisateur, e-mail, matricule), `agences` (code, nom, e-mail).

---

## 5. Rôles et habilitations

Trois rôles : `ADMIN`, `TECHNICIEN`, `AGENT`.

Règle générale : **lecture ouverte à tout utilisateur authentifié, écriture
restreinte, suppression réservée à ADMIN.**

`UtilisateurController` fait exception : il est **ADMIN au niveau de la classe**.
Tout y est donc réservé aux administrateurs, sauf trois endpoints qu'un
utilisateur peut appeler sur son propre compte :

- `GET /api/utilisateurs/{id}` (le sien)
- `PUT /api/utilisateurs/{id}/changer-mot-de-passe`
- `PUT /api/utilisateurs/{id}/marquer-premiere-connexion-terminee`

Le front doit **masquer les actions non autorisées** plutôt que de les afficher
et laisser survenir un 403 : les rôles sont connus dès `AuthResponse.role`.

---

## 6. Règles métier à respecter côté front

### 6.1 Immobilisations

- `numeroSerie` est **optionnel**. Absent, le code est dérivé de la catégorie :
  `{AGENCE}-{CAT}-{horodatage}` ou `STOCK-{CAT}-{horodatage}`. Présent, l'ancien
  format est conservé : `{AGENCE}-{numeroSerie}` ou
  `STOCK-{numeroSerie}-{horodatage}`.
- `codeImmobilisation` est **généré par le back**, jamais saisi. Il est
  **régénéré** quand le numéro de série ou l'agence change — donc ne jamais le
  mettre en cache comme identifiant stable ; l'identifiant stable est `id`.
- `valeurActuelle` est une **saisie manuelle libre** (valeur de marché,
  réévaluation). Ce n'est **pas** la valeur nette comptable : celle-ci se
  demande au module d'amortissement (§7.7). Ne pas confondre les deux à l'écran.
- La réponse porte des champs calculés prêts à afficher : `sousGarantie`,
  `ageEnMois`, `nombreInterventions`, `nombreTransactions`, `categorieNom`,
  `agenceNom`.

### 6.2 Catégories

- `code` : **obligatoire**, 2 à 10 caractères alphanumériques, sans espace ni
  accent. Normalisé en majuscules par le back — afficher la valeur renvoyée,
  pas celle saisie.
- Unicité sur le nom **et** sur le code, avec des messages d'erreur distincts.
- `dureeAmortissementMois` : 1 à 600.
- Passer `methodeAmortissement` à `NON_AMORTISSABLE` **efface**
  `dureeAmortissementMois` côté serveur. Le formulaire doit désactiver le champ
  durée dans ce cas, pour ne pas laisser croire à une saisie conservée.
- Catégories de référence créées au démarrage : `INFO` (36 mois), `MOB`
  (120 mois), `VEH` (60 mois), toutes en linéaire.

### 6.3 Transactions

Le workflow est : `EN_ATTENTE` → `VALIDEE` | `REJETEE`.

- **Une seule transaction `EN_ATTENTE` par immobilisation.** Vérifier avant de
  proposer le bouton :
  `GET /api/transactions/immobilisation/{id}/en-attente`
- Validation **réservée à ADMIN**, et re-contrôlée côté serveur contre
  `validationDto.validateurId` — pas seulement par le rôle.
- L'annulation réutilise l'état `REJETEE` avec un motif généré. Il n'existe pas
  d'état « ANNULEE » : à l'écran, distinguer les deux cas par le `motifRejet`.
- Le rejet **exige** un `motifRejet`.

**Agences à fournir selon le type** — n'envoyer que ce que le type exige :

| Type | `agenceSourceId` | `agenceDestinationId` |
|---|---|---|
| `TRANSFERT` | obligatoire | obligatoire, **différente** de la source |
| `AFFECTATION` | facultative | obligatoire |
| `DESAFFECTATION` | obligatoire | ignorée |

Le message d'erreur renvoyé est adapté au type (« Pour une affectation, l'agence
destination est obligatoire »), donc affichable tel quel.

### 6.4 Tickets

- Créables par **tout utilisateur authentifié** (`immobilisationId` +
  `descriptionProbleme`).
- Validation ADMIN. **Valider passe toujours l'immobilisation à `EN_PANNE`.**
- La validation peut créer une intervention liée ; le ticket passe alors à
  `EN_COURS_DE_TRAITEMENT` au lieu de `VALIDE`. Le front doit gérer les deux
  issues d'une même action.
- Le rejet exige un `motifRejet`.

### 6.5 Véhicules

- `PUT /api/immobilisations/{id}/vehicule` est un **remplacement complet**
  (upsert idempotent) : un champ omis est effacé, pas conservé. Toujours
  renvoyer l'objet entier.
- L'immatriculation est **canonisée** : majuscules, séparateurs retirés.
  `AB-123-CD`, `ab 123 cd` et `AB123CD` sont la même plaque et entrent en
  collision. Afficher la valeur renvoyée par le back.
- Le **numéro de châssis (VIN) n'est pas** dans le satellite : c'est le numéro
  de série constructeur, il se saisit dans `Immobilisation.numeroSerie`.
- Aucun contrôle n'impose que le bien soit de catégorie `VEH` — c'est délibéré,
  les codes de catégorie étant modifiables. Au front de proposer l'onglet
  véhicule au bon moment.
- `controleTechniqueAJour` et `assuranceAJour` valent **`null`** quand la date
  correspondante n'est pas renseignée. Trois états à l'affichage : à jour,
  expiré, non renseigné. Ne pas traiter `null` comme `false`.

### 6.6 Amortissement

- Tout est **calculé à la demande**, rien n'est stocké.
- Quand un plan est impossible, la réponse n'échoue pas : `amortissable: false`
  et `motifNonAmortissable` explique pourquoi (catégorie non amortissable,
  durée absente, prix d'acquisition manquant, aucune date exploitable).
  **Afficher ce motif** — c'est actionnable par l'utilisateur.
- Le plan détaillé (`lignes`) n'est renseigné que sur l'endpoint d'un bien.
  La synthèse du parc renvoie `lignes: null` — c'est voulu.

---

## 7. Référence des endpoints

Légende des habilitations : **A** = ADMIN, **T** = TECHNICIEN, **G** = AGENT,
**auth** = tout utilisateur authentifié.

### 7.1 Authentification — `/api/auth` (public)

| Méthode | Chemin | Corps | Réponse |
|---|---|---|---|
| POST | `/login` | `{ identifier, password }` | `{ message, userId }` |
| POST | `/send-otp` | `{ userId }` | `{ message }` |
| POST | `/verify-otp` | `{ userId, code }` | `AuthResponse` |
| POST | `/refresh` | `{ refreshToken }` | `AuthResponse` |
| POST | `/logout` | — (en-tête) | `{ message }` |
| GET | `/me` | — | `Map` de l'utilisateur courant |

### 7.2 Immobilisations — `/api/immobilisations`

| Méthode | Chemin | Rôle | Note |
|---|---|---|---|
| POST | `/` | A, T | |
| GET | `/{id}` | auth | |
| GET | `/code/{code}` | auth | |
| GET | `/` | auth | Liste **non paginée** |
| GET | `/actives` | auth | Liste non paginée |
| GET | `/paginated` | auth | `Page` — filtres `searchTerm`, `actif` |
| PUT | `/{id}` | A, T | |
| DELETE | `/{id}` | A | Refusé si interventions/transactions liées |
| GET | `/search` | auth | `Page` |
| GET | `/search-light` | auth | Plafonné à **10** résultats — autocomplétion |
| GET | `/categorie/{id}` · `/agence/{id}` · `/sans-agence` · `/etat/{etat}` | auth | `Page` |
| GET | `/periode-acquisition` · `/plage-prix` | auth | |
| PUT | `/{id}/activer` · `/{id}/desactiver` · `/{id}/changer-etat` | A, T | |
| PUT | `/{id}/transferer-agence` · `/{id}/retirer-agence` | A | Régénère le code |
| GET | `/{id}/interventions` | A, T | Historique |
| GET | `/{id}/transactions` | A, G | Historique |
| GET | `/{id}/etiquette` | A, G | **PDF** — étiquette 80×50 mm, code-barres |
| GET | `/etats` · `/etats-avec-libelles` | auth | Référentiel |
| GET | `/validate/numero-serie` · `/validate/code` | A, T | `{ available }` |
| GET | `/stats/dashboard` et `/stats/...` | auth | |

### 7.3 Catégories — `/api/categories`

| Méthode | Chemin | Rôle |
|---|---|---|
| POST | `/` | A |
| GET | `/{id}` · `/` · `/actives` | auth |
| GET | `/paginated` · `/search` | auth (`Page`) |
| PUT | `/{id}` | A |
| DELETE | `/{id}` | A (refusé si immobilisations liées) |
| PUT | `/{id}/activer` · `/{id}/desactiver` | A |

### 7.4 Agences — `/api/agences`

CRUD complet (écriture ADMIN, lecture authentifiée), plus recherche paginée,
filtres par ville et pays, validations d'unicité, statistiques, export CSV,
actions par lot (`/batch/activer`, `/batch/desactiver`) et recherche avancée
(`POST /search/advanced`).

### 7.5 Utilisateurs — `/api/utilisateurs`

**ADMIN par défaut au niveau de la classe** (voir §5). CRUD, pagination,
filtres par rôle/agence/état, activation, verrouillage/déverrouillage,
changement et réinitialisation de mot de passe, transfert d'agence, validations
d'unicité, statistiques, export CSV, actions par lot.

### 7.6 Véhicules

| Méthode | Chemin | Rôle | Note |
|---|---|---|---|
| PUT | `/api/immobilisations/{id}/vehicule` | A, T | Upsert — remplacement complet |
| GET | `/api/immobilisations/{id}/vehicule` | auth | 404 si absent |
| DELETE | `/api/immobilisations/{id}/vehicule` | A | |
| GET | `/api/vehicules` | auth | **`Page`**, taille 20 |
| GET | `/api/vehicules/immatriculation/{immat}` | auth | |
| GET | `/api/vehicules/carburants` | auth | Référentiel |

Corps de `PUT` : `immatriculation` (obligatoire), `kilometrage`,
`typeCarburant`, `puissanceFiscale` (1–100), `nombrePlaces` (1–100),
`dateDernierControleTechnique`, `dateProchainControleTechnique`,
`compagnieAssurance`, `numeroPoliceAssurance`, `dateExpirationAssurance`,
`observations`.

### 7.7 Amortissement

| Méthode | Chemin | Rôle | Note |
|---|---|---|---|
| GET | `/api/immobilisations/{id}/amortissement` | auth | Plan complet avec `lignes` |
| GET | `/api/immobilisations/{id}/amortissement/situation?date=` | auth | Cumul + VNC, sans `lignes` |
| GET | `/api/amortissements/synthese?date=&page=&size=` | A, T | `Page`, taille 20, `lignes: null` |

Chaque `ligne` : `exercice`, `valeurDebut`, `taux` (en %), `dotation`,
`cumulAmortissements`, `valeurNetteComptable`.

### 7.8 Transactions — `/api/transactions`

Création et consultation ouvertes aux trois rôles ; validation
(`POST /{id}/valider`), suppression, statistiques et consultation par validateur
réservées à ADMIN. Nombreux filtres paginés : par immobilisation, demandeur,
état, type, agence source/destination, période, en attente, recherche.
`GET /all` est la variante non paginée, réservée à ADMIN.

### 7.9 Interventions — `/api/interventions`

Création et modification A/T, suppression A, lecture ouverte aux trois rôles
sur les consultations par immobilisation et agence. Transitions dédiées :
`PUT /{id}/commencer`, `PUT /{id}/terminer`. `GET /all` est la variante non
paginée (A/T).

### 7.10 Tickets — `/api/tickets`

| Méthode | Chemin | Rôle |
|---|---|---|
| POST | `/` | auth |
| GET | `/` · `/{id}` | auth |
| POST | `/{id}/valider` | A |

### 7.11 Rapports — `/api/reports` (ADMIN)

`POST /interventions/preview`, `/interventions/signed`,
`/transactions/preview`, `/transactions/signed`.

Réponses **PDF binaires** — traiter en `blob`, pas en JSON. Les variantes
`/signed` **revérifient le mot de passe de l'appelant** avant de signer : prévoir
une saisie de mot de passe dans le parcours. Les variantes `preview` produisent
un document tamponné « aperçu ».

### 7.12 Assistant IA — `/api/ai/query` (ADMIN)

Question en langage naturel traduite en SQL puis exécutée. Réponse : lignes sous
forme de tableau de `Map`, dont les colonnes varient selon la question — le front
doit s'adapter dynamiquement aux clés reçues.

---

## 8. Pièges connus, en résumé

À garder sous les yeux pendant l'intégration :

1. **Login par e-mail uniquement**, malgré le nom du champ (§2.1).
2. **Trois appels** pour se connecter, pas un (§2.2).
3. **La déconnexion ne révoque pas le jeton** (§2.4).
4. **`excludeId` obligatoire** sur les validations d'unicité en modification (§4.5).
5. **`codeImmobilisation` change** lors d'un transfert d'agence (§6.1).
6. **`valeurActuelle` ≠ valeur nette comptable** (§6.1, §6.6).
7. **`controleTechniqueAJour` peut être `null`** — trois états, pas deux (§6.5).
8. **Le `PUT` véhicule efface les champs omis** (§6.5).
9. **Liste ou `Page` selon l'endpoint**, parfois sur la même ressource (§4.1).
10. **Les agences de transaction dépendent du type** (§6.3).
