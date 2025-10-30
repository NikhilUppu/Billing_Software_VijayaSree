# Billing Backend - Current Status and How to Run

## What is included
- Spring Boot 3 (Java 17), JPA/Hibernate, Flyway, PostgreSQL 16
- Service: services/catalog-service
- Features:
  - Companies and Products (with size/volume, price, image_url)
  - Users with roles (ADMIN, MANAGER, ACCOUNTANT, CASHIER)
  - Users have optional company association and 10-digit mobile number
  - Role-guarded endpoints (admin-only for user management)

## Database (PostgreSQL)
- DB name: catalog
- Flyway migrations auto-run:
  - V1__init.sql: company, product + seed data (company id: nakasa)
  - V2__users.sql: user_account
  - V3__user_mobile.sql: add mobile (unique)
  - V4__user_company_fk.sql: link user_account to company

### Postgres setup (macOS/Homebrew)
```bash
brew install postgresql@16
brew services start postgresql@16
/opt/homebrew/opt/postgresql@16/bin/psql -U "$USER" -d postgres -c "CREATE DATABASE catalog;"
/opt/homebrew/opt/postgresql@16/bin/psql -U "$USER" -d postgres -c "CREATE USER app WITH PASSWORD 'appsecret';"
/opt/homebrew/opt/postgresql@16/bin/psql -U "$USER" -d catalog -c "GRANT CONNECT, TEMP ON DATABASE catalog TO app;"
/opt/homebrew/opt/postgresql@16/bin/psql -U "$USER" -d catalog -c "GRANT USAGE, CREATE ON SCHEMA public TO app;"
/opt/homebrew/opt/postgresql@16/bin/psql -U "$USER" -d catalog -c "ALTER SCHEMA public OWNER TO app;"
```

## App configuration
- Use profile: postgres
- File: services/catalog-service/src/main/resources/application-postgres.properties
```
spring.datasource.url=jdbc:postgresql://localhost:5432/catalog
spring.datasource.username=app
spring.datasource.password=appsecret
spring.datasource.driver-class-name=org.postgresql.Driver
```

## Build and run
```bash
cd "/Users/nikhiluppucherla/Documents/Backend Projects/Billing_Software_VijayaSree"
mvn -q -DskipTests=true -pl services/catalog-service -am clean package
java -jar services/catalog-service/target/catalog-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=postgres
# if 8081 is busy, append: --server.port=8082
```

## Endpoints (Basic Auth)
- Test user: admin/admin123 (ADMIN)

### Products
- GET /api/products
- GET /api/products/{id}
- POST /api/products
  - Example body:
    {
      "id":"demo-1", "name":"Demo Product", "volume":"1 L", "price":500,
      "company": { "id": "nakasa" }
    }
- PUT /api/products/{id}
- DELETE /api/products/{id}

### Users (ADMIN only)
- GET /api/users
- POST /api/users
  - Body:
    {
      "username":"cashier2", "password":"pass123", "role":"CASHIER",
      "mobile":"9876543210", "companyId":"nakasa"
    }
- DELETE /api/users/{id}

## Suggestions (next)
- Replace Basic Auth with JWT login using mobile + password
- Company CRUD and GET /api/companies/{id}/products
- Input validation + global error handler
- Add CSV import for products and price lists
- Add unit/integration tests and CI
