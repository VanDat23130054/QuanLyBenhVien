package model;

import java.time.LocalDateTime;

public class LichKham {
    private int maLichKham;
    private int maBenhNhan;
    private int maBacSi;
    private LocalDateTime ngayKham;
    private String trangThai;

    public LichKham() {
    }

    public LichKham(int maBenhNhan, int maBacSi, LocalDateTime ngayKham) {
        this.maBenhNhan = maBenhNhan;
        this.maBacSi = maBacSi;
        this.ngayKham = ngayKham;
        this.trangThai = "Cho kham";
    }

    public int getMaLichKham() {
        return maLichKham;
    }

    public void setMaLichKham(int maLichKham) {
        this.maLichKham = maLichKham;
    }

    public int getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(int maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public int getMaBacSi() {
        return maBacSi;
    }

    public void setMaBacSi(int maBacSi) {
        this.maBacSi = maBacSi;
    }

    public LocalDateTime getNgayKham() {
        return ngayKham;
    }

    public void setNgayKham(LocalDateTime ngayKham) {
        this.ngayKham = ngayKham;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "LichKham{" +
                "maLichKham=" + maLichKham +
                ", maBenhNhan=" + maBenhNhan +
                ", maBacSi=" + maBacSi +
                ", ngayKham=" + ngayKham +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
