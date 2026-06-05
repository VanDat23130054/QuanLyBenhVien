package main;

import dao.*;
import model.*;

import java.time.LocalDateTime;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        System.out.println("=== Ứng dụng Quản lý Bệnh viện ===\n");

        // Test DAO classes
        testVaiTroDAO();
        testNguoiDungDAO();
        testThuocDAO();
    }

    private static void testVaiTroDAO() {
        System.out.println(">>> Test VaiTroDAO <<<");
        VaiTroDAO vaiTroDAO = DAOFactory.getVaiTroDAO();
        
        // Get all
        List<VaiTro> allVaiTro = vaiTroDAO.getAll();
        System.out.println("Tất cả vai trò: " + allVaiTro.size() + " records");
        for (VaiTro vt : allVaiTro) {
            System.out.println("  - " + vt);
        }
        System.out.println();
    }

    private static void testNguoiDungDAO() {
        System.out.println(">>> Test NguoiDungDAO <<<");
        NguoiDungDAO nguoiDungDAO = DAOFactory.getNguoiDungDAO();
        
        // Get all
        List<NguoiDung> allUsers = nguoiDungDAO.getAll();
        System.out.println("Tất cả người dùng: " + allUsers.size() + " records");
        for (NguoiDung user : allUsers) {
            System.out.println("  - " + user);
        }
        System.out.println();
    }

    private static void testThuocDAO() {
        System.out.println(">>> Test ThuocDAO <<<");
        ThuocDAO thuocDAO = DAOFactory.getThuocDAO();
        
        // Get all
        List<Thuoc> allThuoc = thuocDAO.getAll();
        System.out.println("Tất cả thuốc: " + allThuoc.size() + " records");
        for (Thuoc thuoc : allThuoc) {
            System.out.println("  - " + thuoc);
        }
        System.out.println();
    }
}