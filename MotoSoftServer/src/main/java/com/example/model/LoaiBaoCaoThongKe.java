package com.example.model;

public enum LoaiBaoCaoThongKe {

    BAO_CAO_THEO_NGAY,
    BAO_CAO_THEO_THANG,
    BAO_CAO_THEO_NAM;

    @Override
    public String toString() {
        if(this == BAO_CAO_THEO_NGAY)
            return "Báo cáo theo ngày";
        else if(this == BAO_CAO_THEO_THANG)
            return "Báo cáo theo tháng";
        else if(this == BAO_CAO_THEO_NAM)
            return "Báo cáo theo năm";
        return super.toString();
    }
}
