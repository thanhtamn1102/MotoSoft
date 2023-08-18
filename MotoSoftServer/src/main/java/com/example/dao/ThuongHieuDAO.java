package com.example.dao;

import com.example.model.ThuongHieu;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ThuongHieuDAO {

    private static String className = ThuongHieu.class.getName();
    
    public List<ThuongHieu> getAllThuongHieu() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        List<ThuongHieu> dsResult = null;
        try {
            transaction.begin();
            String selectQuery = "select th from " + className + " th";
            dsResult = session.createQuery(selectQuery, ThuongHieu.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsResult;
    }

    public  ThuongHieu getThuongHieu(int brandId) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        ThuongHieu result = null;
        try {
            transaction.begin();
            result = session.find(ThuongHieu.class, brandId);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return result;
    }

    public  boolean addThuongHieu(ThuongHieu thuongHieu) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.persist(thuongHieu);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  boolean updateThuongHieu(ThuongHieu thuongHieu) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(thuongHieu);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  boolean deleteThuongHieu(ThuongHieu thuongHieu) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.remove(thuongHieu);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

}
