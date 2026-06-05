package model;

import java.math.BigDecimal;

public class TinhLuong {
    private int maBangLuong;
    private int maNhanVien;
    private int thang;
    private int nam;
    private BigDecimal tongLuong;
    private int nguoiTinh;

    public TinhLuong() {
    }

    public TinhLuong(int maNhanVien, int thang, int nam, BigDecimal tongLuong, int nguoiTinh) {
        this.maNhanVien = maNhanVien;
        this.thang = thang;
        this.nam = nam;
        this.tongLuong = tongLuong;
        this.nguoiTinh = nguoiTinh;
    }

    public int getMaBangLuong() {
        return maBangLuong;
    }

    public void setMaBangLuong(int maBangLuong) {
        this.maBangLuong = maBangLuong;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public BigDecimal getTongLuong() {
        return tongLuong;
    }

    public void setTongLuong(BigDecimal tongLuong) {
        this.tongLuong = tongLuong;
    }

    public int getNguoiTinh() {
        return nguoiTinh;
    }

    public void setNguoiTinh(int nguoiTinh) {
        this.nguoiTinh = nguoiTinh;
    }

    @Override
    public String toString() {
        return "TinhLuong{" +
                "maBangLuong=" + maBangLuong +
                ", maNhanVien=" + maNhanVien +
                ", thang=" + thang +
                ", nam=" + nam +
                ", tongLuong=" + tongLuong +
                '}';
    }
}
