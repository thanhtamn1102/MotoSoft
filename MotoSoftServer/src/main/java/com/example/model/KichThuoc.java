package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
public class KichThuoc implements Serializable {

    @Serial
    private static final long serialVersionUID = 5370876053031717153L;

    @Column(nullable = false)
    private double dai;

    @Column(nullable = false)
    private double rong;

    @Column(nullable = false)
    private double cao;

    public KichThuoc() {

    }

    public KichThuoc(double dai, double rong, double cao) {
        this.dai = dai;
        this.rong = rong;
        this.cao = cao;
    }

    public double getDai() {
        return dai;
    }

    public void setDai(double dai) {
        this.dai = dai;
    }

    public double getRong() {
        return rong;
    }

    public void setRong(double rong) {
        this.rong = rong;
    }

    public double getCao() {
        return cao;
    }

    public void setCao(double cao) {
        this.cao = cao;
    }

    @Override
    public String toString() {
        return dai + "x" + rong + "x" + cao;
    }
}
