# Money Manager — Spring Boot + Thymeleaf

A server-rendered Money Manager starter: Java 17, Spring Boot 3, Thymeleaf,
Spring Security (session-based), Spring Data JPA. Runs out of the box on an
in-memory H2 database — no local Postgres install required to try it.

## Run it

```bash
mvn spring-boot:run
```

Then open **http://localhost:8080** — you'll land on the login page.
Click **Create one** to register; a default "Cash" account and a starter
category list are seeded automatically for every new user.

H2 console (dev only, to poke at the data directly): **http://localhost:8080/h2-console**
JDBC URL: `jdbc:h2:mem:moneymanager`, user `sa`, empty password.

## Switching to PostgreSQL for production

```bash
export DB_URL=jdbc:postgresql://localhost:5432/moneymanager
export DB_USERNAME=postgres
export DB_PASSWORD=your_password
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

With `ddl-auto: validate` in prod, create the schema with a migration tool
(Flyway/Liquibase) before first run, or temporarily set `ddl-auto: update`
once to let Hibernate generate it, then switch back to `validate`.

## Project layout

```
src/main/java/com/moneymanager/
  MoneyManagerApplication.java   entry point
  config/
    SecurityConfig.java          form login, BCrypt, route rules
    JpaAuditingConfig.java       enables createdAt/updatedAt
  entity/                        JPA entities (see schema below)
  repository/                    Spring Data JPA repositories
  service/
    UserDetailsServiceImpl.java  bridges User -> Spring Security
    RegistrationService.java     signup + default account/category seed
  controller/
    AuthController.java          /login /register
    DashboardController.java     /dashboard

src/main/resources/
  application.yml                dev (H2) + prod (Postgres) profiles
  templates/                     Thymeleaf pages (login, register, dashboard)
  static/css/                    tokens.css (design system) + style.css
```

## Data model (current scaffold)

```
User 1───* Account 1───* Transaction *───1 Category
User 1───* Category
User 1───* Budget         (optionally scoped to a Category and/or Account)
User 1───* Goal
User 1───* Bill           (optionally linked to an Account)
User 1───* Investment     (optionally linked to an Account)
User 1───* Loan
Transaction *───1 Account (toAccount, only set when type = TRANSFER)
```

Every entity extends `BaseEntity` (`id`, `createdAt`, `updatedAt`), and
`Transaction` supports tags (`transaction_tags` join table), a receipt image
URL, notes, location, and a payment method — matching the fields called out
in the original feature spec.

## What's implemented vs. what's scaffolded

**Working end-to-end:** registration, login/logout, password hashing,
dashboard showing real account balances and recent transactions pulled from
the database.

**Scaffolded (entities + repositories exist, UI/controllers are next):**
Accounts CRUD page, Transactions CRUD page, Budgets, Goals, Bills,
Investments, Loans, Reports/charts, category management, account
difference/comparison tool, CSV/Excel import, receipt upload, 2FA, Google
Sign-In.

## Suggested build order

1. Accounts page (list/create/edit) — everything else references accounts.
2. Transactions page (list/create with category + account pickers).
3. Dashboard charts (Chart.js) once there's real transaction data to chart.
4. Budgets + Goals + Bills.
5. Reports, account-difference comparison tool, AI features.

Let me know which of these you want built next and I'll scaffold it the same
way.
