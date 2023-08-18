package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class DonNhapHang implements Serializable {

    @Serial
    private static final long serialVersionUID = 660196626971131876L;

    @Id
    @Column(columnDefinition = "varchar(10)")
    private String maDonNhapHang;

    @Column(nullable = false)
    private LocalDateTime ngayTao;

    @Column(nullable = false)
    private DonNhapHangStatus trangThai;

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
    private double tongThanhToan;

    @Column(nullable = false)
    private int tongSoLuong;

    @Column(nullable = false)
    private int soLuongConLai;

    @Column(nullable = false)
    private double daThanhToan;

    @Column(nullable = false)
    private double congNo;

    @Column(columnDefinition = "nvarchar(255)")
    private String ghiChu;

    @ManyToOne
    @JoinColumn(name = "maNhanVienTaoDon", nullable = false)
    private NhanVien nhanVienTaoDon;

    @ManyToOne
    @JoinColumn(name = "maNhaCungCap", nullable = false)
    private NhaCungCap nhaCungCap;

    @OneToMany(mappedBy = "donNhapHang", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChiTietDonNhapHang> chiTietDonNhapHangs;


    public DonNhapHang() {
        chiTietDonNhapHangs = new HashSet<>();
    }

    public DonNhapHang(LocalDateTime ngayTao, DonNhapHangStatus trangThai, NhanVien nhanVienTaoDon, NhaCungCap nhaCungCap) {
        this();
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.nhanVienTaoDon = nhanVienTaoDon;
        this.nhaCungCap = nhaCungCap;
    }

    public DonNhapHang(String maDonNhapHang, LocalDateTime ngayTao, DonNhapHangStatus trangThai, NhanVien nhanVienTaoDon, NhaCungCap nhaCungCap) {
        this(ngayTao, trangThai, nhanVienTaoDon, nhaCungCap);
        this.maDonNhapHang = maDonNhapHang;
    }

    public String getMaDonNhapHang() {
        return maDonNhapHang;
    }

    public void setMaDonNhapHang(String maDonNhapHang) {
        this.maDonNhapHang = maDonNhapHang;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public DonNhapHangStatus getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(DonNhapHangStatus trangThai) {
        this.trangThai = trangThai;
    }

    public NhanVien getNhanVienTaoDon() {
        return nhanVienTaoDon;
    }

    public void setNhanVienTaoDon(NhanVien nhanVienTaoDon) {
        this.nhanVienTaoDon = nhanVienTaoDon;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public double tinhTongThanhToan() {
        double tongThanhToan = (tongTien + phuThu - giamGia + phiVanChuyen) * (VAT / 100 + 1);
        this.tongThanhToan = tongThanhToan;
        return tongThanhToan;
    }

    public int tinhTongSoLuong() {
        int tongSoLuong = chiTietDonNhapHangs.stream()
                .mapToInt(chiTietDonNhapHang -> chiTietDonNhapHang.getSoLuong())
                .sum();
        this.tongSoLuong = tongSoLuong;
        return tongSoLuong;
    }

    public int tinhSoLuongConLai() {
        int soLuongConLai = tongSoLuong -
                chiTietDonNhapHangs.stream()
                        .mapToInt(chiTietDonNhapHang -> chiTietDonNhapHang.getSoLuongDaNhan())
                        .sum();
        this.soLuongConLai = soLuongConLai;
        return soLuongConLai;
    }

    public double getTongThanhToan() {
        return tongThanhToan;
    }

    public int getTongSoLuong() {
        return tongSoLuong;
    }

    public int getSoLuongConLai() {
        return soLuongConLai;
    }

    public double getTongTien() {
        return tongTien;
    }

    public double tinhTongTien() {
        double tongTien = chiTietDonNhapHangs.stream()
                .mapToDouble(chiTietDonNhapHang -> chiTietDonNhapHang.getSoLuong() * chiTietDonNhapHang.getGiaNhap())
                .sum();
        this.tongTien = tongTien;
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

    public double getCongNo() {
        return congNo;
    }

    public double tinhCongNo() {
        double congNo = tongThanhToan - daThanhToan;
        this.congNo = congNo;
        return congNo;
    }

    public double getDaThanhToan() {
        return daThanhToan;
    }

    public void setDaThanhToan(double daThanhToan) {
        this.daThanhToan = daThanhToan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Set<ChiTietDonNhapHang> getChiTietDonNhapHangs() {
        return chiTietDonNhapHangs;
    }

    public void setChiTietDonNhapHangs(Set<ChiTietDonNhapHang> chiTietDonNhapHangs) {
        this.chiTietDonNhapHangs = chiTietDonNhapHangs;
    }

    @Override
    public String toString() {
        return "DonNhapHang{" +
                "maDonNhapHang='" + maDonNhapHang + '\'' +
                ", ngayTao=" + ngayTao +
                ", trangThai=" + trangThai +
                ", tongTien=" + tongTien +
                ", giamGia=" + giamGia +
                ", phuThu=" + phuThu +
                ", VAT=" + VAT +
                ", phiVanChuyen=" + phiVanChuyen +
                ", tongThanhToan=" + tongThanhToan +
                ", tongSoLuong=" + tongSoLuong +
                ", soLuongConLai=" + soLuongConLai +
                ", daThanhToan=" + daThanhToan +
                ", congNo=" + congNo +
                ", ghiChu='" + ghiChu + '\'' +
                ", nhanVienTaoDon=" + nhanVienTaoDon +
                ", nhaCungCap=" + nhaCungCap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DonNhapHang that = (DonNhapHang) o;
        return Objects.equals(maDonNhapHang, that.maDonNhapHang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maDonNhapHang);
    }
}
