package com.example.fashionshop.mapper;

import com.example.fashionshop.model.ChiTietDonHangProperty;
import com.example.fashionshop.model.ChiTietDonNhapHangProperty;
import com.example.fashionshop.model.DonNhapHangProperty;
import com.example.model.ChiTietDonNhapHang;
import com.example.model.DonNhapHang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DonNhapHangMapper {

    public static DonNhapHang toDonNhapHang(DonNhapHangProperty donNhapHangProperty) {
        DonNhapHang donNhapHang = new DonNhapHang();
        donNhapHang.setMaDonNhapHang(donNhapHangProperty.getMaDonNhapHang());
        donNhapHang.setTrangThai(donNhapHangProperty.getTrangThai());
        donNhapHang.setNgayTao(donNhapHangProperty.getNgayTao());
        donNhapHang.setNhanVienTaoDon(donNhapHangProperty.getNhanVienTaoDon());
        donNhapHang.setNhaCungCap(donNhapHangProperty.getNhaCungCap());
        donNhapHang.setGiamGia(donNhapHangProperty.getGiamGia());
        donNhapHang.setPhuThu(donNhapHangProperty.getPhuThu());
        donNhapHang.setVAT(donNhapHangProperty.getVAT());
        donNhapHang.setPhiVanChuyen(donNhapHangProperty.getPhiVanChuyen());
        donNhapHang.setDaThanhToan(donNhapHangProperty.getDaThanhToan());
        donNhapHang.setGhiChu(donNhapHangProperty.getGhiChu());

        Set<ChiTietDonNhapHang> chiTietDonNhapHangs = new HashSet<>();
        donNhapHangProperty.getChiTietDonNhapHangs().forEach(chiTietDonNhapHangProperty -> {
            ChiTietDonNhapHang chiTietDonNhapHang = ChiTietDonNhapHangMapper.toChiTietDonNhapHang(chiTietDonNhapHangProperty);
            chiTietDonNhapHangs.add(chiTietDonNhapHang);
        });

        donNhapHang.setChiTietDonNhapHangs(chiTietDonNhapHangs);
        donNhapHang.tinhTongTien();
        donNhapHang.tinhTongThanhToan();
        donNhapHang.tinhCongNo();
        donNhapHang.tinhTongSoLuong();
        donNhapHang.tinhSoLuongConLai();

        return donNhapHang;
    }

    public static DonNhapHangProperty toDonNhapHangProperty(DonNhapHang donNhapHang) {
        DonNhapHangProperty donNhapHangProperty = new DonNhapHangProperty();
        donNhapHangProperty.setMaDonNhapHang(donNhapHang.getMaDonNhapHang());
        donNhapHangProperty.setTrangThai(donNhapHang.getTrangThai());
        donNhapHangProperty.setNgayTao(donNhapHang.getNgayTao());
        donNhapHangProperty.setNhanVienTaoDon(donNhapHang.getNhanVienTaoDon());
        donNhapHangProperty.setNhaCungCap(donNhapHang.getNhaCungCap());
        donNhapHangProperty.setGiamGia(donNhapHang.getGiamGia());
        donNhapHangProperty.setPhuThu(donNhapHang.getPhuThu());
        donNhapHangProperty.setVAT(donNhapHang.getVAT());
        donNhapHangProperty.setPhiVanChuyen(donNhapHang.getPhiVanChuyen());
        donNhapHangProperty.setDaThanhToan(donNhapHang.getDaThanhToan());
        donNhapHangProperty.setGhiChu(donNhapHang.getGhiChu());

        ObservableList<ChiTietDonNhapHangProperty> chiTietDonNhapHangProperties = FXCollections.observableArrayList();
        donNhapHang.getChiTietDonNhapHangs().forEach(chiTietDonNhapHang -> {
            ChiTietDonNhapHangProperty chiTietDonNhapHangProperty = ChiTietDonNhapHangMapper.toChiTietDonNhapHangProperty(chiTietDonNhapHang);
            chiTietDonNhapHangProperties.add(chiTietDonNhapHangProperty);
        });

        donNhapHangProperty.setChiTietDonNhapHangs(chiTietDonNhapHangProperties);

        return donNhapHangProperty;
    }

}
