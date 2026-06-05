package com.hospital.service;

import com.hospital.model.Appointment;
import com.hospital.dao.IAppointmentDAO;
import com.hospital.dao.DAOFactory;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer for Appointment operations
 * Handles business logic and orchestrates between ViewModels and DAOs
 * Provides a clean API for appointment management operations
 */
public class AppointmentService {
    private IAppointmentDAO appointmentDAO;

    public AppointmentService() {
        this.appointmentDAO = DAOFactory.getAppointmentDAO();
    }

    /**
     * Constructor for dependency injection (useful for testing)
     */
    public AppointmentService(IAppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    /**
     * Get appointment by ID
     */
    public Appointment getAppointmentById(int appointmentId) {
        if (appointmentId <= 0) {
            throw new IllegalArgumentException("Invalid appointment ID");
        }
        return appointmentDAO.getById(appointmentId);
    }

    /**
     * Get all appointments
     */
    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAll();
    }

    /**
     * Create a new appointment
     */
    public boolean createAppointment(Appointment appointment) {
        validateAppointment(appointment);
        return appointmentDAO.add(appointment);
    }

    /**
     * Update existing appointment
     */
    public boolean updateAppointment(Appointment appointment) {
        validateAppointment(appointment);
        if (appointment.getAppointmentId() <= 0) {
            throw new IllegalArgumentException("Invalid appointment ID for update");
        }
        return appointmentDAO.update(appointment);
    }

    /**
     * Delete appointment
     */
    public boolean deleteAppointment(int appointmentId) {
        if (appointmentId <= 0) {
            throw new IllegalArgumentException("Invalid appointment ID");
        }
        return appointmentDAO.delete(appointmentId);
    }

    /**
     * Get appointments by patient ID
     */
    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        if (patientId <= 0) {
            throw new IllegalArgumentException("Invalid patient ID");
        }
        return appointmentDAO.getByPatientId(patientId);
    }

    /**
     * Get appointments by doctor ID
     */
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {
        if (doctorId <= 0) {
            throw new IllegalArgumentException("Invalid doctor ID");
        }
        return appointmentDAO.getByDoctorId(doctorId);
    }

    /**
     * Get appointments by date range
     */
    public List<Appointment> getAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        return appointmentDAO.getByDateRange(startDate, endDate);
    }

    /**
     * Get appointments by status
     */
    public List<Appointment> getAppointmentsByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }
        return appointmentDAO.getByStatus(status.trim());
    }

    /**
     * Check if appointment slot is available
     */
    public boolean isAppointmentSlotAvailable(int doctorId, LocalDateTime appointmentDateTime) {
        List<Appointment> appointments = appointmentDAO.getByDoctorId(doctorId);
        for (Appointment appt : appointments) {
            // Check if there's already an appointment within 30 minutes
            if (appt.getAppointmentDateTime().isBefore(appointmentDateTime.plusMinutes(30)) &&
                appt.getAppointmentDateTime().isAfter(appointmentDateTime.minusMinutes(30))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validate appointment data
     */
    private void validateAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }
        if (appointment.getPatientId() <= 0) {
            throw new IllegalArgumentException("Invalid patient ID");
        }
        if (appointment.getDoctorId() <= 0) {
            throw new IllegalArgumentException("Invalid doctor ID");
        }
        if (appointment.getAppointmentDateTime() == null) {
            throw new IllegalArgumentException("Appointment date/time cannot be empty");
        }
        if (appointment.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment date/time must be in the future");
        }
        if (appointment.getStatus() == null || appointment.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Appointment status cannot be empty");
        }
    }
}
