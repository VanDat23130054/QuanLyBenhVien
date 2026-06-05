# Hệ Thống Quản Lý Bệnh Viện

## Mô Tả
Ứng dụng quản lý bệnh viện toàn diện được xây dựng với pattern **DAO (Data Access Object)** để quản lý các khía cạnh của bệnh viện bao gồm:
- Quản lý nhân sự (lịch trực, chấm công, tính lương)
- Quản lý bệnh nhân
- Quản lý lịch khám
- Quản lý chẩn đoán
- Quản lý thuốc và đơn thuốc
- Quản lý nội trú
- Quản lý hóa đơn

## Cấu Trúc Dự Án

```
QuanLyBenhVien/
├── src/main/java/
│   ├── model/           # Entity/Model classes
│   ├── dao/             # Data Access Object classes
│   ├── database/        # Database connection
│   ├── main/            # Application entry point
│   └── util/            # Utility classes
├── database/
│   └── CreateDatabase.sql  # Database schema
└── pom.xml              # Maven configuration
```

## Các Thành Phần Chính

### 1. Model Classes (Entity Classes)
Đại diện cho các đối tượng trong hệ thống:
- `VaiTro.java` - Vai trò người dùng
- `NguoiDung.java` - Người dùng hệ thống
- `BenhNhan.java` - Bệnh nhân
- `LichKham.java` - Lịch khám bệnh
- `HoSoBenhAn.java` - Hồ sơ bệnh án
- `ChanDoan.java` - Chẩn đoán
- `Thuoc.java` - Thông tin thuốc
- `DonThuoc.java` - Đơn thuốc
- `ChiTietDonThuoc.java` - Chi tiết đơn thuốc
- `HoaDon.java` - Hóa đơn
- `NoiTru.java` - Nội trú
- `LichTruc.java` - Lịch trực nhân viên
- `ChamCong.java` - Chấm công
- `TinhLuong.java` - Tính lương

### 2. DAO Classes (Data Access Objects)
Xử lý tất cả các thao tác với cơ sở dữ liệu:
- `IBaseDAO.java` - Interface chung cho tất cả DAO
- `VaiTroDAO.java` - Quản lý vai trò
- `NguoiDungDAO.java` - Quản lý người dùng
- `BenhNhanDAO.java` - Quản lý bệnh nhân
- `LichKhamDAO.java` - Quản lý lịch khám
- `HoSoBenhAnDAO.java` - Quản lý hồ sơ bệnh án
- `ChanDoanDAO.java` - Quản lý chẩn đoán
- `ThuocDAO.java` - Quản lý thuốc
- `DonThuocDAO.java` - Quản lý đơn thuốc
- `ChiTietDonThuocDAO.java` - Quản lý chi tiết đơn thuốc
- `HoaDonDAO.java` - Quản lý hóa đơn
- `NoiTruDAO.java` - Quản lý nội trú
- `LichTrucDAO.java` - Quản lý lịch trực
- `ChamCongDAO.java` - Quản lý chấm công
- `TinhLuongDAO.java` - Quản lý tính lương

### 3. Database Connection
- `DatabaseConnection.java` - Quản lý kết nối SQL Server

### 4. Utility Classes
- `Constants.java` - Hằng số của hệ thống
- `InputValidator.java` - Xác thực input từ người dùng

## Yêu Cầu
- Java 11 hoặc cao hơn
- SQL Server
- Maven

## Dependency
```xml
<dependency>
    <groupId>com.microsoft.sqlserver</groupId>
    <artifactId>mssql-jdbc</artifactId>
    <version>12.2.0.jre11</version>
</dependency>
```

## Cấu Hình Cơ Sở Dữ Liệu
Sửa file `DatabaseConnection.java` với thông tin kết nối của bạn:
```java
private static final String SERVER = "localhost";
private static final String DATABASE = "QuanLyBenhVien";
private static final String USER = "sa";
private static final String PASSWORD = "YourStrongPassword123!";
```

## Cách Sử Dụng

### 1. Tạo Cơ Sở Dữ Liệu
Chạy script SQL trong file `database/CreateDatabase.sql` để tạo database.

### 2. Biên Dịch Dự Án
```bash
mvn clean compile
```

### 3. Chạy Ứng Dụng
```bash
mvn exec:java -Dexec.mainClass="main.Application"
```

## Ví Dụ Sử Dụng DAO

### Lấy danh sách tất cả bệnh nhân
```java
BenhNhanDAO benhNhanDAO = new BenhNhanDAO();
List<BenhNhan> danhSach = benhNhanDAO.getAll();
```

### Thêm bệnh nhân mới
```java
BenhNhan benhNhan = new BenhNhan(1, LocalDate.of(1990, 5, 15), "Nam", "123 Đường A", "BH123456");
benhNhanDAO.insert(benhNhan);
```

### Cập nhật thông tin bệnh nhân
```java
benhNhan.setDiaChi("456 Đường B");
benhNhanDAO.update(benhNhan);
```

### Xóa bệnh nhân
```java
benhNhanDAO.delete(benhNhanId);
```

### Tìm kiếm bệnh nhân theo tên
```java
List<BenhNhan> results = benhNhanDAO.searchByName("Nguyễn");
```

## Pattern Được Sử Dụng

### DAO Pattern
- **Tách biệt** logic truy cập dữ liệu khỏi business logic
- **Tái sử dụng** code dễ dàng
- **Bảo trì** dễ hơn
- **Unit test** hiệu quả hơn

### Interface Pattern
- `IBaseDAO<T>` cung cấp các phương thức cơ bản: `getById`, `getAll`, `insert`, `update`, `delete`
- Tất cả DAO classes triển khai interface này

## Cách Mở Rộng

### Thêm DAO Mới
1. Tạo Model class mới nếu cần
2. Tạo DAO class triển khai `IBaseDAO<T>`
3. Implement các phương thức yêu cầu
4. Thêm các phương thức tìm kiếm tùy chỉnh

## Liên Hệ & Hỗ Trợ
Để báo cáo lỗi hoặc đề xuất tính năng, vui lòng liên hệ đội phát triển.

---
**Phiên bản:** 1.0  
**Cập nhật lần cuối:** 2026
