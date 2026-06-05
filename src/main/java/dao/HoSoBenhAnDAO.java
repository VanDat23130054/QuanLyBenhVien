package dao;

import database.DatabaseConnection;
import model.HoSoBenhAn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoSoBenhAnDAO implements IBaseDAO<HoSoBenhAn> {

    @Override
    public HoSoBenhAn getById(int id) {
        String sql = "SELECT * FROM HoSoBenhAn WHERE MaHoSo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToHoSoBenhAn(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting HoSoBenhAn by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<HoSoBenhAn> getAll() {
        List<HoSoBenhAn> list = new ArrayList<>();
        String sql = "SELECT * FROM HoSoBenhAn";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToHoSoBenhAn(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all HoSoBenhAn: " + e.getMessage());
        }
        return list;
    }

    public HoSoBenhAn getByBenhNhan(int maBenhNhan) {
        String sql = "SELECT * FROM HoSoBenhAn WHERE MaBenhNhan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maBenhNhan);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToHoSoBenhAn(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting HoSoBenhAn by patient: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean insert(HoSoBenhAn obj) {
        String sql = "INSERT INTO HoSoBenhAn (MaBenhNhan, NgayTao) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaBenhNhan());
            pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(obj.getNgayTao()));
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting HoSoBenhAn: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(HoSoBenhAn obj) {
        String sql = "UPDATE HoSoBenhAn SET MaBenhNhan=?, NgayTao=? WHERE MaHoSo=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaBenhNhan());
            pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(obj.getNgayTao()));
            pstmt.setInt(3, obj.getMaHoSo());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating HoSoBenhAn: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM HoSoBenhAn WHERE MaHoSo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting HoSoBenhAn: " + e.getMessage());
            return false;
        }
    }

    private HoSoBenhAn mapRowToHoSoBenhAn(ResultSet rs) throws SQLException {
        HoSoBenhAn hoSoBenhAn = new HoSoBenhAn();
        hoSoBenhAn.setMaHoSo(rs.getInt("MaHoSo"));
        hoSoBenhAn.setMaBenhNhan(rs.getInt("MaBenhNhan"));
        java.sql.Timestamp timestamp = rs.getTimestamp("NgayTao");
        if (timestamp != null) {
            hoSoBenhAn.setNgayTao(timestamp.toLocalDateTime());
        }
        return hoSoBenhAn;
    }
}
