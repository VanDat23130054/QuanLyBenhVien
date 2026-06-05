package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HoaDon {
    private int maHoaDon;
    private int maBenhNhan;
    private int nguoiTao;
    private BigDecimal tongTien;
    private LocalDateTime ngayLap;
    private String trangThai;
    private String loaiHoaDon;

    public HoaDon() {
    }

    public HoaDon(int maBenhNhan, int nguoiTao, BigDecimal tongTien, String loaiHoaDon) {
        this.maBenhNhan = maBenhNhan;
        this.nguoiTao = nguoiTao;
        this.tongTien = tongTien;
        this.loaiHoaDon = loaiHoaDon;
        this.ngayLap = LocalDateTime.now();
        this.trangThai = "Chua thanh toan";
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(int maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public int getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(int nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public LocalDateTime getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDateTime ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getLoaiHoaDon() {
        return loaiHoaDon;
    }

    public void setLoaiHoaDon(String loaiHoaDon) {
        this.loaiHoaDon = loaiHoaDon;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon=" + maHoaDon +
                ", maBenhNhan=" + maBenhNhan +
                ", tongTien=" + tongTien +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
