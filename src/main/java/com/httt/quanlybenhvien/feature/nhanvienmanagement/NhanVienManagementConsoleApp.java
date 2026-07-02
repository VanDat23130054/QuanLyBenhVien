package com.httt.quanlybenhvien.feature.nhanvienmanagement;

import com.httt.quanlybenhvien.model.NhanVien;

import java.util.List;
import java.util.Scanner;

public class NhanVienManagementConsoleApp {
    private final Scanner scanner = new Scanner(System.in);
    private final NhanVienManagementService service = new NhanVienManagementService();

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = readLine("Chọn chức năng: ");
            try {
                switch (choice) {
                    case "1":
                        themNhanVien();
                        break;
                    case "2":
                        doiChucVu();
                        break;
                    case "3":
                        suaMucLuong();
                        break;
                    case "4":
                        taoTaiKhoanNhanVien();
                        break;
                    case "5":
                        xoaTaiKhoanNhanVien();
                        break;
                    case "6":
                        xemDanhSachNhanVien();
                        break;
                    case "7":
                        xemThongTinNhanVienTheoMa();
                        break;
                    case "0":
                        running = false;
                        System.out.println("Đã thoát chương trình.");
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ.");
                }
            } catch (Exception ex) {
                System.out.println("Lỗi: " + ex.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("====================================");
        System.out.println("   QUẢN LÝ NHÂN VIÊN - HỆ THỐNG");
        System.out.println("====================================");
        System.out.println("1. Thêm nhân viên");
        System.out.println("2. Đổi chức vụ");
        System.out.println("3. Sửa mức lương");
        System.out.println("4. Tạo tài khoản nhân viên");
        System.out.println("5. Xóa tài khoản nhân viên");
        System.out.println("6. Xem danh sách nhân viên");
        System.out.println("7. Xem thông tin nhân viên theo mã");
        System.out.println("0. Thoát");
        System.out.println("====================================");
    }

    /**
     * Thêm nhân viên mới
     */
    private void themNhanVien() throws Exception {
        System.out.println("\n--- THÊM NHÂN VIÊN MỚI ---");
        String maNhanVien = readLine("Mã nhân viên: ");
        String tenNhanVien = readLine("Tên nhân viên: ");
        String khoa = readLine("Khoa: ");
        String chucVu = readLine("Chức vụ: ");
        Long mucLuong = readLong("Mức lương (0 nếu không có): ");
        String tenDangNhap = readLine("Tên đăng nhập (bỏ trống nếu không tạo ngay): ");

        NhanVien nhanVien = service.themNhanVien(
                maNhanVien,
                tenNhanVien,
                khoa,
                chucVu,
                mucLuong > 0 ? mucLuong : null,
                tenDangNhap.isEmpty() ? null : tenDangNhap
        );

        System.out.println("Thêm nhân viên thành công!");
        System.out.printf("Mã nhân viên: %s%n", nhanVien.getMaNhanVien());
        System.out.printf("Tên nhân viên: %s%n", nhanVien.getTenNhanVien());
        System.out.printf("Chức vụ: %s%n", nhanVien.getChucVu());
        if (nhanVien.getMucLuong() != null) {
            System.out.printf("Mức lương: %,d VND%n", nhanVien.getMucLuong());
        }
    }

    /**
     * Đổi chức vụ nhân viên
     */
    private void doiChucVu() throws Exception {
        System.out.println("\n--- ĐỔI CHỨC VỤ NHÂN VIÊN ---");
        String maNhanVien = readLine("Nhập mã nhân viên: ");
        String chucVuMoi = readLine("Nhập chức vụ mới: ");

        NhanVien nhanVien = service.doiChucVu(maNhanVien, chucVuMoi);

        System.out.println("Đổi chức vụ thành công!");
        System.out.printf("Nhân viên: %s%n", nhanVien.getTenNhanVien());
        System.out.printf("Chức vụ mới: %s%n", nhanVien.getChucVu());
    }

    /**
     * Sửa mức lương nhân viên
     */
    private void suaMucLuong() throws Exception {
        System.out.println("\n--- SỬA MỨC LƯƠNG NHÂN VIÊN ---");
        String maNhanVien = readLine("Nhập mã nhân viên: ");
        Long mucLuongMoi = readLong("Nhập mức lương mới (VND): ");

        NhanVien nhanVien = service.suaMucLuong(maNhanVien, mucLuongMoi);

        System.out.println("Sửa mức lương thành công!");
        System.out.printf("Nhân viên: %s%n", nhanVien.getTenNhanVien());
        System.out.printf("Mức lương mới: %,d VND%n", nhanVien.getMucLuong());
    }

    /**
     * Tạo tài khoản cho nhân viên
     */
    private void taoTaiKhoanNhanVien() throws Exception {
        System.out.println("\n--- TẠO TÀI KHOẢN NHÂN VIÊN ---");
        String maNhanVien = readLine("Nhập mã nhân viên: ");
        String tenDangNhap = readLine("Nhập tên đăng nhập: ");
        String matKhau = readLine("Nhập mật khẩu: ");

        service.taoTaiKhoanNhanVien(maNhanVien, tenDangNhap, matKhau);

        System.out.println("Tạo tài khoản thành công!");
        System.out.printf("Tên đăng nhập: %s%n", tenDangNhap);
        System.out.printf("Loại tài khoản: Nhân viên%n");
    }

    /**
     * Xóa tài khoản nhân viên
     */
    private void xoaTaiKhoanNhanVien() throws Exception {
        System.out.println("\n--- XÓA TÀI KHOẢN NHÂN VIÊN ---");
        String tenDangNhap = readLine("Nhập tên đăng nhập cần xóa: ");

        String confirm = readLine("Bạn chắc chắn muốn xóa tài khoản '" + tenDangNhap + "'? (Y/N): ");
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Hủy bỏ xóa tài khoản.");
            return;
        }

        service.xoaTaiKhoanNhanVien(tenDangNhap);

        System.out.println("Xóa tài khoản thành công!");
    }

    /**
     * Xem danh sách tất cả nhân viên
     */
    private void xemDanhSachNhanVien() throws Exception {
        System.out.println("\n--- DANH SÁCH NHÂN VIÊN ---");
        List<NhanVien> nhanVienList = service.layDanhSachNhanVien();

        if (nhanVienList.isEmpty()) {
            System.out.println("Hiện chưa có nhân viên trong hệ thống.");
            return;
        }

        System.out.printf("%-12s %-25s %-15s %-20s %-20s%n", "Mã NV", "Tên nhân viên", "Khoa", "Chức vụ", "Mức lương");
        System.out.println("---------------------------------------------------------------------------------------------------");

        for (NhanVien nv : nhanVienList) {
            String mucLuongStr = nv.getMucLuong() != null ? String.format("%,d", nv.getMucLuong()) : "N/A";
            System.out.printf("%-12s %-25s %-15s %-20s %-20s%n",
                    nv.getMaNhanVien(),
                    nv.getTenNhanVien(),
                    nv.getKhoa(),
                    nv.getChucVu(),
                    mucLuongStr);
        }
    }

    /**
     * Xem thông tin chi tiết nhân viên theo mã
     */
    private void xemThongTinNhanVienTheoMa() throws Exception {
        System.out.println("\n--- THÔNG TIN NHÂN VIÊN ---");
        String maNhanVien = readLine("Nhập mã nhân viên: ");

        NhanVien nv = service.layNhanVienTheoMa(maNhanVien);

        System.out.println("Thông tin nhân viên:");
        System.out.printf("Mã nhân viên: %s%n", nv.getMaNhanVien());
        System.out.printf("Tên nhân viên: %s%n", nv.getTenNhanVien());
        System.out.printf("Khoa: %s%n", nv.getKhoa());
        System.out.printf("Chức vụ: %s%n", nv.getChucVu());
        if (nv.getMucLuong() != null) {
            System.out.printf("Mức lương: %,d VND%n", nv.getMucLuong());
        } else {
            System.out.println("Mức lương: Chưa được cấp");
        }
        if (nv.getTenDangNhap() != null && !nv.getTenDangNhap().isEmpty()) {
            System.out.printf("Tên đăng nhập: %s%n", nv.getTenDangNhap());
        } else {
            System.out.println("Trạng thái tài khoản: Chưa tạo");
        }
    }

    private String readLine(String label) {
        System.out.print(label);
        return scanner.nextLine().trim();
    }

    private Long readLong(String label) {
        while (true) {
            String value = readLine(label);
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException ex) {
                System.out.println("Vui lòng nhập một số hợp lệ.");
            }
        }
    }
}
