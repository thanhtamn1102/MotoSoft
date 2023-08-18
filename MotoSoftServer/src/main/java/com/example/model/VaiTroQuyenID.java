package com.example.model;

import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VaiTroQuyenID implements Serializable {

    @Serial
    private static final long serialVersionUID = -9016196399272088253L;

    private VaiTroNhomQuyenID nhomQuyen;
    private String quyen;

    public VaiTroQuyenID() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VaiTroQuyenID that = (VaiTroQuyenID) o;
        return Objects.equals(nhomQuyen, that.nhomQuyen) && Objects.equals(quyen, that.quyen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nhomQuyen, quyen);
    }
}
