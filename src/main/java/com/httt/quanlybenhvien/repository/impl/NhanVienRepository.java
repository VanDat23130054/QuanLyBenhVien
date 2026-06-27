package com.httt.quanlybenhvien.repository.impl;

import com.httt.quanlybenhvien.DatabaseConfig;
import com.httt.quanlybenhvien.model.NhanVien;
import com.httt.quanlybenhvien.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NhanVienRepository implements Repository<NhanVien, String> {

    @Override
    public NhanVien save(NhanVien entity) throws SQLException {
        String sql = "INSERT INTO NhanVien (maNhanVien, tenNhanVien, khoa, chucVu, mucLuong, tenDangNhap) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            fillStatement(ps, entity);
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public Optional<NhanVien> findById(String maNhanVien) throws SQLException {
        String sql = "SELECT maNhanVien, tenNhanVien, khoa, chucVu, mucLuong, tenDangNhap FROM NhanVien WHERE maNhanVien = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<NhanVien> findAll() throws SQLException {
        String sql = "SELECT maNhanVien, tenNhanVien, khoa, chucVu, mucLuong, tenDangNhap FROM NhanVien ORDER BY maNhanVien";
        return queryList(sql, null);
    }

    public List<NhanVien> findByChucVu(String chucVuKeyword) throws SQLException {
        String sql = "SELECT maNhanVien, tenNhanVien, khoa, chucVu, mucLuong, tenDangNhap " +
                "FROM NhanVien WHERE LOWER(chucVu) LIKE LOWER(?) ORDER BY maNhanVien";
        return queryList(sql, "%" + chucVuKeyword + "%");
    }

    @Override
    public NhanVien update(NhanVien entity) throws SQLException {
        String sql = "UPDATE NhanVien SET tenNhanVien = ?, khoa = ?, chucVu = ?, mucLuong = ?, tenDangNhap = ? WHERE maNhanVien = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getTenNhanVien());
            ps.setString(2, entity.getKhoa());
            ps.setString(3, entity.getChucVu());
            if (entity.getMucLuong() == null) {
                ps.setNull(4, java.sql.Types.BIGINT);
            } else {
                ps.setLong(4, entity.getMucLuong());
            }
            ps.setString(5, entity.getTenDangNhap());
            ps.setString(6, entity.getMaNhanVien());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public void deleteById(String maNhanVien) throws SQLException {
        String sql = "DELETE FROM NhanVien WHERE maNhanVien = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
            ps.executeUpdate();
        }
    }

    private List<NhanVien> queryList(String sql, String parameter) throws SQLException {
        List<NhanVien> result = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (parameter != null) {
                ps.setString(1, parameter);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }
        }
        return result;
    }

    private void fillStatement(PreparedStatement ps, NhanVien entity) throws SQLException {
        ps.setString(1, entity.getMaNhanVien());
        ps.setString(2, entity.getTenNhanVien());
        ps.setString(3, entity.getKhoa());
        ps.setString(4, entity.getChucVu());
        if (entity.getMucLuong() == null) {
            ps.setNull(5, java.sql.Types.BIGINT);
        } else {
            ps.setLong(5, entity.getMucLuong());
        }
        ps.setString(6, entity.getTenDangNhap());
    }

    private NhanVien mapRow(ResultSet rs) throws SQLException {
        long mucLuong = rs.getLong("mucLuong");
        Long mucLuongValue = rs.wasNull() ? null : mucLuong;
        return new NhanVien(
                rs.getString("maNhanVien"),
                rs.getString("tenNhanVien"),
                rs.getString("khoa"),
                rs.getString("chucVu"),
                mucLuongValue,
                rs.getString("tenDangNhap")
        );
    }
}
