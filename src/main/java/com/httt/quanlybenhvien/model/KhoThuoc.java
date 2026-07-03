package com.httt.quanlybenhvien.model;

import java.util.ArrayList;
import java.util.List;

public class KhoThuoc {

    private String maKhoThuoc;

    private NhanVien duocSi;

    private List<Thuoc> danhSachThuoc = new ArrayList<>();

    public KhoThuoc() {
    }

    public KhoThuoc(String maKhoThuoc, NhanVien duocSi) {
        this.maKhoThuoc = maKhoThuoc;
        this.duocSi = duocSi;
    }

    public String getMaKhoThuoc() {
        return maKhoThuoc;
    }

    public void setMaKhoThuoc(String maKhoThuoc) {
        this.maKhoThuoc = maKhoThuoc;
    }

    public NhanVien getDuocSi() {
        return duocSi;
    }

    public void setDuocSi(NhanVien duocSi) {
        this.duocSi = duocSi;
    }

    public List<Thuoc> getDanhSachThuoc() {
        return danhSachThuoc;
    }

    public void setDanhSachThuoc(List<Thuoc> danhSachThuoc) {
        this.danhSachThuoc = danhSachThuoc;
    }

    @Override
    public String toString() {
        return "KhoThuoc{" +
                "maKhoThuoc='" + maKhoThuoc + '\'' +
                ", duocSi=" + (duocSi != null ? duocSi.getMaNhanVien() : null) +
                ", soThuoc=" + (danhSachThuoc != null ? danhSachThuoc.size() : 0) +
                '}';
    }
}
