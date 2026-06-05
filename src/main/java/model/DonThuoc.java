package model;

import java.time.LocalDateTime;

public class DonThuoc {
    private int maDonThuoc;
    private int maChanDoan;
    private int maBacSi;
    private LocalDateTime ngayKe;
    private String trangThai;

    public DonThuoc() {
    }

    public DonThuoc(int maChanDoan, int maBacSi) {
        this.maChanDoan = maChanDoan;
        this.maBacSi = maBacSi;
        this.ngayKe = LocalDateTime.now();
        this.trangThai = "Chua cap phat";
    }

    public int getMaDonThuoc() {
        return maDonThuoc;
    }

    public void setMaDonThuoc(int maDonThuoc) {
        this.maDonThuoc = maDonThuoc;
    }

    public int getMaChanDoan() {
        return maChanDoan;
    }

    public void setMaChanDoan(int maChanDoan) {
        this.maChanDoan = maChanDoan;
    }

    public int getMaBacSi() {
        return maBacSi;
    }

    public void setMaBacSi(int maBacSi) {
        this.maBacSi = maBacSi;
    }

    public LocalDateTime getNgayKe() {
        return ngayKe;
    }

    public void setNgayKe(LocalDateTime ngayKe) {
        this.ngayKe = ngayKe;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "DonThuoc{" +
                "maDonThuoc=" + maDonThuoc +
                ", maChanDoan=" + maChanDoan +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
