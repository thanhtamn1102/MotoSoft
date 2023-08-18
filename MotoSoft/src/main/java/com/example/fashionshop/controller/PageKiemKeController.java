package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.ReadOnlyObjectWrapperTableColume;
//import com.example.fashionshop.impl.PhieuKiemKeImpl;
//import com.example.fashionshop.service.PhieuKiemKeService;
import com.example.fashionshop.values.Icons;
import com.example.fashionshop.values.StringValues;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.model.*;
import com.example.service.PhieuKiemKeService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Optional;

public class PageKiemKeController extends VBox {

    @FXML private TextField txtTimKiem;
    @FXML private TableView<PhieuKiemKe> tableView;
    @FXML private Button btnThemMoi;

    private TableColumn<PhieuKiemKe, PhieuKiemKe> colSelect;
    private TableColumn<PhieuKiemKe, String> colMaPhieuKiemKe;
    private TableColumn<PhieuKiemKe, NhanVien> colNhanVienTaoPhieu;
    private TableColumn<PhieuKiemKe, LocalDateTime> colNgayTao;
    private TableColumn<PhieuKiemKe, NhanVien> colNhanVienKiemKe;
    private TableColumn<PhieuKiemKe, LocalDateTime> colNgayKiemKe;
    private TableColumn<PhieuKiemKe, PhieuKiemKeStatus> colTrangThai;
    private TableColumn<PhieuKiemKe, PhieuKiemKe> colAction;
    private TableColumn<PhieuKiemKe, PhieuKiemKe> colCheck;
    private TableColumn<PhieuKiemKe, PhieuKiemKe> colInventory;
    private TableColumn<PhieuKiemKe, PhieuKiemKe> colEdit;
    private TableColumn<PhieuKiemKe, PhieuKiemKe> colDelete;

    private static Context context ;
    private ObservableList<PhieuKiemKe> dsPhieuKiemKe;
    private PhieuKiemKeService phieuKiemKeService ;

    private FilteredList<PhieuKiemKe> filteredData;
    private TaiKhoan taiKhoan;

    private boolean INVENTORY = true;
    private boolean CREATE_INVENTORY_SHEET = false;
    private boolean UPDATE_INVENTORY_SHEET = false;
    private boolean DELETE_INVENTORY_SHEET = false;

    public PageKiemKeController(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
        try {
            context = new InitialContext();
            phieuKiemKeService = (PhieuKiemKeService) context.lookup(StringValues.SERVER_URL + "PhieuKiemKeService");
        }catch (Exception exception){
            exception.printStackTrace();
        }

        VaiTro vaiTro = taiKhoan.getVaiTro();
        int index = vaiTro.getDsNhomQuyen().indexOf(new VaiTroNhomQuyen(vaiTro, new NhomQuyen("INVENTORY_PERMISSION")));
        VaiTroNhomQuyen vaiTroNhomQuyen = vaiTro.getDsNhomQuyen().get(index);
        for (VaiTroQuyen vaiTroQuyen : vaiTroNhomQuyen.getDsQuyen()) {
            Quyen quyen = vaiTroQuyen.getQuyen();
            if(quyen.getMaQuyen().equalsIgnoreCase("INVENTORY")
                    && vaiTroQuyen.getTrangThai() == true) {
                INVENTORY = true;
            }
            if(quyen.getMaQuyen().equalsIgnoreCase("CREATE_INVENTORY_SHEET")
                    && vaiTroQuyen.getTrangThai() == true) {
                CREATE_INVENTORY_SHEET = true;
            }
            if(quyen.getMaQuyen().equalsIgnoreCase("UPDATE_INVENTORY_SHEET")
                    && vaiTroQuyen.getTrangThai() == true) {
                UPDATE_INVENTORY_SHEET = true;
            }
            if(quyen.getMaQuyen().equalsIgnoreCase("DELETE_INVENTORY_SHEET")
                    && vaiTroQuyen.getTrangThai() == true) {
                DELETE_INVENTORY_SHEET = true;
            }
        }

        init();
        addControls();
        loadData();
        addEvent();
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("pages/page-kiem-ke.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void addControls() {
        colSelect = new TableColumn<>();
        colMaPhieuKiemKe = new TableColumn<>("Mã phiếu kiểm kê");
        colNhanVienTaoPhieu = new TableColumn<>("Nhân viên tạo phiếu");
        colNgayTao = new TableColumn<>("Ngày tạo phiếu");
        colNhanVienKiemKe = new TableColumn<>("Nhân viên kiểm kê");
        colNgayKiemKe = new TableColumn<>("Ngày kiểm kê");
        colTrangThai = new TableColumn<>("Trạng thái");

        colSelect.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colSelect.setCellFactory(new Callback<TableColumn<PhieuKiemKe, PhieuKiemKe>, TableCell<PhieuKiemKe, PhieuKiemKe>>() {
            @Override
            public TableCell<PhieuKiemKe, PhieuKiemKe> call(TableColumn<PhieuKiemKe, PhieuKiemKe> categoryCategoryTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(PhieuKiemKe phieuKiemKe, boolean b) {
                        super.updateItem(phieuKiemKe, b);
                        if(phieuKiemKe != null) {
                            CheckBox checkBox = new CheckBox();
                            setGraphic(checkBox);
                            setText(null);
                        }
                        else {
                            setGraphic(null);
                            setText(null);
                        }
                    }
                };
            }
        });

        colMaPhieuKiemKe.setCellValueFactory(new PropertyValueFactory<>("maPhieuKiemKe"));
        colNhanVienTaoPhieu.setCellValueFactory(new PropertyValueFactory<>("nhanVienTaoPhieu"));
        colNgayTao.setCellValueFactory(new PropertyValueFactory<>("ngayTao"));
        colNhanVienKiemKe.setCellValueFactory(new PropertyValueFactory<>("nhanVienKiemKe"));
        colNgayKiemKe.setCellValueFactory(new PropertyValueFactory<>("ngayKiemKe"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        colTrangThai.setCellFactory(new Callback<TableColumn<PhieuKiemKe, PhieuKiemKeStatus>, TableCell<PhieuKiemKe, PhieuKiemKeStatus>>() {
            @Override
            public TableCell<PhieuKiemKe, PhieuKiemKeStatus> call(TableColumn<PhieuKiemKe, PhieuKiemKeStatus> phieuKiemKePhieuKiemKeStatusTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(PhieuKiemKeStatus phieuKiemKeStatus, boolean b) {
                        super.updateItem(phieuKiemKeStatus, b);
                        if(phieuKiemKeStatus != null) {
                            Label label = new Label(phieuKiemKeStatus.toString());
                            label.setFont(new Font("Segoe UI Semibold", 12));
                            label.setTextFill(Color.WHITE);
                            label.setPadding(new Insets(4, 13, 4, 13));

                            if(phieuKiemKeStatus == PhieuKiemKeStatus.TAO_MOI_CHO_KIEM_KE) {
                                label.setStyle("-fx-background-color: #00A2E8; -fx-background-radius: 15;");
                            } else if(phieuKiemKeStatus == PhieuKiemKeStatus.DA_KIEM_KE_CHO_DUYET) {
                                label.setStyle("-fx-background-color: #FFC90E; -fx-background-radius: 15;");
                            } else if(phieuKiemKeStatus == PhieuKiemKeStatus.DA_DUYET) {
                                label.setStyle("-fx-background-color: #24BD51; -fx-background-radius: 15;");
                            }

                            setPadding(new Insets(5, 5, 5, 5));
                            setGraphic(label);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        colAction = new TableColumn<>("Thao tác");
        colCheck = new TableColumn<>("Duyệt");
        colInventory = new TableColumn<>("KK");
        colEdit = new TableColumn<>("Sửa");
        colDelete = new TableColumn<>("Xóa");
        if(UPDATE_INVENTORY_SHEET)
            colAction.getColumns().add(colCheck);
        if(INVENTORY)
            colAction.getColumns().add(colInventory);
        if(UPDATE_INVENTORY_SHEET)
            colAction.getColumns().add(colEdit);
        if(DELETE_INVENTORY_SHEET)
            colAction.getColumns().add(colDelete);

        tableView.getColumns().addAll(
                colSelect, colMaPhieuKiemKe, colNhanVienTaoPhieu, colNgayTao,
                colNhanVienKiemKe, colNgayKiemKe, colTrangThai
        );
        if(INVENTORY || UPDATE_INVENTORY_SHEET || DELETE_INVENTORY_SHEET)
            tableView.getColumns().add(colAction);


        colCheck.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<PhieuKiemKe>());
        colCheck.setCellFactory(new Callback<TableColumn<PhieuKiemKe, PhieuKiemKe>, TableCell<PhieuKiemKe, PhieuKiemKe>>() {
            @Override
            public TableCell<PhieuKiemKe, PhieuKiemKe> call(TableColumn<PhieuKiemKe, PhieuKiemKe> phieuKiemKePhieuKiemKeTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(PhieuKiemKe phieuKiemKe, boolean b) {
                        super.updateItem(phieuKiemKe, b);
                        if(phieuKiemKe != null && phieuKiemKe.getTrangThai() == PhieuKiemKeStatus.DA_KIEM_KE_CHO_DUYET) {
                            SVGPath editIcon = new SVGPath();
                            editIcon.setContent(Icons.CHECK_ICON_2);

                            Region btnCheck = new Region();
                            btnCheck.setMaxWidth(15);
                            btnCheck.setMaxHeight(10);
                            btnCheck.setPrefWidth(15);
                            btnCheck.setPrefHeight(10);
                            btnCheck.setShape(editIcon);
                            btnCheck.setStyle("-fx-background-color: #04B431;");

                            btnCheck.setOnMouseClicked(mouseEvent -> {
                                duyetPhieuKiemKe(phieuKiemKe);
                            });

                            setGraphic(btnCheck);
                            setText(null);
                        }
                        else {
                            setGraphic(null);
                            setText(null);
                        }
                    }
                };
            }
        });
        colEdit.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<PhieuKiemKe>());
        colEdit.setCellFactory(new Callback<TableColumn<PhieuKiemKe, PhieuKiemKe>, TableCell<PhieuKiemKe, PhieuKiemKe>>() {
            @Override
            public TableCell<PhieuKiemKe, PhieuKiemKe> call(TableColumn<PhieuKiemKe, PhieuKiemKe> CategoryCategoryTableColumn) {
                return new TableCell<PhieuKiemKe, PhieuKiemKe>() {
                    @Override
                    protected void updateItem(PhieuKiemKe phieuKiemKe, boolean b) {
                        super.updateItem(phieuKiemKe, b);
                        if(phieuKiemKe != null && phieuKiemKe.getTrangThai() != PhieuKiemKeStatus.DA_DUYET) {
                            SVGPath editIcon = new SVGPath();
                            editIcon.setContent(Icons.EDIT);

                            Region btnEdit = new Region();
                            btnEdit.setMaxWidth(19);
                            btnEdit.setMaxHeight(20);
                            btnEdit.setPrefWidth(19);
                            btnEdit.setPrefHeight(20);
                            btnEdit.setShape(editIcon);
                            btnEdit.setStyle("-fx-background-color: #04B431;");

                            btnEdit.setOnMouseClicked(mouseEvent -> {
                                Stage context = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                                openFormPhieuKiemKe(context, dsPhieuKiemKe, null, FormPhieuKiemKeController.UPDATE);
                            });

                            setGraphic(btnEdit);
                            setText(null);
                        }
                        else {
                            setGraphic(null);
                            setText(null);
                        }
                    }
                };
            }
        });
        colDelete.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<PhieuKiemKe>());
        colDelete.setCellFactory(new Callback<TableColumn<PhieuKiemKe, PhieuKiemKe>, TableCell<PhieuKiemKe, PhieuKiemKe>>() {
            @Override
            public TableCell<PhieuKiemKe, PhieuKiemKe> call(TableColumn<PhieuKiemKe, PhieuKiemKe> CategoryCategoryTableColumn) {
                return new TableCell<PhieuKiemKe, PhieuKiemKe>() {
                    @Override
                    protected void updateItem(PhieuKiemKe phieuKiemKe, boolean b) {
                        super.updateItem(phieuKiemKe, b);
                        if(phieuKiemKe != null) {
                            SVGPath deleteIcon = new SVGPath();
                            deleteIcon.setContent(Icons.TRASH);

                            Region btnDelete = new Region();
                            btnDelete.setMaxWidth(15);
                            btnDelete.setMaxHeight(20);
                            btnDelete.setPrefWidth(15);
                            btnDelete.setPrefHeight(20);
                            btnDelete.setShape(deleteIcon);
                            btnDelete.setStyle("-fx-background-color: #FF8000;");

                            btnDelete.setOnMouseClicked(mouseEvent -> {
                                deletePhieuKiemKe(phieuKiemKe);
                            });

                            setGraphic(btnDelete);
                            setText(null);
                        }
                        else {
                            setGraphic(null);
                            setText(null);
                        }
                    }
                };
            }
        });
        colInventory.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<PhieuKiemKe>());
        colInventory.setCellFactory(new Callback<TableColumn<PhieuKiemKe, PhieuKiemKe>, TableCell<PhieuKiemKe, PhieuKiemKe>>() {
            @Override
            public TableCell<PhieuKiemKe, PhieuKiemKe> call(TableColumn<PhieuKiemKe, PhieuKiemKe> CategoryCategoryTableColumn) {
                return new TableCell<PhieuKiemKe, PhieuKiemKe>() {
                    @Override
                    protected void updateItem(PhieuKiemKe phieuKiemKe, boolean b) {
                        super.updateItem(phieuKiemKe, b);
                        if(phieuKiemKe != null && phieuKiemKe.getTrangThai() == PhieuKiemKeStatus.TAO_MOI_CHO_KIEM_KE) {
                            SVGPath deleteIcon = new SVGPath();
                            deleteIcon.setContent(Icons.INVENTORY_ICON);

                            Region btnInventory = new Region();
                            btnInventory.setMaxWidth(15);
                            btnInventory.setMaxHeight(15);
                            btnInventory.setPrefWidth(15);
                            btnInventory.setPrefHeight(15);
                            btnInventory.setShape(deleteIcon);
                            btnInventory.setStyle("-fx-background-color: #0040ff;");

                            btnInventory.setOnMouseClicked(mouseEvent -> {
                                Stage context = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                                openFormKiemKe(context, dsPhieuKiemKe, phieuKiemKe, FormPhieuKiemKeController.UPDATE);
                            });

                            setGraphic(btnInventory);
                            setText(null);
                        }
                        else {
                            setGraphic(null);
                            setText(null);
                        }
                    }
                };
            }
        });



        colSelect.setMaxWidth(1500);
        colCheck.setMaxWidth(1500);
        colInventory.setMaxWidth(1500);
        colEdit.setMaxWidth(1500);
        colDelete.setMaxWidth(1500);

        colSelect.setStyle("-fx-alignment: center;");
        colMaPhieuKiemKe.setStyle("-fx-alignment: center;");
        colTrangThai.setStyle("-fx-alignment: center;");
        colCheck.setStyle("-fx-alignment: center;");
        colInventory.setStyle("-fx-alignment: center;");
        colEdit.setStyle("-fx-alignment: center;");
        colDelete.setStyle("-fx-alignment: center;");
        colNhanVienTaoPhieu.setStyle("-fx-alignment: center-left;");
        colNgayTao.setStyle("-fx-alignment: center-left;");
        colNhanVienKiemKe.setStyle("-fx-alignment: center-left;");
        colNgayKiemKe.setStyle("-fx-alignment: center-left;");

        if(CREATE_INVENTORY_SHEET == false)
            btnThemMoi.setVisible(false);
    }

    private void addEvent() {
        tableView.setRowFactory(tableView -> {
            TableRow<PhieuKiemKe> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    PhieuKiemKe phieuKiemKe = row.getItem();
                    Stage context = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                    openFormPhieuKiemKe(context, dsPhieuKiemKe, phieuKiemKe, FormPhieuKiemKeController.VIEW);
                }
            });
            return row;
        });

        btnThemMoi.setOnMousePressed(mouseEvent -> {
            Stage context = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            openFormPhieuKiemKe(context, dsPhieuKiemKe, null, FormPhieuKiemKeController.ADD);
        });

        filteredData = new FilteredList<>(dsPhieuKiemKe, p -> true);
        txtTimKiem.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                findPhieuKiemKe(newValue);
            }
        });
        SortedList<PhieuKiemKe> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    private void loadData() {
try {
    dsPhieuKiemKe = FXCollections.observableArrayList(phieuKiemKeService.getAllPhieuKiemKe());

}catch (Exception exception){
    exception.printStackTrace();
}
    }

    private void openFormPhieuKiemKe(Stage context, ObservableList<PhieuKiemKe> dsPhieuKiemKe,  PhieuKiemKe phieuKiemKe, int type) {
        Scene scene = new Scene(new FormPhieuKiemKeController(taiKhoan, dsPhieuKiemKe, phieuKiemKe, type));
        scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toString());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(context);
        stage.setTitle("Phiếu kiểm kê");
        stage.show();
    }

    private void openFormKiemKe(Stage context, ObservableList<PhieuKiemKe> dsPhieuKiemKe,  PhieuKiemKe phieuKiemKe, int type) {
        Scene scene = new Scene(new FormKiemKeController(taiKhoan, dsPhieuKiemKe, phieuKiemKe, type));
        scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toString());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(context);
        stage.setTitle("Kiểm kê");
        stage.show();
    }

    private void deletePhieuKiemKe(PhieuKiemKe phieuKiemKe) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xóa danh mục sản phẩm");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa phiếu kiểm kê này?");

        Optional<ButtonType> option = alert.showAndWait();

        try {
            if(option.get() == ButtonType.OK) {
                if(phieuKiemKeService.deletePhieuKiemKe(phieuKiemKe) && dsPhieuKiemKe.remove(phieuKiemKe)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Xóa phiếu kiểm kê thành công");
                    tableView.getSelectionModel().clearSelection();
                }
                else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Error: Xóa phiếu kiểm kê không thành công!");
                }
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void findPhieuKiemKe(String text) {
        filteredData.setPredicate(phieuKiemKe -> {
            if(text == null || text.isEmpty()) {
                return true;
            }

            if(phieuKiemKe.getMaPhieuKiemKe().toLowerCase().contains(text.toLowerCase()))
                return true;
            return false;
        });
    }

    private void duyetPhieuKiemKe(PhieuKiemKe phieuKiemKe) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Duyệt phiếu kiểm kê");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn duyệt phiếu kiểm kê này?");

        Optional<ButtonType> option = alert.showAndWait();
        if(option.get() == ButtonType.OK) {
            phieuKiemKe.setTrangThai(PhieuKiemKeStatus.DA_DUYET);
            try {
                if(phieuKiemKeService.updatePhieuKiemKe(phieuKiemKe))
                    dsPhieuKiemKe.set(dsPhieuKiemKe.indexOf(phieuKiemKe), phieuKiemKe);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
