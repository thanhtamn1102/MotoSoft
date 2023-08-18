package com.example.fashionshop.controller;

import com.example.fashionshop.cus_comp.VaiTroNhomQuyenItem;
import com.example.fashionshop.values.StringValues;
import com.example.model.*;
import com.example.service.VaiTroService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.URL;
import java.util.*;

public class FormVaiTroController implements Initializable {

    public static final int ADD = 1;
    public static final int UPDATE = 2;
    public static final int VIEW = 3;

    @FXML private TextField txtTenVaiTro;
    @FXML private VBox col1, col2, col3;
    @FXML private Button btnLuu, btnThoat, btnCapNhat, btnXoa;

    private ObservableList<VaiTro> dsVaiTro;
    private VaiTro vaiTro;
    private int type;

    private VaiTroService vaiTroService;

    public FormVaiTroController(ObservableList<VaiTro> dsVaiTro, VaiTro vaiTro, int type) {
        this.dsVaiTro = dsVaiTro;
        this.vaiTro = vaiTro;
        this.type = type;

        try {
            Context context = new InitialContext();
            vaiTroService = (VaiTroService) context.lookup(StringValues.SERVER_URL + "VaiTroService");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addControls();
        addEvents();
        loadData();

        if(type == ADD) {
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);
        }
        else if(type == UPDATE) {
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);
        }
        else if(type == VIEW) {
            btnLuu.setVisible(false);
        }
    }

    private void addControls() {

    }

    private void addEvents() {
        btnThoat.setOnMousePressed(mouseEvent -> {
            closeStage(mouseEvent);
        });

        btnLuu.setOnMousePressed(mouseEvent -> {
            if(type == ADD) {

            } else if(type == UPDATE) {
                updateVaiTro(vaiTro, mouseEvent);
            }
        });

        btnCapNhat.setOnMouseClicked(mouseEvent -> {
            if(btnCapNhat.getText().equalsIgnoreCase("Cập nhật")) {
                btnCapNhat.setText("Hủy cập nhật"); btnCapNhat.setStyle("-fx-background-color: #C4C4C4");
                btnLuu.setVisible(true);
                btnXoa.setVisible(false);
                setInputEditable(true);
                type = UPDATE;
            }
            else {
                btnCapNhat.setText("Cập nhật"); btnCapNhat.setStyle("-fx-background-color: #0C75F5");
                btnLuu.setVisible(false);
                btnXoa.setVisible(true);
                setInputEditable(false);
                type = VIEW;
                loadData();
            }
        });

        btnXoa.setOnMousePressed(mouseEvent -> {
            deleteVaiTro();
        });
    }

    private void setInputEditable(boolean b) {
        txtTenVaiTro.setEditable(b);
    }

    private void loadData() {
        col1.getChildren().clear();
        col2.getChildren().clear();
        col3.getChildren().clear();

        if(vaiTro != null) {
            txtTenVaiTro.setText(vaiTro.getTenVaiTro());

            List<VaiTroNhomQuyen> dsNhomQuyen = vaiTro.getDsNhomQuyen();

            int nRow = dsNhomQuyen.size() % 3 == 0 ? dsNhomQuyen.size() / 3 : dsNhomQuyen.size() / 3 + 1;

            for(int r = 0; r < nRow; r++) {
                for(int c = 0; c < 3 && r * 3 + c < dsNhomQuyen.size(); c++) {

                    VaiTroNhomQuyen nhomQuyen = dsNhomQuyen.get(r * 3 + c);

                    VaiTroNhomQuyenItem vaiTroNhomQuyenItem = new VaiTroNhomQuyenItem(nhomQuyen);

                    switch (c) {
                        case 0: col1.getChildren().add(vaiTroNhomQuyenItem); break;
                        case 1: col2.getChildren().add(vaiTroNhomQuyenItem); break;
                        case 2: col3.getChildren().add(vaiTroNhomQuyenItem); break;
                    }
                }
            }
        }
    }

    private void closeStage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void deleteVaiTro() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xóa vai trò");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa vai trò này không?");

        Optional<ButtonType> option = alert.showAndWait();
        if(option.get() == ButtonType.OK) {

        }
    }

    private void updateVaiTro(VaiTro vaiTro, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cập nhật vai trò");
        alert.setHeaderText(null);

        try {
            if(vaiTroService.updateVaiTro(vaiTro)) {
                alert.setContentText("Cập nhật thông tin vai trò thành công");
                alert.show();
                closeStage(mouseEvent);
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Cập nhật thông tin vai trò không thành công");
                alert.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
