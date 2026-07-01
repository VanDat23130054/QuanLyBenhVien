package com.httt.quanlybenhvien.repository.impl;

import com.httt.quanlybenhvien.DatabaseConfig;
import com.httt.quanlybenhvien.model.Thuoc;
import com.httt.quanlybenhvien.repository.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThuocRepository implements Repository<Thuoc, String> {

    @Override
    public Thuoc save(Thuoc entity) throws SQLException {
        String sql = "INSERT INTO Thuoc (maThuoc, tenThuoc, ngayNhap, donVi, soLuong, donGia, hanDung, maKhoThuoc) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getMaThuoc());
            ps.setString(2, entity.getTenThuoc());
            ps.setTimestamp(3, entity.getNgayNhap() != null ? Timestamp.valueOf(entity.getNgayNhap()) : null);
            ps.setString(4, entity.getDonVi());
            ps.setInt(5, entity.getSoLuong());
            ps.setInt(6, entity.getDonGia());
            ps.setDate(7, entity.getHanDung() != null ? Date.valueOf(entity.getHanDung()) : null);
            ps.setString(8, entity.getMaKhoThuoc());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public Optional<Thuoc> findById(String maThuoc) throws SQLException {
        String sql = "SELECT maThuoc, tenThuoc, ngayNhap, donVi, soLuong, donGia, hanDung, maKhoThuoc FROM Thuoc WHERE maThuoc = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maThuoc);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Thuoc> findAll() throws SQLException {
        List<Thuoc> result = new ArrayList<>();
        String sql = "SELECT maThuoc, tenThuoc, ngayNhap, donVi, soLuong, donGia, hanDung, maKhoThuoc FROM Thuoc ORDER BY maThuoc";
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
    public Thuoc update(Thuoc entity) throws SQLException {
        String sql = "UPDATE Thuoc SET tenThuoc = ?, ngayNhap = ?, donVi = ?, soLuong = ?, donGia = ?, hanDung = ?, maKhoThuoc = ? WHERE maThuoc = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getTenThuoc());
            ps.setTimestamp(2, entity.getNgayNhap() != null ? Timestamp.valueOf(entity.getNgayNhap()) : null);
            ps.setString(3, entity.getDonVi());
            ps.setInt(4, entity.getSoLuong());
            ps.setInt(5, entity.getDonGia());
            ps.setDate(6, entity.getHanDung() != null ? Date.valueOf(entity.getHanDung()) : null);
            ps.setString(7, entity.getMaKhoThuoc());
            ps.setString(8, entity.getMaThuoc());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public void deleteById(String maThuoc) throws SQLException {
        String sql = "DELETE FROM Thuoc WHERE maThuoc = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maThuoc);
            ps.executeUpdate();
        }
    }

    public String generateNextMaThuoc() throws SQLException {
        String sql = "SELECT maThuoc FROM Thuoc WHERE maThuoc LIKE 'TH%' ORDER BY maThuoc DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            int max = 0;
            while (rs.next()) {
                max = Math.max(max, parseNumber(rs.getString("maThuoc"), "TH"));
            }
            return String.format("TH%03d", max + 1);
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

    private Thuoc mapRow(ResultSet rs) throws SQLException {
        LocalDateTime ngayNhap = null;
        Timestamp ts = rs.getTimestamp("ngayNhap");
        if (ts != null) {
            ngayNhap = ts.toLocalDateTime();
        }

        LocalDate hanDung = null;
        Date dateHanDung = rs.getDate("hanDung");
        if (dateHanDung != null) {
            hanDung = dateHanDung.toLocalDate();
        }

        return new Thuoc(
                rs.getString("maThuoc"),
                rs.getString("tenThuoc"),
                ngayNhap,
                rs.getString("donVi"),
                rs.getInt("soLuong"),
                rs.getInt("donGia"),
                hanDung,
                rs.getString("maKhoThuoc")
        );
    }
}
