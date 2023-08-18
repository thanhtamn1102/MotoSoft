package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.ReadOnlyObjectWrapperTableColume;
//import com.example.fashionshop.model.DanhMuc;
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.model.DanhMuc;
import com.example.service.DanhMucService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Optional;

public class PageDanhMucSanPhamController extends VBox {
    @FXML private TableView<DanhMuc> tableView;
    @FXML private Button btnThemMoi;
    @FXML private TextField txtTimKiem;
    @FXML private SVGPath btnTimKiem;

    private TableColumn<DanhMuc, DanhMuc> colCategorySelect;
    private TableColumn<DanhMuc, String> colCategoryId;
    private TableColumn<DanhMuc, String> colCategoryName;
    private TableColumn<DanhMuc, DanhMuc> colCategoryStatus;
    private TableColumn<DanhMuc, Void> colAction;
    private TableColumn<DanhMuc, DanhMuc> colEdit;
    private TableColumn<DanhMuc, DanhMuc> colDelete;
    private FilteredList<DanhMuc> filteredData;

    private ObservableList<DanhMuc> categories;
    private Context context;
    private DanhMucService danhMucService;


    public PageDanhMucSanPhamController() throws RemoteException {
        try {
            context = new InitialContext();
            danhMucService = (DanhMucService) context.lookup(StringValues.SERVER_URL + "DanhMucService");

            init();
            loadData();
            addEvent();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("pages/page-danh-muc-san-pham.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        tableView.getColumns().addAll(
                colCategorySelect = new TableColumn<>("Select"),
                colCategoryId = new TableColumn<>("Mã danh mục"),
                colCategoryName = new TableColumn<>("Tên danh mục"),
                colCategoryStatus = new TableColumn<>("Status"),
                colAction = new TableColumn<>("Hành động")
        );

        colAction.getColumns().addAll(
                colEdit = new TableColumn<DanhMuc, DanhMuc>("Sửa"),
                colDelete = new TableColumn<>("Xóa")
        );

        colCategorySelect.setMaxWidth(1800);
        colEdit.setMaxWidth(1800);
        colDelete.setMaxWidth(1800);

        colCategorySelect.setStyle("-fx-alignment: center;");
        colCategoryId.setStyle("-fx-alignment: center;");
        colCategoryName.setStyle("-fx-alignment: center-left;");
        colCategoryStatus.setStyle("-fx-alignment: center;");
        colEdit.setStyle("-fx-alignment: center;");
        colDelete.setStyle("-fx-alignment: center;");

        colCategorySelect.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colCategorySelect.setCellFactory(new Callback<TableColumn<DanhMuc, DanhMuc>, TableCell<DanhMuc, DanhMuc>>() {
            @Override
            public TableCell<DanhMuc, DanhMuc> call(TableColumn<DanhMuc, DanhMuc> categoryCategoryTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(DanhMuc danhMuc, boolean b) {
                        super.updateItem(danhMuc, b);
                        if(danhMuc != null) {
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

        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("maDanhMuc"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("tenDanhMuc"));
        colCategoryStatus.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colCategoryStatus.setCellFactory(new Callback<TableColumn<DanhMuc, DanhMuc>, TableCell<DanhMuc, DanhMuc>>() {
            @Override
            public TableCell<DanhMuc, DanhMuc> call(TableColumn<DanhMuc, DanhMuc> categoryCategoryTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(DanhMuc danhMuc, boolean b) {
                        super.updateItem(danhMuc, b);
                        if(danhMuc != null) {
                            CheckBox checkBox = new CheckBox();
                            checkBox.setSelected(danhMuc.getTrangThai());
                            checkBox.setDisable(true);
                            setGraphic(checkBox);
                        }
                        else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        colEdit.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<DanhMuc>());
        colEdit.setCellFactory(new Callback<TableColumn<DanhMuc, DanhMuc>, TableCell<DanhMuc, DanhMuc>>() {
            @Override
            public TableCell<DanhMuc, DanhMuc> call(TableColumn<DanhMuc, DanhMuc> CategoryCategoryTableColumn) {
                return new TableCell<DanhMuc, DanhMuc>() {
                    @Override
                    protected void updateItem(DanhMuc DanhMuc, boolean b) {
                        super.updateItem(DanhMuc, b);
                        if(DanhMuc != null) {
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
                                Scene scene = new Scene(new FormDanhMucSanPhamController(categories, DanhMuc, FormDanhMucSanPhamController.UPDATE));
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.setTitle("Edit Category");
                                stage.show();
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
        colDelete.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<DanhMuc>());
        colDelete.setCellFactory(new Callback<TableColumn<DanhMuc, DanhMuc>, TableCell<DanhMuc, DanhMuc>>() {
            @Override
            public TableCell<DanhMuc, DanhMuc> call(TableColumn<DanhMuc, DanhMuc> CategoryCategoryTableColumn) {
                return new TableCell<DanhMuc, DanhMuc>() {
                    @Override
                    protected void updateItem(DanhMuc danhMuc, boolean b) {
                        super.updateItem(danhMuc, b);
                        if(danhMuc != null) {
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
                                    deleteCategory(danhMuc);
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


        tableView.setRowFactory(tableView -> {
            TableRow<DanhMuc> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    DanhMuc danhMuc = row.getItem();
                    Stage context = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                    openFormDanhMucSanPham(context, danhMuc);
                }
            });
            return row;
        });
    }

    private void loadData() throws RemoteException {
        categories = FXCollections.observableArrayList(danhMucService.getAllCategory()) ;
        tableView.setItems(categories);
    }

    private void addEvent() {
        btnThemMoi.setOnMousePressed(mouseEvent -> {
            Scene scene = new Scene(new FormDanhMucSanPhamController(categories, null, FormDanhMucSanPhamController.ADD));
            Stage stage = new Stage();
            stage.setTitle("Thêm danh mục sản phẩm");
            stage.setScene(scene);
            stage.show();
        });

        filteredData = new FilteredList<>(categories, p -> true);
        txtTimKiem.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                findCategoryByName(newValue);
            }
        });
        SortedList<DanhMuc> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    private void findCategoryByName(String categoryName) {
        filteredData.setPredicate(category -> {
            if(categoryName == null || categoryName.isEmpty()) {
                return true;
            }

            if(category.getTenDanhMuc().toLowerCase().contains(categoryName.toLowerCase()))
                return true;
            return false;
        });
    }

    private void deleteCategory(DanhMuc DanhMuc) throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xóa danh mục sản phẩm");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa danh mục sản phẩm này?");

        Optional<ButtonType> option = alert.showAndWait();
        if(option.get() == ButtonType.OK) {
            if(danhMucService.removeCategory(DanhMuc) && categories.remove(DanhMuc)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Xóa danh mục sản phẩm thành công");
                tableView.getSelectionModel().clearSelection();
            }
            else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error: Xóa danh mục sản phẩm không thành công!");
            }
            alert.show();
        }
    }

    private void openFormDanhMucSanPham(Stage context, DanhMuc danhMuc) {
        Scene scene = new Scene(new FormDanhMucSanPhamController(categories, danhMuc, FormDanhMucSanPhamController.VIEW));
        scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toString());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(context);
        stage.setTitle("Danh mục sản phẩm");
        stage.show();
    }

}
