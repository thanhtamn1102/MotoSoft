package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@IdClass(ChiTietDonHangID.class)
public class ChiTietDonHang implements Serializable {

    @Serial
    private static final long serialVersionUID = -6180456068836795832L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maDonHang", columnDefinition = "varchar(10)")
    private DonHang donHang;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maSanPham", columnDefinition = "varchar(10)")
    private SanPham sanPham;

    @Column(nullable = false)
    private double donGia;

    @Column(nullable = false)
    private int soLuong;

    public ChiTietDonHang() {

    }

    public ChiTietDonHang(DonHang donHang, SanPham sanPham, double donGia, int soLuong) {
        this.donHang = donHang;
        this.sanPham = sanPham;
        this.donGia = donGia;
        this.soLuong = soLuong;
    }

    public DonHang getDonHang() {
        return donHang;
    }

    public void setDonHang(DonHang donHang) {
        this.donHang = donHang;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getThanhTien() {
        return soLuong * donGia;
    }

    @Override
    public String toString() {
        return "ChiTietDonHang{" +
                "donHang=" + donHang.getMaDonHang() +
                ", sanPham=" + sanPham +
                ", donGia=" + donGia +
                ", soLuong=" + soLuong +
                '}';
    }
}
