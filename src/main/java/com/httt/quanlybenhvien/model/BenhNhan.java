package com.httt.quanlybenhvien.model;

import java.util.*;

public class BenhNhan {
    private String maBenhNhan;
    private String hoTen;
    private Date ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String soDienThoai;

    private List<LichKham> lichKhams = new ArrayList<>();
    private List<BenhAn> benhAns = new ArrayList<>();
    private BHYT bhyt;

     public BenhNhan() {
    }
    public BenhNhan(String maBenhNhan, String hoTen, Date ngaySinh,
                     String gioiTinh, String diaChi, String soDienThoai) {
        this.maBenhNhan = maBenhNhan;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }
    
    public String getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(String maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public List<LichKham> getLichKhams() {
        return lichKhams;
    }

    public void setLichKhams(List<LichKham> lichKhams) {
        this.lichKhams = lichKhams;
    }

    public List<BenhAn> getBenhAns() {
        return benhAns;
    }

    public void setBenhAns(List<BenhAn> benhAns) {
        this.benhAns = benhAns;
    }

    public BHYT getBHYT() {
        return bhyt;
    }

    public void setBHYT(BHYT bhyt) {
        this.bhyt = bhyt;
    }

    public void dangKyKham() {
        System.out.println("Đăng ký khám");
    }

    public void capNhatThongTin() {
        System.out.println("Cập nhật thông tin");
    }
}
