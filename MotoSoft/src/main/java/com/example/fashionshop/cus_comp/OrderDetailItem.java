package com.example.fashionshop.cus_comp;

import com.example.fashionshop.MotoSoftApp;
//import com.example.fashionshop.model.ChiTietDonHang;
import com.example.fashionshop.model.ChiTietDonHangProperty;
//import com.example.fashionshop.model.SanPham;
import com.example.fashionshop.utils.StringUtils;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import com.example.model.SanPham;

public class OrderDetailItem extends HBox {

    @FXML private Label lblTenSanPham;
    @FXML private Label lblMaSanPham;
    @FXML private Label lblThuongHieu;
    @FXML private Label lblDonGia;
    @FXML private Label lblSize;
    @FXML private VBox boxColor;
    @FXML private SVGPath btnDelete;
    @FXML private ImageView imageView;
    @FXML private Label lblColor;
    @FXML private HBox boxAction;
    @FXML private VBox btnPlus;
    @FXML private VBox btnMinus;
    @FXML private TextField txtValue;
    @FXML private Label lblThanhTien;
    @FXML private VBox boxSoLuong;

    private ContextMenu contextMenuSoLuongError;
    private MenuItem menuItemSoLuongError;

    private IntegerProperty value;

    private ChiTietDonHangProperty chiTietDonHang;
    private SanPham sanPham;


    public OrderDetailItem(ChiTietDonHangProperty chiTietDonHang) {
        this.chiTietDonHang = chiTietDonHang;
        sanPham = chiTietDonHang.getSanPham();

        init();
        addControls();
        addEvents();
        loadData();
    }

    private void init() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("cus_comp_views/order-detail-item.fxml"));
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
        lblDonGia.setText(StringUtils.formatCurrency(sanPham.getGiaBan()));
        lblThuongHieu.setText(sanPham.getThuongHieu().getTenThuongHieu());
        boxColor.setStyle("-fx-background-color: " + sanPham.getMauSac().getCode() + "; -fx-background-radius: 3; -fx-border-radius: 2; -fx-border-color:  #2EC3E9; -fx-border-width: 2;");
        lblColor.setText(sanPham.getMauSac().getTenMau());
        lblSize.setText(sanPham.getKichThuoc().toString());
        value.set(chiTietDonHang.getSoLuong());
        lblThanhTien.setText(StringUtils.formatCurrency(chiTietDonHang.getThanhTien()));

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

    private void addControls() {
        value = new SimpleIntegerProperty(0);
        txtValue.setText(String.valueOf(value.get()));

        menuItemSoLuongError = new MenuItem();
        menuItemSoLuongError.setDisable(true);
        menuItemSoLuongError.getStyleClass().clear();
        menuItemSoLuongError.setStyle("-fx-text-fill: red");

        contextMenuSoLuongError = new ContextMenu();
        contextMenuSoLuongError.getItems().add(menuItemSoLuongError);
    }

    private void addEvents() {
        addCounterEvent();

        this.setOnMouseEntered(mouseEvent -> {
            this.setStyle("-fx-background-color: #EDEDED; -fx-border-color:  #EDEDED; -fx-border-radius: 10; -fx-background-radius: 10");
        });

        this.setOnMouseExited(mouseEvent -> {
            this.setStyle("-fx-background-color: #FFFFFF; -fx-border-color:  #EDEDED; -fx-border-radius: 10; -fx-background-radius: 10");
        });

        chiTietDonHang.soLuongProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                value.set(newValue.intValue());
            }
        });
    }

    private void addCounterEvent() {
        btnPlus.setOnMousePressed(mouseEvent -> {
            if(value.get() < sanPham.getSoLuong()) {
                value.set(value.get() + 1);
            } else {
                menuItemSoLuongError.setText("Đã đạt số lượng tối đa");
                contextMenuSoLuongError.show(txtValue, Side.BOTTOM, 10, 5);
            }
        });

        btnMinus.setOnMousePressed(mouseEvent -> {
            if(value.get() > 1)
                value.set(value.get() - 1);
            else {
                menuItemSoLuongError.setText("Đã đạt số lượng tối thiểu");
                contextMenuSoLuongError.show(txtValue, Side.BOTTOM, 0, 5);
            }
        });

        value.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                txtValue.setText(newValue.toString());
                chiTietDonHang.setSoLuong(value.get());
                lblThanhTien.setText(StringUtils.formatCurrency(chiTietDonHang.getThanhTien()));
            }
        });

        txtValue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                contextMenuSoLuongError.hide();
                if(!newValue.trim().isEmpty()) {
                    if (!newValue.matches("\\d*")) {
                        txtValue.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            }
        });

        txtValue.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    if(!txtValue.getText().trim().isEmpty()) {
                        int soLuong = Integer.parseInt(txtValue.getText());

                        if(soLuong > sanPham.getSoLuong()) {
                            value.set(sanPham.getSoLuong());
                            txtValue.setText(Integer.toString(value.get()));
                        } else if (soLuong <= 0) {
                            value.set(1);
                            txtValue.setText("1");
                        } else {
                            value.set(soLuong);
                        }
                    } else {
                        value.set(0);
                        txtValue.setText("0");
                    }
                }
            }
        });
    }

    public Label getLblTenSanPham() {
        return lblTenSanPham;
    }

    public SVGPath getBtnDelete() {
        return btnDelete;
    }
}
