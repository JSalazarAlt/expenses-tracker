# Expense Tracker App

A simple web application for tracking personal expenses, built as a learning project using Spring Boot and Thymeleaf. This application helps users record and organize their daily expenses with basic filtering and visualization features.

## About This Project

This is a beginner-friendly project created to practice web development concepts including:
- Spring Boot framework basics
- Database operations with JPA
- Web forms and data validation
- Basic chart visualization
- Responsive web design

## Features

- ✅ Add new expenses with description, amount, date, and category
- 📊 View all expenses in an organized table
- 🔍 Filter expenses by category
- 📈 View yearly expense charts with monthly breakdown
- 🎨 Clean user interface with gradient styling
- 📱 Responsive layout for different screen sizes

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
- **Database**: MySQL
- **Build Tool**: Maven

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/expense-tracker-app.git
   cd expense-tracker-app
   ```

2. Set up your MySQL database:
   - Create a database named `expense_tracker`
   - Update `application.properties` with your database credentials

3. (Optional) Populate with sample data:
   ```bash
   mysql -u your_username -p expense_tracker < sample_data.sql
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

5. Open your browser and navigate to `http://localhost:8080/expenses`

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
- MySQL database server

## Learning Objectives

This project was built to understand:
- Spring Boot application structure
- MVC pattern implementation
- Database integration with Spring Data JPA
- Thymeleaf templating engine
- Frontend-backend data flow
- Chart.js integration for data visualization

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
│   │       │   │   ├── expense-list.css
│   │       │   │   └── expense-chart.css
│   │       │   └── js/
│   │       │       ├── expense-list.js
│   │       │       └── expense-chart.js
│   │       ├── templates/
│   │       │   ├── expense-add.html
│   │       │   ├── expense-list.html
│   │       │   └── expense-chart.html
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Usage

1. **Add Expense**: Navigate to `/expenses/new` to add a new expense
2. **View Expenses**: Visit `/expenses` to see all recorded expenses
3. **Filter**: Use the category dropdown to filter expenses by type
4. **View Charts**: Click "View Yearly Chart" to see monthly expense visualization
5. **Change Year**: Use the year filter on the chart page to view different years

## Current Status

This is a work-in-progress learning project. Features are being added as I learn new concepts and improve my development skills.