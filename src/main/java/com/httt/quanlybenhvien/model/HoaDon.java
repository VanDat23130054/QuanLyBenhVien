package com.httt.quanlybenhvien.model;

public class HoaDon {
    private String maHoaDon;
    private String loaiHoaDon;
    private int donGia;
    private String maBenhNhan;

    public HoaDon() {}

    public HoaDon(String maHoaDon, String loaiHoaDon, int donGia, String maBenhNhan) {
        this.maHoaDon = maHoaDon;
        this.loaiHoaDon = loaiHoaDon;
        this.donGia = donGia;
        this.maBenhNhan = maBenhNhan;
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

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", loaiHoaDon='" + loaiHoaDon + '\'' +
                ", donGia=" + donGia +
                ", maBenhNhan='" + maBenhNhan + '\'' +
                '}';
    }
}
