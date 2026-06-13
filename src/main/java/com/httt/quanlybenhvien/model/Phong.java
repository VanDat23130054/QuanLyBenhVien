package com.httt.quanlybenhvien.model;

public class Phong {
    private String maPhong;

    public Phong() {}

    public Phong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    @Override
    public String toString() {
        return "Phong{" +
                "maPhong='" + maPhong + '\'' +
                '}';
    }
}
