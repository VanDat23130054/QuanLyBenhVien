package com.httt.quanlybenhvien.service;

import com.httt.quanlybenhvien.model.LichKham;
import java.util.List;

public class LichKhamService {

    private List<LichKham> dsLichKham;

    public LichKhamService(List<LichKham> dsLichKham) {
        this.dsLichKham = dsLichKham;
    }

    public boolean chanDoan(String maLichKham, String chanDoan) {

        for (LichKham lk : dsLichKham) {
            if (lk.getMaLichKham().equals(maLichKham)) {

                lk.setChanDoan(chanDoan);

                System.out.println("✔ Đã cập nhật chẩn đoán: " + chanDoan);
                return true;
            }
        }

        System.out.println("❌ Không tìm thấy lịch khám: " + maLichKham);
        return false;
    }
}
