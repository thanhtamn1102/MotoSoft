package com.example.dao;

import com.example.model.ChucVu;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ChucVuDAO {

    private static String className = ChucVu.class.getName();
    
    public List<ChucVu> getAllCChucVu() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        List<ChucVu> dsChucVu = null;
        try {
            transaction.begin();
            String selectQuery = "select dm from " + className + " dm";
            dsChucVu = session.createQuery(selectQuery, ChucVu.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsChucVu;
    }

    public boolean addChucVu(ChucVu chucVu){
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.persist(chucVu);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public boolean removeChucVu(ChucVu chucVu){
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.remove(chucVu);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateChucVu(ChucVu chucVu){
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(chucVu);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
