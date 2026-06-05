package dao;

import database.DatabaseConnection;
import model.ChanDoan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChanDoanDAO implements IBaseDAO<ChanDoan> {

    @Override
    public ChanDoan getById(int id) {
        String sql = "SELECT * FROM ChanDoan WHERE MaChanDoan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToChanDoan(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting ChanDoan by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<ChanDoan> getAll() {
        List<ChanDoan> list = new ArrayList<>();
        String sql = "SELECT * FROM ChanDoan";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToChanDoan(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all ChanDoan: " + e.getMessage());
        }
        return list;
    }

    public List<ChanDoan> getByLichKham(int maLichKham) {
        List<ChanDoan> list = new ArrayList<>();
        String sql = "SELECT * FROM ChanDoan WHERE MaLichKham = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maLichKham);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToChanDoan(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting ChanDoan by appointment: " + e.getMessage());
        }
        return list;
    }

    public List<ChanDoan> getByBacSi(int maBacSi) {
        List<ChanDoan> list = new ArrayList<>();
        String sql = "SELECT * FROM ChanDoan WHERE MaBacSi = ? ORDER BY NgayKham DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maBacSi);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToChanDoan(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting ChanDoan by doctor: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean insert(ChanDoan obj) {
        String sql = "INSERT INTO ChanDoan (MaLichKham, MaHoSo, MaBacSi, KetQua, NgayKham) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaLichKham());
            pstmt.setInt(2, obj.getMaHoSo());
            pstmt.setInt(3, obj.getMaBacSi());
            pstmt.setString(4, obj.getKetQua());
            pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(obj.getNgayKham()));
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting ChanDoan: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(ChanDoan obj) {
        String sql = "UPDATE ChanDoan SET MaLichKham=?, MaHoSo=?, MaBacSi=?, KetQua=?, NgayKham=? WHERE MaChanDoan=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaLichKham());
            pstmt.setInt(2, obj.getMaHoSo());
            pstmt.setInt(3, obj.getMaBacSi());
            pstmt.setString(4, obj.getKetQua());
            pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(obj.getNgayKham()));
            pstmt.setInt(6, obj.getMaChanDoan());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating ChanDoan: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM ChanDoan WHERE MaChanDoan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting ChanDoan: " + e.getMessage());
            return false;
        }
    }

    private ChanDoan mapRowToChanDoan(ResultSet rs) throws SQLException {
        ChanDoan chanDoan = new ChanDoan();
        chanDoan.setMaChanDoan(rs.getInt("MaChanDoan"));
        chanDoan.setMaLichKham(rs.getInt("MaLichKham"));
        chanDoan.setMaHoSo(rs.getInt("MaHoSo"));
        chanDoan.setMaBacSi(rs.getInt("MaBacSi"));
        chanDoan.setKetQua(rs.getString("KetQua"));
        java.sql.Timestamp timestamp = rs.getTimestamp("NgayKham");
        if (timestamp != null) {
            chanDoan.setNgayKham(timestamp.toLocalDateTime());
        }
        return chanDoan;
    }
}
