package com.example.fashionshop.cus_comp;

import com.example.fashionshop.MotoSoftApp;
import com.example.model.DanhMuc;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.EqualsAndHashCode;

import java.io.IOException;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DanhMucItem extends HBox {

    @FXML private VBox btnDelete;
    @FXML private Label lblTenDanhMuc;

    @EqualsAndHashCode.Include
    private DanhMuc danhMuc;

    public DanhMucItem(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;

        init();
        loadData();
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("cus_comp_views/danh-muc-item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadData() {
        if(danhMuc != null) {
            lblTenDanhMuc.setText(danhMuc.getTenDanhMuc());
        }
    }

    public VBox getBtnDelete() {
        return btnDelete;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }
}
