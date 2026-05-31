-- Tao co so du lieu
CREATE DATABASE QuanLyBenhVien;
GO
USE QuanLyBenhVien;
GO

-- ==========================================
-- 1. QUAN LY TAI KHOAN & VAI TRO
-- ==========================================

CREATE TABLE VaiTro (
    MaVaiTro INT IDENTITY(1,1) PRIMARY KEY,
    TenVaiTro VARCHAR(50) NOT NULL -- Ke toan, Le tan, Benh nhan, Bac si, Duoc si, Quan ly
);

CREATE TABLE NguoiDung (
    MaNguoiDung INT IDENTITY(1,1) PRIMARY KEY,
    TenDangNhap VARCHAR(50) UNIQUE NOT NULL,
    MatKhau VARCHAR(255) NOT NULL,
    MaVaiTro INT NOT NULL,
    HoTen VARCHAR(100) NOT NULL,
    SoDienThoai VARCHAR(15),
    Email VARCHAR(100),
    TrangThai BIT DEFAULT 1, -- 1: Hoat dong, 0: Khoa
    FOREIGN KEY (MaVaiTro) REFERENCES VaiTro(MaVaiTro)
);

-- ==========================================
-- 2. QUAN LY NHAN SU (Lich truc, Cham cong, Luong)
-- ==========================================

CREATE TABLE LichTruc (
    MaLichTruc INT IDENTITY(1,1) PRIMARY KEY,
    MaNhanVien INT NOT NULL, -- MaNguoiDung cua Bac si/Nhan vien
    NgayTruc DATE NOT NULL,
    CaTruc VARCHAR(50) NOT NULL,
    NguoiTao INT, -- MaNguoiDung cua Quan ly
    FOREIGN KEY (MaNhanVien) REFERENCES NguoiDung(MaNguoiDung),
    FOREIGN KEY (NguoiTao) REFERENCES NguoiDung(MaNguoiDung)
);

CREATE TABLE ChamCong (
    MaChamCong INT IDENTITY(1,1) PRIMARY KEY,
    MaNhanVien INT NOT NULL,
    NgayLam DATE NOT NULL,
    TrangThai VARCHAR(50), -- Co mat, Vang, Tre
    FOREIGN KEY (MaNhanVien) REFERENCES NguoiDung(MaNguoiDung)
);

CREATE TABLE TinhLuong (
    MaBangLuong INT IDENTITY(1,1) PRIMARY KEY,
    MaNhanVien INT NOT NULL,
    Thang INT NOT NULL,
    Nam INT NOT NULL,
    TongLuong DECIMAL(18,2) NOT NULL,
    NguoiTinh INT, -- MaNguoiDung cua Ke toan
    FOREIGN KEY (MaNhanVien) REFERENCES NguoiDung(MaNguoiDung),
    FOREIGN KEY (NguoiTinh) REFERENCES NguoiDung(MaNguoiDung)
);

-- ==========================================
-- 3. BENH NHAN & LICH KHAM
-- ==========================================

CREATE TABLE BenhNhan (
    MaBenhNhan INT PRIMARY KEY, -- Lien ket 1-1 voi MaNguoiDung
    NgaySinh DATE,
    GioiTinh VARCHAR(10),
    DiaChi VARCHAR(255),
    MaBHYT VARCHAR(50),
    FOREIGN KEY (MaBenhNhan) REFERENCES NguoiDung(MaNguoiDung)
);

CREATE TABLE LichKham (
    MaLichKham INT IDENTITY(1,1) PRIMARY KEY,
    MaBenhNhan INT NOT NULL,
    MaBacSi INT, -- Co the duoc Le tan cap nhat sau
    NgayKham DATETIME NOT NULL,
    TrangThai VARCHAR(50) DEFAULT 'Cho kham', -- Cho kham, Da kham, Da huy
    FOREIGN KEY (MaBenhNhan) REFERENCES BenhNhan(MaBenhNhan),
    FOREIGN KEY (MaBacSi) REFERENCES NguoiDung(MaNguoiDung)
);

-- ==========================================
-- 4. HO SO BENH AN & CHAN DOAN
-- ==========================================

CREATE TABLE HoSoBenhAn (
    MaHoSo INT IDENTITY(1,1) PRIMARY KEY,
    MaBenhNhan INT NOT NULL,
    NgayTao DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (MaBenhNhan) REFERENCES BenhNhan(MaBenhNhan)
);

CREATE TABLE ChanDoan (
    MaChanDoan INT IDENTITY(1,1) PRIMARY KEY,
    MaLichKham INT NOT NULL,
    MaHoSo INT NOT NULL,
    MaBacSi INT NOT NULL,
    KetQua VARCHAR(MAX) NOT NULL,
    NgayKham DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (MaLichKham) REFERENCES LichKham(MaLichKham),
    FOREIGN KEY (MaHoSo) REFERENCES HoSoBenhAn(MaHoSo),
    FOREIGN KEY (MaBacSi) REFERENCES NguoiDung(MaNguoiDung)
);

-- ==========================================
-- 5. KHO THUOC & DON THUOC
-- ==========================================

CREATE TABLE Thuoc (
    MaThuoc INT IDENTITY(1,1) PRIMARY KEY,
    TenThuoc VARCHAR(100) NOT NULL,
    DonVi VARCHAR(20) NOT NULL,
    SoLuongTon INT DEFAULT 0,
    DonGia DECIMAL(18,2) NOT NULL
);

CREATE TABLE DonThuoc (
    MaDonThuoc INT IDENTITY(1,1) PRIMARY KEY,
    MaChanDoan INT NOT NULL,
    MaBacSi INT NOT NULL,
    NgayKe DATETIME DEFAULT GETDATE(),
    TrangThai VARCHAR(50) DEFAULT 'Chua cap phat', -- Chua cap phat, Da cap phat
    FOREIGN KEY (MaChanDoan) REFERENCES ChanDoan(MaChanDoan),
    FOREIGN KEY (MaBacSi) REFERENCES NguoiDung(MaNguoiDung)
);

CREATE TABLE ChiTietDonThuoc (
    MaChiTiet INT IDENTITY(1,1) PRIMARY KEY,
    MaDonThuoc INT NOT NULL,
    MaThuoc INT NOT NULL,
    SoLuong INT NOT NULL,
    HuongDan VARCHAR(MAX),
    FOREIGN KEY (MaDonThuoc) REFERENCES DonThuoc(MaDonThuoc),
    FOREIGN KEY (MaThuoc) REFERENCES Thuoc(MaThuoc)
);

-- ==========================================
-- 6. QUAN LY NOI TRU
-- ==========================================

CREATE TABLE NoiTru (
    MaNoiTru INT IDENTITY(1,1) PRIMARY KEY,
    MaBenhNhan INT NOT NULL,
    PhongGiuong VARCHAR(100), -- Do Quan ly xap sep
    NgayNhapVien DATETIME DEFAULT GETDATE(),
    NgayXuatVien DATETIME, -- Quan ly xac nhan
    TrangThai VARCHAR(50) DEFAULT 'Dang nam vien', -- Dang nam vien, Da xuat vien
    FOREIGN KEY (MaBenhNhan) REFERENCES BenhNhan(MaBenhNhan)
);

-- ==========================================
-- 7. HOA DON & THANH TOAN
-- ==========================================

CREATE TABLE HoaDon (
    MaHoaDon INT IDENTITY(1,1) PRIMARY KEY,
    MaBenhNhan INT NOT NULL,
    NguoiTao INT NOT NULL, -- MaNguoiDung cua Ke toan
    TongTien DECIMAL(18,2) NOT NULL,
    NgayLap DATETIME DEFAULT GETDATE(),
    TrangThai VARCHAR(50) DEFAULT 'Chua thanh toan', -- Chua thanh toan, Da thanh toan
    LoaiHoaDon VARCHAR(50), -- Kham benh, Thuoc, Noi tru
    FOREIGN KEY (MaBenhNhan) REFERENCES BenhNhan(MaBenhNhan),
    FOREIGN KEY (NguoiTao) REFERENCES NguoiDung(MaNguoiDung)
);