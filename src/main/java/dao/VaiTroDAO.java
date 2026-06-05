package dao;

import database.DatabaseConnection;
import model.VaiTro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VaiTroDAO implements IBaseDAO<VaiTro> {

    @Override
    public VaiTro getById(int id) {
        String sql = "SELECT * FROM VaiTro WHERE MaVaiTro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToVaiTro(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting VaiTro by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<VaiTro> getAll() {
        List<VaiTro> list = new ArrayList<>();
        String sql = "SELECT * FROM VaiTro";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToVaiTro(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all VaiTro: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean insert(VaiTro obj) {
        String sql = "INSERT INTO VaiTro (TenVaiTro) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, obj.getTenVaiTro());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting VaiTro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(VaiTro obj) {
        String sql = "UPDATE VaiTro SET TenVaiTro = ? WHERE MaVaiTro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, obj.getTenVaiTro());
            pstmt.setInt(2, obj.getMaVaiTro());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating VaiTro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM VaiTro WHERE MaVaiTro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting VaiTro: " + e.getMessage());
            return false;
        }
    }

    private VaiTro mapRowToVaiTro(ResultSet rs) throws SQLException {
        VaiTro vaiTro = new VaiTro();
        vaiTro.setMaVaiTro(rs.getInt("MaVaiTro"));
        vaiTro.setTenVaiTro(rs.getString("TenVaiTro"));
        return vaiTro;
    }
}
