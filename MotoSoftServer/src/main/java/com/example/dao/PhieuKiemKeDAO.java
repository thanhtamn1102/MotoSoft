package com.example.dao;

import com.example.model.PhieuKiemKe;
import com.example.utlis.HibernateUtlis;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PhieuKiemKeDAO {

    private static String className = PhieuKiemKe.class.getName();
    
    public List<PhieuKiemKe> getAllPhieuKiemKe() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        String sql = "select pkk from " + className + " pkk";
        List<PhieuKiemKe> dsResult = null;
        try {
            transaction.begin();
            dsResult = session.createQuery(sql, PhieuKiemKe.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsResult;
    }

    public  boolean addPhieuKiemKe(PhieuKiemKe phieuKiemKe) {
       Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(phieuKiemKe);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  boolean updatePhieuKiemKe(PhieuKiemKe phieuKiemKe) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(phieuKiemKe);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  boolean deletePhieuKiemKe(PhieuKiemKe phieuKiemKe) {
         Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.delete(phieuKiemKe);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }
}
