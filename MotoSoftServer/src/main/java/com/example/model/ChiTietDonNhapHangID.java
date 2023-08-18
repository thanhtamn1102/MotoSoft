package com.example.model;

import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ChiTietDonNhapHangID implements Serializable {

    @Serial
    private static final long serialVersionUID = -1142055500387547384L;

    private String donNhapHang;
    private String sanPham;

    public ChiTietDonNhapHangID() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietDonNhapHangID that = (ChiTietDonNhapHangID) o;
        return Objects.equals(donNhapHang, that.donNhapHang) && Objects.equals(sanPham, that.sanPham);
    }

    @Override
    public int hashCode() {
        return Objects.hash(donNhapHang, sanPham);
    }
}
