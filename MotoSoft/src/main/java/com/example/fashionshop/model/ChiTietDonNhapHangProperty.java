package com.example.fashionshop.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.*;
import com.example.model.ChiTietDonNhapHang;
import com.example.model.DonNhapHang;
import com.example.model.SanPham;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ChiTietDonNhapHangProperty {

    @EqualsAndHashCode.Include
    private ObjectProperty<DonNhapHang> donNhapHang;

    @EqualsAndHashCode.Include
    private ObjectProperty<SanPham> sanPham;

    private DoubleProperty giaNhap;
    private IntegerProperty soLuong;
    private IntegerProperty soLuongDaNhan;
    private IntegerBinding soLuongConLai;
    private DoubleBinding thanhTien;

    public ChiTietDonNhapHangProperty() {
        donNhapHang = new SimpleObjectProperty<>();
        sanPham = new SimpleObjectProperty<>();
        giaNhap = new SimpleDoubleProperty(0);
        soLuong = new SimpleIntegerProperty(0);
        soLuongDaNhan = new SimpleIntegerProperty(0);

        thanhTien = (DoubleBinding) Bindings.multiply(soLuong, giaNhap);
        soLuongConLai = (IntegerBinding) Bindings.subtract(soLuong, soLuongDaNhan);
    }

    public DonNhapHang getDonNhapHang() {
        return donNhapHang.get();
    }

    public ObjectProperty<DonNhapHang> donNhapHangProperty() {
        return donNhapHang;
    }

    public void setDonNhapHang(DonNhapHang donNhapHang) {
        this.donNhapHang.set(donNhapHang);
    }

    public SanPham getSanPham() {
        return sanPham.get();
    }

    public ObjectProperty<SanPham> sanPhamProperty() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham.set(sanPham);
    }

    public double getGiaNhap() {
        return giaNhap.get();
    }

    public DoubleProperty giaNhapProperty() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap.set(giaNhap);
    }

    public int getSoLuong() {
        return soLuong.get();
    }

    public IntegerProperty soLuongProperty() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong.set(soLuong);
    }

    public int getSoLuongDaNhan() {
        return soLuongDaNhan.get();
    }

    public IntegerProperty soLuongDaNhanProperty() {
        return soLuongDaNhan;
    }

    public void setSoLuongDaNhan(int soLuongDaNhan) {
        this.soLuongDaNhan.set(soLuongDaNhan);
    }

    public int getSoLuongConLai() {
        return soLuongConLai.get();
    }

    public IntegerBinding soLuongConLaiProperty() {
        return soLuongConLai;
    }

    public Number getThanhTien() {
        return thanhTien.get();
    }

    public DoubleBinding thanhTienProperty() {
        return thanhTien;
    }
}
