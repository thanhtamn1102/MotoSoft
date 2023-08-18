package com.example.fashionshop.controller;

import com.example.fashionshop.cus_comp.ProductItem1;
import com.example.fashionshop.cus_comp.ProductItemCategoryView;
//import com.example.fashionshop.impl.*;
import com.example.fashionshop.mapper.DonNhapHangMapper;
import com.example.fashionshop.model.*;
//import com.example.fashionshop.service.*;
import com.example.fashionshop.values.StringValues;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.model.*;
import com.example.service.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class FormThemSPVaoDNH implements Initializable {

    private Context context;
    @FXML private TextField txtMaSanPham;
    @FXML private TextField txtTenSanPham;
    @FXML private ComboBox<ThuongHieu> cbxThuongHieu;
    @FXML private ComboBox<DanhMuc> cbxDanhMuc;
    @FXML private ComboBox<MauSac> cbxMauSac;
    @FXML private ComboBox<KichThuoc> cbxKichThuoc;
    @FXML private Button btnTimKiem;
    @FXML private Button btnHienThiTatCaSP;
    @FXML private Button btnReset;
    @FXML private ListView<SanPham> lvKetQuaTimKiem; private ObservableList<SanPham> dsKetQuaTimKiem;
    @FXML private ListView<SanPham> lvSanPhamDaChon; private ObservableList<SanPham> dsSanPhamDaChon;
    @FXML private Button btnHuy;
    @FXML private Button btnThemVaoDonNhapHang;

    private DonNhapHangProperty donNhapHang;
    private DanhMucService danhMucService;
    private ThuongHieuService thuongHieuService;
    private MauSacService mauSacService;
    private SanPhamService sanPhamService;
    private ObservableList<SanPham> dsSanPham;


    public FormThemSPVaoDNH(DonNhapHangProperty donNhapHang) {
        this.donNhapHang = donNhapHang;
        this.dsKetQuaTimKiem = FXCollections.observableArrayList();
        this.dsSanPhamDaChon = FXCollections.observableArrayList();
        try {
            context = new InitialContext();
            danhMucService = (DanhMucService) context.lookup(StringValues.SERVER_URL + "DanhMucService");
            thuongHieuService = (ThuongHieuService) context.lookup(StringValues.SERVER_URL + "ThuongHieuService");
            mauSacService = (MauSacService) context.lookup(StringValues.SERVER_URL + "MauSacService");
            sanPhamService = (SanPhamService) context.lookup(StringValues.SERVER_URL + "SanPhamService");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addControls();
        addEvents();
        try {
            loadData();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void addControls() {
        lvKetQuaTimKiem.setCellFactory(new Callback<>() {
            @Override
            public ListCell<SanPham> call(ListView<SanPham> sanPhamListView) {
                return new ListCell<SanPham>() {
                    @Override
                    protected void updateItem(SanPham sanPham, boolean b) {
                        super.updateItem(sanPham, b);
                        if (sanPham != null) {
                            ProductItemCategoryView item = new ProductItemCategoryView(sanPham);

                            if (dsSanPhamDaChon.contains(sanPham)) {
                                item.setStyle("-fx-background-color: #EDEDED; -fx-border-color:  #EDEDED; -fx-border-radius: 10; -fx-background-radius: 10");
                                item.getCbkSelect().setSelected(true);
                            }

                            item.getCbkSelect().selectedProperty().addListener(new ChangeListener<Boolean>() {
                                @Override
                                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                                    if (t1) {
                                        if (!dsSanPhamDaChon.contains(sanPham))
                                            dsSanPhamDaChon.add(sanPham);
                                    } else {
                                        dsSanPhamDaChon.remove(sanPham);
                                    }
                                }
                            });

                            setGraphic(item);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
        lvKetQuaTimKiem.setItems(dsKetQuaTimKiem);

        lvSanPhamDaChon.setCellFactory(new Callback<ListView<SanPham>, ListCell<SanPham>>() {
            @Override
            public ListCell<SanPham> call(ListView<SanPham> sanPhamListView) {
                return new ListCell<SanPham>() {
                    @Override
                    protected void updateItem(SanPham sanPham, boolean b) {
                        super.updateItem(sanPham, b);
                        if(sanPham != null) {
                            ProductItem1 item = new ProductItem1(sanPham);

                            item.getBtnDelete().setOnMousePressed(mouseEvent -> {
                                dsSanPhamDaChon.remove(sanPham);
                            });

                            setGraphic(item);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
        lvSanPhamDaChon.setItems(dsSanPhamDaChon);
    }

    private void addEvents() {
        btnTimKiem.setOnMousePressed(mouseEvent -> {
            String maSanPham = txtMaSanPham.getText().trim();
            String tenSanPham = txtTenSanPham.getText().trim();
            DanhMuc danhMuc = cbxDanhMuc.getSelectionModel().getSelectedItem();
            ThuongHieu thuongHieu = cbxThuongHieu.getSelectionModel().getSelectedItem();
            MauSac mauSac = cbxMauSac.getSelectionModel().getSelectedItem();
            KichThuoc kichThuoc = cbxKichThuoc.getSelectionModel().getSelectedItem();

            dsKetQuaTimKiem.setAll(dsSanPham);
            for (SanPham sanPham : dsSanPham) {
                if(!sanPham.getMaSanPham().toLowerCase().contains(maSanPham.toLowerCase())) {
                    dsKetQuaTimKiem.remove(sanPham);
                }
                if(!sanPham.getTenSanPham().toLowerCase().contains(tenSanPham.toLowerCase())) {
                    dsKetQuaTimKiem.remove(sanPham);
                }
                if(danhMuc != null && !sanPham.getDanhMucs().contains(danhMuc)) {
                    dsKetQuaTimKiem.remove(sanPham);
                }
                if(thuongHieu != null && !sanPham.getThuongHieu().equals(thuongHieu)) {
                    dsKetQuaTimKiem.remove(sanPham);
                }
                if(mauSac != null && !sanPham.getMauSac().equals(mauSac)) {
                    dsKetQuaTimKiem.remove(sanPham);
                }
                if(kichThuoc != null && !sanPham.getKichThuoc().equals(kichThuoc)) {
                    dsKetQuaTimKiem.remove(sanPham);
                }
            }
        });

        btnHienThiTatCaSP.setOnMousePressed(mouseEvent -> {
            dsKetQuaTimKiem.setAll(dsSanPham);
        });

        btnThemVaoDonNhapHang.setOnMousePressed(mouseEvent -> {
            if(dsSanPhamDaChon.size() > 0) {
                dsSanPhamDaChon.forEach(sanPham -> {
                    ChiTietDonNhapHangProperty chiTietDonNhapHang = new ChiTietDonNhapHangProperty();
                    chiTietDonNhapHang.setDonNhapHang(DonNhapHangMapper.toDonNhapHang(donNhapHang));
                    chiTietDonNhapHang.setSanPham(sanPham);
                    chiTietDonNhapHang.setGiaNhap(sanPham.getGiaNhap());

                    int index = donNhapHang.getChiTietDonNhapHangs().indexOf(chiTietDonNhapHang);
                    if(index >= 0) {
                        chiTietDonNhapHang.setSoLuong(donNhapHang.getChiTietDonNhapHangs().get(index).getSoLuong() + 1);
                        donNhapHang.getChiTietDonNhapHangs().set(index, chiTietDonNhapHang);
                    } else {
                        chiTietDonNhapHang.setSoLuong(1);
                        donNhapHang.getChiTietDonNhapHangs().add(chiTietDonNhapHang);
                    }
                });
                closeStage(mouseEvent);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Không có sản phẩm nào được chọn");
                alert.show();
            }
        });

        btnReset.setOnMousePressed(mouseEvent -> {
            dsKetQuaTimKiem.clear();
        });

        btnHuy.setOnMousePressed(mouseEvent -> {
            closeStage(mouseEvent);
        });
    }

    private void loadData() throws RemoteException {
        cbxDanhMuc.setItems(FXCollections.observableList(danhMucService.getAllCategory()));
        cbxThuongHieu.setItems(FXCollections.observableList(thuongHieuService.getAllThuongHieu()));
        cbxMauSac.setItems(FXCollections.observableList(mauSacService.getAllMauSac()));
        dsSanPham = FXCollections.observableArrayList(sanPhamService.getAllSanPham());
    }

    private void closeStage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
