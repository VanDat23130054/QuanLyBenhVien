package model;

import java.time.LocalDate;

public class LichTruc {
    private int maLichTruc;
    private int maNhanVien;
    private LocalDate ngayTruc;
    private String caTruc;
    private int nguoiTao;

    public LichTruc() {
    }

    public LichTruc(int maNhanVien, LocalDate ngayTruc, String caTruc, int nguoiTao) {
        this.maNhanVien = maNhanVien;
        this.ngayTruc = ngayTruc;
        this.caTruc = caTruc;
        this.nguoiTao = nguoiTao;
    }

    public int getMaLichTruc() {
        return maLichTruc;
    }

    public void setMaLichTruc(int maLichTruc) {
        this.maLichTruc = maLichTruc;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public LocalDate getNgayTruc() {
        return ngayTruc;
    }

    public void setNgayTruc(LocalDate ngayTruc) {
        this.ngayTruc = ngayTruc;
    }

    public String getCaTruc() {
        return caTruc;
    }

    public void setCaTruc(String caTruc) {
        this.caTruc = caTruc;
    }

    public int getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(int nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    @Override
    public String toString() {
        return "LichTruc{" +
                "maLichTruc=" + maLichTruc +
                ", maNhanVien=" + maNhanVien +
                ", ngayTruc=" + ngayTruc +
                ", caTruc='" + caTruc + '\'' +
                '}';
    }
}
