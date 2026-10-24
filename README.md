# Inventory Billing System

A monolithic web application for managing the stock of a small shop and billing its customers. Built with Spring Boot, it stores products in an in-memory H2 database and serves a single-page web interface from the same server.

## Features

- Add, edit and remove products
- Inventory table with search, sorting and stock bars
- Low-stock (under 10) and out-of-stock tags, with summary figures
- Create a bill by choosing products and quantities (blocked if stock is not enough)
- Stock is reduced automatically in one transaction when the bill is generated
- Printable invoice with invoice number, date, line items and grand total

## Tech stack

| Part | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.1.1 |
| Data access | Spring Data JPA, Hibernate |
| Database | H2 (in-memory) |
| Build tool | Maven (Maven Wrapper included) |
| Frontend | HTML, CSS, JavaScript (Fetch API) |

## Architecture

Layered design: **Controller → Service → Repository → Entity**.
Product management and billing are two chains that share one `ProductRepository`.

## REST API

| Method | URL | Purpose |
|---|---|---|
| POST | `/api/inventory/add` | Add a product |
| GET | `/api/inventory/` | List all products |
| PUT | `/api/inventory/{id}` | Update a product |
| DELETE | `/api/inventory/{id}` | Remove a product |
| POST | `/api/billing/checkout` | Create a bill, reduce stock, return the invoice |

## How to run

1. Install Java 17 or later.
2. Clone the repository, then open the inventory-app folder inside it.
3. Run:
```
   mvnw.cmd spring-boot:run
```
   (on Mac/Linux: `./mvnw spring-boot:run`)
4. Open http://localhost:8080/ in your browser.
5. Optional: view the database at http://localhost:8080/h2-console
   (JDBC URL `jdbc:h2:mem:inventory_db`, user `sa`, password `password`).

## Notes

- The database is in memory, so data is lost when the application restarts.
- Invoices are shown and printed but not saved in the database.

## Future scope

Persistent database (MySQL/PostgreSQL), saved invoice history, tax and discounts, customer details, login with roles, server-side validation, PDF invoices, and protection against two bills selling the same last stock.

## Team

Kushagra Tiwari, Arjun Mallah, Abhijeet Pasi
Guide: Prof. Savitha Devaraj
