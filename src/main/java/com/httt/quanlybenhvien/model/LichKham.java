package com.httt.quanlybenhvien.model;

import java.time.LocalDateTime;

public class LichKham {

    private String maLichKham;
    private LocalDateTime ngayKham;
    private String chanDoan;

    private BenhNhan benhNhan;
    private NhanVien bacSi;
    private NhanVien leTan;

    public LichKham() {
    }

    public LichKham(String maLichKham, LocalDateTime ngayKham, String chanDoan,
                    BenhNhan benhNhan, NhanVien bacSi, NhanVien leTan) {
        this.maLichKham = maLichKham;
        this.ngayKham = ngayKham;
        this.chanDoan = chanDoan;
        this.benhNhan = benhNhan;
        this.bacSi = bacSi;
        this.leTan = leTan;
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
        this.bacSi = bacSi;
    }

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
                ", benhNhan=" + (benhNhan != null ? benhNhan.getMaBenhNhan() : null) +
                ", bacSi=" + (bacSi != null ? bacSi.getMaNhanVien() : null) +
                ", leTan=" + (leTan != null ? leTan.getMaNhanVien() : null) +
                '}';
    }
}
