package dao;

import database.DatabaseConnection;
import model.NguoiDung;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO implements IBaseDAO<NguoiDung> {

    @Override
    public NguoiDung getById(int id) {
        String sql = "SELECT * FROM NguoiDung WHERE MaNguoiDung = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToNguoiDung(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting NguoiDung by id: " + e.getMessage());
        }
        return null;
    }

    public NguoiDung getByUsername(String username) {
        String sql = "SELECT * FROM NguoiDung WHERE TenDangNhap = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToNguoiDung(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting NguoiDung by username: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<NguoiDung> getAll() {
        List<NguoiDung> list = new ArrayList<>();
        String sql = "SELECT * FROM NguoiDung";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToNguoiDung(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all NguoiDung: " + e.getMessage());
        }
        return list;
    }

    public List<NguoiDung> getByRole(int maVaiTro) {
        List<NguoiDung> list = new ArrayList<>();
        String sql = "SELECT * FROM NguoiDung WHERE MaVaiTro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maVaiTro);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToNguoiDung(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting NguoiDung by role: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean insert(NguoiDung obj) {
        String sql = "INSERT INTO NguoiDung (TenDangNhap, MatKhau, MaVaiTro, HoTen, SoDienThoai, Email, TrangThai) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, obj.getTenDangNhap());
            pstmt.setString(2, obj.getMatKhau());
            pstmt.setInt(3, obj.getMaVaiTro());
            pstmt.setString(4, obj.getHoTen());
            pstmt.setString(5, obj.getSoDienThoai());
            pstmt.setString(6, obj.getEmail());
            pstmt.setBoolean(7, obj.isTrangThai());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting NguoiDung: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(NguoiDung obj) {
        String sql = "UPDATE NguoiDung SET TenDangNhap=?, MatKhau=?, MaVaiTro=?, HoTen=?, SoDienThoai=?, Email=?, TrangThai=? " +
                "WHERE MaNguoiDung = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, obj.getTenDangNhap());
            pstmt.setString(2, obj.getMatKhau());
            pstmt.setInt(3, obj.getMaVaiTro());
            pstmt.setString(4, obj.getHoTen());
            pstmt.setString(5, obj.getSoDienThoai());
            pstmt.setString(6, obj.getEmail());
            pstmt.setBoolean(7, obj.isTrangThai());
            pstmt.setInt(8, obj.getMaNguoiDung());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating NguoiDung: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM NguoiDung WHERE MaNguoiDung = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting NguoiDung: " + e.getMessage());
            return false;
        }
    }

    private NguoiDung mapRowToNguoiDung(ResultSet rs) throws SQLException {
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setMaNguoiDung(rs.getInt("MaNguoiDung"));
        nguoiDung.setTenDangNhap(rs.getString("TenDangNhap"));
        nguoiDung.setMatKhau(rs.getString("MatKhau"));
        nguoiDung.setMaVaiTro(rs.getInt("MaVaiTro"));
        nguoiDung.setHoTen(rs.getString("HoTen"));
        nguoiDung.setSoDienThoai(rs.getString("SoDienThoai"));
        nguoiDung.setEmail(rs.getString("Email"));
        nguoiDung.setTrangThai(rs.getBoolean("TrangThai"));
        return nguoiDung;
    }
}
