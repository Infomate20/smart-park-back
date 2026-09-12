# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Layout

The git root is **not** the Maven project. From here:

- `smart-park/` — the Maven project (`pom.xml`, `mvnw`). Run all build commands from there.
- `graphify-out/` — knowledge graph artifacts (see graphify section below)
- `logs/`, `smart-park/logs/` — runtime logs (`ai-training.jsonl`)

Branches: `main`, `developement`, `feature/diego-gestion`. Remote is `Infomate20/smart-park-back`.

## Commands

Run from `smart-park/`:

```bash
./mvnw spring-boot:run              # start on http://localhost:8082
./mvnw clean package                # build target/smart-park-0.0.1-SNAPSHOT.jar
./mvnw test                         # run all tests
./mvnw test -Dtest=SmartParkApplicationTests#contextLoads   # single test
./mvnw compile                      # triggers Lombok annotation processing
```

Java 21, Spring Boot 3.5.4. Requires a running PostgreSQL at `localhost:5432/smartpark` (user `smartpark`) — every test is a `@SpringBootTest` and needs the DB to pass. Swagger UI is available via springdoc (`/swagger-ui.html`).

**The schema belongs to Flyway** (`src/main/resources/db/migration`), not to Hibernate: `ddl-auto` is `validate`, so the app refuses to start when the entities and the schema disagree. Changing an entity therefore means writing a migration — nothing is applied automatically any more.

- `V1__schema_initial.sql` is the full schema. It never runs on a pre-existing database: `baseline-on-migrate` with `baseline-version: 1` marks it as applied. It only executes on a virgin database.
- `V2__convergence_bases_pre_flyway.sql` brings databases built by the old `ddl-auto: update` up to V1's shape. Every statement is idempotent, so it is a no-op right after V1.
- `MigrationDepuisZeroTest` replays all migrations into a throwaway schema and drops it. It exists because V1 is never exercised on developer machines — without it, a defect in V1 would only surface on a fresh install.

`src/main/java/smartPark/smart_park/docker-compose.yml` is misnamed — it contains a **Dockerfile** (eclipse-temurin:17-jre, EXPOSE 8080). The sibling file actually named `Dockerfile` is empty. Neither is wired into the build, and both disagree with the pom (Java 21) and the configured port (8082).

## Domain

Asset/fixed-inventory management ("gestion des immobilisations") for a multi-agency IT park. All domain naming is **French** — keep new code consistent with it.

Core entities and how they interact:

- **`Immobilisation`** — the asset. Belongs to a `Categorie` and optionally an `Agence`. `etat`: EN_SERVICE / EN_PANNE / EN_REPARATION / MISE_AU_REBUS.
- **`Agence`** — branch/site. Its `code` drives asset code generation.
- **`Utilisateur`** — roles ADMIN / TECHNICIEN / AGENT, with lockout state (`tentativesConnexionEchouees`, `compteVerrouille` after 5 failures), `premiereConnexion`, `actif`.
- **`Transaction`** — a *movement request* (TRANSFERT / AFFECTATION / DESAFFECTATION), state EN_ATTENTE → VALIDEE | REJETEE. Each type has different agency requirements, enforced in `validerAgencesSelonType`. Validation is ADMIN-only and re-checked inside `TransactionServiceImpl.validerTransaction` against `validationDto.validateurId`, not just by `@PreAuthorize`. On validation, `mettreAJourImmobilisationApreValidation` mutates `immobilisation.agence` (destination, or `null` for DESAFFECTATION). Only one EN_ATTENTE transaction may exist per asset. Cancelling reuses the REJETEE state with a generated `motifRejet`.
- **`Ticket`** — a fault report by any authenticated user. ADMIN validates or rejects it (`TicketServiceImpl.validerOuRejeterTicket`); validating always sets the asset to EN_PANNE, and optionally spawns a linked `Intervention` (MAINTENANCE_CORRECTIVE / PLANIFIER), moving the ticket to EN_COURS_DE_TRAITEMENT instead of VALIDE. Rejection requires a `motifRejet`.
- **`Intervention`** — maintenance work on an asset by a technician.
- **`EtatTransaction`** is the live enum; **`StatusTransaction`** is a dead duplicate. `Logiciel` has an entity and repository but no service or controller.

Asset codes are minted by `CodeGenerationServiceImpl`: `{CODE_AGENCE}-{numeroSerie}` with an agency, `STOCK-{numeroSerie}-{yyyyMMddHHmm}` without. `ImmobilisationMapper.updateEntityFromDto` regenerates the code whenever `numeroSerie` or `agenceId` changes.

## Architecture

Package root `smartPark.smart_park` (group is `smartPark`, package segment `smart_park`).

`controller → services (interface) → services/impl → repository`, with `mapper` converting between `models/entity` and `models/dto/{request,response}`. Most services follow the interface + `*ServiceImpl` split; `ReportService`, `EtiquetteImpl`, and `QRCodeGeneratorService` live directly in `services/impl` without one (note `ReportController` imports `services.impl.ReportService` directly).

**Mappers are hand-written Spring `@Component`s, not MapStruct.** MapStruct is a dependency, but the compiler plugin's `annotationProcessorPaths` lists only Lombok, so the MapStruct processor never runs. Mappers inject repositories and resolve FK ids to entities (e.g. `ImmobilisationMapper` calls `CodeGenerationService` to mint `codeImmobilisation`). Adding a mapper means writing the methods by hand.

Beans use Lombok `@RequiredArgsConstructor` for constructor injection. Fields are additionally decorated with stray `@Autowired` — including on `static final` constants, where it does nothing. Harmless, but don't copy it into new code.

### Auth (two-step, JWT)

`POST /api/auth/login` validates credentials and returns only a `userId` — **no token yet**. `POST /api/auth/send-otp` mails a 6-digit code (`OtpService` + `EmailService`, `OneTimePassword` entity), and `POST /api/auth/verify-otp` exchanges userId+code for the access/refresh token pair. `/api/auth/refresh` and `/api/auth/logout` complete the flow; logout only validates the token — there is no blacklist.

Identity is ambiguous by design and is a recurring source of bugs:
- `CustomUserDetailsService` resolves a user by `nomUtilisateur` OR `email` OR `telephone`,
- but `UserDetails.getUsername()` and the JWT `subject` are `nomUtilisateur`,
- while `AuthServiceImpl.authenticateAndGetUserId` looks up strictly by **email** (so login only works with an email identifier, even though the field is named `usernameOrEmailOrPhone`),
- and services taking the authenticated principal (`TicketServiceImpl`, `UtilisateurServiceImpl.findInfoUser` used by `ReportController`) call `findByNomUtilisateur(...)` on it while naming the parameter `...Email`.

Authorization is `SecurityConfig` plus `@PreAuthorize` per method. **Rule order in `SecurityConfig` is load-bearing**: `/api/**` is declared `authenticated()` *before* the catch-all rule that permits any path without a dot (which exists so Angular SPA routes resolve). Moving the dotless rule above the `/api/**` rule would silently expose the entire API unauthenticated — this was a real defect, fixed in the current version.

Every controller method carries a `@PreAuthorize` for fine-grained roles. `UtilisateurController` instead declares `@PreAuthorize("hasRole('ADMIN')")` **at class level** — a fail-closed default, so new endpoints are ADMIN-only unless they explicitly override it; the overrides there allow a user to read their own record and change their own password. Denials surface as 403 via the `AccessDeniedException` handler in `GlobalExceptionHandler`; without it they would fall through to the catch-all `Exception` handler and return 500.

`POST /api/utilisateurs/create` is ADMIN-only: `UtilisateurRequestDto` carries a required `role`, so a public create endpoint would allow anyone to mint an ADMIN account. CORS is fixed to `http://localhost:4200` in `SecurityConfig`, but `ImmobilisationController` and `AgenceController` override it with `@CrossOrigin(origins = "*")`.

### AI assistant (`assistance/`)

Natural-language → SQL over the live DB: `AiQueryController` (ADMIN only) → `AiQueryService` → `PromptFactory` (schema-pinned prompt) → `GroqService` (Groq API, llama-3.1-8b-instant) → `SqlValidatorService` → `NativeQueryService` (`EntityManager` native query, rows as `Map`).

`SqlValidatorService` is the only thing between generated text and the database: it requires a leading SELECT, rejects `;`, SQL comments, and a DDL/DML keyword list. **Any change to this class is a security change.** The table/column list the model is allowed to use is hard-coded in `PromptFactory` and must be updated whenever entities change. `logback-spring.xml` defines an `AiTrainingLogger` writing `logs/ai-training.jsonl`, but `AiQueryService` constructs an `AiTrainingLog` and never logs it — the file is not being populated.

### PDF / labels

iText 7 + ZXing. `ReportService` builds intervention and transaction reports over a date range; passing a `signataire` embeds a QR-code signature block, passing `null` stamps it as a preview. The `/signed` endpoints re-verify the caller's password with `passwordEncoder.matches` before signing. `EtiquetteImpl` renders an 80×50 mm asset label with a Code128 barcode (`GET /api/immobilisations/{id}/etiquette`).

### SPA hosting

A built Angular app is committed to `src/main/resources/static/` (hashed `chunk-*.js`, `main-*.js`, `media/*.woff2`). `SpaFallbackController` forwards dotless paths up to 3 segments deep to `index.html`. `SecurityConfig` whitelists some bundle filenames by exact hashed name (`main-L2OMXW53.js`, …) which no longer match the files on disk; the dotless-path rule and the `/*.js` rule are what actually make it work. Replacing the frontend build means replacing these files wholesale — expect large, noisy diffs.

### Errors

`GlobalExceptionHandler` (`@RestControllerAdvice`) maps the custom exceptions in `exceptions/` to `ErrorResponse`. Throw those (`ResourceNotFoundException` → 404, `BusinessException`/`ValidationException` → 400, `UnauthorizedException` → 401, `ForbiddenException` → 403, …) rather than returning error `ResponseEntity`s by hand. Service layers also throw raw `IllegalStateException` / `IllegalArgumentException`, which fall through to the catch-all 500 handler.

## Configuration

`smart-park/src/main/resources/application.yml` contains **no secrets** — only `${VAR}` placeholders. Values come from environment variables, or from `smart-park/.env` (git-ignored) which the file imports via `spring.config.import: optional:file:./.env[.properties]`. `smart-park/.env.example` is the tracked template; copy it to `.env` to set a machine up.

The secrets deliberately have **no default value**, so a missing one stops startup with a named `PlaceholderResolutionException` instead of booting a half-configured app. `DB_URL`, `DB_USERNAME` and the mail host/port do have dev defaults — note that setting one of those to an empty value overrides the default rather than falling back to it. `GROQ_API_KEY` defaults to empty because the AI assistant is optional.

Never reintroduce a literal secret into `application.yml`, and never commit `.env`. **The secrets that used to live in this file are still in git history** (they predate the externalisation), so those particular credentials must be considered compromised until rotated.

`DataInitializer` seeds a default ADMIN on startup when no ADMIN exists (hard-coded email/password in the class).

## graphify

This project has a knowledge graph at `graphify-out/` with god nodes, community structure, and cross-file relationships.

Rules:
- For codebase questions, first run `graphify query "<question>"` when `graphify-out/graph.json` exists. Use `graphify path "<A>" "<B>"` for relationships and `graphify explain "<concept>"` for focused concepts. These return a scoped subgraph, usually much smaller than GRAPH_REPORT.md or raw grep output.
- Read `graphify-out/GRAPH_REPORT.md` only for broad architecture review or when query/path/explain do not surface enough context.
- After modifying code, run `py -m graphify update .` to keep the graph current (AST-only, no API cost).
