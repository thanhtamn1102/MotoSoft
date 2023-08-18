package com.example.fashionshop.cus_comp;

import com.example.fashionshop.MotoSoftApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;

import java.io.IOException;

public class ImageItem extends Pane {

    @FXML private ImageView imageView;
    @FXML private SVGPath btnDelete;

    private String imageAddress;

    public ImageItem(String imageAddress) {
        this.imageAddress = imageAddress;

        init();

        if(imageAddress != null && !imageAddress.isEmpty()) {
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
                    image.cancel();
                }
            });
            image.exceptionProperty().addListener(new ChangeListener<Exception>() {
                @Override
                public void changed(ObservableValue<? extends Exception> observableValue, Exception exception, Exception t1) {
                    image.cancel();
                }
            });
        }
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("cus_comp_views/image-item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SVGPath getBtnDelete() {
        return btnDelete;
    }

    public String getImageAddress() {
        return imageAddress;
    }
}
