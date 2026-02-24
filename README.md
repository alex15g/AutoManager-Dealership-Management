# 🏎️ AutoManager - Car Dealership Management System

![Java](https://img.shields.io/badge/Language-Java%2017-red.svg)
![Database](https://img.shields.io/badge/DB-SQL%20Server-blue.svg)
![Architecture](https://img.shields.io/badge/Architecture-MVC-green.svg)
![UI](https://img.shields.io/badge/UI-JavaFX-orange.svg)

## 🌟 Overview
**AutoManager** is a robust desktop application designed to simulate the operations of a modern car dealership. It provides a comprehensive solution for managing customers, vehicle inventory, employee records, service history, and sales transactions within a unified, secure environment.

The project emphasizes **Data Integrity**, **Object-Oriented Programming (OOP)** principles, and a clear separation of concerns using the **MVC (Model-View-Controller)** pattern.

---

## 🚀 Key Features
- [cite_start]**Comprehensive CRUD Operations**: Manage Clients, Vehicles, Employees, Services, and Sales[cite: 511].
- [cite_start]**Functional Interconnectivity**: Transactions link clients, cars, and employees seamlessly[cite: 512].
- [cite_start]**Service & Maintenance Tracking**: Digital service book for each vehicle with automated cost calculation and mileage validation[cite: 530, 532, 533].
- [cite_start]**Role-Based Access Control (RBAC)**: Distinct permissions for `ADMIN` and `EMPLOYEE` roles to ensure data security[cite: 545, 613].
- [cite_start]**Relational Data Integrity**: Strict database constraints (Foreign Keys) prevent accidental deletion of critical transaction data[cite: 598, 601].
- [cite_start]**Error Handling & User Feedback**: Graceful handling of SQL exceptions and intuitive UI messages[cite: 618, 627].

---

## 🛠️ Tech Stack
| Component | Technology |
| :--- | :--- |
| **Language** | [cite_start]Java 17 [cite: 553] |
| **GUI Framework** | [cite_start]JavaFX [cite: 553] |
| **Database** | [cite_start]Microsoft SQL Server [cite: 553] |
| **Data Access** | [cite_start]JDBC (Java Database Connectivity) [cite: 553] |
| **Build Tool** | [cite_start]Apache Maven [cite: 553] |
| **Architecture** | [cite_start]MVC Pattern with DAO (Data Access Object) [cite: 553, 562] |

---

## 📊 Database Schema
The system relies on a normalized relational database with 9 interconnected entities:
- [cite_start]**Customers & Employees**: Personal and professional contact details[cite: 583, 584].
- [cite_start]**Vehicles & Options**: Multi-vehicle management with N-N relationship for equipment options[cite: 585, 589, 590].
- [cite_start]**Service Books & Inspections**: Comprehensive history of technical interventions[cite: 587, 588].
- [cite_start]**Sales**: Secure records of all successful dealership transactions[cite: 586].

---

## 🖼️ User Interface Showcase
[cite_start]*The application features a modular, tab-based interface for easy navigation between modules[cite: 631, 632].*


---

## ⚙️ Installation & Setup
### Prerequisites
- [cite_start]**JDK 17** or higher[cite: 415].
- [cite_start]**Apache Maven**[cite: 416].
- [cite_start]**Microsoft SQL Server**[cite: 417].

### Database Initialization
1. [cite_start]Create a new database in SQL Server (e.g., `gestionare_reprezentanta_auto`)[cite: 420].
2. [cite_start]Execute the provided SQL script found in the `/sql` directory to generate tables and constraints[cite: 421].

### Configuration
Update the `DBConnection.java` file with your SQL Server credentials:
```java
// Example JDBC Connection string
String url = "jdbc:sqlserver://localhost:1433;databaseName=gestionare_reprezentanta_auto;";
