package dao;

import database.DatabaseConnection;
import model.ChamCong;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChamCongDAO implements IBaseDAO<ChamCong> {

    @Override
    public ChamCong getById(int id) {
        String sql = "SELECT * FROM ChamCong WHERE MaChamCong = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToChamCong(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting ChamCong by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<ChamCong> getAll() {
        List<ChamCong> list = new ArrayList<>();
        String sql = "SELECT * FROM ChamCong";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToChamCong(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all ChamCong: " + e.getMessage());
        }
        return list;
    }

    public List<ChamCong> getByNhanVien(int maNhanVien) {
        List<ChamCong> list = new ArrayList<>();
        String sql = "SELECT * FROM ChamCong WHERE MaNhanVien = ? ORDER BY NgayLam DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maNhanVien);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToChamCong(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting ChamCong by employee: " + e.getMessage());
        }
        return list;
    }

    public List<ChamCong> getByMonth(int maNhanVien, int thang, int nam) {
        List<ChamCong> list = new ArrayList<>();
        String sql = "SELECT * FROM ChamCong WHERE MaNhanVien = ? AND MONTH(NgayLam) = ? AND YEAR(NgayLam) = ? " +
                "ORDER BY NgayLam DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maNhanVien);
            pstmt.setInt(2, thang);
            pstmt.setInt(3, nam);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToChamCong(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting ChamCong by month: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean insert(ChamCong obj) {
        String sql = "INSERT INTO ChamCong (MaNhanVien, NgayLam, TrangThai) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaNhanVien());
            pstmt.setDate(2, java.sql.Date.valueOf(obj.getNgayLam()));
            pstmt.setString(3, obj.getTrangThai());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting ChamCong: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(ChamCong obj) {
        String sql = "UPDATE ChamCong SET MaNhanVien=?, NgayLam=?, TrangThai=? WHERE MaChamCong=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaNhanVien());
            pstmt.setDate(2, java.sql.Date.valueOf(obj.getNgayLam()));
            pstmt.setString(3, obj.getTrangThai());
            pstmt.setInt(4, obj.getMaChamCong());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating ChamCong: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM ChamCong WHERE MaChamCong = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting ChamCong: " + e.getMessage());
            return false;
        }
    }

    private ChamCong mapRowToChamCong(ResultSet rs) throws SQLException {
        ChamCong chamCong = new ChamCong();
        chamCong.setMaChamCong(rs.getInt("MaChamCong"));
        chamCong.setMaNhanVien(rs.getInt("MaNhanVien"));
        java.sql.Date sqlDate = rs.getDate("NgayLam");
        if (sqlDate != null) {
            chamCong.setNgayLam(sqlDate.toLocalDate());
        }
        chamCong.setTrangThai(rs.getString("TrangThai"));
        return chamCong;
    }
}
