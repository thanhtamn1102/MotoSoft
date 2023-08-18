package com.example.model;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class QuanHuyen implements Serializable {

    @Serial
    private static final long serialVersionUID = -1849214354808081610L;

    @Id
    @Column(columnDefinition = "nvarchar(32)")
    @SerializedName(value = "code")

    private String id;

    @Column(columnDefinition = "nvarchar(32)")
    private String name;

    @Column(columnDefinition = "nvarchar(32)")
    private String type;

    @Column(columnDefinition = "nvarchar(32)")
    private String slug;

    @SerializedName(value = "name_with_type")
    @Column(columnDefinition = "nvarchar(32)")
    private String nameWithType;

    @Column(columnDefinition = "nvarchar(64)")
    private String path;

    @SerializedName(value = "path_with_type")
    @Column(columnDefinition = "nvarchar(64)")
    private String pathWithType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_code", columnDefinition = "nvarchar(32)")
    private TinhTP tinhTP;

    @SerializedName(value = "xa-phuong")
    @OneToMany(mappedBy = "quanHuyen", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<XaPhuong> dsXaPhuong = new ArrayList<>();

    public QuanHuyen(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nameWithType;
    }

    public QuanHuyen() {
    }

    public QuanHuyen(String id, String name, String type, String slug, String nameWithType, String path, String pathWithType, TinhTP tinhTP, List<XaPhuong> dsXaPhuong) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.slug = slug;
        this.nameWithType = nameWithType;
        this.path = path;
        this.pathWithType = pathWithType;
        this.tinhTP = tinhTP;
        this.dsXaPhuong = dsXaPhuong;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getNameWithType() {
        return nameWithType;
    }

    public void setNameWithType(String nameWithType) {
        this.nameWithType = nameWithType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathWithType() {
        return pathWithType;
    }

    public void setPathWithType(String pathWithType) {
        this.pathWithType = pathWithType;
    }

    public TinhTP getTinhTP() {
        return tinhTP;
    }

    public void setTinhTP(TinhTP tinhTP) {
        this.tinhTP = tinhTP;
    }

    public List<XaPhuong> getDsXaPhuong() {
        return dsXaPhuong;
    }

    public void setDsXaPhuong(List<XaPhuong> dsXaPhuong) {
        this.dsXaPhuong = dsXaPhuong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuanHuyen quanHuyen = (QuanHuyen) o;
        return Objects.equals(id, quanHuyen.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

