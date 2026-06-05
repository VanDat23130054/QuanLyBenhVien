package service;

import dao.DAOFactory;
import dao.NoiTruDAO;
import dao.BenhNhanDAO;
import model.NoiTru;
import util.Constants;

import java.time.LocalDateTime;
import java.util.List;

public class NoiTruService {
    private NoiTruDAO noiTruDAO;
    private BenhNhanDAO benhNhanDAO;

    public NoiTruService() {
        this.noiTruDAO = DAOFactory.getNoiTruDAO();
        this.benhNhanDAO = DAOFactory.getBenhNhanDAO();
    }

    /**
     * Lấy danh sách tất cả nội trú
     */
    public List<NoiTru> getAllAdmissions() {
        return noiTruDAO.getAll();
    }

    /**
     * Lấy danh sách bệnh nhân đang nằm viện
     */
    public List<NoiTru> getCurrentAdmittedPatients() {
        return noiTruDAO.getAdmittedPatients();
    }

    /**
     * Nhập viện bệnh nhân mới
     */
    public boolean admitPatient(int patientId, String room) {
        // Kiểm tra bệnh nhân có tồn tại không
        if (benhNhanDAO.getById(patientId) == null) {
            System.out.println("Bệnh nhân không tồn tại!");
            return false;
        }
        
        // Kiểm tra bệnh nhân có đang nằm viện không
        List<NoiTru> admissions = noiTruDAO.getByBenhNhan(patientId);
        for (NoiTru admission : admissions) {
            if (Constants.ADMISSION_ACTIVE.equals(admission.getTrangThai())) {
                System.out.println("Bệnh nhân này đang nằm viện!");
                return false;
            }
        }
        
        NoiTru admission = new NoiTru(patientId, room);
        
        if (noiTruDAO.insert(admission)) {
            System.out.println("Nhập viện thành công!");
            return true;
        }
        
        return false;
    }

    /**
     * Xuất viện bệnh nhân
     */
    public boolean dischargePatient(int admissionId) {
        NoiTru admission = noiTruDAO.getById(admissionId);
        
        if (admission == null) {
            System.out.println("Hồ sơ nội trú không tồn tại!");
            return false;
        }
        
        if (Constants.ADMISSION_DISCHARGED.equals(admission.getTrangThai())) {
            System.out.println("Bệnh nhân này đã xuất viện!");
            return false;
        }
        
        admission.setNgayXuatVien(LocalDateTime.now());
        admission.setTrangThai(Constants.ADMISSION_DISCHARGED);
        
        if (noiTruDAO.update(admission)) {
            System.out.println("Xuất viện thành công!");
            return true;
        }
        
        return false;
    }

    /**
     * Cập nhật phòng giường
     */
    public boolean updateRoom(int admissionId, String newRoom) {
        NoiTru admission = noiTruDAO.getById(admissionId);
        
        if (admission == null) {
            System.out.println("Hồ sơ nội trú không tồn tại!");
            return false;
        }
        
        admission.setPhongGiuong(newRoom);
        return noiTruDAO.update(admission);
    }

    /**
     * Lấy lịch sử nội trú của bệnh nhân
     */
    public List<NoiTru> getPatientAdmissionHistory(int patientId) {
        return noiTruDAO.getByBenhNhan(patientId);
    }

    /**
     * Tính số ngày nằm viện
     */
    public long calculateStayDays(int admissionId) {
        NoiTru admission = noiTruDAO.getById(admissionId);
        
        if (admission == null) {
            return 0;
        }
        
        LocalDateTime endDate = admission.getNgayXuatVien();
        if (endDate == null) {
            endDate = LocalDateTime.now();
        }
        
        return java.time.temporal.ChronoUnit.DAYS.between(
            admission.getNgayNhapVien(),
            endDate
        );
    }

    /**
     * Xóa hồ sơ nội trú
     */
    public boolean deleteAdmission(int admissionId) {
        return noiTruDAO.delete(admissionId);
    }
}