package service;

import dao.DAOFactory;
import dao.NguoiDungDAO;
import model.NguoiDung;
import util.Constants;

import java.util.List;

public class AuthenticationService {
    private NguoiDungDAO nguoiDungDAO;

    public AuthenticationService() {
        this.nguoiDungDAO = DAOFactory.getNguoiDungDAO();
    }

    /**
     * Xác thực đăng nhập
     */
    public NguoiDung login(String username, String password) {
        NguoiDung user = nguoiDungDAO.getByUsername(username);
        
        if (user == null) {
            System.out.println("Tên đăng nhập không tồn tại!");
            return null;
        }
        
        if (!user.getMatKhau().equals(password)) {
            System.out.println("Mật khẩu không chính xác!");
            return null;
        }
        
        if (!user.isTrangThai()) {
            System.out.println("Tài khoản này đã bị khóa!");
            return null;
        }
        
        System.out.println("Đăng nhập thành công! Chào " + user.getHoTen());
        return user;
    }

    /**
     * Đăng ký tài khoản mới
     */
    public boolean register(String username, String password, String hoTen, String email, String soDienThoai, int roleId) {
        // Kiểm tra xem username đã tồn tại chưa
        NguoiDung existingUser = nguoiDungDAO.getByUsername(username);
        if (existingUser != null) {
            System.out.println("Tên đăng nhập đã tồn tại!");
            return false;
        }
        
        NguoiDung newUser = new NguoiDung(username, password, roleId, hoTen, soDienThoai, email);
        
        if (nguoiDungDAO.insert(newUser)) {
            System.out.println("Đăng ký thành công!");
            return true;
        } else {
            System.out.println("Lỗi khi đăng ký!");
            return false;
        }
    }

    /**
     * Đổi mật khẩu
     */
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        NguoiDung user = nguoiDungDAO.getById(userId);
        
        if (user == null) {
            System.out.println("Người dùng không tồn tại!");
            return false;
        }
        
        if (!user.getMatKhau().equals(oldPassword)) {
            System.out.println("Mật khẩu cũ không chính xác!");
            return false;
        }
        
        user.setMatKhau(newPassword);
        if (nguoiDungDAO.update(user)) {
            System.out.println("Đổi mật khẩu thành công!");
            return true;
        } else {
            System.out.println("Lỗi khi đổi mật khẩu!");
            return false;
        }
    }

    /**
     * Lấy danh sách người dùng theo vai trò
     */
    public List<NguoiDung> getUsersByRole(int roleId) {
        return nguoiDungDAO.getByRole(roleId);
    }

    /**
     * Khóa/Mở khóa tài khoản
     */
    public boolean toggleUserStatus(int userId) {
        NguoiDung user = nguoiDungDAO.getById(userId);
        
        if (user == null) {
            System.out.println("Người dùng không tồn tại!");
            return false;
        }
        
        user.setTrangThai(!user.isTrangThai());
        
        if (nguoiDungDAO.update(user)) {
            String status = user.isTrangThai() ? "hoạt động" : "khóa";
            System.out.println("Tài khoản đã được " + status);
            return true;
        }
        
        return false;
    }
}