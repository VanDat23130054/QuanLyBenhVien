package com.hospital.dao;

import com.hospital.model.Appointment;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Access Object interface for Appointment entity
 * Defines all data access operations specific to Appointment
 */
public interface IAppointmentDAO extends IBaseDAO<Appointment> {
    /**
     * Get appointments by patient ID
     */
    List<Appointment> getByPatientId(int patientId);

    /**
     * Get appointments by doctor ID
     */
    List<Appointment> getByDoctorId(int doctorId);

    /**
     * Get appointments by date range
     */
    List<Appointment> getByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Get appointments by status
     */
    List<Appointment> getByStatus(String status);
}
