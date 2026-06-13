package com.httt.quanlybenhvien.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Thuoc {
    private String maThuoc;
    private String tenThuoc;
    private LocalDateTime ngayNhap; // DATETIME2
    private String donVi;
    private int soLuong;
    private int donGia;
    private LocalDate hanDung; // DATE
    private String maKhoThuoc;

    public Thuoc() {}

    public Thuoc(String maThuoc, String tenThuoc, LocalDateTime ngayNhap, String donVi, int soLuong, int donGia, LocalDate hanDung, String maKhoThuoc) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.ngayNhap = ngayNhap;
        this.donVi = donVi;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.hanDung = hanDung;
        this.maKhoThuoc = maKhoThuoc;
    }

    public String getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public LocalDateTime getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDateTime ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public LocalDate getHanDung() {
        return hanDung;
    }

    public void setHanDung(LocalDate hanDung) {
        this.hanDung = hanDung;
    }

    public String getMaKhoThuoc() {
        return maKhoThuoc;
    }

    public void setMaKhoThuoc(String maKhoThuoc) {
        this.maKhoThuoc = maKhoThuoc;
    }

    @Override
    public String toString() {
        return "Thuoc{" +
                "maThuoc='" + maThuoc + '\'' +
                ", tenThuoc='" + tenThuoc + '\'' +
                ", ngayNhap=" + ngayNhap +
                ", donVi='" + donVi + '\'' +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", hanDung=" + hanDung +
                ", maKhoThuoc='" + maKhoThuoc + '\'' +
                '}';
    }
}
