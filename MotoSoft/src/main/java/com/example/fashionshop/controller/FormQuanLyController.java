package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.CardsPane;
import com.example.fashionshop.cus_comp.Menu;
import com.example.fashionshop.cus_comp.MenuItem;
//import com.example.fashionshop.model.TaiKhoan;
//import com.example.fashionshop.model.VaiTroNhomQuyen;
import com.example.fashionshop.values.Icons;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.model.TaiKhoan;
import com.example.model.VaiTroNhomQuyen;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FormQuanLyController implements Initializable {
    @FXML private BorderPane panelMain;
    @FXML private VBox panelMenu;
    @FXML private StackPane paneContent;
    @FXML private Label pageTitle, lblMaNhanVien, lblTenNhanVien;
    @FXML private Region titleIcon;
    @FXML private CardsPane cardsPane;
    @FXML private HBox boxTaiKhoan;
    @FXML private VBox boxThongBao, boxDangXuat;

    private List<MenuItem> menuItems;
    private MenuItem currentMenuItemSelection;
    private Menu menu;
    private TaiKhoan taiKhoan;

    private List<String> permissionGroupList = new ArrayList<>();
    public FormQuanLyController(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addControls();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        addEvents();
        loadData();
    }

    private void addControls() throws RemoteException {
        cardsPane = new CardsPane();
        cardsPane.add("pageDashboard", new PageDashboardController());
        cardsPane.add("pageHoaDon", new PageHoaDonController(taiKhoan));
        cardsPane.add("pageDanhMuc", new PageDanhMucSanPhamController());
        cardsPane.add("pageSanPham", new PageSanPhamController());
        cardsPane.add("pageNhanVien", new PageNhanVienController());
        cardsPane.add("pageKhachHang", new PageKhachHangController());
        cardsPane.add("pageThongKeDoanhThu", new PageThongKeDoanhThuController());
        cardsPane.add("pageCaiDat", new PageCaiDatController());
        cardsPane.add("pageVaiTro", new PageVaiTroController());
        cardsPane.add("pageChucVu", new PageChucVuController());
        cardsPane.add("pageNhaCungCap", new PageNhaCungCapController());
        cardsPane.add("pageKiemKe", new PageKiemKeController(taiKhoan));
        cardsPane.add("pageTaiKhoanNhanVien", new PageTaiKhoanNhanVienController());
        cardsPane.add("pageDonNhapHang", new PageDonNhapHangController(taiKhoan));
        cardsPane.add("pagePhieuNhapHang", new PagePhieuNhapHangController());
        paneContent.getChildren().add(cardsPane);

        try {
            MenuItem menuBanHang = new MenuItem(Icons.SUB_MENU_ICON, "Bán hàng", "SELL_PERMISSION", "");
            menuBanHang.setOnMouseClicked(mouseEvent -> {
                try {
                    changeSceneBanHang(mouseEvent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            MenuItem menuDonNhapHang = new MenuItem(Icons.SUB_MENU_ICON, "Đơn nhập hàng", "", "pageDonNhapHang");
            MenuItem menuPhieuNhapHang = new MenuItem(Icons.SUB_MENU_ICON, "Phiếu nhập hàng", "", "pagePhieuNhapHang");
            MenuItem menuKiemKe = new MenuItem(Icons.SUB_MENU_ICON, "Kiểm kê", "INVENTORY_PERMISSION", "pageKiemKe");
            MenuItem menuQLCuaHang = new MenuItem(Icons.STORE_ICON, "Quản lý cửa hàng", "STORE_MANAGER_PERMISSION", "");
            menuQLCuaHang.getSubMenu().addAll(menuDonNhapHang, menuPhieuNhapHang, menuKiemKe);

            MenuItem menuDonHang = new MenuItem(Icons.SUB_MENU_ICON, "Hóa đơn", "BILL_PERMISSION", "pageHoaDon");
            MenuItem menuSell = new MenuItem(Icons.CART_SHOPPING, "Bán hàng", "SELL_SUPER_PERMISSION", "");
            menuSell.getSubMenu().addAll(menuDonHang, menuBanHang);

            MenuItem menuDanhMuc = new MenuItem(Icons.SUB_MENU_ICON, "Danh mục sản phẩm", "CATEGORY_PERMISSION", "pageDanhMuc");
            MenuItem menuSanPham = new MenuItem(Icons.SUB_MENU_ICON, "Sản phẩm", "PRODUCT_PERMISSION", "pageSanPham");
            MenuItem menuProduct = new MenuItem(Icons.SHIRT, "Quản lý sản phẩm", "PRODUCT_SUPER_PERMISSION", "");
            menuProduct.getSubMenu().addAll(menuDanhMuc, menuSanPham);

            MenuItem menuKhachHang = new MenuItem(Icons.PERSON, "Quản lý khách hàng", "CUSTOMER_PERMISSION", "pageKhachHang");
            MenuItem menuNhaCungCap = new MenuItem(Icons.HANDSAKE, "Quản lý nhà cung cấp", "SUPPLIER_PERMISSION", "pageNhaCungCap");

            MenuItem menuChucVu = new MenuItem(Icons.SUB_MENU_ICON, "Chức vụ", "POSITION_PERMISISON", "pageChucVu");
            MenuItem menuNhanVien = new MenuItem(Icons.SUB_MENU_ICON, "Nhân viên", "EMPLOYEES_PERMISSION", "pageNhanVien");
            MenuItem menuTaiKhoanNV = new MenuItem(Icons.SUB_MENU_ICON, "Tài khoản nhân viên", "", "pageTaiKhoanNhanVien");
            MenuItem menuEmployee = new MenuItem(Icons.USER, "Quản lý nhân viên", "EMPLOYEES_SUPER_PERMISSION", "");
            menuEmployee.getSubMenu().addAll(menuChucVu, menuNhanVien, menuTaiKhoanNV);

            MenuItem menuThongKeDoanhThu = new MenuItem(Icons.SUB_MENU_ICON, "Thống kê doanh thu", "REVENUE_STATISTICS_PERMISSION", "pageThongKeDoanhThu");
            MenuItem menuBaoCaoThongKe = new MenuItem(Icons.CHART_LINE, "Báo cáo thống kê", "STATISTIC_PERMISSION", "");
            menuBaoCaoThongKe.getSubMenu().addAll(menuThongKeDoanhThu);


            menuItems = new ArrayList<>();
            menuItems.add(new MenuItem(Icons.HOME, "Trang chủ", "DASHBOARD", "pageDashboard"));
            menuItems.add(menuQLCuaHang);
            menuItems.add(menuSell);
            menuItems.add(menuProduct);
            menuItems.add(menuNhaCungCap);
            menuItems.add(menuKhachHang);
            menuItems.add(menuEmployee);
            menuItems.add(menuBaoCaoThongKe);
            menuItems.add(new MenuItem(Icons.WRENCH, "Cài đặt", "SETTING_PERMISSION", "pageCaiDat"));
            menuItems.add(new MenuItem(Icons.SHIELD_ICON, "Thiết lập vai trò", "ROLE_SETTING_PERMISSION", "pageVaiTro"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        menu = new Menu(menuItems);
        panelMenu.getChildren().add(menu);

        for (VaiTroNhomQuyen rolePermissionGroup : taiKhoan.getVaiTro().getDsNhomQuyen()) {
            if(rolePermissionGroup.getTrangThai()) {
                permissionGroupList.add(rolePermissionGroup.getNhomQuyen().getMaNhomQuyen());
            }
        }
        menu.show(permissionGroupList.toArray(new String[0]));

        menu.setCurrentMenuItemSelection(menuItems.get(0));
        try {
            MenuItem currentMenuItemSelection = menu.getCurrentMenuItemSelection();
            cardsPane.show(currentMenuItemSelection.getUrl());
            pageTitle.setText(currentMenuItemSelection.getText());
            titleIcon.setShape(currentMenuItemSelection.getIcon());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        for (MenuItem item : menuItems) {
            if(item.getSubMenu().size() > 0) {
                for (MenuItem subMenuItem : item.getSubMenu()) {
                    if(subMenuItem.getUrl() != null && !subMenuItem.getUrl().isEmpty()) {
                        subMenuItem.setOnMouseClicked(mouseEvent -> {
                            cardsPane.show(subMenuItem.getUrl());
                            pageTitle.setText(subMenuItem.getText());
                            titleIcon.setShape(subMenuItem.getIcon());
                        });
                    }
                }
            } else {
                if(item.getUrl() != null && !item.getUrl().isEmpty()) {
                    item.setOnMouseClicked(mouseEvent -> {
                        cardsPane.show(item.getUrl());
                        pageTitle.setText(item.getText());
                        titleIcon.setShape(item.getIcon());
                    });
                }
            }
        }
    }

    private void addEvents() {
        boxTaiKhoan.setOnMousePressed(mouseEvent -> {
            openFormThongTinTaiKhoan(taiKhoan, mouseEvent);
        });
        boxThongBao.setOnMousePressed(mouseEvent -> {
            Stage stage = new Stage();
            openFormThongBao(stage);
        });
        boxDangXuat.setOnMousePressed(mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Bạn có chắn chắn muốn đăng xuất?");
            alert.setHeaderText(null);
            Optional<ButtonType> optional = alert.showAndWait();
            if(optional.get() == ButtonType.OK) {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-login.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
                closeStage(mouseEvent);
            }
        });
    }

    private void loadData() {
        if(taiKhoan != null) {
            lblMaNhanVien.setText(taiKhoan.getNhanVien().getMaNhanVien());
            lblTenNhanVien.setText(taiKhoan.getNhanVien().getHoTen());
        }
    }

    private void changeSceneBanHang(MouseEvent e) throws IOException {
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setMaximized(false);
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-ban-hang.fxml"));
        fxmlLoader.setController(new FormBanHangController(taiKhoan));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toString());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

//    private void openFormTaiKhoan(Stage context, TaiKhoan taiKhoan) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(FashionShopApp.class.getResource("views/form-tai-khoan.fxml"));
//            fxmlLoader.setController(new FormTaiKhoanController(taiKhoan));
//            Scene scene = new Scene(fxmlLoader.load());
//            scene.getStylesheets().add(FashionShopApp.class.getResource("styles/styles.css").toString());
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.initModality(Modality.WINDOW_MODAL);
//            stage.initOwner(context);
//            stage.show();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

    private void openFormThongTinTaiKhoan(TaiKhoan taiKhoan, MouseEvent mouseEvent) {
        try {
            Stage context = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-thong-tin-tai-khoan.fxml"));
            fxmlLoader.setController(new FormThongTinTaiKhoanController(taiKhoan));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(context);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openFormThongBao(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-thong-bao.fxml"));
            fxmlLoader.setController(new FormThongBaoController());
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toString());
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void closeStage(MouseEvent mouseEvent){
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}