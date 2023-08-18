package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.utils.StringUtils;
import com.example.fashionshop.values.StringValues;
import com.example.service.*;
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
import com.example.model.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;
import java.util.regex.Pattern;

public class FormKhachHangController extends BorderPane {

    public static final int VIEW = 0;
    public static final int ADD = 1;
    public static final int UPDATE = 2;

    @FXML private Label lblTitle;
    @FXML private TextField txtName;
    @FXML private TextField txtPhone;
    @FXML private TextField txtEmail;
    @FXML private TextField txtDiaChiChiTiet;
    @FXML private Label lblNameError;
    @FXML private Label lblPhoneError;
    @FXML private Label lblEmailError;
    @FXML private Label bllAddressError;
    @FXML private ComboBox<TinhTP> cbxTinh;
    @FXML private ComboBox<QuanHuyen> cbxHuyen;
    @FXML private ComboBox<XaPhuong> cbxXa;
    @FXML private Button btnLuu;
    @FXML private Button btnThoat;
    @FXML private Button btnCapNhat;
    @FXML private Button btnXoa;
    @FXML private HBox boxTinhTPError, boxQuanHuyenError, boxPhuongXaError, boxDiaChiChiTietError;

    private int type;
    private KhachHang khachHang;
    private ObservableList<KhachHang> listKhachHang;

    private TinhTPService tinhTPService;        private ObservableList<TinhTP> dsTinhTP;
    private QuanHuyenService quanHuyenService;  private ObservableList<QuanHuyen> dsQuanHuyen;
    private XaPhuongService xaPhuongService;    private ObservableList<XaPhuong> dsXaPhuong;
    private Context context ;
    private KhachHangService khachHangService;
    private IDService idService;

    public FormKhachHangController(ObservableList<KhachHang> listKhachHang, KhachHang khachHang, int type) {
        this.type = type;
        this.listKhachHang = listKhachHang;
        this.khachHang = khachHang;

        try {
            context = new InitialContext();
            tinhTPService = (TinhTPService) context.lookup(StringValues.SERVER_URL + "TinhTPService");
            quanHuyenService = (QuanHuyenService) context.lookup(StringValues.SERVER_URL + "QuanHuyenService");
            xaPhuongService = (XaPhuongService) context.lookup(StringValues.SERVER_URL + "XaPhuongService");
            khachHangService = (KhachHangService) context.lookup(StringValues.SERVER_URL + "KhachHangService");
            idService = (IDService) context.lookup(StringValues.SERVER_URL + "IDService");
        }catch (Exception exception){
            exception.printStackTrace();
        };

        dsTinhTP = FXCollections.observableArrayList();
        dsQuanHuyen = FXCollections.observableArrayList();
        dsXaPhuong = FXCollections.observableArrayList();

        init();
        addEvents();
        loadData();
        loadDataDiaChiToCombobox();

        if(type == VIEW) {
            setTextFieldEditable(false);
            btnLuu.setVisible(false);
            lblTitle.setText("Thông tin khách hàng");
        }
        else if(type == ADD) {
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);

            lblTitle.setText("Thêm khách hàng");
        }
        else if (type == UPDATE) {
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);

            lblTitle.setText("Cập nhật thông tin khách hàng");
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-khach-hang.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void closeStage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void addEvents() {

        btnThoat.setOnMousePressed(mouseEvent -> {
            closeStage(mouseEvent);
        });

        btnLuu.setOnMousePressed(mouseEvent -> {
            String name = txtName.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String diaChiChiTiet = txtDiaChiChiTiet.getText();
            TinhTP tinhTP = cbxTinh.getSelectionModel().getSelectedItem();
            QuanHuyen quanHuyen = cbxHuyen.getSelectionModel().getSelectedItem();
            XaPhuong xaPhuong = cbxXa.getSelectionModel().getSelectedItem();
            
            if (checkInput(name, email, phone, diaChiChiTiet, tinhTP, quanHuyen, xaPhuong)) {
                KhachHang khachHang = new KhachHang(name, phone, email, new DiaChi(tinhTP, quanHuyen, xaPhuong, diaChiChiTiet));

                if (type == ADD) {
                    addCustomer(khachHang, mouseEvent);
                } else if (type == UPDATE || type == VIEW) {
                    khachHang.setMaKhachHang(this.khachHang.getMaKhachHang());
                    updateCustomer(khachHang, mouseEvent);
                }
            }
        });

        btnXoa.setOnMousePressed(mouseEvent -> {
            deleteCustomer(khachHang, mouseEvent);
        });

        btnCapNhat.setOnMousePressed(mouseEvent -> {
            if(btnCapNhat.getText().equalsIgnoreCase("CẬP NHẬT")) {
                btnCapNhat.setText("HỦY CẬP NHẬT");
                btnCapNhat.setStyle("-fx-background-color: grey");
                lblTitle.setText("Cập nhật thông tin khách hàng");
                btnLuu.setVisible(true);
                btnXoa.setVisible(false);
                setTextFieldEditable(true);
            }
            else {
                btnCapNhat.setText("CẬP NHẬT");
                btnCapNhat.setStyle("-fx-background-color: #0C75F5");
                lblTitle.setText("Thông tin khách hàng");
                btnLuu.setVisible(false);
                btnXoa.setVisible(true);
                setTextFieldEditable(false);

                loadData();
            }
        });

        cbxTinh.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TinhTP>() {
            @Override
            public void changed(ObservableValue<? extends TinhTP> observableValue, TinhTP tinhTP, TinhTP t1) {
                boxTinhTPError.getChildren().clear();
                if(t1 != null) {
                    try {
                        dsQuanHuyen.setAll(quanHuyenService.getQuanHuyenInTinhTP(t1.getId()).stream().sorted(Comparator.comparing(qh -> StringUtils.removeAccent(qh.getName()))).toList());
                        dsXaPhuong.clear();
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }
                }
            }
        });

        cbxHuyen.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<QuanHuyen>() {
            @Override
            public void changed(ObservableValue<? extends QuanHuyen> observableValue, QuanHuyen quanHuyen, QuanHuyen t1) {
                boxQuanHuyenError.getChildren().clear();
                if(t1 != null)
                {
                    try {
                        dsXaPhuong.setAll(xaPhuongService.getXaPhuongInQuanHuyen(t1.getId()).stream().sorted(Comparator.comparing(xp -> StringUtils.removeAccent(xp.getName()))).toList());
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }
                }
            }
        });

        cbxXa.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<XaPhuong>() {
            @Override
            public void changed(ObservableValue<? extends XaPhuong> observableValue, XaPhuong xaPhuong, XaPhuong t1) {
                boxPhuongXaError.getChildren().clear();
            }
        });

        txtDiaChiChiTiet.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                boxDiaChiChiTietError.getChildren().clear();
            }
        });
    }

    private void loadData() {
        if (khachHang != null) {
            txtName.setText(khachHang.getHoTen());
            txtPhone.setText(khachHang.getSdt());
            txtEmail.setText(khachHang.getEmail());
            txtDiaChiChiTiet.setText(khachHang.getDiaChi().getDiaChiChiTiet());
            cbxTinh.getSelectionModel().select(khachHang.getDiaChi().getTinhTP());
            cbxHuyen.getSelectionModel().select(khachHang.getDiaChi().getQuanHuyen());
            cbxXa.getSelectionModel().select(khachHang.getDiaChi().getXaPhuong());
        }
    }

    private void loadDataDiaChiToCombobox() {
        try {
            dsTinhTP.setAll(tinhTPService.getAllTinhTP().stream().sorted(Comparator.comparing(tinhTP -> StringUtils.removeAccent(tinhTP.getName()))).toList());
        }catch (Exception exception){
            exception.printStackTrace();
        }
        cbxTinh.setItems(dsTinhTP);
        cbxHuyen.setItems(dsQuanHuyen);
        cbxXa.setItems(dsXaPhuong);
    }

    private void addCustomer(KhachHang khachHang, MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add customer");
        alert.setHeaderText(null);
        try {
            long maKhachHang = -1;
            if ((maKhachHang = khachHangService.addCustomer(khachHang)) > 0) {
                khachHang.setMaKhachHang(maKhachHang);
                if(listKhachHang.add(khachHang)) {
                    alert.setContentText("Thêm khách hàng thành công!");
                    alert.show();
                    closeStage(event);
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Error: Thêm khách hàng thất bại!");
                    alert.show();
                }
            }
            else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error: Thêm khách hàng thất bại!");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void updateCustomer(KhachHang khachHang, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cập nhật thông tin khách hàng");
        alert.setHeaderText(null);
        try {
            if(khachHangService.updateCustomer(khachHang)) {
                listKhachHang.set(listKhachHang.indexOf(khachHang), khachHang);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Cập nhật thông tin khách hàng thành công!");
                alert.show();
                closeStage(mouseEvent);
            }
            else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error: Cập nhật thông tin khách hàng không thành công");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void deleteCustomer(KhachHang khachHang, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete customer");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa khách hàng này không?");

        Optional<ButtonType> optional = alert.showAndWait();
        try {
            if(optional.get() == ButtonType.OK) {
                if(khachHangService.removeCustomer(khachHang) && listKhachHang.remove(khachHang)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Xóa khách hàng thành công");
                    closeStage(mouseEvent);
                }
                else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Error: Xóa khách hàng không thành công");
                }
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private boolean checkInput(String name, String email, String phone, String diaChiChiTiet,
                               TinhTP tinhTP, QuanHuyen quanHuyen, XaPhuong xaPhuong) {
        setLabelErrorEmpty();

        if (name.isEmpty()) {
            lblNameError.setText("Tên khách hàng không được bỏ trống.");
            return false;
        } else if (phone.isEmpty()) {
            lblPhoneError.setText("Số điện thoại khách hàng không được để trống.");
            return false;
        }

        Pattern patternPhone = Pattern.compile("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$");
        if (!patternPhone.matcher(phone).matches()) {
            lblPhoneError.setText("Số điện thoại không hợp lệ");
            return false;
        }

        Label label = new Label();
        label.setTextFill(Color.RED);

        if(tinhTP == null) {
            label.setText("Tỉnh thành phố không được để trống");
            boxTinhTPError.getChildren().add(label);
            return false;
        }

        if(quanHuyen == null) {
            label.setText("Quận huyện không được để trống");
            boxQuanHuyenError.getChildren().add(label);
            return false;
        }

        if(xaPhuong == null) {
            label.setText("Xã phường không được để trống");
            boxPhuongXaError.getChildren().add(label);
            return false;
        }

        if(diaChiChiTiet.isEmpty()) {
            label.setText("Địa chỉ chi tiết không được để trống");
            boxDiaChiChiTietError.getChildren().add(label);
            return false;
        }

        return true;
    }

    private void setLabelErrorEmpty() {
        lblNameError.setText("");
        lblPhoneError.setText("");
    }

    private void setTextFieldEditable(boolean b) {
        txtName.setEditable(b);
        txtPhone.setEditable(b);
        txtEmail.setEditable(b);
        txtDiaChiChiTiet.setEditable(b);
        cbxTinh.setDisable(!b);
        cbxHuyen.setDisable(!b);
        cbxXa.setDisable(!b);
    }
}
