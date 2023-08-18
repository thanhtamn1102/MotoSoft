package com.example.dao;

import com.example.model.DanhMuc;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DanhMucDAO {
    
    private static String className = DanhMuc.class.getName();
    
    public List<DanhMuc> getAllCategory() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        List<DanhMuc> dsDanhMuc = null;
        try {
            transaction.begin();
            String selectQuery = "select dm from " + className + " dm";
            dsDanhMuc = session.createQuery(selectQuery, DanhMuc.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsDanhMuc;
    }

    public boolean addCategory(DanhMuc danhMuc) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.persist(danhMuc);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public boolean updateCategory(DanhMuc danhMuc) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(danhMuc);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean removeCategory(DanhMuc danhMuc) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.remove(danhMuc);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public DanhMuc getCategoryById(String category_id) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        DanhMuc danhMuc = null;
        try {
            transaction.begin();
            danhMuc = session.find(DanhMuc.class, category_id);
            transaction.commit();
        } catch(Exception ex)
        {
            ex.printStackTrace();
            transaction.rollback();
        }
        return danhMuc;
    }

}
