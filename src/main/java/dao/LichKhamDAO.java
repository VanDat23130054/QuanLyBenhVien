package dao;

import database.DatabaseConnection;
import model.LichKham;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LichKhamDAO implements IBaseDAO<LichKham> {

    @Override
    public LichKham getById(int id) {
        String sql = "SELECT * FROM LichKham WHERE MaLichKham = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToLichKham(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting LichKham by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<LichKham> getAll() {
        List<LichKham> list = new ArrayList<>();
        String sql = "SELECT * FROM LichKham";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToLichKham(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all LichKham: " + e.getMessage());
        }
        return list;
    }

    public List<LichKham> getByBenhNhan(int maBenhNhan) {
        List<LichKham> list = new ArrayList<>();
        String sql = "SELECT * FROM LichKham WHERE MaBenhNhan = ? ORDER BY NgayKham DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maBenhNhan);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToLichKham(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting LichKham by patient: " + e.getMessage());
        }
        return list;
    }

    public List<LichKham> getByBacSi(int maBacSi) {
        List<LichKham> list = new ArrayList<>();
        String sql = "SELECT * FROM LichKham WHERE MaBacSi = ? ORDER BY NgayKham DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maBacSi);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToLichKham(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting LichKham by doctor: " + e.getMessage());
        }
        return list;
    }

    public List<LichKham> getByStatus(String trangThai) {
        List<LichKham> list = new ArrayList<>();
        String sql = "SELECT * FROM LichKham WHERE TrangThai = ? ORDER BY NgayKham DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trangThai);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToLichKham(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting LichKham by status: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean insert(LichKham obj) {
        String sql = "INSERT INTO LichKham (MaBenhNhan, MaBacSi, NgayKham, TrangThai) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaBenhNhan());
            pstmt.setInt(2, obj.getMaBacSi() > 0 ? obj.getMaBacSi() : null);
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(obj.getNgayKham()));
            pstmt.setString(4, obj.getTrangThai());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting LichKham: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(LichKham obj) {
        String sql = "UPDATE LichKham SET MaBenhNhan=?, MaBacSi=?, NgayKham=?, TrangThai=? WHERE MaLichKham=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaBenhNhan());
            pstmt.setInt(2, obj.getMaBacSi());
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(obj.getNgayKham()));
            pstmt.setString(4, obj.getTrangThai());
            pstmt.setInt(5, obj.getMaLichKham());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating LichKham: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM LichKham WHERE MaLichKham = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting LichKham: " + e.getMessage());
            return false;
        }
    }

    private LichKham mapRowToLichKham(ResultSet rs) throws SQLException {
        LichKham lichKham = new LichKham();
        lichKham.setMaLichKham(rs.getInt("MaLichKham"));
        lichKham.setMaBenhNhan(rs.getInt("MaBenhNhan"));
        int maBacSi = rs.getInt("MaBacSi");
        lichKham.setMaBacSi(rs.wasNull() ? 0 : maBacSi);
        java.sql.Timestamp timestamp = rs.getTimestamp("NgayKham");
        if (timestamp != null) {
            lichKham.setNgayKham(timestamp.toLocalDateTime());
        }
        lichKham.setTrangThai(rs.getString("TrangThai"));
        return lichKham;
    }
}
