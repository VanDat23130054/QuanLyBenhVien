package model;

import java.time.LocalDate;

public class ChamCong {
    private int maChamCong;
    private int maNhanVien;
    private LocalDate ngayLam;
    private String trangThai;

    public ChamCong() {
    }

    public ChamCong(int maNhanVien, LocalDate ngayLam, String trangThai) {
        this.maNhanVien = maNhanVien;
        this.ngayLam = ngayLam;
        this.trangThai = trangThai;
    }

    public int getMaChamCong() {
        return maChamCong;
    }

    public void setMaChamCong(int maChamCong) {
        this.maChamCong = maChamCong;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public LocalDate getNgayLam() {
        return ngayLam;
    }

    public void setNgayLam(LocalDate ngayLam) {
        this.ngayLam = ngayLam;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "ChamCong{" +
                "maChamCong=" + maChamCong +
                ", maNhanVien=" + maNhanVien +
                ", ngayLam=" + ngayLam +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
