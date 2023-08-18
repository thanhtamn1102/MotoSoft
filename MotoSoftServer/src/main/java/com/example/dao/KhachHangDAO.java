package com.example.dao;

import com.example.model.KhachHang;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class KhachHangDAO {

    private static String className = KhachHang.class.getName();

    public List<KhachHang> getAllCustomer() {
        List<KhachHang> listKhachHang = null;
        String sqlQuery = "Select kh from " + className + " kh";
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            listKhachHang = session.createQuery(sqlQuery, KhachHang.class).getResultList();
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            transaction.rollback();
        }
        return listKhachHang;
    }

    public long addCustomer(KhachHang khachHang) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            Long maKhachHang = (Long) session.save(khachHang);
            transaction.commit();
            return maKhachHang;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            transaction.rollback();
        }
        return -1;
    }

    public boolean updateCustomer(KhachHang khachHang) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(khachHang);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean removeCustomer(KhachHang khachHang) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.remove(khachHang);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
