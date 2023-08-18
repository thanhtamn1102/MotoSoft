package com.example.fashionshop.cus_comp;

import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu extends VBox {

    private List<MenuItem> menuItems;
    private MenuItem currentMenuItemSelection;

    public Menu() {
        init();
        menuItems = new ArrayList<>();
    }

    public Menu(List<MenuItem> menuItems) {
        init();
        this.menuItems = menuItems;
        addEvent(menuItems.toArray(new MenuItem[0]));
    }

    public void init() {
        setVgrow(this, Priority.ALWAYS);
        setSpacing(5);
    }

    public void addItems(MenuItem ...items) {
        menuItems.addAll(List.of(items));
        addEvent(items);
    }

    public void addItem(MenuItem item) {
        menuItems.add(item);
        addEvent(item);
    }

    public void show(String ...permissions) {
        getChildren().clear();
        getChildren().add(menuItems.get(0));

        List<String> permissionList = Arrays.asList(permissions);
        for (MenuItem item : menuItems) {
            if(permissionList.indexOf(item.getPermissionCode()) >= 0)
                getChildren().add(item);
        }
    }

    private void addEvent(MenuItem ...menuItems) {
        for (MenuItem item : menuItems) {
            item.setOnMousePressed(mouseEvent -> {
                if(item.getSubMenu().size() > 0) {
                    int index = this.getChildren().indexOf(item) + 1;

                    if(item.isSubMenuShowing()) {
                        this.getChildren().removeAll(item.getSubMenu());
                        item.setSubMenuShowing(false);
                    }
                    else {
                        this.getChildren().addAll(index, item.getSubMenu());
                        addEvent(item.getSubMenu().toArray(new MenuItem[0]));
                        item.setSubMenuShowing(true);
                    }
                }
                else {
                    onMenuItemMousePress((MenuItem) mouseEvent.getSource());
                }
            });
            item.setOnMouseEntered(mouseEvent -> {
                onMenuItemMouseEntered((MenuItem) mouseEvent.getSource());
            });
            item.setOnMouseExited(mouseEvent -> {
                onMenuItemMouseExited((MenuItem) mouseEvent.getSource());
            });
        }
    }

    private void onMenuItemMousePress(MenuItem menuItem) {
        if(!menuItem.equals(currentMenuItemSelection)) {
            currentMenuItemSelection.setStyle("-fx-background-color:  #0c75f5; -fx-border-radius: 5; -fx-background-radius: 5");
            currentMenuItemSelection.setIconColor("#f1f6fc");
            currentMenuItemSelection.setTextColor("#f1f6fc");

            setCurrentMenuItemSelection(menuItem);
        }
    }

    public void setCurrentMenuItemSelection(MenuItem currentMenuItemSelection) {
        this.currentMenuItemSelection = currentMenuItemSelection;
        currentMenuItemSelection.setStyle("-fx-background-color:  #f1f6fc; -fx-border-radius: 5; -fx-background-radius: 5");
        currentMenuItemSelection.setIconColor("#0c75f5");
        currentMenuItemSelection.setTextColor("#0c75f5");
    }

    public MenuItem getCurrentMenuItemSelection() {
        return currentMenuItemSelection;
    }

    private void onMenuItemMouseEntered(MenuItem menuItem) {
        if(!menuItem.equals(currentMenuItemSelection)) {
            menuItem.setStyle("-fx-background-color: #2E9CFF; -fx-border-radius: 5 ;-fx-background-radius: 5;");
        }
    }

    private void onMenuItemMouseExited(MenuItem menuItem) {
        if(!menuItem.equals(currentMenuItemSelection)) {
            menuItem.setStyle("-fx-background-color: #0c75f5; -fx-border-radius: 5 ;-fx-background-radius: 5");
        }
    }


}
