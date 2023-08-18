module com.example.fashionshop {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires guava;
    requires poi;
    requires static lombok;
    requires com.google.gson;
    requires java.json;
    requires java.rmi;
    requires MotoSoft;

    opens com.example.fashionshop to javafx.fxml;
    exports com.example.fashionshop;

    opens com.example.fashionshop.model to org.hibernate.orm.core, com.google.gson;
    exports com.example.fashionshop.model;

    exports com.example.fashionshop.controller;
    opens com.example.fashionshop.controller to javafx.fxml;

    exports com.example.fashionshop.cus_comp;
    opens com.example.fashionshop.cus_comp to javafx.fxml;

}