package com.example.dao;

import com.example.model.Quyen;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class QuyenDAO {

    private static String className = Quyen.class.getName();
    
    public List<Quyen> getAllQuyen() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        List<Quyen> dsQuyen = null;
        try {
            transaction.begin();
            String selectQuery = "select q from " + className + " q";
            dsQuyen = session.createQuery(selectQuery, Quyen.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsQuyen;
    }

    public  Quyen getQuyen(String maQuyen) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        Quyen quyen = null;
        try {
            transaction.begin();
            quyen = session.find(Quyen.class, maQuyen);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return quyen;
    }

    public  boolean addQuyen(Quyen quyen) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(quyen);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  boolean updateQuyen(Quyen quyen) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.update(quyen);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  boolean removeQuyen(Quyen quyen) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.delete(quyen);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

}
