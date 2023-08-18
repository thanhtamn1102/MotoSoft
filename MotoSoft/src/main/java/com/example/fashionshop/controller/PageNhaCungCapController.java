package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.ReadOnlyObjectWrapperTableColume;
//import com.example.fashionshop.model.NhaCungCap;
import com.example.fashionshop.values.Icons;
import com.example.fashionshop.values.StringValues;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import com.example.model.NhaCungCap;
import com.example.service.NhaCungCapService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Optional;

public class PageNhaCungCapController extends VBox {

    @FXML private TableView<NhaCungCap> tableView;
    @FXML private TextField txtTimKiem;
    @FXML private Button btnThemMoi;

    private TableColumn<NhaCungCap, NhaCungCap> colSelect;
    private TableColumn<NhaCungCap, String> colTenNhaCungCap;
    private TableColumn<NhaCungCap, String> colSDT;
    private TableColumn<NhaCungCap, String> colEmail;
    private TableColumn<NhaCungCap, String> colDiaChi;
    private TableColumn<NhaCungCap, NhaCungCap> colAction;
    private TableColumn<NhaCungCap, NhaCungCap> colEdit;
    private TableColumn<NhaCungCap, NhaCungCap> colDelete;

    private FilteredList<NhaCungCap> filteredData;
    private ObservableList<NhaCungCap> dsNhaCungCap;
    private Context context;
    private NhaCungCapService nhaCungCapService;

    public PageNhaCungCapController() throws RemoteException {
        try {
            context = new InitialContext();
            nhaCungCapService = (NhaCungCapService) context.lookup(StringValues.SERVER_URL + "NhaCungCapService");

            init();
            addControls();
            loadData();
            addEvents();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("pages/page-nha-cung-cap.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void addControls() {

        colSelect = new TableColumn<>();
        colSelect.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colSelect.setCellFactory(new Callback<TableColumn<NhaCungCap, NhaCungCap>, TableCell<NhaCungCap, NhaCungCap>>() {
            @Override
            public TableCell<NhaCungCap, NhaCungCap> call(TableColumn<NhaCungCap, NhaCungCap> categoryCategoryTableColumn) {
                return new TableCell<NhaCungCap, NhaCungCap>() {
                    @Override
                    protected void updateItem(NhaCungCap nhaCungCap, boolean b) {
                        super.updateItem(nhaCungCap, b);
                        if(nhaCungCap != null) {
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

        colTenNhaCungCap = new TableColumn<>("Tên nhà cung cấp");
        colTenNhaCungCap.setCellValueFactory(new PropertyValueFactory<>("tenNhaCungCap"));
        colSDT = new TableColumn<>("Số điện thoại");
        colSDT.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDiaChi = new TableColumn<>("Địa chỉ");
        colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));

        colEdit = new TableColumn<>("Sửa");
        colEdit.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<NhaCungCap>());
        colEdit.setCellFactory(new Callback<TableColumn<NhaCungCap, NhaCungCap>, TableCell<NhaCungCap, NhaCungCap>>() {
            @Override
            public TableCell<NhaCungCap, NhaCungCap> call(TableColumn<NhaCungCap, NhaCungCap> nhaCungCapNhaCungCapTableColumn) {
                return new TableCell<NhaCungCap, NhaCungCap>() {
                    @Override
                    protected void updateItem(NhaCungCap nhaCungCap, boolean b) {
                        super.updateItem(nhaCungCap, b);
                        if(nhaCungCap != null) {
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
                                openFormNhaCungCap(nhaCungCap, FormNhaCungCapController.UPDATE);
                            });

                            setGraphic(btnEdit);
                        }
                        else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        colDelete = new TableColumn<>("Xóa");
        colDelete.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<NhaCungCap>());
        colDelete.setCellFactory(new Callback<TableColumn<NhaCungCap, NhaCungCap>, TableCell<NhaCungCap, NhaCungCap>>() {
            @Override
            public TableCell<NhaCungCap, NhaCungCap> call(TableColumn<NhaCungCap, NhaCungCap> nhaCungCapNhaCungCapTableColumn) {
                return new TableCell<NhaCungCap, NhaCungCap>() {
                    @Override
                    protected void updateItem(NhaCungCap nhaCungCap, boolean b) {
                        super.updateItem(nhaCungCap, b);
                        if(nhaCungCap != null) {
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
                                    deleteNhaCungCap(nhaCungCap);
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                            setGraphic(btnDelete);
                        }
                        else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        colAction = new TableColumn<>("Thao tác");
        colAction.getColumns().addAll(colEdit, colDelete);

        tableView.getColumns().addAll(colSelect, colTenNhaCungCap, colSDT, colEmail, colDiaChi, colAction);

        colSelect.setMaxWidth(1800);
        colEdit.setMaxWidth(1800);
        colDelete.setMaxWidth(1800);

        colSelect.setStyle("-fx-alignment: center;");
        colTenNhaCungCap.setStyle("-fx-alignment: center-left;");
        colSDT.setStyle("-fx-alignment: center;");
        colEdit.setStyle("-fx-alignment: center;");
        colDelete.setStyle("-fx-alignment: center;");
    }

    private void addEvents() {
        btnThemMoi.setOnMousePressed(mouseEvent -> {
            openFormNhaCungCap(null, FormNhaCungCapController.ADD);
        });

        filteredData = new FilteredList<>(dsNhaCungCap, p -> true);
        txtTimKiem.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                findNhaCungCap(newValue);
            }
        });
        SortedList<NhaCungCap> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);

        tableView.setRowFactory(tableView -> {
            TableRow<NhaCungCap> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    NhaCungCap nhaCungCap = row.getItem();
                    openFormNhaCungCap(nhaCungCap, FormNhaCungCapController.VIEW);
                }
            });
            return row;
        });
    }

    private void loadData() throws RemoteException {
        dsNhaCungCap = FXCollections.observableArrayList();
        dsNhaCungCap.setAll(nhaCungCapService.getAllNhaCungCap());
    }

    private void findNhaCungCap(String tenNhaCung) {
        filteredData.setPredicate(nhaCungCap -> {
            if(tenNhaCung == null || tenNhaCung.isEmpty()) {
                return true;
            }

            if(nhaCungCap.getTenNhaCungCap().toLowerCase().contains(tenNhaCung.toLowerCase()))
                return true;
            return false;
        });
    }

    private void deleteNhaCungCap(NhaCungCap nhaCungCap) throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xóa danh nhà cung cấp");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa nhà cung cấp này?");

        Optional<ButtonType> option = alert.showAndWait();
        if(option.get() == ButtonType.OK) {
            if(nhaCungCapService.deleteNhaCungCap(nhaCungCap) && dsNhaCungCap.remove(nhaCungCap)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Xóa nhà cung cấp thành công");
                tableView.getSelectionModel().clearSelection();
            }
            else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error: Xóa nhà cung cấp không thành công!");
            }
            alert.show();
        }
    }

    private void openFormNhaCungCap(NhaCungCap nhaCungCap, int type) {
        Scene scene = new Scene(new FormNhaCungCapController(dsNhaCungCap, nhaCungCap, type));
        scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toString());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Nhà cung cấp");
        stage.show();
    }
}
