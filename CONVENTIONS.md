# Conventions

## Layering rules

- **Package-by-feature**, not package-by-layer (see [ARCHITECTURE.md](ARCHITECTURE.md)).
- **Controllers are thin** — request/response mapping and validation only, no
  business logic.
- **Services own business rules** — validation of invariants, orchestration
  across repositories, transaction boundaries (`@Transactional`).
- **DTOs never expose entities** — controllers accept/return request/response
  DTOs; JPA entities never cross the controller boundary.
- **Repositories are Spring Data interfaces** — no custom SQL unless a query
  can't be expressed as a derived method or `@Query`.

## Naming

- Entities: singular noun (`Product`, `PriceHistory`).
- Repositories: `<Entity>Repository`.
- Services: `<Entity>Service` (+ `Impl` only if an interface is genuinely
  needed for multiple implementations — not by default).
- DTOs: `<Entity>Request` / `<Entity>Response` (or a more specific verb, e.g.
  `RegisterProductRequest`).
- Packages/tables: `snake_case` for SQL, `camelCase`/`PascalCase` for Java.

## Git workflow (manual Git Flow)

- `master` — releases only.
- `develop` — integration branch.
- `feature/*` — one branch per Trello card, branched from `develop`, merged
  back with `--no-ff`.
- Branch name mirrors the card, e.g. `feature/entity-modeling`.

## Commits

- [Conventional Commits](https://www.conventionalcommits.org/): `feat:`,
  `fix:`, `docs:`, `chore:`, `refactor:`, `test:`.
- One logical change per commit; commit message explains *why*, not just
  *what*.

## Definition of Done (self code-review checklist)

Before moving a card to **Done**:

- [ ] Code compiles and existing tests pass (`./gradlew test`).
- [ ] New/changed schema has a Flyway migration (no reliance on
      `ddl-auto: update`).
- [ ] No entity leaks past the controller (DTOs only at the boundary).
- [ ] No business logic in controllers.
- [ ] Package and file placement matches the feature-package structure.
- [ ] Card description / Trello docs updated if the change affects
      architecture, workflow, or conventions.

Source of truth: this file. Update it first, then keep the Trello
"Conventions" card summary in sync.
