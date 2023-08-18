package com.example.dao;

import com.example.model.ChiTietDonNhapHang;
import com.example.model.DonNhapHang;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DonNhapHangDAO {

    private static String CLASS_NAME = DonNhapHang.class.getName();
    
    public List<DonNhapHang> getAllDonNhapHang() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        String sql = "select dnh from " + CLASS_NAME + " dnh";
        List<DonNhapHang> dsDonNhapHang = null;
        try {
            transaction.begin();
            dsDonNhapHang = session.createQuery(sql, DonNhapHang.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsDonNhapHang;
    }

    public  boolean addDonNhapHang(DonNhapHang donNhapHang) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(donNhapHang);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  boolean updateDonNhapHang(DonNhapHang donNhapHang) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(donNhapHang);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  boolean deleteDonNhapHang(DonNhapHang donNhapHang) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.delete(donNhapHang);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

}
