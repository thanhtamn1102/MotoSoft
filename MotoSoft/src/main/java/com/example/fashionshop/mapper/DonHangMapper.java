package com.example.fashionshop.mapper;

import com.example.fashionshop.model.ChiTietDonHangProperty;
import com.example.fashionshop.model.DonHangProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.model.ChiTietDonHang;
import com.example.model.DonHang;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DonHangMapper {

    public static DonHang toDonHang(DonHangProperty donHangProperty) {
        DonHang donHang = new DonHang();
        donHang.setMaDonHang(donHangProperty.getMaDonHang());
        donHang.setNgayTaoDonHang(donHangProperty.getNgayTaoDonHang());
        donHang.setGiamGia(donHangProperty.getGiamGia());
        donHang.setPhuThu(donHangProperty.getPhuThu());
        donHang.setVAT(donHangProperty.getVAT());
        donHang.setPhiVanChuyen(donHangProperty.getPhiVanChuyen());
        donHang.setGhiChu(donHangProperty.getGhiChu());
        donHang.setNhanVien(donHangProperty.getNhanVien());
        donHang.setKhachHang(donHangProperty.getKhachHang());
        donHang.setPhieuTraHang(donHangProperty.getPhieuTraHang());

        Set<ChiTietDonHang> chiTietDonHangs = donHangProperty.getChiTietDonHangs()
                .stream()
                .map(chiTietDonHangProperty -> ChiTietDonHangMapper.toChiTietDonHang(chiTietDonHangProperty))
                .collect(Collectors.toSet());

        donHang.setChiTietDonHangs(chiTietDonHangs);
        donHang.tinhTongTien();
        donHang.tinhTongThanhTien();

        return donHang;
    }

    public static DonHangProperty toDonHangProperty(DonHang donHang) {
        DonHangProperty donHangProperty = new DonHangProperty();
        donHangProperty.setMaDonHang(donHang.getMaDonHang());
        donHangProperty.setNgayTaoDonHang(donHang.getNgayTaoDonHang());
        donHangProperty.setGiamGia(donHang.getGiamGia());
        donHangProperty.setPhuThu(donHang.getPhuThu());
        donHangProperty.setVAT(donHang.getVAT());
        donHangProperty.setPhiVanChuyen(donHang.getPhiVanChuyen());
        donHangProperty.setGhiChu(donHang.getGhiChu());
        donHangProperty.setNhanVien(donHang.getNhanVien());
        donHangProperty.setKhachHang(donHang.getKhachHang());
        donHangProperty.setPhieuTraHang(donHang.getPhieuTraHang());

        ObservableList<ChiTietDonHangProperty> chiTietDonHangProperties = FXCollections.observableArrayList();
        chiTietDonHangProperties.addAll(
                donHang.getChiTietDonHangs()
                        .stream()
                        .map(chiTietDonHang -> ChiTietDonHangMapper.toChiTietDonHangProperty(chiTietDonHang))
                        .collect(Collectors.toList())
        );

        donHangProperty.setChiTietDonHangs(chiTietDonHangProperties);
        return donHangProperty;
    }

}
