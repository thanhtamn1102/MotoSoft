package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.DanhMucItem;
import com.example.fashionshop.cus_comp.ImageItem;
import com.example.fashionshop.utils.NumberUtils;
import com.example.fashionshop.utils.StringUtils;
import com.example.fashionshop.values.StringValues;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.model.*;
import com.example.service.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FormSanPhamController extends BorderPane {

    public static final int VIEW = 0;
    public static final int ADD = 1;
    public static final int UPDATE = 2;

    @FXML private TextField txtTenSanPham;
    @FXML private ComboBox<DanhMuc> cbxDanhMucSanPham;
    @FXML private TextField txtGiaNhap;
    @FXML private TextField txtGiaBan;
    @FXML private TextArea txtMoTa;
    @FXML private TextField txtSoLuong;
    @FXML private HBox boxHinhAnhSanPham;
    @FXML private Label lblTenSanPhamError, lblGiaNhapError, lblGiaBanError, lblDanhMucError;
    @FXML private VBox boxColor;
    @FXML private ComboBox<MauSac> cbxColor;
    @FXML private ComboBox<ThuongHieu> cbxBrand;
    @FXML private Button btnLuu;
    @FXML private Button btnCapNhat;
    @FXML private Button btnXoa;
    @FXML private Button btnThoat;
    @FXML private VBox btnMinus;
    @FXML private VBox btnPlus;
    @FXML private VBox boxContent;
    @FXML private VBox boxBienThe;
    @FXML private VBox boxSanPhamOption;
    @FXML private Label lblMaSanPham;
    @FXML private Button btnThemHinhAnh;
    @FXML private VBox btnEditThuongHieu, btnEditDanhMuc, btnEditMauSac;
    @FXML private HBox boxDanhMuc;

    @FXML private TextField txtChieuDai, txtChieuRong, txtChieuCao, txtKhoiLuong,
            txtKhoangCachTrucBanhXe, txtDoCaoYen, txtKhoangSangGamXe, txtDungTichBinhXang,
            txtKichCoLopTruoc, txtKichCoLopSau, txtPhuocTruoc, txtPhuocSau,
            txtLoaiDongCo, txtCongSuatToiDa, txtDungTichNhotMay, txtMucTieuThuNhienLieu,
            txtLoaiTruyenDong, txtHeThongKhoiDong, txtMomenCucDai, txtDungTichXyLanh;
    @FXML private HBox boxKichThuocError, boxKhoiLuongError, boxKhoangCachTrucBanhXeError, boxDoCaoYenError,
            boxKhoangSangGamXeError, boxDungTichBinhXangError, boxKichCoLopTruocError, boxKichCoLopSauError,
            boxPhuocTruocError, boxPhuocSauError, boxLoaiDongCoError, boxCongSuatToiDaError,
            boxDungTichNhotMayError, boxMucTieuThuNhienLieuError, boxLoaiTruyenDongError, boxHeThongKhoiDongError,
            boxMomenCucDaiError, boxDungTichXyLanhError;
    private Label lblKichThuocError, lblKhoiLuongError, lblKhoangCachTrucBanhXeError, lblDoCaoYenError, lblKhoangSangGamXeError,
                lblDungTichBinhXangError, lblKichCoLopTruocError, lblKichCoLopSauError, lblPhuocTruocError, lblPhuocSauError, lblLoaiDongCoError,
                lblCongSuatToiDaError, lblDungTichNhotMayError, lblMucTieuThuNhienLieuError, lblLoaiTruyenDongError, lblHeThongKhoiDongError,
                lblMomenCucDaiError, lblDungTichXyLanhError;


    private IntegerProperty soLuong;

    private ObservableList<SanPham> dsSanPham;
    private ObservableList<DanhMuc> dsDanhMuc;
    private ObservableList<MauSac> dsMauSac;
    private ObservableList<KichThuoc> dsKichThuoc;
    private ObservableList<ThuongHieu> dsThuongHieu;
    private SanPham sanPham;
    private int type;
    private Context context;
    private IDService idService;
    private MauSacService mauSacService;
    private DanhMucService danhMucService;
    private ThuongHieuService thuongHieuService;
    private SanPhamService sanPhamService;

    public FormSanPhamController(ObservableList<SanPham> dsSanPham, SanPham sanPham, int type) {
        this.dsSanPham = dsSanPham;
        this.sanPham = sanPham;
        this.type = type;
        this.soLuong = new SimpleIntegerProperty(1);
        this.dsDanhMuc = FXCollections.observableArrayList();
        this.dsKichThuoc = FXCollections.observableArrayList();
        this.dsMauSac = FXCollections.observableArrayList();
        this.dsThuongHieu = FXCollections.observableArrayList();

        try {
            context = new InitialContext();
            idService = (IDService) context.lookup(StringValues.SERVER_URL + "IDService");
            sanPhamService = (SanPhamService) context.lookup(StringValues.SERVER_URL + "SanPhamService");
            danhMucService = (DanhMucService) context.lookup(StringValues.SERVER_URL + "DanhMucService");
            thuongHieuService = (ThuongHieuService) context.lookup(StringValues.SERVER_URL + "ThuongHieuService");
            mauSacService = (MauSacService) context.lookup(StringValues.SERVER_URL + "MauSacService");

            init();
            addControls();
            addEvents();

            loadDanhMucSanPhamToCombobox();
            loadColorToCombobox();
            loadBrandToComboxbox();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if(type == VIEW) {
            setInputEditable(false);
            btnLuu.setVisible(false);
            loadData();
        }
        else if(type == ADD) {
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);
        }
        else if (type == UPDATE) {
            btnCapNhat.setVisible(false);
            btnXoa.setVisible(false);
            loadData();
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-san-pham.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void addControls() {
        lblKichThuocError = new Label();
        lblKichThuocError.setTextFill(Color.RED);
        lblKhoiLuongError = new Label();
        lblKhoangCachTrucBanhXeError = new Label();
        lblDoCaoYenError = new Label();
        lblKhoangSangGamXeError = new Label();
        lblDungTichBinhXangError = new Label();
        lblKichCoLopTruocError = new Label();
        lblKichCoLopSauError = new Label();
        lblPhuocTruocError = new Label();
        lblPhuocSauError = new Label();
        lblLoaiDongCoError = new Label();
        lblCongSuatToiDaError = new Label();
        lblDungTichNhotMayError = new Label();
        lblMucTieuThuNhienLieuError = new Label();
        lblLoaiTruyenDongError = new Label();
        lblHeThongKhoiDongError = new Label();
        lblMomenCucDaiError = new Label();
        lblDungTichXyLanhError = new Label();

        lblKichThuocError.setTextFill(Color.RED);
        lblKhoiLuongError.setTextFill(Color.RED);
        lblKhoangCachTrucBanhXeError.setTextFill(Color.RED);
        lblDoCaoYenError.setTextFill(Color.RED);
        lblKhoangSangGamXeError.setTextFill(Color.RED);
        lblDungTichBinhXangError.setTextFill(Color.RED);
        lblKichCoLopTruocError.setTextFill(Color.RED);
        lblKichCoLopSauError.setTextFill(Color.RED);
        lblPhuocTruocError.setTextFill(Color.RED);
        lblPhuocSauError.setTextFill(Color.RED);
        lblLoaiDongCoError.setTextFill(Color.RED);
        lblCongSuatToiDaError.setTextFill(Color.RED);
        lblDungTichNhotMayError.setTextFill(Color.RED);
        lblMucTieuThuNhienLieuError.setTextFill(Color.RED);
        lblLoaiTruyenDongError.setTextFill(Color.RED);
        lblHeThongKhoiDongError.setTextFill(Color.RED);
        lblMomenCucDaiError.setTextFill(Color.RED);
        lblDungTichXyLanhError.setTextFill(Color.RED);
    }

    private void addEvents() {
        btnThemHinhAnh.setOnMousePressed(mouseEvent -> {
            openFormThemHinhAnh(mouseEvent);
        });

        btnEditMauSac.setOnMousePressed(mouseEvent -> {
            try {
                Stage context = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-ql-mau-sac.fxml"));
                fxmlLoader.setController(new FormQLMauSacController(cbxColor.getItems()));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(context);
                stage.setTitle("Quản lý màu sắc");
                stage.setScene(scene);
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnEditThuongHieu.setOnMousePressed(mouseEvent -> {
            try {
                Stage context = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-ql-thuong-hieu.fxml"));
                fxmlLoader.setController(new FormQLThuongHieu(cbxBrand.getItems()));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(context);
                stage.setTitle("Quản lý thương hiệu");
                stage.setScene(scene);
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnLuu.setOnMousePressed(mouseEvent -> {
            String tenSanPham = txtTenSanPham.getText().trim();
            ThuongHieu thuongHieu = cbxBrand.getSelectionModel().getSelectedItem();
            DanhMuc danhMucSanPham = cbxDanhMucSanPham.getSelectionModel().getSelectedItem();
            String giaNhap = txtGiaNhap.getText().trim().replace(",", "");
            String giaBan = txtGiaBan.getText().trim().replace(",", "");
            String soLuong = txtSoLuong.getText().trim();
            String moTa = txtMoTa.getText();
            MauSac color = cbxColor.getSelectionModel().getSelectedItem();

            String chieuDai = txtChieuDai.getText().trim();
            String chieuRong = txtChieuRong.getText().trim();
            String chieuCao = txtChieuCao.getText().trim();
            String khoiLuong = txtKhoiLuong.getText().trim();
            String khoangCachTrucBanhXe = txtKhoangCachTrucBanhXe.getText().trim();
            String doCaoYen = txtDoCaoYen.getText().trim();
            String khoangSangGamXe = txtKhoangSangGamXe.getText().trim();
            String dungTichBinhXang = txtDungTichBinhXang.getText().trim();
            String kichCoLopTruoc = txtKichCoLopTruoc.getText().trim();
            String kichCoLopSau = txtKichCoLopSau.getText().trim();
            String phuocTruoc = txtPhuocTruoc.getText().trim();
            String phuocSau = txtPhuocSau.getText().trim();
            String loaiDongCo = txtLoaiDongCo.getText().trim();
            String congSuatToiDa = txtCongSuatToiDa.getText().trim();
            String dungTichNhotMay = txtDungTichNhotMay.getText().trim();
            String mucTieuThuNhienLieu = txtMucTieuThuNhienLieu.getText().trim();
            String loaiTruyenDong = txtLoaiTruyenDong.getText().trim();
            String heThongKhoiDong = txtHeThongKhoiDong.getText().trim();
            String momenCucDai = txtMomenCucDai.getText().trim();
            String dungTichXyLanh = txtDungTichXyLanh.getText().trim();

            try {
                if (checkInput(tenSanPham, giaNhap, giaBan, chieuDai, chieuRong, chieuCao, khoiLuong,
                        khoangCachTrucBanhXe, doCaoYen, khoangSangGamXe, dungTichBinhXang, kichCoLopTruoc,
                        kichCoLopSau, phuocTruoc, phuocSau, loaiDongCo, congSuatToiDa, dungTichNhotMay,
                        mucTieuThuNhienLieu, loaiTruyenDong, heThongKhoiDong, momenCucDai, dungTichXyLanh)) {
                    SanPham sanPham = new SanPham(
                            tenSanPham,
                            Double.parseDouble(giaNhap),
                            Double.parseDouble(giaBan),
                            moTa, Integer.parseInt(soLuong),
                            new KichThuoc(Double.parseDouble(chieuDai), Double.parseDouble(chieuRong), Double.parseDouble(chieuCao)),
                            Double.parseDouble(khoiLuong), Double.parseDouble(khoangCachTrucBanhXe), Double.parseDouble(doCaoYen), Double.parseDouble(khoangSangGamXe), Double.parseDouble(dungTichBinhXang),
                            kichCoLopTruoc, kichCoLopSau, phuocTruoc, phuocSau,
                            loaiDongCo, congSuatToiDa, dungTichNhotMay, Double.parseDouble(mucTieuThuNhienLieu),
                            loaiTruyenDong, heThongKhoiDong, momenCucDai, Double.parseDouble(dungTichXyLanh),
                            color, thuongHieu);

                    Set<String> dsImageAddress = boxHinhAnhSanPham
                            .getChildren()
                            .stream()
                            .map(node -> ((ImageItem)node).getImageAddress())
                            .collect(Collectors.toSet());
                    sanPham.setHinhAnhs(dsImageAddress);

                    Set<DanhMuc> dsDanhMuc = boxDanhMuc
                            .getChildren()
                            .stream()
                            .map(node -> ((DanhMucItem)node).getDanhMuc())
                            .collect(Collectors.toSet());
                    sanPham.setDanhMucs(dsDanhMuc);

                    if (type == ADD) {
                        sanPham.setMaSanPham(idService.createMaSanPham());
                        if (addSanPham(sanPham))
                            closeStage(mouseEvent);
                    } else if (type == UPDATE) {
                        sanPham.setMaSanPham(this.sanPham.getMaSanPham());
                        updateSanPham(sanPham, mouseEvent);
                    }
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
        });

        btnCapNhat.setOnMouseClicked(mouseEvent -> {
            if(btnCapNhat.getText().equalsIgnoreCase("Cập nhật")) {
                btnCapNhat.setText("Hủy cập nhật"); btnCapNhat.setStyle("-fx-background-color: #C4C4C4");
                btnLuu.setVisible(true);
                btnXoa.setVisible(false);
                setInputEditable(true);
                type = UPDATE;
            }
            else {
                btnCapNhat.setText("Cập nhật"); btnCapNhat.setStyle("-fx-background-color: #0C75F5");
                btnLuu.setVisible(false);
                btnXoa.setVisible(true);
                setInputEditable(false);
                type = VIEW;
                loadData();
            }
        });

        btnXoa.setOnMouseClicked(mouseEvent -> {
            try {
                deleteSanPham(sanPham, mouseEvent);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        btnThoat.setOnMousePressed(mouseEvent -> {
            closeStage(mouseEvent);
        });

        txtTenSanPham.textProperty().addListener((observable, oldValue, newValue) -> lblTenSanPhamError.setText(""));

        txtGiaBan.textProperty().addListener((observable, oldValue, newValue) -> {
            lblGiaBanError.setText("");

            String numberString = newValue.replace(",", "");
            txtGiaBan.setText(StringUtils.formatCurrency(Double.parseDouble(numberString)));
        });

        txtGiaBan.setOnKeyPressed(keyEvent -> {
            String keyChar = keyEvent.getCode().getChar();
            if(keyChar.matches("[0-9]") || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                txtGiaBan.setEditable(true);
            }
            else {
                txtGiaBan.setEditable(false);
            }
        });

        txtGiaNhap.textProperty().addListener((observable, oldValue, newValue) -> {
            lblGiaNhapError.setText("");

            String numberString = newValue.replace(",", "");
            txtGiaNhap.setText(StringUtils.formatCurrency(Double.parseDouble(numberString)));
        });

        txtGiaNhap.setOnKeyPressed(keyEvent -> {
            String keyChar = keyEvent.getCode().getChar();
            if(keyChar.matches("[0-9]") || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                txtGiaNhap.setEditable(true);
            }
            else {
                txtGiaNhap.setEditable(false);
            }
        });

        btnMinus.setOnMousePressed(mouseEvent -> {
            if(soLuong.get() > 1)
                soLuong.set(soLuong.get() - 1);
        });

        btnPlus.setOnMousePressed(mouseEvent -> {
            soLuong.set(soLuong.get() + 1);
        });

        soLuong.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                txtSoLuong.setText(newValue.toString());
            }
        });

        txtSoLuong.setOnKeyPressed(keyEvent -> {
            String keyChar = keyEvent.getCode().getChar();
            if(keyChar.matches("[0-9]") || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                txtSoLuong.setEditable(true);
            }
            else {
                txtSoLuong.setEditable(false);
            }
        });

        cbxColor.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MauSac>() {
            @Override
            public void changed(ObservableValue<? extends MauSac> observableValue, MauSac mauSac, MauSac newValue) {
                boxColor.setStyle("-fx-border-color: #2EC3E9; -fx-border-width: 2; -fx-background-radius: 3; -fx-border-radius: 2;" +
                        "-fx-background-color: " + newValue.getCode());
            }
        });

        cbxDanhMucSanPham.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DanhMuc>() {
            @Override
            public void changed(ObservableValue<? extends DanhMuc> observableValue, DanhMuc danhMuc, DanhMuc danhMucNew) {
                if(danhMucNew != null) {
                    lblDanhMucError.setText("");

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            DanhMucItem danhMucItem = new DanhMucItem(danhMucNew);
                            cbxDanhMucSanPham.getSelectionModel().select(null);

                            if(boxDanhMuc.getChildren().contains(danhMucItem)) {
                                lblDanhMucError.setText("Danh mục được chọn đã tồn tại trong danh sách");
                                return;
                            } else if(boxDanhMuc.getChildren().size() == 5) {
                                lblDanhMucError.setText("Mỗi sản phẩm thuộc tối đa 5 danh mục");
                                return;
                            }

                            danhMucItem.getBtnDelete().setOnMousePressed(mouseEvent -> {
                                boxDanhMuc.getChildren().remove(danhMucItem);
                            });

                            boxDanhMuc.getChildren().add(danhMucItem);
                            cbxDanhMucSanPham.getSelectionModel().select(null);
                        }
                    });
                }
            }
        });
    }


    private void closeStage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void loadData() {
        if(sanPham != null) {
            lblMaSanPham.setText(sanPham.getMaSanPham());
            txtTenSanPham.setText(sanPham.getTenSanPham());
            txtGiaNhap.setText(Double.toString(sanPham.getGiaNhap()));
            txtGiaBan.setText(Double.toString(sanPham.getGiaBan()));
            txtMoTa.setText(sanPham.getMoTa());
            soLuong.set(sanPham.getSoLuong());
            txtSoLuong.setText(Integer.toString(soLuong.get()));

            txtChieuDai.setText(String.valueOf(sanPham.getKichThuoc().getDai()));
            txtChieuRong.setText(String.valueOf(sanPham.getKichThuoc().getRong()));
            txtChieuCao.setText(String.valueOf(sanPham.getKichThuoc().getCao()));
            txtKhoiLuong.setText(String.valueOf(sanPham.getKhoiLuong()));
            txtKhoangCachTrucBanhXe.setText(String.valueOf(sanPham.getKhoangCachTrucBanhXe()));
            txtDoCaoYen.setText(String.valueOf(sanPham.getDoCaoYen()));
            txtKhoangSangGamXe.setText(String.valueOf(sanPham.getKhoangSangGamXe()));
            txtDungTichBinhXang.setText(String.valueOf(sanPham.getDungTichBinhXang()));
            txtKichCoLopTruoc.setText(sanPham.getKichCoLopTruoc());
            txtKichCoLopSau.setText(sanPham.getKichCoLopSau());
            txtPhuocTruoc.setText(sanPham.getPhuocSau());
            txtPhuocSau.setText(sanPham.getPhuocSau());
            txtLoaiDongCo.setText(sanPham.getLoaiDongCo());
            txtCongSuatToiDa.setText(sanPham.getCongSuatToiDa());
            txtDungTichNhotMay.setText(sanPham.getDungTichNhotMay());
            txtMucTieuThuNhienLieu.setText(String.valueOf(sanPham.getMucTieuThuNhienLieu()));
            txtLoaiTruyenDong.setText(sanPham.getLoaiTruyenDong());
            txtHeThongKhoiDong.setText(sanPham.getHeThongKhoiDong());
            txtMomenCucDai.setText(sanPham.getMomentCucDai());
            txtDungTichXyLanh.setText(String.valueOf(sanPham.getDungTichXyLanh()));

            boxHinhAnhSanPham.getChildren().clear();
            if(sanPham.getHinhAnhs() != null && sanPham.getHinhAnhs().size() > 0) {
                for(String imageAddress : sanPham.getHinhAnhs()) {
                    ImageItem imageItem = new ImageItem(imageAddress);

                    imageItem.getBtnDelete().setVisible(false);

                    imageItem.getBtnDelete().setOnMousePressed(mouseEvent -> {
                        boxHinhAnhSanPham.getChildren().remove(imageItem);
                    });

                    boxHinhAnhSanPham.getChildren().add(imageItem);
                }
            }

            boxDanhMuc.getChildren().clear();
            if(sanPham.getDanhMucs() != null && sanPham.getDanhMucs().size() > 0) {
                for(DanhMuc danhMuc : sanPham.getDanhMucs()) {
                    DanhMucItem danhMucItem = new DanhMucItem(danhMuc);

                    danhMucItem.getBtnDelete().setDisable(true);

                    danhMucItem.getBtnDelete().setOnMousePressed(mouseEvent -> {
                        boxDanhMuc.getChildren().remove(danhMucItem);
                    });

                    boxDanhMuc.getChildren().add(danhMucItem);
                }
            }
        }
    }

    private void loadColorToCombobox() throws RemoteException {
        dsMauSac.setAll(mauSacService.getAllMauSac());
        cbxColor.setItems(dsMauSac);

        if(sanPham == null)
            cbxColor.getSelectionModel().select(0);
        else {
            cbxColor.getSelectionModel().select(sanPham.getMauSac());
            boxColor.setStyle("-fx-background-color: " + sanPham.getMauSac().getCode() + "; -fx-border-color:  #2EC3E9; -fx-border-width: 2; -fx-background-radius: 3; -fx-border-radius: 2;");
        }
    }

    private void loadDanhMucSanPhamToCombobox() throws RemoteException {
        dsDanhMuc.setAll(danhMucService.getAllCategory().stream().sorted(Comparator.comparing(DanhMuc::getTenDanhMuc)).collect(Collectors.toList()));
        cbxDanhMucSanPham.setItems(dsDanhMuc);
    }

    private void loadBrandToComboxbox() throws RemoteException {
        dsThuongHieu.setAll(thuongHieuService.getAllThuongHieu());
        cbxBrand.setItems(dsThuongHieu);

        if(sanPham == null)
            cbxBrand.getSelectionModel().select(0);
        else
            cbxBrand.getSelectionModel().select(sanPham.getThuongHieu());
    }

    private void setInputEditable(boolean b) {
        txtTenSanPham.setEditable(b);
        cbxDanhMucSanPham.setDisable(!b);
        txtGiaNhap.setEditable(b);
        txtGiaBan.setEditable(b);
        txtMoTa.setEditable(b);
        txtSoLuong.setEditable(b);
        btnPlus.setDisable(!b);
        btnMinus.setDisable(!b);
        cbxColor.setDisable(!b);
        cbxBrand.setDisable(!b);

        txtChieuDai.setEditable(b);
        txtChieuRong.setEditable(b);
        txtChieuCao.setEditable(b);
        txtKhoiLuong.setEditable(b);
        txtKhoangCachTrucBanhXe.setEditable(b);
        txtDoCaoYen.setEditable(b);
        txtKhoangSangGamXe.setEditable(b);
        txtDungTichBinhXang.setEditable(b);
        txtKichCoLopTruoc.setEditable(b);
        txtKichCoLopSau.setEditable(b);
        txtPhuocTruoc.setEditable(b);
        txtPhuocSau.setEditable(b);
        txtLoaiDongCo.setEditable(b);
        txtCongSuatToiDa.setEditable(b);
        txtDungTichNhotMay.setEditable(b);
        txtMucTieuThuNhienLieu.setEditable(b);
        txtLoaiTruyenDong.setEditable(b);
        txtHeThongKhoiDong.setEditable(b);
        txtMomenCucDai.setEditable(b);
        txtDungTichXyLanh.setEditable(b);

        btnThemHinhAnh.setVisible(b);

        boxHinhAnhSanPham.getChildren().forEach(node -> {
            ((ImageItem)node).getBtnDelete().setVisible(b);
        });
        boxDanhMuc.getChildren().forEach(node -> {
            ((DanhMucItem)node).getBtnDelete().setDisable(!b);
        });
    }

    private boolean checkInput(String tenSanPham, String giaNhap, String giaBan,
                               String chieuDai, String chieuRong, String chieuCao, String khoiLuong,
                               String khoangCachTrucBanhXe, String doCaoYen, String khoangSangGanXe, String dungTichBinhXang,
                               String kichCoLopTruoc, String kichCoLopSau, String phuocTruoc, String phuocSau,
                               String loaiDongCo, String congSuatToiDa, String dungTichNhotMay, String mucTieuThuNhienLieu,
                               String loaiTruyenDong, String heThongKhoiDong, String momenCucDai, String dungTichXyLanh) {
        setLabelErrorEmpty();

        boolean check = true;

        if(tenSanPham.isEmpty()) {
            lblTenSanPhamError.setText("Tên sản phẩm không được để trống");
            check = false;
        }

        if(boxDanhMuc.getChildren().size() <= 0) {
            lblDanhMucError.setText("Sản phẩm phải thuộc ít nhất một danh mục");
            check = false;
        }

        if(giaNhap.isEmpty()) {
            lblGiaNhapError.setText("Giá nhập không được để trống");
            check = false;
        }
        else if(!NumberUtils.isNumber(giaNhap)) {
            lblGiaNhapError.setText("Giá nhập phải là số");
            check = false;
        }
        else if(Double.parseDouble(giaNhap) <= 0) {
            lblGiaNhapError.setText("Giá nhập phải > 0");
            check = false;
        }

        if(giaBan.isEmpty()) {
            lblGiaBanError.setText("Giá bán không được để trống");
            check = false;
        }
        else if(!NumberUtils.isNumber(giaBan)) {
            lblGiaBanError.setText("Giá bán phải là số");
            check = false;
        }
        else if(Double.parseDouble(giaBan) <= 0) {
            lblGiaBanError.setText("Giá bán phải > 0");
            check = false;
        }

        if(chieuDai.isEmpty()) {
            lblKichThuocError.setText("Chiều dài không được để trống");
            boxKichThuocError.getChildren().add(lblKichThuocError);
            check = false;
        } else if(!NumberUtils.isNumber(chieuDai)) {
            lblKichThuocError.setText("Chiều dài phải là số");
            boxKichThuocError.getChildren().add(lblKichThuocError);
            check = false;
        } else if(Double.parseDouble(chieuDai) <= 0) {
            lblKichThuocError.setText("Chiều dài phải > 0");
            boxKichThuocError.getChildren().add(lblKichThuocError);
            check = false;
        } else if(chieuRong.isEmpty()) {
            lblKichThuocError.setText("Chiều rộng không được để trống");
            boxKichThuocError.getChildren().add(lblKichThuocError);
            check = false;
        } else if(!NumberUtils.isNumber(chieuRong)) {
            lblKichThuocError.setText("Chiều rộng phải là số");
            boxKichThuocError.getChildren().add(lblKichThuocError);
            check = false;
        } else if(Double.parseDouble(chieuRong) <= 0) {
            lblKichThuocError.setText("Chiều rộng phải > 0");
            boxKichThuocError.getChildren().add(lblKichThuocError);
            check = false;
        } else if(chieuCao.isEmpty()) {
            lblKichThuocError.setText("Chiều cao không được để trống");
            boxKichThuocError.getChildren().add(lblKichThuocError);
            check = false;
        } else if(!NumberUtils.isNumber(chieuCao)) {
            lblKichThuocError.setText("Chiều cao phải là số");
            boxKichThuocError.getChildren().add(lblKichThuocError);
            check = false;
        } else if(Double.parseDouble(chieuCao) <= 0) {
            lblKichThuocError.setText("Chiều cao phải > 0");
            boxKichThuocError.getChildren().add(lblKichThuocError);
            check = false;
        }


        if(khoiLuong.isEmpty()) {
            lblKhoiLuongError.setText("Khối lượng không được để trống");
            boxKhoiLuongError.getChildren().add(lblKhoiLuongError);
            check = false;
        } else if(!NumberUtils.isNumber(khoiLuong)) {
            lblKhoiLuongError.setText("Khối lượng phải là số");
            boxKhoiLuongError.getChildren().add(lblKhoiLuongError);
            check = false;
        } else if(Double.parseDouble(khoiLuong) <= 0) {
            lblKhoiLuongError.setText("Khối lượng phải > 0");
            boxKhoiLuongError.getChildren().add(lblKhoiLuongError);
            check = false;
        }


        if(khoangCachTrucBanhXe.isEmpty()) {
            lblKhoangCachTrucBanhXeError.setText("Khoảng cách trục bánh xe không được để trống");
            boxKhoangCachTrucBanhXeError.getChildren().add(lblKhoangCachTrucBanhXeError);
            check = false;
        } else if(!NumberUtils.isNumber(khoangCachTrucBanhXe)) {
            lblKhoangCachTrucBanhXeError.setText("Khoảng cách trục bánh xe phải là số");
            boxKhoangCachTrucBanhXeError.getChildren().add(lblKhoangCachTrucBanhXeError);
            check = false;
        } else if(Double.parseDouble(khoangCachTrucBanhXe) <= 0) {
            lblKhoangCachTrucBanhXeError.setText("Khoảng cách trục bánh xe phải > 0");
            boxKhoangCachTrucBanhXeError.getChildren().add(lblKhoangCachTrucBanhXeError);
            check = false;
        }


        if(doCaoYen.isEmpty()) {
            lblDoCaoYenError.setText("Độ cao yên không được để trống");
            boxDoCaoYenError.getChildren().add(lblDoCaoYenError);
            check = false;
        } else if(!NumberUtils.isNumber(doCaoYen)) {
            lblDoCaoYenError.setText("Độ cao yên phải là số");
            boxDoCaoYenError.getChildren().add(lblDoCaoYenError);
            check = false;
        } else if(Double.parseDouble(doCaoYen) <= 0) {
            lblDoCaoYenError.setText("Độ cao yên phải > 0");
            boxDoCaoYenError.getChildren().add(lblDoCaoYenError);
            check = false;
        }


        if(khoangSangGanXe.isEmpty()) {
            lblKhoangSangGamXeError.setText("Khoảng sáng gầm xe không được để trống");
            boxKhoangSangGamXeError.getChildren().add(lblKhoangSangGamXeError);
            check = false;
        } else if(!NumberUtils.isNumber(khoangSangGanXe)) {
            lblKhoangSangGamXeError.setText("Khoảng sáng gầm xe phải là số");
            boxKhoangSangGamXeError.getChildren().add(lblKhoangSangGamXeError);
            check = false;
        } else if(Double.parseDouble(khoangSangGanXe) <= 0) {
            lblKhoangSangGamXeError.setText("Khoảng sáng gầm xe phải > 0");
            boxKhoangSangGamXeError.getChildren().add(lblKhoangSangGamXeError);
            check = false;
        }


        if(dungTichBinhXang.isEmpty()) {
            lblDungTichBinhXangError.setText("Dung tích bình xăng gầm xe không được để trống");
            boxDungTichBinhXangError.getChildren().add(lblDungTichBinhXangError);
            check = false;
        } else if(!NumberUtils.isNumber(dungTichBinhXang)) {
            lblDungTichBinhXangError.setText("Dung tích bình xăng gầm xe phải là số");
            boxDungTichBinhXangError.getChildren().add(lblDungTichBinhXangError);
            check = false;
        } else if(Double.parseDouble(dungTichBinhXang) <= 0) {
            lblDungTichBinhXangError.setText("Dung tích bình xăng gầm xe phải > 0");
            boxDungTichBinhXangError.getChildren().add(lblDungTichBinhXangError);
            check = false;
        }


        if(kichCoLopTruoc.isEmpty()) {
            lblKichCoLopTruocError.setText("Kích cỡ lốp trước không được để trống");
            boxKichCoLopTruocError.getChildren().add(lblKichCoLopTruocError);
            check = false;
        }


        if(kichCoLopSau.isEmpty()) {
            lblKichCoLopSauError.setText("Kích cỡ lốp sau không được để trống");
            boxKichCoLopSauError.getChildren().add(lblKichCoLopSauError);
            check = false;
        }


        if(phuocTruoc.isEmpty()) {
            lblPhuocTruocError.setText("Phuộc trước không được để trống");
            boxPhuocTruocError.getChildren().add(lblPhuocTruocError);
            check = false;
        }


        if(phuocSau.isEmpty()) {
            lblPhuocSauError.setText("Phuộc sau không được để trống");
            boxPhuocSauError.getChildren().add(lblPhuocSauError);
            check = false;
        }


        if(loaiDongCo.isEmpty()) {
            lblLoaiDongCoError.setText("Loại động cơ không được để trống");
            boxLoaiDongCoError.getChildren().add(lblLoaiDongCoError);
            check = false;
        }


        if(congSuatToiDa.isEmpty()) {
            lblCongSuatToiDaError.setText("Công suất tối đa không được để trống");
            boxCongSuatToiDaError.getChildren().add(lblCongSuatToiDaError);
            check = false;
        }


        if(dungTichNhotMay.isEmpty()) {
            lblDungTichNhotMayError.setText("Dung tích nhớt máy không được để trống");
            boxDungTichNhotMayError.getChildren().add(lblDungTichNhotMayError);
            check = false;
        }


        if(mucTieuThuNhienLieu.isEmpty()) {
            lblMucTieuThuNhienLieuError.setText("Mức tiêu thụ nhiên liệu không được để trống");
            boxMucTieuThuNhienLieuError.getChildren().add(lblMucTieuThuNhienLieuError);
            check = false;
        }


        if(loaiTruyenDong.isEmpty()) {
            lblLoaiTruyenDongError.setText("Loại truyền động không được để trống");
            boxLoaiTruyenDongError.getChildren().add(lblLoaiTruyenDongError);
            check = false;
        }


        if(heThongKhoiDong.isEmpty()) {
            lblHeThongKhoiDongError.setText("Hệ thống khởi động không được để trống");
            boxHeThongKhoiDongError.getChildren().add(lblHeThongKhoiDongError);
            check = false;
        }


        if(momenCucDai.isEmpty()) {
            lblMomenCucDaiError.setText("Momen cực đại không được để trống");
            boxMomenCucDaiError.getChildren().add(lblMomenCucDaiError);
            check = false;
        }


        if(dungTichXyLanh.isEmpty()) {
            lblDungTichXyLanhError.setText("Dung tích xy lanh gầm xe không được để trống");
            boxDungTichXyLanhError.getChildren().add(lblDungTichXyLanhError);
            check = false;
        } else if(!NumberUtils.isNumber(dungTichXyLanh)) {
            lblDungTichXyLanhError.setText("Dung tích xy lanh gầm xe phải là số");
            boxDungTichXyLanhError.getChildren().add(lblDungTichXyLanhError);
            check = false;
        } else if(Double.parseDouble(dungTichXyLanh) <= 0) {
            lblDungTichXyLanhError.setText("Dung tích xy lanh gầm xe phải > 0");
            boxDungTichXyLanhError.getChildren().add(lblDungTichXyLanhError);
            check = false;
        }

        return check;
    }

    private void setLabelErrorEmpty() {
        lblTenSanPhamError.setText("");
        lblGiaNhapError.setText("");
        lblGiaBanError.setText("");

        boxKichThuocError.getChildren().clear();
        boxKhoiLuongError.getChildren().clear();
        boxKhoangCachTrucBanhXeError.getChildren().clear();
        boxDoCaoYenError.getChildren().clear();
        boxKhoangSangGamXeError.getChildren().clear();
        boxDungTichBinhXangError.getChildren().clear();
        boxKichCoLopSauError.getChildren().clear();
        boxKichCoLopSauError.getChildren().clear();
        boxPhuocTruocError.getChildren().clear();
        boxPhuocSauError.getChildren().clear();
        boxLoaiDongCoError.getChildren().clear();
        boxCongSuatToiDaError.getChildren().clear();
        boxDungTichNhotMayError.getChildren().clear();
        boxLoaiTruyenDongError.getChildren().clear();
        boxHeThongKhoiDongError.getChildren().clear();
        boxMomenCucDaiError.getChildren().clear();
        boxDungTichXyLanhError.getChildren().clear();
    }

    private boolean addSanPham(SanPham sanPham) throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thêm sản phẩm");
        alert.setHeaderText(null);

        if (sanPhamService.addSanPham(sanPham) && dsSanPham.add(sanPham)) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Thêm sản phẩm thành công");
            alert.show();
            return true;
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Thêm sản phẩm không thành công");
            alert.show();
            return false;
        }

    }

    private void deleteSanPham(SanPham sanPham, MouseEvent mouseEvent) throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete SanPham");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa sản phẩm này không?");

        Optional<ButtonType> optional = alert.showAndWait();

        if (optional.get() == ButtonType.OK) {
            if (sanPhamService.removeSanPham(sanPham) && dsSanPham.remove(sanPham)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Xóa sản phẩm thành công");
                closeStage(mouseEvent);
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error: Xóa sản phẩm không thành công");
            }
            alert.show();
        }
    }

    private void updateSanPham(SanPham sanPham, MouseEvent mouseEvent) throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cập nhật thông tin sản phẩm");
        alert.setHeaderText(null);

        if (sanPhamService.updateSanPham(sanPham)) {
            dsSanPham.set(dsSanPham.indexOf(sanPham), sanPham);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Cập nhật thông tin sản phẩm thành công!");
            alert.show();
            closeStage(mouseEvent);
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Error: Cập nhật thông tin sản phẩm không thành công");
            alert.show();
        }
    }

    private void openFormThemHinhAnh(MouseEvent mouseEvent) {
        try {
            Stage context = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-them-hinh-anh.fxml"));
            fxmlLoader.setController(new FormThemHinhAnh(boxHinhAnhSanPham));
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
}
