package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
//import com.example.fashionshop.model.NhaCungCap;
import com.example.fashionshop.utils.StringUtils;
import com.example.fashionshop.values.StringValues;
import com.example.model.*;
import com.example.service.QuanHuyenService;
import com.example.service.TinhTPService;
import com.example.service.XaPhuongService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.example.service.NhaCungCapService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.Optional;

public class FormNhaCungCapController extends BorderPane {

    public static final int VIEW = 0;
    public static final int ADD = 1;
    public static final int UPDATE = 2;

    @FXML private Label lblTitle;
    @FXML private TextField txtTenNhaCungCap, txtSDT, txtEmail;
    @FXML private Label lblTenNhaCungCapError, lblSDTError, lblEmailError;
    @FXML private Button btnCapNhat, btnXoa, btnLuu, btnThoat;
    @FXML private ComboBox<TinhTP> cbxTinhTP;
    @FXML private ComboBox<QuanHuyen> cbxQuanHuyen;
    @FXML private ComboBox<XaPhuong> cbxXaPhuong;
    @FXML private TextField txtDiaChiChiTiet;
    @FXML private HBox boxTinhTPError, boxQuanHuyenError, boxXaPhuongError, boxDiaChiChiTietError;
    private Label lblTinhTPError, lblQuanHuyenError, lblXaPhuongError, lblDiaChiChiTietError;

    private int type;

    private ObservableList<NhaCungCap> dsNhaCungCap;
    private NhaCungCap nhaCungCap;
    private Context context;
    private NhaCungCapService nhaCungCapService;
    private TinhTPService tinhTPService;        private ObservableList<TinhTP> dsTinhTP;
    private QuanHuyenService quanHuyenService;  private ObservableList<QuanHuyen> dsQuanHuyen;
    private XaPhuongService xaPhuongService;    private ObservableList<XaPhuong> dsXaPhuong;

    public FormNhaCungCapController(ObservableList<NhaCungCap> dsNhaCungCap, NhaCungCap nhaCungCap, int type) {
        this.dsNhaCungCap = dsNhaCungCap;
        this.nhaCungCap = nhaCungCap;
        this.dsTinhTP = FXCollections.observableArrayList();
        this.dsQuanHuyen = FXCollections.observableArrayList();
        this.dsXaPhuong = FXCollections.observableArrayList();

        try {
            context = new InitialContext();
            nhaCungCapService = (NhaCungCapService) context.lookup(StringValues.SERVER_URL + "NhaCungCapService");
            tinhTPService = (TinhTPService) context.lookup(StringValues.SERVER_URL + "TinhTPService");
            quanHuyenService = (QuanHuyenService) context.lookup(StringValues.SERVER_URL + "QuanHuyenService");
            xaPhuongService = (XaPhuongService) context.lookup(StringValues.SERVER_URL + "XaPhuongService");

            init();
            addControls();
            addEvents();
            loadData();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        this.type = type;
        if(type == VIEW) {
            lblTitle.setText("Nhà cung cấp");
            btnLuu.setVisible(false);
            setInputControlEnable(false);
        }
        else if(type == ADD) {
            lblTitle.setText("Thêm Nhà cung cấp");
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);
        }
        else if (type == UPDATE) {
            lblTitle.setText("Cập nhật Nhà cung cấp");
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);
        }
    }

    private void loadData() throws RemoteException {
        boxTinhTPError.getChildren().clear();
        boxQuanHuyenError.getChildren().clear();
        boxXaPhuongError.getChildren().clear();
        boxDiaChiChiTietError.getChildren().clear();

        txtTenNhaCungCap.setText(nhaCungCap.getTenNhaCungCap());
        txtSDT.setText(nhaCungCap.getSdt());
        txtEmail.setText(nhaCungCap.getEmail());

        DiaChi diaChi = nhaCungCap.getDiaChi();
        if(diaChi != null) {
            cbxTinhTP.getSelectionModel().select(diaChi.getTinhTP());
            cbxQuanHuyen.getSelectionModel().select(diaChi.getQuanHuyen());
            cbxXaPhuong.getSelectionModel().select(diaChi.getXaPhuong());
            txtDiaChiChiTiet.setText(diaChi.getDiaChiChiTiet());
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-nha-cung-cap.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void addControls() throws RemoteException {
        lblTinhTPError = new Label();
        lblTinhTPError.setTextFill(Color.RED);
        lblQuanHuyenError = new Label();
        lblQuanHuyenError.setTextFill(Color.RED);
        lblXaPhuongError = new Label();
        lblXaPhuongError.setTextFill(Color.RED);
        lblDiaChiChiTietError = new Label();
        lblDiaChiChiTietError.setTextFill(Color.RED);

        dsTinhTP.setAll(tinhTPService.getAllTinhTP());
        cbxTinhTP.setItems(dsTinhTP);
        cbxQuanHuyen.setItems(dsQuanHuyen);
        cbxXaPhuong.setItems(dsXaPhuong);
    }

    private void addEvents() {
        btnThoat.setOnMousePressed(mouseEvent -> {
            closeStage(mouseEvent);
        });

        btnLuu.setOnMousePressed(mouseEvent -> {
            String tenNhaCungCap = txtTenNhaCungCap.getText().trim();
            String sdt = txtSDT.getText().trim();
            String email = txtEmail.getText().trim();
            TinhTP tinhTP = cbxTinhTP.getSelectionModel().getSelectedItem();
            QuanHuyen quanHuyen = cbxQuanHuyen.getSelectionModel().getSelectedItem();
            XaPhuong xaPhuong = cbxXaPhuong.getSelectionModel().getSelectedItem();
            String diaChiChiTiet = txtDiaChiChiTiet.getText().trim();
            DiaChi diaChi = new DiaChi(tinhTP, quanHuyen, xaPhuong, diaChiChiTiet);

            if(checkInput(tenNhaCungCap, sdt, email, diaChi)) {
                NhaCungCap nhaCungCap = new NhaCungCap(tenNhaCungCap, sdt, email, diaChi);
                if(type == ADD) {
                    addNhaCungCap(nhaCungCap, mouseEvent);
                }
                else if(type == UPDATE) {
                    nhaCungCap.setMaNhaCungCap(this.nhaCungCap.getMaNhaCungCap());
                    updateNhaCungCap(nhaCungCap, mouseEvent);
                }
            }
        });

        btnXoa.setOnMousePressed(mouseEvent -> {
            deleteNhaCungCap(nhaCungCap);
            closeStage(mouseEvent);
        });

        btnCapNhat.setOnMousePressed(mouseEvent -> {
            if(btnCapNhat.getText().equalsIgnoreCase("CẬP NHẬT")) {
                lblTitle.setText("Cập nhật Nhà cung cấp");
                btnCapNhat.setText("HỦY CẬP NHẬT"); btnCapNhat.setStyle("-fx-background-color:grey");
                btnLuu.setVisible(true);
                setInputControlEnable(true);
                type = UPDATE;
            }
            else {
                lblTitle.setText("Nhà cung cấp");
                btnCapNhat.setText("CẬP NHẬT"); btnCapNhat.setStyle("-fx-background-color:#0C75F5");
                btnLuu.setVisible(false);
                setInputControlEnable(false);
                type = VIEW;
                try {
                    loadData();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        txtTenNhaCungCap.textProperty().addListener((observableValue, s, t1) -> lblTenNhaCungCapError.setText(""));

        txtSDT.textProperty().addListener((observableValue, s, t1) -> lblSDTError.setText(""));

        txtEmail.textProperty().addListener((observableValue, s, t1) -> lblEmailError.setText(""));

        cbxTinhTP.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TinhTP>() {
            @Override
            public void changed(ObservableValue<? extends TinhTP> observableValue, TinhTP tinhTP, TinhTP t1) {
                boxTinhTPError.getChildren().clear();
                if(t1 != null) {
                    try {
                        dsQuanHuyen.setAll(quanHuyenService.getQuanHuyenInTinhTP(t1.getId()).stream().sorted(Comparator.comparing(qh -> StringUtils.removeAccent(qh.getName()))).toList());
                        cbxQuanHuyen.getSelectionModel().clearSelection();
                        dsXaPhuong.clear();
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }
                }
            }
        });

        cbxQuanHuyen.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<QuanHuyen>() {
            @Override
            public void changed(ObservableValue<? extends QuanHuyen> observableValue, QuanHuyen quanHuyen, QuanHuyen t1) {
                boxQuanHuyenError.getChildren().clear();
                if(t1 != null)
                {
                    try {
                        dsXaPhuong.setAll(xaPhuongService.getXaPhuongInQuanHuyen(t1.getId()).stream().sorted(Comparator.comparing(xp -> StringUtils.removeAccent(xp.getName()))).toList());
                        cbxXaPhuong.getSelectionModel().clearSelection();
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }
                }
            }
        });

        cbxXaPhuong.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<XaPhuong>() {
            @Override
            public void changed(ObservableValue<? extends XaPhuong> observableValue, XaPhuong xaPhuong, XaPhuong t1) {
                boxXaPhuongError.getChildren().clear();
            }
        });

        txtDiaChiChiTiet.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                boxDiaChiChiTietError.getChildren().clear();
            }
        });

    }

    private void setInputControlEnable(boolean b) {
        txtTenNhaCungCap.setEditable(b);
        txtSDT.setEditable(b);
        txtEmail.setEditable(b);
        cbxTinhTP.setDisable(!b);
        cbxQuanHuyen.setDisable(!b);
        cbxXaPhuong.setDisable(!b);
        txtDiaChiChiTiet.setEditable(b);
    }

    private void closeStage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void addNhaCungCap(NhaCungCap nhaCungCap, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Category");
        alert.setHeaderText(null);
        try {
            if(nhaCungCapService.addNhaCungCap(nhaCungCap) && dsNhaCungCap.add(nhaCungCap)) {
                alert.setContentText("Thêm nhà cung cấp thành công");
                alert.show();
                closeStage(mouseEvent);
            }
            else {
                alert.setContentText("Error: Thêm nhà cung cấp không thành công!");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void deleteNhaCungCap(NhaCungCap nhaCungCap) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xóa nhà cung cấp");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa nhà cung cấp này?");

        Optional<ButtonType> option = alert.showAndWait();
        try {
            if(option.get() == ButtonType.OK) {
                if(nhaCungCapService.deleteNhaCungCap(nhaCungCap) && dsNhaCungCap.remove(nhaCungCap)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Xóa nhà cung cấp thành công!");
                }
                else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Error: Xóa nhà cung cấp không thành công!");
                }
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void updateNhaCungCap(NhaCungCap nhaCungCap, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cập nhật nhà cung cấp");
        alert.setHeaderText(null);
        try {
            if(nhaCungCapService.updateNhaCungCap(nhaCungCap)) {
                dsNhaCungCap.set(dsNhaCungCap.indexOf(nhaCungCap), nhaCungCap);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Cập nhật thông tin nhà cung cấp thành công!");
                alert.show();
                closeStage(mouseEvent);
            }
            else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error: Cập nhật thông tin nhà cung cấp không thành công");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private boolean checkInput(String tenNhaCungCap, String sdt, String email, DiaChi diaChi) {
        boolean result = true;

        boxTinhTPError.getChildren().clear();
        boxQuanHuyenError.getChildren().clear();
        boxXaPhuongError.getChildren().clear();
        boxDiaChiChiTietError.getChildren().clear();

        if(tenNhaCungCap.isEmpty()) {
            result = false;
            lblTenNhaCungCapError.setText("Tên nhà cung cấp không được để trống");
        }

        if(sdt.isEmpty()) {
            result = false;
            lblSDTError.setText("Số điện thoại không được để trống");
        }

        if (email.isEmpty()) {
            result = false;
            lblEmailError.setText("Email không được để trống");
        }

        if(diaChi.getTinhTP() == null) {
            result = false;
            lblTinhTPError.setText("Tỉnh/TP không được để trống");
            boxTinhTPError.getChildren().add(lblTinhTPError);
        }

        if(diaChi.getQuanHuyen() == null) {
            result = false;
            lblQuanHuyenError.setText("Quận/Huyện không được để trống");
            boxQuanHuyenError.getChildren().add(lblQuanHuyenError);
        }

        if(diaChi.getXaPhuong() == null) {
            result = false;
            lblXaPhuongError.setText("Xã/Phường không được để trống");
            boxXaPhuongError.getChildren().add(lblXaPhuongError);
        }

        if(diaChi.getDiaChiChiTiet().isEmpty()) {
            result = false;
            lblDiaChiChiTietError.setText("Địa chỉ chi tiết không được để trống");
            boxDiaChiChiTietError.getChildren().add(lblDiaChiChiTietError);
        }

        return result;
    }
}
