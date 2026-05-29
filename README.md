# Automation Portfolio

End-to-end automation framework built with Java, Selenium, TestNG and RestAssured, covering UI and API testing with professional design patterns.

## Tech Stack

| Tool | Version | Purpose |
|------|---------|---------|
| Java | 17 | Programming language |
| Selenium | 4.21.0 | UI automation |
| TestNG | 7.10.2 | Test framework |
| RestAssured | 5.4.0 | API testing |
| WebDriverManager | 5.9.1 | Driver management |
| Jackson | 2.17.1 | JSON serialization |
| ExtentReports | 5.1.1 | Test reporting |
| Maven | 3.x | Build tool |

## Project Structure

```
src/test/java/com/victormtz/
├── base/
│   └── BaseTest.java              # WebDriver setup/teardown
├── api/
│   ├── base/
│   │   └── BaseApiTest.java       # RestAssured setup
│   └── models/
│       ├── Pet.java
│       ├── Order.java
│       └── User.java
├── pages/                          # POM
│   ├── LoginPage.java
│   ├── InventoryPage.java
│   ├── CartPage.java
│   └── CheckoutPage.java
├── tests/
│   ├── ui/                         #UI Tests
│   │   ├── LoginTest.java
│   │   ├── InventoryTest.java
│   │   ├── CartTest.java
│   │   └── CheckoutTest.java
│   └── api/                        #API Tests
│       ├── PetTest.java
│       ├── OrderTest.java
│       └── UserTest.java
└── data/
    └── TestDataProvider.java       
src/test/resources/
└── data/
    └── users.json
```

## Test Coverage

### UI Tests — Sauce Demo (saucedemo.com)

| Class | Tests | Description |
|-------|-------|-------------|
| `LoginTest` | 4 | Valid/invalid login with Data-Driven testing |
| `InventoryTest` | 5 | Product listing, sorting A-Z, Z-A, price filters |
| `CartTest` | 5 | Add, remove, and empty cart |
| `CheckoutTest` | 6 | Full checkout flow, validations, error handling |

### API Tests — PetStore (petstore.swagger.io)

| Class | Tests | Description |
|-------|-------|-------------|
| `PetTest` | 8 | CRUD operations + search by status |
| `OrderTest` | 5 | CRUD operations + inventory check |
| `UserTest` | 7 | CRUD operations + login/logout |

**Total: 40 tests passing**

## Design Patterns

- **Page Object Model (POM)** — UI elements and actions separated from test logic
- **Data-Driven Testing** — Login tests driven by `users.json` via TestNG `@DataProvider`
- **Base Test Classes** — Shared setup and teardown for UI and API tests
- **POJO Models** — Java objects for API request/response serialization

## Prerequisites

- Java 17
- Maven 3.x
- Google Chrome (latest)

## How to Run

**Run all tests:**
```bash
mvn test
```

**Run only UI tests:**
```bash
mvn test -Dgroups=ui
```

**Run only API tests:**
```bash
mvn test -Dgroups=api
```

**Run a specific test class:**
```bash
mvn test -Dtest=LoginTest
```

## Test Sites

- UI: [https://www.saucedemo.com](https://www.saucedemo.com)
- API: [https://petstore.swagger.io](https://petstore.swagger.io)