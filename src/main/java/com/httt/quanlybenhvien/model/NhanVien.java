package com.httt.quanlybenhvien.model;

public class NhanVien {

    private String maNhanVien;
    private String tenNhanVien;
    private String khoa;
    private String chucVu;
    private double mucLuong;

    private TaiKhoan taiKhoan;

    public NhanVien() {
    }

    public NhanVien(String maNhanVien, String tenNhanVien, String khoa,
                    String chucVu, double mucLuong, TaiKhoan taiKhoan) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.khoa = khoa;
        this.chucVu = chucVu;
        this.mucLuong = mucLuong;
        this.taiKhoan = taiKhoan;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public double getMucLuong() {
        return mucLuong;
    }

    public void setMucLuong(double mucLuong) {
        this.mucLuong = mucLuong;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNhanVien='" + maNhanVien + '\'' +
                ", tenNhanVien='" + tenNhanVien + '\'' +
                ", khoa='" + khoa + '\'' +
                ", chucVu='" + chucVu + '\'' +
                ", mucLuong=" + mucLuong +
                ", taiKhoan=" + (taiKhoan != null ? taiKhoan.getTenDangNhap() : null) +
                '}';
    }
}
