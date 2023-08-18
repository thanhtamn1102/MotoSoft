package com.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class TaiKhoan implements Serializable {

    @Serial
    private static final long serialVersionUID = 4767643781823220092L;

    @Id
    @Column(columnDefinition = "varchar(8)")
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name="property", value = "nhanVien"))
    private String maNhanVien;

    @OneToOne
    @JoinColumn(name = "maNhanVien", columnDefinition = "varchar(8)")
    @MapsId
    private NhanVien nhanVien;

    @Column(columnDefinition = "varchar(128)", nullable = false)
    private String matKhau;

    @Column(columnDefinition = "varchar(36)", nullable = false)
    private String chuoiRiengTu;

    @Column(nullable = false)
    private boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "maVaiTro", nullable = false)
    private VaiTro vaiTro;

    public TaiKhoan() {

    }

    public TaiKhoan(NhanVien nhanVien, String matKhau, String chuoiRiengTu, boolean trangThai) {
        this.nhanVien = nhanVien;
        this.matKhau = matKhau;
        this.chuoiRiengTu = chuoiRiengTu;
        this.trangThai = trangThai;
    }

    public TaiKhoan(NhanVien nhanVien, String matKhau, String chuoiRiengTu, boolean trangThai, VaiTro vaiTro) {
        this(nhanVien, matKhau, chuoiRiengTu, trangThai);
        this.vaiTro = vaiTro;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getChuoiRiengTu() {
        return chuoiRiengTu;
    }

    public void setChuoiRiengTu(String chuoiRiengTu) {
        this.chuoiRiengTu = chuoiRiengTu;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public VaiTro getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(VaiTro vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "nhanVien=" + nhanVien +
                ", matKhau='" + matKhau + '\'' +
                ", chuoiRiengTu='" + chuoiRiengTu + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaiKhoan taiKhoan = (TaiKhoan) o;
        return maNhanVien == taiKhoan.getNhanVien().getMaNhanVien();
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNhanVien);
    }
}
