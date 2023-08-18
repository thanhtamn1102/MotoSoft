package com.example.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaoCaoThongKeDoanhThu implements Serializable {

    @Serial
    private static final long serialVersionUID = -6592701902332169554L;

    private double tongDoanhThu;
    private double tongVon;
    private double tongTraHang;
    private double tongLoiNhuan;

    private List<Object[]> chiTietBaoCao;

    public BaoCaoThongKeDoanhThu() {
        chiTietBaoCao = new ArrayList<>();
    }

    public double getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(double tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }

    public double getTongVon() {
        return tongVon;
    }

    public void setTongVon(double tongVon) {
        this.tongVon = tongVon;
    }

    public double getTongTraHang() {
        return tongTraHang;
    }

    public void setTongTraHang(double tongTraHang) {
        this.tongTraHang = tongTraHang;
    }

    public double getTongLoiNhuan() {
        return tongLoiNhuan;
    }

    public void setTongLoiNhuan(double tongLoiNhuan) {
        this.tongLoiNhuan = tongLoiNhuan;
    }

    public List<Object[]> getChiTietBaoCao() {
        return chiTietBaoCao;
    }

    public void setChiTietBaoCao(List<Object[]> chiTietBaoCao) {
        this.chiTietBaoCao.clear();
        this.chiTietBaoCao.addAll(chiTietBaoCao);
    }
}
