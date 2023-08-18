package com.example.dao;

import com.example.model.VaiTro;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VaiTroDAO {

    private static String className = VaiTro.class.getName();
    
    public List<VaiTro> getAllRoles() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        List<VaiTro> dsVaiTro = null;
        try {
            transaction.begin();
            String selectQuery = "select vt from " + className + " vt";
            dsVaiTro = session.createQuery(selectQuery, VaiTro.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsVaiTro;
    }
    public  boolean addVaiTro(VaiTro vaiTro){
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.persist(vaiTro);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public boolean updateVaiTro(VaiTro vaiTro) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.update(vaiTro);
            transaction.commit();
            return true;
        }catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public VaiTro getRoleById(int roleId) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        VaiTro vaiTro = null;
        try {
            transaction.begin();
            vaiTro = session.find(VaiTro.class, roleId);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return vaiTro;
    }

}
