package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@IdClass(VaiTroNhomQuyenID.class)
public class VaiTroNhomQuyen implements Serializable {

    @Serial
    private static final long serialVersionUID = -1210954629650183495L;

    @Id
    @ManyToOne
    @JoinColumn(name = "maVaiTro")
    private VaiTro vaiTro;

    @Id
    @ManyToOne
    @JoinColumn(name = "maNhomQuyen", columnDefinition = "varchar(32)")
    private NhomQuyen nhomQuyen;

    @Column(nullable = false)
    private boolean trangThai;

    @OneToMany(mappedBy = "nhomQuyen", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<VaiTroQuyen> dsQuyen;

    public VaiTroNhomQuyen() {

    }

    public VaiTroNhomQuyen(VaiTro vaiTro, NhomQuyen nhomQuyen) {
        this.vaiTro = vaiTro;
        this.nhomQuyen = nhomQuyen;
    }

    public VaiTroNhomQuyen(VaiTro vaiTro, NhomQuyen nhomQuyen, boolean trangThai) {
        this.vaiTro = vaiTro;
        this.nhomQuyen = nhomQuyen;
        this.trangThai = trangThai;
    }

    public VaiTroNhomQuyen(VaiTro vaiTro, NhomQuyen nhomQuyen, boolean trangThai, List<VaiTroQuyen> dsQuyen) {
        this.vaiTro = vaiTro;
        this.nhomQuyen = nhomQuyen;
        this.trangThai = trangThai;
        this.dsQuyen = dsQuyen;
    }

    public VaiTro getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(VaiTro vaiTro) {
        this.vaiTro = vaiTro;
    }

    public NhomQuyen getNhomQuyen() {
        return nhomQuyen;
    }

    public void setNhomQuyen(NhomQuyen nhomQuyen) {
        this.nhomQuyen = nhomQuyen;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public List<VaiTroQuyen> getDsQuyen() {
        return dsQuyen;
    }

    public void setDsQuyen(List<VaiTroQuyen> dsQuyen) {
        this.dsQuyen = dsQuyen;
    }

    @Override
    public String toString() {
        return "VaiTroNhomQuyen{" +
                "vaiTro=" + vaiTro +
                ", nhomQuyen=" + nhomQuyen +
                ", trangThai=" + trangThai +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VaiTroNhomQuyen that = (VaiTroNhomQuyen) o;
        return Objects.equals(vaiTro, that.vaiTro) && Objects.equals(nhomQuyen, that.nhomQuyen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vaiTro, nhomQuyen);
    }
}
