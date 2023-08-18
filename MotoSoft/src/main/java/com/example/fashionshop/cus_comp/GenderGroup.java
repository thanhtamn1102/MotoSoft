package com.example.fashionshop.cus_comp;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class GenderGroup {

    private HBox btnNam, btnNu;
    private Label lblBtnNam, lblBtnNu;

    private boolean disable;
    private boolean btnNamSelected;
    private boolean btnNuSelected;

    public GenderGroup(HBox btnNam, Label lblBtnNam, HBox btnNu, Label lblBtnNu) {
        this.btnNam = btnNam;
        this.btnNu = btnNu;
        this.lblBtnNam = lblBtnNam;
        this.lblBtnNu = lblBtnNu;

        disable = false;
        btnNamSelected = true;
        btnNuSelected = false;

        setBtnNamSelected(true);

        addEvents();
    }

    private void addEvents() {
        btnNam.setOnMousePressed(mouseEvent -> {
            if(btnNamSelected == false) {
                setBtnNamSelected(true);
            }
        });

        btnNu.setOnMousePressed(mouseEvent -> {
            if(btnNuSelected == false) {
                setBtnNuSelected(true);
            }
        });
    }

    public boolean isBtnNamSelected() {
        return btnNamSelected;
    }

    public void setBtnNamSelected(boolean btnNamSelected) {
        this.btnNamSelected = btnNamSelected;

        if(btnNamSelected) {
            btnNam.setStyle("-fx-background-color:  #0C75F5; -fx-border-color: #0C75F5; -fx-border-radius: 15; -fx-background-radius: 15");
            lblBtnNam.setTextFill(Color.WHITE);
            btnNu.setStyle("-fx-background-color: white; -fx-border-color:  #FFA500; -fx-background-radius: 15; -fx-border-radius: 15");
            lblBtnNu.setTextFill(Color.web("#cecece"));

            btnNuSelected = false;
        } else {
            btnNu.setStyle("-fx-background-color: #FFA500; -fx-border-color:  #FFA500; -fx-background-radius: 15; -fx-border-radius: 15");
            lblBtnNu.setTextFill(Color.WHITE);
            btnNam.setStyle("-fx-background-color:  white; -fx-border-color: #0C75F5; -fx-border-radius: 15; -fx-background-radius: 15");
            lblBtnNam.setTextFill(Color.web("#cecece"));

            btnNuSelected = true;
        }
    }

    public boolean isBtnNuSelected() {
        return btnNuSelected;
    }

    public void setBtnNuSelected(boolean btnNuSelected) {
        this.btnNuSelected = btnNuSelected;

        if(btnNuSelected) {
            btnNu.setStyle("-fx-background-color: #FFA500; -fx-border-color:  #FFA500; -fx-background-radius: 15; -fx-border-radius: 15");
            lblBtnNu.setTextFill(Color.WHITE);
            btnNam.setStyle("-fx-background-color:  white; -fx-border-color: #0C75F5; -fx-border-radius: 15; -fx-background-radius: 15");
            lblBtnNam.setTextFill(Color.web("#cecece"));

            btnNamSelected = false;
        } else {
            btnNam.setStyle("-fx-background-color:  #0C75F5; -fx-border-color: #0C75F5; -fx-border-radius: 15; -fx-background-radius: 15");
            lblBtnNam.setTextFill(Color.WHITE);
            btnNu.setStyle("-fx-background-color: white; -fx-border-color:  #FFA500; -fx-background-radius: 15; -fx-border-radius: 15");
            lblBtnNu.setTextFill(Color.web("#cecece"));

            btnNamSelected = true;
        }
    }

    private void btnNamSelect() {

    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
        btnNam.setDisable(disable);
        btnNu.setDisable(disable);
    }
}
