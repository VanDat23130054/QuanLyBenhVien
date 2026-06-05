USE QuanLyBenhVien;
GO

-- ==========================================
-- STEP 1: XÓA SẠCH DỮ LIỆU CŨ (Theo đúng thứ tự ràng buộc)
-- ==========================================
DELETE FROM ChiTietDonThuoc;
DELETE FROM DonThuoc;
DELETE FROM ChanDoan;
DELETE FROM HoSoBenhAn;
DELETE FROM LichKham;
DELETE FROM NoiTru;
DELETE FROM HoaDon;
DELETE FROM LichTruc;
DELETE FROM ChamCong;
DELETE FROM TinhLuong;
DELETE FROM BenhNhan;
DELETE FROM NguoiDung;
DELETE FROM VaiTro;
DELETE FROM Thuoc; -- Đã thêm bảng Thuốc bị thiếu để dọn sạch sẽ
GO

-- ==========================================
-- STEP 2: CHÈN DỮ LIỆU VỚI CƠ CHẾ ÉP ID (IDENTITY_INSERT)
-- ==========================================

-- 1. Bảng VaiTro
SET IDENTITY_INSERT VaiTro ON;
INSERT INTO VaiTro (MaVaiTro, TenVaiTro) VALUES 
(1, 'Quan ly'), (2, 'Bac si'), (3, 'Le tan'), (4, 'Ke toan'), (5, 'Duoc si'), (6, 'Benh nhan');
SET IDENTITY_INSERT VaiTro OFF;
GO

-- 2. Bảng NguoiDung
SET IDENTITY_INSERT NguoiDung ON;
INSERT INTO NguoiDung (MaNguoiDung, TenDangNhap, MatKhau, MaVaiTro, HoTen, SoDienThoai, Email, TrangThai) VALUES
(1, 'admin01', 'password123', 1, 'Nguyen Van Quan Ly', '0901234567', 'quanly@hospital.com', 1),   
(2, 'bacsi.an', 'password123', 2, 'Dr. Nguyen Van An', '0912345678', 'an.nguyen@hospital.com', 1), 
(3, 'bacsi.binh', 'password123', 2, 'Dr. Tran Thi Binh', '0923456789', 'binh.tran@hospital.com', 1),
(4, 'letan01', 'password123', 3, 'Le Thi Le Tan', '0934567890', 'letan@hospital.com', 1),          
(5, 'ketoan01', 'password123', 4, 'Pham Minh Ke Toan', '0945678901', 'ketoan@hospital.com', 1),    
(6, 'duocsi01', 'password123', 5, 'Hoang Van Duoc Sii', '0956789012', 'duocsi@hospital.com', 1),   
(7, 'patient.cuong', 'password123', 6, 'Nguyen Van Cuong', '0967890123', 'cuong@gmail.com', 1),   
(8, 'patient.dung', 'password123', 6, 'Le Thi Dung', '0978901234', 'dung@gmail.com', 1);          
SET IDENTITY_INSERT NguoiDung OFF;
GO

-- 3. Bảng BenhNhan (Bảng này không có IDENTITY nên chèn trực tiếp ID 7 và 8)
INSERT INTO BenhNhan (MaBenhNhan, NgaySinh, GioiTinh, DiaChi, MaBHYT) VALUES
(7, '1990-05-15', 'Nam', '123 Nguyen Trai, Quan 1, TP.HCM', 'GD4797912345678'),
(8, '1995-10-20', 'Nu', '456 Le Loi, Quan Go Vap, TP.HCM', 'DN4797987654321');
GO

-- 4. Bảng LichTruc
SET IDENTITY_INSERT LichTruc ON;
INSERT INTO LichTruc (MaLichTruc, MaNhanVien, NgayTruc, CaTruc, NguoiTao) VALUES
(1, 2, '2026-06-01', 'Ca Sang (07:00 - 13:00)', 1),
(2, 3, '2026-06-01', 'Ca Chieu (13:00 - 19:00)', 1),
(3, 4, '2026-06-01', 'Ca Dem (19:00 - 07:00)', 1);
SET IDENTITY_INSERT LichTruc OFF;
GO

-- 5. Bảng ChamCong
SET IDENTITY_INSERT ChamCong ON;
INSERT INTO ChamCong (MaChamCong, MaNhanVien, NgayLam, TrangThai) VALUES
(1, 2, '2026-06-01', 'Co mat'),
(2, 3, '2026-06-01', 'Co mat'),
(3, 4, '2026-06-01', 'Tre');
SET IDENTITY_INSERT ChamCong OFF;
GO

-- 6. Bảng TinhLuong
SET IDENTITY_INSERT TinhLuong ON;
INSERT INTO TinhLuong (MaBangLuong, MaNhanVien, Thang, Nam, TongLuong, NguoiTinh) VALUES
(1, 2, 6, 2026, 25000000.00, 5),
(2, 3, 6, 2026, 23000000.00, 5),
(3, 4, 6, 2026, 8000000.00, 5);
SET IDENTITY_INSERT TinhLuong OFF;
GO

-- 7. Bảng LichKham
SET IDENTITY_INSERT LichKham ON;
INSERT INTO LichKham (MaLichKham, MaBenhNhan, MaBacSi, NgayKham, TrangThai) VALUES
(1, 7, 2, '2026-06-05 08:30:00', 'Da kham'),
(2, 8, 3, '2026-06-05 10:00:00', 'Cho kham');
SET IDENTITY_INSERT LichKham OFF;
GO

-- 8. Bảng HoSoBenhAn
SET IDENTITY_INSERT HoSoBenhAn ON;
INSERT INTO HoSoBenhAn (MaHoSo, MaBenhNhan, NgayTao) VALUES
(1, 7, '2026-06-05 08:00:00'),
(2, 8, '2026-06-05 09:30:00');
SET IDENTITY_INSERT HoSoBenhAn OFF;
GO

-- 9. Bảng ChanDoan
SET IDENTITY_INSERT ChanDoan ON;
INSERT INTO ChanDoan (MaChanDoan, MaLichKham, MaHoSo, MaBacSi, KetQua, NgayKham) VALUES
(1, 1, 1, 2, 'Viem hong cap tinh, ho khan, sot nhẹ 38 do.', '2026-06-05 09:00:00');
SET IDENTITY_INSERT ChanDoan OFF;
GO

-- 10. Bảng Thuoc
SET IDENTITY_INSERT Thuoc ON;
INSERT INTO Thuoc (MaThuoc, TenThuoc, DonVi, SoLuongTon, DonGia) VALUES
(1, 'Paracetamol 500mg', 'Vien', 2000, 1500.00),
(2, 'Amoxicillin 500mg', 'Vien', 1500, 3500.00),
(3, 'Siro Ho Bao Thanh', 'Chai', 120, 35000.00);
SET IDENTITY_INSERT Thuoc OFF;
GO

-- 11. Bảng DonThuoc
SET IDENTITY_INSERT DonThuoc ON;
INSERT INTO DonThuoc (MaDonThuoc, MaChanDoan, MaBacSi, NgayKe, TrangThai) VALUES
(1, 1, 2, '2026-06-05 09:05:00', 'Chua cap phat');
SET IDENTITY_INSERT DonThuoc OFF;
GO

-- 12. Bảng ChiTietDonThuoc
SET IDENTITY_INSERT ChiTietDonThuoc ON;
INSERT INTO ChiTietDonThuoc (MaChiTiet, MaDonThuoc, MaThuoc, SoLuong, HuongDan) VALUES
(1, 1, 1, 10, 'Uong ngày 2 lan, moi lan 1 vien sau an'),
(2, 1, 2, 14, 'Uong ngay 2 lan, moi lan 1 vien sang/toi (khang sinh)'),
(3, 1, 3, 1, 'Uong ngay 3 lan, moi lan 15ml');
SET IDENTITY_INSERT ChiTietDonThuoc OFF;
GO

-- 13. Bảng NoiTru
SET IDENTITY_INSERT NoiTru ON;
INSERT INTO NoiTru (MaNoiTru, MaBenhNhan, PhongGiuong, NgayNhapVien, NgayXuatVien, TrangThai) VALUES
(1, 8, 'Phong Khong Luu 402 - Giuong B', '2026-06-05 10:30:00', NULL, 'Dang nam vien');
SET IDENTITY_INSERT NoiTru OFF;
GO

-- 14. Bảng HoaDon
SET IDENTITY_INSERT HoaDon ON;
INSERT INTO HoaDon (MaHoaDon, MaBenhNhan, NguoiTao, TongTien, NgayLap, TrangThai, LoaiHoaDon) VALUES
(1, 7, 5, 99000.00, '2026-06-05 09:15:00', 'Chua thanh toan', 'Thuoc'),
(2, 7, 5, 150000.00, '2026-06-05 09:15:00', 'Da thanh toan', 'Kham benh');
SET IDENTITY_INSERT HoaDon OFF;
GO