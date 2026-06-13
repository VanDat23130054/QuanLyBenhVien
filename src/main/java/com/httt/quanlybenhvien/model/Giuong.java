package com.httt.quanlybenhvien.model;

public class Giuong {
    private String maGiuong;
    private int donGia;
    private String maPhong;
    private String maBenhNhan; // optional

    public Giuong() {}

    public Giuong(String maGiuong, int donGia, String maPhong, String maBenhNhan) {
        this.maGiuong = maGiuong;
        this.donGia = donGia;
        this.maPhong = maPhong;
        this.maBenhNhan = maBenhNhan;
    }

    public String getMaGiuong() {
        return maGiuong;
    }

    public void setMaGiuong(String maGiuong) {
        this.maGiuong = maGiuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(String maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    @Override
    public String toString() {
        return "Giuong{" +
                "maGiuong='" + maGiuong + '\'' +
                ", donGia=" + donGia +
                ", maPhong='" + maPhong + '\'' +
                ", maBenhNhan='" + maBenhNhan + '\'' +
                '}';
    }
}
