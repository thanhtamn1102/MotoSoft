package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(ChiTietPhieuNhapHangID.class)
public class ChiTietPhieuNhapHang implements Serializable {

    @Serial
    private static final long serialVersionUID = -8622189113905838806L;

    @Id
    @ManyToOne
    @JoinColumn(name = "maPhieuNhapHang", columnDefinition = "varchar(10)")
    private PhieuNhapHang phieuNhapHang;

    @Id
    @ManyToOne
    @JoinColumn(name = "maSanPham", columnDefinition = "varchar(10)")
    private SanPham sanPham;

    @Column(nullable = false)
    private int soLuong;

    @Column(nullable = false)
    private double giaNhap;

    public ChiTietPhieuNhapHang() {

    }

    public ChiTietPhieuNhapHang(PhieuNhapHang phieuNhapHang, SanPham sanPham, int soLuong, double giaNhap) {
        this.phieuNhapHang = phieuNhapHang;
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
    }

    public PhieuNhapHang getPhieuNhapHang() {
        return phieuNhapHang;
    }

    public void setPhieuNhapHang(PhieuNhapHang phieuNhapHang) {
        this.phieuNhapHang = phieuNhapHang;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    @Override
    public String toString() {
        return "ChiTietPhieuNhapHang{" +
                "phieuNhapHang=" + phieuNhapHang +
                ", sanPham=" + sanPham +
                ", soLuong=" + soLuong +
                ", giaNhap=" + giaNhap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietPhieuNhapHang that = (ChiTietPhieuNhapHang) o;
        return Objects.equals(phieuNhapHang, that.phieuNhapHang) && Objects.equals(sanPham, that.sanPham);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phieuNhapHang, sanPham);
    }
}
