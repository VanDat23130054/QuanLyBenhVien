package com.hospital.repository;

import com.hospital.model.Patient;
import java.util.List;

/**
 * Repository interface for Patient data access operations
 */
public interface IPatientRepository {
    Patient getPatientById(int patientId);
    List<Patient> getAllPatients();
    boolean addPatient(Patient patient);
    boolean updatePatient(Patient patient);
    boolean deletePatient(int patientId);
    List<Patient> searchPatients(String searchTerm);
}
