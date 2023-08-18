package com.example.fashionshop.controller;

import com.example.fashionshop.cus_comp.ImageItem;
import com.example.fashionshop.cus_comp.PTHImageItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FormThemHinhAnhVaoPTH implements Initializable {

    @FXML private ImageView imageView;
    @FXML private TextField txtImageAddress;
    @FXML private Button btnChonTuMayTinh, btnThem, btnThoat, btnLoad;
    @FXML private Label lblError;

    private HBox boxHinhAnhSanPham;

    public FormThemHinhAnhVaoPTH(HBox boxHinhAnhSanPham) {
        this.boxHinhAnhSanPham = boxHinhAnhSanPham;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEvents();
    }

    private void addEvents() {
        btnThoat.setOnMousePressed(mouseEvent -> {
            closeStage(mouseEvent);
        });

        btnLoad.setOnMousePressed(mouseEvent -> {
            String imageAddress = txtImageAddress.getText().trim();
            if(imageAddress.isEmpty()) {
                lblError.setText("Đường dẫn hình ảnh không được để trống");
            } else {
                Image image = new Image(imageAddress, true);
                image.progressProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if(t1.doubleValue() == 1.0) {
                            imageView.setImage(image);
                        }
                    }
                });
                image.errorProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                        lblError.setText("Không load được hình ảnh");
                        image.cancel();
                    }
                });
                image.exceptionProperty().addListener(new ChangeListener<Exception>() {
                    @Override
                    public void changed(ObservableValue<? extends Exception> observableValue, Exception exception, Exception t1) {
                        lblError.setText("Không load được hình ảnh");
                        image.cancel();
                    }
                });
            }
        });

        btnThem.setOnMousePressed(mouseEvent -> {
            String imageAddress = txtImageAddress.getText().trim();
            if(imageAddress.isEmpty()) {
                lblError.setText("Đường dẫn hình ảnh không được để trống");
            } else {
                Image image = new Image(imageAddress, true);
                image.progressProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if(t1.doubleValue() == 1.0) {
                            PTHImageItem imageItem = new PTHImageItem(imageAddress);

                            imageItem.getBtnDelete().setOnMousePressed(mouseEvent1 -> {
                                boxHinhAnhSanPham.getChildren().remove(imageItem);
                            });

                            boxHinhAnhSanPham.getChildren().add(imageItem);
                            closeStage(mouseEvent);
                        }
                    }
                });
                image.errorProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                        lblError.setText("Không load được hình ảnh");
                        image.cancel();
                    }
                });
                image.exceptionProperty().addListener(new ChangeListener<Exception>() {
                    @Override
                    public void changed(ObservableValue<? extends Exception> observableValue, Exception exception, Exception t1) {
                        lblError.setText("Không load được hình ảnh");
                        image.cancel();
                    }
                });
            }
        });

        btnChonTuMayTinh.setOnMousePressed(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Chọn hình ảnh sản phẩm");
            Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);
            if(file != null) {
                Image image = new Image(file.toURI().toString(), true);
                txtImageAddress.setText(file.toURI().toString());
                imageView.setImage(image);
            }
        });

        txtImageAddress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblError.setText("");
            }
        });
    }

    private void closeStage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
