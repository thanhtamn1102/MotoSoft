package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class PhieuNhapHang implements Serializable {

    @Serial
    private static final long serialVersionUID = -2093082145667775906L;

    @Id
    @Column(columnDefinition = "varchar(10)")
    private String maPhieuNhapHang;

    @Column(nullable = false)
    private LocalDateTime ngayTao;

    @Column(nullable = false)
    private PhieuNhapHangStatus trangThai;

    @Column(columnDefinition = "nvarchar(255)")
    private String ghiChu;

    @ManyToOne
    @JoinColumn(name = "maNhanVienTaoDon", nullable = false)
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "maNhaCungCap", nullable = false)
    private NhaCungCap nhaCungCap;

    @OneToMany(mappedBy = "phieuNhapHang", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<ChiTietPhieuNhapHang> chiTietPhieuNhapHangs;

    public PhieuNhapHang() {

    }

    public PhieuNhapHang(String maPhieuNhapHang, LocalDateTime ngayTao, PhieuNhapHangStatus trangThai, String ghiChu, NhanVien nhanVien, NhaCungCap nhaCungCap) {
        this.maPhieuNhapHang = maPhieuNhapHang;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
        this.nhanVien = nhanVien;
        this.nhaCungCap = nhaCungCap;
    }

    public PhieuNhapHang(String maPhieuNhapHang, LocalDateTime ngayTao, PhieuNhapHangStatus trangThai, String ghiChu, NhanVien nhanVien, NhaCungCap nhaCungCap, List<ChiTietPhieuNhapHang> chiTietPhieuNhapHangs) {
        this.maPhieuNhapHang = maPhieuNhapHang;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
        this.nhanVien = nhanVien;
        this.nhaCungCap = nhaCungCap;
        this.chiTietPhieuNhapHangs = chiTietPhieuNhapHangs;
    }

    public String getMaPhieuNhapHang() {
        return maPhieuNhapHang;
    }

    public void setMaPhieuNhapHang(String maPhieuNhapHang) {
        this.maPhieuNhapHang = maPhieuNhapHang;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public List<ChiTietPhieuNhapHang> getChiTietPhieuNhapHangs() {
        return chiTietPhieuNhapHangs;
    }

    public void setChiTietPhieuNhapHangs(List<ChiTietPhieuNhapHang> chiTietPhieuNhapHangs) {
        this.chiTietPhieuNhapHangs = chiTietPhieuNhapHangs;
    }

    public PhieuNhapHangStatus getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(PhieuNhapHangStatus trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "PhieuNhapHang{" +
                "maPhieuNhapHang='" + maPhieuNhapHang + '\'' +
                ", ngayTao=" + ngayTao +
                ", nhanVien=" + nhanVien +
                ", nhaCungCap=" + nhaCungCap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhieuNhapHang that = (PhieuNhapHang) o;
        return Objects.equals(maPhieuNhapHang, that.maPhieuNhapHang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maPhieuNhapHang);
    }
}
