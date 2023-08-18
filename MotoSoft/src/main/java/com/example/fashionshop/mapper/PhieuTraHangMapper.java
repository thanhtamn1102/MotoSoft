package com.example.fashionshop.mapper;

import com.example.fashionshop.model.ChiTietDonHangProperty;
import com.example.fashionshop.model.PhieuTraHangProperty;
import com.example.model.PhieuTraHang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PhieuTraHangMapper {

    public static PhieuTraHang toPhieuTraHang(PhieuTraHangProperty phieuTraHangProperty) {
        PhieuTraHang phieuTraHang = new PhieuTraHang();
        phieuTraHang.setDonHang(phieuTraHangProperty.getDonHang());
        phieuTraHang.setNgayTaoPhieu(phieuTraHangProperty.getNgayTaoPhieu());
        phieuTraHang.setTiLeHoanTien(phieuTraHangProperty.getTiLeTienHoanLai());
        phieuTraHang.setPhuThu(phieuTraHangProperty.getPhuThu());
        phieuTraHang.setGhiChu(phieuTraHangProperty.getGhiChu());
        phieuTraHang.setNhanVien(phieuTraHangProperty.getNhanVien());

        Set<String> hinhAnhs = new HashSet<>();
        hinhAnhs.addAll(phieuTraHangProperty.getDsHinhAnh());

        phieuTraHang.setDsHinhAnh(hinhAnhs);
        phieuTraHang.tinhTienHoanLai();
        phieuTraHang.tinhTongTienHoan();

        return phieuTraHang;
    }

    public static PhieuTraHangProperty toPhieuTraHangProperty(PhieuTraHang phieuTraHang) {
        PhieuTraHangProperty phieuTraHangProperty = new PhieuTraHangProperty();
        phieuTraHangProperty.setDonHang(phieuTraHang.getDonHang());
        phieuTraHangProperty.setNgayTaoPhieu(phieuTraHang.getNgayTaoPhieu());
        phieuTraHangProperty.setTiLeTienHoanLai(phieuTraHang.getTiLeHoanTien());
        phieuTraHangProperty.setPhuThu(phieuTraHang.getPhuThu());
        phieuTraHangProperty.setGhiChu(phieuTraHang.getGhiChu());
        phieuTraHangProperty.setNhanVien(phieuTraHang.getNhanVien());

        ObservableSet<String> dsHinhAnh = FXCollections.observableSet();
        dsHinhAnh.addAll(phieuTraHang.getDsHinhAnh());

        phieuTraHangProperty.setDsHinhAnh(dsHinhAnh);

        return phieuTraHangProperty;
    }

}
