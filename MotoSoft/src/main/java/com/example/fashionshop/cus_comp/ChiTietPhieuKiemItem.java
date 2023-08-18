package com.example.fashionshop.cus_comp;

import com.example.fashionshop.MotoSoftApp;
//import com.example.fashionshop.model.ChiTietDonHang;
//import com.example.fashionshop.model.ChiTietPhieuKiem;
//import com.example.fashionshop.model.SanPham;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import com.example.model.ChiTietPhieuKiem;
import com.example.model.SanPham;

public class ChiTietPhieuKiemItem extends HBox {

    @FXML private Label lblTenSanPham;
    @FXML private Label lblMaSanPham;
    @FXML private Label lblThuongHieu;
    @FXML private Label lblSize;
    @FXML private VBox boxColor;
    @FXML private SVGPath btnDelete;
    @FXML private ImageView imageView;
    @FXML private Label lblColor;
    @FXML private HBox boxAction;
    @FXML private Label lblSLTruocKiem;
    @FXML private TextField txtSLThucTe;
    @FXML private Label lblSLChenhLech;
    @FXML private TextField txtGhiChu;
    @FXML private Label lblSLHeThong;


    private SanPham product;
    private ChiTietPhieuKiem chiTietPhieuKiem;
    private IntegerProperty slThucTe;

    private boolean enable = true;

    public ChiTietPhieuKiemItem(ChiTietPhieuKiem chiTietPhieuKiem) {
        this.chiTietPhieuKiem = chiTietPhieuKiem;
        product = chiTietPhieuKiem.getSanPham();
        slThucTe = new SimpleIntegerProperty(chiTietPhieuKiem.getSoLuongThucTe());

        init();
        addControls();
        addEvents();
        loadData();
    }

    private void init() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("cus_comp_views/chi-tiet-phieu-kiem-item.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadData() {
        if(product != null) {
            lblTenSanPham.setText(product.getTenSanPham());
            lblMaSanPham.setText(product.getMaSanPham());
            lblThuongHieu.setText(product.getThuongHieu().getTenThuongHieu());
            boxColor.setStyle("-fx-background-color: " + product.getMauSac().getCode() + "; -fx-background-radius: 3; -fx-border-radius: 2; -fx-border-color:  #2EC3E9; -fx-border-width: 2;");
            lblColor.setText(product.getMauSac().getTenMau());
            lblSize.setText(product.getKichThuoc().toString());
            lblSLTruocKiem.setText(Integer.toString(chiTietPhieuKiem.getSoLuongHeThong()));
            txtSLThucTe.setText(Integer.toString(chiTietPhieuKiem.getSoLuongThucTe()));
            lblSLChenhLech.setText(Integer.toString(Math.abs(chiTietPhieuKiem.getSoLuongHeThong() - chiTietPhieuKiem.getSoLuongThucTe())));
            txtGhiChu.setText(chiTietPhieuKiem.getGhiChu());
            lblSLHeThong.setText(Integer.toString(chiTietPhieuKiem.getSoLuongHeThong()));

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
                    }
                });
            }
        }
    }

    private void addControls() {
        txtSLThucTe.setText(Integer.toString(chiTietPhieuKiem.getSoLuongHeThong()));
    }

    private void addEvents() {

        btnDelete.setOnMousePressed(mouseEvent -> {

        });

        this.setOnMouseEntered(mouseEvent -> {
            this.setStyle("-fx-background-color: #EDEDED; -fx-border-color:  #EDEDED; -fx-border-radius: 10; -fx-background-radius: 10");
        });

        this.setOnMouseExited(mouseEvent -> {
            this.setStyle("-fx-background-color: #FFFFFF; -fx-border-color:  #EDEDED; -fx-border-radius: 10; -fx-background-radius: 10");
        });

        slThucTe.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                txtSLThucTe.setText(newValue.toString());
                chiTietPhieuKiem.setSoLuongThucTe(slThucTe.get());
                lblSLChenhLech.setText(Integer.toString(Math.abs(chiTietPhieuKiem.getSoLuongHeThong() - slThucTe.get())));
            }
        });

        txtSLThucTe.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.trim().isEmpty()) {
                    if (!newValue.matches("\\d*")) {
                        txtSLThucTe.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            }
        });

        txtSLThucTe.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    if(!txtSLThucTe.getText().trim().isEmpty()) {
                        int soLuong = Integer.parseInt(txtSLThucTe.getText());

                        if (soLuong <= 0) {
                            slThucTe.set(1);
                            txtSLThucTe.setText("1");
                        } else {
                            slThucTe.set(soLuong);
                        }
                    } else {
                        slThucTe.set(0);
                        txtSLThucTe.setText("0");
                    }
                }
            }
        });

        txtGhiChu.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                chiTietPhieuKiem.setGhiChu(txtGhiChu.getText());
            }
        });

    }

    public SVGPath getBtnDelete() {
        return btnDelete;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        txtSLThucTe.setDisable(!enable);
        txtGhiChu.setDisable(!enable);
        btnDelete.setVisible(enable);
    }
}
