package com.example.fashionshop.model;

import com.example.model.DonHang;
import com.example.model.NhanVien;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.time.LocalDateTime;

public class PhieuTraHangProperty {

    private StringProperty maPhieuTraHang;
    private ObjectProperty<DonHang> donHang;
    private ObjectProperty<LocalDateTime> ngayTaoPhieu;
    private DoubleProperty tiLeTienHoanLai;
    private DoubleBinding tienHoanLai;
    private DoubleProperty phuThu;
    private DoubleBinding tongTienHoan;
    private StringProperty ghiChu;
    private ObjectProperty<NhanVien> nhanVien;
    private ObservableSet<String> dsHinhAnh;

    public PhieuTraHangProperty() {
        maPhieuTraHang = new SimpleStringProperty();
        donHang = new SimpleObjectProperty<>();
        ngayTaoPhieu = new SimpleObjectProperty<>();
        tiLeTienHoanLai = new SimpleDoubleProperty();
        phuThu = new SimpleDoubleProperty();
        ghiChu = new SimpleStringProperty();
        nhanVien = new SimpleObjectProperty<>();
        dsHinhAnh = FXCollections.observableSet();

        tienHoanLai = new DoubleBinding() {
            {
                super.bind(donHang, tiLeTienHoanLai);
            }
            @Override
            protected double computeValue() {
                return (donHang.get() == null ? 0 : donHang.get().getTongThanhTien()) * tiLeTienHoanLai.get() / 100;
            }
        };

        tongTienHoan = (DoubleBinding) Bindings.add(tienHoanLai, phuThu);
    }

    public String getMaPhieuTraHang() {
        return maPhieuTraHang.get();
    }

    public StringProperty maPhieuTraHangProperty() {
        return maPhieuTraHang;
    }

    public void setMaPhieuTraHang(String maPhieuTraHang) {
        this.maPhieuTraHang.set(maPhieuTraHang);
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

    public LocalDateTime getNgayTaoPhieu() {
        return ngayTaoPhieu.get();
    }

    public ObjectProperty<LocalDateTime> ngayTaoPhieuProperty() {
        return ngayTaoPhieu;
    }

    public void setNgayTaoPhieu(LocalDateTime ngayTaoPhieu) {
        this.ngayTaoPhieu.set(ngayTaoPhieu);
    }

    public double getTiLeTienHoanLai() {
        return tiLeTienHoanLai.get();
    }

    public DoubleProperty tiLeTienHoanLaiProperty() {
        return tiLeTienHoanLai;
    }

    public void setTiLeTienHoanLai(double tiLeTienHoanLai) {
        this.tiLeTienHoanLai.set(tiLeTienHoanLai);
    }

    public double getTienHoanLai() {
        return tienHoanLai.get();
    }

    public DoubleBinding tienHoanLaiProperty() {
        return tienHoanLai;
    }

    public double getPhuThu() {
        return phuThu.get();
    }

    public DoubleProperty phuThuProperty() {
        return phuThu;
    }

    public void setPhuThu(double phuThu) {
        this.phuThu.set(phuThu);
    }

    public double getTongTienHoan() {
        return tongTienHoan.get();
    }

    public DoubleBinding tongTienHoanProperty() {
        return tongTienHoan;
    }

    public String getGhiChu() {
        return ghiChu.get();
    }

    public StringProperty ghiChuProperty() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu.set(ghiChu);
    }

    public NhanVien getNhanVien() {
        return nhanVien.get();
    }

    public ObjectProperty<NhanVien> nhanVienProperty() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien.set(nhanVien);
    }

    public ObservableSet<String> getDsHinhAnh() {
        return dsHinhAnh;
    }

    public void setDsHinhAnh(ObservableSet<String> dsHinhAnh) {
        this.dsHinhAnh = dsHinhAnh;
    }
}
