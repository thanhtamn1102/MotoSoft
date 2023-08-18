package com.example.fashionshop.mapper;

import com.example.fashionshop.model.ChiTietDonNhapHangProperty;
import com.example.model.ChiTietDonNhapHang;

public class ChiTietDonNhapHangMapper {

    public static ChiTietDonNhapHang toChiTietDonNhapHang(ChiTietDonNhapHangProperty chiTietDonNhapHangProperty) {
        ChiTietDonNhapHang chiTietDonNhapHang = new ChiTietDonNhapHang();
        chiTietDonNhapHang.setDonNhapHang(chiTietDonNhapHangProperty.getDonNhapHang());
        chiTietDonNhapHang.setSanPham(chiTietDonNhapHangProperty.getSanPham());
        chiTietDonNhapHang.setSoLuong(chiTietDonNhapHangProperty.getSoLuong());
        chiTietDonNhapHang.setGiaNhap(chiTietDonNhapHangProperty.getGiaNhap());
        chiTietDonNhapHang.setSoLuongDaNhan(chiTietDonNhapHangProperty.getSoLuongDaNhan());

        return chiTietDonNhapHang;
    }

    public static ChiTietDonNhapHangProperty toChiTietDonNhapHangProperty(ChiTietDonNhapHang chiTietDonNhapHang) {
        ChiTietDonNhapHangProperty chiTietDonNhapHangProperty = new ChiTietDonNhapHangProperty();
        chiTietDonNhapHangProperty.setDonNhapHang(chiTietDonNhapHang.getDonNhapHang());
        chiTietDonNhapHangProperty.setSanPham(chiTietDonNhapHang.getSanPham());
        chiTietDonNhapHangProperty.setSoLuong(chiTietDonNhapHang.getSoLuong());
        chiTietDonNhapHangProperty.setGiaNhap(chiTietDonNhapHang.getGiaNhap());
        chiTietDonNhapHangProperty.setSoLuongDaNhan(chiTietDonNhapHang.getSoLuongDaNhan());

        return chiTietDonNhapHangProperty;
    }

}
