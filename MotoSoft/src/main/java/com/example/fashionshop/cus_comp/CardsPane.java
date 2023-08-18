package com.example.fashionshop.cus_comp;

import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Hashtable;

public class CardsPane extends VBox {

    private Hashtable<String, Node> cards;
    private Node currentCard;

    public CardsPane() {
        cards = new Hashtable<>();
    }

    public void add(String name, Node card) {
        cards.put(name, card);
    }

    public void show(String name) {
        try {
            getChildren().clear();

            if(!name.isEmpty()) {
                Node card = cards.get(name);
                if(card != currentCard) {
                    getChildren().add(card);
                    currentCard = card;
                    setVgrow(card, Priority.ALWAYS);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
