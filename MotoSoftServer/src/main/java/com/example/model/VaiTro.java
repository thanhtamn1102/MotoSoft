package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
public class VaiTro implements Serializable {

    @Serial
    private static final long serialVersionUID = -6439803004915705990L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maVaiTro;

    @Column(columnDefinition = "nvarchar(32)", nullable = false)
    private String tenVaiTro;

    @OneToMany(mappedBy = "vaiTro", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<VaiTroNhomQuyen> dsNhomQuyen;


    public VaiTro() {

    }

    public VaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }

    public VaiTro(int maVaiTro, String tenVaiTro) {
        this.maVaiTro = maVaiTro;
        this.tenVaiTro = tenVaiTro;
    }

    public VaiTro(int maVaiTro, String tenVaiTro, List<VaiTroNhomQuyen> dsNhomQuyen) {
        this.maVaiTro = maVaiTro;
        this.tenVaiTro = tenVaiTro;
        this.dsNhomQuyen = dsNhomQuyen;
    }

    public int getMaVaiTro() {
        return maVaiTro;
    }

    public void setMaVaiTro(int maVaiTro) {
        this.maVaiTro = maVaiTro;
    }

    public String getTenVaiTro() {
        return tenVaiTro;
    }

    public void setTenVaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }

    public List<VaiTroNhomQuyen> getDsNhomQuyen() {
        return dsNhomQuyen;
    }

    public void setDsNhomQuyen(List<VaiTroNhomQuyen> dsNhomQuyen) {
        this.dsNhomQuyen = dsNhomQuyen;
    }

    @Override
    public String toString() {
        return tenVaiTro;
    }
}
