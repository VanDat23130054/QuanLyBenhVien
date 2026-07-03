package com.httt.quanlybenhvien.model;

import java.time.LocalDateTime;

public class HoaDon {

    public static final String CHUA_THANH_TOAN = "Chưa thanh toán";
    public static final String DA_THANH_TOAN = "Đã thanh toán";

    private String maHoaDon;
    private String loaiHoaDon;
    private int donGia;

    private BenhNhan benhNhan;

    private String trangThaiThanhToan;
    private LocalDateTime ngayThanhToan;
    private String phuongThucThanhToan;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, String loaiHoaDon, int donGia, BenhNhan benhNhan) {
        this.maHoaDon = maHoaDon;
        this.loaiHoaDon = loaiHoaDon;
        this.donGia = donGia;
        this.benhNhan = benhNhan;
        this.trangThaiThanhToan = CHUA_THANH_TOAN;
    }

    public HoaDon(String maHoaDon, String loaiHoaDon, int donGia, BenhNhan benhNhan,
                  String trangThaiThanhToan, LocalDateTime ngayThanhToan,
                  String phuongThucThanhToan) {
        this.maHoaDon = maHoaDon;
        this.loaiHoaDon = loaiHoaDon;
        this.donGia = donGia;
        this.benhNhan = benhNhan;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.ngayThanhToan = ngayThanhToan;
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    // ===== GETTER SETTER =====

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getLoaiHoaDon() {
        return loaiHoaDon;
    }

    public void setLoaiHoaDon(String loaiHoaDon) {
        this.loaiHoaDon = loaiHoaDon;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public BenhNhan getBenhNhan() {
        return benhNhan;
    }

    public void setBenhNhan(BenhNhan benhNhan) {
        this.benhNhan = benhNhan;
    }

    public String getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }

    public void setTrangThaiThanhToan(String trangThaiThanhToan) {
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

    public LocalDateTime getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(LocalDateTime ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    // ===== BUSINESS METHOD (OK giữ lại) =====
    public boolean daThanhToan() {
        return DA_THANH_TOAN.equalsIgnoreCase(trangThaiThanhToan);
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", loaiHoaDon='" + loaiHoaDon + '\'' +
                ", donGia=" + donGia +
                ", benhNhan=" + (benhNhan != null ? benhNhan.getMaBenhNhan() : null) +
                ", trangThaiThanhToan='" + trangThaiThanhToan + '\'' +
                ", ngayThanhToan=" + ngayThanhToan +
                ", phuongThucThanhToan='" + phuongThucThanhToan + '\'' +
                '}';
    }
}
