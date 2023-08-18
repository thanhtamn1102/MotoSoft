package com.example.fashionshop.mapper;

import com.example.fashionshop.model.ChiTietDonHangProperty;
import com.example.model.ChiTietDonHang;

public class ChiTietDonHangMapper {

    public static ChiTietDonHang toChiTietDonHang(ChiTietDonHangProperty chiTietDonHangProperty) {
        ChiTietDonHang chiTietDonHang = new ChiTietDonHang();
        chiTietDonHang.setDonHang(chiTietDonHangProperty.getDonHang());
        chiTietDonHang.setSanPham(chiTietDonHangProperty.getSanPham());
        chiTietDonHang.setSoLuong(chiTietDonHangProperty.getSoLuong());
        chiTietDonHang.setDonGia(chiTietDonHangProperty.getDonGia());
        return chiTietDonHang;
    }

    public static ChiTietDonHangProperty toChiTietDonHangProperty(ChiTietDonHang chiTietDonHang) {
        ChiTietDonHangProperty chiTietDonHangProperty = new ChiTietDonHangProperty(
                chiTietDonHang.getDonHang(),
                chiTietDonHang.getSanPham(),
                chiTietDonHang.getSoLuong()
        );
        return chiTietDonHangProperty;
    }

}
