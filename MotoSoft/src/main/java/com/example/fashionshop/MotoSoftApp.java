package com.example.fashionshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MotoSoftApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Đăng nhập");
        stage.setScene(scene);
        stage.setTitle("Moto Soft");
        stage.show();

        // userName: 12345678
        // password: admin
    }

    public static void main(String[] args) {
        launch();
    }


}