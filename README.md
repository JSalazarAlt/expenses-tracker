# Expense Tracker Application

A full-stack expense tracking application built with Spring Boot backend and React frontend.

## Architecture

- **Backend**: Spring Boot 3.5.5 with REST API
- **Frontend**: React 19 with Vite
- **Database**: MySQL
- **Build Tools**: Maven (backend), Vite (frontend)

## Features

### Core Functionality
- Create, read, update, and delete expenses
- Expense categorization with 9 predefined categories
- Server-side pagination with filtering and sorting
- Advanced filtering by category and date range
- Financial precision with BigDecimal
- Input validation and date constraints
- CORS-enabled API for frontend integration

### User Experience
- **Modern Design System** - Professional UI with consistent styling
- **Responsive Design** - Optimized for desktop, tablet, and mobile
- **Interactive Elements** - Smooth animations and hover effects
- **Enhanced Date Picker** - Custom React DatePicker with timezone handling
- **Category Icons** - Visual category identification with emojis
- **Smart Filtering** - Auto-adjusting date ranges and real-time filtering
- **Professional Styling** - Gradient backgrounds, shadows, and modern typography

### Technical Excellence
- Comprehensive documentation (Javadoc + JSDoc)
- Clean architecture with separation of concerns
- Type-safe API communication with Axios
- Performance optimizations and error handling

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
    │   ├── components/        # React components
    │   ├── services/          # API service layer
    │   └── constants/         # Application constants
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
| GET | `/api/expenses?page=0&size=10&sortBy=expenseDate&sortDir=desc&category=FOOD&startDate=2024-01-01&endDate=2024-12-31` | Get paginated expenses with filtering |
| POST | `/api/expenses` | Create new expense |
| GET | `/api/expenses/{id}` | Get expense by ID |
| PUT | `/api/expenses/{id}` | Update expense |
| DELETE | `/api/expenses/{id}` | Delete expense |

### Query Parameters

- `page` - Zero-based page number (default: 0)
- `size` - Number of records per page (default: 10, max: 100)
- `sortBy` - Sort field: expenseDate, expenseAmount, expenseDescription (default: expenseDate)
- `sortDir` - Sort direction: asc, desc (default: desc)
- `category` - Filter by category (optional)
- `startDate` - Filter by start date in YYYY-MM-DD format (optional)
- `endDate` - Filter by end date in YYYY-MM-DD format (optional)

### Request/Response Format

**Expense DTO:**
**Paginated Response:**
```json
{
  "content": [
    {
      "expenseId": 1,
      "expenseDescription": "Grocery shopping",
      "expenseAmount": 45.67,
      "expenseDate": "2024-01-15",
      "expenseCategory": "FOOD"
    }
  ],
  "currentPage": 0,
  "totalPages": 5,
  "totalElements": 42,
  "size": 10,
  "first": true,
  "last": false
}
```

**Categories:**
- FOOD
- HOUSING
- TRANSPORTATION
- UTILITIES
- ENTERTAINMENT
- HEALTHCARE
- EDUCATION
- PERSONAL_CARE
- MISCELLANEOUS

## Key Technologies

### Backend
- Spring Boot 3.5.5
- Spring Data JPA (with pagination support)
- Spring Validation
- MapStruct 1.6.3
- Lombok
- MySQL Connector
- Comprehensive Javadoc documentation

### Frontend
- React 19 with modern hooks (useState, useReducer, useCallback)
- Vite 7 for fast development and building
- React DatePicker for enhanced date selection
- Axios for HTTP client with error handling
- ESLint for code quality
- JSDoc documentation
- **Modern CSS Design System**:
  - CSS Custom Properties for consistent theming
  - Professional color palette and typography scale
  - Responsive grid layouts and flexbox
  - Smooth animations and transitions
  - Mobile-first responsive design
  - Accessibility-focused styling

## Development Notes

### Backend Architecture
- Minimum expense amount: $0.01
- Financial amounts use BigDecimal for precision
- Server-side pagination (default: 10 items per page)
- Date validation (2020-01-01 to today)
- CORS configured for React dev server (port 5173)
- Validation annotations ensure data integrity
- MapStruct handles entity-DTO mapping
- Comprehensive Javadoc documentation following industry standards

### Frontend Architecture
- Component-based architecture with reusable form components
- State management using useReducer pattern for complex state
- Custom hooks for API integration and data fetching
- Timezone-safe date handling with proper ISO string conversion
- CSS design system with custom properties for maintainability
- Mobile-responsive design with breakpoints at 768px and 480px
- Performance optimizations with useCallback for event handlers
- Comprehensive JSDoc documentation for all components and functions

### UI/UX Design Principles
- **Consistency** - Unified design language across all components
- **Accessibility** - Proper focus management and keyboard navigation
- **Performance** - Optimized animations and efficient CSS
- **Responsiveness** - Seamless experience across all device sizes
- **Visual Hierarchy** - Clear information architecture and typography
- **User Feedback** - Loading states, hover effects, and visual confirmations

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