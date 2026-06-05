# DAO Pattern - Quick Visual Reference

## 🎯 When to Use What

| Need | Use | Example |
|------|-----|---------|
| Full CRUD with validation | `Service` | `patientService.createPatient(...)` |
| Direct database access | `DAO` | `DAOFactory.getPatientDAO().add(...)` |
| UI state management | `ViewModel` | `patientViewModel.loadAllPatients()` |
| Display data in UI | `ViewModel` + data binding | `viewModel.getPatients()` |
| Testing data access | Mock `DAO` | `new PatientService(mockDAO)` |
| Testing business logic | Mock `DAO` | `new PatientService(mockDAO)` |

---

## 📊 Method Mapping

### Creating a Patient

```
User Interface (View)
        │
        ↓
PatientViewModel.addPatient(patient)
        │
        ↓
PatientService.createPatient(patient)
    ├── Validate input
    ├── Set registration date
    └── Call DAO
        │
        ↓
IPatientDAO.add(patient)
    ├── Build SQL INSERT
    ├── Execute PreparedStatement
    └── Return boolean
        │
        ↓
Database (SQL Server)
```

### Reading All Patients

```
User Interface (View)
        │
        ↓
PatientViewModel.loadAllPatients()
        │
        ↓
PatientService.getAllPatients()
        │
        ↓
IPatientDAO.getAll()
    ├── Build SQL SELECT
    ├── Execute PreparedStatement
    ├── Map ResultSet to Objects
    └── Return List<Patient>
        │
        ↓
Database (SQL Server)
```

---

## 🔗 Component Relationships

```
Service Layer Usage:
PatientService
    │
    └─→ IPatientDAO (obtained from DAOFactory)
        └─→ SQL Server Database

DAO Layer Usage:
IPatientDAO (obtained from DAOFactory)
    │
    └─→ SQL Server Database

ViewModel Usage:
PatientViewModel
    │
    └─→ PatientService
        └─→ IPatientDAO
            └─→ SQL Server Database
```

---

## 📝 Code Templates

### Template 1: Service Usage in ViewModel
```java
public class MyViewModel extends BaseViewModel {
    private PatientService patientService;
    
    public MyViewModel() {
        this.patientService = new PatientService();
    }
    
    public void myMethod() {
        try {
            List<Patient> patients = patientService.getAllPatients();
            setPatients(patients);
        } catch (IllegalArgumentException e) {
            setStatusMessage(e.getMessage());
        }
    }
}
```

### Template 2: Service Testing
```java
@Test
public void testPatientService() {
    // Mock DAO
    IPatientDAO mockDAO = mock(IPatientDAO.class);
    when(mockDAO.getAll()).thenReturn(Arrays.asList(...));
    
    // Test service
    PatientService service = new PatientService(mockDAO);
    List<Patient> result = service.getAllPatients();
    
    // Verify
    assertEquals(expected, result);
    verify(mockDAO).getAll();
}
```

### Template 3: DAO Usage
```java
public class MyClass {
    public void doSomething() {
        IPatientDAO dao = DAOFactory.getPatientDAO();
        Patient patient = dao.getById(1);
        // Use patient...
    }
}
```

### Template 4: Service Validation Handling
```java
public void handlePatientCreation(Patient patient) {
    try {
        patientService.createPatient(patient);
        showSuccess("Patient created successfully");
    } catch (IllegalArgumentException e) {
        showError("Validation error: " + e.getMessage());
    }
}
```

---

## 🔍 Debugging Checklist

| Problem | Check | Solution |
|---------|-------|----------|
| Import error for Service | Package path | Ensure `com.hospital.service.PatientService` |
| DAOFactory not found | Package path | Ensure `com.hospital.dao.DAOFactory` |
| Validation exception | Input data | Check error message, fix data |
| No results returned | Query logic | Check database, verify data exists |
| Database connection error | Database credentials | Check DatabaseConnection.java settings |
| NullPointerException | Initialization | Ensure service/DAO created before use |

---

## 🎓 Common Patterns

### Pattern 1: Load and Display Data
```java
public void loadData() {
    // In ViewModel
    List<Patient> patients = patientService.getAllPatients();
    setPatients(patients);
    setStatusMessage("Data loaded: " + patients.size());
}
```

### Pattern 2: CRUD Operation with Error Handling
```java
public void createNewPatient(Patient patient) {
    try {
        patientService.createPatient(patient);
        loadData(); // Refresh
        setStatusMessage("Success!");
    } catch (IllegalArgumentException e) {
        setStatusMessage("Error: " + e.getMessage());
    }
}
```

### Pattern 3: Search/Filter
```java
public void searchPatients(String term) {
    List<Patient> results = patientService.searchPatients(term);
    setPatients(results);
    setStatusMessage("Found: " + results.size());
}
```

### Pattern 4: Conditional Operation
```java
public void scheduleAppointment(Appointment appt) {
    if (appointmentService.isAppointmentSlotAvailable(
            appt.getDoctorId(), appt.getAppointmentDateTime())) {
        appointmentService.createAppointment(appt);
        setStatusMessage("Appointment scheduled!");
    } else {
        setStatusMessage("Time slot not available");
    }
}
```

---

## 📚 File Quick Reference

| File | Purpose | Usage |
|------|---------|-------|
| `IBaseDAO.java` | Generic DAO interface | Extend for specific DAOs |
| `IPatientDAO.java` | Patient DAO interface | Implement for Patient operations |
| `PatientDAO.java` | Patient DAO implementation | Used by PatientService |
| `DAOFactory.java` | DAO factory | `DAOFactory.getPatientDAO()` |
| `PatientService.java` | Patient business logic | Use in ViewModels |
| `PatientViewModel.java` | Patient state management | Use in Views |

---

## 🔑 Key Methods by Entity

### Patient Operations
```java
// Service (with validation)
patientService.getAllPatients()
patientService.getPatientById(id)
patientService.createPatient(patient)
patientService.updatePatient(patient)
patientService.deletePatient(id)
patientService.searchPatients(term)
patientService.getPatientByPhoneNumber(phone)
patientService.getPatientByEmail(email)

// DAO (no validation)
patientDAO.getAll()
patientDAO.getById(id)
patientDAO.add(patient)
patientDAO.update(patient)
patientDAO.delete(id)
patientDAO.search(term)
patientDAO.getByPhoneNumber(phone)
patientDAO.getByEmail(email)
```

### Appointment Operations
```java
// Service (with validation)
appointmentService.getAllAppointments()
appointmentService.getAppointmentById(id)
appointmentService.createAppointment(appt)
appointmentService.updateAppointment(appt)
appointmentService.deleteAppointment(id)
appointmentService.getAppointmentsByPatientId(patientId)
appointmentService.getAppointmentsByDoctorId(doctorId)
appointmentService.getAppointmentsByDateRange(start, end)
appointmentService.getAppointmentsByStatus(status)
appointmentService.isAppointmentSlotAvailable(doctorId, dateTime)

// DAO (no validation)
appointmentDAO.getAll()
appointmentDAO.getById(id)
appointmentDAO.add(appt)
appointmentDAO.update(appt)
appointmentDAO.delete(id)
appointmentDAO.getByPatientId(patientId)
appointmentDAO.getByDoctorId(doctorId)
appointmentDAO.getByDateRange(start, end)
appointmentDAO.getByStatus(status)
```

---

## 🚀 Quick Start Code Snippets

### Snippet 1: Load All Patients in ViewModel
```java
public void initializeData() {
    patientService = new PatientService();
    List<Patient> patients = patientService.getAllPatients();
    this.patients = patients;
    firePropertyChange("patients", null, patients);
}
```

### Snippet 2: Add New Patient
```java
Patient newPatient = new Patient();
newPatient.setFullName("John Doe");
newPatient.setPhoneNumber("0123456789");
newPatient.setEmail("john@example.com");

try {
    patientService.createPatient(newPatient);
    showMessage("Patient added successfully!");
} catch (IllegalArgumentException e) {
    showError("Failed to add patient: " + e.getMessage());
}
```

### Snippet 3: Search Patients
```java
String searchTerm = searchField.getText();
List<Patient> results = patientService.searchPatients(searchTerm);
updateTable(results);
```

### Snippet 4: Update Patient
```java
patient.setFullName("New Name");
try {
    patientService.updatePatient(patient);
    refreshData();
} catch (IllegalArgumentException e) {
    showError(e.getMessage());
}
```

### Snippet 5: Mock Service for Testing
```java
@Before
public void setUp() {
    mockDAO = mock(IPatientDAO.class);
    service = new PatientService(mockDAO);
}

@Test
public void testGetAll() {
    when(mockDAO.getAll()).thenReturn(Arrays.asList(
        new Patient(...),
        new Patient(...)
    ));
    
    List<Patient> result = service.getAllPatients();
    assertEquals(2, result.size());
}
```

---

## 📋 Validation Rules

### Patient Validation
```
✓ Name: Required, not empty
✓ Phone: If provided, must be digits only
✓ Email: If provided, must be valid email format
✓ Age: Must be positive (if applicable)
```

### Appointment Validation
```
✓ PatientID: Required, must be > 0
✓ DoctorID: Required, must be > 0
✓ DateTime: Required, must not be null
✓ Future: DateTime must be in future
✓ Status: Required, not empty
```

---

## 🎯 Decision Tree

```
Need to access data?
    │
    ├─ YES, from UI/ViewModel?
    │   └─ Use Service (includes validation)
    │
    ├─ YES, need low-level access?
    │   └─ Use DAO from DAOFactory
    │
    └─ NO
        └─ Keep data in memory/cache
```

---

## 💾 Database Operations Summary

| Operation | Layer | Method | Returns |
|-----------|-------|--------|---------|
| Create | Service | createPatient(p) | boolean |
| Create | DAO | add(p) | boolean |
| Read One | Service | getPatientById(id) | Patient |
| Read One | DAO | getById(id) | Patient |
| Read All | Service | getAllPatients() | List<Patient> |
| Read All | DAO | getAll() | List<Patient> |
| Update | Service | updatePatient(p) | boolean |
| Update | DAO | update(p) | boolean |
| Delete | Service | deletePatient(id) | boolean |
| Delete | DAO | delete(id) | boolean |

---

## 🔗 Quick Links

- **Full Guide:** See `DAO_PATTERN_GUIDE.md`
- **Setup Info:** See `DAO_PATTERN_SETUP_CHECKLIST.md`
- **Code Examples:** See `DAO_QUICK_START.md`
- **Overview:** See `README_DAO_IMPLEMENTATION.md`
- **Source Code:** See `src/main/java/com/hospital/`

---

**Last Updated:** June 5, 2026
**Version:** 1.0
**Status:** Complete ✅
