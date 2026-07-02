package com.httt.quanlybenhvien.feature.nhanvienmanagement;

import com.httt.quanlybenhvien.model.NhanVien;
import com.httt.quanlybenhvien.model.TaiKhoan;
import com.httt.quanlybenhvien.repository.impl.NhanVienRepository;
import com.httt.quanlybenhvien.repository.impl.TaiKhoanRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Service xử lý quản lý nhân viên:
 * Thêm nhân viên, Đổi chức vụ, Sửa mức lương, Tạo tài khoản nhân viên, Xóa tài khoản nhân viên
 */
public class NhanVienManagementService {
    private static final String LOAI_TAI_KHOAN_NHAN_VIEN = "Nhan Vien";

    private final NhanVienRepository nhanVienRepository;
    private final TaiKhoanRepository taiKhoanRepository;

    public NhanVienManagementService() {
        this.nhanVienRepository = new NhanVienRepository();
        this.taiKhoanRepository = new TaiKhoanRepository();
    }

    /**
     * Thêm nhân viên mới vào hệ thống
     */
    public NhanVien themNhanVien(String maNhanVien, String tenNhanVien, String khoa, String chucVu, Long mucLuong, String tenDangNhap) throws Exception {
        validateNotBlank(maNhanVien, "Mã nhân viên");
        validateNotBlank(tenNhanVien, "Tên nhân viên");
        validateNotBlank(khoa, "Khoa");
        validateNotBlank(chucVu, "Chức vụ");

        // Kiểm tra xem mã nhân viên đã tồn tại chưa
        if (nhanVienRepository.findById(maNhanVien).isPresent()) {
            throw new IllegalArgumentException("Mã nhân viên " + maNhanVien + " đã tồn tại.");
        }

        NhanVien nhanVien = new NhanVien(maNhanVien, tenNhanVien, khoa, chucVu, mucLuong, tenDangNhap);
        return nhanVienRepository.save(nhanVien);
    }

    /**
     * Đổi chức vụ của nhân viên
     */
    public NhanVien doiChucVu(String maNhanVien, String chucVuMoi) throws Exception {
        validateNotBlank(maNhanVien, "Mã nhân viên");
        validateNotBlank(chucVuMoi, "Chức vụ mới");

        NhanVien nhanVien = nhanVienRepository.findById(maNhanVien)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên có mã: " + maNhanVien));

        nhanVien.setChucVu(chucVuMoi);
        return nhanVienRepository.update(nhanVien);
    }

    /**
     * Sửa mức lương của nhân viên
     */
    public NhanVien suaMucLuong(String maNhanVien, Long mucLuongMoi) throws Exception {
        validateNotBlank(maNhanVien, "Mã nhân viên");
        
        if (mucLuongMoi != null && mucLuongMoi < 0) {
            throw new IllegalArgumentException("Mức lương không được âm.");
        }

        NhanVien nhanVien = nhanVienRepository.findById(maNhanVien)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên có mã: " + maNhanVien));

        nhanVien.setMucLuong(mucLuongMoi);
        return nhanVienRepository.update(nhanVien);
    }

    /**
     * Tạo tài khoản cho nhân viên
     */
    public void taoTaiKhoanNhanVien(String maNhanVien, String tenDangNhap, String matKhau) throws Exception {
        validateNotBlank(maNhanVien, "Mã nhân viên");
        validateNotBlank(tenDangNhap, "Tên đăng nhập");
        validateNotBlank(matKhau, "Mật khẩu");

        // Kiểm tra xem nhân viên có tồn tại không
        NhanVien nhanVien = nhanVienRepository.findById(maNhanVien)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên có mã: " + maNhanVien));

        // Kiểm tra xem tên đăng nhập đã tồn tại chưa
        if (taiKhoanRepository.existsById(tenDangNhap)) {
            throw new IllegalArgumentException("Tên đăng nhập " + tenDangNhap + " đã tồn tại.");
        }

        // Tạo tài khoản
        TaiKhoan taiKhoan = new TaiKhoan(tenDangNhap, matKhau, LOAI_TAI_KHOAN_NHAN_VIEN);
        taiKhoanRepository.save(taiKhoan);

        // Cập nhật tên đăng nhập cho nhân viên
        nhanVien.setTenDangNhap(tenDangNhap);
        nhanVienRepository.update(nhanVien);
    }

    /**
     * Xóa tài khoản nhân viên
     */
    public void xoaTaiKhoanNhanVien(String tenDangNhap) throws Exception {
        validateNotBlank(tenDangNhap, "Tên đăng nhập");

        // Kiểm tra xem tài khoản có tồn tại không
        TaiKhoan taiKhoan = taiKhoanRepository.findById(tenDangNhap)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản: " + tenDangNhap));

        // Kiểm tra xem đây có phải tài khoản nhân viên không
        if (!LOAI_TAI_KHOAN_NHAN_VIEN.equalsIgnoreCase(taiKhoan.getLoaiTaiKhoan())) {
            throw new IllegalArgumentException("Chỉ có thể xóa tài khoản nhân viên.");
        }

        // Xóa tài khoản
        taiKhoanRepository.deleteById(tenDangNhap);
    }

    /**
     * Lấy danh sách tất cả nhân viên
     */
    public List<NhanVien> layDanhSachNhanVien() throws SQLException {
        return nhanVienRepository.findAll();
    }

    /**
     * Tìm nhân viên theo mã
     */
    public NhanVien layNhanVienTheoMa(String maNhanVien) throws Exception {
        validateNotBlank(maNhanVien, "Mã nhân viên");
        return nhanVienRepository.findById(maNhanVien)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên có mã: " + maNhanVien));
    }

    /**
     * Kiểm tra tên đăng nhập tồn tại hay không
     */
    public boolean kiemTraTenDangNhapTonTai(String tenDangNhap) throws SQLException {
        return taiKhoanRepository.existsById(tenDangNhap);
    }

    private void validateNotBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " không được để trống.");
        }
    }
}
