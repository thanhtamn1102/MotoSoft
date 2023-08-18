package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.AutoCompleteTextField;
import com.example.fashionshop.cus_comp.OrderDetailItem;
import com.example.fashionshop.cus_comp.OrderItem;
import com.example.fashionshop.cus_comp.ProductItemSearchView;
//import com.example.fashionshop.impl.ChiTietDonHangServiceImpl;
//import com.example.fashionshop.model.*;
//import com.example.fashionshop.service.ChiTietDonHangService;
import com.example.fashionshop.mapper.DonHangMapper;
import com.example.fashionshop.model.ChiTietDonHangProperty;
import com.example.fashionshop.model.DonHangProperty;
import com.example.fashionshop.utils.StringUtils;
import com.example.fashionshop.values.StringValues;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.model.DonHang;
import com.example.model.KhachHang;
import com.example.model.SanPham;
import com.example.model.TaiKhoan;
import com.example.service.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Optional;

public class PageBanHangController extends VBox {

    private static final double VAT = 8;
    private static final int NUMBER_SAVE_ORDER_MAX = 5;

    @FXML
    private TabPane tabPane;
    @FXML private Label lblMaDonHang, lblNgayTaoDonHang;
    @FXML private Label lblMaNhanVien, lblTenKhachHang, lblSoDienThoai, lblDiaChi;
    @FXML private Label lblTenNhanVien, lblTongTien, lblTongThanhToan;
    @FXML private VBox boxTextFieldKhachHang, boxTextFieldSanPham, boxDonHang;
    @FXML private TextField txtGiamGia, txtPhuThu, txtVAT, txtPhiVanChuyen, txtTienNhan;
    @FXML private VBox boxPhuThuError, boxGiamGiaError, boxVATError, boxPhiVanChuyenError;
    private Label lblPhuThuError, lblGiamGiaError, lblVATError, lblPhiVanChuyenError;
    @FXML private Label lblTienThoiLai, lblTienNhanError;
    @FXML private Button btnThanhToan, btnLuuDonHang, btnThemKhachHang;
    @FXML private ListView<ChiTietDonHangProperty> listView;
    @FXML private ListView<DonHangProperty> lvDonHangChoThanhToan;
    @FXML private TextArea txtGhiChu;
    @FXML private HBox boxTaiKhoan;

    private AutoCompleteTextField<KhachHang> txtKhachHang;
    private AutoCompleteTextField<SanPham> txtSanPham;

    private ObservableList<KhachHang> khachHangList = FXCollections.observableArrayList();
    private ObservableList<SanPham> productList = FXCollections.observableArrayList();
    private ObservableList<DonHangProperty> dsDonHangChoThanhToan = FXCollections.observableArrayList();
    private DonHangProperty donHang;
    private TaiKhoan taiKhoan;
    private static Context context ;

    private DonHangService donHangService;
    private KhachHangService khachHangService;
    private SanPhamService sanPhamService;
    private IDService idService;


    public PageBanHangController(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;

        try {
           context = new InitialContext();
           donHangService = (DonHangService) context.lookup(StringValues.SERVER_URL + "DonHangService");
           khachHangService = (KhachHangService) context.lookup(StringValues.SERVER_URL + "KhachHangService");
           sanPhamService = (SanPhamService) context.lookup(StringValues.SERVER_URL + "SanPhamService");
           idService = (IDService) context.lookup(StringValues.SERVER_URL + "IDService");

            init();
            addControls();
            addEvents();
            loadData();

            createOrder();
        }catch (Exception exception){
           exception.printStackTrace();
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("pages/page-ban-hang.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addControls() {
        txtKhachHang = new AutoCompleteTextField<KhachHang>(khachHangList);
        txtKhachHang.setStyle("-fx-background-color: white; -fx-border-color: white;");
        txtKhachHang.setFont(new Font("Segoe UI", 13.5));
        txtKhachHang.setPromptText("Nhập tên hoặc số điện thoại để tìm khách hàng");
        boxTextFieldKhachHang.getChildren().add(txtKhachHang);

        txtSanPham = new AutoCompleteTextField<>(productList) {
            @Override
            public Node updateItem(SanPham product, String text) {
                return new ProductItemSearchView(product);
            }
        };
        txtSanPham.setStyle("-fx-background-color: white; -fx-border-color: white;");
        txtSanPham.setFont(new Font("Segoe UI", 13.5));
        txtSanPham.setPromptText("Nhập tên hoặc mã sản phẩm để tìm kiếm sản phẩm");
        boxTextFieldSanPham.getChildren().add(txtSanPham);

        listView.setCellFactory(new Callback<ListView<ChiTietDonHangProperty>, ListCell<ChiTietDonHangProperty>>() {
            @Override
            public ListCell<ChiTietDonHangProperty> call(ListView<ChiTietDonHangProperty> chiTietDonHangPropertyListView) {
                return new ListCell<ChiTietDonHangProperty>() {
                    @Override
                    protected void updateItem(ChiTietDonHangProperty chiTietDonHang, boolean b) {
                        super.updateItem(chiTietDonHang, b);

                        if(chiTietDonHang != null) {
                            OrderDetailItem orderDetailItem = new OrderDetailItem(chiTietDonHang);

                            orderDetailItem.getBtnDelete().setOnMousePressed(mouseEvent -> {
                                donHang.getChiTietDonHangs().remove(chiTietDonHang);
                            });

                            orderDetailItem.getLblTenSanPham().setOnMouseClicked(mouseEvent -> {
                                Stage context = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                                openFormViewProductDetail(context, chiTietDonHang.getSanPham());
                            });

                            setGraphic(orderDetailItem);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        lvDonHangChoThanhToan.setCellFactory(new Callback<ListView<DonHangProperty>, ListCell<DonHangProperty>>() {
            @Override
            public ListCell<DonHangProperty> call(ListView<DonHangProperty> donHangPropertyListView) {
                return new ListCell<DonHangProperty>() {
                    @Override
                    protected void updateItem(DonHangProperty o, boolean b) {
                        super.updateItem(o, b);

                        if(o != null) {
                            OrderItem orderItem = new OrderItem(DonHangMapper.toDonHang(o));

                            orderItem.getBtnDelete().setOnMouseClicked(mouseEvent -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Thông báo");
                                alert.setHeaderText(null);
                                alert.setContentText("Bạn có chắc chắn muốn xóa đơn hàng chờ thanh toán này?");
                                Optional<ButtonType> option = alert.showAndWait();
                                if(option.get() == ButtonType.OK) {
                                    dsDonHangChoThanhToan.remove(o);
                                }
                            });

                            orderItem.getBoxAction().getChildren().remove(orderItem.getBtnXemChiTiet());
                            orderItem.getBoxAction().getChildren().remove(orderItem.getBtnTraHang());
                            orderItem.getBoxAction().getChildren().remove(orderItem.getBtnPhieuTraHang());

                            orderItem.getBtnThanhToan().setOnMouseClicked(mouseEvent -> {
                                createOrder(o);
                                dsDonHangChoThanhToan.remove(o);
                                tabPane.getSelectionModel().select(0);
                            });

                            setGraphic(orderItem);
                        } else {
                            setGraphic(null);
                        }

                    }
                };
            }
        });

        lblGiamGiaError = new Label();
        lblGiamGiaError.setTextFill(Color.RED);
        lblPhuThuError = new Label();
        lblPhuThuError.setTextFill(Color.RED);
        lblVATError = new Label();
        lblVATError.setTextFill(Color.RED);
        lblPhiVanChuyenError = new Label();
        lblPhiVanChuyenError.setTextFill(Color.RED);
    }

    private void addEvents() {
        btnThanhToan.setOnMouseClicked(mouseEvent -> {
            try {
                if(thanhToan()) {
                    xuatHoaDon();
                    clearOrder();
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        btnLuuDonHang.setOnMouseClicked(mouseEvent -> {
            luuDonHangChoThanhToan();
        });

        boxTaiKhoan.setOnMousePressed(mouseEvent -> {
            Stage stage = new Stage();
            openFormTaiKhoan(stage, taiKhoan);
        });

        btnThemKhachHang.setOnMousePressed(mouseEvent -> {
            openFromThemKhachHang(mouseEvent);
        });
    }

    private void addEventForOrder(DonHangProperty donHang) {
        txtKhachHang.getEntryMenu().setOnAction(e -> {
            ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, event -> {
                if (txtKhachHang.getLastSelectedObject() != null) {
                    KhachHang khachHang = txtKhachHang.getLastSelectedObject();
                    txtKhachHang.setText(khachHang.toString());
                    lblTenKhachHang.setText(khachHang.getHoTen());
                    lblSoDienThoai.setText(khachHang.getSdt());
                    lblDiaChi.setText(khachHang.getDiaChi().toString());

                    donHang.setKhachHang(khachHang);
                }
            });
        });

        txtSanPham.getEntryMenu().setOnAction(e -> {
            ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, event -> {
                if(txtSanPham.getLastSelectedObject() != null) {
                    SanPham product = txtSanPham.getLastSelectedObject();
                    addProductToOrder(product);
                    txtSanPham.setText("");
                }
            });
        });

        donHang.tongThanhTienProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lblTongThanhToan.setText(StringUtils.formatCurrency(newValue.doubleValue()));
            }
        });

        donHang.tongTienProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newValue) {
                lblTongTien.setText(StringUtils.formatCurrency(newValue.doubleValue()));
            }
        });

        txtGiamGia.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                boxGiamGiaError.getChildren().clear();

                if(newValue.trim().isEmpty()) {
                    txtGiamGia.setText("0");
                } else {
                    String numberString = newValue.replace(",", "");

                    try {
                        double giamGia = Double.parseDouble(numberString);
                        if(giamGia < 0) {
                            lblGiamGiaError.setText("Giảm giá phải là số và >= 0");
                            boxGiamGiaError.getChildren().add(lblGiamGiaError);
                        } else {
                            donHang.setGiamGia(giamGia);
                        }
                    } catch (Exception ex) {
                        lblGiamGiaError.setText("Giảm giá phải là số và >= 0");
                        boxGiamGiaError.getChildren().add(lblGiamGiaError);
                    }
                }
            }
        });

        txtGiamGia.setOnKeyPressed(keyEvent -> {
            String keyChar = keyEvent.getCode().getChar();
            if(keyChar.matches("[0-9]") || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                txtGiamGia.setEditable(true);
            }
            else {
                txtGiamGia.setEditable(false);
            }
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
                            donHang.setPhuThu(phuThu);
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

        txtVAT.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                boxVATError.getChildren().clear();

                if(newValue.trim().isEmpty()) {
                    txtVAT.setText("0");
                } else {
                    String numberString = newValue.replace(",", "");

                    try {
                        double VAT = Double.parseDouble(numberString);
                        if(VAT <= 0) {
                            lblVATError.setText("VAT phải là số và > 0");
                            boxVATError.getChildren().add(lblVATError);
                        } else {
                            donHang.setVAT(VAT);
                        }
                    } catch (Exception ex) {
                        lblVATError.setText("VAT là số và > 0");
                        boxVATError.getChildren().add(lblVATError);
                    }
                }
            }
        });

        txtVAT.setOnKeyPressed(keyEvent -> {
            String keyChar = keyEvent.getCode().getChar();
            if(keyChar.matches("[0-9]") || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                txtVAT.setEditable(true);
            }
            else {
                txtVAT.setEditable(false);
            }
        });

        txtPhiVanChuyen.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                boxPhiVanChuyenError.getChildren().clear();

                if(newValue.trim().isEmpty()) {
                    txtPhiVanChuyen.setText("0");
                } else {
                    String numberString = newValue.replace(",", "");

                    try {
                        double phiVanChuyen = Double.parseDouble(numberString);
                        if(phiVanChuyen < 0) {
                            lblPhiVanChuyenError.setText("Phí vận chuyển phải là số và >= 0");
                            boxPhiVanChuyenError.getChildren().add(lblPhiVanChuyenError);
                        } else {
                            donHang.setPhiVanChuyen(phiVanChuyen);
                        }
                    } catch (Exception ex) {
                        lblPhiVanChuyenError.setText("Phí vận chuyển là số và >= 0");
                        boxPhiVanChuyenError.getChildren().add(lblPhiVanChuyenError);
                    }
                }
            }
        });

        txtPhiVanChuyen.setOnKeyPressed(keyEvent -> {
            String keyChar = keyEvent.getCode().getChar();
            if(keyChar.matches("[0-9]") || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                txtPhiVanChuyen.setEditable(true);
            }
            else {
                txtPhiVanChuyen.setEditable(false);
            }
        });

        txtTienNhan.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lblTienNhanError.setText("");

                if(newValue.trim().isEmpty()) {
                    txtTienNhan.setText("0");
                }
                else {
                    String numberString = newValue.replace(",", "");

                    double tienNhan = Double.parseDouble(numberString);
                    if(tienNhan < donHang.getTongThanhTien()) {
                        lblTienNhanError.setText("Tiền nhận phải >= Tổng thành tiền");
                        lblTienThoiLai.setText("0");
                    }
                    else {
                        double tienThoiLai = tienNhan - donHang.getTongThanhTien();
                        lblTienThoiLai.setText(StringUtils.formatCurrency(tienThoiLai));
                    }

                    txtTienNhan.setText(StringUtils.formatCurrency(Double.parseDouble(numberString)));
                }
            }
        });

        txtTienNhan.setOnKeyPressed(keyEvent -> {
            String keyChar = keyEvent.getCode().getChar();
            if(keyChar.matches("[0-9]") || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                txtTienNhan.setEditable(true);
            }
            else {
                txtTienNhan.setEditable(false);
            }
        });

        txtGhiChu.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String ghiChu) {
                donHang.setGhiChu(ghiChu);
            }
        });
    }

    private boolean thanhToan() throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);

        double giamGia = Double.parseDouble(txtGiamGia.getText().trim());
        double phuThu = Double.parseDouble(txtPhuThu.getText().trim());
        double VAT = Double.parseDouble(txtVAT.getText().trim());
        double phiVanChuyen = Double.parseDouble(txtPhiVanChuyen.getText().trim());
        double tienNhan = Double.parseDouble(txtTienNhan.getText().replace(",", ""));

        if (donHang.getKhachHang() == null) {
            alert.setContentText("Chưa nhập thông tin khách hàng!");
            alert.show();
            return false;
        } else if (donHang.getChiTietDonHangs().size() <= 0) {
            alert.setContentText("Không có sản phẩm nào trong đơn hàng");
            alert.show();
            return false;
        } else if (tienNhan < donHang.getTongThanhTien()) {
            alert.setContentText("Tiền nhận phải >= Tổng thành tiền");
            alert.show();
            return false;
        } else {
            donHang.setNgayTaoDonHang(LocalDateTime.now());
            donHang.setGhiChu(txtGhiChu.getText());

            DonHang donHangMoi = DonHangMapper.toDonHang(donHang);

            if (donHangService.addDonHang(donHangMoi)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Thanh toán thành công");
                alert.show();
                return true;
            } else {
                alert.setContentText("Thanh toán thất bại");
                alert.show();
                return false;
            }
        }
    }

    private void xuatHoaDon() {

    }

    private void luuDonHangChoThanhToan() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);

        if(dsDonHangChoThanhToan.size() < NUMBER_SAVE_ORDER_MAX) {
            if(donHang.getChiTietDonHangs().size() > 0) {
                try {
                    donHang.setNgayTaoDonHang(LocalDateTime.now());
                    DonHangProperty o = donHang.clone();
                    dsDonHangChoThanhToan.add(o);
                    clearOrder();
                    alert.setContentText("Lưu đơn hàng chờ thanh toán thành công");
                    alert.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setHeaderText("Đơn hàng rỗng");
                alert.setHeaderText("Đơn hàng không có sản phẩm nào");
                alert.show();
            }
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("Đã đạt số lượng đơn hàng chờ thanh toán tối đa");
            alert.setContentText("Chỉ cho phép lưu tối đa " + NUMBER_SAVE_ORDER_MAX + " đơn hàng chờ thanh toán");
            alert.show();
        }
    }

    private void addProductToOrder(SanPham product) {
        for (ChiTietDonHangProperty od : donHang.getChiTietDonHangs()) {
            if(od.getSanPham().getMaSanPham().equals(product.getMaSanPham())) {
                od.setSoLuong(od.getSoLuong() + 1);
                return;
            }
        }

        ChiTietDonHangProperty orderDetail = new ChiTietDonHangProperty(DonHangMapper.toDonHang(donHang), product, 1);
        orderDetail.setDonHang(DonHangMapper.toDonHang(donHang));
        donHang.getChiTietDonHangs().add(orderDetail);
    }

    private void loadData() throws RemoteException {
        lblMaNhanVien.setText(taiKhoan.getNhanVien().getMaNhanVien());
        lblTenNhanVien.setText(taiKhoan.getNhanVien().getHoTen());

        khachHangList.setAll(khachHangService.getAllCustomer());
        productList.setAll(sanPhamService.getAllSanPham());
        lvDonHangChoThanhToan.setItems(dsDonHangChoThanhToan);

        Thread thread = new Thread(() -> {
            try {
                while(true) {
                    LocalDateTime nowDate = LocalDateTime.now();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            lblNgayTaoDonHang.setText(nowDate.getDayOfMonth() + "/" + nowDate.getMonthValue() + "/" + nowDate.getYear()
                                    + "   " + nowDate.getHour() + ":" + nowDate.getMinute() + ":" + nowDate.getSecond());
                        }
                    });
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }); thread.start();
    }

    private void createOrder() throws RemoteException {
        String maDonHang = idService.createMaDonHang();
        lblMaDonHang.setText(maDonHang);
        donHang = new DonHangProperty();
        donHang.setMaDonHang(maDonHang);
        donHang.setNhanVien(taiKhoan.getNhanVien());
        donHang.setVAT(VAT);

        listView.setItems(donHang.getChiTietDonHangs());

        addEventForOrder(donHang);
    }

    private void createOrder(DonHangProperty o) {
        try {
            this.donHang = o.clone();
            donHang.setNhanVien(taiKhoan.getNhanVien());

            listView.setItems(donHang.getChiTietDonHangs());

            lblTongTien.setText(StringUtils.formatCurrency(donHang.getTongTien()));
            lblTongThanhToan.setText(StringUtils.formatCurrency(donHang.getTongThanhTien()));

            txtGiamGia.setText(Double.toString(donHang.getGiamGia()));
            txtPhuThu.setText(Double.toString(donHang.getPhuThu()));
            txtVAT.setText(Double.toString(donHang.getVAT()));
            txtPhiVanChuyen.setText(Double.toString(donHang.getPhiVanChuyen()));
            txtGhiChu.setText(donHang.getGhiChu());

            KhachHang khachHang = donHang.getKhachHang();
            if(khachHang != null) {
                txtKhachHang.setText(khachHang.toString());
                lblTenKhachHang.setText(khachHang.getHoTen());
                lblSoDienThoai.setText(khachHang.getSdt());
            }

            addEventForOrder(donHang);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void clearOrder() throws RemoteException {
        listView.getItems().clear();
        lblTenKhachHang.setText("");
        lblSoDienThoai.setText("");
        lblDiaChi.setText("");
        lblTongTien.setText("0");
        lblTongThanhToan.setText("0");
        txtGiamGia.setText("0");
        txtPhuThu.setText("0");
        txtVAT.setText("8");
        txtPhiVanChuyen.setText("0");
        txtTienNhan.setText("0");
        lblTienThoiLai.setText("0");
        txtGhiChu.setText("");
        txtKhachHang.setText("");

        createOrder();
    }

    private void openFormViewProductDetail(Stage context, SanPham product) {
        Scene scene = new Scene(new FormSanPhamController(productList, product, FormSanPhamController.VIEW));
        scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toExternalForm());
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(context);
        stage.setScene(scene);
        stage.setTitle("Sản phẩm");
        stage.show();
    }

    private void openFormTaiKhoan(Stage stage, TaiKhoan taiKhoan) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-tai-khoan.fxml"));
            fxmlLoader.setController(new FormTaiKhoanController(taiKhoan));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toString());
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openFromThemKhachHang(MouseEvent mouseEvent) {
        Stage context = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(new FormKhachHangController(khachHangList, null, FormKhachHangController.ADD));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(context);
        stage.setTitle("Thêm khách hàng");
        stage.show();
    }

}
