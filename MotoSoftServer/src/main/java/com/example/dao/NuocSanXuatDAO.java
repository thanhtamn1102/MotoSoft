package com.example.dao;

import com.example.model.NuocSanXuat;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class NuocSanXuatDAO {

    private static String className = NuocSanXuat.class.getName();
    
    public List<NuocSanXuat> getAllNuocSanXuat() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        List<NuocSanXuat> dsResult = null;
        try {
            transaction.begin();
            String selectQuery = "select th from " + className + " th";
            dsResult = session.createQuery(selectQuery, NuocSanXuat.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsResult;
    }

    public  NuocSanXuat getNuocSanXuat(int maNuocSanXuat) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        NuocSanXuat result = null;
        try {
            transaction.begin();
            result = session.find(NuocSanXuat.class, maNuocSanXuat);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return result;
    }

    public  boolean addNuocSanXuat(NuocSanXuat nuocSanXuat) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.persist(nuocSanXuat);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }
    public boolean updateNuocSanXuat(NuocSanXuat nuocSanXuat) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(nuocSanXuat);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }
    public  boolean deleteNuocSanXuat(NuocSanXuat nuocSanXuat) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.remove(nuocSanXuat);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

}
