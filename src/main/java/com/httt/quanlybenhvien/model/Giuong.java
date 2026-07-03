package com.httt.quanlybenhvien.model;

public class Giuong {

    private String maGiuong;
    private int donGia;

    private Phong phong;
    private BenhNhan benhNhan;

    public Giuong() {}

    public Giuong(String maGiuong, int donGia, Phong phong, BenhNhan benhNhan) {
        this.maGiuong = maGiuong;
        this.donGia = donGia;
        this.phong = phong;
        this.benhNhan = benhNhan;
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

    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) {
        this.phong = phong;
    }

    public BenhNhan getBenhNhan() {
        return benhNhan;
    }

    public void setBenhNhan(BenhNhan benhNhan) {
        this.benhNhan = benhNhan;
    }

    @Override
    public String toString() {
        return "Giuong{" +
                "maGiuong='" + maGiuong + '\'' +
                ", donGia=" + donGia +
                ", phong=" + (phong != null ? phong.getMaPhong() : null) +
                ", benhNhan=" + (benhNhan != null ? benhNhan.getMaBenhNhan() : null) +
                '}';
    }
}
