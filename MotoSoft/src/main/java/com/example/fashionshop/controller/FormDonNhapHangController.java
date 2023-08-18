package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.ChiTietDonNhapHangItem;
//import com.example.fashionshop.impl.DonNhapHangImpl;
//import com.example.fashionshop.impl.NhaCungCapImpl;
import com.example.fashionshop.mapper.DonNhapHangMapper;
import com.example.fashionshop.model.*;
//import com.example.fashionshop.service.DonNhapHangService;
//import com.example.fashionshop.service.NhaCungCapService;
import com.example.fashionshop.utils.NumberUtils;
import com.example.fashionshop.utils.StringUtils;
import com.example.fashionshop.values.StringValues;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.model.DonNhapHang;
import com.example.model.DonNhapHangStatus;
import com.example.model.NhaCungCap;
import com.example.model.TaiKhoan;
import com.example.service.DonNhapHangService;
import com.example.service.IDService;
import com.example.service.NhaCungCapService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

public class FormDonNhapHangController extends BorderPane {

    public static final int VIEW = 0;
    public static final int ADD = 1;
    public static final int UPDATE = 2;

    @FXML private Label lblTitle;
    @FXML private Label lblMaDonNhapHang;
    @FXML private Label lblNgayTaoDon;
    @FXML private Label lblTrangThai;
    @FXML private ComboBox<NhaCungCap> cbxTimKiemNCC;
    @FXML private Label lblMaNhanVien;
    @FXML private Label lblHoTenNhanVien;
    @FXML private Label lblTenNhaCungCap;
    @FXML private Label lblSDTNhaCungCap;
    @FXML private Label lblDiaChiNhaCungCap;
    @FXML private ListView<ChiTietDonNhapHangProperty> listView;
    @FXML private Label lblTongTien, lblTongThanhToan;
    @FXML private TextField txtGiamGia, txtPhuThu, txtVAT, txtPhiVanChuyen;
    @FXML private Label lblGiamGiaError, lblPhuThuError, lblVATError, lblPhiVanChuyenError;
    @FXML private TextField txtDaThanhToan;
    @FXML private Label lblDaThanhToanError;
    @FXML private Label lblCongNo;
    @FXML private TextArea txtGhiChu;
    @FXML private Button btnLuu, btnXoa, btnCapNhat, btnThoat, btnThemSanPham;

    private int type;

    private TaiKhoan taiKhoan;
    private ObservableList<DonNhapHang> dsDonNhapHang;
    private ObservableList<ChiTietDonNhapHangProperty> dsChiTietDonNhapHang;
    private DonNhapHangProperty donNhapHang;
    private DonNhapHang donNhapHangGoc;
    private DonNhapHangService donNhapHangService;
    private NhaCungCapService nhaCungCapService;
    private static Context context ;
    private IDService idService;

    public FormDonNhapHangController(TaiKhoan taiKhoan, ObservableList<DonNhapHang> dsDonNhapHang, DonNhapHang donNhapHang, int type) {
        this.taiKhoan = taiKhoan;
        this.dsDonNhapHang = dsDonNhapHang;
        this.donNhapHangGoc = donNhapHang;
        this.type = type;

        try {
            context = new InitialContext();
            donNhapHangService = (DonNhapHangService) context.lookup(StringValues.SERVER_URL + "DonNhapHangService");
            nhaCungCapService = (NhaCungCapService) context.lookup(StringValues.SERVER_URL + "NhaCungCapService");
            idService = (IDService) context.lookup(StringValues.SERVER_URL + "IDService");

            if (donNhapHang == null) {
                this.donNhapHang = new DonNhapHangProperty();
                this.donNhapHang.setMaDonNhapHang(idService.createMaDonNhapHang());
                this.donNhapHang.setNgayTao(LocalDateTime.now());
                this.donNhapHang.setTrangThai(DonNhapHangStatus.TAO_MOI);
                this.donNhapHang.setNhanVienTaoDon(taiKhoan.getNhanVien());
            } else {
                this.donNhapHang = DonNhapHangMapper.toDonNhapHangProperty(donNhapHang);
            }

            this.dsChiTietDonNhapHang = this.donNhapHang.getChiTietDonNhapHangs();

            init();
            addControls();
            addEvents();
            loadData();

            cbxTimKiemNCC.setItems(FXCollections.observableArrayList(nhaCungCapService.getAllNhaCungCap()));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (type == VIEW) {
            lblTitle.setText("Đơn nhập hàng");
            btnLuu.setVisible(false);
            setInputControlEnable(false);
        } else if (type == ADD) {
            lblTitle.setText("Thêm Đơn nhập hàng");
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);
        } else if (type == UPDATE) {
            lblTitle.setText("Cập nhật Đơn nhập hàng");
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-don-nhap-hang.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void addControls() {
        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<ChiTietDonNhapHangProperty> call(ListView<ChiTietDonNhapHangProperty> chiTietDonNhapHangPropertyListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(ChiTietDonNhapHangProperty chiTietDonNhapHangProperty, boolean b) {
                        super.updateItem(chiTietDonNhapHangProperty, b);
                        if (chiTietDonNhapHangProperty != null) {
                            ChiTietDonNhapHangItem item = new ChiTietDonNhapHangItem(chiTietDonNhapHangProperty);

                            item.getBtnDelete().setOnMousePressed(mouseEvent -> {
                                donNhapHang.getChiTietDonNhapHangs().remove(chiTietDonNhapHangProperty);
                            });

                            setGraphic(item);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
        listView.setItems(donNhapHang.getChiTietDonNhapHangs());

        if(donNhapHang.getTrangThai() == DonNhapHangStatus.DA_HOAN_THANH) {
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);
            btnLuu.setVisible(false);
        }
    }

    private void addEvents() {
        addDonNhapHangPropertyListener();

        btnThemSanPham.setOnMousePressed(mouseEvent -> {
            Stage context = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            openFormThemSanPham(context, donNhapHang);
        });

        btnThoat.setOnMousePressed(mouseEvent -> {
            closeStage(mouseEvent);
        });

        btnLuu.setOnMousePressed(mouseEvent -> {
            if (checkInput()) {
                donNhapHang.setGhiChu(txtGhiChu.getText());
                try {
                    if (donNhapHang.getChiTietDonNhapHangs().size() > 0) {
                        DonNhapHang donNhapHang = DonNhapHangMapper.toDonNhapHang(this.donNhapHang);

                        if (type == ADD) {
                            donNhapHang.setMaDonNhapHang(idService.createMaDonNhapHang());
                            addDonNhapHang(donNhapHang, mouseEvent);
                        } else if (type == UPDATE) {
                            updateDonNhapHang(donNhapHang, mouseEvent);
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("Không có sản phẩm nào trong đơn nhập hàng");
                        alert.show();
                    }
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });

        btnXoa.setOnMousePressed(mouseEvent -> {
            deleteDonNhapHang(donNhapHang, mouseEvent);
            closeStage(mouseEvent);
        });

        btnCapNhat.setOnMousePressed(mouseEvent -> {
            if (btnCapNhat.getText().equalsIgnoreCase("CẬP NHẬT")) {
                lblTitle.setText("Cập nhật Đơn nhập hàng");
                btnCapNhat.setText("HỦY CẬP NHẬT");
                btnCapNhat.setStyle("-fx-background-color:grey");
                btnLuu.setVisible(true);
                setInputControlEnable(true);
                type = UPDATE;
            } else {
                lblTitle.setText("Đơn nhập hàng");
                btnCapNhat.setText("CẬP NHẬT");
                btnCapNhat.setStyle("-fx-background-color:#0C75F5");
                btnLuu.setVisible(false);
                setInputControlEnable(false);
                type = VIEW;
                loadData();
            }
        });

        donNhapHang.tongTienProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                lblTongTien.setText(StringUtils.formatCurrency(newValue.doubleValue()));
            }
        });

        txtGiamGia.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblGiamGiaError.setText("");
                try {
                    double giamGia = Double.parseDouble(txtGiamGia.getText().replace(",", "").trim());

                    if (t1.length() > 3)
                        txtGiamGia.setText(StringUtils.formatCurrency(giamGia));

                    donNhapHang.setGiamGia(giamGia);
                } catch (Exception ex) {
                    lblGiamGiaError.setText("Giá trị nhập vào phải là số và > 0");
                }
            }
        });

        txtPhuThu.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblPhuThuError.setText("");
                try {
                    double phuThu = Double.parseDouble(txtPhuThu.getText().replace(",", "").trim());

                    if (t1.length() > 3)
                        txtPhuThu.setText(StringUtils.formatCurrency(phuThu));

                    donNhapHang.setPhuThu(phuThu);
                } catch (Exception ex) {
                    lblPhuThuError.setText("Giá trị nhập vào phải là số và > 0");
                }
            }
        });

        txtVAT.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblVATError.setText("");
                try {
                    double VAT = Double.parseDouble(txtVAT.getText().replace(",", "").trim());

                    if (t1.length() > 3)
                        txtVAT.setText(StringUtils.formatCurrency(VAT));

                    donNhapHang.setVAT(VAT);
                } catch (Exception ex) {
                    lblVATError.setText("Giá trị nhập vào phải là số và > 0");
                }
            }
        });

        txtPhiVanChuyen.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblPhiVanChuyenError.setText("");
                try {
                    double phiVanChuyen = Double.parseDouble(txtPhiVanChuyen.getText().replace(",", "").trim());

                    if (t1.length() > 3)
                        txtPhiVanChuyen.setText(StringUtils.formatCurrency(phiVanChuyen));

                    donNhapHang.setPhiVanChuyen(phiVanChuyen);
                } catch (Exception ex) {
                    lblPhiVanChuyenError.setText("Giá trị nhập vào phải là số và > 0");
                }
            }
        });

        txtDaThanhToan.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblDaThanhToanError.setText("");
                try {
                    double daThanhToan = Double.parseDouble(txtDaThanhToan.getText().replace(",", "").trim());

                    if (t1.length() > 3)
                        txtDaThanhToan.setText(StringUtils.formatCurrency(daThanhToan));

                    donNhapHang.setDaThanhToan(daThanhToan);
                } catch (Exception ex) {
                    lblDaThanhToanError.setText("Giá trị nhập vào phải là số và > 0");
                }
            }
        });

        cbxTimKiemNCC.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NhaCungCap>() {
            @Override
            public void changed(ObservableValue<? extends NhaCungCap> observableValue, NhaCungCap nhaCungCap, NhaCungCap t1) {
                donNhapHang.setNhaCungCap(t1);
                lblTenNhaCungCap.setText(donNhapHang.getNhaCungCap().getEmail());
                lblSDTNhaCungCap.setText(donNhapHang.getNhaCungCap().getSdt());
                lblDiaChiNhaCungCap.setText(donNhapHang.getNhaCungCap().getDiaChi().toString());
            }
        });
    }

    private void addDonNhapHangPropertyListener() {
        donNhapHang.tongThanhToanProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lblTongThanhToan.setText(StringUtils.formatCurrency(newValue.doubleValue()));
            }
        });

        donNhapHang.giamGiaProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                txtGiamGia.setText(StringUtils.formatCurrency(t1.doubleValue()));
            }
        });

        donNhapHang.phuThuProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                txtPhuThu.setText(StringUtils.formatCurrency(t1.doubleValue()));
            }
        });

        donNhapHang.VATProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                txtVAT.setText(StringUtils.formatCurrency(t1.doubleValue()));
            }
        });

        donNhapHang.phiVanChuyenProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                txtPhiVanChuyen.setText(StringUtils.formatCurrency(t1.doubleValue()));
            }
        });

        donNhapHang.daThanhToanProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                txtDaThanhToan.setText(StringUtils.formatCurrency(t1.doubleValue()));
            }
        });

        donNhapHang.congNoProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                lblCongNo.setText(StringUtils.formatCurrency(t1.doubleValue()));
            }
        });
    }

    private void loadData() {
        try {
            if(donNhapHang != null) {
                lblMaDonNhapHang.setText(donNhapHang.getMaDonNhapHang());
                lblNgayTaoDon.setText(donNhapHang.getNgayTao().toString());

                if(donNhapHang.getTrangThai() != null)
                    lblTrangThai.setText(donNhapHang.getTrangThai().toString());

                if(donNhapHang.getNhanVienTaoDon() != null) {
                    lblMaNhanVien.setText(donNhapHang.getNhanVienTaoDon().getMaNhanVien());
                    lblHoTenNhanVien.setText(donNhapHang.getNhanVienTaoDon().getHoTen());
                }

                if(donNhapHang.getNhaCungCap() != null) {
                    cbxTimKiemNCC.getSelectionModel().select(donNhapHang.getNhaCungCap());
                    lblTenNhaCungCap.setText(donNhapHang.getNhaCungCap().getEmail());
                    lblSDTNhaCungCap.setText(donNhapHang.getNhaCungCap().getSdt());
                    lblDiaChiNhaCungCap.setText(donNhapHang.getNhaCungCap().getDiaChi().toString());
                }

                lblTongTien.setText(StringUtils.formatCurrency(donNhapHang.getTongTien()));
                txtGiamGia.setText(StringUtils.formatCurrency(donNhapHang.getGiamGia()));
                txtPhuThu.setText(StringUtils.formatCurrency(donNhapHang.getPhuThu()));
                txtVAT.setText(StringUtils.formatCurrency(donNhapHang.getVAT()));
                txtPhiVanChuyen.setText(StringUtils.formatCurrency(donNhapHang.getPhiVanChuyen()));
                lblTongThanhToan.setText(StringUtils.formatCurrency(donNhapHang.getTongThanhToan()));
                lblCongNo.setText(StringUtils.formatCurrency(donNhapHang.getCongNo()));
                txtDaThanhToan.setText(StringUtils.formatCurrency(donNhapHang.getDaThanhToan()));
                txtGhiChu.setText(donNhapHang.getGhiChu());
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void openFormThemSanPham(Stage context, DonNhapHangProperty donNhapHang) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-them-sp-vao-dnh.fxml"));
        fxmlLoader.setController(new FormThemSPVaoDNH(donNhapHang));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(context);
            stage.setTitle("Thêm sản phẩm vào đơn nhập hàng");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean checkInput() {
        boolean result = true;

        if(NumberUtils.isNumber(txtGiamGia.getText().replace(",", "").trim()) == false) {
            result = false;
        }
        else if(NumberUtils.isNumber(txtPhuThu.getText().replace(",", "").trim()) == false) {
            result = false;
        }
        else if(NumberUtils.isNumber(txtVAT.getText().replace(",", "").trim()) == false) {
            result = false;
        }
        else if(NumberUtils.isNumber(txtPhiVanChuyen.getText().replace(",", "").trim()) == false) {
            result = false;
        }
        else if(NumberUtils.isNumber(txtDaThanhToan.getText().replace(",", "").trim()) == false) {
            result = false;
        }

        if(cbxTimKiemNCC.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa chọn nhà cung cấp");
            alert.show();
            result = false;
        }

        return result;
    }

    private void addDonNhapHang(DonNhapHang donNhapHang, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thêm đơn nhập hàng");
        alert.setHeaderText(null);

        try {
            if(donNhapHangService.addDonNhapHang(donNhapHang) && dsDonNhapHang.add(donNhapHang)) {
                alert.setContentText("Thêm đơn nhập hàng thành công");
                alert.show();
                closeStage(mouseEvent);
            }
            else {
                alert.setContentText("Error: Thêm đơn nhập hàng không thành công!");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void updateDonNhapHang(DonNhapHang donNhapHang, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cập nhật đơn nhập hàng");
        alert.setHeaderText(null);

        try {
            if(donNhapHangService.updateDonNhapHang(donNhapHang)) {
                dsDonNhapHang.set(dsDonNhapHang.indexOf(donNhapHang), donNhapHang);

                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Cập nhật thông tin đơn nhập hàng thành công!");
                alert.show();
                closeStage(mouseEvent);
            }
            else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error: Cập nhật thông tin đơn nhập hàng không thành công");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void deleteDonNhapHang(DonNhapHangProperty donNhapHang, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xóa đơn nhập hàng");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đơn nhập hàng này?");

        Optional<ButtonType> option = alert.showAndWait();

        if(option.get() == ButtonType.OK) {
            try {
                if(donNhapHangService.deleteDonNhapHang(donNhapHangGoc) && dsDonNhapHang.remove(donNhapHangGoc)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Xóa đơn nhập hàng thành công!");
                    closeStage(mouseEvent);
                }
                else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Error: Xóa đơn nhập hàng không thành công!");
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
            alert.show();
        }
    }

    private void setInputControlEnable(boolean b) {
        btnThemSanPham.setVisible(b);
        cbxTimKiemNCC.setDisable(!b);
        txtDaThanhToan.setEditable(b);
        txtVAT.setEditable(b);
        txtPhuThu.setEditable(b);
        txtGiamGia.setEditable(b);
        txtGhiChu.setEditable(b);
        txtPhiVanChuyen.setEditable(b);
    }

    private void closeStage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
