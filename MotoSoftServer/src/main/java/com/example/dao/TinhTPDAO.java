package com.example.dao;

import com.example.model.TinhTP;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TinhTPDAO {

    private final String CLASS_NAME = TinhTP.class.getName();
    
    public boolean addTinhTP(TinhTP tinhTP) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(tinhTP);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public List<TinhTP> getAllTinhTP() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        String sql = "select t from " + CLASS_NAME + " t";
        List<TinhTP> result = null;
        try {
            transaction.begin();
            result = session.createQuery(sql, TinhTP.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return result;
    }

    public TinhTP getTinhTPById(String id) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        TinhTP tinhTP = null;
        try {
            transaction.begin();
            tinhTP = session.find(TinhTP.class, id);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return tinhTP;
    }

}
