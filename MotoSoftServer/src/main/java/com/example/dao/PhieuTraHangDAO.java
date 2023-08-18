package com.example.dao;

import com.example.model.PhieuTraHang;
import com.example.utlis.HibernateUtlis;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PhieuTraHangDAO {

    public boolean addPhieuTraHang(PhieuTraHang phieuTraHang) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(phieuTraHang);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public boolean updatePhieuTraHang(PhieuTraHang phieuTraHang) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(phieuTraHang);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public boolean deletePhieuTraHang(PhieuTraHang phieuTraHang) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.remove(phieuTraHang);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }
}
