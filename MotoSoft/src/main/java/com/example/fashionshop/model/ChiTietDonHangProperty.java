package com.example.fashionshop.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import lombok.*;
import com.example.model.ChiTietDonHang;
import com.example.model.DonHang;
import com.example.model.SanPham;

@ToString
public class ChiTietDonHangProperty {

    private ObjectProperty<DonHang> donHang;
    private ObjectProperty<SanPham> sanPham;
    private IntegerProperty soLuong;
    private DoubleBinding donGia;
    private DoubleBinding thanhTien;


    public ChiTietDonHangProperty(DonHang dh, SanPham sp, int sl) {
        donHang = new SimpleObjectProperty<>(dh);
        sanPham = new SimpleObjectProperty<>(sp);
        soLuong = new SimpleIntegerProperty(sl);

        donGia = Bindings.createDoubleBinding(() -> sanPham.getValue() == null ? null : sanPham.getValue().getGiaBan(), sanPham);
        thanhTien = (DoubleBinding) Bindings.multiply(soLuong, donGia);
    }

    public DonHang getDonHang() {
        return donHang.get();
    }

    public ObjectProperty<DonHang> donHangProperty() {
        return donHang;
    }

    public void setDonHang(DonHang donHang) {
        this.donHang.set(donHang);
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

    public double getDonGia() {
        return donGia.get();
    }

    public DoubleBinding donGiaProperty() {
        return donGia;
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

    public double getThanhTien() {
        return thanhTien.get();
    }

    public DoubleBinding thanhTienProperty() {
        return thanhTien;
    }
}
