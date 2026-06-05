package service;

import dao.DAOFactory;
import dao.DonThuocDAO;
import dao.ChiTietDonThuocDAO;
import dao.ThuocDAO;
import model.DonThuoc;
import model.ChiTietDonThuoc;
import model.Thuoc;
import util.Constants;

import java.util.List;

public class ThuocService {
    private ThuocDAO thuocDAO;
    private DonThuocDAO donThuocDAO;
    private ChiTietDonThuocDAO chiTietDonThuocDAO;

    public ThuocService() {
        this.thuocDAO = DAOFactory.getThuocDAO();
        this.donThuocDAO = DAOFactory.getDonThuocDAO();
        this.chiTietDonThuocDAO = DAOFactory.getChiTietDonThuocDAO();
    }

    /**
     * Lấy danh sách tất cả thuốc
     */
    public List<Thuoc> getAllMedicine() {
        return thuocDAO.getAll();
    }

    /**
     * Lấy danh sách thuốc có sẵn
     */
    public List<Thuoc> getAvailableMedicine() {
        return thuocDAO.getAvailableMedicine();
    }

    /**
     * Tìm kiếm thuốc theo tên
     */
    public List<Thuoc> searchMedicine(String name) {
        return thuocDAO.searchByName(name);
    }

    /**
     * Thêm thuốc mới
     */
    public boolean addMedicine(Thuoc medicine) {
        if (thuocDAO.insert(medicine)) {
            System.out.println("Thêm thuốc thành công!");
            return true;
        }
        System.out.println("Lỗi khi thêm thuốc!");
        return false;
    }

    /**
     * Cập nhật thông tin thuốc
     */
    public boolean updateMedicine(Thuoc medicine) {
        if (thuocDAO.update(medicine)) {
            System.out.println("Cập nhật thuốc thành công!");
            return true;
        }
        return false;
    }

    /**
     * Tạo đơn thuốc mới
     */
    public DonThuoc createPrescription(int diagnosisId, int doctorId) {
        DonThuoc prescription = new DonThuoc(diagnosisId, doctorId);
        
        if (donThuocDAO.insert(prescription)) {
            System.out.println("Tạo đơn thuốc thành công!");
            return prescription;
        }
        
        return null;
    }

    /**
     * Thêm chi tiết đơn thuốc
     */
    public boolean addPrescriptionDetail(int prescriptionId, int medicineId, int quantity, String instruction) {
        // Kiểm tra tồn kho
        Thuoc medicine = thuocDAO.getById(medicineId);
        if (medicine == null || medicine.getSoLuongTon() < quantity) {
            System.out.println("Số lượng thuốc không đủ!");
            return false;
        }

        ChiTietDonThuoc detail = new ChiTietDonThuoc(prescriptionId, medicineId, quantity, instruction);
        
        if (chiTietDonThuocDAO.insert(detail)) {
            // Giảm tồn kho
            thuocDAO.updateQuantity(medicineId, quantity);
            System.out.println("Thêm chi tiết đơn thuốc thành công!");
            return true;
        }
        
        return false;
    }

    /**
     * Lấy danh sách chi tiết đơn thuốc
     */
    public List<ChiTietDonThuoc> getPrescriptionDetails(int prescriptionId) {
        return chiTietDonThuocDAO.getByDonThuoc(prescriptionId);
    }

    /**
     * Lấy đơn thuốc theo chẩn đoán
     */
    public List<DonThuoc> getPrescriptionsByDiagnosis(int diagnosisId) {
        return donThuocDAO.getByChanDoan(diagnosisId);
    }

    /**
     * Cập nhật trạng thái đơn thuốc
     */
    public boolean updatePrescriptionStatus(int prescriptionId, String status) {
        DonThuoc prescription = donThuocDAO.getById(prescriptionId);
        
        if (prescription == null) {
            System.out.println("Đơn thuốc không tồn tại!");
            return false;
        }
        
        prescription.setTrangThai(status);
        return donThuocDAO.update(prescription);
    }

    /**
     * Xóa thuốc
     */
    public boolean deleteMedicine(int medicineId) {
        return thuocDAO.delete(medicineId);
    }

    /**
     * Kiểm tra tồn kho thuốc
     */
    public int checkMedicineStock(int medicineId) {
        Thuoc medicine = thuocDAO.getById(medicineId);
        return medicine != null ? medicine.getSoLuongTon() : 0;
    }
}