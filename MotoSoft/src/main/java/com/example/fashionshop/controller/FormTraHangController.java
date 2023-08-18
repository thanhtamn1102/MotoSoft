package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.OrderDetailItemOrderView;
import com.example.fashionshop.cus_comp.PTHImageItem;
import com.example.fashionshop.mapper.PhieuTraHangMapper;
import com.example.fashionshop.model.PhieuTraHangProperty;
import com.example.fashionshop.utils.StringUtils;
import com.example.fashionshop.values.StringValues;
import com.example.model.*;
import com.example.service.PhieuTraHangService;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class FormTraHangController implements Initializable {

    public static final int ADD = 1;
    public static final int VIEW = 2;

    @FXML
    private Label lblMaDonHang, lblNgayTaoDon, lblNhanVien;
    @FXML private Label lblHoTenKhachHang, lblSoDienThoai, lblDiaChi;
    @FXML private Label lblTongTien, lblGiamGia, lblPhuThu, lblVAT, lblPhiVanChuyen, lblTongThanhToan;
    @FXML private TextArea txtGhiChu;
    @FXML private ListView<ChiTietDonHang> lvChiTietDonHang;

    @FXML private Label lblNgayTaoPhieuTraHang, lblNhanVienTaoPhieuTraHang;
    @FXML private HBox boxHinhAnhPhieuTraHang;
    @FXML private Label lblTienHoanLai, lblTongTienHoanLai;
    @FXML private TextField txtTiLeHoanTien, txtPhuThu;
    @FXML private TextArea txtGhiChuPhieuTraHang;
    @FXML private VBox boxTiLeHoanTienError, boxPhuThuError;
    private Label lblTiLeHoanTienError, lblPhuThuError;
    @FXML private Button btnThemHinhAnh, btnXacNhanTraHangHoanTien;

    private DonHang donHang;
    private PhieuTraHang phieuTraHang;
    private int type;

    private PhieuTraHangProperty phieuTraHangProperty;

    private PhieuTraHangService phieuTraHangService;

    private TaiKhoan taiKhoan;

    public FormTraHangController(TaiKhoan taiKhoan, DonHang donHang, int type){
        this.taiKhoan = taiKhoan;
        this.donHang = donHang;
        this.phieuTraHang = donHang.getPhieuTraHang();
        this.type = type;

        if(phieuTraHang == null) {
            phieuTraHang = new PhieuTraHang();
            phieuTraHang.setDonHang(donHang);
            phieuTraHang.setTiLeHoanTien(100);
            phieuTraHang.setPhuThu(0);
            phieuTraHang.setNhanVien(taiKhoan.getNhanVien());
        }

        phieuTraHangProperty = PhieuTraHangMapper.toPhieuTraHangProperty(phieuTraHang);

        try {
            Context context = new InitialContext();
            phieuTraHangService = (PhieuTraHangService) context.lookup(StringValues.SERVER_URL + "PhieuTraHangSerivce");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        init();
        addControls();
        addEvents();
        loadData();

        if(type == ADD) {
            setEditable(true);
        } else if(type == VIEW) {
            setEditable(false);
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-don-hang.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void addControls(){
        lblTiLeHoanTienError = new Label();
        lblTiLeHoanTienError.setTextFill(Color.RED);
        lblPhuThuError = new Label();
        lblPhuThuError.setTextFill(Color.RED);

        lvChiTietDonHang.setCellFactory(new Callback<>() {
            @Override
            public ListCell<ChiTietDonHang> call(ListView<ChiTietDonHang> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(ChiTietDonHang orderDetail, boolean empty) {
                        super.updateItem(orderDetail, empty);
                        if (orderDetail != null) {
                            OrderDetailItemOrderView orderDetailItem = new OrderDetailItemOrderView(orderDetail);
                            setGraphic(orderDetailItem);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
    }

    private void addEvents(){
        btnThemHinhAnh.setOnMousePressed(mouseEvent -> {
            openFormThemHinhAnh(mouseEvent);
        });

        txtPhuThu.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                boxPhuThuError.getChildren().clear();

                if(newValue.trim().isEmpty()) {
                    txtPhuThu.setText("0");
                } else {
                    String numberString = newValue.replace(",", "");

                    try {
                        double phuThu = Double.parseDouble(numberString);
                        if(phuThu < 0) {
                            lblPhuThuError.setText("Phụ thu phải là số và >= 0");
                            boxPhuThuError.getChildren().add(lblPhuThuError);
                        } else {
                            phieuTraHangProperty.setPhuThu(phuThu);
                        }
                    } catch (Exception ex) {
                        lblPhuThuError.setText("Phụ thu phải là số và >= 0");
                        boxPhuThuError.getChildren().add(lblPhuThuError);
                    }
                }
            }
        });

        txtPhuThu.setOnKeyPressed(keyEvent -> {
            String keyChar = keyEvent.getCode().getChar();
            if(keyChar.matches("[0-9]") || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                txtPhuThu.setEditable(true);
            }
            else {
                txtPhuThu.setEditable(false);
            }
        });

        txtTiLeHoanTien.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                boxTiLeHoanTienError.getChildren().clear();

                if(newValue.trim().isEmpty()) {
                    txtTiLeHoanTien.setText("0");
                } else {
                    String numberString = newValue.replace(",", "");

                    try {
                        double tiLeHoanTien = Double.parseDouble(numberString);
                        if(tiLeHoanTien <= 0) {
                            lblTiLeHoanTienError.setText("Tỉ lệ hoàn tiền phải là số và > 0");
                            boxTiLeHoanTienError.getChildren().add(lblTiLeHoanTienError);
                        } else {
                            phieuTraHangProperty.setTiLeTienHoanLai(tiLeHoanTien);
                        }
                    } catch (Exception ex) {
                        lblTiLeHoanTienError.setText("Tỉ lệ hoàn tiền phải là số và > 0");
                        boxTiLeHoanTienError.getChildren().add(lblTiLeHoanTienError);
                    }
                }
            }
        });

        txtTiLeHoanTien.setOnKeyPressed(keyEvent -> {
            String keyChar = keyEvent.getCode().getChar();
            if(keyChar.matches("[0-9]") || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                txtTiLeHoanTien.setEditable(true);
            }
            else {
                txtTiLeHoanTien.setEditable(false);
            }
        });

        phieuTraHangProperty.tienHoanLaiProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number tienHoanLai) {
                lblTienHoanLai.setText(StringUtils.formatCurrency(tienHoanLai.doubleValue()));
            }
        });

        phieuTraHangProperty.tongTienHoanProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                lblTongTienHoanLai.setText(StringUtils.formatCurrency(t1.doubleValue()));
            }
        });

        txtGhiChu.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                phieuTraHangProperty.setGhiChu(t1);
            }
        });

        btnXacNhanTraHangHoanTien.setOnMousePressed(mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Xác nhận trả hàng hoàn tiền");
            alert.setContentText("Xác nhận trả hàng hoàn tiền");

            Optional<ButtonType> option = alert.showAndWait();

            if(option.get() == ButtonType.OK) {
                Set<String> dsHinhAnh = boxHinhAnhPhieuTraHang.getChildren()
                        .stream()
                        .map(node -> ((PTHImageItem)node).getImageAddress())
                        .collect(Collectors.toSet());

                PhieuTraHang phieuTraHang = PhieuTraHangMapper.toPhieuTraHang(phieuTraHangProperty);
                phieuTraHang.setNgayTaoPhieu(LocalDateTime.now());
                phieuTraHang.setDsHinhAnh(dsHinhAnh);

                try {
                    if(phieuTraHangService.addPhieuTraHang(phieuTraHang)) {
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setContentText("Tạo phiếu trả hành thành công");
                        alert.show();
                        closeStage(mouseEvent);
                    } else {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText("Tạo phiếu trả hàng không thành công");
                        alert.show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void loadData(){
        if(donHang != null){
            lblMaDonHang.setText(donHang.getMaDonHang());

            if(donHang.getKhachHang() != null) {
                lblHoTenKhachHang.setText(donHang.getKhachHang().getHoTen());
                lblSoDienThoai.setText(donHang.getKhachHang().getSdt());
                lblDiaChi.setText(donHang.getKhachHang().getDiaChi().toString());
            }

            if(donHang.getNhanVien() != null)
                lblNhanVien.setText(donHang.getNhanVien().toString());

            lblNgayTaoDon.setText(donHang.getNgayTaoDonHang().toString());
            txtGhiChu.setText(donHang.getGhiChu());
            lblTongTien.setText(StringUtils.formatCurrency(donHang.tinhTongTien()));
            lblGiamGia.setText(StringUtils.formatCurrency(donHang.getGiamGia()));
            lblPhuThu.setText(StringUtils.formatCurrency(donHang.getPhuThu()));
            lblVAT.setText(Double.toString(donHang.getVAT()));
            lblPhiVanChuyen.setText(StringUtils.formatCurrency(donHang.getPhiVanChuyen()));
            lblTongThanhToan.setText(StringUtils.formatCurrency(donHang.getTongThanhTien()));

            lvChiTietDonHang.setItems(FXCollections.observableArrayList(donHang.getChiTietDonHangs().stream().toList()));
        }

        if(phieuTraHangProperty != null) {
            NhanVien nhanVienTaoPhieuTraHang = phieuTraHangProperty.getNhanVien();
            if(nhanVienTaoPhieuTraHang != null)
                lblNhanVienTaoPhieuTraHang.setText(nhanVienTaoPhieuTraHang.toString());

            if(phieuTraHangProperty.getNgayTaoPhieu() != null)
                lblNgayTaoPhieuTraHang.setText(phieuTraHangProperty.getNgayTaoPhieu().toString());

            txtTiLeHoanTien.setText(Double.toString(phieuTraHangProperty.getTiLeTienHoanLai()));
            lblTongTien.setText(StringUtils.formatCurrency(phieuTraHangProperty.getTienHoanLai()));
            txtPhuThu.setText(Double.toString(phieuTraHangProperty.getPhuThu()));
            lblTongTienHoanLai.setText(StringUtils.formatCurrency(phieuTraHangProperty.getTongTienHoan()));
            txtGhiChu.setText(phieuTraHangProperty.getGhiChu());

            if(phieuTraHangProperty.getDsHinhAnh() != null && phieuTraHangProperty.getDsHinhAnh().size() > 0) {
                for(String imageAddress : phieuTraHang.getDsHinhAnh()) {
                    PTHImageItem imageItem = new PTHImageItem(imageAddress);

                    imageItem.getBtnDelete().setVisible(false);

                    imageItem.getBtnDelete().setOnMousePressed(mouseEvent -> {
                        boxHinhAnhPhieuTraHang.getChildren().remove(imageItem);
                    });

                    boxHinhAnhPhieuTraHang.getChildren().add(imageItem);
                }
            }
        }
    }

    private void setEditable(boolean b) {
        btnThemHinhAnh.setVisible(b);
        txtTiLeHoanTien.setEditable(b);
        txtPhuThu.setEditable(b);
        btnXacNhanTraHangHoanTien.setVisible(b);
        txtGhiChuPhieuTraHang.setEditable(b);
    }

    private void openFormThemHinhAnh(MouseEvent mouseEvent) {
        try {
            Stage context = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-them-hinh-anh.fxml"));
            fxmlLoader.setController(new FormThemHinhAnhVaoPTH(boxHinhAnhPhieuTraHang));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(context);
            stage.setTitle("Thêm hình ảnh");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void closeStage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
