package com.example.utlis;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;

public class IDUtils implements Serializable {
    
    public String createMaSanPham() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Integer maxId = (Integer) session.createNativeQuery("select convert(INT, max(right(maSanPham,8))) from SanPham").getSingleResult();
        transaction.commit();
        String id = "SP";
        if(maxId == null)
            id += "00000001";
        else {
            maxId += 1;
            if(maxId >= 0 && maxId <= 9)
                id += "0000000";
            else if(maxId >= 10 && maxId <= 99)
                id += "000000";
            else if(maxId >= 100 && maxId <= 999)
                id += "00000";
            else if(maxId >= 1000 && maxId <= 9999)
                id += "0000";
            else if(maxId >= 10000 && maxId <= 99999)
                id += "000";
            else if(maxId >= 100000 && maxId <= 999999)
                id += "00";
            else if(maxId >= 1000000 && maxId <= 9999999)
                id += "0";
            id += maxId;
        }
        return id;
    }

    public String createMaDanhMuc() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Integer maxId = (Integer) session.createNativeQuery("select convert(INT, max(right(maDanhMuc,8))) from DanhMuc").getSingleResult();
        transaction.commit();
        String id = "DM";
        if(maxId == null)
            id += "00000001";
        else {
            maxId += 1;
            if(maxId >= 0 && maxId <= 9)
                id += "0000000";
            else if(maxId >= 10 && maxId <= 99)
                id += "000000";
            else if(maxId >= 100 && maxId <= 999)
                id += "00000";
            else if(maxId >= 1000 && maxId <= 9999)
                id += "0000";
            else if(maxId >= 10000 && maxId <= 99999)
                id += "000";
            else if(maxId >= 100000 && maxId <= 999999)
                id += "00";
            else if(maxId >= 1000000 && maxId <= 9999999)
                id += "0";
            id += maxId;
        }
        return id;
    }

    public String createMaPhieuKiemKe() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Integer maxId = (Integer) session.createNativeQuery("select convert(INT, max(right(maPhieuKiemKe,7))) from PhieuKiemKe").getSingleResult();
        transaction.commit();
        String id = "PKK";
        if(maxId == null)
            id += "0000001";
        else {
            maxId += 1;
            if(maxId >= 0 && maxId <= 9)
                id += "000000";
            else if(maxId >= 10 && maxId <= 99)
                id += "00000";
            else if(maxId >= 100 && maxId <= 999)
                id += "0000";
            else if(maxId >= 1000 && maxId <= 9999)
                id += "000";
            else if(maxId >= 10000 && maxId <= 99999)
                id += "00";
            else if(maxId >= 100000 && maxId <= 999999)
                id += "0";
            id += maxId;
        }
        return id;
    }

    public String createMaDonNhapHang() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Integer maxId = (Integer) session.createNativeQuery("select convert(INT, max(right(maDonNhapHang,7))) from DonNhapHang").getSingleResult();
        transaction.commit();
        String id = "DNH";
        if(maxId == null)
            id += "0000001";
        else {
            maxId += 1;
            if(maxId >= 0 && maxId <= 9)
                id += "000000";
            else if(maxId >= 10 && maxId <= 99)
                id += "00000";
            else if(maxId >= 100 && maxId <= 999)
                id += "0000";
            else if(maxId >= 1000 && maxId <= 9999)
                id += "000";
            else if(maxId >= 10000 && maxId <= 99999)
                id += "00";
            else if(maxId >= 100000 && maxId <= 999999)
                id += "0";
            id += maxId;
        }
        return id;
    }

    public String createMaPhieuNhapHang() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Integer maxId = (Integer) session.createNativeQuery("select convert(INT, max(right(maPhieuNhapHang,7))) from PhieuNhapHang").getSingleResult();
        transaction.commit();
        String id = "PNH";
        if(maxId == null)
            id += "0000001";
        else {
            maxId += 1;
            if(maxId >= 0 && maxId <= 9)
                id += "000000";
            else if(maxId >= 10 && maxId <= 99)
                id += "00000";
            else if(maxId >= 100 && maxId <= 999)
                id += "0000";
            else if(maxId >= 1000 && maxId <= 9999)
                id += "000";
            else if(maxId >= 10000 && maxId <= 99999)
                id += "00";
            else if(maxId >= 100000 && maxId <= 999999)
                id += "0";
            id += maxId;
        }
        return id;
    }

    public String createMaDonHang() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Integer maxId = (Integer) session.createNativeQuery("select convert(INT, max(right(maDonHang,8))) from DonHang").getSingleResult();
        transaction.commit();
        String id = "DH";
        if(maxId == null)
            id += "00000001";
        else {
            maxId += 1;
            if(maxId >= 0 && maxId <= 9)
                id += "0000000";
            else if(maxId >= 10 && maxId <= 99)
                id += "000000";
            else if(maxId >= 100 && maxId <= 999)
                id += "00000";
            else if(maxId >= 1000 && maxId <= 9999)
                id += "0000";
            else if(maxId >= 10000 && maxId <= 99999)
                id += "000";
            else if(maxId >= 100000 && maxId <= 999999)
                id += "00";
            else if(maxId >= 1000000 && maxId <= 9999999)
                id += "0";
            id += maxId;
        }
        return id;
    }

    public String createMaNhanVien() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Integer maxId = (Integer) session.createNativeQuery("select convert(INT, max(right(maNhanVien,6))) from NhanVien").getSingleResult();
        transaction.commit();
        String id = "";
        if(maxId == null)
            id += "00000001";
        else {
            maxId += 1;
            if(maxId >= 0 && maxId <= 9)
                id += "0000000";
            else if(maxId >= 10 && maxId <= 99)
                id += "000000";
            else if(maxId >= 100 && maxId <= 999)
                id += "00000";
            else if(maxId >= 1000 && maxId <= 9999)
                id += "0000";
            else if(maxId >= 10000 && maxId <= 99999)
                id += "000";
            else if(maxId >= 100000 && maxId <= 999999)
                id += "00";
            else if(maxId >= 1000000 && maxId <= 9999999)
                id += "0";
            id += maxId;
        }
        return id;
    }

}
