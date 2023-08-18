package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.ProductItem;
//import com.example.fashionshop.model.SanPham;
import com.example.fashionshop.values.StringValues;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.model.SanPham;
import com.example.service.SanPhamService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class FormThongBaoController implements Initializable {

    @FXML private Button btnDong;
    @FXML private ListView<SanPham> listView;

    @FXML private VBox boxDanhMucSanPham;
    @FXML private VBox boxTrangThai;
    @FXML private VBox boxSapXep;
    @FXML private TextField txtTimKiem;


    private ObservableList<SanPham> productList = FXCollections.observableArrayList();
    private ObservableList<SanPham> listSanPhamSapHet = FXCollections.observableArrayList();
    private FilteredList<SanPham> filteredData;
    private Context context;
    private SanPhamService sanPhamService;

    public FormThongBaoController(){
        try {
            context = new InitialContext();
            sanPhamService = (SanPhamService) context.lookup(StringValues.SERVER_URL + "SanPhamService");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
        event();
        try {
            loadData();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void event(){
        btnDong.setOnMousePressed(mouseEvent ->{
            closeStage(mouseEvent);
        });

        filteredData = new FilteredList<SanPham>(listSanPhamSapHet, p -> true);
        txtTimKiem.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                findSanPham(newValue);
            }
        });
        listView.setItems(filteredData);
    }

    private void closeStage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void init(){
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
                                    openFormView(product);
                                }
                            });
//
                            productItem.getBtnDelete().setVisible(false);
                            productItem.getBtnEdit().setVisible(false);
                            productItem.setVisibleThongBao();


                            setGraphic(productItem);
                            setPadding(new Insets(6, 12, 6, 12));
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
        setVisible();

    }
    private void loadData() throws RemoteException {
        productList.setAll(sanPhamService.getAllSanPham());
        for (SanPham sanpham : productList) {
            if (sanpham.getSoLuong() <= 3) {
                listSanPhamSapHet.add(sanpham);
            }
        }
    }

    private void openFormView(SanPham product) {
        Scene scene = new Scene(new FormSanPhamController(productList, product, FormSanPhamController.VIEW));
        scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Sản phẩm");
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

    public void setVisible(){
        boxDanhMucSanPham.setVisible(false);
        boxSapXep.setVisible(false);
        boxTrangThai.setVisible(false);
    }

}
