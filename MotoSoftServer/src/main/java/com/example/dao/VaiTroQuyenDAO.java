package com.example.dao;

import com.example.model.VaiTroQuyen;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VaiTroQuyenDAO {

    private static String className = VaiTroQuyen.class.getName();

    public List<VaiTroQuyen> getAllVaiTroQuyen() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        List<VaiTroQuyen> dsVaiTroQuyen = null;
        try {
            transaction.begin();
            String selectQuery = "select sp from " + className + " sp";
            dsVaiTroQuyen = session.createQuery(selectQuery, VaiTroQuyen.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsVaiTroQuyen;
    }

    public  VaiTroQuyen getVaiTroQuyen(String maVaiTroQuyen) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        VaiTroQuyen vaiTroQuyen = null;
        try {
            transaction.begin();
            vaiTroQuyen = session.find(VaiTroQuyen.class, maVaiTroQuyen);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return vaiTroQuyen;
    }

    public  boolean addVaiTroQuyen(VaiTroQuyen vaiTroQuyen) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(vaiTroQuyen);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  boolean updateVaiTroQuyen(VaiTroQuyen vaiTroQuyen) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(vaiTroQuyen);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  boolean removeVaiTroQuyen(VaiTroQuyen vaiTroQuyen) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.remove(vaiTroQuyen);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }
}
