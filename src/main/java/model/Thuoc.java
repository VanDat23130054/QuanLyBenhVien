package model;

import java.math.BigDecimal;

public class Thuoc {
    private int maThuoc;
    private String tenThuoc;
    private String donVi;
    private int soLuongTon;
    private BigDecimal donGia;

    public Thuoc() {
    }

    public Thuoc(String tenThuoc, String donVi, int soLuongTon, BigDecimal donGia) {
        this.tenThuoc = tenThuoc;
        this.donVi = donVi;
        this.soLuongTon = soLuongTon;
        this.donGia = donGia;
    }

    public int getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(int maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    @Override
    public String toString() {
        return "Thuoc{" +
                "maThuoc=" + maThuoc +
                ", tenThuoc='" + tenThuoc + '\'' +
                ", donVi='" + donVi + '\'' +
                ", soLuongTon=" + soLuongTon +
                ", donGia=" + donGia +
                '}';
    }
}
