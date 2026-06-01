package com.hospital.repository;

import com.hospital.model.Appointment;
import java.util.List;
import java.time.LocalDateTime;

/**
 * Repository interface for Appointment data access operations
 */
public interface IAppointmentRepository {
    Appointment getAppointmentById(int appointmentId);
    List<Appointment> getAllAppointments();
    List<Appointment> getAppointmentsByPatientId(int patientId);
    List<Appointment> getAppointmentsByDoctorId(int doctorId);
    List<Appointment> getAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    boolean addAppointment(Appointment appointment);
    boolean updateAppointment(Appointment appointment);
    boolean deleteAppointment(int appointmentId);
}
