package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.OrderDetailItemOrderView;
import com.example.fashionshop.utils.StringUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.util.Callback;
import com.example.model.ChiTietDonHang;
import com.example.model.DonHang;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FormDonHangController extends Parent implements Initializable {

    @FXML private Label lblMaDonHang, lblNgayTaoDon, lblNhanVien;
    @FXML private Label lblHoTenKhachHang, lblSoDienThoai, lblDiaChi;
    @FXML private Label lblTongTien, lblGiamGia, lblPhuThu, lblVAT, lblPhiVanChuyen, lblTongThanhToan;
    @FXML private TextArea txtGhiChu;
    @FXML private ListView<ChiTietDonHang> lvChiTietDonHang;

    private DonHang donHang;

    public FormDonHangController(DonHang donHang){
        this.donHang = donHang;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        init();
        addControls();

        addEvents();
        loadData();
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

    }

    private void loadData(){
        if(donHang != null){;
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
    }


}
