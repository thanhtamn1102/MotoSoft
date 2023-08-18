package com.example.fashionshop.controller;

import com.example.fashionshop.cus_comp.ReadOnlyObjectWrapperTableColume;
//import com.example.fashionshop.impl.MauSacImpl;
//import com.example.fashionshop.model.MauSac;
//import com.example.fashionshop.service.MauSacService;
import com.example.fashionshop.values.StringValues;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.model.MauSac;
import com.example.service.MauSacService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FormQLMauSacController implements Initializable {

    @FXML private TextField txtTenMau;
    @FXML private ColorPicker colorPicker;
    @FXML private Button btnThem, btnCapNhat, btnXoa, btnTimKiem, btnHienThiTatCa, btnThoat;
    @FXML private TableView<MauSac> tableView;

    private TableColumn<MauSac, MauSac> colSelect;
    private TableColumn<MauSac, String> colTenMau;
    private TableColumn<MauSac, String> colCode;
    private TableColumn<MauSac, MauSac> colBoxColor;

    private MauSacService mauSacService;
    private ObservableList<MauSac> dsMauSac;

    private ContextMenu contextMenuTenMauError;
    private MenuItem menuItemTenMauError;
    private static Context context ;

    public FormQLMauSacController(ObservableList<MauSac> dsMauSac) {
        this.dsMauSac = dsMauSac;
        try {
            context = new InitialContext();
            mauSacService = (MauSacService) context.lookup(StringValues.SERVER_URL + "MauSacService");
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addControls();
        addEvents();
        loadData();
    }

    private void addControls() {
        menuItemTenMauError = new MenuItem();
        menuItemTenMauError.setDisable(true);
        menuItemTenMauError.getStyleClass().clear();
        menuItemTenMauError.setStyle("-fx-text-fill: red");
        contextMenuTenMauError = new ContextMenu();
        contextMenuTenMauError.getItems().add(menuItemTenMauError);

        colSelect = new TableColumn<>();
        colTenMau = new TableColumn<>("Tên màu");
        colCode = new TableColumn<>("Code");
        colBoxColor = new TableColumn<>();

        colSelect.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<MauSac>());
        colSelect.setCellFactory(new Callback<>() {
            @Override
            public TableCell<MauSac, MauSac> call(TableColumn<MauSac, MauSac> mauSacMauSacTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(MauSac mauSac, boolean b) {
                        super.updateItem(mauSac, b);
                        if (mauSac != null) {
                            CheckBox checkBox = new CheckBox("");
                            setGraphic(checkBox);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
        colTenMau.setCellValueFactory(new PropertyValueFactory<>("tenMau"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colBoxColor.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<MauSac>());
        colBoxColor.setCellFactory(new Callback<TableColumn<MauSac, MauSac>, TableCell<MauSac, MauSac>>() {
            @Override
            public TableCell<MauSac, MauSac> call(TableColumn<MauSac, MauSac> mauSacMauSacTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(MauSac mauSac, boolean b) {
                        super.updateItem(mauSac, b);
                        if(mauSac != null) {
                            VBox vBox = new VBox();
                            vBox.setPrefSize(25, 25);
                            vBox.setMaxSize(25, 25);
                            vBox.setMinSize(25, 25);
                            vBox.setStyle("-fx-background-radius: 3; -fx-border-radius: 2; -fx-border-color:  #2EC3E9; -fx-border-width: 2; -fx-background-color: " + mauSac.getCode() + ";");
                            setGraphic(vBox);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        colSelect.setMaxWidth(1500);
        colSelect.setStyle("-fx-alignment: center;");
        colTenMau.setStyle("-fx-alignment: center;");
        colCode.setStyle("-fx-alignment: center;");
        colBoxColor.setStyle("-fx-alignment: center;");

        tableView.getColumns().addAll(colSelect, colTenMau, colCode, colBoxColor);
        tableView.setItems(dsMauSac);
    }

    private void addEvents() {
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MauSac>() {
            @Override
            public void changed(ObservableValue<? extends MauSac> observableValue, MauSac mauSac, MauSac t1) {
                if(t1 != null) {
                    txtTenMau.setText(t1.getTenMau());
                    colorPicker.setValue(Color.web(t1.getCode()));
                }
            }
        });

        btnThem.setOnMousePressed(mouseEvent -> {
            String code = tcomBCode(colorPicker.getValue());
            System.out.println(code);
            String tenMau = txtTenMau.getText().trim();
            if(checkInput(tenMau)){
                MauSac mauSac = new MauSac(tenMau, code);
                addMauSac(mauSac, mouseEvent);
            }
        });

        btnCapNhat.setOnMousePressed(mouseEvent -> {
            MauSac mauSac = tableView.getSelectionModel().getSelectedItem();
            if(mauSac != null) {
                String tenMauSac = txtTenMau.getText().trim();
                String code = tcomBCode(colorPicker.getValue());

                if(!tenMauSac.isEmpty()){
                    mauSac.setTenMau(tenMauSac);
                    mauSac.setCode(code);
                    updateMauSac(mauSac, mouseEvent);
                } else {
                    menuItemTenMauError.setText("Tên màu sắc không được để trống");
                    contextMenuTenMauError.show(txtTenMau, Side.BOTTOM, 10, 5);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Bạn chưa chọn màu sắc cần cập nhật");
                alert.show();
            }
        });

        btnXoa.setOnMousePressed(mouseEvent -> {
            MauSac mauSac = tableView.getSelectionModel().getSelectedItem();
            if(mauSac != null) {
                deleteMauSac(mauSac, mouseEvent);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Bạn chưa chọn màu sắc cần xóa");
                alert.show();
            }
        });

        btnThoat.setOnMousePressed(mouseEvent -> {
            closeStage(mouseEvent);
        });

        btnTimKiem.setOnMousePressed(mouseEvent -> {
            List<MauSac> dstimKiem = new ArrayList<>();
            String timKiem = txtTenMau.getText().trim();
            for (MauSac mauSac: dsMauSac) {
                if(mauSac.getTenMau().equals(timKiem))
                    dstimKiem.add(mauSac);
            }
            tableView.setItems(FXCollections.observableArrayList(dstimKiem));
        });
        btnHienThiTatCa.setOnMousePressed(mouseEvent -> {
            tableView.setItems(dsMauSac);
        });
    }

    private void loadData() {

    }
    
    private boolean checkInput(String tenMau){
        if(tenMau.isEmpty()){
            menuItemTenMauError.setText("Tên màu sắc không được để trống");
            contextMenuTenMauError.show(txtTenMau, Side.BOTTOM, 10, 5);
            return false;
        }
        for (MauSac mauSac: dsMauSac) {
            if(mauSac.getTenMau().equals(tenMau)){
                menuItemTenMauError.setText("Tên màu sắc đã có");
                contextMenuTenMauError.show(txtTenMau, Side.BOTTOM, 10, 5);
                return false;
            }
        }

        return true;
        }

    private void addMauSac(MauSac mauSac, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thêm màu sắc");
        alert.setHeaderText(null);

        try {
            if(mauSacService.addMauSac(mauSac) && dsMauSac.add(mauSac)){
                alert.setContentText("Thêm màu sắc thành công");
                alert.show();
            }
            else {
                alert.setContentText("Error: Thêm màu sắc thất bại");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void updateMauSac(MauSac mauSac, MouseEvent mouseEvent) {
        try {
            if (mauSacService.updateMauSac(mauSac)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cập nhật thông tin màu sắc");
                alert.setHeaderText(null);

                if (mauSacService.updateMauSac(mauSac)) {
                    dsMauSac.set(dsMauSac.indexOf(mauSac), mauSac);
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Cập nhật thông tin màu sắc thành công!");
                    alert.show();
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Error: Cập nhật thông tin sản màu sắc thành công");
                    alert.show();
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void deleteMauSac(MauSac mauSac, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xóa màu sắc");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa màu sắc này?");

        Optional<ButtonType> option = alert.showAndWait();

       try {
           if(option.get() == ButtonType.OK) {
               if(mauSacService.deleteMauSac(mauSac) && dsMauSac.remove(mauSac)) {
                   alert.setAlertType(Alert.AlertType.INFORMATION);
                   alert.setContentText("Xóa màu sắc thành công!");
               }
               else {
                   alert.setAlertType(Alert.AlertType.ERROR);
                   alert.setContentText("Error: Xóa màu sắc không thành công!");
               }
               alert.show();
           }
       }catch (Exception exception){
           exception.printStackTrace();
       }
    }

    private void closeStage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public static String tcomBCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

}
