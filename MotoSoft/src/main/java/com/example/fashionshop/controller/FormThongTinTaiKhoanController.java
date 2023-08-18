package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.GenderGroup;
import com.example.model.*;
import com.example.service.QuanHuyenService;
import com.example.service.TinhTPService;
import com.example.service.XaPhuongService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class FormThongTinTaiKhoanController implements Initializable {

    @FXML private VBox boxAvatar;
    @FXML private Label lblHoVaTen, lblChucVu;
    @FXML private Button btnUploadNewAvatar, btnDangXuat;
    @FXML private HBox btnNam, btnNu;
    @FXML private Label lblBtnNam, lblBtnNu;
    private GenderGroup genderGroup;
    @FXML private TextField txtMaNhanVien, txtHoVaTen, txtCCCD, txtEmail, txtSoDienThoai, txtDiaChiChiTiet;
    @FXML private HBox boxMaNhanVienError, boxHoVaTenError, boxCCCDError, boxEmailError, boxSoDienThoaiError, boxNgaySinhError,
                        boxChucVuError, boxTinhTPError, boxQuanHuyenError, boxXaPhuongError, boxDiaChiChiTietError;
    @FXML private ComboBox<ChucVu> cbxChucVu;
    @FXML private ComboBox<Integer> cbxNgay;
    @FXML private ComboBox<String> cbxThang;
    @FXML private ComboBox<Integer> cbxNam;
    @FXML private ComboBox<TinhTP> cbxTinhTP;
    @FXML private ComboBox<QuanHuyen> cbxQuanHuyen;
    @FXML private ComboBox<XaPhuong> cbxXaPhuong;

    private Label lblMaNhanVienError, lblHoVaTenError, lblCCCDError, lblEmailError, lblSoDienThoaiError, lblNgaySinhError,
                    lblChucVuError, lblTinhTPError, lblQuanHuyenError, lblXaPhuongError, lblDiaChiChiTietError;

    private TaiKhoan taiKhoan;

    private TinhTPService tinhTPService;
    private QuanHuyenService quanHuyenService;
    private XaPhuongService xaPhuongService;

    public FormThongTinTaiKhoanController(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addControls();
        addEvents();
        loadData();
    }

    private void addControls() {
        genderGroup = new GenderGroup(btnNam, lblBtnNam, btnNu, lblBtnNu);

        lblMaNhanVienError = new Label();
        lblMaNhanVienError.setTextFill(Color.RED);
        lblHoVaTenError = new Label();
        lblHoVaTenError.setTextFill(Color.RED);
        lblCCCDError = new Label();
        lblCCCDError.setTextFill(Color.RED);
        lblEmailError = new Label();
        lblEmailError.setTextFill(Color.RED);
        lblSoDienThoaiError = new Label();
        lblSoDienThoaiError.setTextFill(Color.RED);
        lblNgaySinhError = new Label();
        lblNgaySinhError.setTextFill(Color.RED);
        lblChucVuError = new Label();
        lblChucVuError.setTextFill(Color.RED);
        lblTinhTPError = new Label();
        lblTinhTPError.setTextFill(Color.RED);
        lblQuanHuyenError = new Label();
        lblQuanHuyenError.setTextFill(Color.RED);
        lblXaPhuongError = new Label();
        lblXaPhuongError.setTextFill(Color.RED);
        lblDiaChiChiTietError = new Label();
        lblDiaChiChiTietError.setTextFill(Color.RED);
    }

    private void addEvents() {
        btnDangXuat.setOnMousePressed(mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Bạn có chắn chắn muốn đăng xuất?");
            alert.setHeaderText(null);
            Optional<ButtonType> optional = alert.showAndWait();
            if(optional.get() == ButtonType.OK) {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-login.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();

                Stage thisStage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
                Stage parentStage = (Stage) thisStage.getOwner();
                thisStage.close();
                parentStage.close();
            }
        });
    }

    private void loadData() {
        if(taiKhoan != null) {
            NhanVien nhanVien = taiKhoan.getNhanVien();
            if(nhanVien != null) {
                lblHoVaTen.setText(nhanVien.getHoTen());

                ChucVu chucVu = nhanVien.getChucVu();
                if(chucVu != null) {
                    lblChucVu.setText(chucVu.getTenChucVu());
                    cbxChucVu.getSelectionModel().select(chucVu);
                }

                txtMaNhanVien.setText(nhanVien.getMaNhanVien());
                txtHoVaTen.setText(nhanVien.getHoTen());
                txtCCCD.setText(nhanVien.getCccd());
                txtEmail.setText(nhanVien.getEmail());
                txtSoDienThoai.setText(nhanVien.getSdt());

                if (nhanVien.isGioiTinh()) {
                    genderGroup.setBtnNamSelected(true);
                } else {
                    genderGroup.setBtnNuSelected(true);
                }

                DiaChi diaChi = nhanVien.getDiaChi();
                if(diaChi != null) {
                    txtDiaChiChiTiet.setText(diaChi.getDiaChiChiTiet());

                    cbxTinhTP.getSelectionModel().select(diaChi.getTinhTP() == null ? null : diaChi.getTinhTP());
                    cbxQuanHuyen.getSelectionModel().select(diaChi.getQuanHuyen() == null ? null : diaChi.getQuanHuyen());
                    cbxXaPhuong.getSelectionModel().select(diaChi.getXaPhuong() == null ? null : diaChi.getXaPhuong());
                }

                Date ngaySinh = nhanVien.getNgaySinh();
                if(ngaySinh != null) {
                    cbxNgay.getSelectionModel().select(ngaySinh.getDay());

                    DateFormatSymbols dfs = new DateFormatSymbols();
                    cbxThang.getSelectionModel().select(dfs.getMonths()[ngaySinh.getMonth()]);

                    cbxNam.getSelectionModel().select(ngaySinh.getYear());
                }
            }
        }
    }

    public void closeStage(MouseEvent mouseEvent){
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
