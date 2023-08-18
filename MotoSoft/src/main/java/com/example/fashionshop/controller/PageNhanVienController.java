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
import com.example.service.TaiKhoanService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Optional;



public class PageNhanVienController extends VBox {


    @FXML private TableView<NhanVien> tableView;
    @FXML private Button btnThemMoi;
    @FXML private TextField txtTimKiemHoTen, txtTimKiemMaNV, txtTimKiemSdt;

    private ObservableList<NhanVien> dsNhanVien = FXCollections.observableArrayList();
    private FilteredList<NhanVien> filteredList = new FilteredList<>(dsNhanVien, b -> true);

    private TableColumn<NhanVien, NhanVien> colSelect;
    private TableColumn<NhanVien, String> colID;
    private TableColumn<NhanVien, String> colName;
    private TableColumn<NhanVien, Date> colBirthDay;
    private TableColumn<NhanVien, Boolean> colGender;
    private TableColumn<NhanVien, String> colCccd;
    private TableColumn<NhanVien, String> colPhone;
    private TableColumn<NhanVien, String> colEmail;
    private TableColumn<NhanVien, String> colAddress;
    private TableColumn<NhanVien, Void> colAction;
    private TableColumn<NhanVien, NhanVien> colEdit;
    private TableColumn<NhanVien, NhanVien> colDelete;
    private Context context;
    private TaiKhoanService taiKhoanService;
    private NhanVienService nhanVienService;


    public PageNhanVienController() {
        try {
            context = new InitialContext();
            nhanVienService = (NhanVienService) context.lookup(StringValues.SERVER_URL + "NhanVienService");
            taiKhoanService = (TaiKhoanService) context.lookup(StringValues.SERVER_URL + "TaiKhoanService");

            init();
            addEvents();
            loadData();
            addControls();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("pages/page-nhan-vien.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        tableView.setEditable(false);
        tableView.getColumns().addAll(
                colSelect = new TableColumn<>("Select"),
                colID = new TableColumn<>("Mã nhân viên"),
                colName = new TableColumn<>("Họ tên nhân viên"),
                colBirthDay = new TableColumn<>("Ngày sinh"),
                colGender = new TableColumn<>("Giới tính"),
                colCccd = new TableColumn<>("Căn cước công dân"),
                colPhone = new TableColumn<>("Số điện thoại"),
                colEmail = new TableColumn<>("Email"),
                colAddress = new TableColumn<>("Địa chỉ"),
                colAction = new TableColumn<>("Hành động")

        );
        colAction.getColumns().addAll(
                colEdit = new TableColumn<>("Sửa"),
                colDelete = new TableColumn<>("Xóa")
        );

        colSelect.setMaxWidth(1800);
        colEdit.setMaxWidth(1800);
        colDelete.setMaxWidth(1800);

        colSelect.setStyle("-fx-alignment: center;");
        colID.setStyle("-fx-alignment: center;");
        colName.setStyle("-fx-alignment: center-left;");
        colBirthDay.setStyle("-fx-alignment: center;");
        colGender.setStyle("-fx-alignment: center;");
        colCccd.setStyle("-fx-alignment: center-left;");
        colPhone.setStyle("-fx-alignment: center-left;");
        colEmail.setStyle("-fx-alignment: center-left;");
        colAddress.setStyle("-fx-alignment: center-left;");
        colEdit.setStyle("-fx-alignment: center;");
        colDelete.setStyle("-fx-alignment: center;");



        colID.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        colName.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        colBirthDay.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        colBirthDay.setCellFactory(new Callback<TableColumn<NhanVien, Date>, TableCell<NhanVien, Date>>() {
            @Override
            public TableCell<NhanVien, Date> call(TableColumn<NhanVien, Date> nhanVienDateTableColumn) {

                return new TableCell<>(){
                    @Override
                    protected void updateItem(Date date, boolean b) {
                        super.updateItem(date, b);
                        if(date != null){
                            setText(date.getDay()+ "/"+ date.getMonth()+"/" + date.getYear());
                        }
                        else{
                            setText("");
                        }
                    }
                };
            }
        });
        colGender.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        colGender.setCellFactory(new Callback<TableColumn<NhanVien, Boolean>, TableCell<NhanVien, Boolean>>() {
            @Override
            public TableCell<NhanVien, Boolean> call(TableColumn<NhanVien, Boolean> nhanVienBooleanTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Boolean aBoolean, boolean b) {
                        super.updateItem(aBoolean, b);
                        if(aBoolean != null) {
                            if(aBoolean)
                                setText("Nam");
                            else
                                setText("Nữ");
                        }
                        else
                            setText(null);
                    }
                };
            }
        });
        colCccd.setCellValueFactory(new PropertyValueFactory<>("cccd"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("diaChi"));

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

        colDelete.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<NhanVien>());
        colDelete.setCellFactory(new Callback<TableColumn<NhanVien, NhanVien>, TableCell<NhanVien, NhanVien>>() {
            @Override
            public TableCell<NhanVien, NhanVien> call(TableColumn<NhanVien, NhanVien> employeeTableColumn) {
                return new TableCell<NhanVien, NhanVien>() {
                    @Override
                    protected void updateItem(NhanVien nhanVien, boolean b) {
                        super.updateItem(nhanVien, b);
                        if(nhanVien != null) {
                            SVGPath deleteIcon = new SVGPath();
                            deleteIcon.setContent(Icons.TRASH);

                            Region btnDelete = new Region();
                            btnDelete.setMaxWidth(15);
                            btnDelete.setMaxHeight(20);
                            btnDelete.setPrefWidth(15);
                            btnDelete.setPrefHeight(20);
                            btnDelete.setShape(deleteIcon);
                            btnDelete.setStyle("-fx-background-color: #FF8000;");

                            btnDelete.setOnMouseClicked(mouseEvent -> {
                                try {
                                    deleteEmployee(nhanVien);
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                            setGraphic(btnDelete);
                            setText(null);
                        }
                        else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

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
                                    FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-nhan-vien.fxml"));
                                    fxmlLoader.setController(new FormNhanVienController(dsNhanVien, nhanVien, FormNhanVienController.UPDATE));
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

        tableView.setRowFactory(tableView -> {
            TableRow<NhanVien> row = new TableRow<>();

            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    NhanVien nhanVien = row.getItem();

                    try {
                        FXMLLoader fxmlLoader1 = new FXMLLoader(MotoSoftApp.class.getResource("views/form-nhan-vien.fxml"));
                        fxmlLoader1.setController(new FormNhanVienController(dsNhanVien, nhanVien, FormNhanVienController.VIEW));
                        Scene scene = new Scene(fxmlLoader1.load());
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("");
                        stage.show();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            return row;
        });
    }

    private void addEvents(){
        btnThemMoi.setOnMousePressed(mouseEvent ->  {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-nhan-vien.fxml"));
                fxmlLoader.setController(new FormNhanVienController(dsNhanVien, null, FormNhanVienController.ADD));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("");
                stage.show();
            } catch (Exception e){
                e.printStackTrace();
            }
        });


        txtTimKiemHoTen.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(nhanVien -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (nhanVien.getHoTen().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        txtTimKiemMaNV.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(nhanVien -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (nhanVien.getMaNhanVien().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        txtTimKiemSdt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(nhanVien -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (nhanVien.getSdt().toLowerCase().indexOf(lowerCaseFilter) != -1) {
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
    private void addControls(){
        txtTimKiemHoTen.setPromptText("Tìm kiếm theo tên nhân viên");
        txtTimKiemMaNV.setPromptText("Tìm kiếm theo mã nhân viên");
        txtTimKiemSdt.setPromptText("Tìm kiếm theo số điện thoai");
    }


    private void deleteEmployee(NhanVien nhanVien) throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete nhanVien");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa nhân viên này không?");

        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            if (nhanVien.getTaiKhoan() != null)
                taiKhoanService.removeTaiKhoan(nhanVien.getTaiKhoan());
            if (nhanVienService.deleteNhanVien(nhanVien) && dsNhanVien.remove(nhanVien)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Xóa nhân viên thành công");
                tableView.getSelectionModel().clearSelection();
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error: Xóa nhân viên không thành công");
            }
            alert.show();
        }
    }

    private void loadData() throws RemoteException {
        dsNhanVien.setAll(nhanVienService.getAllNhanVien());
    }




}
