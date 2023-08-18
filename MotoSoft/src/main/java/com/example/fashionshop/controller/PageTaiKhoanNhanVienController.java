package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.ReadOnlyObjectWrapperTableColume;
//import com.example.fashionshop.model.NhanVien;
import com.example.fashionshop.values.Icons;
import com.example.fashionshop.values.StringValues;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.model.NhanVien;
import com.example.service.NhanVienService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.rmi.RemoteException;

public class PageTaiKhoanNhanVienController extends VBox {

    @FXML private TableView<NhanVien> tableView;
    @FXML private TableColumn<NhanVien, NhanVien> colSelect;
    @FXML private TableColumn<NhanVien, NhanVien> colTaiKhoan;
    @FXML private TableColumn<NhanVien, NhanVien> colHoTen;
    @FXML private TableColumn<NhanVien, NhanVien> colEdit;
    @FXML private TextField txtTimKiem;
    @FXML private Button btnThemMoi;

    private ObservableList<NhanVien> dsNhanVien = FXCollections.observableArrayList();
    private ObservableList<NhanVien> dsTaiKhoanNhanVien = FXCollections.observableArrayList();
    private FilteredList<NhanVien> filteredList = new FilteredList<>(dsTaiKhoanNhanVien, b -> true);
    private Context context;
    private NhanVienService nhanVienService;

    public PageTaiKhoanNhanVienController() throws RemoteException {
        try {
            context = new InitialContext();
            nhanVienService = (NhanVienService) context.lookup(StringValues.SERVER_URL + "NhanVienService");

            init();
            setTableView();
            loadData();
            addEvent();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void init(){
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("pages/page-tai-khoan-nhan-vien.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void loadData() throws RemoteException {
        dsNhanVien.setAll(nhanVienService.getAllNhanVien());
        for (NhanVien nhanVien : dsNhanVien) {
            if (nhanVien.getTaiKhoan() != null) {
                dsTaiKhoanNhanVien.add(nhanVien);
            }
        }
    }

    private void setTableView(){
//        tableView.setEditable(false);
        tableView.getColumns().addAll(
                colSelect = new TableColumn<>("Select"),
                colHoTen = new TableColumn<>("Họ tên nhân viên"),
                colTaiKhoan = new TableColumn<>("Tài khoản"),
                colEdit = new TableColumn<>("Đổi mật khâẩu")
        );


        colSelect.setStyle("-fx-alignment: center;");
        colHoTen.setStyle("-fx-alignment: center-left;");
        colTaiKhoan.setStyle("-fx-alignment: center;");
        colEdit.setStyle("-fx-alignment: center;");


        colSelect.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colSelect.setCellFactory(new Callback<TableColumn<NhanVien, NhanVien>, TableCell<NhanVien, NhanVien>>() {
            @Override
            public TableCell<NhanVien, NhanVien> call(TableColumn<NhanVien, NhanVien> employeeEmployeeTableColumn) {
                return new TableCell<NhanVien, NhanVien>() {
                    @Override
                    protected void updateItem(NhanVien nhanVien, boolean b) {
                        super.updateItem(nhanVien, b);
                        if (nhanVien != null) {
                            CheckBox checkBox = new CheckBox();
                            setGraphic(checkBox);
                        }
                        else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
        colHoTen.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        colTaiKhoan.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        colEdit.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<NhanVien>());
        colEdit.setCellFactory(new Callback<TableColumn<NhanVien, NhanVien>, TableCell<NhanVien, NhanVien>>() {
            @Override
            public TableCell<NhanVien, NhanVien> call(TableColumn<NhanVien, NhanVien> employeeTableColumn) {
                return new TableCell<NhanVien, NhanVien>() {
                    @Override
                    protected void updateItem(NhanVien nhanVien, boolean b) {
                        super.updateItem(nhanVien, b);
                        if (nhanVien != null) {
                            SVGPath editIcon = new SVGPath();
                            editIcon.setContent(Icons.EDIT);

                            Region btnEdit = new Region();
                            btnEdit.setMaxWidth(19);
                            btnEdit.setMaxHeight(20);
                            btnEdit.setPrefWidth(19);
                            btnEdit.setPrefHeight(20);
                            btnEdit.setShape(editIcon);
                            btnEdit.setStyle("-fx-background-color: #04B431;");

                            btnEdit.setOnMouseClicked(mouseEvent -> {
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-tai-khoan-nhan-vien.fxml"));
                                    fxmlLoader.setController(new FormTaiKhoanNhanVienController(dsNhanVien, nhanVien));
                                    Scene scene = new Scene(fxmlLoader.load());
                                    Stage stage = new Stage();
                                    stage.setScene(scene);
                                    stage.setTitle("");
                                    stage.show();
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            });

                            setGraphic(btnEdit);
                            setText(null);
                        }
                        else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });


    }
    private void addEvent(){
        txtTimKiem.setPromptText("Nhập họ tên hoặc tài khoản");
        txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(nhanVien -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (nhanVien.getHoTen().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (nhanVien.getMaNhanVien().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<NhanVien> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }

}
