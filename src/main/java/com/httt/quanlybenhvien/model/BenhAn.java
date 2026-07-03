package com.httt.quanlybenhvien.model;

import java.time.LocalDateTime;

public class BenhAn {

    private String maBenhAn;
    private LocalDateTime ngayTao;
    private String chanDoan;
    private String ghiChu;

    private BenhNhan benhNhan;
    private NhanVien bacSi;

    public BenhAn() {
    }

    public BenhAn(String maBenhAn, LocalDateTime ngayTao, String chanDoan, String ghiChu,
                  BenhNhan benhNhan, NhanVien bacSi) {
        this.maBenhAn = maBenhAn;
        this.ngayTao = ngayTao;
        this.chanDoan = chanDoan;
        this.ghiChu = ghiChu;
        this.benhNhan = benhNhan;
        this.bacSi = bacSi;
    }

    public String getMaBenhAn() {
        return maBenhAn;
    }

    public void setMaBenhAn(String maBenhAn) {
        this.maBenhAn = maBenhAn;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getChanDoan() {
        return chanDoan;
    }

    public void setChanDoan(String chanDoan) {
        this.chanDoan = chanDoan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
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

    @Override
    public String toString() {
        return "BenhAn{" +
                "maBenhAn='" + maBenhAn + '\'' +
                ", chanDoan='" + chanDoan + '\'' +
                ", benhNhan=" + (benhNhan != null ? benhNhan.getMaBenhNhan() : null) +
                ", bacSi=" + (bacSi != null ? bacSi.getMaNhanVien() : null) +
                '}';
    }
}
