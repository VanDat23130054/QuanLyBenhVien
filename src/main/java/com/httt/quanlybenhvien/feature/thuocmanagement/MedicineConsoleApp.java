package com.httt.quanlybenhvien.feature.thuocmanagement;

import com.httt.quanlybenhvien.model.ChiTietDonThuoc;
import com.httt.quanlybenhvien.model.Thuoc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Console application cho quản lý thuốc
 * Chức năng:
 * 1. Thêm thuốc
 * 2. Chỉnh sửa thông tin thuốc
 * 3. Lấy thuốc theo đơn
 * 4. Xem danh sách thuốc
 * 5. Xem danh sách thuốc hết hạn
 * 6. Xem danh sách thuốc sắp hết hạn
 */
public class MedicineConsoleApp {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter VIEW_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Scanner scanner = new Scanner(System.in);
    private final MedicineManagementService service = new MedicineManagementService();

    public void run() {
        boolean running = true;
        try {
            while (running) {
                printMenu();
                String choice = readLine("Chọn chức năng: ");
                try {
                    switch (choice) {
                        case "1":
                            themThuoc();
                            break;
                        case "2":
                            chinhSuaThuoc();
                            break;
                        case "3":
                            layThuocTheoDon();
                            break;
                        case "4":
                            xemDanhSachThuoc();
                            break;
                        case "5":
                            xemThuocHetHan();
                            break;
                        case "6":
                            xemThuocSapHetHan();
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
        } finally {
            // Do not propagate exceptions when closing System.in, but ensure resource is released
            try {
                scanner.close();
            } catch (Exception ignored) {
            }
        }
    }

    public static void main(String[] args) {
        new MedicineConsoleApp().run();
    }

    private void printMenu() {
        System.out.println();
        System.out.println("====================================");
        System.out.println("    QUẢN LÝ THUỐC - KHO THUỐC");
        System.out.println("====================================");
        System.out.println("1. Thêm thuốc mới");
        System.out.println("2. Chỉnh sửa thông tin thuốc");
        System.out.println("3. Lấy thuốc theo đơn");
        System.out.println("4. Xem danh sách tất cả thuốc");
        System.out.println("5. Xem danh sách thuốc hết hạn");
        System.out.println("6. Xem danh sách thuốc sắp hết hạn (30 ngày)");
        System.out.println("0. Thoát");
    }

    private void themThuoc() throws Exception {
        System.out.println("\n--- THÊM THUỐC MỚI ---");
        
        String tenThuoc = readLine("Tên thuốc: ");
        String donVi = readLine("Đơn vị (viên, hộp, lọ, v.v.): ");
        int soLuong = readInt("Số lượng: ");
        int donGia = readInt("Đơn giá (VND): ");
        LocalDate hanDung = readDate("Hạn dùng (dd/MM/yyyy): ");
        String maKhoThuoc = readLine("Mã kho thuốc: ");

        Thuoc thuoc = service.themThuoc(tenThuoc, donVi, soLuong, donGia, hanDung, maKhoThuoc);
        System.out.println("✓ Thêm thuốc thành công!");
        System.out.println("Mã thuốc: " + thuoc.getMaThuoc());
        System.out.println("Tên thuốc: " + thuoc.getTenThuoc());
        System.out.println("Số lượng: " + thuoc.getSoLuong());
    }

    private void chinhSuaThuoc() throws Exception {
        System.out.println("\n--- CHỈNH SỬA THUỐC ---");
        
        // Hiển thị danh sách thuốc
        List<Thuoc> danhSach = service.layDanhSachThuoc();
        if (danhSach.isEmpty()) {
            System.out.println("Hiện không có thuốc nào trong kho.");
            return;
        }

        System.out.println("\nDanh sách thuốc:");
        System.out.printf("%-8s %-30s %-10s %-10s %-12s%n", "Mã", "Tên thuốc", "Đơn vị", "Số lượng", "Hạn dùng");
        System.out.println("------------------------------------------------------------------------");
        for (Thuoc t : danhSach) {
            String hanDung = t.getHanDung() != null ? t.getHanDung().format(VIEW_DATE_FORMAT) : "N/A";
            System.out.printf("%-8s %-30s %-10s %-10d %-12s%n", t.getMaThuoc(), t.getTenThuoc(), 
                    t.getDonVi(), t.getSoLuong(), hanDung);
        }

        String maThuoc = readLine("\nNhập mã thuốc cần chỉnh sửa: ");
        Thuoc thuocHienTai = service.layThongTinThuoc(maThuoc);

        System.out.println("\nThông tin hiện tại:");
        System.out.println("- Tên thuốc: " + thuocHienTai.getTenThuoc());
        System.out.println("- Đơn vị: " + thuocHienTai.getDonVi());
        System.out.println("- Số lượng: " + thuocHienTai.getSoLuong());
        System.out.println("- Đơn giá: " + thuocHienTai.getDonGia());
        if (thuocHienTai.getHanDung() != null) {
            System.out.println("- Hạn dùng: " + thuocHienTai.getHanDung().format(VIEW_DATE_FORMAT));
        }

        System.out.println("\n(Nhập trực tiếp để giữ nguyên, hoặc nhập giá trị mới)");

        String tenThuocMoi = readLineOptional("Tên thuốc mới [" + thuocHienTai.getTenThuoc() + "]: ");
        String donViMoi = readLineOptional("Đơn vị mới [" + thuocHienTai.getDonVi() + "]: ");
        int soLuongMoi = readIntOptionalWithDefault("Số lượng mới [" + thuocHienTai.getSoLuong() + "]: ", thuocHienTai.getSoLuong());
        int donGiaMoi = readIntOptionalWithDefault("Đơn giá mới [" + thuocHienTai.getDonGia() + "]: ", thuocHienTai.getDonGia());
        LocalDate hanDungMoi = readDateOptionalWithDefault("Hạn dùng mới [" + 
                (thuocHienTai.getHanDung() != null ? thuocHienTai.getHanDung().format(VIEW_DATE_FORMAT) : "N/A") + "]: ", thuocHienTai.getHanDung());

        service.chinhSuaThuoc(
                maThuoc,
                tenThuocMoi.isEmpty() ? thuocHienTai.getTenThuoc() : tenThuocMoi,
                donViMoi.isEmpty() ? thuocHienTai.getDonVi() : donViMoi,
                soLuongMoi,
                donGiaMoi,
                hanDungMoi
        );

        System.out.println("✓ Chỉnh sửa thuốc thành công!");
    }

    private void layThuocTheoDon() throws Exception {
        System.out.println("\n--- LẤY THUỐC THEO ĐƠN ---");
        
        String maDonThuoc = readLine("Nhập mã đơn thuốc: ");
        
        // Lấy danh sách chi tiết thuốc
        List<ChiTietDonThuoc> chiTietList = service.layChiTietThuocTheoDon(maDonThuoc);
        
        if (chiTietList.isEmpty()) {
            System.out.println("Đơn thuốc này chưa có thuốc nào.");
            return;
        }

        System.out.println("\nDanh sách thuốc trong đơn " + maDonThuoc + ":");
        System.out.printf("%-8s %-30s %-10s %-15s %-12s %-12s%n", "Mã thuốc", "Tên thuốc", "Đơn vị", "Số lượng", "Đơn giá", "Thành tiền");
        System.out.println("-----------------------------------------------------------------------------------------------");

        int tongThanhTien = 0;
        for (ChiTietDonThuoc chiTiet : chiTietList) {
            Thuoc thuoc = service.layThongTinThuoc(chiTiet.getMaThuoc());
            int thanhTien = thuoc.getDonGia() * chiTiet.getSoLuongKeDon();
            tongThanhTien += thanhTien;

            System.out.printf("%-8s %-30s %-10s %-15d %,12d %,12d%n",
                    thuoc.getMaThuoc(),
                    thuoc.getTenThuoc(),
                    thuoc.getDonVi(),
                    chiTiet.getSoLuongKeDon(),
                    thuoc.getDonGia(),
                    thanhTien);
        }

        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.printf("%-8s %-30s %-10s %-15s %,12s %,12d%n", "", "", "", "Tổng cộng:", "", tongThanhTien);
    }

    private void xemDanhSachThuoc() throws Exception {
        System.out.println("\n--- DANH SÁCH TẤT CẢ THUỐC ---");
        
        List<Thuoc> danhSach = service.layDanhSachThuoc();
        
        if (danhSach.isEmpty()) {
            System.out.println("Hiện không có thuốc nào trong kho.");
            return;
        }

        System.out.printf("%-8s %-30s %-10s %-10s %-12s %-12s%n", "Mã", "Tên thuốc", "Đơn vị", "Số lượng", "Đơn giá", "Hạn dùng");
        System.out.println("-----------------------------------------------------------------------------------");
        
        for (Thuoc t : danhSach) {
            String hanDung = t.getHanDung() != null ? t.getHanDung().format(VIEW_DATE_FORMAT) : "N/A";
            System.out.printf("%-8s %-30s %-10s %-10d %,12d %-12s%n",
                    t.getMaThuoc(),
                    t.getTenThuoc(),
                    t.getDonVi(),
                    t.getSoLuong(),
                    t.getDonGia(),
                    hanDung);
        }
        
        System.out.println("Tổng số loại thuốc: " + danhSach.size());
    }

    private void xemThuocHetHan() throws Exception {
        System.out.println("\n--- DANH SÁCH THUỐC HẾT HẠN ---");
        
        List<Thuoc> thuocHetHan = service.layThuocHetHan();
        
        if (thuocHetHan.isEmpty()) {
            System.out.println("Hiện không có thuốc hết hạn.");
            return;
        }

        System.out.printf("%-8s %-30s %-10s %-10s %-12s%n", "Mã", "Tên thuốc", "Đơn vị", "Số lượng", "Hạn dùng");
        System.out.println("------------------------------------------------------------------");
        
        for (Thuoc t : thuocHetHan) {
            String hanDung = t.getHanDung() != null ? t.getHanDung().format(VIEW_DATE_FORMAT) : "N/A";
            System.out.printf("%-8s %-30s %-10s %-10d %-12s%n", 
                    t.getMaThuoc(), 
                    t.getTenThuoc(), 
                    t.getDonVi(), 
                    t.getSoLuong(),
                    hanDung);
        }
        
        System.out.println("⚠ Tổng số loại thuốc hết hạn: " + thuocHetHan.size());
    }

    private void xemThuocSapHetHan() throws Exception {
        System.out.println("\n--- DANH SÁCH THUỐC SẮP HẾT HẠN (30 NGÀY) ---");
        
        List<Thuoc> thuocSapHetHan = service.layThuocSapHetHan();
        
        if (thuocSapHetHan.isEmpty()) {
            System.out.println("Hiện không có thuốc sắp hết hạn.");
            return;
        }

        System.out.printf("%-8s %-30s %-10s %-10s %-12s%n", "Mã", "Tên thuốc", "Đơn vị", "Số lượng", "Hạn dùng");
        System.out.println("------------------------------------------------------------------");
        
        for (Thuoc t : thuocSapHetHan) {
            String hanDung = t.getHanDung() != null ? t.getHanDung().format(VIEW_DATE_FORMAT) : "N/A";
            System.out.printf("%-8s %-30s %-10s %-10d %-12s%n", 
                    t.getMaThuoc(), 
                    t.getTenThuoc(), 
                    t.getDonVi(), 
                    t.getSoLuong(),
                    hanDung);
        }
        
        System.out.println("⚠ Tổng số loại thuốc sắp hết hạn: " + thuocSapHetHan.size());
    }

    private String readLine(String label) {
        System.out.print(label);
        return scanner.nextLine().trim();
    }

    private String readLineOptional(String label) {
        System.out.print(label);
        return scanner.nextLine().trim();
    }

    private int readInt(String label) {
        while (true) {
            String value = readLine(label);
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                System.out.println("Vui lòng nhập một số nguyên hợp lệ.");
            }
        }
    }

    private LocalDate readDate(String label) {
        while (true) {
            String value = readLine(label);
            try {
                return LocalDate.parse(value, DATE_FORMAT);
            } catch (DateTimeParseException ex) {
                System.out.println("Sai định dạng. Vui lòng nhập theo định dạng dd/MM/yyyy. Ví dụ: 25/12/2025");
            }
        }
    }

    private int readIntOptionalWithDefault(String label, int defaultValue) {
        while (true) {
            String value = readLineOptional(label);
            if (value.isEmpty()) {
                return defaultValue;
            }
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                System.out.println("Vui lòng nhập một số nguyên hợp lệ hoặc để trống để giữ nguyên.");
            }
        }
    }

    private LocalDate readDateOptionalWithDefault(String label, LocalDate defaultValue) {
        while (true) {
            String value = readLineOptional(label);
            if (value.isEmpty()) {
                return defaultValue;
            }
            try {
                return LocalDate.parse(value, DATE_FORMAT);
            } catch (DateTimeParseException ex) {
                System.out.println("Sai định dạng. Vui lòng nhập theo định dạng dd/MM/yyyy hoặc để trống để giữ nguyên.");
            }
        }
    }
}
