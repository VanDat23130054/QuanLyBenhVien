package com.hospital.dao;

import com.hospital.model.Patient;
import java.util.List;

/**
 * Data Access Object interface for Patient entity
 * Defines all data access operations specific to Patient
 */
public interface IPatientDAO extends IBaseDAO<Patient> {
    /**
     * Search patients by search term (name, phone, email)
     */
    List<Patient> search(String searchTerm);

    /**
     * Get patient by phone number
     */
    Patient getByPhoneNumber(String phoneNumber);

    /**
     * Get patient by email
     */
    Patient getByEmail(String email);
}
