package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.OrderItem;
//import com.example.fashionshop.model.DonHang;
//import com.example.fashionshop.model.TaiKhoan;
import com.example.fashionshop.values.StringValues;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.model.DonHang;
import com.example.model.TaiKhoan;
import com.example.service.DonHangService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PageHoaDonController extends BorderPane implements Initializable{

    @FXML private ListView<DonHang> listView;

    @FXML private TextField txtTimKiemMaDH, txtTimKiemSdtKH;
    @FXML private DatePicker startPicker, endPicker;

    private ObservableList<DonHang> dsDonHang = FXCollections.observableArrayList();
    private FilteredList<DonHang> filteredList;

    private Context context;
    private Object donHangServiceRemote;
    private DonHangService donHangService;
    private TaiKhoan taiKhoan;

    public PageHoaDonController(TaiKhoan taiKhoan){
        this.taiKhoan = taiKhoan;

        try {
            context = new InitialContext();
            donHangServiceRemote =  context.lookup(StringValues.SERVER_URL + "DonHangService");
            donHangService = (DonHangService) donHangServiceRemote;
            init();
            addControls();
            addEvents();
            loadData();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("pages/page-hoa-don.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void addControls(){

        listView.setCellFactory(new Callback<ListView<DonHang>, ListCell<DonHang>>() {
            @Override
            public ListCell<DonHang> call(ListView<DonHang> donHangPropertyListView) {
                return new ListCell<DonHang>() {
                    @Override
                    protected void updateItem(DonHang o, boolean b) {
                        super.updateItem(o, b);

                        if(o != null) {
                            OrderItem orderItem = new OrderItem(o);

                            orderItem.setOnMouseClicked(mouseEvent -> {
                                if(mouseEvent.getClickCount() == 2) {
                                    Stage context = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                                    openFormChiTietDonHang( context, o);
                                }
                            });

                            orderItem.getBtnXemChiTiet().setOnMousePressed(mouseEvent -> {
                                Stage context = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                                openFormChiTietDonHang(context, o);
                            });

                            orderItem.getBtnTraHang().setOnMousePressed(mouseEvent -> {
                                openFormTraHang(o, FormTraHangController.ADD, mouseEvent);
                            });

                            orderItem.getBtnPhieuTraHang().setOnMousePressed(mouseEvent -> {
                                openFormTraHang(o, FormTraHangController.VIEW, mouseEvent);
                            });

                            orderItem.getBoxAction().getChildren().remove(orderItem.getBtnThanhToan());
                            orderItem.getBoxAction().getChildren().remove(orderItem.getBtnDelete());

                            if(o.getPhieuTraHang() == null)
                                orderItem.getBoxAction().getChildren().remove(orderItem.getBtnPhieuTraHang());

                            if(o.getPhieuTraHang() != null || o.getNgayTaoDonHang().plusHours(48).compareTo(LocalDateTime.now()) < 0)
                                orderItem.getBoxAction().getChildren().remove(orderItem.getBtnTraHang());

                            setGraphic(orderItem);
                        } else {
                            setGraphic(null);
                        }

                    }
                };
            }
        });
    }




    private void addEvents(){

        filteredList = new FilteredList<>(dsDonHang, b -> true);

        txtTimKiemMaDH.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                findDonHang(txtTimKiemMaDH.getText().trim(), txtTimKiemSdtKH.getText().trim(), startPicker.getValue(), endPicker.getValue());
            }
        });

        txtTimKiemSdtKH.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                findDonHang(txtTimKiemMaDH.getText().trim(), txtTimKiemSdtKH.getText().trim(), startPicker.getValue(), endPicker.getValue());
            }
        });

        startPicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate newDate) {
                findDonHang(txtTimKiemMaDH.getText().trim(), txtTimKiemSdtKH.getText().trim(), startPicker.getValue(), endPicker.getValue());
            }
        });

        endPicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                findDonHang(txtTimKiemMaDH.getText().trim(), txtTimKiemSdtKH.getText().trim(), startPicker.getValue(), endPicker.getValue());
            }
        });

        listView.setItems(filteredList);
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

    private void loadData() throws RemoteException {
        dsDonHang.setAll(donHangService.getAllDonHang());
    }

    public void openFormChiTietDonHang(Stage context, DonHang donHang){
        try {
            FXMLLoader fxmlLoader1 = new FXMLLoader(MotoSoftApp.class.getResource("views/form-don-hang.fxml"));
            fxmlLoader1.setController(new FormDonHangController(donHang));
            Scene scene = new Scene(fxmlLoader1.load());
            scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toString());
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(context);
            stage.setScene(scene);
            stage.setTitle("");
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void openFormTraHang(DonHang donHang, int type, MouseEvent mouseEvent){
        try {
            Stage context = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader1 = new FXMLLoader(MotoSoftApp.class.getResource("views/form-tra-hang.fxml"));
            fxmlLoader1.setController(new FormTraHangController(taiKhoan, donHang, type));
            Scene scene = new Scene(fxmlLoader1.load());
            scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toString());
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(context);
            stage.setScene(scene);
            stage.setTitle("Trả hàng");
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void findDonHang(String maDonHang, String sdtKhachHang, LocalDate startDate, LocalDate endDate) {
        filteredList.setPredicate(donHang -> {
            if(maDonHang != null && !maDonHang.isEmpty()) {
                if(!donHang.getMaDonHang().toLowerCase().contains(maDonHang.toLowerCase()))
                    return false;
            }

            if(sdtKhachHang != null && !sdtKhachHang.isEmpty()) {
                if(!donHang.getKhachHang().getSdt().contains(sdtKhachHang))
                    return false;
            }

            if(startDate != null && endDate != null) {
                if(!(donHang.getNgayTaoDonHang().toLocalDate().compareTo(startDate) >= 0 &&
                    donHang.getNgayTaoDonHang().toLocalDate().compareTo(endDate) <= 0))
                    return false;
            }
            else if(startDate != null) {
                if(donHang.getNgayTaoDonHang().toLocalDate().compareTo(startDate) != 0)
                    return false;
            }
            else if(endDate != null) {
                if(donHang.getNgayTaoDonHang().toLocalDate().compareTo(endDate) != 0)
                    return false;
            }

            return true;
        });
    }

}




