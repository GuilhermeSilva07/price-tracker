# Price Tracker

SaaS for tracking product prices and sending alerts when they drop below a target value.

## About

Users register a product URL (starting with Mercado Livre) and a target price. The system periodically checks the current price and sends an alert (email, with Telegram planned) when the price drops to or below the target.

Built as a hands-on learning project to deepen backend engineering skills — architecture decisions, scheduled jobs, external API integration, authentication, and paid subscription plans (freemium model).

## Tech Stack

- Java 21
- Spring Boot 3.x
- PostgreSQL
- Flyway (planned)
- Gradle (Kotlin DSL)
- Docker / Docker Compose
- RabbitMQ (planned)

## Status

🚧 Under active development — Phase 1 (MVP).

## Getting Started

1. Copy `.env.example` to `.env` and fill in the values
2. Start the database: `docker compose up -d`
3. Run the application: `./gradlew bootRun`

## Roadmap

- [x] Project setup
- [ ] Entity modeling (User, Product, PriceHistory)
- [ ] Product registration endpoint
- [ ] Mercado Livre price integration
- [ ] Email alerts
- [ ] Scheduled price checks
- [ ] Free/paid plans
- [ ] Telegram alerts
- [ ] Billing (Stripe)