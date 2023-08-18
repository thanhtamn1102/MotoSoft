package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class DonHang implements Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 2496895078424840929L;

    @Id
    @Column(columnDefinition = "varchar(10)")
    private String maDonHang;

    @Column(nullable = false)
    private LocalDateTime ngayTaoDonHang;

    @Column(nullable = false)
    private double tongTien;

    @Column(nullable = false)
    private double giamGia;

    @Column(nullable = false)
    private double phuThu;

    @Column(nullable = false)
    private double VAT;

    @Column(nullable = false)
    private double phiVanChuyen;

    @Column(nullable = false)
    private double tongThanhTien;

    @Column(columnDefinition = "nvarchar(255)")
    private String ghiChu;

    @ManyToOne
    @JoinColumn(name = "maNhanVien", columnDefinition = "varchar(8)", nullable = false)
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "maKhachHang", nullable = false)
    private KhachHang khachHang;

    @OneToOne
    @PrimaryKeyJoinColumn
    private PhieuTraHang phieuTraHang;

    @OneToMany(mappedBy = "donHang", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChiTietDonHang> chiTietDonHangs;
    
    public DonHang() {
        chiTietDonHangs = new HashSet<>();
    }

    public DonHang(String maDonHang) {
        this();
        this.maDonHang = maDonHang;
    }

    public DonHang(LocalDateTime ngayTaoDonHang, double giamGia, double phuThu, double VAT, double phiVanChuyen,
                   String ghiChu, NhanVien nhanVien, KhachHang khachHang) {
        this();
        this.ngayTaoDonHang = ngayTaoDonHang;
        this.giamGia = giamGia;
        this.phuThu = phuThu;
        this.VAT = VAT;
        this.phiVanChuyen = phiVanChuyen;
        this.ghiChu = ghiChu;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
    }

    public DonHang(String maDonHang, LocalDateTime ngayTaoDonHang, double giamGia, double phuThu, double VAT, double phiVanChuyen,
                   String ghiChu, NhanVien nhanVien, KhachHang khachHang) {
        this(ngayTaoDonHang, giamGia, phuThu, VAT, phiVanChuyen, ghiChu, nhanVien, khachHang);
        this.maDonHang = maDonHang;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public LocalDateTime getNgayTaoDonHang() {
        return ngayTaoDonHang;
    }

    public void setNgayTaoDonHang(LocalDateTime ngayTaoDonHang) {
        this.ngayTaoDonHang = ngayTaoDonHang;
    }

    public double getTongTien() {
        return tongTien;
    }

    public double tinhTongTien() {
        tongTien = 0;
        for (ChiTietDonHang orderDetail : chiTietDonHangs) {
            tongTien += orderDetail.getThanhTien();
        }
        return tongTien;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public double getPhuThu() {
        return phuThu;
    }

    public void setPhuThu(double phuThu) {
        this.phuThu = phuThu;
    }

    public double getVAT() {
        return VAT;
    }

    public void setVAT(double VAT) {
        this.VAT = VAT;
    }

    public double getPhiVanChuyen() {
        return phiVanChuyen;
    }

    public void setPhiVanChuyen(double phiVanChuyen) {
        this.phiVanChuyen = phiVanChuyen;
    }

    public double getTongThanhTien() {
        return tongThanhTien;
    }

    public double tinhTongThanhTien() {
        tongThanhTien = tongTien * (1 + VAT / 100.0) + phuThu + phiVanChuyen - giamGia;
        return tongThanhTien;
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

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public Set<ChiTietDonHang> getChiTietDonHangs() {
        return chiTietDonHangs;
    }

    public void setChiTietDonHangs(Set<ChiTietDonHang> chiTietDonHangs) {
        this.chiTietDonHangs = chiTietDonHangs;
    }

    public int getSize() {
        return this.chiTietDonHangs.size();
    }

    public PhieuTraHang getPhieuTraHang() {
        return phieuTraHang;
    }

    public void setPhieuTraHang(PhieuTraHang phieuTraHang) {
        this.phieuTraHang = phieuTraHang;
    }

    @Override
    public String toString() {
        return "DonHang{" +
                "maDonHang='" + maDonHang + '\'' +
                ", ngayTaoDonHang=" + ngayTaoDonHang +
                ", tongThanhTien=" + tongThanhTien +
                ", ghiChu='" + ghiChu + '\'' +
                ", nhanVien=" + nhanVien +
                ", khachHang=" + khachHang +
                ", chiTietDonHangs=" + chiTietDonHangs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DonHang donHang = (DonHang) o;
        return Objects.equals(maDonHang, donHang.maDonHang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maDonHang);
    }

}
