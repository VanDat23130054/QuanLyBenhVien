package com.hospital.service;

import com.hospital.model.Patient;
import com.hospital.dao.IPatientDAO;
import com.hospital.dao.DAOFactory;
import java.time.LocalDate;
import java.util.List;

/**
 * Service layer for Patient operations
 * Handles business logic and orchestrates between ViewModels and DAOs
 * Provides a clean API for patient management operations
 */
public class PatientService {
    private IPatientDAO patientDAO;

    public PatientService() {
        this.patientDAO = DAOFactory.getPatientDAO();
    }

    /**
     * Constructor for dependency injection (useful for testing)
     */
    public PatientService(IPatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    /**
     * Get patient by ID
     */
    public Patient getPatientById(int patientId) {
        if (patientId <= 0) {
            throw new IllegalArgumentException("Invalid patient ID");
        }
        return patientDAO.getById(patientId);
    }

    /**
     * Get all patients
     */
    public List<Patient> getAllPatients() {
        return patientDAO.getAll();
    }

    /**
     * Create a new patient
     */
    public boolean createPatient(Patient patient) {
        validatePatient(patient);
        patient.setRegistrationDate(LocalDate.now());
        return patientDAO.add(patient);
    }

    /**
     * Update existing patient
     */
    public boolean updatePatient(Patient patient) {
        validatePatient(patient);
        if (patient.getPatientId() <= 0) {
            throw new IllegalArgumentException("Invalid patient ID for update");
        }
        return patientDAO.update(patient);
    }

    /**
     * Delete patient
     */
    public boolean deletePatient(int patientId) {
        if (patientId <= 0) {
            throw new IllegalArgumentException("Invalid patient ID");
        }
        return patientDAO.delete(patientId);
    }

    /**
     * Search patients by search term
     */
    public List<Patient> searchPatients(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return patientDAO.getAll();
        }
        return patientDAO.search(searchTerm.trim());
    }

    /**
     * Get patient by phone number
     */
    public Patient getPatientByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        return patientDAO.getByPhoneNumber(phoneNumber.trim());
    }

    /**
     * Get patient by email
     */
    public Patient getPatientByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        return patientDAO.getByEmail(email.trim());
    }

    /**
     * Validate patient data
     */
    private void validatePatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null");
        }
        if (patient.getFullName() == null || patient.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name cannot be empty");
        }
        if (patient.getPhoneNumber() != null && !patient.getPhoneNumber().trim().isEmpty()) {
            // Basic phone number validation
            if (!patient.getPhoneNumber().matches("\\d+")) {
                throw new IllegalArgumentException("Invalid phone number format");
            }
        }
        if (patient.getEmail() != null && !patient.getEmail().trim().isEmpty()) {
            // Basic email validation
            if (!patient.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new IllegalArgumentException("Invalid email format");
            }
        }
    }
}
