package com.example.fashionshop.cus_comp;

import com.example.fashionshop.MotoSoftApp;
//import com.example.fashionshop.model.ChiTietDonHang;
//import com.example.fashionshop.model.SanPham;
import com.example.fashionshop.utils.StringUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import com.example.model.ChiTietDonHang;
import com.example.model.SanPham;

public class ProductItem extends HBox {

    @FXML private Label lblTenSanPham;
    @FXML private Label lblMaSanPham;
    @FXML private Label lblThuongHieu;
    @FXML private Label lblGiaNhap;
    @FXML private Label lblGiaBan;
    @FXML private Label lblSoLuong;
    @FXML private Label lblSize;
    @FXML private VBox boxColor;
    @FXML private SVGPath btnEdit;
    @FXML private SVGPath btnDelete;
    @FXML private ImageView imageView;
    @FXML private Label lblColor;
    @FXML private HBox boxAction;
    @FXML private HBox boxGiaNhap;
    @FXML private HBox boxGiaBan;


    private SanPham product;
    private ChiTietDonHang orderDetail;


    public ProductItem(SanPham product) {
        this.product = product;

        init();
        addControls();
        addEvents();
        loadData();
    }

    private void init() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("cus_comp_views/product-item.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadData() {
        lblTenSanPham.setText(product.getTenSanPham());
        lblMaSanPham.setText(product.getMaSanPham());
        lblGiaNhap.setText(StringUtils.formatCurrency(product.getGiaNhap()));
        lblGiaBan.setText(StringUtils.formatCurrency(product.getGiaBan()));
        lblSoLuong.setText(Integer.toString(product.getSoLuong()));
        lblThuongHieu.setText(product.getThuongHieu().getTenThuongHieu());
        boxColor.setStyle("-fx-background-color: " + product.getMauSac().getCode() + "; -fx-background-radius: 3; -fx-border-radius: 2; -fx-border-color:  #2EC3E9; -fx-border-width: 2;");
        lblColor.setText(product.getMauSac().getTenMau());
        lblSize.setText(product.getKichThuoc().toString());

        Image imageNoAvailable = new Image(MotoSoftApp.class.getResource("drawable/no-image_available.png").toString(), true);
        imageView.setImage(imageNoAvailable);

        if(product.getHinhAnhs() != null && product.getHinhAnhs().size() > 0) {
            Image productImage = new Image(product.getHinhAnhs().stream().findFirst().get(), true);
            productImage.progressProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue.doubleValue() == 1) {
                    imageView.setImage(productImage);
                }
            });
            productImage.errorProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue) {
                    productImage.cancel();
                    imageView.setImage(imageNoAvailable);
                }
            });
        }
    }

    private void addControls() {

    }

    private void addEvents() {
        this.setOnMouseEntered(mouseEvent -> {
            this.setStyle("-fx-background-color: #EDEDED; -fx-border-color:  #EDEDED; -fx-border-radius: 10; -fx-background-radius: 10");
        });

        this.setOnMouseExited(mouseEvent -> {
            this.setStyle("-fx-background-color: #FFFFFF; -fx-border-color:  #EDEDED; -fx-border-radius: 10; -fx-background-radius: 10");
        });
    }

    public SVGPath getBtnDelete() {
        return btnDelete;
    }

    public SVGPath getBtnEdit() {
        return btnEdit;
    }

    public void setVisibleThongBao(){
        boxGiaNhap.setVisible(false);
        boxGiaBan.setVisible(false);
    }
}
