~~# Fitness Tracker API - Endterm Project

## Project Overview

The **Fitness Tracker API** is a comprehensive Spring Boot RESTful application designed to help users monitor their fitness journey, track workouts, log exercises, and manage nutrition data. This project demonstrates professional backend architecture by integrating Design Patterns, Component Principles, SOLID principles, and modern Spring Boot practices.

### Key Features
- User management with profile customization
- Workout tracking with multiple workout types (Cardio, Strength, Yoga)
- Exercise library with detailed metrics
- Nutrition logging and calorie tracking
- RESTful API with full CRUD operations
- Robust exception handling
- Relational database integration

### Technologies Used
- **Framework**: Spring Boot 3.x
- **Language**: Java 17+
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Testing**: Postman
- **Architecture**: Layered (Controller → Service → Repository → Database)

---

## REST API Documentation

### Base URL
```
http://localhost:8080/api/v1
```

### Endpoints

#### 1. User Management

**GET** `/users` - Get all users
```bash
curl -X GET http://localhost:8080/api/v1/users
```

**Response:**
```json
[
  {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "age": 28,
    "weight": 75.5,
    "height": 180,
    "fitnessGoal": "WEIGHT_LOSS"
  }
]
```

**GET** `/users/{id}` - Get user by ID

**POST** `/users` - Create new user
```json
{
  "username": "jane_smith",
  "email": "jane@example.com",
  "password": "securePass123",
  "age": 25,
  "weight": 62.0,
  "height": 165,
  "fitnessGoal": "MUSCLE_GAIN"
}
```

**PUT** `/users/{id}` - Update user

**DELETE** `/users/{id}` - Delete user

---

#### 2. Workout Management

**GET** `/workouts` - Get all workouts

**GET** `/workouts/{id}` - Get workout by ID

**Response:**
```json
{
  "id": 1,
  "userId": 1,
  "workoutType": "CARDIO",
  "title": "Morning Run",
  "duration": 45,
  "caloriesBurned": 420,
  "intensity": "MEDIUM",
  "date": "2026-02-10T08:00:00",
  "notes": "5km run in the park"
}
```

**POST** `/workouts` - Create new workout
```json
{
  "userId": 1,
  "workoutType": "STRENGTH",
  "title": "Upper Body Strength",
  "duration": 60,
  "intensity": "HIGH",
  "exercises": [
    {
      "name": "Bench Press",
      "sets": 4,
      "reps": 10,
      "weight": 80
    },
    {
      "name": "Pull-ups",
      "sets": 3,
      "reps": 12
    }
  ]
}
```

**PUT** `/workouts/{id}` - Update workout

**DELETE** `/workouts/{id}` - Delete workout

**GET** `/workouts/user/{userId}` - Get workouts by user

---

#### 3. Exercise Library

**GET** `/exercises` - Get all exercises

**GET** `/exercises/{id}` - Get exercise by ID

**Response:**
```json
{
  "id": 1,
  "name": "Squat",
  "category": "STRENGTH",
  "muscleGroup": "LEGS",
  "equipment": "BARBELL",
  "difficulty": "INTERMEDIATE",
  "description": "Compound lower body exercise",
  "caloriesPerMinute": 8.5
}
```

**POST** `/exercises` - Create new exercise

**GET** `/exercises/category/{category}` - Filter by category

---

#### 4. Nutrition Tracking

**GET** `/nutrition` - Get all nutrition logs

**POST** `/nutrition` - Log nutrition entry
```json
{
  "userId": 1,
  "mealType": "BREAKFAST",
  "foodName": "Oatmeal with Berries",
  "calories": 350,
  "protein": 12,
  "carbs": 58,
  "fats": 8,
  "date": "2026-02-10T07:30:00"
}
```

**GET** `/nutrition/user/{userId}/date/{date}` - Get daily nutrition

---

### Postman Collection Screenshots

![Postman GET Users](docs/screenshots/get_users.png)
![Postman POST Workout](docs/screenshots/post_workout.png)
![Postman GET Exercises](docs/screenshots/get_exercises.png)

---

## Design Patterns Implementation

### 1. Singleton Pattern

**Purpose**: Ensure a single shared instance of critical system components.

#### ConfigurationManager
```java
@Component
public class ConfigurationManager {
    private static ConfigurationManager instance;
    private Properties config;
    
    private ConfigurationManager() {
        loadConfiguration();
    }
    
    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }
    
    public String getProperty(String key) {
        return config.getProperty(key);
    }
}
```

**Usage**: Manages application-wide configuration like API limits, default values, and feature flags.

#### DatabaseConnectionManager
```java
public class DatabaseConnectionManager {
    private static DatabaseConnectionManager instance;
    private DataSource dataSource;
    
    private DatabaseConnectionManager() {
        initializeDataSource();
    }
    
    public static synchronized DatabaseConnectionManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
        }
        return instance;
    }
}
```

**Usage**: Manages database connection pooling and ensures consistent database access across the application.

#### LoggingService
```java
@Component
public class LoggingService {
    private static LoggingService instance;
    private Logger logger = LoggerFactory.getLogger(LoggingService.class);
    
    private LoggingService() {}
    
    public static synchronized LoggingService getInstance() {
        if (instance == null) {
            instance = new LoggingService();
        }
        return instance;
    }
    
    public void logInfo(String message) {
        logger.info(message);
    }
    
    public void logError(String message, Exception e) {
        logger.error(message, e);
    }
}
```

**Usage**: Centralized logging for tracking API requests, errors, and system events.

---

### 2. Factory Pattern

**Purpose**: Create different types of workouts without exposing creation logic.

```java
public interface Workout {
    Long getId();
    String getWorkoutType();
    int calculateCaloriesBurned();
    String getIntensityLevel();
}

public class CardioWorkout implements Workout {
    private Long id;
    private int duration;
    private double distance;
    private double averageHeartRate;
    
    @Override
    public int calculateCaloriesBurned() {
        return (int) (duration * 10 * (averageHeartRate / 120));
    }
    
    @Override
    public String getWorkoutType() {
        return "CARDIO";
    }
}

public class StrengthWorkout implements Workout {
    private Long id;
    private int duration;
    private int totalSets;
    private int totalReps;
    private double totalWeight;
    
    @Override
    public int calculateCaloriesBurned() {
        return (int) (totalSets * totalReps * 0.5);
    }
    
    @Override
    public String getWorkoutType() {
        return "STRENGTH";
    }
}

public class YogaWorkout implements Workout {
    private Long id;
    private int duration;
    private String style; // Hatha, Vinyasa, Ashtanga
    private String difficultyLevel;
    
    @Override
    public int calculateCaloriesBurned() {
        return duration * 3; // Approximate
    }
    
    @Override
    public String getWorkoutType() {
        return "YOGA";
    }
}
```

#### WorkoutFactory
```java
@Component
public class WorkoutFactory {
    
    public Workout createWorkout(String type, Map<String, Object> params) {
        return switch (type.toUpperCase()) {
            case "CARDIO" -> createCardioWorkout(params);
            case "STRENGTH" -> createStrengthWorkout(params);
            case "YOGA" -> createYogaWorkout(params);
            default -> throw new IllegalArgumentException("Unknown workout type: " + type);
        };
    }
    
    private CardioWorkout createCardioWorkout(Map<String, Object> params) {
        CardioWorkout workout = new CardioWorkout();
        workout.setDuration((Integer) params.get("duration"));
        workout.setDistance((Double) params.get("distance"));
        workout.setAverageHeartRate((Double) params.get("heartRate"));
        return workout;
    }
    
    private StrengthWorkout createStrengthWorkout(Map<String, Object> params) {
        StrengthWorkout workout = new StrengthWorkout();
        workout.setDuration((Integer) params.get("duration"));
        workout.setTotalSets((Integer) params.get("sets"));
        workout.setTotalReps((Integer) params.get("reps"));
        return workout;
    }
    
    private YogaWorkout createYogaWorkout(Map<String, Object> params) {
        YogaWorkout workout = new YogaWorkout();
        workout.setDuration((Integer) params.get("duration"));
        workout.setStyle((String) params.get("style"));
        workout.setDifficultyLevel((String) params.get("difficulty"));
        return workout;
    }
}
```

**Benefits**:
- Easy to add new workout types without modifying existing code (Open/Closed Principle)
- Encapsulates object creation logic
- Returns base `Workout` interface for polymorphic handling

---

### 3. Builder Pattern

**Purpose**: Construct complex workout objects with many optional parameters.

```java
public class WorkoutBuilder {
    private Long userId;
    private String workoutType;
    private String title;
    private Integer duration;
    private String intensity;
    private LocalDateTime date;
    private String notes;
    private List<Exercise> exercises;
    private Integer caloriesBurned;
    private Double distance;
    private Integer averageHeartRate;
    
    public WorkoutBuilder() {
        this.exercises = new ArrayList<>();
        this.date = LocalDateTime.now();
    }
    
    public WorkoutBuilder userId(Long userId) {
        this.userId = userId;
        return this;
    }
    
    public WorkoutBuilder workoutType(String workoutType) {
        this.workoutType = workoutType;
        return this;
    }
    
    public WorkoutBuilder title(String title) {
        this.title = title;
        return this;
    }
    
    public WorkoutBuilder duration(Integer duration) {
        this.duration = duration;
        return this;
    }
    
    public WorkoutBuilder intensity(String intensity) {
        this.intensity = intensity;
        return this;
    }
    
    public WorkoutBuilder date(LocalDateTime date) {
        this.date = date;
        return this;
    }
    
    public WorkoutBuilder notes(String notes) {
        this.notes = notes;
        return this;
    }
    
    public WorkoutBuilder addExercise(Exercise exercise) {
        this.exercises.add(exercise);
        return this;
    }
    
    public WorkoutBuilder exercises(List<Exercise> exercises) {
        this.exercises = exercises;
        return this;
    }
    
    public WorkoutBuilder caloriesBurned(Integer calories) {
        this.caloriesBurned = calories;
        return this;
    }
    
    public WorkoutBuilder distance(Double distance) {
        this.distance = distance;
        return this;
    }
    
    public WorkoutBuilder averageHeartRate(Integer heartRate) {
        this.averageHeartRate = heartRate;
        return this;
    }
    
    public Workout build() {
        validateRequiredFields();
        
        Workout workout = WorkoutFactory.createWorkout(workoutType, buildParams());
        workout.setUserId(userId);
        workout.setTitle(title);
        workout.setDuration(duration);
        workout.setIntensity(intensity);
        workout.setDate(date);
        workout.setNotes(notes);
        
        return workout;
    }
    
    private void validateRequiredFields() {
        if (userId == null) throw new IllegalStateException("User ID is required");
        if (workoutType == null) throw new IllegalStateException("Workout type is required");
        if (duration == null) throw new IllegalStateException("Duration is required");
    }
    
    private Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("duration", duration);
        params.put("exercises", exercises);
        params.put("calories", caloriesBurned);
        params.put("distance", distance);
        params.put("heartRate", averageHeartRate);
        return params;
    }
}
```

**Usage Example**:
```java
Workout workout = new WorkoutBuilder()
    .userId(1L)
    .workoutType("STRENGTH")
    .title("Full Body Workout")
    .duration(75)
    .intensity("HIGH")
    .addExercise(new Exercise("Deadlift", 4, 8, 120))
    .addExercise(new Exercise("Squat", 4, 10, 100))
    .addExercise(new Exercise("Bench Press", 3, 10, 80))
    .notes("Felt strong today!")
    .build();
```

**Benefits**:
- Fluent API for readable code
- Handles optional parameters elegantly
- Validates required fields before object creation
- Separates construction logic from representation

---

## Component Principles

### REP - Reuse/Release Equivalence Principle

**Implementation**: Code is organized into reusable, independently releasable modules.

```
src/main/java/
├── patterns/           # Reusable design patterns
│   ├── singleton/
│   ├── factory/
│   └── builder/
├── repository/         # Database access layer (reusable)
├── service/           # Business logic (reusable)
└── utils/             # Utility classes (reusable)
    ├── validators/
    ├── converters/
    └── calculators/
```

**Example**: The `utils` package contains standalone utility classes that can be extracted and reused in other projects:
- `CalorieCalculator` - Calculates calories based on workout type
- `BMICalculator` - Calculates Body Mass Index
- `ValidationUtils` - Input validation logic

---

### CCP - Common Closure Principle

**Implementation**: Classes that change together are grouped together.

```
controller/
├── UserController.java
├── WorkoutController.java
├── ExerciseController.java
└── NutritionController.java

service/
├── UserService.java
├── WorkoutService.java
├── ExerciseService.java
└── NutritionService.java

repository/
├── UserRepository.java
├── WorkoutRepository.java
├── ExerciseRepository.java
└── NutritionRepository.java
```

**Rationale**:
- Changes to user-related features only affect `User*` classes
- Workout modifications are isolated to `Workout*` classes
- Each vertical slice (User, Workout, Exercise, Nutrition) changes independently

---

### CRP - Common Reuse Principle

**Implementation**: Modules don't depend on classes they don't use.

```
model/
├── domain/            # Core business entities
│   ├── User.java
│   ├── Workout.java
│   └── Exercise.java
├── dto/              # Data transfer objects (API layer only)
│   ├── UserDTO.java
│   └── WorkoutDTO.java
└── enums/            # Shared enumerations
    ├── WorkoutType.java
    ├── Intensity.java
    └── FitnessGoal.java
```

**Example**:
- The `repository` layer depends only on `domain` models
- The `controller` layer uses `dto` but not internal domain details
- `service` layer converts between DTOs and domain models
- No layer is forced to depend on unused classes

---

## SOLID & OOP Summary

### Single Responsibility Principle (SRP)
- **Controllers**: Handle HTTP requests/responses only
- **Services**: Contain business logic only
- **Repositories**: Manage database operations only
- **DTOs**: Transfer data between layers only

### Open/Closed Principle (OCP)
- `WorkoutFactory` allows adding new workout types without modifying existing code
- Interface-based design allows extension through implementation

### Liskov Substitution Principle (LSP)
- All workout types (`CardioWorkout`, `StrengthWorkout`, `YogaWorkout`) can substitute the `Workout` interface
- Polymorphic handling in services

### Interface Segregation Principle (ISP)
- Small, focused interfaces rather than large monolithic ones
- `WorkoutRepository` interface has specific methods, not generic CRUD bloat

### Dependency Inversion Principle (DIP)
- Controllers depend on `Service` interfaces, not concrete implementations
- Services depend on `Repository` interfaces
- Spring Boot's dependency injection enforces this naturally

### Advanced OOP Features
- **Abstraction**: `Workout` interface abstracts different workout implementations
- **Encapsulation**: Private fields with public getters/setters
- **Inheritance**: Workout subclasses inherit from base `Workout` interface
- **Polymorphism**: Factory returns base `Workout` type, actual type determined at runtime

---

## Database Schema

### Entity Relationship Diagram

```
┌─────────────────┐
│      USER       │
├─────────────────┤
│ id (PK)         │
│ username        │
│ email           │
│ password        │
│ age             │
│ weight          │
│ height          │
│ fitness_goal    │
│ created_at      │
└────────┬────────┘
         │
         │ 1:N
         │
┌────────▼────────┐
│    WORKOUT      │
├─────────────────┤
│ id (PK)         │
│ user_id (FK)    │
│ workout_type    │
│ title           │
│ duration        │
│ calories_burned │
│ intensity       │
│ date            │
│ notes           │
└────────┬────────┘
         │
         │ M:N
         │
┌────────▼────────────┐       ┌─────────────────┐
│ WORKOUT_EXERCISE    │       │    EXERCISE     │
├─────────────────────┤       ├─────────────────┤
│ workout_id (FK)     │───────│ id (PK)         │
│ exercise_id (FK)    │       │ name            │
│ sets                │       │ category        │
│ reps                │       │ muscle_group    │
│ weight              │       │ equipment       │
└─────────────────────┘       │ difficulty      │
                               │ description     │
                               └─────────────────┘

┌─────────────────┐
│   NUTRITION     │
├─────────────────┤
│ id (PK)         │
│ user_id (FK)    │
│ meal_type       │
│ food_name       │
│ calories        │
│ protein         │
│ carbs           │
│ fats            │
│ date            │
└─────────────────┘
```

### SQL Schema

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    age INTEGER,
    weight DECIMAL(5,2),
    height DECIMAL(5,2),
    fitness_goal VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE workouts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    workout_type VARCHAR(20) NOT NULL,
    title VARCHAR(100) NOT NULL,
    duration INTEGER NOT NULL,
    calories_burned INTEGER,
    intensity VARCHAR(20),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notes TEXT
);

CREATE TABLE exercises (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(20) NOT NULL,
    muscle_group VARCHAR(50),
    equipment VARCHAR(50),
    difficulty VARCHAR(20),
    description TEXT,
    calories_per_minute DECIMAL(4,2)
);

CREATE TABLE workout_exercises (
    workout_id BIGINT REFERENCES workouts(id) ON DELETE CASCADE,
    exercise_id BIGINT REFERENCES exercises(id) ON DELETE CASCADE,
    sets INTEGER,
    reps INTEGER,
    weight DECIMAL(5,2),
    PRIMARY KEY (workout_id, exercise_id)
);

CREATE TABLE nutrition (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    meal_type VARCHAR(20) NOT NULL,
    food_name VARCHAR(100) NOT NULL,
    calories INTEGER NOT NULL,
    protein DECIMAL(5,2),
    carbs DECIMAL(5,2),
    fats DECIMAL(5,2),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_workouts_user ON workouts(user_id);
CREATE INDEX idx_workouts_date ON workouts(date);
CREATE INDEX idx_nutrition_user ON nutrition(user_id);
CREATE INDEX idx_nutrition_date ON nutrition(date);
```

---

## System Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                         CLIENT LAYER                          │
│            (Postman / Mobile App / Web Frontend)             │
└───────────────────────────┬──────────────────────────────────┘
                            │
                            │ HTTP Requests (JSON)
                            │
┌───────────────────────────▼──────────────────────────────────┐
│                    REST CONTROLLER LAYER                      │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐       │
│  │    User      │  │   Workout    │  │  Exercise    │       │
│  │ Controller   │  │  Controller  │  │  Controller  │       │
│  └──────────────┘  └──────────────┘  └──────────────┘       │
│                  @RestController                             │
│            (Request Mapping, Validation, DTOs)               │
└───────────────────────────┬──────────────────────────────────┘
                            │
                            │ Method Calls
                            │
┌───────────────────────────▼──────────────────────────────────┐
│                      SERVICE LAYER                            │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐       │
│  │    User      │  │   Workout    │  │  Exercise    │       │
│  │   Service    │  │   Service    │  │   Service    │       │
│  └──────────────┘  └──────────────┘  └──────────────┘       │
│                    @Service                                  │
│         (Business Logic, Validation, Patterns)               │
│  ┌─────────────────────────────────────────────┐            │
│  │  Design Patterns Integration:                │            │
│  │  - WorkoutFactory                            │            │
│  │  - WorkoutBuilder                            │            │
│  │  - ConfigurationManager (Singleton)          │            │
│  │  - LoggingService (Singleton)                │            │
│  └─────────────────────────────────────────────┘            │
└───────────────────────────┬──────────────────────────────────┘
                            │
                            │ Database Calls
                            │
┌───────────────────────────▼──────────────────────────────────┐
│                    REPOSITORY LAYER                           │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐       │
│  │    User      │  │   Workout    │  │  Exercise    │       │
│  │ Repository   │  │ Repository   │  │ Repository   │       │
│  └──────────────┘  └──────────────┘  └──────────────┘       │
│                 @Repository / JPA                            │
│              (Data Access, SQL Queries)                      │
│  ┌─────────────────────────────────────────────┐            │
│  │  DatabaseConnectionManager (Singleton)       │            │
│  └─────────────────────────────────────────────┘            │
└───────────────────────────┬──────────────────────────────────┘
                            │
                            │ JDBC/JPA
                            │
┌───────────────────────────▼──────────────────────────────────┐
│                      DATABASE LAYER                           │
│                     PostgreSQL Database                       │
│  ┌─────────┐ ┌─────────┐ ┌──────────┐ ┌──────────┐         │
│  │  users  │ │workouts │ │exercises │ │nutrition │         │
│  └─────────┘ └─────────┘ └──────────┘ └──────────┘         │
└──────────────────────────────────────────────────────────────┘

                      CROSS-CUTTING CONCERNS
┌──────────────────────────────────────────────────────────────┐
│  Exception Handling  │  Logging  │  Validation  │  Security  │
└──────────────────────────────────────────────────────────────┘
```

### Sample Test Flow

1. **Create a User**
```
POST http://localhost:8080/api/v1/users
Body: { "username": "test_user", "email": "test@example.com", ... }
```

2. **Create a Workout**
```
POST http://localhost:8080/api/v1/workouts
Body: { "userId": 1, "workoutType": "CARDIO", ... }
```

3. **Add Exercises**
```
POST http://localhost:8080/api/v1/exercises
Body: { "name": "Squat", "category": "STRENGTH", ... }
```

4. **Get User's Workouts**
```
GET http://localhost:8080/api/v1/workouts/user/1
```

5. **Log Nutrition**
```
POST http://localhost:8080/api/v1/nutrition
Body: { "userId": 1, "mealType": "LUNCH", ... }
```

---

## Exception Handling

### Global Exception Handler

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InvalidWorkoutException.class)
    public ResponseEntity<ErrorResponse> handleInvalidWorkout(InvalidWorkoutException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An unexpected error occurred",
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

### Custom Exceptions
- `UserNotFoundException`
- `WorkoutNotFoundException`
- `InvalidWorkoutException`
- `DuplicateUserException`
- `DatabaseConnectionException`

---

## Reflection

### What Worked Well

1. **Design Patterns Integration**: The Factory and Builder patterns significantly improved code quality. Creating different workout types became clean and extensible, and the Builder pattern made complex object construction readable and maintainable.

2. **Layered Architecture**: The strict separation between Controller, Service, and Repository layers enforced SOLID principles naturally and made testing each layer independently straightforward.

3. **Component Principles**: Organizing code by CCP and CRP made the codebase intuitive. When adding new features, it was clear where new classes belonged, and changes were localized to specific packages.

4. **Spring Boot**: The framework's dependency injection and auto-configuration reduced boilerplate significantly, allowing focus on business logic rather than infrastructure code.

### Challenges Faced

1. **Singleton in Spring**: Initially struggled with implementing traditional Singleton pattern in a Spring-managed environment. Learned that Spring beans are singletons by default, but implemented manual Singleton for non-Spring-managed utilities.

2. **Factory vs Builder**: Determining when to use Factory (for type selection) vs Builder (for complex construction) required careful thought. Eventually established clear guidelines: Factory for polymorphic creation, Builder for parameter-heavy construction.

3. **DTO Mapping**: Converting between DTOs and domain entities became verbose. Considered using MapStruct or ModelMapper for future iterations.

4. **Database Design**: Modeling the many-to-many relationship between workouts and exercises required a junction table, which added complexity to queries.

### Lessons Learned

1. **Design Patterns Aren't Silver Bullets**: While patterns solved specific problems elegantly, over-engineering simple features with unnecessary patterns would have added complexity without benefit.

2. **Component Principles Guide Architecture**: Following REP, CCP, and CRP from the start made refactoring easier. Changes propagated predictably through the codebase.

3. **API Design Matters**: Spending time designing intuitive, RESTful endpoints upfront saved debugging time later. Consistent naming conventions and proper HTTP methods made the API self-documenting.

4. **Exception Handling is Critical**: Global exception handling with custom exceptions provided much better error messages than generic Spring exceptions, improving developer experience.

### Future Enhancements

- Add authentication and authorization (Spring Security + JWT)
- Implement pagination for large datasets
- Add caching layer (Redis) for frequently accessed data
- Create comprehensive unit and integration tests
- Add API documentation with Swagger/OpenAPI
- Implement workout plan templates
- Add data analytics and progress tracking dashboard
- Integrate with fitness device APIs (Fitbit, Apple Health)

---

## Acknowledgments

- Spring Boot Documentation
- Design Patterns: Elements of Reusable Object-Oriented Software (Gang of Four)
- Clean Architecture by Robert C. Martin
- Course instructors and teaching assistants~~
