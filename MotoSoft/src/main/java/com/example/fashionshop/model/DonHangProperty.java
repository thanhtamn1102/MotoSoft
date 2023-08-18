package com.example.fashionshop.model;

import com.example.model.*;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
public class DonHangProperty implements Cloneable {

    private StringProperty maDonHang;
    private ObjectProperty<LocalDateTime> ngayTaoDonHang;
    private DoubleBinding tongTien;
    private DoubleProperty giamGia;
    private DoubleProperty phuThu;
    private DoubleProperty VAT;
    private DoubleProperty phiVanChuyen;
    private DoubleBinding tongThanhTien;
    private StringProperty ghiChu;
    private ObjectProperty<NhanVien> nhanVien;
    private ObjectProperty<KhachHang> khachHang;
    private ObservableList<ChiTietDonHangProperty> chiTietDonHangs;
    private ObjectProperty<PhieuTraHang> phieuTraHang;

    public DonHangProperty() {
        maDonHang = new SimpleStringProperty();
        ngayTaoDonHang = new SimpleObjectProperty<>();
        giamGia = new SimpleDoubleProperty();
        phuThu = new SimpleDoubleProperty();
        VAT = new SimpleDoubleProperty();
        phiVanChuyen = new SimpleDoubleProperty();
        ghiChu = new SimpleStringProperty();
        nhanVien = new SimpleObjectProperty<>();
        khachHang = new SimpleObjectProperty<>();
        chiTietDonHangs = FXCollections.observableArrayList();
        phieuTraHang = new SimpleObjectProperty<>();

        tongTien = new SumTotalMoneyOfOrderForListOfSimpleElementsDoubleBinding(chiTietDonHangs);

        tongThanhTien = new DoubleBinding() {
            {
                super.bind(tongTien, giamGia, phuThu, VAT, phiVanChuyen);
            }
            @Override
            protected double computeValue() {
                return tongTien.get() * (1 + VAT.get() / 100) + phiVanChuyen.get() + phuThu.get() - giamGia.get();
            }
        };
    }

    public String getMaDonHang() {
        return maDonHang.get();
    }

    public StringProperty maDonHangProperty() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang.set(maDonHang);
    }

    public LocalDateTime getNgayTaoDonHang() {
        return ngayTaoDonHang.get();
    }

    public ObjectProperty<LocalDateTime> ngayTaoDonHangProperty() {
        return ngayTaoDonHang;
    }

    public void setNgayTaoDonHang(LocalDateTime ngayTaoDonHang) {
        this.ngayTaoDonHang.set(ngayTaoDonHang);
    }

    public double getTongTien() {
        return tongTien.get();
    }

    public DoubleBinding tongTienProperty() {
        return tongTien;
    }

    public double getGiamGia() {
        return giamGia.get();
    }

    public DoubleProperty giamGiaProperty() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia.set(giamGia);
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

    public double getVAT() {
        return VAT.get();
    }

    public DoubleProperty VATProperty() {
        return VAT;
    }

    public void setVAT(double VAT) {
        this.VAT.set(VAT);
    }

    public double getPhiVanChuyen() {
        return phiVanChuyen.get();
    }

    public DoubleProperty phiVanChuyenProperty() {
        return phiVanChuyen;
    }

    public void setPhiVanChuyen(double phiVanChuyen) {
        this.phiVanChuyen.set(phiVanChuyen);
    }

    public double getTongThanhTien() {
        return tongThanhTien.get();
    }

    public DoubleBinding tongThanhTienProperty() {
        return tongThanhTien;
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

    public KhachHang getKhachHang() {
        return khachHang.get();
    }

    public ObjectProperty<KhachHang> khachHangProperty() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang.set(khachHang);
    }

    public ObservableList<ChiTietDonHangProperty> getChiTietDonHangs() {
        return chiTietDonHangs;
    }

    public void setChiTietDonHangs(ObservableList<ChiTietDonHangProperty> chiTietDonHangs) {
        this.chiTietDonHangs.setAll(chiTietDonHangs);
    }

    public PhieuTraHang getPhieuTraHang() {
        return phieuTraHang.get();
    }

    public ObjectProperty<PhieuTraHang> phieuTraHangProperty() {
        return phieuTraHang;
    }

    public void setPhieuTraHang(PhieuTraHang phieuTraHang) {
        this.phieuTraHang.set(phieuTraHang);
    }

    @Override
    public DonHangProperty clone() throws CloneNotSupportedException {
        super.clone();

        DonHangProperty donHangProperty = new DonHangProperty();
        donHangProperty.setMaDonHang(getMaDonHang());
        donHangProperty.setNgayTaoDonHang(getNgayTaoDonHang());
        donHangProperty.setGiamGia(getGiamGia());
        donHangProperty.setPhuThu(getPhuThu());
        donHangProperty.setVAT(getVAT());
        donHangProperty.setPhiVanChuyen(getPhiVanChuyen());
        donHangProperty.setGhiChu(getGhiChu());
        donHangProperty.setNhanVien(getNhanVien());
        donHangProperty.setKhachHang(getKhachHang());
        donHangProperty.setChiTietDonHangs(getChiTietDonHangs());
        donHangProperty.setPhieuTraHang(getPhieuTraHang());
        return donHangProperty;
    }


    private class SumTotalMoneyOfOrderForListOfSimpleElementsDoubleBinding extends DoubleBinding {

        // Reference to our observable list
        private final ObservableList<ChiTietDonHangProperty> boundList;

        // Array of currently observed properties of elements of our list
        private DoubleBinding[] observedProperties = {};

        // Listener that has to call rebinding in response of any change in observable list
        private final ListChangeListener<ChiTietDonHangProperty> BOUND_LIST_CHANGE_LISTENER
                = (ListChangeListener.Change<? extends ChiTietDonHangProperty> change) -> {
            refreshBinding();
        };

        SumTotalMoneyOfOrderForListOfSimpleElementsDoubleBinding(ObservableList<ChiTietDonHangProperty> boundList) {
            this.boundList = boundList;
            boundList.addListener(BOUND_LIST_CHANGE_LISTENER);
            refreshBinding();
        }

        @Override
        protected double computeValue() {
            int i = 0;
            for (DoubleBinding bp : observedProperties) {
                i += bp.get();
            }

            return i;
        }

        @Override
        public void dispose() {
            boundList.removeListener(BOUND_LIST_CHANGE_LISTENER);
            unbind(observedProperties);
        }

        private void refreshBinding() {
            // Clean old properties from IntegerBinding's inner listener
            unbind(observedProperties);

            // Load new properties
            List<DoubleBinding> tmplist = new ArrayList<>();
            boundList.stream().map((boundList1) -> boundList1.thanhTienProperty()).forEach((integerProperty) -> {
                tmplist.add(integerProperty);
            });

            observedProperties = tmplist.toArray(new DoubleBinding[0]);

            // Bind IntegerBinding's inner listener to all new properties
            super.bind(observedProperties);

            // Invalidate binding to generate events
            // Eager/Lazy recalc depends on type of listeners attached to this instance
            // see IntegerBinding sources
            this.invalidate();
        }
    }
}
