package com.example.model;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;


@Entity
public class XaPhuong implements Serializable {

    @Serial
    private static final long serialVersionUID = -910142938915602863L;

    @Id
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

    @Column(columnDefinition = "nvarchar(128)")
    private String path;

    @SerializedName(value = "path_with_type")
    @Column(columnDefinition = "nvarchar(128)")
    private String pathWithType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_code", columnDefinition = "nvarchar(32)")
    @ToString.Exclude
    private QuanHuyen quanHuyen;

    @Override
    public String toString() {
        return nameWithType;
    }

    public XaPhuong(String id, String name, String type, String slug, String nameWithType, String path, String pathWithType, QuanHuyen quanHuyen) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.slug = slug;
        this.nameWithType = nameWithType;
        this.path = path;
        this.pathWithType = pathWithType;
        this.quanHuyen = quanHuyen;
    }

    public XaPhuong() {
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

    public QuanHuyen getQuanHuyen() {
        return quanHuyen;
    }

    public void setQuanHuyen(QuanHuyen quanHuyen) {
        this.quanHuyen = quanHuyen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XaPhuong xaPhuong = (XaPhuong) o;
        return Objects.equals(id, xaPhuong.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
