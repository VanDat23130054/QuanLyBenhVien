package com.hospital.viewmodel;

import com.hospital.model.Patient;
import com.hospital.service.PatientService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for Patient operations
 * Handles business logic and state management for patient-related views
 * Uses Service layer for business logic
 */
public class PatientViewModel extends BaseViewModel {
    private PatientService patientService;
    
    private List<Patient> patients;
    private Patient selectedPatient;
    private String statusMessage;
    private boolean isLoading;

    public PatientViewModel() {
        this.patientService = new PatientService();
        this.patients = new ArrayList<>();
        this.isLoading = false;
    }

    public PatientViewModel(PatientService patientService) {
        this.patientService = patientService;
        this.patients = new ArrayList<>();
        this.isLoading = false;
    }

    // Business Logic Methods

    public void loadAllPatients() {
        setIsLoading(true);
        try {
            List<Patient> loadedPatients = patientService.getAllPatients();
            setPatients(loadedPatients);
            setStatusMessage("Patients loaded successfully");
        } catch (Exception e) {
            setStatusMessage("Error loading patients: " + e.getMessage());
        } finally {
            setIsLoading(false);
        }
    }

    public void searchPatients(String searchTerm) {
        setIsLoading(true);
        try {
            List<Patient> searchResults = patientService.searchPatients(searchTerm);
            setPatients(searchResults);
            setStatusMessage("Search completed: " + searchResults.size() + " patient(s) found");
        } catch (Exception e) {
            setStatusMessage("Error searching patients: " + e.getMessage());
        } finally {
            setIsLoading(false);
        }
    }

    public void addPatient(Patient patient) {
        try {
            if (patientService.createPatient(patient)) {
                setStatusMessage("Patient added successfully");
                loadAllPatients();
            } else {
                setStatusMessage("Failed to add patient");
            }
        } catch (Exception e) {
            setStatusMessage("Error adding patient: " + e.getMessage());
        }
    }

    public void updatePatient(Patient patient) {
        try {
            if (patientService.updatePatient(patient)) {
                setStatusMessage("Patient updated successfully");
                loadAllPatients();
            } else {
                setStatusMessage("Failed to update patient");
            }
        } catch (Exception e) {
            setStatusMessage("Error updating patient: " + e.getMessage());
        }
    }

    public void deletePatient(int patientId) {
        try {
            if (patientService.deletePatient(patientId)) {
                setStatusMessage("Patient deleted successfully");
                loadAllPatients();
            } else {
                setStatusMessage("Failed to delete patient");
            }
        } catch (Exception e) {
            setStatusMessage("Error deleting patient: " + e.getMessage());
        }
    }

    public Patient getPatientById(int patientId) {
        try {
        	return patientService.getPatientById(patientId);
        } catch (Exception e) {
            setStatusMessage("Error retrieving patient: " + e.getMessage());
            return null;
        }
    }

    // Property Getters and Setters with Property Change Support

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        List<Patient> oldValue = this.patients;
        this.patients = patients;
        firePropertyChange("patients", oldValue, patients);
    }

    public Patient getSelectedPatient() {
        return selectedPatient;
    }

    public void setSelectedPatient(Patient selectedPatient) {
        Patient oldValue = this.selectedPatient;
        this.selectedPatient = selectedPatient;
        firePropertyChange("selectedPatient", oldValue, selectedPatient);
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        String oldValue = this.statusMessage;
        this.statusMessage = statusMessage;
        firePropertyChange("statusMessage", oldValue, statusMessage);
    }

    public boolean isIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        boolean oldValue = this.isLoading;
        this.isLoading = isLoading;
        firePropertyChange("isLoading", oldValue, isLoading);
    }
}
