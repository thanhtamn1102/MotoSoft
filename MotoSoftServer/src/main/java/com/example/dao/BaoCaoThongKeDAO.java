package com.example.dao;

import com.example.model.BaoCaoThongKeDoanhThu;
import com.example.model.LoaiBaoCaoThongKe;
import com.example.utlis.HibernateUtlis;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.*;

public class BaoCaoThongKeDAO {

    public double getTongDoanhThu(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        double tongDoanhThu = 0.0;
        String sNgayBatDau = ngayBatDau.getYear() + "-" + ngayBatDau.getMonthValue() + "-" + ngayBatDau.getDayOfMonth();
        String sNgayKetThuc = ngayKetThuc.getYear() + "-" + ngayKetThuc.getMonthValue() + "-" + ngayKetThuc.getDayOfMonth();
        try {
            transaction.begin();
            String sql = "SELECT SUM(tongThanhTien) AS tongDoanhThu \n" +
                    "FROM DonHang dh \n" +
                    "where dh.ngayTaoDonHang BETWEEN '" + sNgayBatDau + "' AND '" + sNgayKetThuc + "'";
            tongDoanhThu = (double) session.createNativeQuery(sql).getSingleResult();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return tongDoanhThu;
    }

    public double getTongVon(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        double tongVon = 0.0;
        String sNgayBatDau = ngayBatDau.getYear() + "-" + ngayBatDau.getMonthValue() + "-" + ngayBatDau.getDayOfMonth();
        String sNgayKetThuc = ngayKetThuc.getYear() + "-" + ngayKetThuc.getMonthValue() + "-" + ngayKetThuc.getDayOfMonth();
        try {
            transaction.begin();
            String sql = "SELECT SUM(sp.giaNhap) as tongVon " +
                    "FROM DonHang dh " +
                    "inner join ChiTietDonHang ctdh on dh.maDonHang = ctdh.maDonHang " +
                    "inner join SanPham sp on ctdh.maSanPham = sp.maSanPham " +
                    "where dh.ngayTaoDonHang BETWEEN '" + sNgayBatDau + "' AND '" + sNgayKetThuc + "'";
            tongVon = (double) session.createNativeQuery(sql).getSingleResult();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return tongVon;
    }

    public double getTongTraHang(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        double tongTraHang = 0;
        String sNgayBatDau = ngayBatDau.getYear() + "-" + ngayBatDau.getMonthValue() + "-" + ngayBatDau.getDayOfMonth();
        String sNgayKetThuc = ngayKetThuc.getYear() + "-" + ngayKetThuc.getMonthValue() + "-" + ngayKetThuc.getDayOfMonth();
        try {
            transaction.begin();
            String sql = "select sum(dh.tongTien) as tongTraHang\n" +
                    "from DonHang dh\n" +
                    "inner join PhieuTraHang pth on dh.maDonHang = pth.maDonHang\n" +
                    "where dh.ngayTaoDonHang BETWEEN '" + sNgayBatDau + "' AND '" + sNgayKetThuc + "'";
            tongTraHang = (double) session.createNativeQuery(sql).getSingleResult();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
        return tongTraHang;
    }

    public List<Object[]> getChiTietBaoCaoThongKeDoanhThuTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        Session session = HibernateUtlis.getSession();
        Transaction transaction = session.getTransaction();
        List<Object[]> data = null;
        String sNgayBatDau = ngayBatDau.getYear() + "-" + ngayBatDau.getMonthValue() + "-" + ngayBatDau.getDayOfMonth();
        String sNgayKetThuc = ngayKetThuc.getYear() + "-" + ngayKetThuc.getMonthValue() + "-" + ngayKetThuc.getDayOfMonth();
        try {
            transaction.begin();
            String sql = "select tb1.ngayTaoDH, tb1.doanhThu, tb1.giamGia, \n" +
                    "tb1.tongPhiVanChuyen, \n" +
                    "(tb1.tongPhiVanChuyen - ISNULL(tb3.phiVanChuyenHoanLai, 0)) as vanChuyen,\n" +
                    "(tb2.tongVon - ISNULL(tb4.tongVonHoanLai, 0)) as tongVon,\n" +
                    "tb2.soLuongDatHang, \n" +
                    "ISNULL(tb3.soLuongTraLai, 0) as soLuongTraLai, \n" +
                    "(tb2.soLuongDatHang - ISNULL(tb3.soLuongTraLai, 0)) as soLuongThuc,\n" +
                    "tb1.donHang, ISNULL(tb3.traLaiHang, 0) as traLaiHang, \n" +
                    "(tb1.doanhThu - tb1.giamGia - ISNULL(tb3.traLaiHang, 0)) as doanhThuThuc,\n" +
                    "(tb1.doanhThu - tb1.giamGia - ISNULL(tb3.traLaiHang, 0) + (tb1.tongPhiVanChuyen - ISNULL(tb3.phiVanChuyenHoanLai, 0))) as tongDoanhThu,\n" +
                    "(tb1.doanhThu - tb1.giamGia - ISNULL(tb3.traLaiHang, 0) - (tb2.tongVon - ISNULL(tb4.tongVonHoanLai, 0))) as loiNhuan,\n" +
                    "ISNULL(((tb1.doanhThu - tb1.giamGia - ISNULL(tb3.traLaiHang, 0) - (tb2.tongVon - ISNULL(tb4.tongVonHoanLai, 0))) * 100 / NULLIF((tb2.tongVon - ISNULL(tb4.tongVonHoanLai, 0)), 0)), 0) as phanTramLoiNhuan\n" +
                    "from (select CAST(ngayTaoDonHang AS DATE) as ngayTaoDH, sum(dh.tongTien) as doanhThu, sum(dh.giamGia) as giamGia, sum(dh.phiVanChuyen) as tongPhiVanChuyen, count(dh.maDonHang) as donHang\n" +
                    "from DonHang dh\n" +
                    "where dh.ngayTaoDonHang BETWEEN '" + sNgayBatDau + "' AND '" + sNgayKetThuc + "'\n" +
                    "GROUP BY CAST(ngayTaoDonHang AS DATE)) tb1\n" +
                    "inner join\n" +
                    "(select CAST(ngayTaoDonHang AS DATE) as ngayTaoDH, sum(sp.giaNhap * ctdh.soLuong) as tongVon, sum(ctdh.soLuong) as soLuongDatHang\n" +
                    "from DonHang dh\n" +
                    "inner join ChiTietDonHang ctdh on dh.maDonHang = ctdh.maDonHang\n" +
                    "inner join SanPham sp on ctdh.maSanPham = sp.maSanPham\n" +
                    "where dh.ngayTaoDonHang BETWEEN '" + sNgayBatDau + "' AND '" + sNgayKetThuc + "'\n" +
                    "GROUP BY CAST(ngayTaoDonHang AS DATE)) tb2\n" +
                    "on tb1.ngayTaoDH = tb2.ngayTaoDH\n" +
                    "left outer join\n" +
                    "(select CAST(ngayTaoDonHang AS DATE) as ngayTaoDH, sum(ctdh.soLuong) as soLuongTraLai, sum(dh.tongTien) as traLaiHang, sum(dh.phiVanChuyen) as phiVanChuyenHoanLai\n" +
                    "from DonHang dh\n" +
                    "inner join PhieuTraHang pth on dh.maDonHang = pth.maDonHang\n" +
                    "inner join ChiTietDonHang ctdh on dh.maDonHang = ctdh.maDonHang\n" +
                    "where dh.ngayTaoDonHang BETWEEN '" + sNgayBatDau + "' AND '" + sNgayKetThuc + "'\n" +
                    "group by CAST(ngayTaoDonHang AS DATE)) tb3\n" +
                    "on tb1.ngayTaoDH = tb3.ngayTaoDH\n" +
                    "left outer join\n" +
                    "(select CAST(ngayTaoDonHang AS DATE) as ngayTaoDH, sum(sp.giaNhap * ctdh.soLuong) as tongVonHoanLai\n" +
                    "from DonHang dh\n" +
                    "inner join PhieuTraHang pth on dh.maDonHang = pth.maDonHang\n" +
                    "inner join ChiTietDonHang ctdh on dh.maDonHang = ctdh.maDonHang\n" +
                    "inner join SanPham sp on sp.maSanPham = ctdh.maSanPham\n" +
                    "where dh.ngayTaoDonHang BETWEEN '" + sNgayBatDau + "' AND '" + sNgayKetThuc + "'\n" +
                    "group by CAST(ngayTaoDonHang AS DATE)) tb4\n" +
                    "on tb1.ngayTaoDH = tb4.ngayTaoDH";
            data = session.createNativeQuery(sql).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }

        return data;
    }

    public List<Object[]> getChiTietBaoCaoThongKeDoanhThuTheoThang(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        return null;
    }

    public List<Object[]> getChiTietBaoCaoThongKeDoanhThuTheoNam(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        return null;
    }

    public BaoCaoThongKeDoanhThu getBaoCaoThongKeDoanhThu(LocalDate ngayBatDau, LocalDate ngayKetThuc, LoaiBaoCaoThongKe type) {
        BaoCaoThongKeDoanhThu baoCaoThongKeDoanhThu = new BaoCaoThongKeDoanhThu();

        double tongDoanhThu = getTongDoanhThu(ngayBatDau, ngayKetThuc);
        baoCaoThongKeDoanhThu.setTongDoanhThu(tongDoanhThu);
        double tongVon = getTongVon(ngayBatDau, ngayKetThuc);
        baoCaoThongKeDoanhThu.setTongVon(tongVon);
        double tongTraHang = getTongTraHang(ngayBatDau, ngayKetThuc);
        baoCaoThongKeDoanhThu.setTongTraHang(tongTraHang);
        double tongLoiNhuan = tongDoanhThu - tongTraHang - tongVon;
        baoCaoThongKeDoanhThu.setTongLoiNhuan(tongLoiNhuan);

        List<Object[]> data = null;

        if(type == LoaiBaoCaoThongKe.BAO_CAO_THEO_NGAY) {
            data = getChiTietBaoCaoThongKeDoanhThuTheoNgay(ngayBatDau, ngayKetThuc);
        }
        else if(type == LoaiBaoCaoThongKe.BAO_CAO_THEO_THANG) {
            data = getChiTietBaoCaoThongKeDoanhThuTheoThang(ngayBatDau, ngayKetThuc);
        }
        else if(type == LoaiBaoCaoThongKe.BAO_CAO_THEO_NAM) {
            data = getChiTietBaoCaoThongKeDoanhThuTheoNam(ngayBatDau, ngayKetThuc);
        }

        baoCaoThongKeDoanhThu.setChiTietBaoCao(data);

        return baoCaoThongKeDoanhThu;
    }
}
