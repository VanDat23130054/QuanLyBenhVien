package model;

public class ChiTietDonThuoc {
    private int maChiTiet;
    private int maDonThuoc;
    private int maThuoc;
    private int soLuong;
    private String huongDan;

    public ChiTietDonThuoc() {
    }

    public ChiTietDonThuoc(int maDonThuoc, int maThuoc, int soLuong, String huongDan) {
        this.maDonThuoc = maDonThuoc;
        this.maThuoc = maThuoc;
        this.soLuong = soLuong;
        this.huongDan = huongDan;
    }

    public int getMaChiTiet() {
        return maChiTiet;
    }

    public void setMaChiTiet(int maChiTiet) {
        this.maChiTiet = maChiTiet;
    }

    public int getMaDonThuoc() {
        return maDonThuoc;
    }

    public void setMaDonThuoc(int maDonThuoc) {
        this.maDonThuoc = maDonThuoc;
    }

    public int getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(int maThuoc) {
        this.maThuoc = maThuoc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getHuongDan() {
        return huongDan;
    }

    public void setHuongDan(String huongDan) {
        this.huongDan = huongDan;
    }

    @Override
    public String toString() {
        return "ChiTietDonThuoc{" +
                "maChiTiet=" + maChiTiet +
                ", maDonThuoc=" + maDonThuoc +
                ", maThuoc=" + maThuoc +
                ", soLuong=" + soLuong +
                '}';
    }
}
