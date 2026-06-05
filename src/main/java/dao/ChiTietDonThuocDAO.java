package dao;

import database.DatabaseConnection;
import model.ChiTietDonThuoc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDonThuocDAO implements IBaseDAO<ChiTietDonThuoc> {

    @Override
    public ChiTietDonThuoc getById(int id) {
        String sql = "SELECT * FROM ChiTietDonThuoc WHERE MaChiTiet = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToChiTietDonThuoc(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting ChiTietDonThuoc by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<ChiTietDonThuoc> getAll() {
        List<ChiTietDonThuoc> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietDonThuoc";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToChiTietDonThuoc(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all ChiTietDonThuoc: " + e.getMessage());
        }
        return list;
    }

    public List<ChiTietDonThuoc> getByDonThuoc(int maDonThuoc) {
        List<ChiTietDonThuoc> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietDonThuoc WHERE MaDonThuoc = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maDonThuoc);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToChiTietDonThuoc(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting ChiTietDonThuoc by prescription: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean insert(ChiTietDonThuoc obj) {
        String sql = "INSERT INTO ChiTietDonThuoc (MaDonThuoc, MaThuoc, SoLuong, HuongDan) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaDonThuoc());
            pstmt.setInt(2, obj.getMaThuoc());
            pstmt.setInt(3, obj.getSoLuong());
            pstmt.setString(4, obj.getHuongDan());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting ChiTietDonThuoc: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(ChiTietDonThuoc obj) {
        String sql = "UPDATE ChiTietDonThuoc SET MaDonThuoc=?, MaThuoc=?, SoLuong=?, HuongDan=? WHERE MaChiTiet=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getMaDonThuoc());
            pstmt.setInt(2, obj.getMaThuoc());
            pstmt.setInt(3, obj.getSoLuong());
            pstmt.setString(4, obj.getHuongDan());
            pstmt.setInt(5, obj.getMaChiTiet());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating ChiTietDonThuoc: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM ChiTietDonThuoc WHERE MaChiTiet = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting ChiTietDonThuoc: " + e.getMessage());
            return false;
        }
    }

    private ChiTietDonThuoc mapRowToChiTietDonThuoc(ResultSet rs) throws SQLException {
        ChiTietDonThuoc chiTiet = new ChiTietDonThuoc();
        chiTiet.setMaChiTiet(rs.getInt("MaChiTiet"));
        chiTiet.setMaDonThuoc(rs.getInt("MaDonThuoc"));
        chiTiet.setMaThuoc(rs.getInt("MaThuoc"));
        chiTiet.setSoLuong(rs.getInt("SoLuong"));
        chiTiet.setHuongDan(rs.getString("HuongDan"));
        return chiTiet;
    }
}
