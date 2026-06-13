package com.httt.quanlybenhvien.model;

public class KhoThuoc {
    private String maKhoThuoc;
    private String maDuocSi; // references NhanVien.maNhanVien

    public KhoThuoc() {}

    public KhoThuoc(String maKhoThuoc, String maDuocSi) {
        this.maKhoThuoc = maKhoThuoc;
        this.maDuocSi = maDuocSi;
    }

    public String getMaKhoThuoc() {
        return maKhoThuoc;
    }

    public void setMaKhoThuoc(String maKhoThuoc) {
        this.maKhoThuoc = maKhoThuoc;
    }

    public String getMaDuocSi() {
        return maDuocSi;
    }

    public void setMaDuocSi(String maDuocSi) {
        this.maDuocSi = maDuocSi;
    }

    @Override
    public String toString() {
        return "KhoThuoc{" +
                "maKhoThuoc='" + maKhoThuoc + '\'' +
                ", maDuocSi='" + maDuocSi + '\'' +
                '}';
    }
}
