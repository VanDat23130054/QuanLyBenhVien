package com.hospital.repository;

import com.hospital.model.Appointment;
import com.hospital.database.DatabaseConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Appointment repository implementation using SQL Server
 */
public class AppointmentRepository implements IAppointmentRepository {

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        String query = "SELECT * FROM Appointments WHERE AppointmentID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToAppointment(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting appointment by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM Appointments";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                appointments.add(mapResultSetToAppointment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all appointments: " + e.getMessage());
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM Appointments WHERE PatientID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                appointments.add(mapResultSetToAppointment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting appointments by patient ID: " + e.getMessage());
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM Appointments WHERE DoctorID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                appointments.add(mapResultSetToAppointment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting appointments by doctor ID: " + e.getMessage());
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM Appointments WHERE AppointmentDateTime BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(startDate));
            stmt.setTimestamp(2, Timestamp.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                appointments.add(mapResultSetToAppointment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting appointments by date range: " + e.getMessage());
        }
        return appointments;
    }

    @Override
    public boolean addAppointment(Appointment appointment) {
        String query = "INSERT INTO Appointments (PatientID, DoctorID, AppointmentDateTime, Reason, Status, Notes) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentDateTime()));
            stmt.setString(4, appointment.getReason());
            stmt.setString(5, appointment.getStatus());
            stmt.setString(6, appointment.getNotes());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding appointment: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
        String query = "UPDATE Appointments SET PatientID = ?, DoctorID = ?, AppointmentDateTime = ?, Reason = ?, Status = ?, Notes = ? " +
                      "WHERE AppointmentID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentDateTime()));
            stmt.setString(4, appointment.getReason());
            stmt.setString(5, appointment.getStatus());
            stmt.setString(6, appointment.getNotes());
            stmt.setInt(7, appointment.getAppointmentId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating appointment: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteAppointment(int appointmentId) {
        String query = "DELETE FROM Appointments WHERE AppointmentID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting appointment: " + e.getMessage());
        }
        return false;
    }

    private Appointment mapResultSetToAppointment(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(rs.getInt("AppointmentID"));
        appointment.setPatientId(rs.getInt("PatientID"));
        appointment.setDoctorId(rs.getInt("DoctorID"));
        Timestamp ts = rs.getTimestamp("AppointmentDateTime");
        if (ts != null) {
            appointment.setAppointmentDateTime(ts.toLocalDateTime());
        }
        appointment.setReason(rs.getString("Reason"));
        appointment.setStatus(rs.getString("Status"));
        appointment.setNotes(rs.getString("Notes"));
        return appointment;
    }
}
