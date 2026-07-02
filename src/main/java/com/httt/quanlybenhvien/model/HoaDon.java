package com.httt.quanlybenhvien.model;

import java.time.LocalDateTime;

public class HoaDon {
    public static final String TRANG_THAI_CHUA_THANH_TOAN = "Chưa thanh toán";
    public static final String TRANG_THAI_DA_THANH_TOAN = "Đã thanh toán";

    private String maHoaDon;
    private String loaiHoaDon;
    private int donGia;
    private String maBenhNhan;
    private String trangThaiThanhToan;
    private LocalDateTime ngayThanhToan;
    private String phuongThucThanhToan;

    public HoaDon() {}

    public HoaDon(String maHoaDon, String loaiHoaDon, int donGia, String maBenhNhan) {
        this(maHoaDon, loaiHoaDon, donGia, maBenhNhan, TRANG_THAI_CHUA_THANH_TOAN, null, null);
    }

    public HoaDon(String maHoaDon, String loaiHoaDon, int donGia, String maBenhNhan,
                  String trangThaiThanhToan, LocalDateTime ngayThanhToan, String phuongThucThanhToan) {
        this.maHoaDon = maHoaDon;
        this.loaiHoaDon = loaiHoaDon;
        this.donGia = donGia;
        this.maBenhNhan = maBenhNhan;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.ngayThanhToan = ngayThanhToan;
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

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

    public String getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(String maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
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

    public boolean daThanhToan() {
        return TRANG_THAI_DA_THANH_TOAN.equalsIgnoreCase(trangThaiThanhToan);
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", loaiHoaDon='" + loaiHoaDon + '\'' +
                ", donGia=" + donGia +
                ", maBenhNhan='" + maBenhNhan + '\'' +
                ", trangThaiThanhToan='" + trangThaiThanhToan + '\'' +
                ", ngayThanhToan=" + ngayThanhToan +
                ", phuongThucThanhToan='" + phuongThucThanhToan + '\'' +
                '}';
    }
}
