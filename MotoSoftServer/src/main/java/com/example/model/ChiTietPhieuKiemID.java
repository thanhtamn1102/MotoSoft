package com.example.model;

import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ChiTietPhieuKiemID implements Serializable {

    @Serial
    private static final long serialVersionUID = -8038438506534759536L;

    private String phieuKiemKe;
    private String sanPham;

    public ChiTietPhieuKiemID() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietPhieuKiemID that = (ChiTietPhieuKiemID) o;
        return Objects.equals(phieuKiemKe, that.phieuKiemKe) && Objects.equals(sanPham, that.sanPham);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phieuKiemKe, sanPham);
    }
}
