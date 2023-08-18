package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.cus_comp.CardsPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import com.example.model.TaiKhoan;

import java.net.URL;
import java.util.ResourceBundle;

public class FormBanHangController implements Initializable {

    @FXML private VBox menuDashboard, menuBanHang, menuDonHang;
    @FXML private SVGPath iconMenuBanHang, iconMenuDonHang;
    @FXML private Label lblMenuBanHang, lblMenuDonHang;
    @FXML private VBox boxContent;

    private PageBanHangController pageBanHang;
    private PageDonHangController pageDonHang;


    private CardsPane cardsPane;

    private TaiKhoan taiKhoan;

    public FormBanHangController(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addControls();
        addEvents();
    }

    private void addControls() {
        pageBanHang = new PageBanHangController(taiKhoan);
        pageBanHang.setId("pageBanHang");
        pageDonHang = new PageDonHangController(taiKhoan);
        pageDonHang.setId("pageDonHang");

        cardsPane = new CardsPane();
        cardsPane.add(pageBanHang.getId(), pageBanHang);
        cardsPane.add(pageDonHang.getId(), pageDonHang);

        boxContent.getChildren().add(cardsPane);

        cardsPane.show(pageBanHang.getId());
    }

    private void addEvents() {
        menuDashboard.setOnMousePressed(mouseEvent -> {
            Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            openFormQuanLy(stage);
        });

        menuBanHang.setOnMousePressed(mouseEvent -> {
            cardsPane.show(pageBanHang.getId());

            menuBanHang.setStyle("-fx-background-color:  #58ACFA;");
            iconMenuBanHang.setFill(Color.WHITE);
            lblMenuBanHang.setTextFill(Color.WHITE);

            menuDonHang.setStyle("-fx-background-color: white;");
            iconMenuDonHang.setFill(Color.web("#bdbdbd"));
            lblMenuDonHang.setTextFill(Color.web("#bdbdbd"));
        });

        menuDonHang.setOnMousePressed(mouseEvent -> {
            cardsPane.show(pageDonHang.getId());

            menuBanHang.setStyle("-fx-background-color: white;");
            iconMenuBanHang.setFill(Color.web("#bdbdbd"));
            lblMenuBanHang.setTextFill(Color.web("#bdbdbd"));

            menuDonHang.setStyle("-fx-background-color:  #58ACFA;");
            iconMenuDonHang.setFill(Color.WHITE);
            lblMenuDonHang.setTextFill(Color.WHITE);
        });
    }

    private void openFormQuanLy(Stage stage) {
        try {
            stage.setMaximized(false);
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-quan-ly.fxml"));
            fxmlLoader.setController(new FormQuanLyController(taiKhoan));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toString());
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

