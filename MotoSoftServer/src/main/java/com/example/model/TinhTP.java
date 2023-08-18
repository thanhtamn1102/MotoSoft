package com.example.model;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class TinhTP implements Serializable {

    @Serial
    private static final long serialVersionUID = -5550060571511058408L;

    @Id
    @Column(columnDefinition = "nvarchar(32)")
    @SerializedName(value = "code")

    private String id;

    @Column(columnDefinition = "nvarchar(32)")
    private String name;

    @Column(columnDefinition = "nvarchar(32)")
    private String slug;

    @Column(columnDefinition = "nvarchar(32)")
    private String type;

    @SerializedName(value = "name_with_type")
    @Column(columnDefinition = "nvarchar(32)")
    private String nameWithType;

    @SerializedName(value = "quan-huyen")
    @OneToMany(mappedBy = "tinhTP", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuanHuyen> dsQuanHuyen = new ArrayList<>();

    public TinhTP(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nameWithType;
    }

    public TinhTP() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameWithType() {
        return nameWithType;
    }

    public void setNameWithType(String nameWithType) {
        this.nameWithType = nameWithType;
    }

    public List<QuanHuyen> getDsQuanHuyen() {
        return dsQuanHuyen;
    }

    public void setDsQuanHuyen(List<QuanHuyen> dsQuanHuyen) {
        this.dsQuanHuyen = dsQuanHuyen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TinhTP tinhTP = (TinhTP) o;
        return Objects.equals(id, tinhTP.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
