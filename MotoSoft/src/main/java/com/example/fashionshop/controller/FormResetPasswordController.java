package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.CardsPane;
import com.example.fashionshop.cus_comp.FormCreateNewPassword;
import com.example.fashionshop.cus_comp.FormXacThucResetPassword;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FormResetPasswordController implements Initializable {

    @FXML private VBox boxContent;
    @FXML private Label btnDangNhap;
    private CardsPane cardsPane;
    private FormXacThucResetPassword formXacThucResetPassword = new FormXacThucResetPassword();
    private FormCreateNewPassword formCreateNewPassword = new FormCreateNewPassword();

    private PasswordField txtMatKhau, txtXacNhanMatKhau;
    private Label lblMatKhauError, lblXacNhanMatKhauError;
    private Button btnTaoMatKhau;
    private TextField txtMaNhanVien;
    private TextField txtMaXacThuc;
    private HBox btnSendOTP;
    private Label lblMaNhanVienError;
    private Label lblMaXacThucError;
    private Button btnXacThuc;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addControls();
        addEvents();
    }

    private void addControls() {
        formXacThucResetPassword.setId("formXacThucResetPassword");
        formCreateNewPassword.setId("formCreateNewPassword");
        txtMatKhau = formCreateNewPassword.getTxtMatKhau();
        txtXacNhanMatKhau = formCreateNewPassword.getTxtXacNhanMatKhau();
        lblMatKhauError = formCreateNewPassword.getLblMatKhauError();
        lblXacNhanMatKhauError = formXacThucResetPassword.getLblMaNhanVienError();
        btnTaoMatKhau = formCreateNewPassword.getBtnTaoMatKhau();
        txtMaNhanVien = formXacThucResetPassword.getTxtMaNhanVien();
        txtMaXacThuc = formXacThucResetPassword.getTxtMaXacThuc();
        btnSendOTP = formXacThucResetPassword.getBtnSendOTP();
        lblMaNhanVienError = formXacThucResetPassword.getLblMaNhanVienError();
        lblMaXacThucError = formXacThucResetPassword.getLblMaXacThucError();
        btnXacThuc = formXacThucResetPassword.getBtnXacThuc();

        cardsPane = new CardsPane();
        cardsPane.add(formXacThucResetPassword.getId(), formXacThucResetPassword);
        cardsPane.add(formCreateNewPassword.getId(), formCreateNewPassword);

        boxContent.getChildren().add(cardsPane);

        try {
            cardsPane.show(formXacThucResetPassword.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addEvents() {
        btnDangNhap.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            openFormLogin(stage);
        });

        txtMaNhanVien.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lblMaNhanVienError.setText("");
            }
        });

        txtMaXacThuc.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lblMaXacThucError.setText("");
            }
        });

        btnXacThuc.setOnMouseClicked(mouseEvent -> {
            String maNhanVien = txtMaNhanVien.getText().trim();
            String maXacThuc = txtMaXacThuc.getText().trim();

            if(checkFormXacThucInput(maNhanVien, maXacThuc)) {
                if(xacThuc(maNhanVien, maXacThuc)) {
                    try {
                        cardsPane.show(formCreateNewPassword.getId());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        txtMatKhau.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lblMatKhauError.setText("");
            }
        });

        txtXacNhanMatKhau.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lblXacNhanMatKhauError.setText("");
            }
        });

        btnTaoMatKhau.setOnMouseClicked(mouseEvent -> {
            String maNhanVien = txtMaNhanVien.getText().trim();
            String matKhau = txtMatKhau.getText().trim();
            String xacNhanMatKhau = txtXacNhanMatKhau.getText().trim();

            if(checkFormCreatePasswordInput(matKhau, xacNhanMatKhau)) {
                if(createNewPassword(maNhanVien, matKhau)) {
                    Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                    openFormLogin(stage);
                }
            }
        });
    }

    private boolean xacThuc(String maNhanVien, String maXacThuc) {
        return true;
    }

    private boolean checkFormXacThucInput(String maNhanVien, String maXacThuc) {
        if(maNhanVien.isEmpty()) {
            lblMaNhanVienError.setText("Mã nhân viên không được để trống");
            return false;
        }

        if(maXacThuc.isEmpty()) {
            lblMaXacThucError.setText("Mã xác thực không được để trống");
            return false;
        }

        return true;
    }

    private void openFormLogin(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Đăng nhập");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean createNewPassword(String maNhanVien, String matKhau) {
        return true;
    }

    private boolean checkFormCreatePasswordInput(String matKhau, String xacNhanMatKhau) {
        if(matKhau.isEmpty()) {
            lblMatKhauError.setText("Mật khẩu không được để trống");
            return false;
        }

        if(xacNhanMatKhau.isEmpty()) {
            lblXacNhanMatKhauError.setText("Xác nhân mật khẩu không được để trống");
            return false;
        } else if (!xacNhanMatKhau.equals(matKhau)) {
            lblXacNhanMatKhauError.setText("Xác nhận mật khẩu không trùng khớp");
            return false;
        }

        return true;
    }

}
