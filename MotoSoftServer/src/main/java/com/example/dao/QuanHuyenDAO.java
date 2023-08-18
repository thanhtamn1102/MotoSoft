package com.example.dao;

import com.example.model.QuanHuyen;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class QuanHuyenDAO {

    private static String CLASS_NAME = QuanHuyen.class.getName();
    
    public boolean addQuanHuyen(QuanHuyen quanHuyen) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.persist(quanHuyen);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public boolean mergeQuanHuyen(QuanHuyen quanHuyen) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(quanHuyen);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public List<QuanHuyen> getAllQuanHuyen() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        String sql = "select q from " + CLASS_NAME + " q";
        List<QuanHuyen> result = null;
        try {
            transaction.begin();
            result = session.createQuery(sql, QuanHuyen.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return result;
    }

    public List<QuanHuyen> getQuanHuyenInTinhTP(String maTinhTP) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        String sql = "select qh from " + CLASS_NAME + " qh where qh.tinhTP='" + maTinhTP + "'";
        List<QuanHuyen> result = null;
        try {
            transaction.begin();
            result = session.createQuery(sql, QuanHuyen.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return result;
    }

    public QuanHuyen getQuanHuyenById(String id) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        QuanHuyen quanHuyen = null;
        try {
            transaction.begin();
            quanHuyen = session.find(QuanHuyen.class, id);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return quanHuyen;
    }

}
