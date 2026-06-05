package dao;

import database.DatabaseConnection;
import model.DonThuoc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonThuocDAO implements IBaseDAO<DonThuoc> {

    @Override
    public DonThuoc getById(int id) {
        String sql = "SELECT * FROM DonThuoc WHERE MaDonThuoc = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToDonThuoc(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting DonThuoc by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<DonThuoc> getAll() {
        List<DonThuoc> list = new ArrayList<>();
        String sql = "SELECT * FROM DonThuoc";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToDonThuoc(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all DonThuoc: " + e.getMessage());
        }
        return list;
    }

    public List<DonThuoc> getByChanDoan(int maChanDoan) {
        List<DonThuoc> list = new ArrayList<>();
        String sql = "SELECT * FROM DonThuoc WHERE MaChanDoan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maChanDoan);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToDonThuoc(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting DonThuoc by diagnosis: " + e.getMessage());
        }
        return list;
    }

    public List<DonThuoc> getByBacSi(int maBacSi) {
        List<DonThuoc> list = new ArrayList<>();
        String sql = "SELECT * FROM DonThuoc WHERE MaBacSi = ? ORDER BY NgayKe DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maBacSi);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToDonThuoc(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting DonThuoc by doctor: " + e.getMessage());
        }
        return list;
    }

    public List<DonThuoc> getByStatus(String trangThai) {
        List<DonThuoc> list = new ArrayList<>();
        String sql = "SELECT * FROM DonThuoc WHERE TrangThai = ? ORDER BY NgayKe DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trangThai);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToDonThuoc(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting DonThuoc by status: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean insert(DonThuoc obj) {
        String sql = "INSERT INTO DonThuoc (MaChanDoan, MaBacSi, NgayKe, TrangThai) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaChanDoan());
            pstmt.setInt(2, obj.getMaBacSi());
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(obj.getNgayKe()));
            pstmt.setString(4, obj.getTrangThai());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting DonThuoc: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(DonThuoc obj) {
        String sql = "UPDATE DonThuoc SET MaChanDoan=?, MaBacSi=?, NgayKe=?, TrangThai=? WHERE MaDonThuoc=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaChanDoan());
            pstmt.setInt(2, obj.getMaBacSi());
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(obj.getNgayKe()));
            pstmt.setString(4, obj.getTrangThai());
            pstmt.setInt(5, obj.getMaDonThuoc());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating DonThuoc: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM DonThuoc WHERE MaDonThuoc = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting DonThuoc: " + e.getMessage());
            return false;
        }
    }

    private DonThuoc mapRowToDonThuoc(ResultSet rs) throws SQLException {
        DonThuoc donThuoc = new DonThuoc();
        donThuoc.setMaDonThuoc(rs.getInt("MaDonThuoc"));
        donThuoc.setMaChanDoan(rs.getInt("MaChanDoan"));
        donThuoc.setMaBacSi(rs.getInt("MaBacSi"));
        java.sql.Timestamp timestamp = rs.getTimestamp("NgayKe");
        if (timestamp != null) {
            donThuoc.setNgayKe(timestamp.toLocalDateTime());
        }
        donThuoc.setTrangThai(rs.getString("TrangThai"));
        return donThuoc;
    }
}
