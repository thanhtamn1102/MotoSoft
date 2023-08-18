package com.example.model;

import java.io.Serializable;

public enum DonNhapHangStatus implements Serializable {

    TAO_MOI,
    DA_HOAN_THANH;

    @Override
    public String toString() {
        if(this == TAO_MOI)
            return "Tạo mới";
        if(this == DA_HOAN_THANH)
            return "Đã hoàn thành";
        return super.toString();
    }

    DonNhapHangStatus() {
    }
}
