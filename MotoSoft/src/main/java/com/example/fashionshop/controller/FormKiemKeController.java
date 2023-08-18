package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.ChiTietPhieuKiemItem;
//import com.example.fashionshop.impl.PhieuKiemKeImpl;
//import com.example.fashionshop.service.PhieuKiemKeService;
import com.example.fashionshop.values.StringValues;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.model.ChiTietPhieuKiem;
import com.example.model.PhieuKiemKe;
import com.example.model.PhieuKiemKeStatus;
import com.example.model.TaiKhoan;
import com.example.service.PhieuKiemKeService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.time.LocalDateTime;

public class FormKiemKeController extends BorderPane {

    @FXML private Label lblTitle;
    @FXML private Label lblMaPhieuKiemKe;
    @FXML private Label lblHoTenNVTaoPhieu, lblMSNVTaoPhieu, lblNgayTaoPhieu;
    @FXML private Label lblHoTenNVKiemKe, lblMSNVKiemKe, lblNgayKiemKe;
    @FXML private TextArea txtGhiChu;
    @FXML private Label lblTrangThai;
    @FXML private ListView<ChiTietPhieuKiem> listView;
    @FXML private Button btnThoat, btnLuu;
    @FXML private Label lblSoLuongSP;

    private int type;

    private ObservableList<PhieuKiemKe> dsPhieuKiemKe;
    private ObservableList<ChiTietPhieuKiem> dsChiTietPhieuKiems;
    private PhieuKiemKe phieuKiemKe;
    private PhieuKiemKeService phieuKiemKeService;
    private static Context context ;
    private TaiKhoan taiKhoan;
    private boolean UPDATE_INVENTORY_SHEET = false;
    private boolean DELETE_INVENTORY_SHEET = false;

    public FormKiemKeController(TaiKhoan taiKhoan, ObservableList<PhieuKiemKe> dsPhieuKiemKe, PhieuKiemKe phieuKiemKe, int type) {
        this.taiKhoan = taiKhoan;

        this.dsPhieuKiemKe = dsPhieuKiemKe;
        this.phieuKiemKe = phieuKiemKe;
        this.dsChiTietPhieuKiems = FXCollections.observableArrayList(this.phieuKiemKe.getChiTietPhieuKiems());
        this.type = (type);

        try {
            context = new InitialContext();
            phieuKiemKeService = (PhieuKiemKeService) context.lookup(StringValues.SERVER_URL + "PhieuKiemKeService");
        }catch (Exception exception){
            exception.printStackTrace();
        }
        init();
        addControls();
        addEvents();
        loadData();
    }

    private void loadData() {
        lblHoTenNVTaoPhieu.setText(phieuKiemKe.getNhanVienTaoPhieu().getHoTen());
        lblMSNVTaoPhieu.setText(phieuKiemKe.getNhanVienTaoPhieu().getMaNhanVien());
        lblNgayTaoPhieu.setText(phieuKiemKe.getNgayTao().toString());
        txtGhiChu.setText(phieuKiemKe.getGhiChu());
        lblSoLuongSP.setText(Integer.toString(dsChiTietPhieuKiems.size()));
        lblHoTenNVKiemKe.setText(taiKhoan.getNhanVien().getHoTen());
        lblMSNVKiemKe.setText(taiKhoan.getNhanVien().getMaNhanVien());
        lblNgayKiemKe.setText(LocalDateTime.now().toString());
        lblTrangThai.setText(phieuKiemKe.getTrangThai().toString());
        lblMaPhieuKiemKe.setText(phieuKiemKe.getMaPhieuKiemKe());

        if(phieuKiemKe.getNhanVienKiemKe() != null) {
            lblHoTenNVKiemKe.setText(phieuKiemKe.getNhanVienKiemKe().getHoTen());
            lblMSNVKiemKe.setText(phieuKiemKe.getNhanVienKiemKe().getMaNhanVien());
        }

        if(phieuKiemKe.getNgayKiemKe() != null) {
            lblNgayKiemKe.setText(phieuKiemKe.getNgayKiemKe().toString());
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-kiem-ke.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void addControls() {
        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<ChiTietPhieuKiem> call(ListView<ChiTietPhieuKiem> chiTietPhieuKiemListView) {
                return new ListCell<ChiTietPhieuKiem>() {
                    @Override
                    protected void updateItem(ChiTietPhieuKiem chiTietPhieuKiem, boolean b) {
                        super.updateItem(chiTietPhieuKiem, b);
                        if (chiTietPhieuKiem != null) {
                            ChiTietPhieuKiemItem item = new ChiTietPhieuKiemItem(chiTietPhieuKiem);

                            item.getBtnDelete().setVisible(false);

                            setGraphic(item);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
        listView.setItems(dsChiTietPhieuKiems);
    }

    private void addEvents() {
        btnThoat.setOnMousePressed(mouseEvent -> {
            closeStage(mouseEvent);
        });

        btnLuu.setOnMousePressed(mouseEvent -> {
            String ghiChu = txtGhiChu.getText();

            if(checkInput()) {
                phieuKiemKe.setGhiChu(ghiChu);
                phieuKiemKe.setChiTietPhieuKiems(dsChiTietPhieuKiems.stream().toList());
                phieuKiemKe.setTrangThai(PhieuKiemKeStatus.DA_KIEM_KE_CHO_DUYET);
                phieuKiemKe.setNhanVienKiemKe(taiKhoan.getNhanVien());
                phieuKiemKe.setNgayKiemKe(LocalDateTime.now());
                updatePhieuKiemKe(phieuKiemKe, mouseEvent);
            }
        });
    }

    private void closeStage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void updatePhieuKiemKe(PhieuKiemKe phieuKiemKe, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cập nhật phiếu kiểm kê");
        alert.setHeaderText(null);

        try {
            if(phieuKiemKeService.updatePhieuKiemKe(phieuKiemKe)) {
                dsPhieuKiemKe.set(dsPhieuKiemKe.indexOf(phieuKiemKe), phieuKiemKe);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Cập nhật thông tin phiếu kiểm kê thành công!");
                alert.show();
                closeStage(mouseEvent);
            }
            else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error: Cập nhật thông tin phiếu kiểm kê không thành công");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private boolean checkInput() {
        boolean result = true;

        return result;
    }


}
