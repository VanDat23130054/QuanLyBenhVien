# Module Phúc Nguyễn - QuanLyBenhVien

Module này code 5 chức năng theo use case của **Phúc Nguyễn**:

1. Đăng nhập
2. Đăng kí
3. Đặt lịch khám
4. Xem lịch khám
5. Thanh toán

## Các file đã thêm

```text
src/main/java/com/httt/quanlybenhvien/Main.java
src/main/java/com/httt/quanlybenhvien/feature/phucnguyen/PhucNguyenConsoleApp.java
src/main/java/com/httt/quanlybenhvien/feature/phucnguyen/PhucNguyenService.java
src/main/java/com/httt/quanlybenhvien/repository/impl/TaiKhoanRepository.java
src/main/java/com/httt/quanlybenhvien/repository/impl/BenhNhanRepository.java
src/main/java/com/httt/quanlybenhvien/repository/impl/NhanVienRepository.java
src/main/java/com/httt/quanlybenhvien/repository/impl/LichKhamRepository.java
src/main/java/com/httt/quanlybenhvien/repository/impl/HoaDonRepository.java
```

## Luồng xử lý

- **Đăng nhập:** kiểm tra bảng `TaiKhoan`, chỉ cho phép tài khoản loại `Benh Nhan` đăng nhập vào module này.
- **Đăng kí:** tạo mới `TaiKhoan`, sau đó tạo hồ sơ `BenhNhan`.
- **Đặt lịch khám:** bệnh nhân chọn bác sĩ, nhập ngày khám, hệ thống tạo `LichKham` và tự tạo `HoaDon` đặt lịch.
- **Xem lịch khám:** lấy danh sách lịch khám theo `maBenhNhan`.
- **Thanh toán:** hiển thị hóa đơn của bệnh nhân và xác nhận thanh toán.

## Cách chạy

1. Chạy file SQL trong thư mục `database` theo thứ tự:
   - `SetupUserSA.sql` nếu cần cấu hình SQL Server.
   - `CreateDatabase.sql`
   - `ImportData.sql`
2. Sửa thông tin kết nối trong:

```text
src/main/resources/application.properties
```

3. Build project:

```bash
mvn clean package
```

4. Chạy chương trình console:

```bash
mvn exec:java -Dexec.mainClass="com.httt.quanlybenhvien.Main"
```

Hoặc chạy class `com.httt.quanlybenhvien.Main` trực tiếp trong IDE.

## Tài khoản test có sẵn

```text
Tên đăng nhập: patient_an
Mật khẩu: hash_pass_123
```

```text
Tên đăng nhập: patient_binh
Mật khẩu: hash_pass_123
```

```text
Tên đăng nhập: patient_cuong
Mật khẩu: hash_pass_123
```

## Ghi chú

Chức năng thanh toán chỉ hiển thị hóa đơn chưa thanh toán, yêu cầu người dùng xác nhận `Y/N`, chọn phương thức `Tiền mặt` hoặc `Chuyển khoản`, sau đó mới cập nhật `trangThaiThanhToan`, `ngayThanhToan` và `phuongThucThanhToan` trong database.
