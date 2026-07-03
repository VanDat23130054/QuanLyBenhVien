package com.httt.quanlybenhvien.model;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

public class LichKham {
    private String maLichKham;
    private LocalDateTime ngayKham;
    private String chanDoan;

    private BenhNhan benhNhan;
    private NhanVien bacSi;
    private NhanVien leTan;

     public LichKham(String maLichKham, LocalDateTime ngayKham, String chanDoan, String maBenhNhan, String maLeTan, String maBacSi) {
        this.maLichKham = maLichKham;
        this.ngayKham = ngayKham;
        this.chanDoan = chanDoan;
        this.maBenhNhan = maBenhNhan;
        this.maLeTan = maLeTan;
        this.maBacSi = maBacSi;
    }
    public void setMaLichKham(String maLichKham) {
        this.maLichKham = maLichKham;
    }

    public LocalDateTime getNgayKham() {
        return ngayKham;
    }
    public void setNgayKham(LocalDateTime ngayKham) {
        this.ngayKham = ngayKham;
    }

    public String getChanDoan() {
        return chanDoan;
    }
    public void setChanDoan(String chanDoan) {
        this.chanDoan = chanDoan;
    }

    public BenhNhan getBenhNhan() {
        return benhNhan;
    }
    public void setBenhNhan(BenhNhan benhNhan) {
        this.benhNhan = benhNhan;
    }

    public NhanVien getBacSi() {
        return bacSi;
    }
    public void setBacSi(NhanVien bacSi) {
        this.bacSi = bacSi; }

    public NhanVien getLeTan() {
        return leTan;
    }
    public void setLeTan(NhanVien leTan) {
        this.leTan = leTan;
    }
    @Override
    public String toString() {
        return "LichKham{" +
                "maLichKham='" + maLichKham + '\'' +
                ", ngayKham=" + ngayKham +
                ", chanDoan='" + chanDoan + '\'' +
                ", maBenhNhan='" + maBenhNhan + '\'' +
                ", maLeTan='" + maLeTan + '\'' +
                ", maBacSi='" + maBacSi + '\'' +
                '}';
    }
}
