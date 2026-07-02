package com.httt.quanlybenhvien.feature.nhanvienmanagement;

import com.httt.quanlybenhvien.model.NhanVien;
import com.httt.quanlybenhvien.model.TaiKhoan;
import com.httt.quanlybenhvien.repository.impl.NhanVienRepository;
import com.httt.quanlybenhvien.repository.impl.TaiKhoanRepository;

import java.sql.SQLException;
import java.text.Normalizer;
import java.util.List;
import java.util.Locale;

/**
 * Service xử lý đăng nhập cho nhân viên
 */
public class NhanVienLoginService {
    private final NhanVienRepository nhanVienRepository;
    private final TaiKhoanRepository taiKhoanRepository;

    public NhanVienLoginService() {
        this.nhanVienRepository = new NhanVienRepository();
        this.taiKhoanRepository = new TaiKhoanRepository();
    }

    /**
     * Đăng nhập nhân viên
     * @return NhanVien object if login successful
     * @throws Exception if login fails
     */
    public NhanVien dangNhap(String tenDangNhap, String matKhau) throws Exception {
        validateNotBlank(tenDangNhap, "Tên đăng nhập");
        validateNotBlank(matKhau, "Mật khẩu");

        // Kiểm tra tài khoản tồn tại
        TaiKhoan taiKhoan = taiKhoanRepository.findById(tenDangNhap)
                .orElseThrow(() -> new IllegalArgumentException("Tên đăng nhập không tồn tại."));

        // Kiểm tra mật khẩu
        if (!taiKhoan.getMatKhau().equals(matKhau)) {
            throw new IllegalArgumentException("Mật khẩu không đúng.");
        }

        // Kiểm tra loại tài khoản
        String loaiTaiKhoan = taiKhoan.getLoaiTaiKhoan();
        if ("Benh Nhan".equalsIgnoreCase(loaiTaiKhoan)) {
            throw new IllegalArgumentException("Tài khoản này là tài khoản bệnh nhân, không phải nhân viên.");
        }

        // Tìm nhân viên tương ứng
        NhanVien nhanVien = findNhanVienByTenDangNhap(tenDangNhap)
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy thông tin nhân viên"));

        return nhanVien;
    }

    /**
     * Tìm nhân viên theo tên đăng nhập
     */
    private java.util.Optional<NhanVien> findNhanVienByTenDangNhap(String tenDangNhap) throws SQLException {
        List<NhanVien> allNhanVien = nhanVienRepository.findAll();
        return allNhanVien.stream()
                .filter(nv -> tenDangNhap.equals(nv.getTenDangNhap()))
                .findFirst();
    }

    /**
     * Kiểm tra xem nhân viên có phải là DuocSi không
     */
    public boolean isDuocSi(NhanVien nhanVien) {
        return nhanVien.getChucVu() != null && 
               normalize(nhanVien.getChucVu()).contains("duoc si");
    }

    /**
     * Kiểm tra xem nhân viên có phải là QuanLy không
     */
    public boolean isQuanLy(NhanVien nhanVien) {
        return nhanVien.getChucVu() != null && 
               normalize(nhanVien.getChucVu()).contains("quan ly");
    }

    private void validateNotBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " không được để trống.");
        }
    }

    private String normalize(String value) {
        if (value == null) return "";
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replace("Đ", "D")
                .replace("đ", "d");
        return normalized.toLowerCase(Locale.ROOT);
    }
}
