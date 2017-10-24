package com.wangzai.lovesy.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class CollectionBean implements Serializable {

    private static final long serialVersionUID = -860109322133555950L;
    private int id;
    private String title;
    private String description;
    private String published_at;
    private boolean curated;
    private boolean featured;
    private int total_photos;
    @JSONField(name = "private")
    private boolean privateX;
    private String share_key;
    private CoverPhotoBean cover_photo;
    private UserBean user;
    private LinksBean links;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public boolean isCurated() {
        return curated;
    }

    public void setCurated(boolean curated) {
        this.curated = curated;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public int getTotal_photos() {
        return total_photos;
    }

    public void setTotal_photos(int total_photos) {
        this.total_photos = total_photos;
    }

    public boolean isPrivateX() {
        return privateX;
    }

    public void setPrivateX(boolean privateX) {
        this.privateX = privateX;
    }

    public String getShare_key() {
        return share_key;
    }

    public void setShare_key(String share_key) {
        this.share_key = share_key;
    }

    public CoverPhotoBean getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(CoverPhotoBean cover_photo) {
        this.cover_photo = cover_photo;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public LinksBean getLinks() {
        return links;
    }

    public void setLinks(LinksBean links) {
        this.links = links;
    }
}
