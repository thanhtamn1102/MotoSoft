package com.example.model;

import java.io.Serializable;

public enum PhieuKiemKeStatus implements Serializable {

    TAO_MOI_CHO_KIEM_KE,
    DA_KIEM_KE_CHO_DUYET,
    DA_DUYET;

    @Override
    public String toString() {
        if(this == TAO_MOI_CHO_KIEM_KE)
            return "Tạo mới chờ kiểm kê";
        if(this == DA_KIEM_KE_CHO_DUYET)
            return "Đã kiểm kê chờ duyệt";
        if(this == DA_DUYET)
            return "Đã duyệt";
        return super.toString();
    }
}
