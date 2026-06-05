package dao;

import database.DatabaseConnection;
import model.Thuoc;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThuocDAO implements IBaseDAO<Thuoc> {

    @Override
    public Thuoc getById(int id) {
        String sql = "SELECT * FROM Thuoc WHERE MaThuoc = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToThuoc(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting Thuoc by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Thuoc> getAll() {
        List<Thuoc> list = new ArrayList<>();
        String sql = "SELECT * FROM Thuoc";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToThuoc(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all Thuoc: " + e.getMessage());
        }
        return list;
    }

    public List<Thuoc> searchByName(String tenThuoc) {
        List<Thuoc> list = new ArrayList<>();
        String sql = "SELECT * FROM Thuoc WHERE TenThuoc LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + tenThuoc + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToThuoc(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching Thuoc by name: " + e.getMessage());
        }
        return list;
    }

    public List<Thuoc> getAvailableMedicine() {
        List<Thuoc> list = new ArrayList<>();
        String sql = "SELECT * FROM Thuoc WHERE SoLuongTon > 0";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToThuoc(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting available medicine: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean insert(Thuoc obj) {
        String sql = "INSERT INTO Thuoc (TenThuoc, DonVi, SoLuongTon, DonGia) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, obj.getTenThuoc());
            pstmt.setString(2, obj.getDonVi());
            pstmt.setInt(3, obj.getSoLuongTon());
            pstmt.setBigDecimal(4, obj.getDonGia());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting Thuoc: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Thuoc obj) {
        String sql = "UPDATE Thuoc SET TenThuoc=?, DonVi=?, SoLuongTon=?, DonGia=? WHERE MaThuoc=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, obj.getTenThuoc());
            pstmt.setString(2, obj.getDonVi());
            pstmt.setInt(3, obj.getSoLuongTon());
            pstmt.setBigDecimal(4, obj.getDonGia());
            pstmt.setInt(5, obj.getMaThuoc());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating Thuoc: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Thuoc WHERE MaThuoc = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting Thuoc: " + e.getMessage());
            return false;
        }
    }

    public boolean updateQuantity(int maThuoc, int quantity) {
        String sql = "UPDATE Thuoc SET SoLuongTon=SoLuongTon-? WHERE MaThuoc=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, maThuoc);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating medicine quantity: " + e.getMessage());
            return false;
        }
    }

    private Thuoc mapRowToThuoc(ResultSet rs) throws SQLException {
        Thuoc thuoc = new Thuoc();
        thuoc.setMaThuoc(rs.getInt("MaThuoc"));
        thuoc.setTenThuoc(rs.getString("TenThuoc"));
        thuoc.setDonVi(rs.getString("DonVi"));
        thuoc.setSoLuongTon(rs.getInt("SoLuongTon"));
        thuoc.setDonGia(rs.getBigDecimal("DonGia"));
        return thuoc;
    }
}
