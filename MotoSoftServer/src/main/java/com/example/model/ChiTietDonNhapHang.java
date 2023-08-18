package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(ChiTietDonNhapHangID.class)
public class ChiTietDonNhapHang implements Serializable {

    @Serial
    private static final long serialVersionUID = 238908520397414436L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maDonNhapHang", columnDefinition = "varchar(10)")
    private DonNhapHang donNhapHang;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maSanPham", columnDefinition = "varchar(10)")
    private SanPham sanPham;

    @Column(nullable = false)
    private double giaNhap;

    @Column(nullable = false)
    private int soLuong;

    @Column(nullable = false)
    private int soLuongDaNhan;


    public ChiTietDonNhapHang() {
        this.soLuongDaNhan = 0;
    }

    public ChiTietDonNhapHang(DonNhapHang donNhapHang, SanPham sanPham, int soLuong) {
        this();
        this.donNhapHang = donNhapHang;
        this.sanPham = sanPham;
        this.giaNhap = sanPham.getGiaNhap();
        this.soLuong = soLuong;
    }

    public DonNhapHang getDonNhapHang() {
        return donNhapHang;
    }

    public void setDonNhapHang(DonNhapHang donNhapHang) {
        this.donNhapHang = donNhapHang;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getSoLuongDaNhan() {
        return soLuongDaNhan;
    }

    public void setSoLuongDaNhan(int soLuongDaNhan) {
        this.soLuongDaNhan = soLuongDaNhan;
    }

    public double getThanhTien() {
        return (soLuong * giaNhap);
    }

    @Override
    public String toString() {
        return "ChiTietDonNhapHang{" +
                "sanPham=" + sanPham +
                ", giaNhap=" + giaNhap +
                ", soLuong=" + soLuong +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietDonNhapHang that = (ChiTietDonNhapHang) o;
        return Objects.equals(donNhapHang, that.donNhapHang) && Objects.equals(sanPham, that.sanPham);
    }

    @Override
    public int hashCode() {
        return Objects.hash(donNhapHang, sanPham);
    }
}
