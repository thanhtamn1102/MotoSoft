package com.example.dao;

import com.example.model.TaiKhoan;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TaiKhoanDAO {

    private static String className = TaiKhoan.class.getName();
    
    public  TaiKhoan getTaiKhoan(String maNhanVien) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        TaiKhoan taiKhoan = null;
        try {
            transaction.begin();
            taiKhoan = session.find(TaiKhoan.class, maNhanVien);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
        }
        return taiKhoan;
    }

    public List<TaiKhoan> getAllTaiKhoan() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        List<TaiKhoan> dsTaiKhoan = null;
        String sql = "select tk from " + TaiKhoan.class.getName() + " tk";
        try {
            transaction.begin();
            dsTaiKhoan = session.createQuery(sql, TaiKhoan.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsTaiKhoan;
    }


    public boolean addTaiKhoan(TaiKhoan taiKhoan){
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(taiKhoan);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public boolean updateTaiKhoan(TaiKhoan taiKhoan){
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(taiKhoan);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  boolean deleteTaiKhoan(TaiKhoan taiKhoan){
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.remove(taiKhoan);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

}
