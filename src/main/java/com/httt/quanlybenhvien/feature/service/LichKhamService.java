package com.httt.quanlybenhvien.service;

import com.httt.quanlybenhvien.model.LichKham;
import java.util.List;

public class LichKhamService {

    private final LichKhamRepository repo = new LichKhamRepository();

    public void chanDoan(String maLichKham, String chanDoan) {

        try {
            Optional<LichKham> lich = repo.findById(maLichKham);

            if (lich.isEmpty()) {
                System.out.println("❌ Không tìm thấy lịch khám");
                return;
            }

            boolean ok = repo.updateChanDoan(maLichKham, chanDoan);

            if (ok) {
                System.out.println("✔ Chẩn đoán thành công: " + chanDoan);
            } else {
                System.out.println("❌ Cập nhật thất bại");
            }

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}
