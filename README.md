# Car Shop Management System

A Java Swing desktop application developed as an academic team project for object-oriented programming and expanded for **CSE430: Software Testing and Quality Assurance**. It demonstrates object-oriented modelling, a small in-memory inventory workflow, GUI interactions, and automated unit tests with JUnit 5.

> **Project scope:** this is an educational prototype, not production dealership software. It does not provide real authentication, persistent storage, a payment process, or production-grade financial estimates.

## Features

### Admin dashboard
- Add commuter and sports vehicles with type-specific attributes.
- View inventory details and summary statistics: total inventory value, average price, most expensive vehicle, and cheapest vehicle.
- Review in-memory customer profiles and purchase histories.

### Customer view
- Register a simple in-session profile with a name, address, and contact field.
- Browse the seeded sample inventory and purchase a vehicle, which moves it from the inventory list into the customer's in-memory history.
- View VIP status using the project rule of total spending of at least 5,000,000 Taka.
- Estimate insurance and trip fuel cost; view sports-car classifications, a simplified quarter-mile heuristic, and an illustrative maintenance-cost estimate.

### Domain and testing examples
- Vehicle inheritance (`Car`, `Commuter`, `Sports`) and the `User` interface.
- Inventory search by make, removal, and price summaries.
- Customer purchase history, total spending, VIP status, and most expensive purchase.
- JUnit Jupiter (JUnit 5) tests for core logic, invalid inputs, edge cases, and parameterized case-insensitive search.

## Screenshots

**Customer view**

![Customer view showing the sample vehicle inventory and vehicle actions](docs/screenshots/customer-view.png)

**Admin dashboard**

![Admin dashboard showing sample inventory statistics and an empty customer log](docs/screenshots/admin-dashboard.png)

## Technology stack

- Java 17 or later
- Java Swing / AWT
- Maven
- JUnit Jupiter (JUnit 5)

## Run the application

### Option A: Maven (recommended)

Install a JDK 17+ and Maven, then run from the repository root:

```bash
mvn clean package
java -cp target/classes carshop.Main
```

The project does not need an external database or environment variables. Maven must be able to download the JUnit and plugin dependencies on the first build.

### Option B: Direct Java compilation

With a JDK 17+ installed, run the helper script:

- **Windows:** double-click `run.bat` or run `run.bat` from Command Prompt.
- **macOS / Linux:** run `./run.sh` from a terminal.

The scripts compile the application source. They do not run the automated test suite.

## Run the tests

From the repository root:

```bash
mvn test
```

To perform the full Maven verification lifecycle:

```bash
mvn verify
```

A GitHub Actions workflow runs `mvn verify` on pushes and pull requests.

## Test coverage

The JUnit 5 suite includes ordinary unit tests and one parameterized test with four case variants. It exercises inventory totals/search/removal, customer purchase history and VIP thresholds, invalid core model data, financial/performance estimates, and boundary/negative inputs. The customer-purchase workflow is tested at the domain-model level; GUI interaction itself is not covered by automated UI tests.

## Project structure

```text
src/
├── main/java/carshop/
│   ├── Main.java                 # Swing application entry point and GUI
│   ├── inventory/CarShop.java   # In-memory vehicle inventory operations
│   ├── users/User.java          # User information contract
│   ├── users/Customer.java      # Customer profile and purchase history
│   └── vehicles/
│       ├── Car.java             # Shared vehicle properties and estimates
│       ├── Commuter.java        # Commuter vehicle logic
│       └── Sports.java          # Sports vehicle logic
└── test/java/carshop/
    ├── inventory/CarShopTest.java
    ├── users/CustomerTest.java
    └── vehicles/
        ├── CarTest.java
        ├── CommuterTest.java
        └── SportsTest.java
```

## Important implementation notes and limitations

- All inventory and customer data is held in memory and is lost when the application closes.
- The “admin” screen is a demo view switch, not a secured administrator login. Do not enter real customer information.
- The sample purchase flow does not process payment, enforce a wallet balance, or persist a transaction.
- The insurance, depreciation, fuel-cost, quarter-mile, VIP, and maintenance rules are simplified academic examples. They are not financial, insurance, or automotive-performance advice.
- The quarter-mile value uses a deliberately simplified formula for demonstrating conditional/model logic; it is **not** a physics-based performance prediction.
- Inventory and purchase totals use `long` aggregation, but each vehicle's stored price is an `int` in this version.

## Academic context and attribution

This is a **team academic project** associated with East West University's CSE110 Object-Oriented Programming coursework and CSE430 Software Testing and Quality Assurance coursework. The source keeps the original project concepts while improving packaging, validation, and maintainability for portfolio review. It should not be represented as a solo-authored project.

A software license has not been added. Because this originated as a team project, confirm the team's permission and intended license before adding an open-source license or reusing the code outside the project.
