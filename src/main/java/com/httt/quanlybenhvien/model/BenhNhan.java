package com.httt.quanlybenhvien.model;

public class BenhNhan {
    private String maBenhNhan;
    private String tenBenhNhan;
    private String tenDangNhap; // optional link to TaiKhoan

    public BenhNhan() {}

    public BenhNhan(String maBenhNhan, String tenBenhNhan, String tenDangNhap) {
        this.maBenhNhan = maBenhNhan;
        this.tenBenhNhan = tenBenhNhan;
        this.tenDangNhap = tenDangNhap;
    }

    public String getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(String maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public String getTenBenhNhan() {
        return tenBenhNhan;
    }

    public void setTenBenhNhan(String tenBenhNhan) {
        this.tenBenhNhan = tenBenhNhan;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    @Override
    public String toString() {
        return "BenhNhan{" +
                "maBenhNhan='" + maBenhNhan + '\'' +
                ", tenBenhNhan='" + tenBenhNhan + '\'' +
                ", tenDangNhap='" + tenDangNhap + '\'' +
                '}';
    }
}
