package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
public class DanhMuc implements Serializable {

    @Serial
    private static final long serialVersionUID = -4438853105185900687L;

    @Id
    @Column(columnDefinition = "varchar(10)")
    private String maDanhMuc;

    @Column(columnDefinition = "nvarchar(32)", nullable = false)
    private String tenDanhMuc;

    @Column(nullable = false)
    private boolean trangThai;

    @ManyToMany(mappedBy = "danhMucs", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SanPham> dsSanPham;


    public DanhMuc() {

    }

    public DanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public DanhMuc(String tenDanhMuc, boolean trangThai) {
        this.tenDanhMuc = tenDanhMuc;
        this.trangThai = trangThai;
    }

    public DanhMuc(String maDanhMuc, String tenDanhMuc, boolean trangThai) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
        this.trangThai = trangThai;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public Set<SanPham> getDsSanPham() {
        return dsSanPham;
    }

    public void setDsSanPham(Set<SanPham> dsSanPham) {
        this.dsSanPham = dsSanPham;
    }

    @Override
    public String toString() {
        return tenDanhMuc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DanhMuc danhMuc = (DanhMuc) o;
        return Objects.equals(maDanhMuc, danhMuc.maDanhMuc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maDanhMuc);
    }
}
