package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class ChucVu implements Serializable {

    @Serial
    private static final long serialVersionUID = -3161286753125203070L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maChucVu;

    @Column(columnDefinition = "nvarchar(32)", nullable = false)
    private String tenChucVu;

    public ChucVu() {

    }

    public ChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    public ChucVu(int maChucVu, String tenChucVu) {
        this.maChucVu = maChucVu;
        this.tenChucVu = tenChucVu;
    }

    public int getMaChucVu() {
        return maChucVu;
    }

    public void setMaChucVu(int maChucVu) {
        this.maChucVu = maChucVu;
    }

    public String getTenChucVu() {
        return tenChucVu;
    }

    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChucVu chucVu = (ChucVu) o;
        return maChucVu == chucVu.getMaChucVu();
    }

    @Override
    public int hashCode() {
        return Objects.hash(maChucVu);
    }
    @Override
    public String toString() {
        return tenChucVu;
    }
}
