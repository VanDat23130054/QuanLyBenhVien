package com.httt.quanlybenhvien.model;

import java.util.ArrayList;
import java.util.List;

public class Phong {

    private String maPhong;

    private List<Giuong> danhSachGiuong = new ArrayList<>();
    private List<LichTruc> danhSachLichTruc = new ArrayList<>();

    public Phong() {
    }

    public Phong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public List<Giuong> getDanhSachGiuong() {
        return danhSachGiuong;
    }

    public void setDanhSachGiuong(List<Giuong> danhSachGiuong) {
        this.danhSachGiuong = danhSachGiuong;
    }

    public List<LichTruc> getDanhSachLichTruc() {
        return danhSachLichTruc;
    }

    public void setDanhSachLichTruc(List<LichTruc> danhSachLichTruc) {
        this.danhSachLichTruc = danhSachLichTruc;
    }

    @Override
    public String toString() {
        return "Phong{" +
                "maPhong='" + maPhong + '\'' +
                ", soGiuong=" + (danhSachGiuong != null ? danhSachGiuong.size() : 0) +
                ", soLichTruc=" + (danhSachLichTruc != null ? danhSachLichTruc.size() : 0) +
                '}';
    }
}
