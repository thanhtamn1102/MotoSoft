package com.example.model;

import java.io.Serializable;

public enum PhieuNhapHangStatus implements Serializable {

    TAO_MOI,
    DA_NHAP_HANG;

    @Override
    public String toString() {
        if(this == TAO_MOI)
            return "Tạo mới";
        else if(this == DA_NHAP_HANG)
            return "Đã nhập hàng";
        return super.toString();
    }
}
