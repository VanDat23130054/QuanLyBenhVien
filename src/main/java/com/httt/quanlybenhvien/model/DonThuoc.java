package com.httt.quanlybenhvien.model;

import java.util.List;

public class DonThuoc {
    private String maDonThuoc;
    private LichKham lichKham;
    private NhanVien bacSi;
    private NhanVien duocSi;

    private List<ChiTietDonThuoc> chiTietList;

    public DonThuoc() {}
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
                ", maLichKham='" + maLichKham + '\'' +
                ", maBacSi='" + maBacSi + '\'' +
                ", maDuocSi='" + maDuocSi + '\'' +
                '}';
    }
}
