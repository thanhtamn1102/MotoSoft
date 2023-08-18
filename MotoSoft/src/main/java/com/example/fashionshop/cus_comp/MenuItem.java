package com.example.fashionshop.cus_comp;

import com.example.fashionshop.MotoSoftApp;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.io.IOException;

public class MenuItem extends HBox {

    @FXML private Region icon;
    @FXML private Label label;

    private String menuName;
    private String permissionCode;
    private String url;
    private SVGPath svg = new SVGPath();
    private ObservableList<MenuItem> subMenu = FXCollections.observableArrayList();
    private BooleanProperty subMenuShowing;

    public MenuItem() throws Exception {
        init();
        setContent("M0 64C0 28.7 28.7 0 64 0H224V128c0 17.7 14.3 32 32 32H384v38.6C310.1 219.5 256 287.4 256 368c0 59.1 29.1 111.3 73.7 143.3c-3.2 .5-6.4 .7-9.7 .7H64c-35.3 0-64-28.7-64-64V64zm384 64H256V0L384 128zm48 384c-79.5 0-144-64.5-144-144s64.5-144 144-144s144 64.5 144 144s-64.5 144-144 144zm59.3-180.7c6.2-6.2 6.2-16.4 0-22.6s-16.4-6.2-22.6 0L432 345.4l-36.7-36.7c-6.2-6.2-16.4-6.2-22.6 0s-6.2 16.4 0 22.6L409.4 368l-36.7 36.7c-6.2 6.2-6.2 16.4 0 22.6s16.4 6.2 22.6 0L432 390.6l36.7 36.7c6.2 6.2 16.4 6.2 22.6 0s6.2-16.4 0-22.6L454.6 368l36.7-36.7z",
                "Menu Name");
    }

    public MenuItem(String icon, String menuName) {
        init();
        setMenuName(menuName);
        setPermissionCode("");
        setUrl("");
        setContent(icon, menuName);
        subMenuShowing = new SimpleBooleanProperty(false);
    }

    public MenuItem(String icon, String menuName, String permissionCode, String url) {
        init();
        setMenuName(menuName);
        setPermissionCode(permissionCode);
        setUrl(url);
        setContent(icon, menuName);
        subMenuShowing = new SimpleBooleanProperty(false);
    }

    public MenuItem(String icon, String menuName, String permissionCode, String url, MenuItem ...subMenuItems) {
        init();
        setMenuName(menuName);
        setPermissionCode(permissionCode);
        setUrl(url);
        setContent(icon, menuName);
        subMenuShowing = new SimpleBooleanProperty(false);
        this.subMenu.setAll(subMenuItems);
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("cus_comp_views/menu-item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setContent(String icon, String name) {
        setIcon(icon);
        setText(name);
    }

    public void setIcon(String content) {
        svg.setContent(content);
        icon.setShape(svg);
    }

    public SVGPath getIcon() {
        return svg;
    }

    public void setIconColor(String colorCode) {
        icon.setStyle("-fx-background-color: " + colorCode + ";");
    }

    public void setText(String text) {
        label.setText(text);
    }
    public String getText() {
        return label.getText();
    }
    public void setTextColor(String colorCode) {
        label.setTextFill(Color.web(colorCode));
    }
    public void setPermissionCode(String codeAction) {
        this.permissionCode = codeAction;
    }
    public String getPermissionCode() {
        return permissionCode;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
        label.setText(menuName);
    }

    public ObservableList<MenuItem> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(ObservableList<MenuItem> subMenu) {
        this.subMenu.setAll(subMenu);
    }

    public void setSubMenu(MenuItem ...menuItems) {
        this.subMenu.setAll(menuItems);
    }

    public boolean isSubMenuShowing() {
        return subMenuShowing.get();
    }

    public BooleanProperty subMenuShowingProperty() {
        return subMenuShowing;
    }

    public void setSubMenuShowing(boolean subMenuShowing) {
        this.subMenuShowing.set(subMenuShowing);
    }
}
