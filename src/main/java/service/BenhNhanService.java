package service;

import dao.DAOFactory;
import dao.BenhNhanDAO;
import dao.LichKhamDAO;
import dao.HoSoBenhAnDAO;
import model.BenhNhan;
import model.LichKham;
import model.HoSoBenhAn;
import util.Constants;

import java.time.LocalDateTime;
import java.util.List;

public class BenhNhanService {
    private BenhNhanDAO benhNhanDAO;
    private LichKhamDAO lichKhamDAO;
    private HoSoBenhAnDAO hoSoBenhAnDAO;

    public BenhNhanService() {
        this.benhNhanDAO = DAOFactory.getBenhNhanDAO();
        this.lichKhamDAO = DAOFactory.getLichKhamDAO();
        this.hoSoBenhAnDAO = DAOFactory.getHoSoBenhAnDAO();
    }

    /**
     * Lấy danh sách tất cả bệnh nhân
     */
    public List<BenhNhan> getAllPatients() {
        return benhNhanDAO.getAll();
    }

    /**
     * Tìm kiếm bệnh nhân theo tên
     */
    public List<BenhNhan> searchPatients(String name) {
        return benhNhanDAO.searchByName(name);
    }

    /**
     * Tạo hồ sơ mới cho bệnh nhân
     */
    public boolean createPatientRecord(BenhNhan patient) {
        if (benhNhanDAO.insert(patient)) {
            // Tự động tạo hồ sơ bệnh án
            HoSoBenhAn hoSo = new HoSoBenhAn(patient.getMaBenhNhan());
            hoSoBenhAnDAO.insert(hoSo);
            System.out.println("Tạo hồ sơ bệnh nhân thành công!");
            return true;
        }
        return false;
    }

    /**
     * Cập nhật thông tin bệnh nhân
     */
    public boolean updatePatient(BenhNhan patient) {
        if (benhNhanDAO.update(patient)) {
            System.out.println("Cập nhật thông tin bệnh nhân thành công!");
            return true;
        }
        return false;
    }

    /**
     * Lấy lịch khám của bệnh nhân
     */
    public List<LichKham> getPatientAppointments(int patientId) {
        return lichKhamDAO.getByBenhNhan(patientId);
    }

    /**
     * Đặt lịch khám mới
     */
    public boolean scheduleAppointment(int patientId, LocalDateTime appointmentTime) {
        LichKham appointment = new LichKham(patientId, 0, appointmentTime);
        
        if (lichKhamDAO.insert(appointment)) {
            System.out.println("Đặt lịch khám thành công!");
            return true;
        }
        
        System.out.println("Lỗi khi đặt lịch khám!");
        return false;
    }

    /**
     * Hủy lịch khám
     */
    public boolean cancelAppointment(int appointmentId) {
        LichKham appointment = lichKhamDAO.getById(appointmentId);
        
        if (appointment == null) {
            System.out.println("Lịch khám không tồn tại!");
            return false;
        }
        
        if (Constants.APPOINTMENT_CANCELLED.equals(appointment.getTrangThai())) {
            System.out.println("Lịch khám này đã được hủy!");
            return false;
        }
        
        appointment.setTrangThai(Constants.APPOINTMENT_CANCELLED);
        return lichKhamDAO.update(appointment);
    }

    /**
     * Lấy hồ sơ bệnh án của bệnh nhân
     */
    public HoSoBenhAn getPatientMedicalRecord(int patientId) {
        return hoSoBenhAnDAO.getByBenhNhan(patientId);
    }

    /**
     * Xóa bệnh nhân
     */
    public boolean deletePatient(int patientId) {
        return benhNhanDAO.delete(patientId);
    }
}