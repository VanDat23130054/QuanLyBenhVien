package com.httt.quanlybenhvien.feature.phucnguyen;

import com.httt.quanlybenhvien.model.BenhNhan;
import com.httt.quanlybenhvien.model.HoaDon;
import com.httt.quanlybenhvien.model.LichKham;
import com.httt.quanlybenhvien.model.NhanVien;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class PhucNguyenConsoleApp {
    private static final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter VIEW_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter PAYMENT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final Path SESSION_FILE = Paths.get(".phuc-nguyen-session");

    private final Scanner scanner = new Scanner(System.in);
    private final PhucNguyenService service = new PhucNguyenService();
    private BenhNhan currentBenhNhan;

    public void run() {
        restoreSession();
        boolean running = true;
        while (running) {
            printMenu();
            String choice = readLine("Chọn chức năng: ");
            try {
                switch (choice) {
                    case "1":
                        if (currentBenhNhan == null) {
                            dangNhap();
                        } else {
                            dangXuat();
                        }
                        break;
                    case "2":
                        if (currentBenhNhan == null) {
                            dangKy();
                        } else {
                            System.out.println("Lựa chọn không hợp lệ.");
                        }
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
        if (currentBenhNhan == null) {
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng kí");
        } else {
            System.out.println("1. Đăng xuất");
        }
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
        saveSession(currentBenhNhan);
        System.out.println("Đăng nhập thành công. Xin chào " + currentBenhNhan.getTenBenhNhan() + "!");
    }

    private void dangKy() throws Exception {
        System.out.println("\n--- ĐĂNG KÍ TÀI KHOẢN BỆNH NHÂN ---");
        String hoTen = readLine("Họ tên bệnh nhân: ");
        String tenDangNhap = readLine("Tên đăng nhập: ");
        String matKhau = readLine("Mật khẩu: ");
        currentBenhNhan = service.dangKy(hoTen, tenDangNhap, matKhau);
        saveSession(currentBenhNhan);
        System.out.println("Đăng kí thành công. Mã bệnh nhân của bạn là: " + currentBenhNhan.getMaBenhNhan());
    }

    private void dangXuat() throws Exception {
        System.out.println("\n--- ĐĂNG XUẤT ---");
        System.out.println("Đã đăng xuất tài khoản: " + currentBenhNhan.getTenBenhNhan());
        currentBenhNhan = null;
        clearSession();
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
            System.out.println("Bạn không có hóa đơn nào cần thanh toán.");
            return;
        }

        System.out.printf("%-10s %-35s %-15s %-20s%n", "Mã HĐ", "Loại hóa đơn", "Đơn giá", "Trạng thái");
        System.out.println("--------------------------------------------------------------------------------");
        for (HoaDon hoaDon : hoaDonList) {
            System.out.printf("%-10s %-35s %,15d VND %-20s%n",
                    hoaDon.getMaHoaDon(),
                    hoaDon.getLoaiHoaDon(),
                    hoaDon.getDonGia(),
                    hoaDon.getTrangThaiThanhToan());
        }

        String maHoaDon = readLine("Nhập mã hóa đơn cần thanh toán: ");
        HoaDon hoaDonCanThanhToan = service.layHoaDonCanThanhToan(currentBenhNhan.getMaBenhNhan(), maHoaDon);

        System.out.println("\nChi tiết hóa đơn:");
        System.out.println("Mã hóa đơn: " + hoaDonCanThanhToan.getMaHoaDon());
        System.out.println("Loại hóa đơn: " + hoaDonCanThanhToan.getLoaiHoaDon());
        System.out.println("Số tiền: " + String.format("%,d VND", hoaDonCanThanhToan.getDonGia()));
        System.out.println("Trạng thái: " + hoaDonCanThanhToan.getTrangThaiThanhToan());

        String confirm = readLine("Xác nhận thanh toán hóa đơn này? (Y/N): ");
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Đã hủy thanh toán.");
            return;
        }

        String phuongThucThanhToan = chonPhuongThucThanhToan(hoaDonCanThanhToan);
        HoaDon hoaDon = service.thanhToan(currentBenhNhan.getMaBenhNhan(), maHoaDon, phuongThucThanhToan);

        if ("Chuyển khoản".equals(phuongThucThanhToan)) {
            System.out.println("Đã ghi nhận thanh toán chuyển khoản.");
        } else {
            System.out.println("Thanh toán tiền mặt thành công!");
        }
        inBienNhan(hoaDon);
    }

    private String chonPhuongThucThanhToan(HoaDon hoaDon) {
        while (true) {
            System.out.println("\nChọn phương thức thanh toán:");
            System.out.println("1. Tiền mặt");
            System.out.println("2. Chuyển khoản");
            String choice = readLine("Lựa chọn của bạn: ");

            switch (choice) {
                case "1":
                    return "Tiền mặt";
                case "2":
                    inThongTinChuyenKhoan(hoaDon);
                    String confirm = readLine("Nhập Y sau khi đã chuyển khoản: ");
                    if (confirm.equalsIgnoreCase("Y")) {
                        return "Chuyển khoản";
                    }
                    System.out.println("Chưa ghi nhận thanh toán chuyển khoản.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private void inThongTinChuyenKhoan(HoaDon hoaDon) {
        System.out.println("\n========== THÔNG TIN CHUYỂN KHOẢN ==========");
        System.out.println("Ngân hàng : MB Bank");
        System.out.println("Chủ TK    : BENH VIEN ABC");
        System.out.println("Số TK     : 123456789");
        System.out.println("Nội dung  : " + hoaDon.getMaHoaDon());
        System.out.println("Số tiền   : " + String.format("%,d VND", hoaDon.getDonGia()));
        System.out.println("============================================");
    }

    private void inBienNhan(HoaDon hoaDon) {
        System.out.println("\nBiên nhận:");
        System.out.println("=========================================");
        System.out.println("Mã hóa đơn : " + hoaDon.getMaHoaDon());
        System.out.println("Loại hóa đơn: " + hoaDon.getLoaiHoaDon());
        System.out.println("Số tiền    : " + String.format("%,d VND", hoaDon.getDonGia()));
        System.out.println("Trạng thái : " + hoaDon.getTrangThaiThanhToan());
        System.out.println("Phương thức: " + hoaDon.getPhuongThucThanhToan());
        System.out.println("Ngày thanh toán: " + hoaDon.getNgayThanhToan().format(PAYMENT_DATE_FORMAT));
        System.out.println("=========================================");
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

    private void restoreSession() {
        if (!Files.exists(SESSION_FILE)) {
            return;
        }

        try {
            String tenDangNhap = new String(Files.readAllBytes(SESSION_FILE), StandardCharsets.UTF_8).trim();
            if (tenDangNhap.isEmpty()) {
                clearSession();
                return;
            }
            currentBenhNhan = service.layBenhNhanTheoTenDangNhap(tenDangNhap);
        } catch (Exception ex) {
            currentBenhNhan = null;
            System.out.println("Không thể khôi phục phiên đăng nhập: " + ex.getMessage());
        }
    }

    private void saveSession(BenhNhan benhNhan) throws Exception {
        Files.write(SESSION_FILE, benhNhan.getTenDangNhap().getBytes(StandardCharsets.UTF_8));
    }

    private void clearSession() throws Exception {
        Files.deleteIfExists(SESSION_FILE);
    }
}
