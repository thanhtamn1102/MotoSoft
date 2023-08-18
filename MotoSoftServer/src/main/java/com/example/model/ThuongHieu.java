package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class ThuongHieu implements Serializable {

    @Serial
    private static final long serialVersionUID = 632772255774120988L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maThuongHieu;

    @Column(columnDefinition = "nvarchar(32)", nullable = false)
    private String tenThuongHieu;

    @OneToMany(mappedBy = "thuongHieu")
    private List<SanPham> dsSanPham;


    public ThuongHieu() {

    }

    public ThuongHieu(int maThuongHieu) {
        this.maThuongHieu = maThuongHieu;
    }

    public ThuongHieu(String tenThuongHieu) {
        this.tenThuongHieu = tenThuongHieu;
    }

    public ThuongHieu(int maThuongHieu, String tenThuongHieu) {
        this.maThuongHieu = maThuongHieu;
        this.tenThuongHieu = tenThuongHieu;
    }

    public ThuongHieu(int maThuongHieu, String tenThuongHieu, List<SanPham> dsSanPham) {
        this.maThuongHieu = maThuongHieu;
        this.tenThuongHieu = tenThuongHieu;
        this.dsSanPham = dsSanPham;
    }

    public int getMaThuongHieu() {
        return maThuongHieu;
    }

    public void setMaThuongHieu(int maThuongHieu) {
        this.maThuongHieu = maThuongHieu;
    }

    public String getTenThuongHieu() {
        return tenThuongHieu;
    }

    public void setTenThuongHieu(String tenThuongHieu) {
        this.tenThuongHieu = tenThuongHieu;
    }

    public List<SanPham> getDsSanPham() {
        return dsSanPham;
    }

    public void setDsSanPham(List<SanPham> dsSanPham) {
        this.dsSanPham = dsSanPham;
    }

    @Override
    public String toString() {
        return tenThuongHieu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThuongHieu that = (ThuongHieu) o;
        return maThuongHieu == that.maThuongHieu;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maThuongHieu);
    }
}
