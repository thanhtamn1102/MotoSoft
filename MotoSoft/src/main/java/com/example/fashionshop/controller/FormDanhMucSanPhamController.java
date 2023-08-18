package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.ProductItemCategoryView;
import com.example.fashionshop.values.StringValues;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.model.DanhMuc;
import com.example.model.SanPham;
import com.example.service.DanhMucService;
import com.example.service.IDService;
import com.example.service.SanPhamService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

public class FormDanhMucSanPhamController extends BorderPane {

    public static final int VIEW = 0;
    public static final int ADD = 1;
    public static final int UPDATE = 2;

    @FXML private TextField txtTenDanhMuc;
    @FXML private Label lblTenDanhMucError;
    @FXML private CheckBox cbTrangThai;
    @FXML private Button btnLuu;
    @FXML private Button btnThoat;
    @FXML private Button btnCapNhat;
    @FXML private Button btnXoa;
    @FXML private ComboBox<String> cbxNumberSanPhamView;
    @FXML private ListView<SanPham> lvSanPham;
    @FXML private Label lblTitle;
    @FXML private VBox boxSanPham;

    private int type;
    private SanPhamService sanPhamService;
    private IDService idService;
    private static Context context ;
    private ObservableList<DanhMuc> categories;
    private ObservableList<SanPham> dsSanPhamTrongDanhMuc;
    private ObservableList<SanPham> dsSanPhamHienThi;
    private DanhMuc danhMuc;
    private DanhMucService danhMucService;

    public FormDanhMucSanPhamController(ObservableList<DanhMuc> categories, DanhMuc danhMuc, int type) {
        this.categories = categories;
        this.dsSanPhamTrongDanhMuc = FXCollections.observableArrayList();
        this.dsSanPhamHienThi = FXCollections.observableArrayList();
        this.danhMuc = danhMuc;

        try {
            context = new InitialContext();
            idService = (IDService) context.lookup(StringValues.SERVER_URL + "IDService");
            sanPhamService = (SanPhamService) context.lookup(StringValues.SERVER_URL + "SanPhamService");
            danhMucService = (DanhMucService) context.lookup(StringValues.SERVER_URL + "DanhMucService");

            init();
            addControls();
            addEvents();
            loadData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.type = type;
        if(type == VIEW) {
            lblTitle.setText("Chi tiết Danh mục sản phẩm");
            btnLuu.setVisible(false);
            setInputControlEnable(false);
        }
        else if(type == ADD) {
            lblTitle.setText("Thêm Danh mục sản phẩm");
            boxSanPham.setVisible(false);
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);
        }
        else if (type == UPDATE) {
            lblTitle.setText("Cập nhật Danh mục sản phẩm");
            boxSanPham.setVisible(false);
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);
        }
    }

    private void loadData() throws RemoteException {
        if(danhMuc != null) {
            txtTenDanhMuc.setText(danhMuc.getTenDanhMuc());
            cbTrangThai.setSelected(danhMuc.getTrangThai());

            cbxNumberSanPhamView.getItems().addAll("Tất cả", "10", "20", "30", "50", "100");
            cbxNumberSanPhamView.getSelectionModel().select(1);

            List<SanPham> dsSanPhamTrongDanhMuc = sanPhamService.getAllSanPhamFromDanhMuc(danhMuc);
            if(dsSanPhamTrongDanhMuc != null && dsSanPhamTrongDanhMuc.size() > 0) {
                this.dsSanPhamTrongDanhMuc.setAll(dsSanPhamTrongDanhMuc);
                String selectN = cbxNumberSanPhamView.getSelectionModel().getSelectedItem();
                if(selectN.equalsIgnoreCase("Tất cả")) {
                    dsSanPhamHienThi.setAll(dsSanPhamTrongDanhMuc);
                } else {
                    int n = Integer.parseInt(selectN);
                    dsSanPhamHienThi.setAll(dsSanPhamTrongDanhMuc.subList(0, dsSanPhamTrongDanhMuc.size() >= n ? n : dsSanPhamTrongDanhMuc.size()));
                }
            }
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-danh-muc-san-pham.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void addControls() {
        lvSanPham.setItems(dsSanPhamHienThi);
        lvSanPham.setCellFactory(new Callback<>() {
            @Override
            public ListCell<SanPham> call(ListView<SanPham> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(SanPham product, boolean empty) {
                        super.updateItem(product, empty);
                        if (product != null) {
                            ProductItemCategoryView productItem = new ProductItemCategoryView(product);
                            setGraphic(productItem);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
    }

    private void addEvents() {
        btnThoat.setOnMousePressed(mouseEvent -> {
            closeStage(mouseEvent);
        });

        btnLuu.setOnMousePressed(mouseEvent -> {
            String tenDanhMuc = txtTenDanhMuc.getText();
            boolean trangThai = cbTrangThai.isSelected();
            try {
                if(!tenDanhMuc.isEmpty()) {
                    DanhMuc danhMuc = new DanhMuc(idService.createMaDanhMuc(), tenDanhMuc, trangThai);
                    if(type == ADD) {
                        addCategory(danhMuc, mouseEvent);
                    }
                    else if(type == UPDATE) {
                        danhMuc.setMaDanhMuc(this.danhMuc.getMaDanhMuc());
                        updateCategory(danhMuc, mouseEvent);
                    }
                }
                else {
                    lblTenDanhMucError.setText("Tên danh mục không được để trống");
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
        });

        btnXoa.setOnMousePressed(mouseEvent -> {
            deleteCategory(danhMuc);
            closeStage(mouseEvent);
        });

        btnCapNhat.setOnMousePressed(mouseEvent -> {
            if(btnCapNhat.getText().equalsIgnoreCase("CẬP NHẬT")) {
                lblTitle.setText("Cập nhật Danh mục sản phẩm");
                boxSanPham.setVisible(false);
                btnCapNhat.setText("HỦY CẬP NHẬT"); btnCapNhat.setStyle("-fx-background-color:grey");
                btnLuu.setVisible(true);
                setInputControlEnable(true);
                type = UPDATE;
            }
            else {
                lblTitle.setText("Chi tiết Danh mục sản phẩm");
                boxSanPham.setVisible(true);
                btnCapNhat.setText("CẬP NHẬT"); btnCapNhat.setStyle("-fx-background-color:#0C75F5");
                btnLuu.setVisible(false);
                setInputControlEnable(false);
                type = VIEW;
                try {
                    loadData();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        txtTenDanhMuc.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblTenDanhMucError.setText("");
            }
        });

        cbxNumberSanPhamView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                if(newValue.equalsIgnoreCase("Tất cả")) {
                    dsSanPhamHienThi.setAll(dsSanPhamTrongDanhMuc);
                } else {
                    int n = Integer.parseInt(newValue);
                    dsSanPhamHienThi.setAll(dsSanPhamTrongDanhMuc.subList(0, dsSanPhamTrongDanhMuc.size() >= n ? n : dsSanPhamTrongDanhMuc.size()));
                }
            }
        });
    }

    private void setInputControlEnable(boolean b) {
        txtTenDanhMuc.setEditable(b);
        cbTrangThai.setDisable(!b);
    }

    private void closeStage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void addCategory(DanhMuc danhMuc, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Category");
        alert.setHeaderText(null);
        try {
            if(danhMucService.addCategory(danhMuc) && categories.add(danhMuc)) {
                alert.setContentText("Thêm danh mục sản phẩm thành công");
                alert.show();
                closeStage(mouseEvent);
            }
            else {
                alert.setContentText("Error: Thêm danh mục sản phẩm không thành công!");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void deleteCategory(DanhMuc danhMuc) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xóa danh mục sản phẩm");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa danh mục sản phẩm này?");

        Optional<ButtonType> option = alert.showAndWait();
        try {
            if(option.get() == ButtonType.OK) {

                if(danhMucService.removeCategory(danhMuc) && categories.remove(danhMuc)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Xóa danh mục sản phẩm thành công!");
                }
                else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Error: Xóa danh mục sản phẩm không thành công!");
                }
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void updateCategory(DanhMuc danhMuc, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cập nhật danh mục sản phẩm");
        alert.setHeaderText(null);
        try {
            if(danhMucService.updateCategory(danhMuc)) {
                categories.set(categories.indexOf(danhMuc), danhMuc);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Cập nhật thông tin danh mục sản phẩm thành công!");
                alert.show();
                closeStage(mouseEvent);
            }
            else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error: Cập nhật thông tin danh mục sản phẩm không thành công");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
