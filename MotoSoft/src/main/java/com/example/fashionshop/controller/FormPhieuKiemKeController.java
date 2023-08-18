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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.model.*;
import com.example.service.IDService;
import com.example.service.PhieuKiemKeService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

public class FormPhieuKiemKeController extends BorderPane {

    public static final int VIEW = 0;
    public static final int ADD = 1;
    public static final int UPDATE = 2;

    @FXML private Label lblTitle;
    @FXML private Label lblMaPhieuKiemKe;
    @FXML private Label lblHoTenNVTaoPhieu, lblMSNVTaoPhieu, lblNgayTaoPhieu;
    @FXML private Label lblHoTenNVKiemKe, lblMSNVKiemKe, lblNgayKiemKe;
    @FXML private TextArea txtGhiChu;
    @FXML private Label lblTrangThai;
    @FXML private ListView<ChiTietPhieuKiem> listView;
    @FXML private Button btnThoat, btnLuu, btnCapNhat, btnXoa;
    @FXML private Label lblSoLuongSP;
    @FXML private Button btnThemSanPham;

    private int type;
    private static Context context ;
    private ObservableList<PhieuKiemKe> dsPhieuKiemKe;
    private ObservableList<ChiTietPhieuKiem> dsChiTietPhieuKiems;
    private PhieuKiemKe phieuKiemKe;
    private PhieuKiemKeService phieuKiemKeService;
    private TaiKhoan taiKhoan;
    private IDService idService;
    private boolean UPDATE_INVENTORY_SHEET = false;
    private boolean DELETE_INVENTORY_SHEET = false;

    public FormPhieuKiemKeController(TaiKhoan taiKhoan, ObservableList<PhieuKiemKe> dsPhieuKiemKe, PhieuKiemKe phieuKiemKe, int type) {
        this.taiKhoan = taiKhoan;

        try {
            context = new InitialContext();
            idService = (IDService) context.lookup(StringValues.SERVER_URL + "IDService");
            phieuKiemKeService = (PhieuKiemKeService) context.lookup(StringValues.SERVER_URL + "PhieuKiemKeService");
        }catch (Exception exception){
            exception.printStackTrace();
        }

        VaiTro vaiTro = taiKhoan.getVaiTro();
        int index = vaiTro.getDsNhomQuyen().indexOf(new VaiTroNhomQuyen(vaiTro, new NhomQuyen("INVENTORY_PERMISSION")));
        VaiTroNhomQuyen vaiTroNhomQuyen = vaiTro.getDsNhomQuyen().get(index);
        for (VaiTroQuyen vaiTroQuyen : vaiTroNhomQuyen.getDsQuyen()) {
            Quyen quyen = vaiTroQuyen.getQuyen();
            if(quyen.getMaQuyen().equalsIgnoreCase("UPDATE_INVENTORY_SHEET")
                    && vaiTroQuyen.getTrangThai() == true) {
                UPDATE_INVENTORY_SHEET = true;
            }
            if(quyen.getMaQuyen().equalsIgnoreCase("DELETE_INVENTORY_SHEET")
                    && vaiTroQuyen.getTrangThai() == true) {
                DELETE_INVENTORY_SHEET = true;
            }
        }

        this.dsPhieuKiemKe = dsPhieuKiemKe;
        try {
            if(phieuKiemKe == null) {
                this.phieuKiemKe = new PhieuKiemKe(
                        idService.createMaPhieuKiemKe(),
                        LocalDateTime.now(),
                        PhieuKiemKeStatus.TAO_MOI_CHO_KIEM_KE,
                        taiKhoan.getNhanVien()
                );
            } else {
                this.phieuKiemKe = phieuKiemKe;
            }

        }catch (Exception exception){
            exception.printStackTrace();
        }
        this.dsChiTietPhieuKiems = FXCollections.observableArrayList(this.phieuKiemKe.getChiTietPhieuKiems());
        this.type = (type);

        init();

        if(type == VIEW) {
            lblTitle.setText("Phiếu kiểm kê");
            btnLuu.setVisible(false);
            setInputControlEnable(false);
        }
        else if(type == ADD) {
            lblTitle.setText("Thêm Phiếu kiểm kê");
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);
        }
        else if (type == UPDATE) {
            lblTitle.setText("Cập nhật Phiếu kiểm kê");
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);
        }

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
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-phieu-kiem-ke.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void addControls() {
        if(this.phieuKiemKe.getTrangThai() == PhieuKiemKeStatus.DA_DUYET) {
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);
            btnThemSanPham.setVisible(false);
            listView.setDisable(false);
            txtGhiChu.setDisable(false);
            txtGhiChu.setEditable(false);
        }

        if(UPDATE_INVENTORY_SHEET == false)
            btnCapNhat.setVisible(false);
        if(DELETE_INVENTORY_SHEET == false)
            btnXoa.setVisible(false);

        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<ChiTietPhieuKiem> call(ListView<ChiTietPhieuKiem> chiTietPhieuKiemListView) {
                return new ListCell<ChiTietPhieuKiem>() {
                    @Override
                    protected void updateItem(ChiTietPhieuKiem chiTietPhieuKiem, boolean b) {
                        super.updateItem(chiTietPhieuKiem, b);
                        if (chiTietPhieuKiem != null) {
                            ChiTietPhieuKiemItem item = new ChiTietPhieuKiemItem(chiTietPhieuKiem);

                            if (phieuKiemKe.getTrangThai() == PhieuKiemKeStatus.DA_DUYET) {
                                item.getBtnDelete().setVisible(false);
                            }

                            item.getBtnDelete().setOnMousePressed(mouseEvent -> {
                                dsChiTietPhieuKiems.remove(chiTietPhieuKiem);
                            });

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
        btnThemSanPham.setOnMousePressed(mouseEvent -> {
            Stage context = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            openFormThemSanPham(context, dsChiTietPhieuKiems, phieuKiemKe);
        });

        btnThoat.setOnMousePressed(mouseEvent -> {
            closeStage(mouseEvent);
        });

        btnLuu.setOnMousePressed(mouseEvent -> {
            String ghiChu = txtGhiChu.getText();

            if(checkInput()) {
                phieuKiemKe.setGhiChu(ghiChu);
                phieuKiemKe.setChiTietPhieuKiems(dsChiTietPhieuKiems.stream().toList());
                if(dsChiTietPhieuKiems.size() > 0) {
                    if(type == ADD) {
                        addPhieuKiemKe(phieuKiemKe, mouseEvent);
                    }
                    else if(type == UPDATE) {
                        updatePhieuKiemKe(phieuKiemKe, mouseEvent);
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Không có sản phẩm nào trong phiếu kiểm kê");
                    alert.show();
                }
            }
        });

        btnXoa.setOnMousePressed(mouseEvent -> {
            deletePhieuKiemKe(phieuKiemKe, mouseEvent);
            closeStage(mouseEvent);
        });

        btnCapNhat.setOnMousePressed(mouseEvent -> {
            if(btnCapNhat.getText().equalsIgnoreCase("CẬP NHẬT")) {
                lblTitle.setText("Cập nhật Phiếu kiểm kê");
                btnCapNhat.setText("HỦY CẬP NHẬT"); btnCapNhat.setStyle("-fx-background-color:grey");
                btnLuu.setVisible(true);
                setInputControlEnable(true);
                type = UPDATE;
            }
            else {
                lblTitle.setText("Phiếu kiểm kê");
                btnCapNhat.setText("CẬP NHẬT"); btnCapNhat.setStyle("-fx-background-color:#0C75F5");
                btnLuu.setVisible(false);
                setInputControlEnable(false);
                type = VIEW;
                loadData();
            }
        });
    }

    private void setInputControlEnable(boolean b) {
//        txtGhiChu.setDisable(!b);
        btnThemSanPham.setVisible(b);
//        listView.setDisable(!b);
    }

    private void closeStage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void addPhieuKiemKe(PhieuKiemKe phieuKiemKe, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thêm phiếu kiểm kê");
        alert.setHeaderText(null);

        try {
            if(phieuKiemKeService.addPhieuKiemKe(phieuKiemKe) && dsPhieuKiemKe.add(phieuKiemKe)) {
                alert.setContentText("Thêm phiếu kiểm kê thành công");
                alert.show();
                closeStage(mouseEvent);
            }
            else {
                alert.setContentText("Error: Thêm phiếu kiểm kê không thành công!");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void deletePhieuKiemKe(PhieuKiemKe phieuKiemKe, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xóa phiếu kiểm kê");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa phiếu kiểm kê này?");

        Optional<ButtonType> option = alert.showAndWait();

        if(option.get() == ButtonType.OK) {
            try {
                if(phieuKiemKeService.deletePhieuKiemKe(phieuKiemKe) && dsPhieuKiemKe.remove(phieuKiemKe)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Xóa phiếu kiểm kê thành công!");
                    closeStage(mouseEvent);
                }
                else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Error: Xóa phiếu kiểm kê không thành công!");
                }
            }catch ( Exception exception){
                exception.printStackTrace();
            }
            alert.show();
        }
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

    private void openFormThemSanPham(Stage context, ObservableList<ChiTietPhieuKiem> dsChiTietPhieuKiem, PhieuKiemKe phieuKiemKe) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-them-sp-vao-pkk.fxml"));
        FormThemSPVaoPKK formThemSPVaoPKK = new FormThemSPVaoPKK(dsChiTietPhieuKiem, phieuKiemKe);
        fxmlLoader.setController(formThemSPVaoPKK);
        try {
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toString());
            stage.setScene(scene);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(context);
        stage.setTitle("Thêm sản phẩm vào phiếu kiểm kê");
        stage.show();
    }



}
