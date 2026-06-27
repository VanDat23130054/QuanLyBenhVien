package com.httt.quanlybenhvien.feature.phucnguyen;

import com.httt.quanlybenhvien.model.BenhNhan;
import com.httt.quanlybenhvien.model.HoaDon;
import com.httt.quanlybenhvien.model.LichKham;
import com.httt.quanlybenhvien.model.NhanVien;
import com.httt.quanlybenhvien.model.TaiKhoan;
import com.httt.quanlybenhvien.repository.impl.BenhNhanRepository;
import com.httt.quanlybenhvien.repository.impl.HoaDonRepository;
import com.httt.quanlybenhvien.repository.impl.LichKhamRepository;
import com.httt.quanlybenhvien.repository.impl.NhanVienRepository;
import com.httt.quanlybenhvien.repository.impl.TaiKhoanRepository;

import java.sql.SQLException;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Service xử lý 5 chức năng trong use case của Phúc Nguyễn:
 * Đăng nhập, Đăng kí, Đặt lịch khám, Xem lịch khám, Thanh toán.
 */
public class PhucNguyenService {
    private static final String LOAI_TAI_KHOAN_BENH_NHAN = "Benh Nhan";
    private static final int PHI_DAT_LICH_KHAM = 150_000;

    private final TaiKhoanRepository taiKhoanRepository;
    private final BenhNhanRepository benhNhanRepository;
    private final LichKhamRepository lichKhamRepository;
    private final HoaDonRepository hoaDonRepository;
    private final NhanVienRepository nhanVienRepository;

    public PhucNguyenService() {
        this.taiKhoanRepository = new TaiKhoanRepository();
        this.benhNhanRepository = new BenhNhanRepository();
        this.lichKhamRepository = new LichKhamRepository();
        this.hoaDonRepository = new HoaDonRepository();
        this.nhanVienRepository = new NhanVienRepository();
    }

    public BenhNhan dangNhap(String tenDangNhap, String matKhau) throws Exception {
        TaiKhoan taiKhoan = taiKhoanRepository.findById(tenDangNhap)
                .orElseThrow(() -> new IllegalArgumentException("Tên đăng nhập không tồn tại."));

        if (!taiKhoan.getMatKhau().equals(matKhau)) {
            throw new IllegalArgumentException("Mật khẩu không đúng.");
        }

        if (!LOAI_TAI_KHOAN_BENH_NHAN.equalsIgnoreCase(taiKhoan.getLoaiTaiKhoan())) {
            throw new IllegalArgumentException("Chức năng này chỉ dành cho tài khoản bệnh nhân.");
        }

        return benhNhanRepository.findByTenDangNhap(tenDangNhap)
                .orElseThrow(() -> new IllegalStateException("Tài khoản chưa được liên kết với hồ sơ bệnh nhân."));
    }

    public BenhNhan dangKy(String hoTen, String tenDangNhap, String matKhau) throws Exception {
        validateNotBlank(hoTen, "Họ tên");
        validateNotBlank(tenDangNhap, "Tên đăng nhập");
        validateNotBlank(matKhau, "Mật khẩu");

        if (taiKhoanRepository.existsById(tenDangNhap)) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.");
        }

        TaiKhoan taiKhoan = new TaiKhoan(tenDangNhap, matKhau, LOAI_TAI_KHOAN_BENH_NHAN);
        taiKhoanRepository.save(taiKhoan);

        String maBenhNhan = benhNhanRepository.generateNextMaBenhNhan();
        BenhNhan benhNhan = new BenhNhan(maBenhNhan, hoTen, tenDangNhap);
        benhNhanRepository.save(benhNhan);
        return benhNhan;
    }

    public LichKham datLichKham(String maBenhNhan, LocalDateTime ngayKham, String maBacSi) throws Exception {
        validateNotBlank(maBenhNhan, "Mã bệnh nhân");
        validateNotBlank(maBacSi, "Mã bác sĩ");

        if (ngayKham.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Ngày khám phải lớn hơn thời điểm hiện tại.");
        }

        NhanVien bacSi = nhanVienRepository.findById(maBacSi)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bác sĩ có mã: " + maBacSi));
        if (!normalize(bacSi.getChucVu()).contains("bac si")) {
            throw new IllegalArgumentException("Nhân viên được chọn không phải bác sĩ.");
        }

        String maLeTan = chonLeTanMacDinh();
        String maLichKham = lichKhamRepository.generateNextMaLichKham();

        LichKham lichKham = new LichKham(
                maLichKham,
                ngayKham,
                "Chưa khám",
                maBenhNhan,
                maLeTan,
                maBacSi
        );
        lichKhamRepository.save(lichKham);

        // Tạo hóa đơn đặt lịch khám theo đúng class model HoaDon hiện có.
        String maHoaDon = hoaDonRepository.generateNextMaHoaDon();
        HoaDon hoaDon = new HoaDon(
                maHoaDon,
                "Hóa đơn đặt lịch khám - " + maLichKham,
                PHI_DAT_LICH_KHAM,
                maBenhNhan
        );
        hoaDonRepository.save(hoaDon);

        return lichKham;
    }

    public List<LichKham> xemLichKham(String maBenhNhan) throws SQLException {
        validateNotBlank(maBenhNhan, "Mã bệnh nhân");
        return lichKhamRepository.findByMaBenhNhan(maBenhNhan);
    }

    public List<HoaDon> xemHoaDon(String maBenhNhan) throws SQLException {
        validateNotBlank(maBenhNhan, "Mã bệnh nhân");
        return hoaDonRepository.findByMaBenhNhan(maBenhNhan);
    }

    public HoaDon thanhToan(String maBenhNhan, String maHoaDon) throws Exception {
        validateNotBlank(maBenhNhan, "Mã bệnh nhân");
        validateNotBlank(maHoaDon, "Mã hóa đơn");

        HoaDon hoaDon = hoaDonRepository.findById(maHoaDon)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn: " + maHoaDon));
        if (!maBenhNhan.equals(hoaDon.getMaBenhNhan())) {
            throw new IllegalArgumentException("Hóa đơn này không thuộc về bệnh nhân đang đăng nhập.");
        }

        // Class model hiện tại chưa có thuộc tính trạng thái thanh toán.
        // Vì vậy thao tác này xác nhận thanh toán và trả về hóa đơn để in biên nhận.
        return hoaDon;
    }

    public List<NhanVien> layDanhSachBacSi() throws SQLException {
        return nhanVienRepository.findAll()
                .stream()
                .filter(nhanVien -> normalize(nhanVien.getChucVu()).contains("bac si"))
                .collect(Collectors.toList());
    }

    public String layTenBacSi(String maBacSi) throws SQLException {
        return nhanVienRepository.findById(maBacSi)
                .map(NhanVien::getTenNhanVien)
                .orElse("Chưa có thông tin");
    }

    private String chonLeTanMacDinh() throws SQLException {
        List<NhanVien> leTanList = nhanVienRepository.findAll()
                .stream()
                .filter(nhanVien -> normalize(nhanVien.getChucVu()).contains("le tan"))
                .collect(Collectors.toList());
        if (leTanList.isEmpty()) {
            return null;
        }
        return leTanList.get(0).getMaNhanVien();
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
