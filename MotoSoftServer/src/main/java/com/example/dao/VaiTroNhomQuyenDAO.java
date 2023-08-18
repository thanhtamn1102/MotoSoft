package com.example.dao;

import com.example.model.VaiTroNhomQuyen;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VaiTroNhomQuyenDAO {

    private static String className = VaiTroNhomQuyen.class.getName();
    
    public List<VaiTroNhomQuyen> getAllRoles() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        List<VaiTroNhomQuyen> dsVaiTroNhomQuyen = null;
        try {
            transaction.begin();
            String selectQuery = "select vt from " + className + " vt";
            dsVaiTroNhomQuyen = session.createQuery(selectQuery, VaiTroNhomQuyen.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsVaiTroNhomQuyen;
    }
    public  boolean addVaiTroNhomQuyen(VaiTroNhomQuyen vaiTro){
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(vaiTro);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  VaiTroNhomQuyen getRoleById(int roleId) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        VaiTroNhomQuyen vaiTroNhomQuyen = null;
        try {
            transaction.begin();
            vaiTroNhomQuyen = session.find(VaiTroNhomQuyen.class, roleId);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return vaiTroNhomQuyen;
    }

}
