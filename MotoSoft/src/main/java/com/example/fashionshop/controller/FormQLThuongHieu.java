package com.example.fashionshop.controller;

import com.example.fashionshop.cus_comp.ReadOnlyObjectWrapperTableColume;
//import com.example.fashionshop.impl.ThuongHieuImpl;
//import com.example.fashionshop.model.ThuongHieu;
//import com.example.fashionshop.service.ThuongHieuService;
import com.example.fashionshop.values.StringValues;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.model.ThuongHieu;
import com.example.service.ThuongHieuService;
import com.example.service.TinhTPService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FormQLThuongHieu implements Initializable {

    private static Context context;
    private ObservableList<ThuongHieu> dsThuongHieu;
    private ThuongHieuService thuongHieuService;
    @FXML private TextField txtTenThuongHieu;
    @FXML private Button btnThem, btnCapNhat, btnXoa, btnHienThiTatCa, btnTimKiem, btnThoat;
    @FXML private TableView<ThuongHieu> tableView;
    @FXML private TableColumn<ThuongHieu, ThuongHieu> colSelect;
    @FXML private TableColumn<ThuongHieu, String> colTenThuongHieu;

    private ContextMenu contextMenuTenMauError;
    private MenuItem menuItemTenMauError;

    public FormQLThuongHieu(ObservableList<ThuongHieu> dsThuongHieu) {
        try {
            context = new InitialContext();
            thuongHieuService = (ThuongHieuService) context.lookup(StringValues.SERVER_URL + "ThuongHieuService");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        this.dsThuongHieu = dsThuongHieu;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTableView();
        addEvent();
    }

    private void setTableView(){
        menuItemTenMauError = new MenuItem();
        menuItemTenMauError.setDisable(true);
        menuItemTenMauError.getStyleClass().clear();
        menuItemTenMauError.setStyle("-fx-text-fill: red");
        contextMenuTenMauError = new ContextMenu();
        contextMenuTenMauError.getItems().add(menuItemTenMauError);

        colSelect = new TableColumn<>();
        colTenThuongHieu = new TableColumn<>("Tên thương hiệu");

        colSelect.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<ThuongHieu>());
        colSelect.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ThuongHieu, ThuongHieu> call(TableColumn<ThuongHieu, ThuongHieu> thuongHieuThuongHieuTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(ThuongHieu thuongHieu, boolean b) {
                        super.updateItem(thuongHieu, b);
                        if (thuongHieu != null) {
                            CheckBox checkBox = new CheckBox("");
                            setGraphic(checkBox);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        colTenThuongHieu.setCellValueFactory(new PropertyValueFactory<>("tenThuongHieu"));

        colSelect.setMaxWidth(1500);
        colSelect.setStyle("-fx-alignment: center;");
        colTenThuongHieu.setStyle("-fx-alignment: center-left;");

        tableView.getColumns().addAll(
                colSelect,
                colTenThuongHieu
        );

        tableView.setItems(dsThuongHieu);

    }

    private void addEvent(){
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ThuongHieu>() {
            @Override
            public void changed(ObservableValue<? extends ThuongHieu> observableValue, ThuongHieu thuongHieu, ThuongHieu t1) {
                if(t1 != null){
                    txtTenThuongHieu.setText(t1.getTenThuongHieu());
                }
            }
        });

        btnThem.setOnMousePressed(mouseEvent -> {
            String tenThuongHieu = txtTenThuongHieu.getText().trim();
            if(checkInput(tenThuongHieu)){
                ThuongHieu thuongHieu = new ThuongHieu(tenThuongHieu);
                addThuongHieu(thuongHieu, mouseEvent);
            }
        });

        btnCapNhat.setOnMousePressed(mouseEvent -> {
            ThuongHieu thuongHieu = tableView.getSelectionModel().getSelectedItem();
            if(thuongHieu != null) {
                String tenThuongHieu = txtTenThuongHieu.getText().trim();
                if(!tenThuongHieu.isEmpty()){
                    thuongHieu.setTenThuongHieu(tenThuongHieu);
                    updateThuongHieu(thuongHieu, mouseEvent);
                } else {
                    menuItemTenMauError.setText("Tên thương hiệu không được để trống");
                    contextMenuTenMauError.show(txtTenThuongHieu, Side.BOTTOM, 10, 5);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Bạn chưa chọn thương hiệu cần cập nhật");
                alert.show();
            }
        });

        btnXoa.setOnMousePressed(mouseEvent -> {
            ThuongHieu thuongHieu = tableView.getSelectionModel().getSelectedItem();
            if(thuongHieu != null) {
                deleteThuongHieu(thuongHieu, mouseEvent);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Bạn chưa chọn thương hiệu cần xóa");
                alert.show();
            }
        });

        btnThoat.setOnMousePressed(mouseEvent -> {
            closeStage(mouseEvent);
        });

        btnTimKiem.setOnMousePressed(mouseEvent -> {
            List<ThuongHieu> dstimKiem = new ArrayList<>();
            String timKiem = txtTenThuongHieu.getText().trim();
            for (ThuongHieu thuongHieu: dsThuongHieu) {
                if(thuongHieu.getTenThuongHieu().equals(timKiem))
                    dstimKiem.add(thuongHieu);
            }
            tableView.setItems(FXCollections.observableArrayList(dstimKiem));
        });
        btnHienThiTatCa.setOnMousePressed(mouseEvent -> {
            tableView.setItems(dsThuongHieu);
        });
    }
    private void addThuongHieu(ThuongHieu thuongHieu, MouseEvent mouseEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thêm thương hiệu");
        alert.setHeaderText(null);

        try {
            if(thuongHieuService.addThuongHieu(thuongHieu) && dsThuongHieu.add(thuongHieu)){
                alert.setContentText("Thêm thương hiệu thành công");
                alert.show();
            }
            else {
                alert.setContentText("Error: Thêm thương hiệu thất bại");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void updateThuongHieu(ThuongHieu thuongHieu, MouseEvent mouseEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cập nhật thông tin thương hiệu");
        alert.setHeaderText(null);

        try {
            if(thuongHieuService.updateThuongHieu(thuongHieu)) {
                dsThuongHieu.set(dsThuongHieu.indexOf(thuongHieu), thuongHieu);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Cập nhật thông tin thương hiệu thành công!");
                alert.show();
            }
            else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error: Cập nhật thông tin sản thương hiệu thành công");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void deleteThuongHieu(ThuongHieu thuongHieu, MouseEvent mouseEvent){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xoá thương hiệu");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa thương hiệu này không?");

        Optional<ButtonType> optional = alert.showAndWait();
        try {
            if(optional.get() == ButtonType.OK) {
                if(thuongHieuService.deleteThuongHieu(thuongHieu) && dsThuongHieu.remove(thuongHieu)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Xóa thương hiệu thành công");
                }
                else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Error: Xóa thương hiệu không thành công");
                }
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void closeStage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private boolean checkInput(String tenThuongHieu){
        if(tenThuongHieu.isEmpty()){
            menuItemTenMauError.setText("Tên thương hiệu không được để trống");
            contextMenuTenMauError.show(txtTenThuongHieu, Side.BOTTOM, 10, 5);
            return false;
        }
        for (ThuongHieu thuongHieu : dsThuongHieu) {
            if(thuongHieu.getTenThuongHieu().equals(tenThuongHieu)){
                menuItemTenMauError.setText("Tên thương hiệu đã có");
                contextMenuTenMauError.show(txtTenThuongHieu, Side.BOTTOM, 10, 5);
                return false;
            }
        }

        return true;
    }


}
