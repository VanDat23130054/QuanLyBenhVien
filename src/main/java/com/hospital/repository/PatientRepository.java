package com.hospital.repository;

import com.hospital.model.Patient;
import com.hospital.database.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Patient repository implementation using SQL Server
 */
public class PatientRepository implements IPatientRepository {

    @Override
    public Patient getPatientById(int patientId) {
        String query = "SELECT * FROM Patients WHERE PatientID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToPatient(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting patient by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM Patients";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                patients.add(mapResultSetToPatient(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all patients: " + e.getMessage());
        }
        return patients;
    }

    @Override
    public boolean addPatient(Patient patient) {
        String query = "INSERT INTO Patients (FullName, DateOfBirth, Gender, PhoneNumber, Email, Address, MedicalHistory, RegistrationDate) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, patient.getFullName());
            stmt.setDate(2, patient.getDateOfBirth() != null ? Date.valueOf(patient.getDateOfBirth()) : null);
            stmt.setString(3, patient.getGender());
            stmt.setString(4, patient.getPhoneNumber());
            stmt.setString(5, patient.getEmail());
            stmt.setString(6, patient.getAddress());
            stmt.setString(7, patient.getMedicalHistory());
            stmt.setDate(8, patient.getRegistrationDate() != null ? Date.valueOf(patient.getRegistrationDate()) : Date.valueOf(LocalDate.now()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding patient: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updatePatient(Patient patient) {
        String query = "UPDATE Patients SET FullName = ?, DateOfBirth = ?, Gender = ?, PhoneNumber = ?, Email = ?, Address = ?, MedicalHistory = ? " +
                      "WHERE PatientID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, patient.getFullName());
            stmt.setDate(2, patient.getDateOfBirth() != null ? Date.valueOf(patient.getDateOfBirth()) : null);
            stmt.setString(3, patient.getGender());
            stmt.setString(4, patient.getPhoneNumber());
            stmt.setString(5, patient.getEmail());
            stmt.setString(6, patient.getAddress());
            stmt.setString(7, patient.getMedicalHistory());
            stmt.setInt(8, patient.getPatientId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating patient: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deletePatient(int patientId) {
        String query = "DELETE FROM Patients WHERE PatientID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting patient: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Patient> searchPatients(String searchTerm) {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM Patients WHERE FullName LIKE ? OR PhoneNumber LIKE ? OR Email LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            String search = "%" + searchTerm + "%";
            stmt.setString(1, search);
            stmt.setString(2, search);
            stmt.setString(3, search);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                patients.add(mapResultSetToPatient(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching patients: " + e.getMessage());
        }
        return patients;
    }

    private Patient mapResultSetToPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setPatientId(rs.getInt("PatientID"));
        patient.setFullName(rs.getString("FullName"));
        Date dob = rs.getDate("DateOfBirth");
        if (dob != null) {
            patient.setDateOfBirth(dob.toLocalDate());
        }
        patient.setGender(rs.getString("Gender"));
        patient.setPhoneNumber(rs.getString("PhoneNumber"));
        patient.setEmail(rs.getString("Email"));
        patient.setAddress(rs.getString("Address"));
        patient.setMedicalHistory(rs.getString("MedicalHistory"));
        Date regDate = rs.getDate("RegistrationDate");
        if (regDate != null) {
            patient.setRegistrationDate(regDate.toLocalDate());
        }
        return patient;
    }
}
