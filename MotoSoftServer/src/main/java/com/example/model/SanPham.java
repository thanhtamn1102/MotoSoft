package com.example.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class SanPham implements Serializable {

    @Serial
    private static final long serialVersionUID = 7351104634429287164L;

    @Id
    @Column(columnDefinition = "varchar(10)")
    private String maSanPham;

    @Column(columnDefinition = "nvarchar(255)", nullable = false)
    private String tenSanPham;

    @Column(nullable = false)
    private double giaNhap;

    @Column(nullable = false)
    private double giaBan;

    @Column(columnDefinition = "nvarchar(MAX)")
    private String moTa;

    @Column(nullable = false)
    private int soLuong;

    @Column(nullable = false)
    private KichThuoc kichThuoc;

    @Column(nullable = false)
    private double khoiLuong;

    @Column(nullable = false)
    private double khoangCachTrucBanhXe;

    @Column(nullable = false)
    private double doCaoYen;

    @Column(nullable = false)
    private double khoangSangGamXe;

    @Column(nullable = false)
    private double dungTichBinhXang;

    @Column(columnDefinition = "nvarchar(32)", nullable = false)
    private String kichCoLopTruoc;

    @Column(columnDefinition = "nvarchar(32)", nullable = false)
    private String kichCoLopSau;

    @Column(columnDefinition = "nvarchar(128)", nullable = false)
    private String phuocTruoc;

    @Column(columnDefinition = "nvarchar(128)", nullable = false)
    private String phuocSau;

    @Column(columnDefinition = "nvarchar(255)", nullable = false)
    private String loaiDongCo;

    @Column(columnDefinition = "nvarchar(64)", nullable = false)
    private String congSuatToiDa;

    @Column(columnDefinition = "nvarchar(128)", nullable = false)
    private String dungTichNhotMay;

    @Column(nullable = false)
    private double mucTieuThuNhienLieu;

    @Column(columnDefinition = "nvarchar(128)", nullable = false)
    private String loaiTruyenDong;

    @Column(columnDefinition = "nvarchar(32)", nullable = false)
    private String heThongKhoiDong;

    @Column(columnDefinition = "nvarchar(128)", nullable = false)
    private String momentCucDai;

    @Column(nullable = false)
    private double dungTichXyLanh;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "HinhAnhSanPham",
            joinColumns = @JoinColumn(name = "maSanPham")
    )
    @Column(name = "hinhAnh", columnDefinition = "nvarchar(MAX)")
    private Set<String> hinhAnhs;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "SanPham_DanhMuc",
        joinColumns = @JoinColumn(name = "maSanPham", referencedColumnName = "maSanPham", columnDefinition = "varchar(10)"),
        inverseJoinColumns = @JoinColumn(name = "maDanhMuc", referencedColumnName = "maDanhMuc", columnDefinition = "varchar(10)")
    )
    private Set<DanhMuc> danhMucs;

    @ManyToOne
    @JoinColumn(name = "maMau", nullable = false)
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "maThuongHieu", nullable = false)
    private ThuongHieu thuongHieu;


    public SanPham() {
        hinhAnhs = new HashSet<>();
        danhMucs = new HashSet<>();
    }

    public SanPham(String tenSanPham, double giaNhap, double giaBan, String moTa, int soLuong, KichThuoc kichThuoc,
                   double khoiLuong, double khoangCachTrucBanhXe, double doCaoYen, double khoangSangGamXe, double dungTichBinhXang,
                   String kichCoLopTruoc, String kichCoLopSau, String phuocTruoc, String phuocSau, String loaiDongCo, String congSuatToiDa,
                   String dungTichNhotMay, double mucTieuThuNhienLieu, String loaiTruyenDong, String heThongKhoiDong, String momentCucDai,
                   double dungTichXyLanh, MauSac mauSac, ThuongHieu thuongHieu) {
        this();
        this.tenSanPham = tenSanPham;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.moTa = moTa;
        this.soLuong = soLuong;
        this.kichThuoc = kichThuoc;
        this.khoiLuong = khoiLuong;
        this.khoangCachTrucBanhXe = khoangCachTrucBanhXe;
        this.doCaoYen = doCaoYen;
        this.khoangSangGamXe = khoangSangGamXe;
        this.dungTichBinhXang = dungTichBinhXang;
        this.kichCoLopTruoc = kichCoLopTruoc;
        this.kichCoLopSau = kichCoLopSau;
        this.phuocTruoc = phuocTruoc;
        this.phuocSau = phuocSau;
        this.loaiDongCo = loaiDongCo;
        this.congSuatToiDa = congSuatToiDa;
        this.dungTichNhotMay = dungTichNhotMay;
        this.mucTieuThuNhienLieu = mucTieuThuNhienLieu;
        this.loaiTruyenDong = loaiTruyenDong;
        this.heThongKhoiDong = heThongKhoiDong;
        this.momentCucDai = momentCucDai;
        this.dungTichXyLanh = dungTichXyLanh;
        this.mauSac = mauSac;
        this.thuongHieu = thuongHieu;
    }

    public SanPham(String maSanPham, String tenSanPham, double giaNhap, double giaBan, String moTa, int soLuong, KichThuoc kichThuoc,
                   double khoiLuong, double khoangCachTrucBanhXe, double doCaoYen, double khoangSangGamXe, double dungTichBinhXang,
                   String kichCoLopTruoc, String kichCoLopSau, String phuocTruoc, String phuocSau, String loaiDongCo, String congSuatToiDa,
                   String dungTichNhotMay, double mucTieuThuNhienLieu, String loaiTruyenDong, String heThongKhoiDong, String momentCucDai,
                   double dungTichXyLanh, MauSac mauSac, ThuongHieu thuongHieu) {
        this(tenSanPham, giaNhap, giaBan, moTa, soLuong, kichThuoc,
                khoiLuong, khoangCachTrucBanhXe, doCaoYen, khoangSangGamXe,
                dungTichBinhXang, kichCoLopTruoc, kichCoLopSau, phuocTruoc,
                phuocSau, loaiDongCo, congSuatToiDa, dungTichNhotMay, mucTieuThuNhienLieu,
                loaiTruyenDong, heThongKhoiDong, momentCucDai, dungTichXyLanh, mauSac, thuongHieu);
        this.maSanPham = maSanPham;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public KichThuoc getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(KichThuoc kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public double getKhoiLuong() {
        return khoiLuong;
    }

    public void setKhoiLuong(double khoiLuong) {
        this.khoiLuong = khoiLuong;
    }

    public double getKhoangCachTrucBanhXe() {
        return khoangCachTrucBanhXe;
    }

    public void setKhoangCachTrucBanhXe(double khoangCachTrucBanhXe) {
        this.khoangCachTrucBanhXe = khoangCachTrucBanhXe;
    }

    public double getDoCaoYen() {
        return doCaoYen;
    }

    public void setDoCaoYen(double doCaoYen) {
        this.doCaoYen = doCaoYen;
    }

    public double getKhoangSangGamXe() {
        return khoangSangGamXe;
    }

    public void setKhoangSangGamXe(double khoangSangGamXe) {
        this.khoangSangGamXe = khoangSangGamXe;
    }

    public double getDungTichBinhXang() {
        return dungTichBinhXang;
    }

    public void setDungTichBinhXang(double dungTichBinhXang) {
        this.dungTichBinhXang = dungTichBinhXang;
    }

    public String getKichCoLopTruoc() {
        return kichCoLopTruoc;
    }

    public void setKichCoLopTruoc(String kichCoLopTruoc) {
        this.kichCoLopTruoc = kichCoLopTruoc;
    }

    public String getKichCoLopSau() {
        return kichCoLopSau;
    }

    public void setKichCoLopSau(String kichCoLopSau) {
        this.kichCoLopSau = kichCoLopSau;
    }

    public String getPhuocTruoc() {
        return phuocTruoc;
    }

    public void setPhuocTruoc(String phuocTruoc) {
        this.phuocTruoc = phuocTruoc;
    }

    public String getPhuocSau() {
        return phuocSau;
    }

    public void setPhuocSau(String phuocSau) {
        this.phuocSau = phuocSau;
    }

    public String getLoaiDongCo() {
        return loaiDongCo;
    }

    public void setLoaiDongCo(String loaiDongCo) {
        this.loaiDongCo = loaiDongCo;
    }

    public String getCongSuatToiDa() {
        return congSuatToiDa;
    }

    public void setCongSuatToiDa(String congSuatToiDa) {
        this.congSuatToiDa = congSuatToiDa;
    }

    public String getDungTichNhotMay() {
        return dungTichNhotMay;
    }

    public void setDungTichNhotMay(String dungTichNhotMay) {
        this.dungTichNhotMay = dungTichNhotMay;
    }

    public double getMucTieuThuNhienLieu() {
        return mucTieuThuNhienLieu;
    }

    public void setMucTieuThuNhienLieu(double mucTieuThuNhienLieu) {
        this.mucTieuThuNhienLieu = mucTieuThuNhienLieu;
    }

    public String getLoaiTruyenDong() {
        return loaiTruyenDong;
    }

    public void setLoaiTruyenDong(String loaiTruyenDong) {
        this.loaiTruyenDong = loaiTruyenDong;
    }

    public String getHeThongKhoiDong() {
        return heThongKhoiDong;
    }

    public void setHeThongKhoiDong(String heThongKhoiDong) {
        this.heThongKhoiDong = heThongKhoiDong;
    }

    public String getMomentCucDai() {
        return momentCucDai;
    }

    public void setMomentCucDai(String momentCucDai) {
        this.momentCucDai = momentCucDai;
    }

    public double getDungTichXyLanh() {
        return dungTichXyLanh;
    }

    public void setDungTichXyLanh(double dungTichXyLanh) {
        this.dungTichXyLanh = dungTichXyLanh;
    }

    public Set<String> getHinhAnhs() {
        return hinhAnhs;
    }

    public void setHinhAnhs(Set<String> hinhAnhs) {
        this.hinhAnhs.clear();
        this.hinhAnhs.addAll(hinhAnhs);
    }

    public Set<DanhMuc> getDanhMucs() {
        return danhMucs;
    }

    public void setDanhMucs(Set<DanhMuc> danhMucs) {
        this.danhMucs.clear();
        this.danhMucs.addAll(danhMucs);
    }

    public MauSac getMauSac() {
        return mauSac;
    }

    public void setMauSac(MauSac mauSac) {
        this.mauSac = mauSac;
    }

    public ThuongHieu getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(ThuongHieu thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    @Override
    public String toString() {
        return maSanPham + '\t' + tenSanPham;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SanPham sanPham = (SanPham) o;
        return Objects.equals(maSanPham, sanPham.maSanPham);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maSanPham);
    }
}
