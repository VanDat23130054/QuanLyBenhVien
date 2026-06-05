package dao;

import database.DatabaseConnection;
import model.BenhNhan;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BenhNhanDAO implements IBaseDAO<BenhNhan> {

    @Override
    public BenhNhan getById(int id) {
        String sql = "SELECT * FROM BenhNhan WHERE MaBenhNhan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToBenhNhan(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting BenhNhan by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<BenhNhan> getAll() {
        List<BenhNhan> list = new ArrayList<>();
        String sql = "SELECT bn.*, nd.HoTen, nd.Email, nd.SoDienThoai FROM BenhNhan bn " +
                "JOIN NguoiDung nd ON bn.MaBenhNhan = nd.MaNguoiDung";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToBenhNhan(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all BenhNhan: " + e.getMessage());
        }
        return list;
    }

    public List<BenhNhan> searchByName(String hoTen) {
        List<BenhNhan> list = new ArrayList<>();
        String sql = "SELECT bn.*, nd.HoTen, nd.Email, nd.SoDienThoai FROM BenhNhan bn " +
                "JOIN NguoiDung nd ON bn.MaBenhNhan = nd.MaNguoiDung WHERE nd.HoTen LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + hoTen + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToBenhNhan(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching BenhNhan by name: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean insert(BenhNhan obj) {
        String sql = "INSERT INTO BenhNhan (MaBenhNhan, NgaySinh, GioiTinh, DiaChi, MaBHYT) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaBenhNhan());
            pstmt.setDate(2, java.sql.Date.valueOf(obj.getNgaySinh()));
            pstmt.setString(3, obj.getGioiTinh());
            pstmt.setString(4, obj.getDiaChi());
            pstmt.setString(5, obj.getMaBHYT());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting BenhNhan: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(BenhNhan obj) {
        String sql = "UPDATE BenhNhan SET NgaySinh=?, GioiTinh=?, DiaChi=?, MaBHYT=? WHERE MaBenhNhan=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(obj.getNgaySinh()));
            pstmt.setString(2, obj.getGioiTinh());
            pstmt.setString(3, obj.getDiaChi());
            pstmt.setString(4, obj.getMaBHYT());
            pstmt.setInt(5, obj.getMaBenhNhan());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating BenhNhan: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM BenhNhan WHERE MaBenhNhan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting BenhNhan: " + e.getMessage());
            return false;
        }
    }

    private BenhNhan mapRowToBenhNhan(ResultSet rs) throws SQLException {
        BenhNhan benhNhan = new BenhNhan();
        benhNhan.setMaBenhNhan(rs.getInt("MaBenhNhan"));
        java.sql.Date sqlDate = rs.getDate("NgaySinh");
        if (sqlDate != null) {
            benhNhan.setNgaySinh(sqlDate.toLocalDate());
        }
        benhNhan.setGioiTinh(rs.getString("GioiTinh"));
        benhNhan.setDiaChi(rs.getString("DiaChi"));
        benhNhan.setMaBHYT(rs.getString("MaBHYT"));
        try {
            benhNhan.setHoTen(rs.getString("HoTen"));
            benhNhan.setEmail(rs.getString("Email"));
            benhNhan.setSoDienThoai(rs.getString("SoDienThoai"));
        } catch (SQLException e) {
            // Columns might not exist if query doesn't include them
        }
        return benhNhan;
    }
}
