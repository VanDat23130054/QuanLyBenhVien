USE QuanLyBenhVien;
GO

-- Clear any existing data first to prevent duplicate errors if re-running
-- Order of deletion matters due to Foreign Key constraints!
DELETE FROM LichTruc;
DELETE FROM PhanCong;
DELETE FROM Giuong;
DELETE FROM Phong;
DELETE FROM ChiTietDonThuoc;
DELETE FROM DonThuoc;
DELETE FROM LichKham;
DELETE FROM HoaDon;
DELETE FROM Thuoc;
DELETE FROM KhoThuoc;
DELETE FROM NhanVien;
DELETE FROM BenhNhan;
DELETE FROM TaiKhoan;
GO

PRINT '==================================================';
PRINT 'INSERTING MOCK DATA FOR QUANLYBENHVIEN';
PRINT '==================================================';

------------------------------------------------------------
-- 1. TAIKHOAN DATA (Accounts)
------------------------------------------------------------
INSERT INTO TaiKhoan (tenDangNhap, matKhau, loaiTaiKhoan) VALUES
('admin_thanh', 'hash_pass_123', 'Quan Ly'),
('doctor_nam',   'hash_pass_123', 'Bac Si'),
('doctor_huong', 'hash_pass_123', 'Bac Si'),
('recep_hoa',    'hash_pass_123', 'Le Tan'),
('pharma_minh',  'hash_pass_123', 'Duoc Si'),
('patient_an',   'hash_pass_123', 'Benh Nhan'),
('patient_binh', 'hash_pass_123', 'Benh Nhan'),
('patient_cuong','hash_pass_123', 'Benh Nhan');

------------------------------------------------------------
-- 2. BENHNHAN DATA (Patients)
------------------------------------------------------------
INSERT INTO BenhNhan (maBenhNhan, tenBenhNhan, tenDangNhap) VALUES
('BN001', N'Nguyễn Văn An',   'patient_an'),
('BN002', N'Trần Thị Bình',   'patient_binh'),
('BN003', N'Lê Hoàng Cường', 'patient_cuong');

------------------------------------------------------------
-- 3. NHANVIEN DATA (Staff & Medical Professionals)
------------------------------------------------------------
INSERT INTO NhanVien (maNhanVien, tenNhanVien, khoa, chucVu, mucLuong, tenDangNhap) VALUES
('NV001', N'Phạm Tiến Thành', N'Ban Giám Đốc', N'Quan ly', 45000000, 'admin_thanh'),
('NV002', N'Nguyễn Hoài Nam',  N'Khoa Nội',    N'Bac si',  35000000, 'doctor_nam'),
('NV003', N'Đỗ Thúy Hương',   N'Khoa Nhi',    N'Bac si',  32000000, 'doctor_huong'),
('NV004', N'Lê Tuyết Hoa',     N'Sảnh Đón Tiếp',N'Le tan',  12000000, 'recep_hoa'),
('NV005', N'Hoàng Nhật Minh',  N'Khoa Dược',   N'Duoc si', 18000000, 'pharma_minh');

------------------------------------------------------------
-- 4. KHOTHUOC & THUOC DATA (Pharmacy System)
------------------------------------------------------------
-- NV005 is our 'Duoc si'
INSERT INTO KhoThuoc (maKhoThuoc, maDuocSi) VALUES
('KHO01', 'NV005');

INSERT INTO Thuoc (maThuoc, tenThuoc, ngayNhap, donVi, soLuong, donGia, hanDung, maKhoThuoc) VALUES
('T001', N'Paracetamol 500mg',   '2026-01-10 08:00:00', N'Viên', 1000, 1500,  '2028-12-31', 'KHO01'),
('T002', N'Amoxicillin 500mg',   '2026-02-15 09:30:00', N'Viên', 500,  4000,  '2027-06-30', 'KHO01'),
('T003', N'Siro Ho Prospan',     '2026-03-01 14:15:00', N'Chai', 120,  75000, '2027-01-01', 'KHO01'),
('T004', N'Vitamin C 1000mg',    '2026-04-12 11:00:00', N'Viên', 800,  2500,  '2029-03-15', 'KHO01');

------------------------------------------------------------
-- 5. HOADON DATA (Invoices)
------------------------------------------------------------
INSERT INTO HoaDon (maHoaDon, loaiHoaDon, donGia, maBenhNhan) VALUES
('HD001', N'Hóa đơn khám lâm sàng', 150000, 'BN001'),
('HD002', N'Hóa đơn khám nhi',       200000, 'BN002'),
('HD003', N'Hóa đơn điều trị nội trú',1200000,'BN003');

------------------------------------------------------------
-- 6. LICHKHAM DATA (Medical Appointments)
------------------------------------------------------------
-- NV004 = Receptionist (Le tan), NV002 & NV003 = Doctors (Bac si)
INSERT INTO LichKham (maLichKham, ngayKham, chanDoan, maBenhNhan, maLeTan, maBacSi) VALUES
('LK001', '2026-06-10 08:30:00', N'Sốt siêu vi',            'BN001', 'NV004', 'NV002'),
('LK002', '2026-06-11 10:15:00', N'Viêm họng cấp ở trẻ em', 'BN002', 'NV004', 'NV003'),
('LK003', '2026-06-12 15:00:00', N'Nhiễm trùng đường ruột', 'BN003', 'NV004', 'NV002');

------------------------------------------------------------
-- 7. DONTHUOC & CHITIETDONTHUOC DATA (Prescriptions)
------------------------------------------------------------
-- Creator (maBacSi), Dispenser (maDuocSi = NV005)
INSERT INTO DonThuoc (maDonThuoc, maLichKham, maBacSi, maDuocSi) VALUES
('DT001', 'LK001', 'NV002', 'NV005'),
('DT002', 'LK002', 'NV003', 'NV005');

-- Add specific items inside those prescriptions
INSERT INTO ChiTietDonThuoc (maDonThuoc, maThuoc, soLuongKeDon) VALUES
('DT001', 'T001', 10), -- 10 viên Paracetamol for patient An
('DT001', 'T004', 5),  -- 5 viên Vitamin C for patient An
('DT002', 'T003', 1);  -- 1 chai Siro Ho Prospan for patient Binh

------------------------------------------------------------
-- 8. PHONG & GIUONG DATA (Rooms & Beds)
------------------------------------------------------------
INSERT INTO Phong (maPhong) VALUES 
('P101'), 
('P102');

-- Bed 02 in Room 101 has Patient 03 (Cuong) checking in
INSERT INTO Giuong (maGiuong, donGia, maPhong, maBenhNhan) VALUES
('G101-A', 250000, 'P101', NULL),
('G101-B', 250000, 'P101', 'BN003'), 
('G102-A', 400000, 'P102', NULL);

------------------------------------------------------------
-- 9. PHANCONG & LICHTRUC DATA (Schedules)
------------------------------------------------------------
-- Created by Manager NV001
INSERT INTO PhanCong (maPhanCong, ngayPhanCong, maQuanLy) VALUES
('PC001', '2026-06-01 07:00:00', 'NV001');

-- Shift assignments linking workers to physical rooms
INSERT INTO LichTruc (maLichTruc, ngayTruc, caTruc, maNhanVien, maPhong, maPhanCong) VALUES
('LT001', '2026-06-15 06:00:00', N'Ca Sáng', 'NV002', 'P101', 'PC001'), -- Dr. Nam on Morning duty in Room 101
('LT002', '2026-06-15 14:00:00', N'Ca Chiều', 'NV003', 'P101', 'PC001'), -- Dr. Huong on Afternoon duty in Room 101
('LT003', '2026-06-15 22:00:00', N'Ca Đêm',   'NV005', 'P102', 'PC001'); -- Pharmacist Minh handling inventory or overnight ward room 102
GO

PRINT '==================================================';
PRINT 'MOCK DATA DEPLOYED SUCCESSFULLY!';
PRINT '==================================================';