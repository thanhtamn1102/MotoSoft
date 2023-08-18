package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.ReadOnlyObjectWrapperTableColume;
//import com.example.fashionshop.impl.DonNhapHangImpl;
//import com.example.fashionshop.service.DonNhapHangService;
import com.example.fashionshop.utils.StringUtils;
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
import com.example.service.DonNhapHangService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

public class PageDonNhapHangController extends VBox {

    @FXML private Button btnThemMoi;
    @FXML private TextField txtTimKiem;
    @FXML private TableView<DonNhapHang> tableView;

    private TableColumn<DonNhapHang, DonNhapHang> colSelect;
    private TableColumn<DonNhapHang, String> colMaDonNhapHang;
    private TableColumn<DonNhapHang, DonNhapHang> colNgayTaoDon;
    private TableColumn<DonNhapHang, NhanVien> colNhanVienTaoDon;
    private TableColumn<DonNhapHang, NhaCungCap> colNhaCungCap;
    private TableColumn<DonNhapHang, DonNhapHang> colTongThanhToan;
    private TableColumn<DonNhapHang, DonNhapHang> colDaThanhToan;
    private TableColumn<DonNhapHang, DonNhapHang> colCongNo;
    private TableColumn<DonNhapHang, DonNhapHang> colTongSoLuong;
    private TableColumn<DonNhapHang, DonNhapHang> colSoLuongDaNhan;
    private TableColumn<DonNhapHang, DonNhapHang> colSoLuongConLai;
    private TableColumn<DonNhapHang, DonNhapHangStatus> colTrangThai;
    private TableColumn<DonNhapHang, DonNhapHang> colAction;
    private TableColumn<DonNhapHang, DonNhapHang> colCheck;
    private TableColumn<DonNhapHang, DonNhapHang> colEdit;
    private TableColumn<DonNhapHang, DonNhapHang> colDelete;

    private DonNhapHangService donNhapHangService;
    private ObservableList<DonNhapHang> dsDonNhapHang;
    private FilteredList<DonNhapHang> filteredData;
    private TaiKhoan taiKhoan;
private Context context;
    public PageDonNhapHangController(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;

        try {
            context = new InitialContext();
            donNhapHangService = (DonNhapHangService) context.lookup(StringValues.SERVER_URL + "DonNhapHangService");

            init();
            addControls();
            loadData();
            addEvents();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("pages/page-don-nhap-hang.fxml"));
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
        colMaDonNhapHang = new TableColumn<>("Mã đơn nhập hàng");
        colNgayTaoDon = new TableColumn<>("Ngày tạo đơn");
        colNhanVienTaoDon = new TableColumn<>("Nhân viên tạo đơn");
        colNhaCungCap = new TableColumn<>("Nhà cung cấp");
        colTongThanhToan = new TableColumn<>("Tổng thanh toán");
        colDaThanhToan = new TableColumn<>("Đã thanh toán");
        colTongSoLuong = new TableColumn<>("Tổng số lượng");
        colSoLuongDaNhan = new TableColumn<>("Số lượng đã nhận");
        colSoLuongConLai = new TableColumn<>("Số lượng còn lại");
        colCongNo = new TableColumn<>("Công nợ");
        colTrangThai = new TableColumn<>("Trạng thái");
        colAction = new TableColumn<>("Thao tác");
        colCheck = new TableColumn<>("Duyệt");
        colEdit = new TableColumn<>("Sửa");
        colDelete = new TableColumn<>("Xóa");
        colAction.getColumns().addAll(colCheck, colEdit, colDelete);

        colMaDonNhapHang.setCellValueFactory(new PropertyValueFactory<>("maDonNhapHang"));
        colNhanVienTaoDon.setCellValueFactory(new PropertyValueFactory<>("nhanVienTaoDon"));
        colNhaCungCap.setCellValueFactory(new PropertyValueFactory<>("nhaCungCap"));
        colNgayTaoDon.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colNgayTaoDon.setCellFactory(new Callback<TableColumn<DonNhapHang, DonNhapHang>, TableCell<DonNhapHang, DonNhapHang>>() {
            @Override
            public TableCell<DonNhapHang, DonNhapHang> call(TableColumn<DonNhapHang, DonNhapHang> donNhapHangDonNhapHangTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(DonNhapHang donNhapHang, boolean b) {
                        super.updateItem(donNhapHang, b);
                        if(donNhapHang != null) {
                            LocalDateTime ngayTaoDon = donNhapHang.getNgayTao();
                            setText(ngayTaoDon.getDayOfMonth() + "/" + ngayTaoDon.getMonthValue() + "/" + ngayTaoDon.getYear() + "  " +
                                    ngayTaoDon.getHour() + ":" + ngayTaoDon.getMinute() + ":" + ngayTaoDon.getSecond());
                        }
                    }
                };
            }
        });
        colTongThanhToan.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<DonNhapHang>());
        colTongThanhToan.setCellFactory(new Callback<TableColumn<DonNhapHang, DonNhapHang>, TableCell<DonNhapHang, DonNhapHang>>() {
            @Override
            public TableCell<DonNhapHang, DonNhapHang> call(TableColumn<DonNhapHang, DonNhapHang> donNhapHangDonNhapHangTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(DonNhapHang donNhapHang, boolean b) {
                        super.updateItem(donNhapHang, b);
                        if(donNhapHang != null) {
                            donNhapHang.tinhTongTien();
                            setText(StringUtils.formatCurrency(donNhapHang.tinhTongThanhToan()));
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        colDaThanhToan.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colDaThanhToan.setCellFactory(new Callback<TableColumn<DonNhapHang, DonNhapHang>, TableCell<DonNhapHang, DonNhapHang>>() {
            @Override
            public TableCell<DonNhapHang, DonNhapHang> call(TableColumn<DonNhapHang, DonNhapHang> donNhapHangDonNhapHangTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(DonNhapHang donNhapHang, boolean b) {
                        super.updateItem(donNhapHang, b);
                        if(donNhapHang != null) {
                            setText(StringUtils.formatCurrency(donNhapHang.getDaThanhToan()));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colTongSoLuong.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<DonNhapHang>());
        colTongSoLuong.setCellFactory(new Callback<TableColumn<DonNhapHang, DonNhapHang>, TableCell<DonNhapHang, DonNhapHang>>() {
            @Override
            public TableCell<DonNhapHang, DonNhapHang> call(TableColumn<DonNhapHang, DonNhapHang> donNhapHangDonNhapHangTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(DonNhapHang donNhapHang, boolean b) {
                        super.updateItem(donNhapHang, b);
                        if(donNhapHang != null){
                            setText(StringUtils.formatCurrency(donNhapHang.tinhTongSoLuong()));
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        colSoLuongDaNhan.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colSoLuongDaNhan.setCellFactory(new Callback<TableColumn<DonNhapHang, DonNhapHang>, TableCell<DonNhapHang, DonNhapHang>>() {
            @Override
            public TableCell<DonNhapHang, DonNhapHang> call(TableColumn<DonNhapHang, DonNhapHang> donNhapHangDonNhapHangTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(DonNhapHang donNhapHang, boolean b) {
                        super.updateItem(donNhapHang, b);
                        if(donNhapHang != null) {
                            setText(String.valueOf(donNhapHang.getTongSoLuong() - donNhapHang.getSoLuongConLai()));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colSoLuongConLai.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<DonNhapHang>());
        colSoLuongConLai.setCellFactory(new Callback<TableColumn<DonNhapHang, DonNhapHang>, TableCell<DonNhapHang, DonNhapHang>>() {
            @Override
            public TableCell<DonNhapHang, DonNhapHang> call(TableColumn<DonNhapHang, DonNhapHang> donNhapHangDonNhapHangTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(DonNhapHang donNhapHang, boolean b) {
                        super.updateItem(donNhapHang, b);
                        if(donNhapHang != null) {
                            setText(StringUtils.formatCurrency(donNhapHang.tinhSoLuongConLai()));
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        colCongNo.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colCongNo.setCellFactory(new Callback<TableColumn<DonNhapHang, DonNhapHang>, TableCell<DonNhapHang, DonNhapHang>>() {
            @Override
            public TableCell<DonNhapHang, DonNhapHang> call(TableColumn<DonNhapHang, DonNhapHang> donNhapHangDonNhapHangTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(DonNhapHang donNhapHang, boolean b) {
                        super.updateItem(donNhapHang, b);
                        if(donNhapHang != null) {
                            setText(StringUtils.formatCurrency(donNhapHang.getCongNo()));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        colTrangThai.setCellFactory(new Callback<TableColumn<DonNhapHang, DonNhapHangStatus>, TableCell<DonNhapHang, DonNhapHangStatus>>() {
            @Override
            public TableCell<DonNhapHang, DonNhapHangStatus> call(TableColumn<DonNhapHang, DonNhapHangStatus> donNhapHangDonNhapHangStatusTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(DonNhapHangStatus donNhapHangStatus, boolean b) {
                        super.updateItem(donNhapHangStatus, b);
                        if(donNhapHangStatus != null) {
                            Label label = new Label(donNhapHangStatus.toString());
                            label.setFont(new Font("Segoe UI Semibold", 12));
                            label.setTextFill(Color.WHITE);
                            label.setPadding(new Insets(4, 13, 4, 13));

                            if(donNhapHangStatus == DonNhapHangStatus.TAO_MOI) {
                                label.setStyle("-fx-background-color: #00A2E8; -fx-background-radius: 15;");
                            } else if(donNhapHangStatus == DonNhapHangStatus.DA_HOAN_THANH) {
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

        tableView.getColumns().addAll(
                colSelect, colMaDonNhapHang, colNgayTaoDon, colNhanVienTaoDon, colNhaCungCap, colTongThanhToan,
                colDaThanhToan, colCongNo, colTongSoLuong, colSoLuongDaNhan, colSoLuongConLai, colTrangThai, colAction
        );

        colSelect.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colSelect.setCellFactory(new Callback<TableColumn<DonNhapHang, DonNhapHang>, TableCell<DonNhapHang, DonNhapHang>>() {
            @Override
            public TableCell<DonNhapHang, DonNhapHang> call(TableColumn<DonNhapHang, DonNhapHang> categoryCategoryTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(DonNhapHang donNhapHang, boolean b) {
                        super.updateItem(donNhapHang, b);
                        if(donNhapHang != null) {
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
        colCheck.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<DonNhapHang>());
        colCheck.setCellFactory(new Callback<TableColumn<DonNhapHang, DonNhapHang>, TableCell<DonNhapHang, DonNhapHang>>() {
            @Override
            public TableCell<DonNhapHang, DonNhapHang> call(TableColumn<DonNhapHang, DonNhapHang> CategoryCategoryTableColumn) {
                return new TableCell<DonNhapHang, DonNhapHang>() {
                    @Override
                    protected void updateItem(DonNhapHang donNhapHang, boolean b) {
                        super.updateItem(donNhapHang, b);
                        if(donNhapHang != null && donNhapHang.getTrangThai() == DonNhapHangStatus.TAO_MOI) {
                            SVGPath editIcon = new SVGPath();
                            editIcon.setContent(Icons.CHECK_ICON_2);

                            Region btnCheck = new Region();
                            btnCheck.setMaxWidth(15);
                            btnCheck.setMaxHeight(10);
                            btnCheck.setPrefWidth(15);
                            btnCheck.setPrefHeight(10);
                            btnCheck.setShape(editIcon);
                            btnCheck.setStyle("-fx-background-color: #F44336;");

                            btnCheck.setOnMouseClicked(mouseEvent -> {
                                duyetDonNhapHang(donNhapHang);
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
        colEdit.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<DonNhapHang>());
        colEdit.setCellFactory(new Callback<TableColumn<DonNhapHang, DonNhapHang>, TableCell<DonNhapHang, DonNhapHang>>() {
            @Override
            public TableCell<DonNhapHang, DonNhapHang> call(TableColumn<DonNhapHang, DonNhapHang> CategoryCategoryTableColumn) {
                return new TableCell<DonNhapHang, DonNhapHang>() {
                    @Override
                    protected void updateItem(DonNhapHang donNhapHang, boolean b) {
                        super.updateItem(donNhapHang, b);
                        if(donNhapHang != null && donNhapHang.getTrangThai() != DonNhapHangStatus.DA_HOAN_THANH) {
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
                                openFormDonNhapHang(context, dsDonNhapHang, null, FormDonNhapHangController.UPDATE);
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
        colDelete.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<DonNhapHang>());
        colDelete.setCellFactory(new Callback<TableColumn<DonNhapHang, DonNhapHang>, TableCell<DonNhapHang, DonNhapHang>>() {
            @Override
            public TableCell<DonNhapHang, DonNhapHang> call(TableColumn<DonNhapHang, DonNhapHang> CategoryCategoryTableColumn) {
                return new TableCell<DonNhapHang, DonNhapHang>() {
                    @Override
                    protected void updateItem(DonNhapHang donNhapHang, boolean b) {
                        super.updateItem(donNhapHang, b);
                        if(donNhapHang != null) {
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
                                deleteDonNhapHang(donNhapHang);
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

        colSelect.setMaxWidth(1500);
        colCheck.setMaxWidth(1500);
        colEdit.setMaxWidth(1500);
        colDelete.setMaxWidth(1500);

        colSelect.setStyle("-fx-alignment: center;");
        colMaDonNhapHang.setStyle("-fx-alignment: center;");
        colNgayTaoDon.setStyle("-fx-alignment: center;");
        colTongThanhToan.setStyle("-fx-alignment: center;");
        colDaThanhToan.setStyle("-fx-alignment: center;");
        colCongNo.setStyle("-fx-alignment: center;");
        colTongSoLuong.setStyle("-fx-alignment: center;");
        colSoLuongDaNhan.setStyle("-fx-alignment: center;");
        colSoLuongConLai.setStyle("-fx-alignment: center;");
        colTrangThai.setStyle("-fx-alignment: center;");
        colCheck.setStyle("-fx-alignment: center;");
        colEdit.setStyle("-fx-alignment: center;");
        colDelete.setStyle("-fx-alignment: center;");
        colNhanVienTaoDon.setStyle("-fx-alignment: center-left;");
        colNhaCungCap.setStyle("-fx-alignment: center-left;");
    }

    private void addEvents() {
        tableView.setRowFactory(tableView -> {
            TableRow<DonNhapHang> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    DonNhapHang donNhapHang = row.getItem();
                    Stage context = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                    openFormDonNhapHang(context, dsDonNhapHang, donNhapHang, FormPhieuKiemKeController.VIEW);
                }
            });
            return row;
        });

        btnThemMoi.setOnMousePressed(mouseEvent -> {
            Stage context = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            openFormDonNhapHang(context, dsDonNhapHang, null, FormPhieuKiemKeController.ADD);
        });

        filteredData = new FilteredList<>(dsDonNhapHang, p -> true);
        txtTimKiem.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                findDonNhapHang(newValue);
            }
        });
        SortedList<DonNhapHang> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    private void loadData() {
       try {
           dsDonNhapHang = FXCollections.observableArrayList(donNhapHangService.getAllDonNhapHang());
       }catch (Exception exception){
           exception.printStackTrace();
       }
    }

    private void openFormDonNhapHang(Stage context, ObservableList<DonNhapHang> dsDonNhapHang, DonNhapHang donNhapHang, int type) {
        Scene scene = new Scene(new FormDonNhapHangController(taiKhoan, dsDonNhapHang, donNhapHang, type));
        scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toString());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(context);
        stage.setMaximized(true);
        stage.setTitle("Đơn nhập hàng");
        stage.show();
    }

    private void deleteDonNhapHang(DonNhapHang donNhapHang) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xóa đơn nhập hàng");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa đơn nhập hàng này?");

        Optional<ButtonType> option = alert.showAndWait();

        if(option.get() == ButtonType.OK) {
            try {
                if(donNhapHangService.deleteDonNhapHang(donNhapHang) && dsDonNhapHang.remove(donNhapHang)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Xóa đơn nhập hàng thành công");
                    tableView.getSelectionModel().clearSelection();
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

    private void findDonNhapHang(String text) {
        filteredData.setPredicate(donNhapHang -> {
            if(text == null || text.isEmpty()) {
                return true;
            }

            if(donNhapHang.getMaDonNhapHang().toLowerCase().contains(text.toLowerCase()))
                return true;
            return false;
        });
    }

    private void duyetDonNhapHang(DonNhapHang donNhapHang) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Duyệt đơn nhập hàng");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn duyệt đơn nhập hàng này?");

        Optional<ButtonType> option = alert.showAndWait();
        if(option.get() == ButtonType.OK) {
            donNhapHang.setTrangThai(DonNhapHangStatus.DA_HOAN_THANH);
            try {
                if(donNhapHangService.updateDonNhapHang(donNhapHang))
                    dsDonNhapHang.set(dsDonNhapHang.indexOf(donNhapHang), donNhapHang);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
