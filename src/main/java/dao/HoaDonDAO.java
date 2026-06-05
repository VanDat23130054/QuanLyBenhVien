package dao;

import database.DatabaseConnection;
import model.HoaDon;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO implements IBaseDAO<HoaDon> {

    @Override
    public HoaDon getById(int id) {
        String sql = "SELECT * FROM HoaDon WHERE MaHoaDon = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToHoaDon(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting HoaDon by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<HoaDon> getAll() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToHoaDon(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all HoaDon: " + e.getMessage());
        }
        return list;
    }

    public List<HoaDon> getByBenhNhan(int maBenhNhan) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE MaBenhNhan = ? ORDER BY NgayLap DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maBenhNhan);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToHoaDon(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting HoaDon by patient: " + e.getMessage());
        }
        return list;
    }

    public List<HoaDon> getByStatus(String trangThai) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE TrangThai = ? ORDER BY NgayLap DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trangThai);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToHoaDon(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting HoaDon by status: " + e.getMessage());
        }
        return list;
    }

    public List<HoaDon> getByInvoiceType(String loaiHoaDon) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE LoaiHoaDon = ? ORDER BY NgayLap DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loaiHoaDon);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToHoaDon(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting HoaDon by invoice type: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean insert(HoaDon obj) {
        String sql = "INSERT INTO HoaDon (MaBenhNhan, NguoiTao, TongTien, NgayLap, TrangThai, LoaiHoaDon) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaBenhNhan());
            pstmt.setInt(2, obj.getNguoiTao());
            pstmt.setBigDecimal(3, obj.getTongTien());
            pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(obj.getNgayLap()));
            pstmt.setString(5, obj.getTrangThai());
            pstmt.setString(6, obj.getLoaiHoaDon());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting HoaDon: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(HoaDon obj) {
        String sql = "UPDATE HoaDon SET MaBenhNhan=?, NguoiTao=?, TongTien=?, NgayLap=?, TrangThai=?, LoaiHoaDon=? " +
                "WHERE MaHoaDon=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaBenhNhan());
            pstmt.setInt(2, obj.getNguoiTao());
            pstmt.setBigDecimal(3, obj.getTongTien());
            pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(obj.getNgayLap()));
            pstmt.setString(5, obj.getTrangThai());
            pstmt.setString(6, obj.getLoaiHoaDon());
            pstmt.setInt(7, obj.getMaHoaDon());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating HoaDon: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM HoaDon WHERE MaHoaDon = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting HoaDon: " + e.getMessage());
            return false;
        }
    }

    private HoaDon mapRowToHoaDon(ResultSet rs) throws SQLException {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHoaDon(rs.getInt("MaHoaDon"));
        hoaDon.setMaBenhNhan(rs.getInt("MaBenhNhan"));
        hoaDon.setNguoiTao(rs.getInt("NguoiTao"));
        hoaDon.setTongTien(rs.getBigDecimal("TongTien"));
        java.sql.Timestamp timestamp = rs.getTimestamp("NgayLap");
        if (timestamp != null) {
            hoaDon.setNgayLap(timestamp.toLocalDateTime());
        }
        hoaDon.setTrangThai(rs.getString("TrangThai"));
        hoaDon.setLoaiHoaDon(rs.getString("LoaiHoaDon"));
        return hoaDon;
    }
}
