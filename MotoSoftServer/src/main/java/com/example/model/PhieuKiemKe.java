package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
public class PhieuKiemKe implements Serializable {

    @Serial
    private static final long serialVersionUID = -67018180175440030L;

    @Id
    @Column(columnDefinition = "varchar(10)")
    private String maPhieuKiemKe;

    @Column(nullable = false)
    private LocalDateTime ngayTao;

    @Column(nullable = true)
    private LocalDateTime ngayKiemKe;

    @Column(columnDefinition = "nvarchar(255)")
    private String ghiChu;

    @Column(nullable = false)
    private PhieuKiemKeStatus trangThai;

    @ManyToOne
    @JoinColumn(name = "maNhanVienTaoPhieu", columnDefinition = "varchar(8)", nullable = false)
    private NhanVien nhanVienTaoPhieu;

    @ManyToOne
    @JoinColumn(name = "maNhanVienKiemKe", columnDefinition = "varchar(8)")
    private NhanVien nhanVienKiemKe;

    @OneToMany(mappedBy = "phieuKiemKe", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChiTietPhieuKiem> chiTietPhieuKiems;

    public PhieuKiemKe() {

    }

    public PhieuKiemKe(String maPhieuKiemKe, LocalDateTime ngayTao,
                       PhieuKiemKeStatus trangThai, NhanVien nhanVienTaoPhieu, ChiTietPhieuKiem ...chiTietPhieuKiems) {
        this.maPhieuKiemKe = maPhieuKiemKe;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.nhanVienTaoPhieu = nhanVienTaoPhieu;
        this.chiTietPhieuKiems = Arrays.asList(chiTietPhieuKiems);
    }

    public PhieuKiemKe(LocalDateTime ngayTao, PhieuKiemKeStatus trangThai,
                       NhanVien nhanVienTaoPhieu, List<ChiTietPhieuKiem> chiTietPhieuKiems) {
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.nhanVienTaoPhieu = nhanVienTaoPhieu;
        this.chiTietPhieuKiems = chiTietPhieuKiems;
    }

    public PhieuKiemKe(LocalDateTime ngayTao, PhieuKiemKeStatus trangThai,
                       NhanVien nhanVienTaoPhieu, ChiTietPhieuKiem ...chiTietPhieuKiems) {
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.nhanVienTaoPhieu = nhanVienTaoPhieu;
        this.chiTietPhieuKiems = Arrays.asList(chiTietPhieuKiems);
    }

    public String getMaPhieuKiemKe() {
        return maPhieuKiemKe;
    }

    public void setMaPhieuKiemKe(String maPhieuKiemKe) {
        this.maPhieuKiemKe = maPhieuKiemKe;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public LocalDateTime getNgayKiemKe() {
        return ngayKiemKe;
    }

    public void setNgayKiemKe(LocalDateTime ngayKiemKe) {
        this.ngayKiemKe = ngayKiemKe;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public PhieuKiemKeStatus getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(PhieuKiemKeStatus trangThai) {
        this.trangThai = trangThai;
    }

    public NhanVien getNhanVienTaoPhieu() {
        return nhanVienTaoPhieu;
    }

    public void setNhanVienTaoPhieu(NhanVien nhanVienTaoPhieu) {
        this.nhanVienTaoPhieu = nhanVienTaoPhieu;
    }

    public NhanVien getNhanVienKiemKe() {
        return nhanVienKiemKe;
    }

    public void setNhanVienKiemKe(NhanVien nhanVienKiemKe) {
        this.nhanVienKiemKe = nhanVienKiemKe;
    }

    public List<ChiTietPhieuKiem> getChiTietPhieuKiems() {
        return chiTietPhieuKiems;
    }

    public void setChiTietPhieuKiems(List<ChiTietPhieuKiem> chiTietPhieuKiems) {
        this.chiTietPhieuKiems = chiTietPhieuKiems;
    }

    public void setChiTietPhieuKiems(ChiTietPhieuKiem ...chiTietPhieuKiems) {
        this.chiTietPhieuKiems = Arrays.asList(chiTietPhieuKiems);
    }

    @Override
    public String toString() {
        return "PhieuKiemKe{" +
                "maPhieuKiemKe='" + maPhieuKiemKe + '\'' +
                ", ngayTao=" + ngayTao +
                ", ngayKiemKe=" + ngayKiemKe +
                ", ghiChu='" + ghiChu + '\'' +
                ", trangThai=" + trangThai +
                ", nhanVienTaoPhieu=" + nhanVienTaoPhieu +
                ", nhanVienKiemKe=" + nhanVienKiemKe +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhieuKiemKe that = (PhieuKiemKe) o;
        return Objects.equals(maPhieuKiemKe, that.maPhieuKiemKe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maPhieuKiemKe);
    }
}
