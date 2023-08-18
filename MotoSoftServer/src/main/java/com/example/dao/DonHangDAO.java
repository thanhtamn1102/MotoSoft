package com.example.dao;

import com.example.model.DonHang;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Comparator;
import java.util.List;

public class DonHangDAO {

    private static String className = DonHang.class.getName();
    
    public  List<DonHang> getAllDonHang() {
        List<DonHang> listDonHang = null;
        String sqlQuery = "Select dh from " + className + " dh order by dh.ngayTaoDonHang DESC ";
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            listDonHang = session.createQuery(sqlQuery, DonHang.class).getResultList();
            transaction.commit();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            transaction.rollback();
        }
        return listDonHang;
    }

    public  boolean addDonHang(DonHang donHang) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(donHang);
            transaction.commit();
            return true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public  boolean updateDonHang(DonHang donHang) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.update(donHang);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public  boolean removeDonHang(DonHang donHang) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.delete(donHang);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
