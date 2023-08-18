package com.example.dao;

import com.example.model.NhaCungCap;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class NhaCungCapDAO {

    private static String className = NhaCungCap.class.getName();
    
    public List<NhaCungCap> getAllNhaCungCap() {
        List<NhaCungCap> dsNhaCungCap = null;
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        String sql = "select ncc from " + className + " ncc";
        try {
            transaction.begin();
            dsNhaCungCap = session.createQuery(sql, NhaCungCap.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dsNhaCungCap;
    }

    public  boolean addNhaCungCap(NhaCungCap nhaCungCap) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.persist(nhaCungCap);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public  boolean updateNhaCungCap(NhaCungCap nhaCungCap) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(nhaCungCap);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public  boolean deleteNhaCungCap(NhaCungCap nhaCungCap) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.remove(nhaCungCap);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
