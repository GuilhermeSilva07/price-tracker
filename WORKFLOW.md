# Development Workflow and Card Order

## Board flow

```
Backlog -> To Do -> In Progress -> Code Review -> Done
```

- Move a card to **To Do** only when you're about to start it.
- Move to **In Progress** when a `feature/*` branch exists for it.
- Move to **Code Review** once the self-review checklist in
  [CONVENTIONS.md](CONVENTIONS.md) passes, before merging to `develop`.
- Move to **Done** only after the merge to `develop` lands.

## Pick-up order

Follow the `[Phase X]` prefix in card titles, phase by phase — don't start a
Phase 2 card while Phase 1 cards remain in Backlog/To Do unless explicitly
reprioritized.

Within a phase, prefer this order:

1. Entity modeling / schema (migrations) for anything the phase's endpoints
   depend on.
2. Endpoints (controllers + services + repositories).
3. Jobs / external integrations that consume those endpoints or entities.

Example for Phase 1: entity modeling → product registration endpoint →
Mercado Livre integration → email alert.

## Current status (last synced 2026-09-15)

- Phase 1 — MVP:
  - [x] Project setup (Spring Boot + Gradle + PostgreSQL)
  - [x] Entity modeling (User, Product, PriceHistory) — merged via PR #1
  - [ ] `POST /api/products` endpoint
  - [ ] Mercado Livre integration
  - [ ] Email alert on price drop
- Phase 5:
  - [x] CI/CD: GitHub Actions pipeline + Dockerfile — merged via PR #2

Every merge to `develop` so far went through a `feature/*` branch and PR,
verified green by CI before merging.

Source of truth: this file. Update it first, then keep the Trello
"Development Workflow and Card Order" card summary in sync.
