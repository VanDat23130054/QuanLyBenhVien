package com.httt.quanlybenhvien.repository.impl;

import com.httt.quanlybenhvien.DatabaseConfig;
import com.httt.quanlybenhvien.model.LichKham;
import com.httt.quanlybenhvien.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LichKhamRepository implements Repository<LichKham, String> {

    @Override
    public LichKham save(LichKham entity) throws SQLException {
        String sql = "INSERT INTO LichKham (maLichKham, ngayKham, chanDoan, maBenhNhan, maLeTan, maBacSi) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getMaLichKham());
            ps.setTimestamp(2, Timestamp.valueOf(entity.getNgayKham()));
            ps.setString(3, entity.getChanDoan());
            ps.setString(4, entity.getMaBenhNhan());
            ps.setString(5, entity.getMaLeTan());
            ps.setString(6, entity.getMaBacSi());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public Optional<LichKham> findById(String maLichKham) throws SQLException {
        String sql = "SELECT maLichKham, ngayKham, chanDoan, maBenhNhan, maLeTan, maBacSi FROM LichKham WHERE maLichKham = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maLichKham);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<LichKham> findAll() throws SQLException {
        String sql = "SELECT maLichKham, ngayKham, chanDoan, maBenhNhan, maLeTan, maBacSi FROM LichKham ORDER BY ngayKham DESC";
        return queryList(sql, null);
    }

    public List<LichKham> findByMaBenhNhan(String maBenhNhan) throws SQLException {
        String sql = "SELECT maLichKham, ngayKham, chanDoan, maBenhNhan, maLeTan, maBacSi " +
                "FROM LichKham WHERE maBenhNhan = ? ORDER BY ngayKham DESC";
        return queryList(sql, maBenhNhan);
    }

    @Override
    public LichKham update(LichKham entity) throws SQLException {
        String sql = "UPDATE LichKham SET ngayKham = ?, chanDoan = ?, maBenhNhan = ?, maLeTan = ?, maBacSi = ? WHERE maLichKham = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(entity.getNgayKham()));
            ps.setString(2, entity.getChanDoan());
            ps.setString(3, entity.getMaBenhNhan());
            ps.setString(4, entity.getMaLeTan());
            ps.setString(5, entity.getMaBacSi());
            ps.setString(6, entity.getMaLichKham());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public void deleteById(String maLichKham) throws SQLException {
        String sql = "DELETE FROM LichKham WHERE maLichKham = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maLichKham);
            ps.executeUpdate();
        }
    }

    public String generateNextMaLichKham() throws SQLException {
        String sql = "SELECT maLichKham FROM LichKham WHERE maLichKham LIKE 'LK%' ORDER BY maLichKham DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            int max = 0;
            while (rs.next()) {
                max = Math.max(max, parseNumber(rs.getString("maLichKham"), "LK"));
            }
            return String.format("LK%03d", max + 1);
        }
    }

    private List<LichKham> queryList(String sql, String parameter) throws SQLException {
        List<LichKham> result = new ArrayList<>();
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

    private LichKham mapRow(ResultSet rs) throws SQLException {
        return new LichKham(
                rs.getString("maLichKham"),
                rs.getTimestamp("ngayKham").toLocalDateTime(),
                rs.getString("chanDoan"),
                rs.getString("maBenhNhan"),
                rs.getString("maLeTan"),
                rs.getString("maBacSi")
        );
    }
    public boolean updateChanDoan(String maLichKham, String chanDoan) throws SQLException {

    String sql = "UPDATE LichKham SET chanDoan = ? WHERE maLichKham = ?";

    try (Connection conn = DatabaseConfig.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, chanDoan);
        ps.setString(2, maLichKham);

        return ps.executeUpdate() > 0;
    }
}
}
