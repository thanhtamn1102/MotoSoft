package com.example.dao;

import com.example.model.NhomQuyen;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class NhomQuyenDAO {

    private static String className = NhomQuyen.class.getName();
    
    public  List<NhomQuyen> getAllPermissionGroup() {
        List<NhomQuyen> dsNhomQuyen = null;
        String sql = "select nq from " + className + " nq";
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            dsNhomQuyen = session.createQuery(sql, NhomQuyen.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsNhomQuyen;
    }

    public boolean updateNhomQuyen(NhomQuyen nhomQuyen) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.update(nhomQuyen);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public boolean addNhomQuyen(NhomQuyen nhomQuyen) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(nhomQuyen);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }
    public  NhomQuyen getPermissionGroupById(int permissionGroupId) {
        NhomQuyen nhomQuyen = null;
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            nhomQuyen = session.find(NhomQuyen.class, permissionGroupId);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return nhomQuyen;
    }

}
