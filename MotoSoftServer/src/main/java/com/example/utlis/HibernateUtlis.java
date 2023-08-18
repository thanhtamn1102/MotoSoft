package com.example.utlis;

import com.example.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtlis {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory buildSessionFactory() {
        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();

            Metadata metadata = new MetadataSources(serviceRegistry)
                    .addAnnotatedClasses(SanPham.class, KichThuoc.class, DanhMuc.class, MauSac.class, ThuongHieu.class)
                    .addAnnotatedClasses(NhanVien.class, TaiKhoan.class, ChucVu.class)
                    .addAnnotatedClasses(VaiTro.class, VaiTroNhomQuyen.class, VaiTroNhomQuyenID.class, VaiTroQuyen.class, VaiTroQuyenID.class, NhomQuyen.class, Quyen.class)
                    .addAnnotatedClasses(PhieuKiemKe.class, ChiTietPhieuKiem.class, ChiTietPhieuKiemID.class)
                    .addAnnotatedClasses(DonNhapHang.class, NhaCungCap.class, ChiTietDonNhapHang.class, ChiTietDonNhapHangID.class)
                    .addAnnotatedClasses(KhachHang.class, DonHang.class, ChiTietDonHang.class, ChiTietDonHangID.class)
                    .addAnnotatedClasses(PhieuNhapHang.class, ChiTietPhieuNhapHang.class, ChiTietPhieuNhapHangID.class)
                    .addAnnotatedClasses(TinhTP.class, QuanHuyen.class, XaPhuong.class)
                    .addAnnotatedClasses(PhieuTraHang.class)
                    .getMetadataBuilder()
                    .build();

            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void close() {
        getSessionFactory().close();
    }

    public static Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
