# Expense Tracker Application

A full-stack expense tracking application built with Spring Boot backend and React frontend.

## Architecture

- **Backend**: Spring Boot 3.5.5 with REST API
- **Frontend**: React 19 with Vite
- **Database**: MySQL
- **Build Tools**: Maven (backend), Vite (frontend)

## Features

- Create, read, update, and delete expenses
- Expense categorization
- Financial precision with BigDecimal
- Input validation
- CORS-enabled API for frontend integration

## Prerequisites

- Java 17+
- Node.js 18+
- MySQL 8.0+
- Maven 3.6+

## Project Structure

```
expense-tracker-app/
├── backend/          # Spring Boot REST API
│   ├── src/
│   │   ├── main/java/com/suyos/tracker/
│   │   │   ├── controller/    # REST controllers
│   │   │   ├── service/       # Business logic
│   │   │   ├── model/         # JPA entities
│   │   │   ├── dto/           # Data transfer objects
│   │   │   ├── mapper/        # MapStruct mappers
│   │   │   └── repository/    # JPA repositories
│   │   └── resources/
│   └── pom.xml
└── frontend/         # React application
    ├── src/
    ├── public/
    └── package.json
```

## Setup Instructions

### Database Setup

1. Create MySQL database:
```sql
CREATE DATABASE expense_tracker;
```

2. Update `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Backend Setup

1. Navigate to backend directory:
```bash
cd backend
```

2. Install dependencies and run:
```bash
./mvnw spring-boot:run
```

Backend runs on `http://localhost:8080`

### Frontend Setup

1. Navigate to frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start development server:
```bash
npm run dev
```

Frontend runs on `http://localhost:5173`

## API Endpoints

### Expenses

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/expenses` | Get all expenses |
| POST | `/api/expenses` | Create new expense |
| GET | `/api/expenses/{id}` | Get expense by ID |
| PUT | `/api/expenses/{id}` | Update expense |
| DELETE | `/api/expenses/{id}` | Delete expense |

### Request/Response Format

**Expense DTO:**
```json
{
  "id": 1,
  "description": "Grocery shopping",
  "amount": 45.67,
  "date": "2024-01-15",
  "category": "FOOD"
}
```

**Categories:**
- FOOD
- TRANSPORTATION
- ENTERTAINMENT
- UTILITIES
- HEALTHCARE
- OTHER

## Key Technologies

### Backend
- Spring Boot 3.5.5
- Spring Data JPA
- Spring Validation
- MapStruct 1.6.3
- Lombok
- MySQL Connector

### Frontend
- React 19
- Vite 7
- ESLint

## Development Notes

- Minimum expense amount: $0.01
- Financial amounts use BigDecimal for precision
- CORS configured for React dev server (port 5173)
- Validation annotations ensure data integrity
- MapStruct handles entity-DTO mapping

## Build for Production

### Backend
```bash
cd backend
./mvnw clean package
java -jar target/tracker-0.0.1-SNAPSHOT.jar
```

### Frontend
```bash
cd frontend
npm run build
npm run preview
```