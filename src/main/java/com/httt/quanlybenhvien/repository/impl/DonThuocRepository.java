package com.httt.quanlybenhvien.repository.impl;

import com.httt.quanlybenhvien.DatabaseConfig;
import com.httt.quanlybenhvien.model.DonThuoc;
import com.httt.quanlybenhvien.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DonThuocRepository implements Repository<DonThuoc, String> {

    @Override
    public DonThuoc save(DonThuoc entity) throws SQLException {
        String sql = "INSERT INTO DonThuoc (maDonThuoc, maLichKham, maBacSi, maDuocSi) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getMaDonThuoc());
            ps.setString(2, entity.getMaLichKham());
            ps.setString(3, entity.getMaBacSi());
            ps.setString(4, entity.getMaDuocSi());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public Optional<DonThuoc> findById(String maDonThuoc) throws SQLException {
        String sql = "SELECT maDonThuoc, maLichKham, maBacSi, maDuocSi FROM DonThuoc WHERE maDonThuoc = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDonThuoc);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<DonThuoc> findAll() throws SQLException {
        List<DonThuoc> result = new ArrayList<>();
        String sql = "SELECT maDonThuoc, maLichKham, maBacSi, maDuocSi FROM DonThuoc ORDER BY maDonThuoc";
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
    public DonThuoc update(DonThuoc entity) throws SQLException {
        String sql = "UPDATE DonThuoc SET maLichKham = ?, maBacSi = ?, maDuocSi = ? WHERE maDonThuoc = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getMaLichKham());
            ps.setString(2, entity.getMaBacSi());
            ps.setString(3, entity.getMaDuocSi());
            ps.setString(4, entity.getMaDonThuoc());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public void deleteById(String maDonThuoc) throws SQLException {
        String sql = "DELETE FROM DonThuoc WHERE maDonThuoc = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDonThuoc);
            ps.executeUpdate();
        }
    }

    public String generateNextMaDonThuoc() throws SQLException {
        String sql = "SELECT maDonThuoc FROM DonThuoc WHERE maDonThuoc LIKE 'DT%' ORDER BY maDonThuoc DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            int max = 0;
            while (rs.next()) {
                max = Math.max(max, parseNumber(rs.getString("maDonThuoc"), "DT"));
            }
            return String.format("DT%03d", max + 1);
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

    private DonThuoc mapRow(ResultSet rs) throws SQLException {
        return new DonThuoc(
                rs.getString("maDonThuoc"),
                rs.getString("maLichKham"),
                rs.getString("maBacSi"),
                rs.getString("maDuocSi")
        );
    }
}
