# Expense Tracker App

A modern web application for tracking personal expenses built with Spring Boot and Thymeleaf.

## Features

- ✅ Add new expenses with description, amount, date, and category
- 📊 View all expenses in a clean, organized table
- 🔍 Filter expenses by category
- 💰 Professional UI with gradient design
- 📱 Responsive layout

## Categories

- 🏠 Rent / Mortgage
- 🍽️ Groceries  
- 🔌 Utilities
- 🚗 Transportation
- ☕ Dining Out
- 🎬 Entertainment
- 👕 Clothing
- 💇 Personal Care
- 💳 Loan Payments
- 🏦 Savings
- 💡 Subscriptions
- 🎁 Gifts & Donations
- ❓ Other

## Tech Stack

- **Backend**: Spring Boot, Spring MVC
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript
- **Database**: H2 (in-memory)
- **Build Tool**: Maven

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/expense-tracker-app.git
   cd expense-tracker-app
   ```
2. Run the application:
   ```bash
   mvn spring-boot:run
   ```
3. Open your browser and navigate to `http://localhost:8080/expenses`

## Database Schema

```sql
CREATE TABLE expenses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    date DATE NOT NULL,
    category VARCHAR(100) NOT NULL
);
```

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Project Structure

```
expense-tracker-app/
├── src/
│   ├── main/
│   │   ├── java/com/projects/tracker/
│   │   │   ├── TrackerApplication.java
│   │   │   ├── controller/ExpenseController.java
│   │   │   ├── model/Expense.java
│   │   │   ├── repository/ExpenseRepository.java
│   │   │   └── services/ExpenseService.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   ├── expense-add.css
│   │       │   │   └── expense-list.css
│   │       │   └── js/
│   │       │       └── expense-list.js
│   │       ├── templates/
│   │       │   ├── expense-add.html
│   │       │   └── expense-list.html
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Usage

1. **Add Expense**: Navigate to `/expenses/new` to add a new expense
2. **View Expenses**: Visit `/expenses` to see all recorded expenses
3. **Filter**: Use the category dropdown to filter expenses by type