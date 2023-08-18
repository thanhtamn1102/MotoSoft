package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class NuocSanXuat implements Serializable {

    @Serial
    private static final long serialVersionUID = -5794805793691399771L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maNuocSanXuat;

    @Column(columnDefinition = "nvarchar(32)", nullable = false)
    private String tenNuocSanXuat;


    public NuocSanXuat() {

    }

    public NuocSanXuat(String tenNuocSanXuat) {
        this.tenNuocSanXuat = tenNuocSanXuat;
    }

    public NuocSanXuat(int maNuocSanXuat, String tenNuocSanXuat) {
        this.maNuocSanXuat = maNuocSanXuat;
        this.tenNuocSanXuat = tenNuocSanXuat;
    }

    public int getMaNuocSanXuat() {
        return maNuocSanXuat;
    }

    public void setMaNuocSanXuat(int maNuocSanXuat) {
        this.maNuocSanXuat = maNuocSanXuat;
    }

    public String getTenNuocSanXuat() {
        return tenNuocSanXuat;
    }

    public void setTenNuocSanXuat(String tenNuocSanXuat) {
        this.tenNuocSanXuat = tenNuocSanXuat;
    }

    @Override
    public String toString() {
        return tenNuocSanXuat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NuocSanXuat nuocSanXuat = (NuocSanXuat) o;
        return maNuocSanXuat == nuocSanXuat.maNuocSanXuat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNuocSanXuat);
    }
}
