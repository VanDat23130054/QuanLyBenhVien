package com.httt.quanlybenhvien.repository.impl;

import com.httt.quanlybenhvien.DatabaseConfig;
import com.httt.quanlybenhvien.model.ChiTietDonThuoc;
import com.httt.quanlybenhvien.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChiTietDonThuocRepository implements Repository<ChiTietDonThuoc, String> {

    @Override
    public ChiTietDonThuoc save(ChiTietDonThuoc entity) throws SQLException {
        String sql = "INSERT INTO ChiTietDonThuoc (maDonThuoc, maThuoc, soLuongKeDon) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getMaDonThuoc());
            ps.setString(2, entity.getMaThuoc());
            ps.setInt(3, entity.getSoLuongKeDon());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public Optional<ChiTietDonThuoc> findById(String id) throws SQLException {
        // This method is not typically used for a composite key table
        // Instead, use findByMaDonThuoc and maThuoc
        return Optional.empty();
    }

    @Override
    public List<ChiTietDonThuoc> findAll() throws SQLException {
        List<ChiTietDonThuoc> result = new ArrayList<>();
        String sql = "SELECT maDonThuoc, maThuoc, soLuongKeDon FROM ChiTietDonThuoc ORDER BY maDonThuoc, maThuoc";
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
    public ChiTietDonThuoc update(ChiTietDonThuoc entity) throws SQLException {
        String sql = "UPDATE ChiTietDonThuoc SET soLuongKeDon = ? WHERE maDonThuoc = ? AND maThuoc = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, entity.getSoLuongKeDon());
            ps.setString(2, entity.getMaDonThuoc());
            ps.setString(3, entity.getMaThuoc());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public void deleteById(String id) throws SQLException {
        // For composite key, use deleteByMaDonThuocAndMaThuoc instead
    }

    public List<ChiTietDonThuoc> findByMaDonThuoc(String maDonThuoc) throws SQLException {
        List<ChiTietDonThuoc> result = new ArrayList<>();
        String sql = "SELECT maDonThuoc, maThuoc, soLuongKeDon FROM ChiTietDonThuoc WHERE maDonThuoc = ? ORDER BY maThuoc";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDonThuoc);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }
        }
        return result;
    }

    public void deleteByMaDonThuoc(String maDonThuoc) throws SQLException {
        String sql = "DELETE FROM ChiTietDonThuoc WHERE maDonThuoc = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDonThuoc);
            ps.executeUpdate();
        }
    }

    public void deleteByMaDonThuocAndMaThuoc(String maDonThuoc, String maThuoc) throws SQLException {
        String sql = "DELETE FROM ChiTietDonThuoc WHERE maDonThuoc = ? AND maThuoc = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDonThuoc);
            ps.setString(2, maThuoc);
            ps.executeUpdate();
        }
    }

    private ChiTietDonThuoc mapRow(ResultSet rs) throws SQLException {
        return new ChiTietDonThuoc(
                rs.getString("maDonThuoc"),
                rs.getString("maThuoc"),
                rs.getInt("soLuongKeDon")
        );
    }
}
