package com.httt.quanlybenhvien.model;

public class NhanVien {
    private String maNhanVien;
    private String tenNhanVien;
    private String khoa;
    private String chucVu;
    private Long mucLuong;
    private String tenDangNhap;

    public NhanVien() {}

    public NhanVien(String maNhanVien, String tenNhanVien, String khoa, String chucVu, Long mucLuong, String tenDangNhap) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.khoa = khoa;
        this.chucVu = chucVu;
        this.mucLuong = mucLuong;
        this.tenDangNhap = tenDangNhap;
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

    public Long getMucLuong() {
        return mucLuong;
    }

    public void setMucLuong(Long mucLuong) {
        this.mucLuong = mucLuong;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNhanVien='" + maNhanVien + '\'' +
                ", tenNhanVien='" + tenNhanVien + '\'' +
                ", khoa='" + khoa + '\'' +
                ", chucVu='" + chucVu + '\'' +
                ", mucLuong=" + mucLuong +
                ", tenDangNhap='" + tenDangNhap + '\'' +
                '}';
    }
}
