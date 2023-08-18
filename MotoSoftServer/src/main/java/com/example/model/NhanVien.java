package com.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class NhanVien implements Serializable {

    @Serial
    private static final long serialVersionUID = -3224406263536579921L;

    @Id
    @NaturalId
    @Column(columnDefinition = "varchar(8)")
    private String maNhanVien;

    @Column(columnDefinition = "nvarchar(32)", nullable = false)
    private String hoTen;

    @Column(nullable = false)
    private Date ngaySinh;

    @Column(nullable = false)
    private boolean gioiTinh;

    @Column(columnDefinition = "varchar(12)", nullable = false)
    private String cccd;

    @Column(columnDefinition = "varchar(10)", nullable = false)
    private String sdt;

    @Column(columnDefinition = "varchar(64)", nullable = false)
    private String email;

    @Column(nullable = false)
    private DiaChi diaChi;

    @Column(columnDefinition = "nvarchar(255)", nullable = false)
    private String hinhAnh;

    @ManyToOne
    @JoinColumn(name = "maChucVu", nullable = false)
    private ChucVu chucVu;

    @OneToOne
    @PrimaryKeyJoinColumn
    private TaiKhoan taiKhoan;

    public NhanVien() {

    }

    public NhanVien(String maNhanVien, String hoTen, Date ngaySinh, boolean gioiTinh, String cccd,
                    String sdt, String email, DiaChi diaChi, String hinhAnh, ChucVu chucVu) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.cccd = cccd;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
        this.hinhAnh = hinhAnh;
        this.chucVu = chucVu;
    }

    public NhanVien(String maNhanVien, String hoTen, Date ngaySinh, boolean gioiTinh, String cccd,
                    String sdt, String email, DiaChi diaChi, String hinhAnh, ChucVu chucVu, TaiKhoan taiKhoan) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.cccd = cccd;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
        this.hinhAnh = hinhAnh;
        this.chucVu = chucVu;
        this.taiKhoan = taiKhoan;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DiaChi getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(DiaChi diaChi) {
        this.diaChi = diaChi;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public ChucVu getChucVu() {
        return chucVu;
    }

    public void setChucVu(ChucVu chucVu) {
        this.chucVu = chucVu;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NhanVien nhanVien = (NhanVien) o;
        return maNhanVien == nhanVien.getMaNhanVien();
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNhanVien);
    }

    @Override
    public String toString() {
        return maNhanVien + " - " + hoTen;
    }
}
