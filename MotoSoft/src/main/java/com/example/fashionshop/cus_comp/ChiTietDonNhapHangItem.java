package com.example.fashionshop.cus_comp;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.model.ChiTietDonNhapHangProperty;
import com.example.fashionshop.utils.StringUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
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

public class ChiTietDonNhapHangItem extends HBox {

    @FXML private Label lblMaSanPham, lblTenSanPham, lblThuongHieu;
    @FXML private Label lblColor, lblSize;
    @FXML private VBox boxColor;
    @FXML private TextField txtGiaNhap, txtSoLuong, txtSoLuongDaNhan;
    @FXML private Label lblThanhTien, lblSoLuongConLai;
    @FXML private SVGPath btnDelete;
    @FXML private ImageView imageView;
    @FXML private VBox btnPlus, btnMinus, btnPlusSoLuongDaNhan, btnMinusSoLuongDaNhan;
    private ContextMenu contextMenuSoLuongError, contextMenuSoLuongDaNhanError;
    private MenuItem menuItemSoLuongError, menuItemSoLuongDaNhanError;

    private ChiTietDonNhapHangProperty chiTietDonNhapHang;
    private SanPham sanPham;
    private IntegerProperty soLuong, soLuongDaNhan;
    private IntegerBinding soLuongConLai;

    public ChiTietDonNhapHangItem(ChiTietDonNhapHangProperty chiTietDonNhapHang) {
        this.chiTietDonNhapHang = chiTietDonNhapHang;
        this.sanPham = chiTietDonNhapHang.getSanPham();
        this.soLuong = new SimpleIntegerProperty(chiTietDonNhapHang.getSoLuong());
        this.soLuongDaNhan = new SimpleIntegerProperty(chiTietDonNhapHang.getSoLuongDaNhan());
        this.soLuongConLai = (IntegerBinding) Bindings.subtract(soLuong, soLuongDaNhan);

        init();
        addControls();
        addEvents();
        loadData();
    }

    private void init() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("cus_comp_views/chi-tiet-don-nhap-hang-item.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addControls() {
        menuItemSoLuongError = new MenuItem();
        menuItemSoLuongError.setDisable(true);
        menuItemSoLuongError.getStyleClass().clear();
        menuItemSoLuongError.setStyle("-fx-text-fill: red");

        contextMenuSoLuongError = new ContextMenu();
        contextMenuSoLuongError.getItems().add(menuItemSoLuongError);

        menuItemSoLuongDaNhanError = new MenuItem();
        menuItemSoLuongDaNhanError.setDisable(true);
        menuItemSoLuongDaNhanError.getStyleClass().clear();
        menuItemSoLuongDaNhanError.setStyle("-fx-text-fill: red");

        contextMenuSoLuongDaNhanError = new ContextMenu();
        contextMenuSoLuongDaNhanError.getItems().add(menuItemSoLuongDaNhanError);
    }

    private void addEvents() {
        addCounterEvent();
    }

    private void addCounterEvent() {
        btnPlus.setOnMousePressed(mouseEvent -> {
            soLuong.set(soLuong.get() + 1);
        });

        btnMinus.setOnMousePressed(mouseEvent -> {
            if(soLuong.get() > 1)
                soLuong.set(soLuong.get() - 1);
            else {
                menuItemSoLuongError.setText("Đã đạt số lượng tối thiểu");
                contextMenuSoLuongError.show(txtSoLuong, Side.BOTTOM, 0, 5);
            }
        });

        soLuong.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                txtSoLuong.setText(newValue.toString());
                chiTietDonNhapHang.setSoLuong(soLuong.get());
                lblThanhTien.setText(StringUtils.formatCurrency(chiTietDonNhapHang.getThanhTien().doubleValue()));
            }
        });

        txtSoLuong.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                contextMenuSoLuongError.hide();
                if(!newValue.trim().isEmpty()) {
                    if (!newValue.matches("\\d*")) {
                        txtSoLuong.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            }
        });

        txtSoLuong.setOnKeyPressed(new EventHandler<>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    if (!txtSoLuong.getText().trim().isEmpty()) {
                        int soLuongNhapVao = Integer.parseInt(txtSoLuong.getText());

                        if (soLuongNhapVao <= 0) {
                            soLuong.set(1);
                            txtSoLuong.setText("1");
                        } else {
                            soLuong.set(soLuongNhapVao);
                        }
                    } else {
                        soLuong.set(0);
                        txtSoLuong.setText("0");
                    }
                }
            }
        });


        btnPlusSoLuongDaNhan.setOnMousePressed(mouseEvent -> {
            if(soLuongDaNhan.get() < soLuong.get())
                soLuongDaNhan.set(soLuongDaNhan.get() + 1);
            else {
                menuItemSoLuongDaNhanError.setText("Đã đạt số lượng tối đa");
                contextMenuSoLuongDaNhanError.show(txtSoLuongDaNhan, Side.BOTTOM, 0, 5);
            }
        });

        btnMinusSoLuongDaNhan.setOnMousePressed(mouseEvent -> {
            if(soLuongDaNhan.get() >= 1)
                soLuongDaNhan.set(soLuongDaNhan.get() - 1);
            else {
                menuItemSoLuongDaNhanError.setText("Đã đạt số lượng tối thiểu");
                contextMenuSoLuongDaNhanError.show(txtSoLuongDaNhan, Side.BOTTOM, 0, 5);
            }
        });

        soLuongDaNhan.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                txtSoLuongDaNhan.setText(newValue.toString());
                chiTietDonNhapHang.setSoLuongDaNhan(soLuongDaNhan.get());
            }
        });

        chiTietDonNhapHang.soLuongConLaiProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newValue) {
                lblSoLuongConLai.setText(String.valueOf(newValue.intValue()));
            }
        });

        txtSoLuongDaNhan.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                contextMenuSoLuongDaNhanError.hide();
                if(!newValue.trim().isEmpty()) {
                    if (!newValue.matches("\\d*")) {
                        txtSoLuongDaNhan.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            }
        });

        txtSoLuongDaNhan.setOnKeyPressed(new EventHandler<>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    if (!txtSoLuongDaNhan.getText().trim().isEmpty()) {
                        int soLuongNhapVao = Integer.parseInt(txtSoLuongDaNhan.getText());

                        if (soLuongNhapVao < 0) {
                            soLuongDaNhan.set(0);
                            txtSoLuongDaNhan.setText("0");
                        } else if(soLuongNhapVao > soLuong.get()) {
                            soLuongDaNhan.set(soLuong.get());
                            txtSoLuongDaNhan.setText(String.valueOf(soLuong.get()));
                        } else {
                            soLuongDaNhan.set(soLuongNhapVao);
                        }
                    } else {
                        soLuongDaNhan.set(0);
                        txtSoLuongDaNhan.setText("0");
                    }
                }
            }
        });
    }

    private void loadData() {
        lblTenSanPham.setText(sanPham.getTenSanPham());
        lblMaSanPham.setText(sanPham.getMaSanPham());
        lblThuongHieu.setText(sanPham.getThuongHieu().getTenThuongHieu());
        lblColor.setText(sanPham.getMauSac().getTenMau());
        boxColor.setStyle("-fx-background-color: " + sanPham.getMauSac().getCode());
        lblSize.setText(sanPham.getKichThuoc().toString());
        txtGiaNhap.setText(StringUtils.formatCurrency(sanPham.getGiaNhap()));
        txtSoLuong.setText(Integer.toString(chiTietDonNhapHang.getSoLuong()));
        txtSoLuongDaNhan.setText(Integer.toString(chiTietDonNhapHang.getSoLuongDaNhan()));
        lblSoLuongConLai.setText(Integer.toString(chiTietDonNhapHang.getSoLuongConLai()));
        lblThanhTien.setText(StringUtils.formatCurrency(chiTietDonNhapHang.getThanhTien().doubleValue()));

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

    public SVGPath getBtnDelete() {
        return btnDelete;
    }
}
