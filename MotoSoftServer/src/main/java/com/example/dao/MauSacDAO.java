package com.example.dao;

import com.example.model.MauSac;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MauSacDAO {

    private static String className = MauSac.class.getName();
    
    public  List<MauSac> getAllMauSac() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        List<MauSac> dsMauSac = null;
        try {
            transaction.begin();
            String selectQuery = "select ms from " + className + " ms";
            dsMauSac = session.createQuery(selectQuery, MauSac.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsMauSac;
    }

    public  MauSac getMauSac(int colorId) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        MauSac mauSac = null;
        try {
            transaction.begin();
            mauSac = session.find(MauSac.class, colorId);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return mauSac;
    }

    public  boolean addMauSac(MauSac mauSac) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.persist(mauSac);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  boolean updateMauSac(MauSac mauSac) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(mauSac);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  boolean deleteMauSac(MauSac mauSac) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.remove(mauSac);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }
}
