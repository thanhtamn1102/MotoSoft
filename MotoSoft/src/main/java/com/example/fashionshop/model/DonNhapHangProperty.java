package com.example.fashionshop.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import com.example.model.*;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
public class DonNhapHangProperty implements Cloneable {

    private StringProperty maDonNhapHang;
    private ObjectProperty<LocalDateTime> ngayTao;
    private ObjectProperty<DonNhapHangStatus> trangThai;
    private DoubleBinding tongTien;
    private DoubleProperty giamGia;
    private DoubleProperty phuThu;
    private DoubleProperty VAT;
    private DoubleProperty phiVanChuyen;
    private DoubleBinding tongThanhToan;
    private IntegerBinding tongSoLuong;
    private IntegerBinding soLuongConLai;
    private DoubleProperty daThanhToan;
    private DoubleBinding congNo;
    private StringProperty ghiChu;
    private ObjectProperty<NhanVien> nhanVienTaoDon;
    private ObjectProperty<NhaCungCap> nhaCungCap;
    private ObservableList<ChiTietDonNhapHangProperty> chiTietDonNhapHangs;

    public DonNhapHangProperty() {
        this.maDonNhapHang = new SimpleStringProperty();
        this.ngayTao = new SimpleObjectProperty<>();
        this.trangThai = new SimpleObjectProperty<>();
        this.giamGia = new SimpleDoubleProperty();
        this.phuThu = new SimpleDoubleProperty();
        this.VAT = new SimpleDoubleProperty();
        this.phiVanChuyen = new SimpleDoubleProperty();
        this.daThanhToan = new SimpleDoubleProperty();
        this.ghiChu = new SimpleStringProperty();
        this.nhanVienTaoDon = new SimpleObjectProperty<>();
        this.nhaCungCap = new SimpleObjectProperty<>();
        this.chiTietDonNhapHangs = FXCollections.observableArrayList();

        this.tongTien = new SumTotalMoneyOfImportOrderForListOfSimpleElementsDoubleBinding(chiTietDonNhapHangs);

        this.tongThanhToan = new DoubleBinding() {
            {
                super.bind(tongTien, VAT, phiVanChuyen, phuThu, giamGia);
            }
            @Override
            protected double computeValue() {
                return tongTien.get() * (1 + VAT.get() / 100) + phiVanChuyen.get() + phuThu.get() - giamGia.get();
            }
        };

        this.congNo = (DoubleBinding) Bindings.subtract(tongThanhToan, daThanhToan);
        this.tongSoLuong = new SumTotalProductOfImportOrderForListOfSimpleElementsDoubleBinding(chiTietDonNhapHangs);
        this.soLuongConLai = new SumRemainingAmountOfImportOrderForListOfSimpleElementsDoubleBinding(chiTietDonNhapHangs);
    }

    public String getMaDonNhapHang() {
        return maDonNhapHang.get();
    }

    public StringProperty maDonNhapHangProperty() {
        return maDonNhapHang;
    }

    public void setMaDonNhapHang(String maDonNhapHang) {
        this.maDonNhapHang.set(maDonNhapHang);
    }

    public LocalDateTime getNgayTao() {
        return ngayTao.get();
    }

    public ObjectProperty<LocalDateTime> ngayTaoProperty() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao.set(ngayTao);
    }

    public DonNhapHangStatus getTrangThai() {
        return trangThai.get();
    }

    public ObjectProperty<DonNhapHangStatus> trangThaiProperty() {
        return trangThai;
    }

    public void setTrangThai(DonNhapHangStatus trangThai) {
        this.trangThai.set(trangThai);
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

    public double getTongThanhToan() {
        return tongThanhToan.get();
    }

    public DoubleBinding tongThanhToanProperty() {
        return tongThanhToan;
    }

    public double getTongSoLuong() {
        return tongSoLuong.get();
    }

    public IntegerBinding tongSoLuongProperty() {
        return tongSoLuong;
    }

    public double getSoLuongConLai() {
        return soLuongConLai.get();
    }

    public IntegerBinding soLuongConLaiProperty() {
        return soLuongConLai;
    }

    public double getDaThanhToan() {
        return daThanhToan.get();
    }

    public DoubleProperty daThanhToanProperty() {
        return daThanhToan;
    }

    public void setDaThanhToan(double daThanhToan) {
        this.daThanhToan.set(daThanhToan);
    }

    public double getCongNo() {
        return congNo.get();
    }

    public DoubleBinding congNoProperty() {
        return congNo;
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

    public NhanVien getNhanVienTaoDon() {
        return nhanVienTaoDon.get();
    }

    public ObjectProperty<NhanVien> nhanVienTaoDonProperty() {
        return nhanVienTaoDon;
    }

    public void setNhanVienTaoDon(NhanVien nhanVienTaoDon) {
        this.nhanVienTaoDon.set(nhanVienTaoDon);
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap.get();
    }

    public ObjectProperty<NhaCungCap> nhaCungCapProperty() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap.set(nhaCungCap);
    }

    public ObservableList<ChiTietDonNhapHangProperty> getChiTietDonNhapHangs() {
        return chiTietDonNhapHangs;
    }

    public void setChiTietDonNhapHangs(ObservableList<ChiTietDonNhapHangProperty> chiTietDonNhapHangs) {
        this.chiTietDonNhapHangs.setAll(chiTietDonNhapHangs);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();

        DonNhapHangProperty donNhapHangProperty = new DonNhapHangProperty();
        donNhapHangProperty.setMaDonNhapHang(getMaDonNhapHang());
        donNhapHangProperty.setNgayTao(getNgayTao());
        donNhapHangProperty.setGiamGia(getGiamGia());
        donNhapHangProperty.setPhuThu(getPhuThu());
        donNhapHangProperty.setVAT(getVAT());
        donNhapHangProperty.setPhiVanChuyen(getPhiVanChuyen());
        donNhapHangProperty.setDaThanhToan(getDaThanhToan());
        donNhapHangProperty.setGhiChu(getGhiChu());
        donNhapHangProperty.setNhanVienTaoDon(getNhanVienTaoDon());
        donNhapHangProperty.setNhaCungCap(getNhaCungCap());
        donNhapHangProperty.setChiTietDonNhapHangs(getChiTietDonNhapHangs());

        return donNhapHangProperty;
    }


    private class SumTotalMoneyOfImportOrderForListOfSimpleElementsDoubleBinding extends DoubleBinding {

        // Reference to our observable list
        private final ObservableList<ChiTietDonNhapHangProperty> boundList;

        // Array of currently observed properties of elements of our list
        private DoubleBinding[] observedProperties = {};

        // Listener that has to call rebinding in response of any change in observable list
        private final ListChangeListener<ChiTietDonNhapHangProperty> BOUND_LIST_CHANGE_LISTENER
                = (ListChangeListener.Change<? extends ChiTietDonNhapHangProperty> change) -> {
            refreshBinding();
        };

        SumTotalMoneyOfImportOrderForListOfSimpleElementsDoubleBinding(ObservableList<ChiTietDonNhapHangProperty> boundList) {
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

    private class SumTotalProductOfImportOrderForListOfSimpleElementsDoubleBinding extends IntegerBinding {

        // Reference to our observable list
        private final ObservableList<ChiTietDonNhapHangProperty> boundList;

        // Array of currently observed properties of elements of our list
        private IntegerProperty[] observedProperties = {};

        // Listener that has to call rebinding in response of any change in observable list
        private final ListChangeListener<ChiTietDonNhapHangProperty> BOUND_LIST_CHANGE_LISTENER
                = (ListChangeListener.Change<? extends ChiTietDonNhapHangProperty> change) -> {
            refreshBinding();
        };

        SumTotalProductOfImportOrderForListOfSimpleElementsDoubleBinding(ObservableList<ChiTietDonNhapHangProperty> boundList) {
            this.boundList = boundList;
            boundList.addListener(BOUND_LIST_CHANGE_LISTENER);
            refreshBinding();
        }

        @Override
        protected int computeValue() {
            int i = 0;
            for (IntegerProperty bp : observedProperties) {
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
            List<IntegerProperty> tmplist = new ArrayList<>();
            boundList.stream().map((boundList1) -> boundList1.soLuongProperty()).forEach((integerProperty) -> {
                tmplist.add(integerProperty);
            });

            observedProperties = tmplist.toArray(new IntegerProperty[0]);

            // Bind IntegerBinding's inner listener to all new properties
            super.bind(observedProperties);

            // Invalidate binding to generate events
            // Eager/Lazy recalc depends on type of listeners attached to this instance
            // see IntegerBinding sources
            this.invalidate();
        }
    }

    private class SumRemainingAmountOfImportOrderForListOfSimpleElementsDoubleBinding extends IntegerBinding {

        // Reference to our observable list
        private final ObservableList<ChiTietDonNhapHangProperty> boundList;

        // Array of currently observed properties of elements of our list
        private IntegerBinding[] observedProperties = {};

        // Listener that has to call rebinding in response of any change in observable list
        private final ListChangeListener<ChiTietDonNhapHangProperty> BOUND_LIST_CHANGE_LISTENER
                = (ListChangeListener.Change<? extends ChiTietDonNhapHangProperty> change) -> {
            refreshBinding();
        };

        SumRemainingAmountOfImportOrderForListOfSimpleElementsDoubleBinding(ObservableList<ChiTietDonNhapHangProperty> boundList) {
            this.boundList = boundList;
            boundList.addListener(BOUND_LIST_CHANGE_LISTENER);
            refreshBinding();
        }

        @Override
        protected int computeValue() {
            int i = 0;
            for (IntegerBinding bp : observedProperties) {
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
            List<IntegerBinding> tmplist = new ArrayList<>();
            boundList.stream().map((boundList1) -> boundList1.soLuongConLaiProperty()).forEach((integerProperty) -> {
                tmplist.add(integerProperty);
            });

            observedProperties = tmplist.toArray(new IntegerBinding[0]);

            // Bind IntegerBinding's inner listener to all new properties
            super.bind(observedProperties);

            // Invalidate binding to generate events
            // Eager/Lazy recalc depends on type of listeners attached to this instance
            // see IntegerBinding sources
            this.invalidate();
        }
    }
}