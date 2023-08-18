package com.example.fashionshop.cus_comp;

import com.example.fashionshop.MotoSoftApp;
import com.example.model.VaiTroNhomQuyen;
import com.example.model.VaiTroQuyen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class VaiTroNhomQuyenItem extends VBox {

    @FXML private CheckBox ckbNhomQuyen;
    @FXML private VBox boxQuyen;

    private VaiTroNhomQuyen vaiTroNhomQuyen;

    public VaiTroNhomQuyenItem(VaiTroNhomQuyen vaiTroNhomQuyen) {
        this.vaiTroNhomQuyen = vaiTroNhomQuyen;

        init();
        loadData();
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("cus_comp_views/vai-tro-nhom-quyen-item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadData() {
        if(vaiTroNhomQuyen != null) {
            ckbNhomQuyen.setText(vaiTroNhomQuyen.getNhomQuyen().getTenNhomQuyen());
            ckbNhomQuyen.setSelected(vaiTroNhomQuyen.getTrangThai());

            ckbNhomQuyen.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    vaiTroNhomQuyen.setTrangThai(t1);
                }
            });

            List<VaiTroQuyen> dsQuyen = vaiTroNhomQuyen.getDsQuyen();
            if(dsQuyen != null && dsQuyen.size() > 0) {
                for(VaiTroQuyen quyen : dsQuyen) {
                    CheckBox checkBox = new CheckBox(quyen.getQuyen().getTenQuyen());
                    checkBox.setSelected(quyen.getTrangThai());

                    checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                            quyen.setTrangThai(t1);
                        }
                    });

                    boxQuyen.getChildren().add(checkBox);
                }
            }
        }
    }
}
