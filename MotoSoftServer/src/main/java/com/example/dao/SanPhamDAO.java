package com.example.dao;

import com.example.model.DanhMuc;
import com.example.model.SanPham;
import com.example.utlis.HibernateUtlis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {

    private static String className = SanPham.class.getName();
    
    public List<SanPham> getAllSanPham() {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        List<SanPham> dsSanPham = null;
        try {
            transaction.begin();
            String selectQuery = "select sp from " + className + " sp";
            dsSanPham = session.createQuery(selectQuery, SanPham.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsSanPham;
    }

    public  SanPham getSanPham(String maSanPham) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        SanPham sanPham = null;
        try {
            transaction.begin();
            sanPham = session.find(SanPham.class, maSanPham);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return sanPham;
    }

    public  boolean addSanPham(SanPham sanPham) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(sanPham);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  boolean updateSanPham(SanPham sanPham) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.update(sanPham);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  boolean removeSanPham(SanPham sanPham) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.delete(sanPham);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return false;
    }

    public  List<SanPham> getAllSanPhamFromDanhMuc(DanhMuc danhMuc, int top) {
        List<SanPham> dsSanPham = null;
        String sql = "select dm from " + DanhMuc.class.getName() + " dm where dm.maDanhMuc='" + danhMuc.getMaDanhMuc() + "'";

        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            DanhMuc dm = session.createQuery(sql, DanhMuc.class).getSingleResult();
            if(dm != null) {
                dsSanPham = new ArrayList<>(dm.getDsSanPham());
                dsSanPham = dsSanPham.subList(0, dsSanPham.size() >= top ? top : dsSanPham.size());
            }
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsSanPham;
    }

    public  List<SanPham> getAllSanPhamFromDanhMuc(DanhMuc danhMuc) {
        List<SanPham> dsSanPham = null;
        String sql = "select dm from " + DanhMuc.class.getName() + " dm where dm.maDanhMuc='" + danhMuc.getMaDanhMuc() + "'";

        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            DanhMuc dm = session.createQuery(sql, DanhMuc.class).getSingleResult();
            if(dm != null)
                dsSanPham = new ArrayList<>(dm.getDsSanPham());
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return dsSanPham;
    }
}
