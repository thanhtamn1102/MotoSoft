package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.utils.StringUtils;
import com.example.fashionshop.values.StringValues;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.example.model.TaiKhoan;
import com.example.service.TaiKhoanService;
import javafx.stage.StageStyle;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.URL;
import java.util.ResourceBundle;

public class FormLoginController implements Initializable {

    @FXML private TextField txtMaNhanVien;
    @FXML private PasswordField txtMatKhau;
    @FXML private Button btnDangNhap;
    @FXML private Label lblQuenMatKhau;
    @FXML private Label lblLoginError;
    @FXML private Label lblMaNhanVienError;
    @FXML private Label lblMatKhauError;

    private Context context;
    private TaiKhoanService taiKhoanService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            context = new InitialContext();
            taiKhoanService = (TaiKhoanService) context.lookup(StringValues.SERVER_URL + "TaiKhoanService");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        addEvents();
    }

    private void addEvents() {
        btnDangNhap.setOnMouseClicked(mouseEvent -> {
            String maNhanVien = txtMaNhanVien.getText().trim();
            String matKhau = txtMatKhau.getText();
            try {
                if(checkInput(maNhanVien, matKhau)) {
                    if(login(maNhanVien, matKhau)) {
                        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                        TaiKhoan taiKhoan = taiKhoanService.getTaiKhoanById(maNhanVien);
//                        openFormQuanLy(stage, taiKhoan);
                        openFormLoading(taiKhoan);
                    }
                    else {
                        lblLoginError.setText("Mã nhân viên hoặc mật khẩu không đúng");
                    }
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
        });

        txtMatKhau.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                String maNhanVien = txtMaNhanVien.getText().trim();
                String matKhau = txtMatKhau.getText();

                if(checkInput(maNhanVien, matKhau)) {
                    if(login(maNhanVien, matKhau)) {
                        Stage stage = (Stage)((Node)ke.getSource()).getScene().getWindow();
                        try {
                            TaiKhoan taiKhoan = taiKhoanService.getTaiKhoanById(maNhanVien);
//                            openFormQuanLy(stage, taiKhoan);
                            openFormLoading(taiKhoan);
                            stage.close();

                        }catch (Exception exception){
                            exception.printStackTrace();
                        }
                    }
                    else {
                        lblLoginError.setText("Mã nhân viên hoặc mật khẩu không đúng");
                    }
                }
            }
        });

        txtMaNhanVien.textProperty().addListener((observable, oldValue, newValue) -> {
            lblMaNhanVienError.setText("");
            lblLoginError.setText("");
        });

        txtMatKhau.textProperty().addListener((observable, oldValue, newValue) -> {
            lblMatKhauError.setText("");
            lblLoginError.setText("");
        });

        lblQuenMatKhau.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            openFormResetPassword(stage);
        });
    }

    private void openFormResetPassword(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-reset-password.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toString());
            stage.setScene(scene);
            stage.setTitle("Moto Soft");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openFormLoading(TaiKhoan taiKhoan) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-loading.fxml"));
            fxmlLoader.setController(new FormLoadingController(taiKhoan));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.setTitle("Moto Soft");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean login(String maNhanVien, String matKhau) {
        try {
            TaiKhoan taiKhoan = taiKhoanService.getTaiKhoanById(maNhanVien);
            if (taiKhoan != null) {
                String hashPassword = StringUtils.createHashString(matKhau + taiKhoan.getChuoiRiengTu());
                if (hashPassword.equals(taiKhoan.getMatKhau()))
                    return true;
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return false;
    }

    private boolean checkInput(String maNhanVien, String matKhau) {
        if(maNhanVien.isEmpty()) {
            lblMaNhanVienError.setText("Mã nhân viên không được để trống");
            return false;
        }

        if(matKhau.isEmpty()) {
            lblMatKhauError.setText("Mật khẩu không được để trống");
            return false;
        }

        return true;
    }

    private void closeStage(MouseEvent mouseEvent){
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
