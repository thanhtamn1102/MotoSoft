package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class MauSac implements Serializable {

    @Serial
    private static final long serialVersionUID = -98922704192910875L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mauMau;

    @Column(columnDefinition = "nvarchar(32)", nullable = false)
    private String tenMau;

    @Column(columnDefinition = "varchar(7)", nullable = false)
    private String code;


    public MauSac() {

    }

    public MauSac(int mauMau) {
        this.mauMau = mauMau;
    }

    public MauSac(String tenMau, String code) {
        this.tenMau = tenMau;
        this.code = code;
    }

    public MauSac(int mauMau, String tenMau, String code) {
        this.mauMau = mauMau;
        this.tenMau = tenMau;
        this.code = code;
    }

    public int getMauMau() {
        return mauMau;
    }

    public void setMauMau(int mauMau) {
        this.mauMau = mauMau;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return tenMau;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MauSac mauSac = (MauSac) o;
        return mauMau == mauSac.mauMau;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mauMau);
    }
}
