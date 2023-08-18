package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.ReadOnlyObjectWrapperTableColume;
//import com.example.fashionshop.model.KhachHang;
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
import com.example.model.KhachHang;
import com.example.service.KhachHangService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Optional;

public class PageKhachHangController extends VBox {

    @FXML private TableView<KhachHang> tableView;
    @FXML private Button btnThemMoi;
    @FXML private TextField txtTimKiem;

    private ObservableList<String> sortOption = FXCollections.observableArrayList("A-Z", "Z-A");
    private ObservableList<KhachHang> listKhachHang = FXCollections.observableArrayList();
    private FilteredList<KhachHang> filteredList = new FilteredList<>(listKhachHang, b -> true);

    TableColumn<KhachHang, KhachHang> colSelect;
    TableColumn<KhachHang, String> colID;
    TableColumn<KhachHang, String> colName;
    TableColumn<KhachHang, String> colPhone;
    TableColumn<KhachHang, String> colEmail;
    TableColumn<KhachHang, String> colAddress;
    TableColumn<KhachHang, Void> colAction;
    TableColumn<KhachHang, KhachHang> colEdit;
    TableColumn<KhachHang, KhachHang> colDelete;
    private Context context;
    private KhachHangService khachHangService;


    public PageKhachHangController() throws RemoteException {
        try {
            context = new InitialContext();
            khachHangService = (KhachHangService) context.lookup(StringValues.SERVER_URL + "KhachHangService");

            init();
            addControls();
            addEvents();
            loadData();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("pages/page-khach-hang.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void addControls() {
//      config table
        tableView.setEditable(false);
        tableView.getColumns().addAll(
                colSelect = new TableColumn<>("Select"),
                colID = new TableColumn<>("Mã khách hàng"),
                colName = new TableColumn<>("Tên khách hàng"),
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
        colPhone.setStyle("-fx-alignment: center;");
        colEmail.setStyle("-fx-alignment: center-left;");
        colAddress.setStyle("-fx-alignment: center-left;");
        colEdit.setStyle("-fx-alignment: center;");
        colDelete.setStyle("-fx-alignment: center;");

        colSelect.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colSelect.setCellFactory(new Callback<>() {
            @Override
            public TableCell<KhachHang, KhachHang> call(TableColumn<KhachHang, KhachHang> customerCustomerTableColumn) {
                return new TableCell<KhachHang, KhachHang>() {
                    @Override
                    protected void updateItem(KhachHang khachHang, boolean b) {
                        super.updateItem(khachHang, b);
                        if (khachHang != null) {
                            CheckBox checkBox = new CheckBox();
                            setGraphic(checkBox);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        colID.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));

        colName.setCellValueFactory(new PropertyValueFactory<>("hoTen"));

        colPhone.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("diaChi"));

        colEdit.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colEdit.setCellFactory(new Callback<>() {
            @Override
            public TableCell<KhachHang, KhachHang> call(TableColumn<KhachHang, KhachHang> customerCustomerTableColumn) {
                return new TableCell<KhachHang, KhachHang>() {
                    @Override
                    protected void updateItem(KhachHang khachHang, boolean b) {
                        super.updateItem(khachHang, b);
                        if (khachHang != null) {
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
                                Scene scene = new Scene(new FormKhachHangController(listKhachHang, khachHang, FormKhachHangController.UPDATE));
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.setTitle("Cập nhật thông tin khách hàng");
                                stage.show();
                            });

                            setGraphic(btnEdit);
                            setText(null);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        colDelete.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colDelete.setCellFactory(new Callback<>() {
            @Override
            public TableCell<KhachHang, KhachHang> call(TableColumn<KhachHang, KhachHang> customerCustomerTableColumn) {
                return new TableCell<KhachHang, KhachHang>() {
                    @Override
                    protected void updateItem(KhachHang khachHang, boolean b) {
                        super.updateItem(khachHang, b);
                        if (khachHang != null) {
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
                                    deleteCustomer(khachHang);
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                            setGraphic(btnDelete);
                            setText(null);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        tableView.setRowFactory(tableView -> {
            TableRow<KhachHang> row = new TableRow<>();

            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    KhachHang khachHang = row.getItem();

                    Scene scene = new Scene(new FormKhachHangController(listKhachHang, khachHang, FormKhachHangController.VIEW));
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Khách hàng");
                    stage.show();
                }
            });

            return row;
        });
    }

    private void addEvents() {
        btnThemMoi.setOnMousePressed(mouseEvent ->  {
            Scene scene = new Scene(new FormKhachHangController(listKhachHang, null, FormKhachHangController.ADD));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Thêm khách hàng mới");
            stage.show();
        });

        txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
            findKhachHang(newValue);
        });
        SortedList<KhachHang> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }

    private void loadData() throws RemoteException {
        listKhachHang.setAll(khachHangService.getAllCustomer());
    }

    private void deleteCustomer(KhachHang khachHang) throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete customer");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa khách hàng này không?");

        Optional<ButtonType> optional = alert.showAndWait();

        if(optional.get() == ButtonType.OK) {
            if(khachHangService.removeCustomer(khachHang) && listKhachHang.remove(khachHang)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Xóa khách hàng thành công");
                tableView.getSelectionModel().clearSelection();
            }
            else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error: Xóa khách hàng không thành công");
            }
            alert.show();
        }
    }

    private void findKhachHang(String s) {
        filteredList.setPredicate(customer -> {
            if (s == null || s.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = s.toLowerCase();

            if (customer.getHoTen().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (customer.getSdt().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(customer.getMaKhachHang()).contains(lowerCaseFilter)) {
                return true;
            } else if (customer.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }
        });
    }
}
