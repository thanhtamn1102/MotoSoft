package com.example.dao;

import com.example.model.NhanVien;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class NhanVienDAO {

    private static String CLASS_NAME = NhanVien.class.getName();

    public List<NhanVien> getAllNhanVien() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        List<NhanVien> dsNhanVien = null;
        String sql = "select nv from " + NhanVien.class.getName() + " nv";
        try {
            transaction.begin();
            dsNhanVien = session.createQuery(sql, NhanVien.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dsNhanVien;
    }

    public NhanVien getNhanVien(String maNhanVien) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        NhanVien nhanVien = null;
        try {
            transaction.begin();
            nhanVien = session.find(NhanVien.class, maNhanVien);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return nhanVien;
    }

    public boolean addNhanVien(NhanVien nhanVien) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(nhanVien);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public boolean updateNhanVien(NhanVien nhanVien) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.update(nhanVien);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public boolean deleteNhanVien(NhanVien nhanVien) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.delete(nhanVien);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }
}
