package dao;

import database.DatabaseConnection;
import model.NoiTru;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoiTruDAO implements IBaseDAO<NoiTru> {

    @Override
    public NoiTru getById(int id) {
        String sql = "SELECT * FROM NoiTru WHERE MaNoiTru = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToNoiTru(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting NoiTru by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<NoiTru> getAll() {
        List<NoiTru> list = new ArrayList<>();
        String sql = "SELECT * FROM NoiTru";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToNoiTru(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all NoiTru: " + e.getMessage());
        }
        return list;
    }

    public List<NoiTru> getByBenhNhan(int maBenhNhan) {
        List<NoiTru> list = new ArrayList<>();
        String sql = "SELECT * FROM NoiTru WHERE MaBenhNhan = ? ORDER BY NgayNhapVien DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maBenhNhan);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToNoiTru(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting NoiTru by patient: " + e.getMessage());
        }
        return list;
    }

    public List<NoiTru> getByStatus(String trangThai) {
        List<NoiTru> list = new ArrayList<>();
        String sql = "SELECT * FROM NoiTru WHERE TrangThai = ? ORDER BY NgayNhapVien DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trangThai);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToNoiTru(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting NoiTru by status: " + e.getMessage());
        }
        return list;
    }

    public List<NoiTru> getAdmittedPatients() {
        return getByStatus("Dang nam vien");
    }

    @Override
    public boolean insert(NoiTru obj) {
        String sql = "INSERT INTO NoiTru (MaBenhNhan, PhongGiuong, NgayNhapVien, TrangThai) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaBenhNhan());
            pstmt.setString(2, obj.getPhongGiuong());
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(obj.getNgayNhapVien()));
            pstmt.setString(4, obj.getTrangThai());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting NoiTru: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(NoiTru obj) {
        String sql = "UPDATE NoiTru SET MaBenhNhan=?, PhongGiuong=?, NgayNhapVien=?, NgayXuatVien=?, TrangThai=? " +
                "WHERE MaNoiTru=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaBenhNhan());
            pstmt.setString(2, obj.getPhongGiuong());
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(obj.getNgayNhapVien()));
            if (obj.getNgayXuatVien() != null) {
                pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(obj.getNgayXuatVien()));
            } else {
                pstmt.setNull(4, Types.TIMESTAMP);
            }
            pstmt.setString(5, obj.getTrangThai());
            pstmt.setInt(6, obj.getMaNoiTru());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating NoiTru: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM NoiTru WHERE MaNoiTru = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting NoiTru: " + e.getMessage());
            return false;
        }
    }

    private NoiTru mapRowToNoiTru(ResultSet rs) throws SQLException {
        NoiTru noiTru = new NoiTru();
        noiTru.setMaNoiTru(rs.getInt("MaNoiTru"));
        noiTru.setMaBenhNhan(rs.getInt("MaBenhNhan"));
        noiTru.setPhongGiuong(rs.getString("PhongGiuong"));
        java.sql.Timestamp timestampNhap = rs.getTimestamp("NgayNhapVien");
        if (timestampNhap != null) {
            noiTru.setNgayNhapVien(timestampNhap.toLocalDateTime());
        }
        java.sql.Timestamp timestampXuat = rs.getTimestamp("NgayXuatVien");
        if (timestampXuat != null) {
            noiTru.setNgayXuatVien(timestampXuat.toLocalDateTime());
        }
        noiTru.setTrangThai(rs.getString("TrangThai"));
        return noiTru;
    }
}
