package com.httt.quanlybenhvien.repository.impl;

import com.httt.quanlybenhvien.DatabaseConfig;
import com.httt.quanlybenhvien.model.BenhNhan;
import com.httt.quanlybenhvien.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BenhNhanRepository implements Repository<BenhNhan, String> {

    @Override
    public BenhNhan save(BenhNhan entity) throws SQLException {
        String sql = "INSERT INTO BenhNhan (maBenhNhan, tenBenhNhan, tenDangNhap) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getMaBenhNhan());
            ps.setString(2, entity.getTenBenhNhan());
            ps.setString(3, entity.getTenDangNhap());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public Optional<BenhNhan> findById(String maBenhNhan) throws SQLException {
        String sql = "SELECT maBenhNhan, tenBenhNhan, tenDangNhap FROM BenhNhan WHERE maBenhNhan = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maBenhNhan);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<BenhNhan> findByTenDangNhap(String tenDangNhap) throws SQLException {
        String sql = "SELECT maBenhNhan, tenBenhNhan, tenDangNhap FROM BenhNhan WHERE tenDangNhap = ?";
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
    public List<BenhNhan> findAll() throws SQLException {
        List<BenhNhan> result = new ArrayList<>();
        String sql = "SELECT maBenhNhan, tenBenhNhan, tenDangNhap FROM BenhNhan ORDER BY maBenhNhan";
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
    public BenhNhan update(BenhNhan entity) throws SQLException {
        String sql = "UPDATE BenhNhan SET tenBenhNhan = ?, tenDangNhap = ? WHERE maBenhNhan = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getTenBenhNhan());
            ps.setString(2, entity.getTenDangNhap());
            ps.setString(3, entity.getMaBenhNhan());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public void deleteById(String maBenhNhan) throws SQLException {
        String sql = "DELETE FROM BenhNhan WHERE maBenhNhan = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maBenhNhan);
            ps.executeUpdate();
        }
    }

    public String generateNextMaBenhNhan() throws SQLException {
        String sql = "SELECT maBenhNhan FROM BenhNhan WHERE maBenhNhan LIKE 'BN%' ORDER BY maBenhNhan DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            int max = 0;
            while (rs.next()) {
                max = Math.max(max, parseNumber(rs.getString("maBenhNhan"), "BN"));
            }
            return String.format("BN%03d", max + 1);
        }
    }

    private int parseNumber(String value, String prefix) {
        if (value == null || !value.startsWith(prefix)) return 0;
        try {
            return Integer.parseInt(value.substring(prefix.length()));
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private BenhNhan mapRow(ResultSet rs) throws SQLException {
        return new BenhNhan(
                rs.getString("maBenhNhan"),
                rs.getString("tenBenhNhan"),
                rs.getString("tenDangNhap")
        );
    }
}
