package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serial;
import java.io.Serializable;


@Embeddable
public class DiaChi implements Serializable {

    @Serial
    private static final long serialVersionUID = -8221767271711562361L;

    @ManyToOne
    @JoinColumn(name = "maTinhTP")
    private TinhTP tinhTP;

    @ManyToOne
    @JoinColumn(name = "maQuanHuyen")
    private QuanHuyen quanHuyen;

    @ManyToOne
    @JoinColumn(name = "maXaPhuong")
    private XaPhuong xaPhuong;

    @Column(columnDefinition = "nvarchar(255)")
    private String diaChiChiTiet;

    @Override
    public String toString() {
        return diaChiChiTiet + ", " + xaPhuong.getNameWithType() + ", "  + quanHuyen.getNameWithType() + ", " + tinhTP.getNameWithType();
    }

    public DiaChi(TinhTP tinhTP, QuanHuyen quanHuyen, XaPhuong xaPhuong, String diaChiChiTiet) {
        this.tinhTP = tinhTP;
        this.quanHuyen = quanHuyen;
        this.xaPhuong = xaPhuong;
        this.diaChiChiTiet = diaChiChiTiet;
    }

    public DiaChi() {
    }

    public TinhTP getTinhTP() {
        return tinhTP;
    }

    public void setTinhTP(TinhTP tinhTP) {
        this.tinhTP = tinhTP;
    }

    public QuanHuyen getQuanHuyen() {
        return quanHuyen;
    }

    public void setQuanHuyen(QuanHuyen quanHuyen) {
        this.quanHuyen = quanHuyen;
    }

    public XaPhuong getXaPhuong() {
        return xaPhuong;
    }

    public void setXaPhuong(XaPhuong xaPhuong) {
        this.xaPhuong = xaPhuong;
    }

    public String getDiaChiChiTiet() {
        return diaChiChiTiet;
    }

    public void setDiaChiChiTiet(String diaChiChiTiet) {
        this.diaChiChiTiet = diaChiChiTiet;
    }

}
