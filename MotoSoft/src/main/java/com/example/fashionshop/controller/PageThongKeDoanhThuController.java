package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.CardsPane;
import com.example.fashionshop.utils.StringUtils;
import com.example.fashionshop.values.StringValues;
import com.example.model.BaoCaoThongKeDoanhThu;
import com.example.model.LoaiBaoCaoThongKe;
import com.example.service.BaoCaoThongKeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PageThongKeDoanhThuController extends BorderPane {

    @FXML private VBox vbox, boxThongKe;
    @FXML private Button btnTKTheoThoiGian, btnTKTheoNhanVien, btnTKTheoSanPham, btnTKTheoKhachHang;
    @FXML private ComboBox<LoaiBaoCaoThongKe> cbxLoaiBaoCao;
    @FXML private DatePicker dpNgayBatDau, dpNgayKetThuc;
    @FXML private Button btnThongKe, btnXuatExcel, btnInBaoCao, btnTongQuan, btnChiTiet, btnHienThi;
    @FXML private Label lblDoanhThu, lblTongVon, lblTraHang, lblLoiNhuan;
    @FXML private HBox boxLoaiBaoCaoError, boxNgayBatDauError, boxNgayKetThucError;
    private Label lblLoaiBaoCaoError, lblNgayBatDauError, lblNgayKetThucError;
    private CardsPane cardsPane;
    private PageTongQuanTKDT pageTongQuanTKDT;
    private PageChiTietTKDT pageChiTietTKDT;

    private ObservableList<Object[]> chiTietBaoCao;
    private BaoCaoThongKeService baoCaoThongKeService;
    public PageThongKeDoanhThuController() {
        chiTietBaoCao = FXCollections.observableArrayList();

        try {
            Context context = new InitialContext();
            baoCaoThongKeService = (BaoCaoThongKeService) context.lookup(StringValues.SERVER_URL + "BaoCaoThongKeService");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        init();
        addControls();
        addEvents();
        loadData();
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("pages/page-thong-ke-doanh-thu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void addControls() {
        cardsPane = new CardsPane();
        pageTongQuanTKDT = new PageTongQuanTKDT(chiTietBaoCao);
        pageChiTietTKDT = new PageChiTietTKDT(chiTietBaoCao, btnHienThi);
        cardsPane.add(pageTongQuanTKDT.toString(), pageTongQuanTKDT);
        cardsPane.add(pageChiTietTKDT.toString(), pageChiTietTKDT);
        boxThongKe.getChildren().add(cardsPane);
        cardsPane.show(pageTongQuanTKDT.toString());

        cbxLoaiBaoCao.setItems(FXCollections.observableArrayList(LoaiBaoCaoThongKe.values()));
        cbxLoaiBaoCao.getSelectionModel().select(0);

        lblLoaiBaoCaoError = new Label();
        lblLoaiBaoCaoError.setTextFill(Color.RED);
        lblNgayBatDauError = new Label();
        lblNgayBatDauError.setTextFill(Color.RED);
        lblNgayKetThucError = new Label();
        lblNgayKetThucError.setTextFill(Color.RED);
    }

    private void addEvents() {
        btnTongQuan.setOnMousePressed(mouseEvent -> {
            btnTongQuan.setStyle("-fx-background-color:  #eaeaea; -fx-border-color: #eaeaea; -fx-text-fill: #00a6ff;");
            btnChiTiet.setStyle("-fx-background-color: white; -fx-text-fill: #7e7e7e; -fx-border-color: #eaeaea;");

            btnHienThi.setVisible(false);

            cardsPane.show(pageTongQuanTKDT.toString());
        });
        btnChiTiet.setOnMousePressed(mouseEvent -> {
            btnChiTiet.setStyle("-fx-background-color:  #eaeaea; -fx-border-color: #eaeaea; -fx-text-fill: #00a6ff;");
            btnTongQuan.setStyle("-fx-background-color: white; -fx-text-fill: #7e7e7e; -fx-border-color: #eaeaea;");

            btnHienThi.setVisible(true);

            cardsPane.show(pageChiTietTKDT.toString());
        });

        btnThongKe.setOnMousePressed(mouseEvent -> {
            LoaiBaoCaoThongKe loaiBaoCaoThongKe = cbxLoaiBaoCao.getSelectionModel().getSelectedItem();
            LocalDate ngayBatDau = dpNgayBatDau.getValue();
            LocalDate ngayKetThuc = dpNgayKetThuc.getValue();
            try {
                if(checkInput(loaiBaoCaoThongKe, ngayBatDau, ngayKetThuc)) {
                    BaoCaoThongKeDoanhThu baoCaoThongKeDoanhThu = baoCaoThongKeService.getBaoCaoThongKeDoanhThu(ngayBatDau, ngayKetThuc, loaiBaoCaoThongKe);
                    lblDoanhThu.setText(StringUtils.formatCurrency(baoCaoThongKeDoanhThu.getTongDoanhThu()));
                    lblTongVon.setText(StringUtils.formatCurrency(baoCaoThongKeDoanhThu.getTongVon()));
                    lblTraHang.setText(StringUtils.formatCurrency(baoCaoThongKeDoanhThu.getTongTraHang()));
                    lblLoiNhuan.setText(StringUtils.formatCurrency(baoCaoThongKeDoanhThu.getTongLoiNhuan()));

                    List<Object[]> chiTietBaoCao = baoCaoThongKeDoanhThu.getChiTietBaoCao();
                    this.chiTietBaoCao.setAll(chiTietBaoCao);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        cbxLoaiBaoCao.getSelectionModel().selectedItemProperty().addListener((observableValue, loaiBaoCaoThongKe, t1) -> boxLoaiBaoCaoError.getChildren().clear());

        dpNgayBatDau.valueProperty().addListener((observableValue, localDate, t1) -> boxNgayBatDauError.getChildren().clear());

        dpNgayKetThuc.valueProperty().addListener((observableValue, localDate, t1) -> boxNgayKetThucError.getChildren().clear());
    }

    private boolean checkInput(LoaiBaoCaoThongKe loaiBaoCaoThongKe, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        boolean check = true;

        boxLoaiBaoCaoError.getChildren().clear();
        boxNgayBatDauError.getChildren().clear();
        boxNgayKetThucError.getChildren().clear();

        if(loaiBaoCaoThongKe == null) {
            lblLoaiBaoCaoError.setText("Bạn chưa chọn loại báo cáo");
            boxLoaiBaoCaoError.getChildren().add(lblLoaiBaoCaoError);
            check = false;
        }

        if(ngayBatDau == null) {
            lblNgayBatDauError.setText("Bạn chưa chọn ngày bắt đầu");
            boxNgayBatDauError.getChildren().add(lblNgayBatDauError);
            check = false;
        } else if(ngayBatDau.compareTo(LocalDate.now()) > 0) {
            lblNgayBatDauError.setText("Ngày bắt đầu phải trước hoặc là ngày hiện tại");
            boxNgayBatDauError.getChildren().add(lblNgayBatDauError);
            check = false;
        }

        if(ngayKetThuc == null) {
            lblNgayKetThucError.setText("Bạn chưa chọn ngày kết thúc");
            boxNgayKetThucError.getChildren().add(lblNgayKetThucError);
            check = false;
        } else if(ngayBatDau != null && ngayKetThuc.compareTo(ngayBatDau) < 0) {
            lblNgayKetThucError.setText("Ngày kết thúc phải bằng hoặc sau ngày bắt đầu");
            boxNgayKetThucError.getChildren().add(lblNgayKetThucError);
            check = false;
        } else if(ngayKetThuc.compareTo(LocalDate.now()) > 0) {
            lblNgayKetThucError.setText("Ngày kết thúc phải trước hoặc là ngày hiện tại");
            boxNgayKetThucError.getChildren().add(lblNgayKetThucError);
            check = false;
        }

        return check;
    }

    private void loadData() {
        dpNgayBatDau.setValue(LocalDate.now());
        dpNgayKetThuc.setValue(LocalDate.now());
    }

}
