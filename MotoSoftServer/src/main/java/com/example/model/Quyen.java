package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Quyen implements Serializable {

    @Serial
    private static final long serialVersionUID = -2030384692661813516L;

    @Id
    @Column(columnDefinition = "varchar(32)")
    private String maQuyen;

    @Column(columnDefinition = "nvarchar(32)", nullable = false)
    private String tenQuyen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNhomQuyen", nullable = false)
    private NhomQuyen nhomQuyen;


    public Quyen() {

    }

    public Quyen(String maQuyen, String tenQuyen, NhomQuyen nhomQuyen) {
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
        this.nhomQuyen = nhomQuyen;
    }

    public String getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(String maQuyen) {
        this.maQuyen = maQuyen;
    }

    public String getTenQuyen() {
        return tenQuyen;
    }

    public void setTenQuyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }

    public NhomQuyen getNhomQuyen() {
        return nhomQuyen;
    }

    public void setNhomQuyen(NhomQuyen nhomQuyen) {
        this.nhomQuyen = nhomQuyen;
    }

    @Override
    public String toString() {
        return tenQuyen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quyen quyen = (Quyen) o;
        return Objects.equals(maQuyen, quyen.maQuyen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maQuyen);
    }
}
