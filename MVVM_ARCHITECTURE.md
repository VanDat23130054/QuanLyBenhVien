# Hospital Management System - MVVM Architecture Documentation

## Overview
The application has been restructured to use the **MVVM (Model-View-ViewModel)** design pattern, which provides clear separation of concerns and improves maintainability, testability, and scalability.

## Architecture Layers

### 1. Model Layer (`com.hospital.model`)
Contains domain entities representing core business concepts.

**Classes:**
- `Patient`: Represents a patient entity with fields like patientId, fullName, dateOfBirth, gender, phoneNumber, email, address, medicalHistory, registrationDate
- `Appointment`: Represents an appointment entity with fields like appointmentId, patientId, doctorId, appointmentDateTime, reason, status, notes

**Responsibility:**
- Define data structures
- Hold business data
- Be independent of UI and database layers
- Provide getters/setters for data access

### 2. Repository Layer (`com.hospital.repository`)
Handles all data persistence operations (CRUD - Create, Read, Update, Delete) and provides an abstraction over the database layer.

**Interfaces:**
- `IPatientRepository`: Defines contract for patient data operations
- `IAppointmentRepository`: Defines contract for appointment data operations

**Implementations:**
- `PatientRepository`: Implements patient CRUD operations using SQL Server
- `AppointmentRepository`: Implements appointment CRUD operations using SQL Server

**Key Methods:**
- Get entity by ID
- Get all entities
- Add/Create new entity
- Update existing entity
- Delete entity
- Search/filter entities

**Responsibility:**
- Abstract database access logic
- Provide clean data access API to ViewModels
- Handle SQL queries and result mapping
- Manage database connections through `DatabaseConnection`

### 3. ViewModel Layer (`com.hospital.viewmodel`)
Acts as the bridge between Views and Models, handling business logic and state management.

**Base Class:**
- `BaseViewModel`: Abstract base class providing property change notification support
  - Uses `PropertyChangeSupport` for observer pattern
  - Allows Views to subscribe to property changes
  - Enables loose coupling between View and ViewModel

**Concrete ViewModels:**
- `PatientViewModel`: Manages patient-related operations and state
  - `loadAllPatients()`: Fetch all patients from repository
  - `searchPatients(searchTerm)`: Search patients by name, phone, or email
  - `addPatient(patient)`: Create new patient
  - `updatePatient(patient)`: Update existing patient
  - `deletePatient(patientId)`: Delete patient
  - Properties: patients (list), selectedPatient, statusMessage, isLoading

- `AppointmentViewModel`: Manages appointment-related operations and state
  - `loadAllAppointments()`: Fetch all appointments
  - `loadAppointmentsByPatient(patientId)`: Get appointments for specific patient
  - `loadAppointmentsByDoctor(doctorId)`: Get appointments for specific doctor
  - `loadAppointmentsByDateRange()`: Get appointments in date range
  - `addAppointment(appointment)`: Schedule new appointment
  - `updateAppointment(appointment)`: Update appointment
  - `deleteAppointment(appointmentId)`: Cancel appointment
  - Properties: appointments (list), selectedAppointment, statusMessage, isLoading

**Responsibility:**
- Execute business logic
- Manage application state
- Transform data for View consumption
- Handle user actions from View
- Notify Views of state changes
- Provide error handling and status messages

### 4. View Layer (`com.hospital.view`)
Presents data to users and captures user input.

**Base Class:**
- `BaseView`: Abstract base class extending JPanel
  - Common UI constants (PADDING, COMPONENT_HEIGHT)
  - Helper methods for user dialogs (showInfo, showError, showConfirmation)
  - Abstract methods: initializeUI(), refresh()

**Concrete Views:**
- `PatientView`: Displays and manages patients
  - Table showing all patients (ID, Name, DOB, Gender, Phone, Email, Address)
  - Search functionality
  - Action buttons: Add, Edit, Delete, Refresh
  - Responds to ViewModel property changes
  - Updates UI when data changes

- `AppointmentView`: Displays and manages appointments
  - Table showing all appointments (ID, Patient ID, Doctor ID, Date/Time, Reason, Status, Notes)
  - Action buttons: Schedule, Edit, Cancel, Refresh
  - Responds to ViewModel property changes
  - Updates UI when data changes

- `MainMenu`: Application main window
  - Contains tabbed interface (Patients, Appointments)
  - Menu bar with File, Patients, Appointments, Help menus
  - Initializes ViewModels on startup
  - Loads initial data (patients and appointments)

**Responsibility:**
- Display data from ViewModel
- Capture user input and delegate to ViewModel
- Observe ViewModel property changes
- Update UI based on state changes
- Provide user feedback and dialogs

### 5. Database Layer (`com.hospital.database`)
Handles low-level database connectivity.

**Class:**
- `DatabaseConnection`: Manages SQL Server connections
  - Singleton-like pattern for connection management
  - Lazy initialization (creates connection when first needed)
  - Connection pooling (reuses existing connection)

**Responsibility:**
- Establish and maintain database connections
- Provide connection to Repository layer

## Data Flow

### Reading Data (e.g., Load Patients)
```
View.refresh()
  ↓
ViewModel.loadAllPatients()
  ↓
Repository.getAllPatients()
  ↓
DatabaseConnection.getConnection()
  ↓
SQL Query execution
  ↓
ResultSet → Patient objects
  ↓
Repository returns List<Patient>
  ↓
ViewModel.setPatients() → firePropertyChange()
  ↓
View.propertyChange() → updateTable()
  ↓
User sees updated table
```

### Writing Data (e.g., Add Patient)
```
View.handleAddPatient()
  ↓
User fills form and confirms
  ↓
View calls ViewModel.addPatient(patient)
  ↓
ViewModel.addPatient()
  ↓
Repository.addPatient()
  ↓
DatabaseConnection.getConnection()
  ↓
SQL INSERT execution
  ↓
ViewModel updates state and notifies View
  ↓
View refreshes table with new data
  ↓
User sees confirmation message and updated table
```

## Benefits of MVVM Architecture

### 1. **Separation of Concerns**
- Models focus on data and business logic
- Views focus on presentation
- ViewModels focus on orchestration and state

### 2. **Testability**
- ViewModels can be tested independently of Views
- Repositories can be mocked for unit testing
- Business logic is separate from UI code

### 3. **Reusability**
- Same ViewModel can support multiple Views
- Same Repository can support multiple ViewModels
- Models are completely independent

### 4. **Maintainability**
- Clear structure makes code easier to understand
- Changes to UI don't affect business logic
- Changes to business logic don't require UI changes

### 5. **Loose Coupling**
- Views and Models don't directly reference each other
- Property change notifications enable loose coupling
- Dependencies are managed through constructor injection

## How to Add New Features

### Example: Add Doctor Management

#### Step 1: Create Model
Create `Doctor.java` in `com.hospital.model`:
```java
public class Doctor {
    private int doctorId;
    private String fullName;
    private String specialization;
    private String phoneNumber;
    private String email;
    // ... getters and setters
}
```

#### Step 2: Create Repository Interface & Implementation
Create `IDoctorRepository.java` in `com.hospital.repository`:
```java
public interface IDoctorRepository {
    Doctor getDoctorById(int doctorId);
    List<Doctor> getAllDoctors();
    boolean addDoctor(Doctor doctor);
    // ... other methods
}
```

Create `DoctorRepository.java` implementing the interface.

#### Step 3: Create ViewModel
Create `DoctorViewModel.java` in `com.hospital.viewmodel`:
```java
public class DoctorViewModel extends BaseViewModel {
    private IDoctorRepository doctorRepository;
    
    public void loadAllDoctors() {
        // Implementation
    }
    // ... other methods
}
```

#### Step 4: Create View
Create `DoctorView.java` in `com.hospital.view`:
```java
public class DoctorView extends BaseView implements PropertyChangeListener {
    private DoctorViewModel viewModel;
    
    @Override
    protected void initializeUI() {
        // Create UI components
    }
    // ... implementation
}
```

#### Step 5: Integrate into MainMenu
Update `MainMenu.java` to include DoctorViewModel and DoctorView in the tabbed pane.

## Directory Structure
```
src/main/java/com/hospital/
├── App.java                          # Entry point
├── MainMenu.java                     # Main window
├── model/
│   ├── Patient.java
│   ├── Appointment.java
│   └── Doctor.java (future)
├── viewmodel/
│   ├── BaseViewModel.java
│   ├── PatientViewModel.java
│   ├── AppointmentViewModel.java
│   └── DoctorViewModel.java (future)
├── view/
│   ├── BaseView.java
│   ├── PatientView.java
│   ├── AppointmentView.java
│   └── DoctorView.java (future)
├── repository/
│   ├── IPatientRepository.java
│   ├── PatientRepository.java
│   ├── IAppointmentRepository.java
│   ├── AppointmentRepository.java
│   ├── IDoctorRepository.java (future)
│   └── DoctorRepository.java (future)
└── database/
    └── DatabaseConnection.java
```

## Best Practices

### 1. Always use Repository Interface
```java
// Good
private IPatientRepository repository;

// Avoid
private PatientRepository repository;
```

### 2. Fire Property Changes for Observable Properties
```java
// In ViewModel
private List<Patient> patients;

public void setPatients(List<Patient> patients) {
    List<Patient> oldValue = this.patients;
    this.patients = patients;
    firePropertyChange("patients", oldValue, patients);  // Important!
}
```

### 3. Implement PropertyChangeListener in Views
```java
// In View
@Override
public void propertyChange(PropertyChangeEvent evt) {
    if ("patients".equals(evt.getPropertyName())) {
        updateTable();
    }
}
```

### 4. Use Loading State for Async Operations
```java
// In ViewModel
public void loadAllPatients() {
    setIsLoading(true);
    try {
        List<Patient> loaded = repository.getAllPatients();
        setPatients(loaded);
    } finally {
        setIsLoading(false);  // Always reset loading state
    }
}
```

### 5. Provide Clear Status Messages
```java
// In ViewModel
setStatusMessage("Patient added successfully");
setStatusMessage("Error adding patient: " + e.getMessage());
```

## Testing

### Unit Test Example: PatientViewModel
```java
@Test
public void testLoadAllPatients() {
    // Arrange
    List<Patient> mockPatients = new ArrayList<>();
    mockPatients.add(new Patient(...));
    
    IPatientRepository mockRepo = mock(IPatientRepository.class);
    when(mockRepo.getAllPatients()).thenReturn(mockPatients);
    
    PatientViewModel viewModel = new PatientViewModel(mockRepo);
    
    // Act
    viewModel.loadAllPatients();
    
    // Assert
    assertEquals(1, viewModel.getPatients().size());
}
```

## Next Steps
1. Implement Add/Edit Patient dialogs in PatientView
2. Implement Add/Edit Appointment dialogs in AppointmentView
3. Add Doctor management (Model, Repository, ViewModel, View)
4. Add data validation in ViewModels
5. Implement unit tests for all ViewModels
6. Add error recovery and retry logic
