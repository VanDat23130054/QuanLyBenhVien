# Hospital Management System - DAO Pattern Architecture

## Overview
This project implements the **Data Access Object (DAO) Pattern** for managing database operations. The DAO pattern is a structural pattern that abstracts and encapsulates all access to the data source, providing a clean separation between the business logic layer and the data persistence layer.

## Architecture Layers

### 1. **Presentation Layer (View)**
- `AppointmentView.java`
- `PatientView.java`
- `BaseView.java`
- **Responsibility**: Handles user interface and user interactions

### 2. **ViewModel Layer (MVVM Pattern)**
- `AppointmentViewModel.java`
- `PatientViewModel.java`
- `BaseViewModel.java`
- **Responsibility**: 
  - Manages state and data for the UI
  - Provides observable data binding
  - Delegates business logic to Service layer

### 3. **Service Layer (Business Logic)**
- `PatientService.java`
- `AppointmentService.java`
- **Responsibility**:
  - Implements business logic and validation
  - Orchestrates operations between ViewModels and DAOs
  - Handles complex workflows
  - Provides clean API for data operations
  - Validates input data

### 4. **DAO Layer (Data Access)**
- `IBaseDAO<T>` - Base DAO interface defining common CRUD operations
- `IPatientDAO` - Patient-specific data access interface
- `IAppointmentDAO` - Appointment-specific data access interface
- `PatientDAO` - Patient DAO implementation
- `AppointmentDAO` - Appointment DAO implementation
- `DAOFactory` - Factory for creating DAO instances
- **Responsibility**:
  - Encapsulates all database operations
  - Provides CRUD operations (Create, Read, Update, Delete)
  - Handles SQL queries and ResultSet mapping
  - Returns domain objects (Patient, Appointment)

### 5. **Database Layer**
- `DatabaseConnection.java` - Manages database connections
- **Responsibility**: Handles SQL Server connectivity

## Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                      VIEW LAYER (Swing UI)                       │
│              PatientView, AppointmentView                         │
└──────────────────┬───────────────────────────────────────────────┘
                   │
┌──────────────────▼───────────────────────────────────────────────┐
│                   VIEWMODEL LAYER (MVVM)                          │
│     PatientViewModel, AppointmentViewModel, BaseViewModel         │
└──────────────────┬───────────────────────────────────────────────┘
                   │
┌──────────────────▼───────────────────────────────────────────────┐
│                  SERVICE LAYER (Business Logic)                   │
│         PatientService, AppointmentService                        │
└──────────────────┬───────────────────────────────────────────────┘
                   │
┌──────────────────▼───────────────────────────────────────────────┐
│                    DAO LAYER (Data Access)                        │
│  ┌──────────────────────────────────────────────────────────┐    │
│  │ DAOFactory                                               │    │
│  │ - getPatientDAO(): IPatientDAO                           │    │
│  │ - getAppointmentDAO(): IAppointmentDAO                   │    │
│  └──────────────────────────────────────────────────────────┘    │
│                         ▲                                         │
│         ┌───────────────┼───────────────┐                        │
│         │               │               │                        │
│  ┌──────┴────────┐ ┌────┴──────────┐ ┌─┴────────────────┐       │
│  │IPatientDAO    │ │IAppointmentDAO│ │ IBaseDAO<T>     │       │
│  └──────┬────────┘ └────┬──────────┘ └─────────────────┘       │
│         │               │                                        │
│  ┌──────▼────────┐ ┌────▼──────────┐                           │
│  │PatientDAO     │ │AppointmentDAO │                           │
│  │- getById()    │ │- getById()    │                           │
│  │- getAll()     │ │- getAll()     │                           │
│  │- add()        │ │- add()        │                           │
│  │- update()     │ │- update()     │                           │
│  │- delete()     │ │- delete()     │                           │
│  │- search()     │ │- getByStatus()│                           │
│  │+ other methods│ │+ other methods│                           │
│  └──────┬────────┘ └────┬──────────┘                           │
│         │               │                                        │
└─────────┼───────────────┼────────────────────────────────────────┘
          │               │
┌─────────▼───────────────▼────────────────────────────────────────┐
│                 DATABASE LAYER                                    │
│     DatabaseConnection (SQL Server via JDBC)                     │
└────────────────────────────────────────────────────────────────┘
```

## Key Design Patterns Used

### 1. **DAO Pattern**
- Encapsulates database access logic
- Provides abstraction from database implementation
- Allows easy switching between different database implementations

### 2. **Factory Pattern**
- `DAOFactory` creates DAO instances
- Centralizes object creation
- Simplifies client code

### 3. **MVVM Pattern** (already implemented)
- Separates UI from business logic
- Provides data binding capabilities

### 4. **Dependency Injection**
- Services and ViewModels accept interfaces
- Supports testing with mock implementations

## Usage Examples

### Creating Services
```java
// Using factory
PatientService patientService = new PatientService();
AppointmentService appointmentService = new AppointmentService();

// Or with dependency injection
IPatientDAO patientDAO = new PatientDAO();
PatientService patientService = new PatientService(patientDAO);
```

### CRUD Operations
```java
// Create
Patient patient = new Patient(...);
patientService.createPatient(patient);

// Read
Patient retrievedPatient = patientService.getPatientById(1);
List<Patient> allPatients = patientService.getAllPatients();

// Update
patient.setFullName("New Name");
patientService.updatePatient(patient);

// Delete
patientService.deletePatient(patientId);

// Search
List<Patient> searchResults = patientService.searchPatients("John");
```

## Benefits of This Architecture

1. **Separation of Concerns**: Each layer has a specific responsibility
2. **Testability**: Easy to mock DAO implementations for unit testing
3. **Maintainability**: Changes to database logic don't affect UI code
4. **Scalability**: Easy to add new entities and operations
5. **Flexibility**: Can easily switch database implementations
6. **Reusability**: Services and DAOs can be reused across different ViewModels
7. **Business Logic Centralization**: All business rules are in the Service layer

## Future Enhancements

1. Add transaction management support
2. Implement connection pooling
3. Add caching layer for performance optimization
4. Implement lazy loading for related entities
5. Add audit logging for all data modifications
6. Implement soft delete functionality
7. Add pagination support for large datasets
8. Implement async operations for better responsiveness

## File Structure

```
src/main/java/com/hospital/
├── dao/
│   ├── IBaseDAO.java              (Base DAO interface)
│   ├── IPatientDAO.java           (Patient DAO interface)
│   ├── IAppointmentDAO.java       (Appointment DAO interface)
│   ├── PatientDAO.java            (Patient DAO implementation)
│   ├── AppointmentDAO.java        (Appointment DAO implementation)
│   └── DAOFactory.java            (DAO Factory)
├── service/
│   ├── PatientService.java        (Patient Service)
│   └── AppointmentService.java    (Appointment Service)
├── viewmodel/
│   ├── BaseViewModel.java
│   ├── PatientViewModel.java
│   └── AppointmentViewModel.java
├── view/
│   ├── BaseView.java
│   ├── PatientView.java
│   └── AppointmentView.java
├── model/
│   ├── Patient.java
│   └── Appointment.java
├── repository/                     (Legacy - can be deprecated)
│   ├── IPatientRepository.java
│   ├── IAppointmentRepository.java
│   ├── PatientRepository.java
│   └── AppointmentRepository.java
└── database/
    └── DatabaseConnection.java
```

## Migration Guide

If you have existing code using the old repository pattern, here's how to migrate:

### Before (Old Repository Pattern):
```java
IPatientRepository patientRepository = new PatientRepository();
List<Patient> patients = patientRepository.getAllPatients();
```

### After (New DAO Pattern with Service):
```java
PatientService patientService = new PatientService();
List<Patient> patients = patientService.getAllPatients();
```

Or with direct DAO usage:
```java
IPatientDAO patientDAO = DAOFactory.getPatientDAO();
List<Patient> patients = patientDAO.getAll();
```

## Conclusion

This architecture provides a robust, maintainable, and scalable foundation for the Hospital Management System. The clear separation of concerns makes the codebase easier to understand, test, and extend.
