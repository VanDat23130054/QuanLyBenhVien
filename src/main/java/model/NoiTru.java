package model;

import java.time.LocalDateTime;

public class NoiTru {
    private int maNoiTru;
    private int maBenhNhan;
    private String phongGiuong;
    private LocalDateTime ngayNhapVien;
    private LocalDateTime ngayXuatVien;
    private String trangThai;

    public NoiTru() {
    }

    public NoiTru(int maBenhNhan, String phongGiuong) {
        this.maBenhNhan = maBenhNhan;
        this.phongGiuong = phongGiuong;
        this.ngayNhapVien = LocalDateTime.now();
        this.trangThai = "Dang nam vien";
    }

    public int getMaNoiTru() {
        return maNoiTru;
    }

    public void setMaNoiTru(int maNoiTru) {
        this.maNoiTru = maNoiTru;
    }

    public int getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(int maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public String getPhongGiuong() {
        return phongGiuong;
    }

    public void setPhongGiuong(String phongGiuong) {
        this.phongGiuong = phongGiuong;
    }

    public LocalDateTime getNgayNhapVien() {
        return ngayNhapVien;
    }

    public void setNgayNhapVien(LocalDateTime ngayNhapVien) {
        this.ngayNhapVien = ngayNhapVien;
    }

    public LocalDateTime getNgayXuatVien() {
        return ngayXuatVien;
    }

    public void setNgayXuatVien(LocalDateTime ngayXuatVien) {
        this.ngayXuatVien = ngayXuatVien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "NoiTru{" +
                "maNoiTru=" + maNoiTru +
                ", maBenhNhan=" + maBenhNhan +
                ", phongGiuong='" + phongGiuong + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
