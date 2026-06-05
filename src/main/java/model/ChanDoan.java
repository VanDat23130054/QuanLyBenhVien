package model;

import java.time.LocalDateTime;

public class ChanDoan {
    private int maChanDoan;
    private int maLichKham;
    private int maHoSo;
    private int maBacSi;
    private String ketQua;
    private LocalDateTime ngayKham;

    public ChanDoan() {
    }

    public ChanDoan(int maLichKham, int maHoSo, int maBacSi, String ketQua) {
        this.maLichKham = maLichKham;
        this.maHoSo = maHoSo;
        this.maBacSi = maBacSi;
        this.ketQua = ketQua;
        this.ngayKham = LocalDateTime.now();
    }

    public int getMaChanDoan() {
        return maChanDoan;
    }

    public void setMaChanDoan(int maChanDoan) {
        this.maChanDoan = maChanDoan;
    }

    public int getMaLichKham() {
        return maLichKham;
    }

    public void setMaLichKham(int maLichKham) {
        this.maLichKham = maLichKham;
    }

    public int getMaHoSo() {
        return maHoSo;
    }

    public void setMaHoSo(int maHoSo) {
        this.maHoSo = maHoSo;
    }

    public int getMaBacSi() {
        return maBacSi;
    }

    public void setMaBacSi(int maBacSi) {
        this.maBacSi = maBacSi;
    }

    public String getKetQua() {
        return ketQua;
    }

    public void setKetQua(String ketQua) {
        this.ketQua = ketQua;
    }

    public LocalDateTime getNgayKham() {
        return ngayKham;
    }

    public void setNgayKham(LocalDateTime ngayKham) {
        this.ngayKham = ngayKham;
    }

    @Override
    public String toString() {
        return "ChanDoan{" +
                "maChanDoan=" + maChanDoan +
                ", maLichKham=" + maLichKham +
                ", ketQua='" + ketQua + '\'' +
                '}';
    }
}
