package com.httt.quanlybenhvien.feature.phucnguyen;

import com.httt.quanlybenhvien.model.BenhNhan;
import com.httt.quanlybenhvien.model.HoaDon;
import com.httt.quanlybenhvien.model.LichKham;
import com.httt.quanlybenhvien.model.NhanVien;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class PhucNguyenConsoleApp {
    private static final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter VIEW_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final Scanner scanner = new Scanner(System.in);
    private final PhucNguyenService service = new PhucNguyenService();
    private BenhNhan currentBenhNhan;

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = readLine("Chọn chức năng: ");
            try {
                switch (choice) {
                    case "1":
                        dangNhap();
                        break;
                    case "2":
                        dangKy();
                        break;
                    case "3":
                        datLichKham();
                        break;
                    case "4":
                        xemLichKham();
                        break;
                    case "5":
                        thanhToan();
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
        System.out.println("  PHÚC NGUYỄN - QUẢN LÝ BỆNH VIỆN");
        System.out.println("====================================");
        if (currentBenhNhan == null) {
            System.out.println("Trạng thái: Chưa đăng nhập");
        } else {
            System.out.println("Đang đăng nhập: " + currentBenhNhan.getTenBenhNhan()
                    + " (" + currentBenhNhan.getMaBenhNhan() + ")");
        }
        System.out.println("1. Đăng nhập");
        System.out.println("2. Đăng kí");
        System.out.println("3. Đặt lịch khám");
        System.out.println("4. Xem lịch khám");
        System.out.println("5. Thanh toán");
        System.out.println("0. Thoát");
    }

    private void dangNhap() throws Exception {
        System.out.println("\n--- ĐĂNG NHẬP ---");
        String tenDangNhap = readLine("Tên đăng nhập: ");
        String matKhau = readLine("Mật khẩu: ");
        currentBenhNhan = service.dangNhap(tenDangNhap, matKhau);
        System.out.println("Đăng nhập thành công. Xin chào " + currentBenhNhan.getTenBenhNhan() + "!");
    }

    private void dangKy() throws Exception {
        System.out.println("\n--- ĐĂNG KÍ TÀI KHOẢN BỆNH NHÂN ---");
        String hoTen = readLine("Họ tên bệnh nhân: ");
        String tenDangNhap = readLine("Tên đăng nhập: ");
        String matKhau = readLine("Mật khẩu: ");
        currentBenhNhan = service.dangKy(hoTen, tenDangNhap, matKhau);
        System.out.println("Đăng kí thành công. Mã bệnh nhân của bạn là: " + currentBenhNhan.getMaBenhNhan());
    }

    private void datLichKham() throws Exception {
        requireLogin();
        System.out.println("\n--- ĐẶT LỊCH KHÁM ---");

        List<NhanVien> bacSiList = service.layDanhSachBacSi();
        if (bacSiList.isEmpty()) {
            System.out.println("Hiện chưa có bác sĩ trong hệ thống.");
            return;
        }

        System.out.println("Danh sách bác sĩ:");
        for (NhanVien bacSi : bacSiList) {
            System.out.printf("- %s | %s | %s%n", bacSi.getMaNhanVien(), bacSi.getTenNhanVien(), bacSi.getKhoa());
        }

        String maBacSi = readLine("Nhập mã bác sĩ muốn đặt lịch: ");
        LocalDateTime ngayKham = readDateTime("Nhập ngày khám (dd/MM/yyyy HH:mm): ");

        LichKham lichKham = service.datLichKham(currentBenhNhan.getMaBenhNhan(), ngayKham, maBacSi);
        System.out.println("Đặt lịch khám thành công!");
        System.out.println("Mã lịch khám: " + lichKham.getMaLichKham());
        System.out.println("Ngày khám: " + lichKham.getNgayKham().format(VIEW_DATE_FORMAT));
        System.out.println("Hệ thống đã tạo hóa đơn đặt lịch khám để bạn thanh toán.");
    }

    private void xemLichKham() throws Exception {
        requireLogin();
        System.out.println("\n--- XEM LỊCH KHÁM ---");
        List<LichKham> lichKhamList = service.xemLichKham(currentBenhNhan.getMaBenhNhan());
        if (lichKhamList.isEmpty()) {
            System.out.println("Bạn chưa có lịch khám nào.");
            return;
        }

        System.out.printf("%-10s %-18s %-25s %-20s%n", "Mã lịch", "Ngày khám", "Bác sĩ", "Chẩn đoán");
        System.out.println("--------------------------------------------------------------------------");
        for (LichKham lichKham : lichKhamList) {
            System.out.printf("%-10s %-18s %-25s %-20s%n",
                    lichKham.getMaLichKham(),
                    lichKham.getNgayKham().format(VIEW_DATE_FORMAT),
                    service.layTenBacSi(lichKham.getMaBacSi()),
                    lichKham.getChanDoan() == null ? "" : lichKham.getChanDoan());
        }
    }

    private void thanhToan() throws Exception {
        requireLogin();
        System.out.println("\n--- THANH TOÁN HÓA ĐƠN ---");
        List<HoaDon> hoaDonList = service.xemHoaDon(currentBenhNhan.getMaBenhNhan());
        if (hoaDonList.isEmpty()) {
            System.out.println("Bạn chưa có hóa đơn nào.");
            return;
        }

        System.out.printf("%-10s %-35s %-15s%n", "Mã HĐ", "Loại hóa đơn", "Đơn giá");
        System.out.println("----------------------------------------------------------------");
        for (HoaDon hoaDon : hoaDonList) {
            System.out.printf("%-10s %-35s %,15d VND%n",
                    hoaDon.getMaHoaDon(), hoaDon.getLoaiHoaDon(), hoaDon.getDonGia());
        }

        String maHoaDon = readLine("Nhập mã hóa đơn cần thanh toán: ");
        HoaDon hoaDon = service.thanhToan(currentBenhNhan.getMaBenhNhan(), maHoaDon);
        System.out.println("Thanh toán thành công!");
        System.out.println("Biên nhận: " + hoaDon.getMaHoaDon()
                + " | " + hoaDon.getLoaiHoaDon()
                + " | " + String.format("%,d VND", hoaDon.getDonGia()));
    }

    private void requireLogin() {
        if (currentBenhNhan == null) {
            throw new IllegalStateException("Bạn cần đăng nhập hoặc đăng kí trước khi dùng chức năng này.");
        }
    }

    private String readLine(String label) {
        System.out.print(label);
        return scanner.nextLine().trim();
    }

    private LocalDateTime readDateTime(String label) {
        while (true) {
            String value = readLine(label);
            try {
                return LocalDateTime.parse(value, INPUT_DATE_FORMAT);
            } catch (DateTimeParseException ex) {
                System.out.println("Sai định dạng. Ví dụ đúng: 25/06/2026 08:30");
            }
        }
    }
}
