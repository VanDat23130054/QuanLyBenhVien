package com.httt.quanlybenhvien.model;

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

    public void dangKyKham() {
        System.out.println("Đăng ký khám cho bệnh nhân");
    }

    public void capNhatThongTin() {
        System.out.println("Cập nhật thông tin bệnh nhân");
    }
}
