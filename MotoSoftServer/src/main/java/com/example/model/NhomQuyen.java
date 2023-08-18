package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class NhomQuyen implements Serializable {

    @Serial
    private static final long serialVersionUID = -6318413441240381222L;

    @Id
    @Column(columnDefinition = "varchar(32)")
    private String maNhomQuyen;

    @Column(columnDefinition = "nvarchar(32)", nullable = false)
    private String tenNhomQuyen;

    @OneToMany(mappedBy = "nhomQuyen", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quyen> dsQuyen;

    public NhomQuyen() {

    }

    public NhomQuyen(String maNhomQuyen) {
        this.maNhomQuyen = maNhomQuyen;
    }

    public NhomQuyen(String maNhomQuyen, String tenNhomQuyen) {
        this(maNhomQuyen);
        this.tenNhomQuyen = tenNhomQuyen;
    }

    public NhomQuyen(String maNhomQuyen, String tenNhomQuyen, List<Quyen> dsQuyen) {
        this(maNhomQuyen, tenNhomQuyen);
        this.dsQuyen = dsQuyen;
    }

    public String getMaNhomQuyen() {
        return maNhomQuyen;
    }

    public void setMaNhomQuyen(String maNhomQuyen) {
        this.maNhomQuyen = maNhomQuyen;
    }

    public String getTenNhomQuyen() {
        return tenNhomQuyen;
    }

    public void setTenNhomQuyen(String tenNhomQuyen) {
        this.tenNhomQuyen = tenNhomQuyen;
    }

    public List<Quyen> getDsQuyen() {
        return dsQuyen;
    }

    public void setDsQuyen(List<Quyen> dsQuyen) {
        this.dsQuyen = dsQuyen;
    }

    @Override
    public String toString() {
        return tenNhomQuyen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NhomQuyen nhomQuyen = (NhomQuyen) o;
        return Objects.equals(maNhomQuyen, nhomQuyen.maNhomQuyen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNhomQuyen);
    }
}
