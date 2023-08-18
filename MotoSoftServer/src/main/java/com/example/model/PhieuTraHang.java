package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PhieuTraHang implements Serializable {

    @Serial
    private static final long serialVersionUID = 5369946852934647438L;

    @Id
    @Column(columnDefinition = "varchar(11)")
    private String maPhieuTraHang;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "maDonHang", columnDefinition = "varchar(10)")
    private DonHang donHang;

    @Column(nullable = false)
    private LocalDateTime ngayTaoPhieu;

    @Column(nullable = false)
    private double tiLeHoanTien;

    @Column(nullable = false)
    private double tienHoanLai;

    @Column(nullable = false)
    private double phuThu;

    @Column(nullable = false)
    private double tongTienHoan;

    @Column(columnDefinition = "nvarchar(MAX)")
    private String ghiChu;

    @ManyToOne
    @JoinColumn(name = "maNhanVien", columnDefinition = "varchar(8)", nullable = false)
    private NhanVien nhanVien;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "HinhAnhPhieuTraHang",
            joinColumns = @JoinColumn(name = "maSanPham")
    )
    @Column(name = "hinhAnh", columnDefinition = "nvarchar(MAX)")
    private Set<String> dsHinhAnh;

    public PhieuTraHang() {
        dsHinhAnh = new HashSet<>();
    }

    public PhieuTraHang(DonHang donHang, LocalDateTime ngayTaoPhieu, double tiLeHoanTien, double phuThu,
                        String ghiChu, NhanVien nhanVien) {
        this();
        this.donHang = donHang;
        this.ngayTaoPhieu = ngayTaoPhieu;
        this.tiLeHoanTien = tiLeHoanTien;
        this.phuThu = phuThu;
        this.ghiChu = ghiChu;
        this.nhanVien = nhanVien;
    }

    public PhieuTraHang(String maPhieuTraHang, DonHang donHang, LocalDateTime ngayTaoPhieu,
                        double tiLeHoanTien, double phuThu, String ghiChu, NhanVien nhanVien) {
        this(donHang, ngayTaoPhieu, tiLeHoanTien, phuThu, ghiChu, nhanVien);
        this.maPhieuTraHang = maPhieuTraHang;
    }

    public String getMaPhieuTraHang() {
        return maPhieuTraHang;
    }

    public void setMaPhieuTraHang(String maPhieuTraHang) {
        this.maPhieuTraHang = maPhieuTraHang;
    }

    public DonHang getDonHang() {
        return donHang;
    }

    public void setDonHang(DonHang donHang) {
        this.donHang = donHang;
    }

    public LocalDateTime getNgayTaoPhieu() {
        return ngayTaoPhieu;
    }

    public void setNgayTaoPhieu(LocalDateTime ngayTaoPhieu) {
        this.ngayTaoPhieu = ngayTaoPhieu;
    }

    public double getTongTienHoan() {
        return tongTienHoan;
    }

    public double tinhTongTienHoan() {
        tongTienHoan = tienHoanLai + phuThu;
        return tongTienHoan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public double getTiLeHoanTien() {
        return tiLeHoanTien;
    }

    public void setTiLeHoanTien(double tiLeHoanTien) {
        this.tiLeHoanTien = tiLeHoanTien;
    }

    public double getPhuThu() {
        return phuThu;
    }

    public void setPhuThu(double phuThu) {
        this.phuThu = phuThu;
    }

    public double getTienHoanLai() {
        return tienHoanLai;
    }

    public double tinhTienHoanLai() {
        tienHoanLai = donHang.tinhTongThanhTien() * tiLeHoanTien / 100;
        return tienHoanLai;
    }

    public Set<String> getDsHinhAnh() {
        return dsHinhAnh;
    }

    public void setDsHinhAnh(Set<String> dsHinhAnh) {
        this.dsHinhAnh = dsHinhAnh;
    }

    @Override
    public String toString() {
        return "PhieuTraHang{" +
                "maPhieuTraHang='" + maPhieuTraHang + '\'' +
                ", ngayTaoPhieu=" + ngayTaoPhieu +
                ", tiLeHoanTien=" + tiLeHoanTien +
                ", tienHoanLai=" + tienHoanLai +
                ", phuThu=" + phuThu +
                ", tongTienHoan=" + tongTienHoan +
                ", ghiChu='" + ghiChu + '\'' +
                ", nhanVien=" + nhanVien +
                ", dsHinhAnh=" + dsHinhAnh +
                '}';
    }
}
