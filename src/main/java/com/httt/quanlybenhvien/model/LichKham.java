package com.httt.quanlybenhvien.model;

import java.time.LocalDateTime;

public class LichKham {
    private String maLichKham;
    private LocalDateTime ngayKham;
    private String chanDoan;
    private String maBenhNhan;
    private String maLeTan;
    private String maBacSi;

    public LichKham() {}

    public LichKham(String maLichKham, LocalDateTime ngayKham, String chanDoan, String maBenhNhan, String maLeTan, String maBacSi) {
        this.maLichKham = maLichKham;
        this.ngayKham = ngayKham;
        this.chanDoan = chanDoan;
        this.maBenhNhan = maBenhNhan;
        this.maLeTan = maLeTan;
        this.maBacSi = maBacSi;
    }

    public String getMaLichKham() {
        return maLichKham;
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

    public String getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(String maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public String getMaLeTan() {
        return maLeTan;
    }

    public void setMaLeTan(String maLeTan) {
        this.maLeTan = maLeTan;
    }

    public String getMaBacSi() {
        return maBacSi;
    }

    public void setMaBacSi(String maBacSi) {
        this.maBacSi = maBacSi;
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
