package com.httt.quanlybenhvien.repository.impl;

import com.httt.quanlybenhvien.DatabaseConfig;
import com.httt.quanlybenhvien.model.HoaDon;
import com.httt.quanlybenhvien.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HoaDonRepository implements Repository<HoaDon, String> {
    private static final String SELECT_COLUMNS = "maHoaDon, loaiHoaDon, donGia, maBenhNhan, " +
            "trangThaiThanhToan, ngayThanhToan, phuongThucThanhToan";

    @Override
    public HoaDon save(HoaDon entity) throws SQLException {
        ensurePaymentColumns();
        String sql = "INSERT INTO HoaDon (maHoaDon, loaiHoaDon, donGia, maBenhNhan, trangThaiThanhToan, ngayThanhToan, phuongThucThanhToan) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getMaHoaDon());
            ps.setString(2, entity.getLoaiHoaDon());
            ps.setInt(3, entity.getDonGia());
            ps.setString(4, entity.getMaBenhNhan());
            ps.setString(5, resolveTrangThai(entity.getTrangThaiThanhToan()));
            setTimestampOrNull(ps, 6, entity.getNgayThanhToan());
            ps.setString(7, entity.getPhuongThucThanhToan());
            ps.executeUpdate();
            return entity;
        }
    }

    @Override
    public Optional<HoaDon> findById(String maHoaDon) throws SQLException {
        ensurePaymentColumns();
        String sql = "SELECT " + SELECT_COLUMNS + " FROM HoaDon WHERE maHoaDon = ?";
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
        ensurePaymentColumns();
        String sql = "SELECT " + SELECT_COLUMNS + " FROM HoaDon ORDER BY maHoaDon DESC";
        return queryList(sql, null);
    }

    public List<HoaDon> findByMaBenhNhan(String maBenhNhan) throws SQLException {
        ensurePaymentColumns();
        String sql = "SELECT " + SELECT_COLUMNS + " FROM HoaDon WHERE maBenhNhan = ? ORDER BY maHoaDon DESC";
        return queryList(sql, maBenhNhan);
    }

    public List<HoaDon> findUnpaidByMaBenhNhan(String maBenhNhan) throws SQLException {
        ensurePaymentColumns();
        String sql = "SELECT " + SELECT_COLUMNS + " FROM HoaDon " +
                "WHERE maBenhNhan = ? AND ISNULL(trangThaiThanhToan, N'Chưa thanh toán') <> N'Đã thanh toán' " +
                "ORDER BY maHoaDon DESC";
        return queryList(sql, maBenhNhan);
    }

    @Override
    public HoaDon update(HoaDon entity) throws SQLException {
        ensurePaymentColumns();
        String sql = "UPDATE HoaDon SET loaiHoaDon = ?, donGia = ?, maBenhNhan = ?, " +
                "trangThaiThanhToan = ?, ngayThanhToan = ?, phuongThucThanhToan = ? WHERE maHoaDon = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getLoaiHoaDon());
            ps.setInt(2, entity.getDonGia());
            ps.setString(3, entity.getMaBenhNhan());
            ps.setString(4, resolveTrangThai(entity.getTrangThaiThanhToan()));
            setTimestampOrNull(ps, 5, entity.getNgayThanhToan());
            ps.setString(6, entity.getPhuongThucThanhToan());
            ps.setString(7, entity.getMaHoaDon());
            ps.executeUpdate();
            return entity;
        }
    }

    public HoaDon markAsPaid(String maHoaDon, String phuongThucThanhToan) throws SQLException {
        ensurePaymentColumns();
        String sql = "UPDATE HoaDon SET trangThaiThanhToan = ?, ngayThanhToan = ?, phuongThucThanhToan = ? WHERE maHoaDon = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, HoaDon.TRANG_THAI_DA_THANH_TOAN);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(3, phuongThucThanhToan);
            ps.setString(4, maHoaDon);
            ps.executeUpdate();
        }
        return findById(maHoaDon).orElseThrow(() -> new SQLException("Không tìm thấy hóa đơn sau khi thanh toán."));
    }

    @Override
    public void deleteById(String maHoaDon) throws SQLException {
        ensurePaymentColumns();
        String sql = "DELETE FROM HoaDon WHERE maHoaDon = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHoaDon);
            ps.executeUpdate();
        }
    }

    public String generateNextMaHoaDon() throws SQLException {
        ensurePaymentColumns();
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
        Timestamp ngayThanhToan = rs.getTimestamp("ngayThanhToan");
        return new HoaDon(
                rs.getString("maHoaDon"),
                rs.getString("loaiHoaDon"),
                rs.getInt("donGia"),
                rs.getString("maBenhNhan"),
                resolveTrangThai(rs.getString("trangThaiThanhToan")),
                ngayThanhToan == null ? null : ngayThanhToan.toLocalDateTime(),
                rs.getString("phuongThucThanhToan")
        );
    }

    private void ensurePaymentColumns() throws SQLException {
        String sql = "IF COL_LENGTH('HoaDon', 'trangThaiThanhToan') IS NULL " +
                "ALTER TABLE HoaDon ADD trangThaiThanhToan NVARCHAR(50) NOT NULL CONSTRAINT DF_HoaDon_TrangThai DEFAULT N'Chưa thanh toán'; " +
                "IF COL_LENGTH('HoaDon', 'ngayThanhToan') IS NULL " +
                "ALTER TABLE HoaDon ADD ngayThanhToan DATETIME2 NULL; " +
                "IF COL_LENGTH('HoaDon', 'phuongThucThanhToan') IS NULL " +
                "ALTER TABLE HoaDon ADD phuongThucThanhToan NVARCHAR(50) NULL;";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement statement = conn.createStatement()) {
            statement.execute(sql);
        }
    }

    private String resolveTrangThai(String trangThai) {
        if (trangThai == null || trangThai.trim().isEmpty()) {
            return HoaDon.TRANG_THAI_CHUA_THANH_TOAN;
        }
        return trangThai;
    }

    private void setTimestampOrNull(PreparedStatement ps, int index, LocalDateTime value) throws SQLException {
        if (value == null) {
            ps.setNull(index, java.sql.Types.TIMESTAMP);
        } else {
            ps.setTimestamp(index, Timestamp.valueOf(value));
        }
    }
}
