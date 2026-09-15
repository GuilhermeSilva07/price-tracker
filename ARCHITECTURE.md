# Architecture Overview

## Stack

- Java 21
- Spring Boot 4.1.1 (Web MVC, Data JPA, Validation)
- PostgreSQL 16
- Flyway (schema migrations, via `spring-boot-starter-flyway` — Spring Boot 4
  moved Flyway autoconfiguration out of `spring-boot-autoconfigure` into its
  own starter; `flyway-core` alone does not trigger migrations)
- Gradle (Kotlin DSL)
- Docker / Docker Compose (local Postgres) + multi-stage `Dockerfile`
- GitHub Actions CI (`.github/workflows/ci.yml`): build + test against a
  Postgres service container, then a Docker image build, on every push/PR to
  `develop`/`master`
- RabbitMQ (planned, Phase 2)

## Package structure (package-by-feature)

```
com.guilherme.price_tracker
├── product        # product registration and tracking
├── pricehistory    # historical price records
├── user            # user accounts, auth
├── notification    # alert delivery (email, later Telegram)
├── scraper         # external marketplace price fetching
├── config          # cross-cutting config (security, scheduling)
└── exception       # custom exceptions, centralized error handling
```

Each feature package owns its own entity, repository, service and controller.
Cross-feature access goes through the owning package's service — never reach
into another feature's repository directly.

## Domain model (Phase 1)

- **User** — id (UUID), name, email (unique), password (hashed).
- **Product** — id (UUID), url, externalId (extracted from the Mercado Livre
  URL), targetPrice, currentPrice, owner (`User`, lazy `@ManyToOne`).
- **PriceHistory** — id (UUID), product (lazy `@ManyToOne`), price, checkedAt
  (`Instant`).

Schema is owned by Flyway migrations under
`src/main/resources/db/migration`; JPA runs with `ddl-auto: validate`, so a
migration must exist for every schema change before the entity is updated.

## Key design decisions

- **UUID primary keys** — avoids exposing sequential ids, works well once the
  system is split across services or replicated.
- **BigDecimal for money** — `targetPrice`/`currentPrice`/`price` are never
  floats, to avoid rounding errors on currency values.
- **Instant for timestamps** — stored and compared in UTC, converted at the
  presentation layer if needed.
- **LAZY fetch on every `@ManyToOne`** — avoids accidental N+1 / over-fetching;
  eager loading is opt-in per query (`JOIN FETCH` / entity graph), not the
  default.
- **Flyway over `ddl-auto: update`** — schema changes are explicit, reviewable
  SQL files, not inferred from entity state.

## Roadmap (by phase)

1. **Phase 1 — MVP**: entity modeling, product registration endpoint,
   Mercado Livre price integration, email alert on price drop.
2. **Phase 2 — Scale**: scheduled price-check job, RabbitMQ processing queue,
   price history + chart endpoint.
3. **Phase 3 — Accounts**: plan modeling (free/paid limits), authentication and
   authorization (Spring Security + JWT).
4. **Phase 4 — Channels**: Telegram alert bot.
5. **Phase 5 — Productionize**: Stripe billing (sandbox), observability
   (structured logs, Micrometer/Actuator metrics, `/health`).
6. **Portfolio**: complete README, Docker/cloud public deploy.

Source of truth: this file. Update it first, then keep the Trello
"System Architecture Overview" card summary in sync.
