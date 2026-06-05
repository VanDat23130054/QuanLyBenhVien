package dao;

import database.DatabaseConnection;
import model.LichTruc;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LichTrucDAO implements IBaseDAO<LichTruc> {

    @Override
    public LichTruc getById(int id) {
        String sql = "SELECT * FROM LichTruc WHERE MaLichTruc = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToLichTruc(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting LichTruc by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<LichTruc> getAll() {
        List<LichTruc> list = new ArrayList<>();
        String sql = "SELECT * FROM LichTruc";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToLichTruc(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all LichTruc: " + e.getMessage());
        }
        return list;
    }

    public List<LichTruc> getByNhanVien(int maNhanVien) {
        List<LichTruc> list = new ArrayList<>();
        String sql = "SELECT * FROM LichTruc WHERE MaNhanVien = ? ORDER BY NgayTruc DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maNhanVien);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToLichTruc(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting LichTruc by employee: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean insert(LichTruc obj) {
        String sql = "INSERT INTO LichTruc (MaNhanVien, NgayTruc, CaTruc, NguoiTao) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaNhanVien());
            pstmt.setDate(2, java.sql.Date.valueOf(obj.getNgayTruc()));
            pstmt.setString(3, obj.getCaTruc());
            pstmt.setInt(4, obj.getNguoiTao());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting LichTruc: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(LichTruc obj) {
        String sql = "UPDATE LichTruc SET MaNhanVien=?, NgayTruc=?, CaTruc=?, NguoiTao=? WHERE MaLichTruc=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaNhanVien());
            pstmt.setDate(2, java.sql.Date.valueOf(obj.getNgayTruc()));
            pstmt.setString(3, obj.getCaTruc());
            pstmt.setInt(4, obj.getNguoiTao());
            pstmt.setInt(5, obj.getMaLichTruc());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating LichTruc: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM LichTruc WHERE MaLichTruc = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting LichTruc: " + e.getMessage());
            return false;
        }
    }

    private LichTruc mapRowToLichTruc(ResultSet rs) throws SQLException {
        LichTruc lichTruc = new LichTruc();
        lichTruc.setMaLichTruc(rs.getInt("MaLichTruc"));
        lichTruc.setMaNhanVien(rs.getInt("MaNhanVien"));
        java.sql.Date sqlDate = rs.getDate("NgayTruc");
        if (sqlDate != null) {
            lichTruc.setNgayTruc(sqlDate.toLocalDate());
        }
        lichTruc.setCaTruc(rs.getString("CaTruc"));
        int nguoiTao = rs.getInt("NguoiTao");
        lichTruc.setNguoiTao(rs.wasNull() ? 0 : nguoiTao);
        return lichTruc;
    }
}
