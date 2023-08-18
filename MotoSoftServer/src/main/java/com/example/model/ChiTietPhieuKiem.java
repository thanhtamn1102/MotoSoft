package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(ChiTietPhieuKiemID.class)
public class ChiTietPhieuKiem implements Serializable {

    @Serial
    private static final long serialVersionUID = -25485438127709502L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maPhieuKiemKe", columnDefinition = "varchar(10)")
    private PhieuKiemKe phieuKiemKe;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maSanPham", columnDefinition = "varchar(10)")
    private SanPham sanPham;

    @Column(nullable = false)
    private int soLuongHeThong;

    @Column(nullable = false)
    private int soLuongThucTe;

    @Column(columnDefinition = "nvarchar(255)")
    private String ghiChu;

    public ChiTietPhieuKiem() {

    }

    public ChiTietPhieuKiem(PhieuKiemKe phieuKiemKe, SanPham sanPham) {
        this.phieuKiemKe = phieuKiemKe;
        this.sanPham = sanPham;
        this.soLuongHeThong = sanPham.getSoLuong();
        this.soLuongThucTe = 0;
    }

    public PhieuKiemKe getPhieuKiemKe() {
        return phieuKiemKe;
    }

    public void setPhieuKiemKe(PhieuKiemKe phieuKiemKe) {
        this.phieuKiemKe = phieuKiemKe;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public int getSoLuongHeThong() {
        return soLuongHeThong;
    }

    public int getSoLuongThucTe() {
        return soLuongThucTe;
    }

    public void setSoLuongThucTe(int soLuongThucTe) {
        this.soLuongThucTe = soLuongThucTe;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "ChiTietPhieuKiem{" +
                "phieuKiemKe=" + phieuKiemKe +
                ", sanPham=" + sanPham +
                ", soLuongHeThong=" + soLuongHeThong +
                ", soLuongThucTe=" + soLuongThucTe +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietPhieuKiem that = (ChiTietPhieuKiem) o;
        return Objects.equals(phieuKiemKe, that.phieuKiemKe) && Objects.equals(sanPham, that.sanPham);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phieuKiemKe, sanPham);
    }
}
