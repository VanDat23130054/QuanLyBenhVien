package com.hospital.viewmodel;

import com.hospital.model.Appointment;
import com.hospital.service.AppointmentService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for Appointment operations
 * Handles business logic and state management for appointment-related views
 * Uses Service layer for business logic
 */
public class AppointmentViewModel extends BaseViewModel {
    private AppointmentService appointmentService;
    
    private List<Appointment> appointments;
    private Appointment selectedAppointment;
    private String statusMessage;
    private boolean isLoading;

    public AppointmentViewModel() {
        this.appointmentService = new AppointmentService();
        this.appointments = new ArrayList<>();
        this.isLoading = false;
    }

    public AppointmentViewModel(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
        this.appointments = new ArrayList<>();
        this.isLoading = false;
    }

    // Business Logic Methods

    public void loadAllAppointments() {
        setIsLoading(true);
        try {
            List<Appointment> loadedAppointments = appointmentService.getAllAppointments();
            setAppointments(loadedAppointments);
            setStatusMessage("Appointments loaded successfully");
        } catch (Exception e) {
            setStatusMessage("Error loading appointments: " + e.getMessage());
        } finally {
            setIsLoading(false);
        }
    }

    public void loadAppointmentsByPatient(int patientId) {
        setIsLoading(true);
        try {
            List<Appointment> patientAppointments = appointmentService.getAppointmentsByPatientId(patientId);
            setAppointments(patientAppointments);
            setStatusMessage("Appointments for patient loaded successfully");
        } catch (Exception e) {
            setStatusMessage("Error loading appointments for patient: " + e.getMessage());
        } finally {
            setIsLoading(false);
        }
    }

    public void loadAppointmentsByDoctor(int doctorId) {
        setIsLoading(true);
        try {
            List<Appointment> doctorAppointments = appointmentService.getAppointmentsByDoctorId(doctorId);
            setAppointments(doctorAppointments);
            setStatusMessage("Appointments for doctor loaded successfully");
        } catch (Exception e) {
            setStatusMessage("Error loading appointments for doctor: " + e.getMessage());
        } finally {
            setIsLoading(false);
        }
    }

    public void loadAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        setIsLoading(true);
        try {
            List<Appointment> rangeAppointments = appointmentService.getAppointmentsByDateRange(startDate, endDate);
            setAppointments(rangeAppointments);
            setStatusMessage("Appointments for date range loaded successfully");
        } catch (Exception e) {
            setStatusMessage("Error loading appointments for date range: " + e.getMessage());
        } finally {
            setIsLoading(false);
        }
    }

    public void addAppointment(Appointment appointment) {
        try {
            if (appointmentService.createAppointment(appointment)) {
                setStatusMessage("Appointment scheduled successfully");
                loadAllAppointments();
            } else {
                setStatusMessage("Failed to schedule appointment");
            }
        } catch (Exception e) {
            setStatusMessage("Error scheduling appointment: " + e.getMessage());
        }
    }

    public void updateAppointment(Appointment appointment) {
        try {
            if (appointmentService.updateAppointment(appointment)) {
                setStatusMessage("Appointment updated successfully");
                loadAllAppointments();
            } else {
                setStatusMessage("Failed to update appointment");
            }
        } catch (Exception e) {
            setStatusMessage("Error updating appointment: " + e.getMessage());
        }
    }

    public void deleteAppointment(int appointmentId) {
        try {
            if (appointmentService.deleteAppointment(appointmentId)) {
                setStatusMessage("Appointment deleted successfully");
                loadAllAppointments();
            } else {
                setStatusMessage("Failed to delete appointment");
            }
        } catch (Exception e) {
            setStatusMessage("Error deleting appointment: " + e.getMessage());
        }
    }

    public Appointment getAppointmentById(int appointmentId) {
        try {
            return appointmentService.getAppointmentById(appointmentId);
        } catch (Exception e) {
            setStatusMessage("Error retrieving appointment: " + e.getMessage());
            return null;
        }
    }

    // Property Getters and Setters with Property Change Support

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        List<Appointment> oldValue = this.appointments;
        this.appointments = appointments;
        firePropertyChange("appointments", oldValue, appointments);
    }

    public Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    public void setSelectedAppointment(Appointment selectedAppointment) {
        Appointment oldValue = this.selectedAppointment;
        this.selectedAppointment = selectedAppointment;
        firePropertyChange("selectedAppointment", oldValue, selectedAppointment);
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
