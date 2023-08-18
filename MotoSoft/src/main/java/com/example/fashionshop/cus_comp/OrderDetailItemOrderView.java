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
import com.example.model.ChiTietDonHang;
import com.example.model.SanPham;

public class OrderDetailItemOrderView extends HBox {

    @FXML
    private Label lblTenSanPham;
    @FXML private Label lblMaSanPham;
    @FXML private Label lblThuongHieu;
    @FXML private Label lblDonGia;
    @FXML private Label lblSoLuong;
    @FXML private Label lblSize;
    @FXML private VBox boxColor;
    @FXML private ImageView imageView;
    @FXML private Label lblColor;

    private SanPham sanPham;
    private ChiTietDonHang chiTietDonHang;

    public OrderDetailItemOrderView(ChiTietDonHang chiTietDonHang) {
        this.chiTietDonHang = chiTietDonHang;
        sanPham = chiTietDonHang.getSanPham();

        init();
        addEvents();
        loadData();
    }

    private void init() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("cus_comp_views/order-detail-item-order-view.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadData() {
        lblTenSanPham.setText(sanPham.getTenSanPham());
        lblMaSanPham.setText(sanPham.getMaSanPham());
        lblDonGia.setText(StringUtils.formatCurrency(chiTietDonHang.getDonGia()));
        lblSoLuong.setText(Integer.toString(chiTietDonHang.getSoLuong()));
        boxColor.setStyle("-fx-background-color: " + sanPham.getMauSac().getCode() + "; -fx-background-radius: 3; -fx-border-radius: 2; -fx-border-color:  #2EC3E9; -fx-border-width: 2;");
        lblColor.setText(sanPham.getMauSac().getTenMau());
        lblSize.setText(sanPham.getKichThuoc().toString());

        Image imageNoAvailable = new Image(MotoSoftApp.class.getResource("drawable/no-image_available.png").toString(), true);
        imageView.setImage(imageNoAvailable);

        if(sanPham.getHinhAnhs() != null && sanPham.getHinhAnhs().size() > 0) {
            Image productImage = new Image(sanPham.getHinhAnhs().stream().findFirst().get(), true);
            productImage.progressProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue.doubleValue() == 1) {
                    imageView.setImage(productImage);
                }
            });
            productImage.errorProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue) {
                    productImage.cancel();
                }
            });
        }
    }

    private void addEvents() {

    }

}
