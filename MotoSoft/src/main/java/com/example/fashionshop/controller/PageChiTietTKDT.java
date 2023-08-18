package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.ReadOnlyObjectWrapperTableColume;
import com.example.fashionshop.utils.StringUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;

public class PageChiTietTKDT extends VBox {

    @FXML private TableView<Object[]> tableView;
    private TableColumn<Object[], Object[]> colNgayTaoDH;
    private TableColumn<Object[], Object[]> colDoanhThu;
    private TableColumn<Object[], Object[]> colGiamGia;
    private TableColumn<Object[], Object[]> colTongPhiVanChuyen;
    private TableColumn<Object[], Object[]> colPhiVanChuyen;
    private TableColumn<Object[], Object[]> colTongVon;
    private TableColumn<Object[], Object[]> colSoLuongDatHang;
    private TableColumn<Object[], Object[]> colSoLuongTraLai;
    private TableColumn<Object[], Object[]> colSoLuongThuc;
    private TableColumn<Object[], Object[]> colDonHang;
    private TableColumn<Object[], Object[]> colTraLaiHang;
    private TableColumn<Object[], Object[]> colDoanhThuThuc;
    private TableColumn<Object[], Object[]> colTongDoanhThu;
    private TableColumn<Object[], Object[]> colLoiNhuan;
    private TableColumn<Object[], Object[]> colPhanTramLoiNhuan;

    private Button btnHienThi;

    private ObservableList<Object[]> chiTietBaoCao;

    public PageChiTietTKDT(ObservableList<Object[]> chiTietBaoCao, Button btnHienThi) {
        this.chiTietBaoCao = chiTietBaoCao;
        this.btnHienThi = btnHienThi;

        init();
        addControls();
        addEvents();
        loadData();
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("pages/page-chi-tiet-tkdt.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addControls() {
        colNgayTaoDH = new TableColumn<>("Ngày");
        colDoanhThu = new TableColumn<>("Doanh thu");
        colGiamGia = new TableColumn<>("Giảm giá");
        colTongPhiVanChuyen = new TableColumn<>("Tổng phí vận chuyển");
        colPhiVanChuyen = new TableColumn<>("Phí vận chuyển");
        colTongVon = new TableColumn<>("Tổng vốn");
        colSoLuongDatHang = new TableColumn<>("Số lượng đặt hàng");
        colSoLuongTraLai = new TableColumn<>("Số lượng trả lại");
        colSoLuongThuc = new TableColumn<>("Số lượng thực");
        colDonHang = new TableColumn<>("Đơn hàng");
        colTraLaiHang = new TableColumn<>("Trả lại hàng");
        colDoanhThuThuc = new TableColumn<>("Doanh thu thực");
        colTongDoanhThu = new TableColumn<>("Tổng doanh thu");
        colLoiNhuan = new TableColumn<>("Lợi nhuận");
        colPhanTramLoiNhuan = new TableColumn<>("Phần trăm lợi nhuận");

        colNgayTaoDH.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colNgayTaoDH.setCellFactory(new Callback<TableColumn<Object[], Object[]>, TableCell<Object[], Object[]>>() {
            @Override
            public TableCell<Object[], Object[]> call(TableColumn<Object[], Object[]> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object[] objects, boolean b) {
                        super.updateItem(objects, b);
                        if(objects != null) {
                            setText(String.valueOf(objects[0]));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colDoanhThu.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colDoanhThu.setCellFactory(new Callback<TableColumn<Object[], Object[]>, TableCell<Object[], Object[]>>() {
            @Override
            public TableCell<Object[], Object[]> call(TableColumn<Object[], Object[]> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object[] objects, boolean b) {
                        super.updateItem(objects, b);
                        if(objects != null) {
                            setText(StringUtils.formatCurrency((double) objects[1]));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colGiamGia.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colGiamGia.setCellFactory(new Callback<TableColumn<Object[], Object[]>, TableCell<Object[], Object[]>>() {
            @Override
            public TableCell<Object[], Object[]> call(TableColumn<Object[], Object[]> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object[] objects, boolean b) {
                        super.updateItem(objects, b);
                        if(objects != null) {
                            setText(StringUtils.formatCurrency((double)objects[2]));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colTongPhiVanChuyen.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colTongPhiVanChuyen.setCellFactory(new Callback<TableColumn<Object[], Object[]>, TableCell<Object[], Object[]>>() {
            @Override
            public TableCell<Object[], Object[]> call(TableColumn<Object[], Object[]> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object[] objects, boolean b) {
                        super.updateItem(objects, b);
                        if(objects != null) {
                            setText(StringUtils.formatCurrency((double)objects[3]));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colPhiVanChuyen.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colPhiVanChuyen.setCellFactory(new Callback<TableColumn<Object[], Object[]>, TableCell<Object[], Object[]>>() {
            @Override
            public TableCell<Object[], Object[]> call(TableColumn<Object[], Object[]> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object[] objects, boolean b) {
                        super.updateItem(objects, b);
                        if(objects != null) {
                            setText(StringUtils.formatCurrency((double)objects[4]));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colTongVon.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colTongVon.setCellFactory(new Callback<TableColumn<Object[], Object[]>, TableCell<Object[], Object[]>>() {
            @Override
            public TableCell<Object[], Object[]> call(TableColumn<Object[], Object[]> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object[] objects, boolean b) {
                        super.updateItem(objects, b);
                        if(objects != null) {
                            setText(StringUtils.formatCurrency((double) objects[5]));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colSoLuongDatHang.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colSoLuongDatHang.setCellFactory(new Callback<TableColumn<Object[], Object[]>, TableCell<Object[], Object[]>>() {
            @Override
            public TableCell<Object[], Object[]> call(TableColumn<Object[], Object[]> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object[] objects, boolean b) {
                        super.updateItem(objects, b);
                        if(objects != null) {
                            setText(String.valueOf(objects[6]));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colSoLuongTraLai.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colSoLuongTraLai.setCellFactory(new Callback<TableColumn<Object[], Object[]>, TableCell<Object[], Object[]>>() {
            @Override
            public TableCell<Object[], Object[]> call(TableColumn<Object[], Object[]> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object[] objects, boolean b) {
                        super.updateItem(objects, b);
                        if(objects != null) {
                            setText(String.valueOf(objects[7]));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colSoLuongThuc.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colSoLuongThuc.setCellFactory(new Callback<TableColumn<Object[], Object[]>, TableCell<Object[], Object[]>>() {
            @Override
            public TableCell<Object[], Object[]> call(TableColumn<Object[], Object[]> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object[] objects, boolean b) {
                        super.updateItem(objects, b);
                        if(objects != null) {
                            setText(String.valueOf(objects[8]));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colDonHang.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colDonHang.setCellFactory(new Callback<TableColumn<Object[], Object[]>, TableCell<Object[], Object[]>>() {
            @Override
            public TableCell<Object[], Object[]> call(TableColumn<Object[], Object[]> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object[] objects, boolean b) {
                        super.updateItem(objects, b);
                        if(objects != null) {
                            setText(String.valueOf(objects[9]));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colTraLaiHang.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colTraLaiHang.setCellFactory(new Callback<TableColumn<Object[], Object[]>, TableCell<Object[], Object[]>>() {
            @Override
            public TableCell<Object[], Object[]> call(TableColumn<Object[], Object[]> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object[] objects, boolean b) {
                        super.updateItem(objects, b);
                        if(objects != null) {
                            setText(StringUtils.formatCurrency((double) objects[10]));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colDoanhThuThuc.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colDoanhThuThuc.setCellFactory(new Callback<TableColumn<Object[], Object[]>, TableCell<Object[], Object[]>>() {
            @Override
            public TableCell<Object[], Object[]> call(TableColumn<Object[], Object[]> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object[] objects, boolean b) {
                        super.updateItem(objects, b);
                        if(objects != null) {
                            setText(StringUtils.formatCurrency((double) objects[11]));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colTongDoanhThu.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colTongDoanhThu.setCellFactory(new Callback<TableColumn<Object[], Object[]>, TableCell<Object[], Object[]>>() {
            @Override
            public TableCell<Object[], Object[]> call(TableColumn<Object[], Object[]> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object[] objects, boolean b) {
                        super.updateItem(objects, b);
                        if(objects != null) {
                            setText(StringUtils.formatCurrency((double) objects[12]));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colLoiNhuan.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colLoiNhuan.setCellFactory(new Callback<TableColumn<Object[], Object[]>, TableCell<Object[], Object[]>>() {
            @Override
            public TableCell<Object[], Object[]> call(TableColumn<Object[], Object[]> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object[] objects, boolean b) {
                        super.updateItem(objects, b);
                        if(objects != null) {
                            setText(StringUtils.formatCurrency((double) objects[13]));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        colPhanTramLoiNhuan.setCellValueFactory(new ReadOnlyObjectWrapperTableColume<>());
        colPhanTramLoiNhuan.setCellFactory(new Callback<TableColumn<Object[], Object[]>, TableCell<Object[], Object[]>>() {
            @Override
            public TableCell<Object[], Object[]> call(TableColumn<Object[], Object[]> tableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object[] objects, boolean b) {
                        super.updateItem(objects, b);
                        if(objects != null) {
                            setText(String.valueOf((double) Math.round((double)objects[14] * 100) / 100));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        colDoanhThu.setStyle("-fx-alignment: center-right;");
        colGiamGia.setStyle("-fx-alignment: center-right;");
        colTongPhiVanChuyen.setStyle("-fx-alignment: center-right;");
        colPhiVanChuyen.setStyle("-fx-alignment: center-right;");
        colTongVon.setStyle("-fx-alignment: center-right;");
        colSoLuongDatHang.setStyle("-fx-alignment: center-right;");
        colSoLuongTraLai.setStyle("-fx-alignment: center-right;");
        colSoLuongThuc.setStyle("-fx-alignment: center-right;");
        colDonHang.setStyle("-fx-alignment: center-right;");
        colTraLaiHang.setStyle("-fx-alignment: center-right;");
        colDoanhThuThuc.setStyle("-fx-alignment: center-right;");
        colTongDoanhThu.setStyle("-fx-alignment: center-right;");
        colLoiNhuan.setStyle("-fx-alignment: center-right;");
        colPhanTramLoiNhuan.setStyle("-fx-alignment: center-right;");

        tableView.getColumns().addAll(
                colNgayTaoDH, colDoanhThu, colGiamGia, colTongPhiVanChuyen, colPhiVanChuyen, colTongVon, colSoLuongDatHang,
                colSoLuongTraLai, colSoLuongThuc, colDonHang, colTraLaiHang, colDoanhThuThuc, colTongDoanhThu, colLoiNhuan,
                colPhanTramLoiNhuan
        );

        tableView.setItems(chiTietBaoCao);
    }

    private void addEvents() {
        ContextMenu contextMenu = new ContextMenu();

        tableView.getColumns().forEach(tableColumn -> {
            CheckMenuItem checkMenuItem = new CheckMenuItem(tableColumn.getText());
            checkMenuItem.setSelected(true);
            checkMenuItem.setStyle("-fx-text-fill: #999999;");

            checkMenuItem.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    tableColumn.setVisible(t1);
                }
            });

            contextMenu.getItems().add(checkMenuItem);
        });

        btnHienThi.addEventHandler(ActionEvent.ACTION, e -> {
            contextMenu.show(btnHienThi, Side.BOTTOM, 10, 5);
        });
    }

    private void loadData() {

    }
}
