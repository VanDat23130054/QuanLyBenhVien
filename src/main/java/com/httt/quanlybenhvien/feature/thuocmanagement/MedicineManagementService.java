package com.httt.quanlybenhvien.feature.thuocmanagement;

import com.httt.quanlybenhvien.model.ChiTietDonThuoc;
import com.httt.quanlybenhvien.model.DonThuoc;
import com.httt.quanlybenhvien.model.Thuoc;
import com.httt.quanlybenhvien.repository.impl.ChiTietDonThuocRepository;
import com.httt.quanlybenhvien.repository.impl.DonThuocRepository;
import com.httt.quanlybenhvien.repository.impl.ThuocRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service xử lý các chức năng quản lý thuốc:
 * - Thêm thuốc
 * - Chỉnh sửa thông tin thuốc
 * - Lấy thuốc theo đơn
 * - Xem danh sách thuốc
 */
public class MedicineManagementService {
    private final ThuocRepository thuocRepository;
    private final DonThuocRepository donThuocRepository;
    private final ChiTietDonThuocRepository chiTietDonThuocRepository;

    public MedicineManagementService() {
        this.thuocRepository = new ThuocRepository();
        this.donThuocRepository = new DonThuocRepository();
        this.chiTietDonThuocRepository = new ChiTietDonThuocRepository();
    }

    /**
     * Thêm một loại thuốc mới vào kho
     */
    public Thuoc themThuoc(String tenThuoc, String donVi, int soLuong, int donGia, 
                          LocalDate hanDung, String maKhoThuoc) throws Exception {
        validateNotBlank(tenThuoc, "Tên thuốc");
        validateNotBlank(donVi, "Đơn vị");
        validateNotBlank(maKhoThuoc, "Mã kho thuốc");
        
        if (soLuong < 0) {
            throw new IllegalArgumentException("Số lượng không được âm.");
        }
        if (donGia < 0) {
            throw new IllegalArgumentException("Đơn giá không được âm.");
        }

        String maThuoc = thuocRepository.generateNextMaThuoc();
        Thuoc thuoc = new Thuoc(
                maThuoc,
                tenThuoc,
                LocalDateTime.now(),
                donVi,
                soLuong,
                donGia,
                hanDung,
                maKhoThuoc
        );
        
        return thuocRepository.save(thuoc);
    }

    /**
     * Chỉnh sửa thông tin thuốc
     */
    public Thuoc chinhSuaThuoc(String maThuoc, String tenThuoc, String donVi, 
                             int soLuong, int donGia, LocalDate hanDung) throws Exception {
        validateNotBlank(maThuoc, "Mã thuốc");
        
        Thuoc thuoc = thuocRepository.findById(maThuoc)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thuốc có mã: " + maThuoc));

        // Cập nhật các trường
        if (tenThuoc != null && !tenThuoc.trim().isEmpty()) {
            thuoc.setTenThuoc(tenThuoc);
        }
        if (donVi != null && !donVi.trim().isEmpty()) {
            thuoc.setDonVi(donVi);
        }
        if (soLuong >= 0) {
            thuoc.setSoLuong(soLuong);
        }
        if (donGia >= 0) {
            thuoc.setDonGia(donGia);
        }
        if (hanDung != null) {
            thuoc.setHanDung(hanDung);
        }

        return thuocRepository.update(thuoc);
    }

    /**
     * Lấy danh sách thuốc theo đơn thuốc
     */
    public List<Thuoc> layThuocTheoDon(String maDonThuoc) throws Exception {
        validateNotBlank(maDonThuoc, "Mã đơn thuốc");
        
        // Kiểm tra xem đơn thuốc có tồn tại không
        donThuocRepository.findById(maDonThuoc)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn thuốc có mã: " + maDonThuoc));

        // Lấy danh sách chi tiết đơn thuốc
        List<ChiTietDonThuoc> chiTietList = chiTietDonThuocRepository.findByMaDonThuoc(maDonThuoc);
        
        // Chuyển đổi sang danh sách Thuoc
        List<Thuoc> result = new java.util.ArrayList<>();
        for (ChiTietDonThuoc chiTiet : chiTietList) {
            try {
                Optional<Thuoc> thuoc = thuocRepository.findById(chiTiet.getMaThuoc());
                if (thuoc.isPresent()) {
                    result.add(thuoc.get());
                }
            } catch (Exception e) {
                // Bỏ qua thuốc không tìm được
            }
        }
        return result;
    }

    /**
     * Lấy chi tiết thuốc theo đơn (bao gồm số lượng kê đơn)
     */
    public List<ChiTietDonThuoc> layChiTietThuocTheoDon(String maDonThuoc) throws Exception {
        validateNotBlank(maDonThuoc, "Mã đơn thuốc");
        
        // Kiểm tra xem đơn thuốc có tồn tại không
        donThuocRepository.findById(maDonThuoc)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn thuốc có mã: " + maDonThuoc));

        return chiTietDonThuocRepository.findByMaDonThuoc(maDonThuoc);
    }

    /**
     * Xem danh sách tất cả thuốc
     */
    public List<Thuoc> layDanhSachThuoc() throws Exception {
        return thuocRepository.findAll();
    }

    /**
     * Xem danh sách thuốc hết hạn
     */
    public List<Thuoc> layThuocHetHan() throws Exception {
        LocalDate hienTai = LocalDate.now();
        return thuocRepository.findAll().stream()
                .filter(t -> t.getHanDung() != null && t.getHanDung().isBefore(hienTai))
                .collect(Collectors.toList());
    }

    /**
     * Xem danh sách thuốc sắp hết hạn (trong 30 ngày)
     */
    public List<Thuoc> layThuocSapHetHan() throws Exception {
        LocalDate hienTai = LocalDate.now();
        LocalDate cuoiKy = hienTai.plusDays(30);
        
        return thuocRepository.findAll().stream()
                .filter(t -> t.getHanDung() != null && 
                        t.getHanDung().isAfter(hienTai) && 
                        t.getHanDung().isBefore(cuoiKy))
                .collect(Collectors.toList());
    }

    /**
     * Lấy thông tin một loại thuốc theo mã
     */
    public Thuoc layThongTinThuoc(String maThuoc) throws Exception {
        validateNotBlank(maThuoc, "Mã thuốc");
        
        return thuocRepository.findById(maThuoc)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thuốc có mã: " + maThuoc));
    }

    /**
     * Xóa một loại thuốc
     */
    public void xoaThuoc(String maThuoc) throws Exception {
        validateNotBlank(maThuoc, "Mã thuốc");
        
        // Kiểm tra xem thuốc có tồn tại không
        thuocRepository.findById(maThuoc)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thuốc có mã: " + maThuoc));
        
        thuocRepository.deleteById(maThuoc);
    }

    /**
     * Thêm thuốc vào đơn
     */
    public void themThuocVaoDon(String maDonThuoc, String maThuoc, int soLuongKeDon) throws Exception {
        validateNotBlank(maDonThuoc, "Mã đơn thuốc");
        validateNotBlank(maThuoc, "Mã thuốc");
        
        if (soLuongKeDon <= 0) {
            throw new IllegalArgumentException("Số lượng kê đơn phải lớn hơn 0.");
        }

        // Kiểm tra xem đơn thuốc và thuốc có tồn tại không
        donThuocRepository.findById(maDonThuoc)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn thuốc có mã: " + maDonThuoc));
        
        thuocRepository.findById(maThuoc)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thuốc có mã: " + maThuoc));

        ChiTietDonThuoc chiTiet = new ChiTietDonThuoc(maDonThuoc, maThuoc, soLuongKeDon);
        chiTietDonThuocRepository.save(chiTiet);
    }

    /**
     * Cập nhật số lượng thuốc trong đơn
     */
    public void capNhatSoLuongThuocTrongDon(String maDonThuoc, String maThuoc, int soLuongKeDon) throws Exception {
        validateNotBlank(maDonThuoc, "Mã đơn thuốc");
        validateNotBlank(maThuoc, "Mã thuốc");
        
        if (soLuongKeDon <= 0) {
            throw new IllegalArgumentException("Số lượng kê đơn phải lớn hơn 0.");
        }

        ChiTietDonThuoc chiTiet = new ChiTietDonThuoc(maDonThuoc, maThuoc, soLuongKeDon);
        chiTietDonThuocRepository.update(chiTiet);
    }

    /**
     * Xóa thuốc từ đơn
     */
    public void xoaThuocKhoiDon(String maDonThuoc, String maThuoc) throws Exception {
        validateNotBlank(maDonThuoc, "Mã đơn thuốc");
        validateNotBlank(maThuoc, "Mã thuốc");
        
        chiTietDonThuocRepository.deleteByMaDonThuocAndMaThuoc(maDonThuoc, maThuoc);
    }

    private void validateNotBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " không được để trống.");
        }
    }
}
