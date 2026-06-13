package com.httt.quanlybenhvien.model;

public class ChiTietDonThuoc {
    private String maDonThuoc;
    private String maThuoc;
    private int soLuongKeDon;

    public ChiTietDonThuoc() {}

    public ChiTietDonThuoc(String maDonThuoc, String maThuoc, int soLuongKeDon) {
        this.maDonThuoc = maDonThuoc;
        this.maThuoc = maThuoc;
        this.soLuongKeDon = soLuongKeDon;
    }

    public String getMaDonThuoc() {
        return maDonThuoc;
    }

    public void setMaDonThuoc(String maDonThuoc) {
        this.maDonThuoc = maDonThuoc;
    }

    public String getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public int getSoLuongKeDon() {
        return soLuongKeDon;
    }

    public void setSoLuongKeDon(int soLuongKeDon) {
        this.soLuongKeDon = soLuongKeDon;
    }

    @Override
    public String toString() {
        return "ChiTietDonThuoc{" +
                "maDonThuoc='" + maDonThuoc + '\'' +
                ", maThuoc='" + maThuoc + '\'' +
                ", soLuongKeDon=" + soLuongKeDon +
                '}';
    }
}
