package com.example.fashionshop.cus_comp;

import com.example.fashionshop.MotoSoftApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;

public class FormCreateNewPassword extends VBox {

    @FXML private PasswordField txtMatKhau, txtXacNhanMatKhau;
    @FXML private Label lblMatKhauError, lblXacNhanMatKhauError;
    @FXML private Button btnTaoMatKhau;

    public FormCreateNewPassword() {
        init();
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("cus_comp_views/form-create-new-password.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public PasswordField getTxtMatKhau() {
        return txtMatKhau;
    }

    public PasswordField getTxtXacNhanMatKhau() {
        return txtXacNhanMatKhau;
    }

    public Label getLblMatKhauError() {
        return lblMatKhauError;
    }

    public Label getLblXacNhanMatKhauError() {
        return lblXacNhanMatKhauError;
    }

    public Button getBtnTaoMatKhau() {
        return btnTaoMatKhau;
    }

}
