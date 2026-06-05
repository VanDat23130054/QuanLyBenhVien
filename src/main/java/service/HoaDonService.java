package service;

import dao.DAOFactory;
import dao.HoaDonDAO;
import dao.BenhNhanDAO;
import model.HoaDon;
import util.Constants;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class HoaDonService {
    private HoaDonDAO hoaDonDAO;
    private BenhNhanDAO benhNhanDAO;

    public HoaDonService() {
        this.hoaDonDAO = DAOFactory.getHoaDonDAO();
        this.benhNhanDAO = DAOFactory.getBenhNhanDAO();
    }

    /**
     * Lấy danh sách tất cả hóa đơn
     */
    public List<HoaDon> getAllInvoices() {
        return hoaDonDAO.getAll();
    }

    /**
     * Lấy hóa đơn của bệnh nhân
     */
    public List<HoaDon> getPatientInvoices(int patientId) {
        return hoaDonDAO.getByBenhNhan(patientId);
    }

    /**
     * Lấy hóa đơn chưa thanh toán
     */
    public List<HoaDon> getUnpaidInvoices() {
        return hoaDonDAO.getByStatus(Constants.INVOICE_UNPAID);
    }

    /**
     * Lấy hóa đơn đã thanh toán
     */
    public List<HoaDon> getPaidInvoices() {
        return hoaDonDAO.getByStatus(Constants.INVOICE_PAID);
    }

    /**
     * Tạo hóa đơn mới
     */
    public boolean createInvoice(int patientId, int createdBy, BigDecimal totalAmount, String invoiceType) {
        HoaDon invoice = new HoaDon(patientId, createdBy, totalAmount, invoiceType);
        
        if (hoaDonDAO.insert(invoice)) {
            System.out.println("Tạo hóa đơn thành công!");
            return true;
        }
        
        System.out.println("Lỗi khi tạo hóa đơn!");
        return false;
    }

    /**
     * Cập nhật hóa đơn
     */
    public boolean updateInvoice(HoaDon invoice) {
        return hoaDonDAO.update(invoice);
    }

    /**
     * Thanh toán hóa đơn
     */
    public boolean payInvoice(int invoiceId) {
        HoaDon invoice = hoaDonDAO.getById(invoiceId);
        
        if (invoice == null) {
            System.out.println("Hóa đơn không tồn tại!");
            return false;
        }
        
        if (Constants.INVOICE_PAID.equals(invoice.getTrangThai())) {
            System.out.println("Hóa đơn này đã được thanh toán!");
            return false;
        }
        
        invoice.setTrangThai(Constants.INVOICE_PAID);
        
        if (hoaDonDAO.update(invoice)) {
            System.out.println("Thanh toán hóa đơn thành công!");
            return true;
        }
        
        return false;
    }

    /**
     * Tính tổng doanh thu
     */
    public BigDecimal calculateTotalRevenue() {
        List<HoaDon> paidInvoices = getPaidInvoices();
        BigDecimal total = BigDecimal.ZERO;
        
        for (HoaDon invoice : paidInvoices) {
            total = total.add(invoice.getTongTien());
        }
        
        return total;
    }

    /**
     * Tính tổng nợ
     */
    public BigDecimal calculateTotalDebt() {
        List<HoaDon> unpaidInvoices = getUnpaidInvoices();
        BigDecimal total = BigDecimal.ZERO;
        
        for (HoaDon invoice : unpaidInvoices) {
            total = total.add(invoice.getTongTien());
        }
        
        return total;
    }

    /**
     * Xóa hóa đơn
     */
    public boolean deleteInvoice(int invoiceId) {
        return hoaDonDAO.delete(invoiceId);
    }

    /**
     * Lấy hóa đơn theo loại
     */
    public List<HoaDon> getInvoicesByType(String type) {
        return hoaDonDAO.getByInvoiceType(type);
    }
}