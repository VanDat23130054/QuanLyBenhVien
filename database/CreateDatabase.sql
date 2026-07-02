USE master;
GO

-- Drop the database if it already exists to reset everything cleanly
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'QuanLyBenhVien')
BEGIN
    -- Force close any existing connections to prevent "database in use" errors
    ALTER DATABASE QuanLyBenhVien SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE QuanLyBenhVien;
    PRINT 'Existing QuanLyBenhVien database dropped successfully.';
END
GO

-- Create the database fresh
CREATE DATABASE QuanLyBenhVien;
GO

USE QuanLyBenhVien;
GO

------------------------------------------------------------
-- 1. BASE SECURITY & IDENTITY TABLES
------------------------------------------------------------

-- Table mapping the 'TaiKhoan' class
CREATE TABLE TaiKhoan (
    tenDangNhap VARCHAR(50) PRIMARY KEY,
    matKhau VARCHAR(255) NOT NULL,
    loaiTaiKhoan NVARCHAR(50) NOT NULL
);

-- Table mapping the 'BenhNhan' class
CREATE TABLE BenhNhan (
    maBenhNhan VARCHAR(50) PRIMARY KEY,
    tenBenhNhan NVARCHAR(100) NOT NULL,
    tenDangNhap VARCHAR(50) UNIQUE,
    CONSTRAINT FK_BenhNhan_TaiKhoan FOREIGN KEY (tenDangNhap) REFERENCES TaiKhoan(tenDangNhap)
);

-- Consolidated 'NhanVien' table
-- Updated with the new 'khoa' attribute from your revised diagram
CREATE TABLE NhanVien (
    maNhanVien VARCHAR(50) PRIMARY KEY,
    tenNhanVien NVARCHAR(100) NOT NULL,
    khoa NVARCHAR(100),  -- Added from your updated class diagram
    chucVu NVARCHAR(50), -- E.g., 'Bac si', 'Le tan', 'Duoc si', 'Quan ly'
    mucLuong BIGINT,
    tenDangNhap VARCHAR(50) UNIQUE,
    CONSTRAINT FK_NhanVien_TaiKhoan FOREIGN KEY (tenDangNhap) REFERENCES TaiKhoan(tenDangNhap)
);

------------------------------------------------------------
-- 2. INVENTORY & PHARMACY TABLES
------------------------------------------------------------

-- Table mapping 'KhoThuoc'
CREATE TABLE KhoThuoc (
    maKhoThuoc VARCHAR(50) PRIMARY KEY,
    maDuocSi VARCHAR(50), -- Points to NhanVien (Pharmacist)
    CONSTRAINT FK_KhoThuoc_NhanVien FOREIGN KEY (maDuocSi) REFERENCES NhanVien(maNhanVien)
);

-- Table mapping 'Thuoc'
CREATE TABLE Thuoc (
    maThuoc VARCHAR(50) PRIMARY KEY,
    tenThuoc NVARCHAR(200) NOT NULL,
    ngayNhap DATETIME2,
    donVi NVARCHAR(50),
    soLuong INT NOT NULL DEFAULT 0,
    donGia INT NOT NULL DEFAULT 0,
    hanDung DATE,
    maKhoThuoc VARCHAR(50),
    CONSTRAINT FK_Thuoc_KhoThuoc FOREIGN KEY (maKhoThuoc) REFERENCES KhoThuoc(maKhoThuoc)
);

------------------------------------------------------------
-- 3. CLINICAL & PATIENT OPERATION TABLES
------------------------------------------------------------

-- Table mapping 'HoaDon'
CREATE TABLE HoaDon (
    maHoaDon VARCHAR(50) PRIMARY KEY,
    loaiHoaDon NVARCHAR(100),
    donGia INT NOT NULL DEFAULT 0,
    trangThaiThanhToan NVARCHAR(50) NOT NULL DEFAULT N'Chưa thanh toán',
    ngayThanhToan DATETIME2 NULL,
    phuongThucThanhToan NVARCHAR(50) NULL,
    maBenhNhan VARCHAR(50) NOT NULL,
    CONSTRAINT FK_HoaDon_BenhNhan FOREIGN KEY (maBenhNhan) REFERENCES BenhNhan(maBenhNhan)
);

-- Table mapping 'LichKham'
CREATE TABLE LichKham (
    maLichKham VARCHAR(50) PRIMARY KEY,
    ngayKham DATETIME2 NOT NULL,
    chanDoan NVARCHAR(500),
    maBenhNhan VARCHAR(50) NOT NULL,
    maLeTan VARCHAR(50), -- Points to NhanVien (Receptionist)
    maBacSi VARCHAR(50), -- Points to NhanVien (Doctor)
    CONSTRAINT FK_LichKham_BenhNhan FOREIGN KEY (maBenhNhan) REFERENCES BenhNhan(maBenhNhan),
    CONSTRAINT FK_LichKham_LeTan FOREIGN KEY (maLeTan) REFERENCES NhanVien(maNhanVien),
    CONSTRAINT FK_LichKham_BacSi FOREIGN KEY (maBacSi) REFERENCES NhanVien(maNhanVien)
);

-- Table mapping 'DonThuoc'
-- Updated: Tracks 'maBacSi' directly as the creator, with an optional 'maDuocSi' for handling fulfillment
CREATE TABLE DonThuoc (
    maDonThuoc VARCHAR(50) PRIMARY KEY,
    maLichKham VARCHAR(50) UNIQUE NOT NULL,
    maBacSi VARCHAR(50) NOT NULL, -- Directly linked to the doctor prescribing it
    maDuocSi VARCHAR(50) NULL,     -- Kept as optional to record who hands it to the patient
    CONSTRAINT FK_DonThuoc_LichKham FOREIGN KEY (maLichKham) REFERENCES LichKham(maLichKham),
    CONSTRAINT FK_DonThuoc_BacSi FOREIGN KEY (maBacSi) REFERENCES NhanVien(maNhanVien),
    CONSTRAINT FK_DonThuoc_DuocSi FOREIGN KEY (maDuocSi) REFERENCES NhanVien(maNhanVien)
);

-- Junction table for 'DonThuoc' and 'Thuoc' (Resolves List<Thuoc>)
CREATE TABLE ChiTietDonThuoc (
    maDonThuoc VARCHAR(50),
    maThuoc VARCHAR(50),
    soLuongKeDon INT NOT NULL DEFAULT 1,
    PRIMARY KEY (maDonThuoc, maThuoc),
    CONSTRAINT FK_ChiTietDonThuoc_DonThuoc FOREIGN KEY (maDonThuoc) REFERENCES DonThuoc(maDonThuoc) ON DELETE CASCADE,
    CONSTRAINT FK_ChiTietDonThuoc_Thuoc FOREIGN KEY (maThuoc) REFERENCES Thuoc(maThuoc)
);

------------------------------------------------------------
-- 4. ROOM, BED, & STAFF SCHEDULING TABLES
------------------------------------------------------------

-- Table mapping 'Phong'
CREATE TABLE Phong (
    maPhong VARCHAR(50) PRIMARY KEY
);

-- Table mapping 'Giuong'
CREATE TABLE Giuong (
    maGiuong VARCHAR(50) PRIMARY KEY,
    donGia INT NOT NULL DEFAULT 0,
    maPhong VARCHAR(50) NOT NULL,
    maBenhNhan VARCHAR(50) NULL,
    CONSTRAINT FK_Giuong_Phong FOREIGN KEY (maPhong) REFERENCES Phong(maPhong) ON DELETE CASCADE,
    CONSTRAINT FK_Giuong_BenhNhan FOREIGN KEY (maBenhNhan) REFERENCES BenhNhan(maBenhNhan)
);

-- Table mapping 'PhanCong'
CREATE TABLE PhanCong (
    maPhanCong VARCHAR(50) PRIMARY KEY,
    ngayPhanCong DATETIME2 NOT NULL,
    maQuanLy VARCHAR(50), -- Points to NhanVien (Manager)
    CONSTRAINT FK_PhanCong_NhanVien FOREIGN KEY (maQuanLy) REFERENCES NhanVien(maNhanVien)
);

-- Table mapping 'LichTruc'
CREATE TABLE LichTruc (
    maLichTruc VARCHAR(50) PRIMARY KEY,
    ngayTruc DATETIME2 NOT NULL,
    caTruc NVARCHAR(50) NOT NULL,
    maNhanVien VARCHAR(50) NOT NULL, -- Points to NhanVien
    maPhong VARCHAR(50) NOT NULL,
    maPhanCong VARCHAR(50) NOT NULL,
    CONSTRAINT FK_LichTruc_NhanVien FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien),
    CONSTRAINT FK_LichTruc_Phong FOREIGN KEY (maPhong) REFERENCES Phong(maPhong),
    CONSTRAINT FK_LichTruc_PhanCong FOREIGN KEY (maPhanCong) REFERENCES PhanCong(maPhanCong) ON DELETE CASCADE
);
GO
