package com.httt.quanlybenhvien.model;

public class DonThuoc {
    private String maDonThuoc;
    private String maLichKham;
    private String maBacSi;
    private String maDuocSi; // optional

    public DonThuoc() {}

    public DonThuoc(String maDonThuoc, String maLichKham, String maBacSi, String maDuocSi) {
        this.maDonThuoc = maDonThuoc;
        this.maLichKham = maLichKham;
        this.maBacSi = maBacSi;
        this.maDuocSi = maDuocSi;
    }

    public String getMaDonThuoc() {
        return maDonThuoc;
    }

    public void setMaDonThuoc(String maDonThuoc) {
        this.maDonThuoc = maDonThuoc;
    }

    public String getMaLichKham() {
        return maLichKham;
    }

    public void setMaLichKham(String maLichKham) {
        this.maLichKham = maLichKham;
    }

    public String getMaBacSi() {
        return maBacSi;
    }

    public void setMaBacSi(String maBacSi) {
        this.maBacSi = maBacSi;
    }

    public String getMaDuocSi() {
        return maDuocSi;
    }

    public void setMaDuocSi(String maDuocSi) {
        this.maDuocSi = maDuocSi;
    }

    @Override
    public String toString() {
        return "DonThuoc{" +
                "maDonThuoc='" + maDonThuoc + '\'' +
                ", maLichKham='" + maLichKham + '\'' +
                ", maBacSi='" + maBacSi + '\'' +
                ", maDuocSi='" + maDuocSi + '\'' +
                '}';
    }
}
