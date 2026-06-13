package com.httt.quanlybenhvien.model;

import java.time.LocalDateTime;

public class PhanCong {
    private String maPhanCong;
    private LocalDateTime ngayPhanCong;
    private String maQuanLy; // references NhanVien.maNhanVien

    public PhanCong() {}

    public PhanCong(String maPhanCong, LocalDateTime ngayPhanCong, String maQuanLy) {
        this.maPhanCong = maPhanCong;
        this.ngayPhanCong = ngayPhanCong;
        this.maQuanLy = maQuanLy;
    }

    public String getMaPhanCong() {
        return maPhanCong;
    }

    public void setMaPhanCong(String maPhanCong) {
        this.maPhanCong = maPhanCong;
    }

    public LocalDateTime getNgayPhanCong() {
        return ngayPhanCong;
    }

    public void setNgayPhanCong(LocalDateTime ngayPhanCong) {
        this.ngayPhanCong = ngayPhanCong;
    }

    public String getMaQuanLy() {
        return maQuanLy;
    }

    public void setMaQuanLy(String maQuanLy) {
        this.maQuanLy = maQuanLy;
    }

    @Override
    public String toString() {
        return "PhanCong{" +
                "maPhanCong='" + maPhanCong + '\'' +
                ", ngayPhanCong=" + ngayPhanCong +
                ", maQuanLy='" + maQuanLy + '\'' +
                '}';
    }
}
