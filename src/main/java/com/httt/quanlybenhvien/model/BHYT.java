package com.httt.quanlybenhvien.model;

import java.time.LocalDate;

public class BHYT {

    private String maBHYT;
    private String noiDangKy;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;

    private BenhNhan benhNhan;

    public BHYT() {
    }

    public BHYT(String maBHYT, String noiDangKy, LocalDate ngayBatDau, LocalDate ngayKetThuc, BenhNhan benhNhan) {
        this.maBHYT = maBHYT;
        this.noiDangKy = noiDangKy;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.benhNhan = benhNhan;
    }

    public String getMaBHYT() {
        return maBHYT;
    }

    public void setMaBHYT(String maBHYT) {
        this.maBHYT = maBHYT;
    }

    public String getNoiDangKy() {
        return noiDangKy;
    }

    public void setNoiDangKy(String noiDangKy) {
        this.noiDangKy = noiDangKy;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public BenhNhan getBenhNhan() {
        return benhNhan;
    }

    public void setBenhNhan(BenhNhan benhNhan) {
        this.benhNhan = benhNhan;
    }

    @Override
    public String toString() {
        return "BHYT{" +
                "maBHYT='" + maBHYT + '\'' +
                ", noiDangKy='" + noiDangKy + '\'' +
                ", ngayKetThuc=" + ngayKetThuc +
                '}';
    }
}
