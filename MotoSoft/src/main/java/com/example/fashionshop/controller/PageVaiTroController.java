package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
//import com.example.fashionshop.dao.*;
import com.example.fashionshop.values.StringValues;
import com.example.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.service.NhomQuyenService;
import com.example.service.VaiTroService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

public class PageVaiTroController extends VBox {

    @FXML private TableView<VaiTro> tableView;
    @FXML private Button btnThemMoi;
    @FXML private VBox btnReload;
    @FXML private List<TableColumn> tableColumns = new ArrayList<>();

    private ObservableList<VaiTro> vaiTroList;
    private Context context;
    private NhomQuyenService nhomQuyenService;
    private VaiTroService vaiTroService;

    public PageVaiTroController() throws RemoteException {
        try {
            context = new InitialContext();
            vaiTroService = (VaiTroService)context.lookup(StringValues.SERVER_URL + "VaiTroService");
            nhomQuyenService = (NhomQuyenService) context.lookup(StringValues.SERVER_URL + "NhomQuyenService");

            init();
            loadData();
            addEvent();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void init() throws RemoteException {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("pages/page-vai-tro.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        TableColumn<VaiTro, String> colRoleName = new TableColumn<>("Tên vai trò");
        colRoleName.setCellValueFactory(new PropertyValueFactory<>("tenVaiTro"));
        TableColumn<VaiTro, Void> colPermissionGroup = new TableColumn("Nhóm quyền");

        List<NhomQuyen> nhomQuyenList = nhomQuyenService.getAllNhomQuyen()
                        .stream()
                        .sorted(Comparator.comparing(NhomQuyen::getTenNhomQuyen))
                        .collect(Collectors.toList());

        for (NhomQuyen nhomQuyen : nhomQuyenList) {
            TableColumn<VaiTro, List<VaiTroNhomQuyen>> tableColumn = new TableColumn<>(nhomQuyen.getTenNhomQuyen());
            tableColumn.setId(nhomQuyen.getMaNhomQuyen());
            tableColumn.setCellValueFactory(new PropertyValueFactory<>("dsNhomQuyen"));
            tableColumn.setCellFactory(new Callback<>() {
                @Override
                public TableCell<VaiTro, List<VaiTroNhomQuyen>> call(TableColumn<VaiTro, List<VaiTroNhomQuyen>> param) {
                    return new TableCell<>() {
                        @Override
                        protected void updateItem(List<VaiTroNhomQuyen> item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                for (VaiTroNhomQuyen perGroup : item) {
                                    if (perGroup.getTrangThai() &&
                                            perGroup.getNhomQuyen().getMaNhomQuyen().equals(param.getId())) {
                                        StringBuilder text = new StringBuilder();
                                        for (VaiTroQuyen permission : perGroup.getDsQuyen()) {
                                            if (permission.getTrangThai()) {
                                                text.append("- ");
                                                text.append(permission.getQuyen());
                                                text.append("\n");
                                            }
                                        }
                                        setText(text.toString());
                                        return;
                                    }
                                }
                            } else {
                                setText(null);
                            }
                        }
                    };
                }
            });
            tableColumns.add(tableColumn);
            colPermissionGroup.getColumns().add(tableColumn);
        }

        tableView.getColumns().addAll(
                colRoleName,
                colPermissionGroup
        );
    }

    private void loadData() throws RemoteException {
        vaiTroList = FXCollections.observableArrayList(vaiTroService.getAllVaiTro());
        tableView.setItems(vaiTroList);
    }

    private void addEvent() {
        tableView.setRowFactory(tableView -> {
            TableRow<VaiTro> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    VaiTro vaiTro = row.getItem();
                    openFormVaiTro(vaiTroList, vaiTro, mouseEvent);
                }
            });
            return row;
        });

        btnReload.setOnMouseClicked(mouseEvent -> {
            try {
                vaiTroList.setAll(vaiTroService.getAllVaiTro());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void openFormVaiTro(ObservableList<VaiTro> dsVaiTro, VaiTro vaiTro, MouseEvent mouseEvent) {
        try {
            Stage context = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-vai-tro.fxml"));
            fxmlLoader.setController(new FormVaiTroController(dsVaiTro, vaiTro, FormVaiTroController.VIEW));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(context);
            stage.setTitle("Vai trò");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
