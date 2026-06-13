package com.httt.quanlybenhvien.model;

import java.time.LocalDateTime;

public class LichTruc {
    private String maLichTruc;
    private LocalDateTime ngayTruc;
    private String caTruc;
    private String maNhanVien;
    private String maPhong;
    private String maPhanCong;

    public LichTruc() {}

    public LichTruc(String maLichTruc, LocalDateTime ngayTruc, String caTruc, String maNhanVien, String maPhong, String maPhanCong) {
        this.maLichTruc = maLichTruc;
        this.ngayTruc = ngayTruc;
        this.caTruc = caTruc;
        this.maNhanVien = maNhanVien;
        this.maPhong = maPhong;
        this.maPhanCong = maPhanCong;
    }

    public String getMaLichTruc() {
        return maLichTruc;
    }

    public void setMaLichTruc(String maLichTruc) {
        this.maLichTruc = maLichTruc;
    }

    public LocalDateTime getNgayTruc() {
        return ngayTruc;
    }

    public void setNgayTruc(LocalDateTime ngayTruc) {
        this.ngayTruc = ngayTruc;
    }

    public String getCaTruc() {
        return caTruc;
    }

    public void setCaTruc(String caTruc) {
        this.caTruc = caTruc;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaPhanCong() {
        return maPhanCong;
    }

    public void setMaPhanCong(String maPhanCong) {
        this.maPhanCong = maPhanCong;
    }

    @Override
    public String toString() {
        return "LichTruc{" +
                "maLichTruc='" + maLichTruc + '\'' +
                ", ngayTruc=" + ngayTruc +
                ", caTruc='" + caTruc + '\'' +
                ", maNhanVien='" + maNhanVien + '\'' +
                ", maPhong='" + maPhong + '\'' +
                ", maPhanCong='" + maPhanCong + '\'' +
                '}';
    }
}
