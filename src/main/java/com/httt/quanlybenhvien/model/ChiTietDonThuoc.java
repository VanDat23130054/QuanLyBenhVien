package com.httt.quanlybenhvien.model;

public class ChiTietDonThuoc {
    private DonThuoc donThuoc;
    private Thuoc thuoc;
    private int soLuongKeDon;
    
    public ChiTietDonThuoc() {}
    public ChiTietDonThuoc(DonThuoc donThuoc, Thuoc thuoc, int soLuongKeDon) {
        this.donThuoc = donThuoc;
        this.thuoc = thuoc;
        this.soLuongKeDon = soLuongKeDon;
    }

    public DonThuoc getDonThuoc() {
        return donThuoc;
    }

    public void setDonThuoc(DonThuoc donThuoc) {
        this.donThuoc = donThuoc;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    public int getSoLuongKeDon() {
        return soLuongKeDon;
    }

    public void setSoLuongKeDon(int soLuongKeDon) {
        this.soLuongKeDon = soLuongKeDon;
    }

    @Override
    public String toString() {
        return "ChiTietDonThuoc{" +
                "donThuoc=" + (donThuoc != null ? donThuoc.getMaDonThuoc() : null) +
                ", thuoc=" + (thuoc != null ? thuoc.getMaThuoc() : null) +
                ", soLuongKeDon=" + soLuongKeDon +
                '}';
    }
}
