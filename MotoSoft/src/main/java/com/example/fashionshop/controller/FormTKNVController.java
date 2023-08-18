package com.example.fashionshop.controller;

import com.example.fashionshop.cus_comp.AutoCompleteTextField;
import com.example.fashionshop.values.StringValues;
import com.example.model.KhachHang;
import com.example.model.NhanVien;
import com.example.model.TaiKhoan;
import com.example.model.VaiTro;
import com.example.service.NhanVienService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class FormTKNVController implements Initializable {

    private static final int VIEW = 0;
    private static final int ADD = 1;
    private static final int UPDATE = 2;

    @FXML private HBox boxNhanVien;
    @FXML private ComboBox<VaiTro> cbxVaiTro;
    @FXML private TextField txtMatKhau;
    @FXML private TextField txtXacNhanMatKhau;
    @FXML private VBox boxMatKhau;
    @FXML private Label lblNhanVienError, lblVaiTroError, lblMatKhauError, lblXacNhanMatKhauError;
    @FXML private Button btnCapNhat, btnXoa, btnLuu, btnThoat;

    private AutoCompleteTextField<NhanVien> txtNhanVien;

    private ObservableList<TaiKhoan> dsTaiKhoan;
    private TaiKhoan taiKhoan;

    private NhanVienService nhanVienService;

    public FormTKNVController(ObservableList<TaiKhoan> dsTaiKhoan, TaiKhoan taiKhoan) {
        this.dsTaiKhoan = dsTaiKhoan;
        this.taiKhoan = taiKhoan;

        if(taiKhoan == null) {
            this.taiKhoan = new TaiKhoan();
        }

        try {
            Context context = new InitialContext();
            nhanVienService = (NhanVienService) context.lookup(StringValues.SERVER_URL + "NhanVienService");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addControls();
            addEvents();
            loadData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addControls() throws RemoteException {
        txtNhanVien = new AutoCompleteTextField<NhanVien>(FXCollections.observableArrayList(nhanVienService.getAllNhanVien()));
        txtNhanVien.setStyle("-fx-background-color: white; -fx-border-color: white;");
        txtNhanVien.setFont(new Font("Segoe UI", 13.5));
        txtNhanVien.setPromptText("Nhập tên hoặc số điện thoại để tìm khách hàng");
        boxNhanVien.getChildren().add(txtNhanVien);
    }

    private void addEvents() {
        txtNhanVien.getEntryMenu().setOnAction(e -> {
            ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, event -> {
                if (txtNhanVien.getLastSelectedObject() != null) {
                    NhanVien nhanVien = txtNhanVien.getLastSelectedObject();
                    taiKhoan.setNhanVien(nhanVien);
                    taiKhoan.setMaNhanVien(nhanVien.getMaNhanVien());
                }
            });
        });

        btnThoat.setOnMousePressed(mouseEvent -> {

        });

        btnLuu.setOnMousePressed(mouseEvent -> {

        });

        btnCapNhat.setOnMousePressed(mouseEvent -> {

        });

        btnXoa.setOnMousePressed(mouseEvent -> {

        });
    }

    private void loadData() {
        if(taiKhoan != null) {
            NhanVien nhanVien = taiKhoan.getNhanVien();
            if(nhanVien != null) {

            }
        }
    }
}
