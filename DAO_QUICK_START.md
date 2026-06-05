# DAO Pattern Implementation Guide - Hospital Management System

## Quick Start

This project has been set up to use the **DAO (Data Access Object) Pattern** for managing database operations. This document provides a quick reference for using the new structure.

## Project Structure

```
com/hospital/
├── dao/                              ← NEW: Data Access Layer
│   ├── IBaseDAO.java                 (Base DAO interface)
│   ├── IPatientDAO.java              (Patient DAO interface)
│   ├── IAppointmentDAO.java          (Appointment DAO interface)
│   ├── PatientDAO.java               (Patient DAO implementation)
│   ├── AppointmentDAO.java           (Appointment DAO implementation)
│   └── DAOFactory.java               (DAO Factory)
│
├── service/                          ← NEW: Business Logic Layer
│   ├── PatientService.java
│   └── AppointmentService.java
│
├── viewmodel/                        ← UPDATED: Now uses Service layer
│   ├── BaseViewModel.java
│   ├── PatientViewModel.java
│   └── AppointmentViewModel.java
│
├── view/                             ← View Layer (unchanged)
│   ├── BaseView.java
│   ├── PatientView.java
│   └── AppointmentView.java
│
├── model/                            ← Domain Models (unchanged)
│   ├── Patient.java
│   └── Appointment.java
│
├── database/                         ← Database Connection (unchanged)
│   └── DatabaseConnection.java
│
└── repository/                       ← LEGACY (can be deprecated)
    ├── IPatientRepository.java
    ├── IAppointmentRepository.java
    ├── PatientRepository.java
    └── AppointmentRepository.java
```

## Architecture Flow

```
User Interface (View) 
    ↓
ViewModel (State Management & Property Binding)
    ↓
Service Layer (Business Logic & Validation)
    ↓
DAO Layer (Database Operations)
    ↓
DatabaseConnection (JDBC)
    ↓
SQL Server Database
```

## Using the DAO Pattern

### 1. Direct DAO Usage (Low-level)

```java
import com.hospital.dao.DAOFactory;
import com.hospital.dao.IPatientDAO;
import com.hospital.model.Patient;

// Get DAO instance from factory
IPatientDAO patientDAO = DAOFactory.getPatientDAO();

// Create
Patient patient = new Patient();
patient.setFullName("John Doe");
patient.setPhoneNumber("0123456789");
patientDAO.add(patient);

// Read
Patient retrieved = patientDAO.getById(1);
List<Patient> all = patientDAO.getAll();

// Update
patient.setFullName("Jane Doe");
patientDAO.update(patient);

// Delete
patientDAO.delete(1);

// Search
List<Patient> results = patientDAO.search("John");
```

### 2. Service Layer Usage (Recommended)

```java
import com.hospital.service.PatientService;
import com.hospital.model.Patient;

// Create service instance
PatientService patientService = new PatientService();

// CRUD operations with validation
try {
    Patient patient = new Patient();
    patient.setFullName("John Doe");
    patient.setPhoneNumber("0123456789");
    patient.setEmail("john@example.com");
    
    // Service validates data and sets registration date
    patientService.createPatient(patient);
    
    // Get all patients
    List<Patient> patients = patientService.getAllPatients();
    
    // Search
    List<Patient> results = patientService.searchPatients("John");
    
    // Get by phone
    Patient byPhone = patientService.getPatientByPhoneNumber("0123456789");
    
} catch (IllegalArgumentException e) {
    System.out.println("Validation error: " + e.getMessage());
}
```

### 3. ViewModel Usage (UI Layer)

```java
import com.hospital.viewmodel.PatientViewModel;
import com.hospital.model.Patient;

// Create ViewModel
PatientViewModel viewModel = new PatientViewModel();

// Load data (automatically handles loading state)
viewModel.loadAllPatients();

// Access data
List<Patient> patients = viewModel.getPatients();
String status = viewModel.getStatusMessage();

// Add patient
Patient newPatient = new Patient();
newPatient.setFullName("Jane Smith");
viewModel.addPatient(newPatient);

// Listen for property changes
viewModel.addPropertyChangeListener("patients", evt -> {
    List<Patient> updated = (List<Patient>) evt.getNewValue();
    System.out.println("Patients updated: " + updated.size());
});
```

## Features

### PatientDAO Methods
- `getById(int id)` - Get patient by ID
- `getAll()` - Get all patients
- `add(Patient)` - Create new patient
- `update(Patient)` - Update patient
- `delete(int id)` - Delete patient
- `search(String term)` - Search by name, phone, email
- `getByPhoneNumber(String phone)` - Get patient by phone
- `getByEmail(String email)` - Get patient by email

### AppointmentDAO Methods
- `getById(int id)` - Get appointment by ID
- `getAll()` - Get all appointments
- `add(Appointment)` - Create new appointment
- `update(Appointment)` - Update appointment
- `delete(int id)` - Delete appointment
- `getByPatientId(int patientId)` - Get patient's appointments
- `getByDoctorId(int doctorId)` - Get doctor's appointments
- `getByDateRange(LocalDateTime, LocalDateTime)` - Get appointments in date range
- `getByStatus(String status)` - Get appointments by status

### PatientService Methods
- `getPatientById(int patientId)` - Get patient by ID
- `getAllPatients()` - Get all patients
- `createPatient(Patient)` - Create with validation
- `updatePatient(Patient)` - Update with validation
- `deletePatient(int patientId)` - Delete patient
- `searchPatients(String term)` - Search patients
- `getPatientByPhoneNumber(String phone)` - Get by phone
- `getPatientByEmail(String email)` - Get by email

### AppointmentService Methods
- `getAppointmentById(int id)` - Get appointment by ID
- `getAllAppointments()` - Get all appointments
- `createAppointment(Appointment)` - Create with validation
- `updateAppointment(Appointment)` - Update with validation
- `deleteAppointment(int id)` - Delete appointment
- `getAppointmentsByPatientId(int patientId)` - Get patient's appointments
- `getAppointmentsByDoctorId(int doctorId)` - Get doctor's appointments
- `getAppointmentsByDateRange(LocalDateTime, LocalDateTime)` - Get by date range
- `getAppointmentsByStatus(String status)` - Get by status
- `isAppointmentSlotAvailable(int doctorId, LocalDateTime)` - Check availability

## Validation

The Service layer provides automatic validation:

### Patient Validation
- Name cannot be empty
- Phone number must be digits only (if provided)
- Email must be valid format (if provided)

### Appointment Validation
- Patient ID must be valid (> 0)
- Doctor ID must be valid (> 0)
- Appointment date/time cannot be null
- Appointment date/time must be in the future
- Status cannot be empty

## Error Handling

All services throw `IllegalArgumentException` for validation errors:

```java
try {
    Patient patient = new Patient();
    // Invalid - no name
    patientService.createPatient(patient);
} catch (IllegalArgumentException e) {
    System.out.println("Error: " + e.getMessage()); 
    // Output: "Error: Patient name cannot be empty"
}
```

## Testing Support

The Service layer and DAOs support dependency injection for easy testing:

```java
// Unit test with mock DAO
@Test
public void testPatientService() {
    // Mock the DAO
    IPatientDAO mockDAO = mock(IPatientDAO.class);
    
    // Create service with mock
    PatientService service = new PatientService(mockDAO);
    
    // Use service as normal
    service.getAllPatients();
    
    // Verify DAO was called
    verify(mockDAO).getAll();
}
```

## Migration from Repository Pattern

If you have existing code using the old Repository pattern, here's how to migrate:

### Before (Old):
```java
IPatientRepository repo = new PatientRepository();
List<Patient> patients = repo.getAllPatients();
```

### After (New - with Service):
```java
PatientService service = new PatientService();
List<Patient> patients = service.getAllPatients();
```

### After (New - with DAO):
```java
IPatientDAO dao = DAOFactory.getPatientDAO();
List<Patient> patients = dao.getAll();
```

## Best Practices

1. **Use Services in ViewModels** - Services handle validation and business logic
2. **Use DAOs in Services** - Services orchestrate DAOs for complex operations
3. **Use DAOFactory** - Get DAO instances from factory, not `new` operator
4. **Handle Exceptions** - Catch and handle validation exceptions
5. **Use Dependency Injection** - Pass interfaces to constructors for testability
6. **Validate Early** - Let services catch invalid data before database operations

## Future Enhancements

1. Add transaction support for multi-step operations
2. Implement connection pooling for better performance
3. Add caching layer for frequently accessed data
4. Implement soft delete functionality
5. Add pagination support for large datasets
6. Implement audit logging for data modifications
7. Add async/Future support for long-running operations
8. Implement lazy loading for related entities

## Files Added/Modified

### New Files Created:
- `dao/IBaseDAO.java`
- `dao/IPatientDAO.java`
- `dao/IAppointmentDAO.java`
- `dao/PatientDAO.java`
- `dao/AppointmentDAO.java`
- `dao/DAOFactory.java`
- `service/PatientService.java`
- `service/AppointmentService.java`
- `DAO_PATTERN_GUIDE.md` (this file)
- `DAO_QUICK_START.md` (quick reference)

### Modified Files:
- `viewmodel/PatientViewModel.java` - Now uses PatientService instead of PatientRepository
- `viewmodel/AppointmentViewModel.java` - Now uses AppointmentService instead of AppointmentRepository

### Legacy Files (Still Available):
- `repository/IPatientRepository.java`
- `repository/IAppointmentRepository.java`
- `repository/PatientRepository.java`
- `repository/AppointmentRepository.java`

These can be kept for backward compatibility or removed if fully migrated.

## Summary

Your project now implements the DAO Pattern with:
- ✅ Base DAO interface for common CRUD operations
- ✅ Specific DAO interfaces for Patient and Appointment
- ✅ DAO implementations with SQL Server integration
- ✅ DAOFactory for centralized instantiation
- ✅ Service layer with business logic and validation
- ✅ Integration with existing MVVM architecture
- ✅ Full backward compatibility with old repository pattern
- ✅ Support for dependency injection and testing

The architecture is now much more maintainable, testable, and scalable!
