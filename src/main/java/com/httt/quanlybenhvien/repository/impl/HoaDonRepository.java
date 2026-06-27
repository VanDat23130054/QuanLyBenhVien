package com.httt.quanlybenhvien.repository.impl;

import com.httt.quanlybenhvien.DatabaseConfig;
import com.httt.quanlybenhvien.model.HoaDon;
import com.httt.quanlybenhvien.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HoaDonRepository implements Repository<HoaDon, String> {

    @Override
    public HoaDon save(HoaDon entity) throws SQLException {
        String sql = "INSERT INTO HoaDon (maHoaDon, loaiHoaDon, donGia, maBenhNhan) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getMaHoaDon());
            ps.setString(2, entity.getLoaiHoaDon());
            ps.setInt(3, entity.getDonGia());
            ps.setString(4, entity.getMaBenhNhan());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public Optional<HoaDon> findById(String maHoaDon) throws SQLException {
        String sql = "SELECT maHoaDon, loaiHoaDon, donGia, maBenhNhan FROM HoaDon WHERE maHoaDon = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHoaDon);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<HoaDon> findAll() throws SQLException {
        String sql = "SELECT maHoaDon, loaiHoaDon, donGia, maBenhNhan FROM HoaDon ORDER BY maHoaDon DESC";
        return queryList(sql, null);
    }

    public List<HoaDon> findByMaBenhNhan(String maBenhNhan) throws SQLException {
        String sql = "SELECT maHoaDon, loaiHoaDon, donGia, maBenhNhan FROM HoaDon WHERE maBenhNhan = ? ORDER BY maHoaDon DESC";
        return queryList(sql, maBenhNhan);
    }

    @Override
    public HoaDon update(HoaDon entity) throws SQLException {
        String sql = "UPDATE HoaDon SET loaiHoaDon = ?, donGia = ?, maBenhNhan = ? WHERE maHoaDon = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getLoaiHoaDon());
            ps.setInt(2, entity.getDonGia());
            ps.setString(3, entity.getMaBenhNhan());
            ps.setString(4, entity.getMaHoaDon());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public void deleteById(String maHoaDon) throws SQLException {
        String sql = "DELETE FROM HoaDon WHERE maHoaDon = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHoaDon);
            ps.executeUpdate();
        }
    }

    public String generateNextMaHoaDon() throws SQLException {
        String sql = "SELECT maHoaDon FROM HoaDon WHERE maHoaDon LIKE 'HD%' ORDER BY maHoaDon DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            int max = 0;
            while (rs.next()) {
                max = Math.max(max, parseNumber(rs.getString("maHoaDon"), "HD"));
            }
            return String.format("HD%03d", max + 1);
        }
    }

    private List<HoaDon> queryList(String sql, String parameter) throws SQLException {
        List<HoaDon> result = new ArrayList<>();
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

    private int parseNumber(String value, String prefix) {
        if (value == null || !value.startsWith(prefix)) return 0;
        try {
            return Integer.parseInt(value.substring(prefix.length()));
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private HoaDon mapRow(ResultSet rs) throws SQLException {
        return new HoaDon(
                rs.getString("maHoaDon"),
                rs.getString("loaiHoaDon"),
                rs.getInt("donGia"),
                rs.getString("maBenhNhan")
        );
    }
}
