package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.model.TaiKhoan;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FormLoadingController implements Initializable {

    @FXML private BorderPane borderPane;

    private TaiKhoan taiKhoan;

    public FormLoadingController(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();

        Thread th2 = new Thread() {
            @Override
            public void run() {
                while(thread.getState() != Thread.State.TERMINATED) { }

                Platform.runLater(() -> {
                    openFormQuanLy(taiKhoan);
                    Stage stage = (Stage) borderPane.getScene().getWindow();
                    stage.close();
                });
            }
        };
        th2.start();
    }

    private void openFormQuanLy(TaiKhoan taiKhoan) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-quan-ly.fxml"));
            FormQuanLyController formQuanLyController = new FormQuanLyController(taiKhoan);
            fxmlLoader.setController(formQuanLyController);

            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(MotoSoftApp.class.getResource("styles/styles.css").toString());
            Stage stage = new Stage();
            stage.setUserData(taiKhoan);
            stage.setTitle("Moto Soft");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
