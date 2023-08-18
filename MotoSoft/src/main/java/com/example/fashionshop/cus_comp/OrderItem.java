package com.example.fashionshop.cus_comp;

import com.example.fashionshop.MotoSoftApp;
//import com.example.fashionshop.model.ChiTietDonHang;
//import com.example.fashionshop.model.DonHang;
import com.example.fashionshop.utils.StringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import com.example.model.ChiTietDonHang;
import com.example.model.DonHang;

import java.io.IOException;

public class OrderItem extends HBox {

    private int ORDER_DETAIL_VIEW_MAX = 2;

    @FXML private Label lblOrderId;
    @FXML private Label lblOrderDate;
    @FXML private Label lblEmployee;
    @FXML private Label lblCustomer;
    @FXML private Label lblTongThanhToan;
    @FXML private Button btnThanhToan;
    @FXML private Button btnXemChiTiet;
    @FXML private Button btnTraHang;
    @FXML private Button btnPhieuTraHang;
    @FXML private Button btnXoaDonHang;
    @FXML private HBox boxAction;
    @FXML private ListView<ChiTietDonHang> lvChiTietDonHang;

    private DonHang donHang;
    private ObservableList<ChiTietDonHang> orderDetailsView;

    public OrderItem(DonHang donHang) {
        this.donHang = donHang;

        init();
        addControls();
        addEvents();
        loadData();
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("cus_comp_views/order-item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addControls() {
        lvChiTietDonHang.setCellFactory(new Callback<ListView<ChiTietDonHang>, ListCell<ChiTietDonHang>>() {
            @Override
            public ListCell<ChiTietDonHang> call(ListView<ChiTietDonHang> param) {
                return new ListCell<ChiTietDonHang>() {
                    @Override
                    protected void updateItem(ChiTietDonHang orderDetail, boolean empty) {
                        super.updateItem(orderDetail, empty);
                        if(orderDetail != null) {
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

    private void addEvents() {
        btnXemChiTiet.setOnMouseClicked(mouseEvent -> {

        });
    }

    private void loadData() {
        lblOrderId.setText(donHang.getMaDonHang());
        lblOrderDate.setText(donHang.getNgayTaoDonHang().toString());
        lblEmployee.setText(donHang.getNhanVien().getMaNhanVien() + "\t" + donHang.getNhanVien().getHoTen());
        if(donHang.getKhachHang() != null) {
            lblCustomer.setText(donHang.getKhachHang().getHoTen() + "\t" + donHang.getKhachHang().getSdt());
        }
        lblTongThanhToan.setText(StringUtils.formatCurrency(donHang.tinhTongThanhTien()));

        orderDetailsView = FXCollections.observableArrayList(donHang.getChiTietDonHangs().stream().limit(ORDER_DETAIL_VIEW_MAX).toList());

        lvChiTietDonHang.setItems(orderDetailsView);
    }

    public Button getBtnDelete() {
        return btnXoaDonHang;
    }

    public Button getBtnTraHang() {
        return btnTraHang;
    }

    public Button getBtnXemChiTiet() {
        return btnXemChiTiet;
    }

    public Button getBtnThanhToan() {
        return btnThanhToan;
    }

    public Button getBtnPhieuTraHang() {
        return btnPhieuTraHang;
    }

    public HBox getBoxAction() {
        return boxAction;
    }
}
