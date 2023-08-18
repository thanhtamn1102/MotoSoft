package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
//import com.example.fashionshop.model.NhanVien;
import com.example.fashionshop.utils.StringUtils;
import com.example.fashionshop.values.StringValues;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.example.model.NhanVien;
import com.example.service.TaiKhoanService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class FormTaiKhoanNhanVienController implements Initializable {

    private NhanVien nhanvien;
    private ObservableList<NhanVien> dsNhanVien;

    @FXML private PasswordField txtMatKhau;
    @FXML private PasswordField txtXacNhanMatKhau;
    @FXML private Button btnLuu;
    @FXML private Button btnThoat;
    @FXML private Label lblMatKhauError;
    @FXML private Label lblXacNhanMatKhauError;
    private Context context;
    private TaiKhoanService taiKhoanService;

    public FormTaiKhoanNhanVienController(ObservableList<NhanVien> dsNhanVien, NhanVien nhanvien ) {
        this.nhanvien = nhanvien;
        this.dsNhanVien = dsNhanVien;

        try {
            context = new InitialContext();
            taiKhoanService = (TaiKhoanService) context.lookup(StringValues.SERVER_URL + "TaiKhoanService");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
        addEvent();
    }

    private void init(){
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-tai-khoan-nhan-vien.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    private void addEvent(){
        btnLuu.setOnMousePressed(mouseEvent -> {
            String matKhau = txtMatKhau.getText().trim();
            String xacNhanMatKhau = txtXacNhanMatKhau.getText().trim();
            String chuoiriengtu = StringUtils.createUniqueString();
            String hashPassword = StringUtils.createHashString(matKhau + chuoiriengtu);



            if(checkInput(matKhau, xacNhanMatKhau)){
                NhanVien nhanVien = this.nhanvien;
                nhanvien.getTaiKhoan().setMatKhau(hashPassword);
                nhanvien.getTaiKhoan().setChuoiRiengTu(chuoiriengtu);
                nhanvien.setMaNhanVien(this.nhanvien.getMaNhanVien());
                try {
                    updateTaiKhoan(nhanVien, mouseEvent);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        btnThoat.setOnMousePressed(mouseEvent -> {
            closeStage(mouseEvent);
        });
    }

    private boolean checkInput(String matKhau, String xacNhanMatKhau){
        setLabelErrorEmpty();

        if(matKhau.isEmpty()){
            lblMatKhauError.setText("Mật khẩu không được để trống");
            return false;
        }
        if(!xacNhanMatKhau.equals(matKhau)){
            lblXacNhanMatKhauError.setText("Xác nhận mật khẩu chưa chính xác");
            return false;
        }
        return true;
    }

    private void setLabelErrorEmpty(){
        lblMatKhauError.setText("");
        lblXacNhanMatKhauError.setText("");
    }

    private void updateTaiKhoan(NhanVien nhanVien, MouseEvent mouseEvent) throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cập nhật thông tin nhân viên");
        alert.setHeaderText(null);

        if (taiKhoanService.updateTaiKhoan(nhanVien.getTaiKhoan())) {
            dsNhanVien.set(dsNhanVien.indexOf(nhanVien), nhanVien);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Đổi mật khẩu thành công!");
            alert.show();
            closeStage(mouseEvent);
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Error: Đổi mật khẩu thất bại");
            alert.show();
        }
    }

    private void closeStage(MouseEvent mouseEvent){
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
