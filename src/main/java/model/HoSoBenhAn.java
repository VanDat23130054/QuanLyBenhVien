package model;

import java.time.LocalDateTime;

public class HoSoBenhAn {
    private int maHoSo;
    private int maBenhNhan;
    private LocalDateTime ngayTao;

    public HoSoBenhAn() {
    }

    public HoSoBenhAn(int maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
        this.ngayTao = LocalDateTime.now();
    }

    public int getMaHoSo() {
        return maHoSo;
    }

    public void setMaHoSo(int maHoSo) {
        this.maHoSo = maHoSo;
    }

    public int getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(int maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    @Override
    public String toString() {
        return "HoSoBenhAn{" +
                "maHoSo=" + maHoSo +
                ", maBenhNhan=" + maBenhNhan +
                ", ngayTao=" + ngayTao +
                '}';
    }
}
