package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.ProductItem;
import com.example.fashionshop.excel_processing.ProductExcelProcessing;
//import com.example.fashionshop.model.SanPham;
import com.example.fashionshop.values.StringValues;
import com.example.service.IDService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.model.SanPham;
import com.example.service.SanPhamService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

public class PageSanPhamController extends BorderPane {

    @FXML private ListView<SanPham> listView;
    @FXML private Button btnThemMoi;
    @FXML private TextField txtTimKiem;
    @FXML private Button btnNhapXuatExcel;

    private FilteredList<SanPham> filteredData;
    private ObservableList<SanPham> productList = FXCollections.observableArrayList();
    private Context context;
    private SanPhamService sanPhamService;
    private IDService idService;

    public PageSanPhamController() throws RemoteException {
        try {
            context = new InitialContext();
            sanPhamService = (SanPhamService) context.lookup(StringValues.SERVER_URL + "SanPhamService");
            idService = (IDService) context.lookup(StringValues.SERVER_URL + "IDService");

            init();
            addEvents();
            loadData();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("pages/page-san-pham.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        listView.setCellFactory(new Callback<ListView<SanPham>, ListCell<SanPham>>() {
            @Override
            public ListCell<SanPham> call(ListView<SanPham> param) {
                return new ListCell<SanPham>() {
                    @Override
                    protected void updateItem(SanPham product, boolean empty) {
                        super.updateItem(product, empty);
                        if(product != null) {
                            ProductItem productItem = new ProductItem(product);

                            productItem.setOnMouseClicked(mouseEvent -> {
                                if(mouseEvent.getClickCount() == 2) {
                                    Stage context = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                                    openFormView(context, product);
                                }
                            });

                            productItem.getBtnEdit().setOnMouseClicked(mouseEvent -> {
                                Stage context = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                                openFormEdit(context, product);
                            });

                            productItem.getBtnDelete().setOnMouseClicked(mouseEvent -> {
                                deleteSanPham(product);
                            });

                            setGraphic(productItem);
                            setPadding(new Insets(6, 12, 6, 12));
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
    }

    private void addEvents() {
        btnThemMoi.setOnMousePressed(mouseEvent -> {
            Stage context = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            openFormAdd(context);
        });

        filteredData = new FilteredList<SanPham>(productList, p -> true);
        txtTimKiem.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                findSanPham(newValue);
            }
        });
        listView.setItems(filteredData);
    }

    private void loadDataToListView(ObservableList<SanPham> products) {
        listView.getItems().clear();
        listView.setItems(products);
    }

    private void loadData() throws RemoteException {
        productList.setAll(sanPhamService.getAllSanPham());

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuImportExcel = new MenuItem("Nhập file Excel");
        MenuItem menuExportExcel = new MenuItem("Xuất file Excel");
        contextMenu.getItems().addAll(menuImportExcel, menuExportExcel);
        btnNhapXuatExcel.addEventHandler(ActionEvent.ACTION, e -> {
            contextMenu.show(btnNhapXuatExcel, Side.BOTTOM, 10, 5);
        });

        menuExportExcel.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Xuất tập tin excel");
            File file = fileChooser.showSaveDialog((Stage) this.getScene().getWindow());
            if (file != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Xuất tập tin excel");
                alert.setHeaderText(null);

                ProductExcelProcessing excelProcessing = new ProductExcelProcessing();
                if (excelProcessing.generator(file, productList)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Lưu file excel thành công");
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Error: Lưu file excel không thành công");
                }

                alert.show();
            }
        });

        menuImportExcel.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Nhập tập tin excel");
            File file = fileChooser.showOpenDialog((Stage) this.getScene().getWindow());
            if (file != null) {
                ProductExcelProcessing excelProcessing = new ProductExcelProcessing();
                List<SanPham> dsSanPham = excelProcessing.parser(file);
                for(SanPham sanPham : dsSanPham) {
                    try {
                        sanPham.setMaSanPham(idService.createMaSanPham());
                        if(sanPhamService.addSanPham(sanPham))
                            this.productList.add(sanPham);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thêm sản phẩm");
                alert.setHeaderText(null);
                alert.setContentText("Thêm sản phẩm từ file excel thành công");
                alert.show();
            }
        });
    }

    private void deleteSanPham(SanPham product) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete SanPham");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn xóa sản phẩm này không?");

            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.get() == ButtonType.OK) {
                if (sanPhamService.removeSanPham(product) && productList.remove(product)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Xóa sản phẩm thành công");
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Error: Xóa sản phẩm không thành công");
                }
                alert.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openFormView(Stage context, SanPham product) {
        Scene scene = new Scene(new FormSanPhamController(productList, product, FormSanPhamController.VIEW));
        scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(context);
        stage.setTitle("Sản phẩm");
        stage.show();
    }

    private void openFormAdd(Stage context) {
        Scene scene = new Scene(new FormSanPhamController(productList, null, FormSanPhamController.ADD));
        scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toExternalForm());
        Stage stage = new Stage();
        stage.setTitle("Thêm sản phẩm mới");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(context);
        stage.show();
    }

    private void openFormEdit(Stage context, SanPham product) {
        Scene scene = new Scene(new FormSanPhamController(productList, product, FormSanPhamController.UPDATE));
        scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(context);
        stage.setTitle("Cập nhật thông tin sản phẩm");
        stage.show();
    }

    private void findSanPham(String findString) {
        filteredData.setPredicate(product -> {
            if(findString == null || findString.isEmpty()) {
                return true;
            }

            if(product.getTenSanPham().toLowerCase().contains(findString.toLowerCase()))
                return true;
            else if(product.getMaSanPham().toLowerCase().contains(findString.toLowerCase()))
                return true;

            return false;
        });
    }

}
