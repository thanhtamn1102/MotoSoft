package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
//import com.example.fashionshop.model.TaiKhoan;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.example.model.TaiKhoan;

import java.net.URL;
import java.util.ResourceBundle;

public class FormTaiKhoanController implements Initializable {

    private TaiKhoan taiKhoan;

    @FXML private Label lblHoTen;
    @FXML private Label lblMaNV;
    @FXML private Label lblGioiTinh;
    @FXML private Label lblNgaySinh;
    @FXML private Label lblCccd;
    @FXML private Label lblSdt;
    @FXML private Label lblChucVu;
    @FXML private Label lblEmail;
    @FXML private Label lblDiaChi;
    @FXML private ImageView imageView;
    @FXML private Button btnDong;

    public FormTaiKhoanController(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        control();
    }

    public void control(){

        btnDong.setOnMousePressed(mouseEvent ->{
            closeStage(mouseEvent);
        });



    }

    public void loadData(){
        if(taiKhoan!=null){
            lblHoTen.setText(taiKhoan.getNhanVien().getHoTen());
            lblMaNV.setText(taiKhoan.getNhanVien().getMaNhanVien());
            lblChucVu.setText(taiKhoan.getNhanVien().getChucVu().getTenChucVu());
            lblCccd.setText(taiKhoan.getNhanVien().getCccd());
            lblEmail.setText(taiKhoan.getNhanVien().getEmail());
            lblDiaChi.setText(taiKhoan.getNhanVien().getDiaChi().getDiaChiChiTiet());
            lblNgaySinh.setText(taiKhoan.getNhanVien().getNgaySinh().toString());
            lblSdt.setText(taiKhoan.getNhanVien().getSdt());
            if(taiKhoan.getNhanVien().isGioiTinh()){
                lblGioiTinh.setText("Nam");
            } else{ lblGioiTinh.setText("Nữ");}
        }

        Image im;
        if(taiKhoan.getNhanVien().getHinhAnh() != null && (!taiKhoan.getNhanVien().getHinhAnh().isEmpty()))
            im = new Image(taiKhoan.getNhanVien().getHinhAnh(), true);
        else
            im = new Image(MotoSoftApp.class.getResource("drawable/no-image_available.png").toString(), true);
        imageView.setImage(im);
    }

    private void closeStage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }



}
