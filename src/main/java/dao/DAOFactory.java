package dao;

/**
 * Simple factory to obtain DAO instances. Keeps construction centralized.
 * Add new DAOs here as the project grows.
 */
public class DAOFactory {

    private DAOFactory() {}

    public static BenhNhanDAO getBenhNhanDAO() {
        return new BenhNhanDAO();
    }

    public static ChamCongDAO getChamCongDAO() {
        return new ChamCongDAO();
    }

    public static ChanDoanDAO getChanDoanDAO() {
        return new ChanDoanDAO();
    }

    public static ChiTietDonThuocDAO getChiTietDonThuocDAO() {
        return new ChiTietDonThuocDAO();
    }

    public static DonThuocDAO getDonThuocDAO() {
        return new DonThuocDAO();
    }

    public static HoaDonDAO getHoaDonDAO() {
        return new HoaDonDAO();
    }

    public static HoSoBenhAnDAO getHoSoBenhAnDAO() {
        return new HoSoBenhAnDAO();
    }

    public static LichKhamDAO getLichKhamDAO() {
        return new LichKhamDAO();
    }

    public static LichTrucDAO getLichTrucDAO() {
        return new LichTrucDAO();
    }

    public static NguoiDungDAO getNguoiDungDAO() {
        return new NguoiDungDAO();
    }

    public static NoiTruDAO getNoiTruDAO() {
        return new NoiTruDAO();
    }

    public static ThuocDAO getThuocDAO() {
        return new ThuocDAO();
    }

    public static TinhLuongDAO getTinhLuongDAO() {
        return new TinhLuongDAO();
    }

    public static VaiTroDAO getVaiTroDAO() {
        return new VaiTroDAO();
    }
}
