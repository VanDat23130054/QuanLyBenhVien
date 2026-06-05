package dao;

import database.DatabaseConnection;
import model.TinhLuong;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TinhLuongDAO implements IBaseDAO<TinhLuong> {

    @Override
    public TinhLuong getById(int id) {
        String sql = "SELECT * FROM TinhLuong WHERE MaBangLuong = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToTinhLuong(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting TinhLuong by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<TinhLuong> getAll() {
        List<TinhLuong> list = new ArrayList<>();
        String sql = "SELECT * FROM TinhLuong";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToTinhLuong(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all TinhLuong: " + e.getMessage());
        }
        return list;
    }

    public List<TinhLuong> getByNhanVien(int maNhanVien) {
        List<TinhLuong> list = new ArrayList<>();
        String sql = "SELECT * FROM TinhLuong WHERE MaNhanVien = ? ORDER BY Nam DESC, Thang DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maNhanVien);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToTinhLuong(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting TinhLuong by employee: " + e.getMessage());
        }
        return list;
    }

    public List<TinhLuong> getByMonth(int thang, int nam) {
        List<TinhLuong> list = new ArrayList<>();
        String sql = "SELECT * FROM TinhLuong WHERE Thang = ? AND Nam = ? ORDER BY MaNhanVien";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, thang);
            pstmt.setInt(2, nam);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToTinhLuong(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting TinhLuong by month: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean insert(TinhLuong obj) {
        String sql = "INSERT INTO TinhLuong (MaNhanVien, Thang, Nam, TongLuong, NguoiTinh) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaNhanVien());
            pstmt.setInt(2, obj.getThang());
            pstmt.setInt(3, obj.getNam());
            pstmt.setBigDecimal(4, obj.getTongLuong());
            pstmt.setInt(5, obj.getNguoiTinh());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting TinhLuong: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(TinhLuong obj) {
        String sql = "UPDATE TinhLuong SET MaNhanVien=?, Thang=?, Nam=?, TongLuong=?, NguoiTinh=? WHERE MaBangLuong=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaNhanVien());
            pstmt.setInt(2, obj.getThang());
            pstmt.setInt(3, obj.getNam());
            pstmt.setBigDecimal(4, obj.getTongLuong());
            pstmt.setInt(5, obj.getNguoiTinh());
            pstmt.setInt(6, obj.getMaBangLuong());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating TinhLuong: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM TinhLuong WHERE MaBangLuong = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting TinhLuong: " + e.getMessage());
            return false;
        }
    }

    private TinhLuong mapRowToTinhLuong(ResultSet rs) throws SQLException {
        TinhLuong tinhLuong = new TinhLuong();
        tinhLuong.setMaBangLuong(rs.getInt("MaBangLuong"));
        tinhLuong.setMaNhanVien(rs.getInt("MaNhanVien"));
        tinhLuong.setThang(rs.getInt("Thang"));
        tinhLuong.setNam(rs.getInt("Nam"));
        tinhLuong.setTongLuong(rs.getBigDecimal("TongLuong"));
        int nguoiTinh = rs.getInt("NguoiTinh");
        tinhLuong.setNguoiTinh(rs.wasNull() ? 0 : nguoiTinh);
        return tinhLuong;
    }
}
