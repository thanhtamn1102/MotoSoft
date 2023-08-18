package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class PageTongQuanTKDT extends VBox {

    private static final int NUM_TOP_DOANH_THU = 10;

    @FXML private VBox boxTongQuanTKDT;

    private ObservableList<Object[]> chiTietBaoCao;

    public PageTongQuanTKDT(ObservableList<Object[]> chiTietBaoCao) {
        this.chiTietBaoCao = chiTietBaoCao;

        init();
        addControls();
        addEvents();
        loadData();
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("pages/page-tong-quan-tkdt.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addControls() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Ngày");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Tổng doanh thu theo ngày");

        BarChart<Number, String> barChart = new BarChart<>(yAxis, xAxis);

        XYChart.Series<Number, String> dataSeries1 = new XYChart.Series<>();

        chiTietBaoCao.addListener((ListChangeListener<Object[]>) change ->
                dataSeries1.setData(
                    FXCollections.observableArrayList(
                            chiTietBaoCao
                                    .subList(0, chiTietBaoCao.size() >= NUM_TOP_DOANH_THU ? NUM_TOP_DOANH_THU : chiTietBaoCao.size())
                                    .stream()
                                    .map(objects -> new XYChart.Data<>((Number) objects[12], String.valueOf(objects[0])))
                                    .toList()
                )
        ));

        barChart.getData().add(dataSeries1);

        barChart.setTitle("Thống kê doanh thu theo ngày");

        boxTongQuanTKDT.getChildren().add(barChart);
    }

    private void addEvents() {

    }

    private void loadData() {

    }
}
