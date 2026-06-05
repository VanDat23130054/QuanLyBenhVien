package com.hospital.dao;

/**
 * Factory class for creating DAO instances
 * Implements the Factory design pattern for centralized DAO object creation
 */
public class DAOFactory {
    
    /**
     * Private constructor to prevent instantiation
     */
    private DAOFactory() {
    }

    /**
     * Get Patient DAO instance
     */
    public static IPatientDAO getPatientDAO() {
        return new PatientDAO();
    }

    /**
     * Get Appointment DAO instance
     */
    public static IAppointmentDAO getAppointmentDAO() {
        return new AppointmentDAO();
    }
}
