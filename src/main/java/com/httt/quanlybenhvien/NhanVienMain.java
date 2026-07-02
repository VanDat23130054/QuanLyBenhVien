package com.httt.quanlybenhvien;

import com.httt.quanlybenhvien.feature.nhanvienmanagement.NhanVienLoginService;
import com.httt.quanlybenhvien.feature.nhanvienmanagement.NhanVienManagementConsoleApp;
import com.httt.quanlybenhvien.feature.thuocmanagement.MedicineConsoleApp;
import com.httt.quanlybenhvien.model.NhanVien;

import java.util.Scanner;

public class NhanVienMain {
	private static final Scanner scanner = new Scanner(System.in);
	private static final NhanVienLoginService loginService = new NhanVienLoginService();

	public static void main(String[] args) {
		printLoginHeader();
		
		try {
			NhanVien nhanVien = handleLogin();
			if (nhanVien != null) {
				routeToApp(nhanVien);
			}
		} catch (Exception ex) {
			System.out.println("Lỗi: " + ex.getMessage());
		}

		scanner.close();
	}

	private static void printLoginHeader() {
		System.out.println();
		System.out.println("==========================================");
		System.out.println("     HỆ THỐNG QUẢN LÝ BỆNH VIỆN");
		System.out.println("         ĐĂNG NHẬP NHÂN VIÊN");
		System.out.println("==========================================");
		System.out.println();
	}

	/**
	 * Xử lý đăng nhập
	 */
	private static NhanVien handleLogin() {
		String tenDangNhap = readLine("Tên đăng nhập: ");
		String matKhau = readLine("Mật khẩu: ");

		try {
			NhanVien nhanVien = loginService.dangNhap(tenDangNhap, matKhau);
			System.out.println("\n✓ Đăng nhập thành công!");
			System.out.println("Xin chào: " + nhanVien.getTenNhanVien());
			System.out.println("Chức vụ: " + nhanVien.getChucVu());
			System.out.println();
			return nhanVien;
		} catch (Exception ex) {
			System.out.println("\n✗ Đăng nhập thất bại: " + ex.getMessage());
			return null;
		}
	}

	/**
	 * Định tuyến đến ứng dụng phù hợp dựa trên chức vụ
	 */
	private static void routeToApp(NhanVien nhanVien) {
		if (loginService.isDuocSi(nhanVien)) {
			System.out.println(">>> Chuyển hướng đến Quản lý Thuốc...\n");
			new MedicineConsoleApp().run();
		} else if (loginService.isQuanLy(nhanVien)) {
			System.out.println(">>> Chuyển hướng đến Quản lý Nhân viên...\n");
			new NhanVienManagementConsoleApp().run();
		} else {
			System.out.println("\n✗ Lỗi: Chức vụ '" + nhanVien.getChucVu() + "' không được hỗ trợ trong hệ thống này.");
			System.out.println("Chỉ DuocSi (Dược sĩ) và QuanLy (Quản lý) mới có quyền truy cập.");
		}
	}

	private static String readLine(String label) {
		System.out.print(label);
		return scanner.nextLine().trim();
	}
}
