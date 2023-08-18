package com.example.fashionshop.cus_comp;

import com.example.fashionshop.MotoSoftApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FormXacThucResetPassword extends VBox {

    @FXML private TextField txtMaNhanVien;
    @FXML private TextField txtMaXacThuc;
    @FXML private HBox btnSendOTP;
    @FXML private Label lblMaNhanVienError;
    @FXML private Label lblMaXacThucError;
    @FXML private Button btnXacThuc;

    public FormXacThucResetPassword() {
        init();
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("cus_comp_views/form-xac-thuc-reset-password.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public TextField getTxtMaNhanVien() {
        return txtMaNhanVien;
    }

    public TextField getTxtMaXacThuc() {
        return txtMaXacThuc;
    }

    public HBox getBtnSendOTP() {
        return btnSendOTP;
    }

    public Label getLblMaNhanVienError() {
        return lblMaNhanVienError;
    }

    public Label getLblMaXacThucError() {
        return lblMaXacThucError;
    }

    public Button getBtnXacThuc() {
        return btnXacThuc;
    }

}
