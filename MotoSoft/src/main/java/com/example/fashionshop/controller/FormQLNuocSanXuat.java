package com.example.fashionshop.controller;

import com.example.fashionshop.cus_comp.ReadOnlyObjectWrapperTableColume;
//import com.example.fashionshop.impl.NuocSanXuatImpl;
//import com.example.fashionshop.model.NuocSanXuat;
//import com.example.fashionshop.service.NuocSanXuatService;
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
import com.example.model.NuocSanXuat;
import com.example.service.NuocSanXuatService;
import com.example.service.TinhTPService;
import com.example.service.XaPhuongService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FormQLNuocSanXuat implements Initializable {

    private ObservableList<NuocSanXuat> dsNuocSanXuat;
    private static NuocSanXuatService nuocSanXuatService;
    @FXML private TextField txtMaNuocSanXuat;
    @FXML private TextField txtTenNuocSanXuat;
    @FXML private Button btnThem, btnCapNhat, btnXoa, btnHienThiTatCa, btnTimKiem, btnThoat;
    @FXML private TableView<NuocSanXuat> tableView;
    @FXML private TableColumn<NuocSanXuat, NuocSanXuat> colSelect;
    @FXML private TableColumn<NuocSanXuat, Integer> colMaNuocSanXuat;
    @FXML private TableColumn<NuocSanXuat, String> colTenNuocSanXuat;

    private ContextMenu contextMenuTenMauError;
    private MenuItem menuItemTenMauError;
    private Context context;

    public FormQLNuocSanXuat(ObservableList<NuocSanXuat> dsNuocSanXuat) {
        Object nuocSanXuatServiceRemote = null;
        try {
            context = new InitialContext();
            nuocSanXuatService = (NuocSanXuatService) context.lookup(StringValues.SERVER_URL + "NuocSanXuatService");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        this.dsNuocSanXuat = dsNuocSanXuat;
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
        colMaNuocSanXuat = new TableColumn<>("Mã nước sản xuất");
        colTenNuocSanXuat = new TableColumn<>("Tên nước sản xuất");

        colSelect.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<NuocSanXuat>());
        colSelect.setCellFactory(new Callback<>() {
            @Override
            public TableCell<NuocSanXuat, NuocSanXuat> call(TableColumn<NuocSanXuat, NuocSanXuat> nuocSanXuatNuocSanXuatTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(NuocSanXuat nuocSanXuat, boolean b) {
                        super.updateItem(nuocSanXuat, b);
                        if (nuocSanXuat != null) {
                            CheckBox checkBox = new CheckBox("");
                            setGraphic(checkBox);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        colMaNuocSanXuat.setCellValueFactory(new PropertyValueFactory<>("maNuocSanXuat"));
        colTenNuocSanXuat.setCellValueFactory(new PropertyValueFactory<>("tenNuocSanXuat"));

        colSelect.setMaxWidth(1500);
        colSelect.setStyle("-fx-alignment: center;");
        colMaNuocSanXuat.setStyle("-fx-alignment: center;");
        colTenNuocSanXuat.setStyle("-fx-alignment: center-left;");

        tableView.getColumns().addAll(
                colSelect,
                colMaNuocSanXuat,
                colTenNuocSanXuat
        );

        tableView.setItems(dsNuocSanXuat);

    }

    private void addEvent(){
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NuocSanXuat>() {
            @Override
            public void changed(ObservableValue<? extends NuocSanXuat> observableValue, NuocSanXuat nuocSanXuat, NuocSanXuat t1) {
                if(t1 != null){
                    txtMaNuocSanXuat.setText(Integer.toString(t1.getMaNuocSanXuat()));
                    txtTenNuocSanXuat.setText(t1.getTenNuocSanXuat());
                }
            }
        });

        btnThem.setOnMousePressed(mouseEvent -> {
            String tenNuocSanXuat = txtTenNuocSanXuat.getText().trim();
            if(checkInput(tenNuocSanXuat)){
                NuocSanXuat nuocSanXuat = new NuocSanXuat(tenNuocSanXuat);
                addNuocSanXuat(nuocSanXuat, mouseEvent);
            }
        });

        btnCapNhat.setOnMousePressed(mouseEvent -> {
            String tenNuocSanXuat = txtTenNuocSanXuat.getText().trim();
            if(checkInput(tenNuocSanXuat)){
                NuocSanXuat nuocSanXuat = tableView.getSelectionModel().getSelectedItem();
                nuocSanXuat.setTenNuocSanXuat(tenNuocSanXuat);
                updateNuocSanXuat(nuocSanXuat, mouseEvent);
            }
        });
        btnXoa.setOnMousePressed(mouseEvent -> {
            NuocSanXuat nuocSanXuat = tableView.getSelectionModel().getSelectedItem();
            deleteNuocSanXuat(nuocSanXuat, mouseEvent);
        });

        btnThoat.setOnMousePressed(mouseEvent -> {
            closeStage(mouseEvent);
        });
        btnTimKiem.setOnMousePressed(mouseEvent -> {
            List<NuocSanXuat> dstimKiem = new ArrayList<>();
            String timKiem = txtTenNuocSanXuat.getText().trim();
            for (NuocSanXuat nuocSanXuat: dsNuocSanXuat) {
                if(nuocSanXuat.getTenNuocSanXuat().equals(timKiem))
                    dstimKiem.add(nuocSanXuat);
            }
            tableView.setItems(FXCollections.observableArrayList(dstimKiem));
        });
        btnHienThiTatCa.setOnMousePressed(mouseEvent -> {
            tableView.setItems(dsNuocSanXuat);
        });
    }
    private void addNuocSanXuat(NuocSanXuat nuocSanXuat, MouseEvent mouseEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thêm nước sản xuất");
        alert.setHeaderText(null);

        try {
            if(nuocSanXuatService.addNuocSanXuat(nuocSanXuat) && dsNuocSanXuat.add(nuocSanXuat)){
                alert.setContentText("Thêm nước sản xuất thành công");
                alert.show();
            }
            else {
                alert.setContentText("Error: Thêm nước sản xuất thất bại");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void updateNuocSanXuat(NuocSanXuat nuocSanXuat, MouseEvent mouseEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cập nhật thông tin nước sản xuất");
        alert.setHeaderText(null);

        try {
            if(nuocSanXuatService.updateNuocSanXuat(nuocSanXuat)) {
                dsNuocSanXuat.set(dsNuocSanXuat.indexOf(nuocSanXuat), nuocSanXuat);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Cập nhật thông tin nước sản xuất thành công!");
                alert.show();
            }
            else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error: Cập nhật thông tin sản nước sản xuất thành công");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void deleteNuocSanXuat(NuocSanXuat nuocSanXuat, MouseEvent mouseEvent){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xoá nước sản xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa nước sản xuất này không?");

        Optional<ButtonType> optional = alert.showAndWait();
        try {
            if(optional.get() == ButtonType.OK) {
                if(nuocSanXuatService.deleteNuocSanXuat(nuocSanXuat) && dsNuocSanXuat.remove(nuocSanXuat)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Xóa sản phẩm thành công");
                }
                else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Error: Xóa sản phẩm không thành công");
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

    private boolean checkInput(String tenNuocSanXuat){
        if(tenNuocSanXuat.isEmpty()){
            menuItemTenMauError.setText("Tên nước sản xuất không được để trống");
            contextMenuTenMauError.show(txtTenNuocSanXuat, Side.BOTTOM, 10, 5);
            return false;
        }
        for (NuocSanXuat nuocSanXuat : dsNuocSanXuat) {
            if(nuocSanXuat.getTenNuocSanXuat().equals(tenNuocSanXuat)){
                menuItemTenMauError.setText("Tên nước sản xuất đã có");
                contextMenuTenMauError.show(txtTenNuocSanXuat, Side.BOTTOM, 10, 5);
                return false;
            }
        }

        return true;
    }


}
