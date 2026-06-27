package com.httt.quanlybenhvien.repository.impl;

import com.httt.quanlybenhvien.DatabaseConfig;
import com.httt.quanlybenhvien.model.TaiKhoan;
import com.httt.quanlybenhvien.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaiKhoanRepository implements Repository<TaiKhoan, String> {

    @Override
    public TaiKhoan save(TaiKhoan entity) throws SQLException {
        String sql = "INSERT INTO TaiKhoan (tenDangNhap, matKhau, loaiTaiKhoan) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getTenDangNhap());
            ps.setString(2, entity.getMatKhau());
            ps.setString(3, entity.getLoaiTaiKhoan());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public Optional<TaiKhoan> findById(String tenDangNhap) throws SQLException {
        String sql = "SELECT tenDangNhap, matKhau, loaiTaiKhoan FROM TaiKhoan WHERE tenDangNhap = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenDangNhap);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<TaiKhoan> findAll() throws SQLException {
        List<TaiKhoan> result = new ArrayList<>();
        String sql = "SELECT tenDangNhap, matKhau, loaiTaiKhoan FROM TaiKhoan ORDER BY tenDangNhap";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        }
        return result;
    }

    @Override
    public TaiKhoan update(TaiKhoan entity) throws SQLException {
        String sql = "UPDATE TaiKhoan SET matKhau = ?, loaiTaiKhoan = ? WHERE tenDangNhap = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getMatKhau());
            ps.setString(2, entity.getLoaiTaiKhoan());
            ps.setString(3, entity.getTenDangNhap());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public void deleteById(String tenDangNhap) throws SQLException {
        String sql = "DELETE FROM TaiKhoan WHERE tenDangNhap = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenDangNhap);
            ps.executeUpdate();
        }
    }

    public boolean existsById(String tenDangNhap) throws SQLException {
        return findById(tenDangNhap).isPresent();
    }

    private TaiKhoan mapRow(ResultSet rs) throws SQLException {
        return new TaiKhoan(
                rs.getString("tenDangNhap"),
                rs.getString("matKhau"),
                rs.getString("loaiTaiKhoan")
        );
    }
}
