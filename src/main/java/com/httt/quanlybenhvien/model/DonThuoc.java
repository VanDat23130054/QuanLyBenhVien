package com.httt.quanlybenhvien.model;

import java.util.List;

public class DonThuoc {
    private String maDonThuoc;
    private LichKham lichKham;
    private NhanVien bacSi;
    private NhanVien duocSi;

    private List<ChiTietDonThuoc> chiTietList;

    public DonThuoc() {
    }

    public DonThuoc(String maDonThuoc, LichKham lichKham,
                    NhanVien bacSi, NhanVien duocSi) {
        this.maDonThuoc = maDonThuoc;
        this.lichKham = lichKham;
        this.bacSi = bacSi;
        this.duocSi = duocSi;
    }

    public String getMaDonThuoc() {
        return maDonThuoc;
    }

    public void setMaDonThuoc(String maDonThuoc) {
        this.maDonThuoc = maDonThuoc;
    }

    public LichKham getLichKham() {
        return lichKham;
    }

    public void setLichKham(LichKham lichKham) {
        this.lichKham = lichKham;
    }

    public NhanVien getBacSi() {
        return bacSi;
    }

    public void setBacSi(NhanVien bacSi) {
        this.bacSi = bacSi;
    }

    public NhanVien getDuocSi() {
        return duocSi;
    }

    public void setDuocSi(NhanVien duocSi) {
        this.duocSi = duocSi;
    }

    public List<ChiTietDonThuoc> getChiTietList() {
        return chiTietList;
    }

    public void setChiTietList(List<ChiTietDonThuoc> chiTietList) {
        this.chiTietList = chiTietList;
    }

    @Override
    public String toString() {
        return "DonThuoc{" +
                "maDonThuoc='" + maDonThuoc + '\'' +
                ", lichKham=" + (lichKham != null ? lichKham.getMaLichKham() : null) +
                ", bacSi=" + (bacSi != null ? bacSi.getMaNhanVien() : null) +
                ", duocSi=" + (duocSi != null ? duocSi.getMaNhanVien() : null) +
                '}';
    }
}
