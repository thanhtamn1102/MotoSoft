package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.ReadOnlyObjectWrapperTableColume;
//import com.example.fashionshop.model.ChucVu;
import com.example.fashionshop.values.Icons;
import com.example.fashionshop.values.StringValues;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;
import com.example.model.ChucVu;
import com.example.service.ChucVuService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Optional;

;

public class PageChucVuController extends VBox {

    @FXML private VBox vbox;
    @FXML private TableView<ChucVu> tableView;
    @FXML private TableColumn<ChucVu, ChucVu> colSelect;
    @FXML private TableColumn<ChucVu, ChucVu> colMaChucVu;
    @FXML private TableColumn<ChucVu, String> colTenChucVu;
    @FXML private TableColumn<ChucVu, ChucVu> colDelete;
    @FXML private TableColumn<ChucVu, ChucVu> colAction;
    @FXML private TextField txtTimKiem;
    @FXML private Button btnThem;
    private static Context context ;

    private ObservableList<ChucVu> dsChucVu = FXCollections.observableArrayList();
    private FilteredList<ChucVu> filteredList = new FilteredList<>(dsChucVu, b -> true);
    private ChucVuService chucVuService;

    public PageChucVuController() {
        try {
            context = new InitialContext();
            chucVuService = (ChucVuService) context.lookup(StringValues.SERVER_URL + "ChucVuService");

            init();
            setTableView();
            loadData();
            addEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("pages/page-chuc-vu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void loadData() throws RemoteException {
        dsChucVu.setAll(chucVuService.getAllChucVu());
    }

    private void setTableView(){
        tableView.getColumns().addAll(
                colSelect = new TableColumn<>("Select"),
                colMaChucVu = new TableColumn<>("Mã chức vụ"),
                colTenChucVu = new TableColumn<>("Tên Chức vụ"),
                colAction = new TableColumn<>("Hành động")
        );
        colAction.getColumns().addAll(
                colDelete = new TableColumn<>("Xoá")
        );


        colSelect.setStyle("-fx-alignment: center;");
        colMaChucVu.setStyle("-fx-alignment: center;");
        colTenChucVu.setStyle("-fx-alignment: center-left;");
        colDelete.setStyle("-fx-alignment: center;");

        colMaChucVu.setEditable(false);
        colTenChucVu.setEditable(true);
        tableView.setEditable(true);

        colSelect.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colSelect.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ChucVu, ChucVu> call(TableColumn<ChucVu, ChucVu> employeeEmployeeTableColumn) {
                return new TableCell<ChucVu, ChucVu>() {
                    @Override
                    protected void updateItem(ChucVu chucVu, boolean b) {
                        super.updateItem(chucVu, b);
                        if (chucVu != null) {
                            CheckBox checkBox = new CheckBox();
                            setGraphic(checkBox);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
        colMaChucVu.setCellValueFactory(new PropertyValueFactory<>("maChucVu"));
        colTenChucVu.setCellValueFactory(new PropertyValueFactory<>("tenChucVu"));
        colTenChucVu.setCellFactory(TextFieldTableCell.forTableColumn());
        colTenChucVu.setOnEditCommit(e-> {
            ChucVu chucVu = e.getTableView().getItems().get(e.getTablePosition().getRow());
            chucVu.setTenChucVu(e.getNewValue());
            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cập nhật chức vụ");
                alert.setHeaderText(null);

                if(chucVuService.updateChucVu(chucVu)) {
                    alert.setContentText("Cập nhật thông tin chức vụ thành công");
                    alert.show();
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Lỗi: Cập nhật thông tin chức vụ không thành công");
                    alert.show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        colDelete.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<ChucVu>());
        colDelete.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ChucVu, ChucVu> call(TableColumn<ChucVu, ChucVu> employeeTableColumn) {
                return new TableCell<ChucVu, ChucVu>() {
                    @Override
                    protected void updateItem(ChucVu chucVu, boolean b) {
                        super.updateItem(chucVu, b);
                        if (chucVu != null) {
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
                                deleteChucVu(chucVu);
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
    }

    private void addEvent(){
        colTenChucVu.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ChucVu, String> event) {
                TablePosition<ChucVu, String> pos = event.getTablePosition();
                String tenChucVuNew = event.getNewValue();
                int row = pos.getRow();
                ChucVu chucVu = event.getTableView().getItems().get(row);
                chucVu.setTenChucVu(tenChucVuNew);

                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cập nhật thông tin chức vụ");
                    alert.setHeaderText(null);

                    if (chucVuService.updateChucVu(chucVu)) {
                        alert.setContentText("Cập nhật thông tin chức vụ thành công");
                        alert.show();
                    } else {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText("Cập nhật thông tin chức vụ không thành công");
                        alert.show();
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });

//        btnThem.setOnMousePressed(mouseEvent -> {
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(FashionShopApp.class.getResource("views/form-chuc-vu.fxml"));
//                fxmlLoader.setController(new FormChucVuController(dsChucVu, null, FormChucVuController.ADD));
//                Scene scene = new Scene(fxmlLoader.load());
//                Stage stage = new Stage();
//                stage.setScene(scene);
//                stage.setTitle("");
//                stage.show();
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        });

        txtTimKiem.setPromptText("Tìm kiếm mã chức vụ hoặc tên chức vụ");
        txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(chucVu -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (chucVu.getTenChucVu().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(chucVu.getMaChucVu()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<ChucVu> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);;
    }

    private void deleteChucVu(ChucVu chucVu) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete chức vụ");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa chức vụ này không?");

        Optional<ButtonType> optional = alert.showAndWait();
        try {
            if(optional.get() == ButtonType.OK) {
                if(chucVuService.removeChucVu(chucVu) && dsChucVu.remove(chucVu)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Xóa chức vụ thành công");
                    tableView.getSelectionModel().clearSelection();
                }
                else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Error: Xóa chức vụ không thành công");
                }
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

}
