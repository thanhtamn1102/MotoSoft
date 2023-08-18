package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class KhachHang implements Serializable {

    @Serial
    private static final long serialVersionUID = -1551420330418165136L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long maKhachHang;

    @Column(columnDefinition = "nvarchar(50)", nullable = false)
    private String hoTen;

    @Column(columnDefinition = "varchar(20)", nullable = false)
    private String sdt;

    @Column(columnDefinition = "nvarchar(50)", nullable = false)
    private String email;

    @Column(nullable = false)
    private DiaChi diaChi;

    public KhachHang() {

    }

    public KhachHang(String hoTen, String sdt, String email, DiaChi diaChi) {
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
    }

    public KhachHang(long maKhachHang, String hoTen, String sdt, String email, DiaChi diaChi) {
        this(hoTen, sdt, email, diaChi);
        this.maKhachHang = maKhachHang;
    }

    public long getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(long maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DiaChi getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(DiaChi diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KhachHang khachHang = (KhachHang) o;
        return maKhachHang == khachHang.maKhachHang;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKhachHang);
    }

    @Override
    public String toString() {
        return sdt + "   " + hoTen;
    }
}
