package com.cleverzheng.wallpaper.bean;

import java.util.List;

/**
 * @author：cleverzheng
 * @date：2017/1/17:17:08
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class PhotoBean {

    private String id;
    private String created_at;
    private int width;
    private int height;
    private String color;
    private int downloads;
    private int likes;
    private boolean liked_by_user;
    private ExifBean exif;
    private LocationBean location;
    private List<CollectionBean> current_user_collections;
    private UrlsBean urls;
    private List<CategoriesBean> categories;
    private LinksBean links;
    private UserBean user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLiked_by_user() {
        return liked_by_user;
    }

    public void setLiked_by_user(boolean liked_by_user) {
        this.liked_by_user = liked_by_user;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public UrlsBean getUrls() {
        return urls;
    }

    public void setUrls(UrlsBean urls) {
        this.urls = urls;
    }

    public LinksBean getLinks() {
        return links;
    }

    public void setLinks(LinksBean links) {
        this.links = links;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public List<CollectionBean> getCurrent_user_collections() {
        return current_user_collections;
    }

    public void setCurrent_user_collections(List<CollectionBean> current_user_collections) {
        this.current_user_collections = current_user_collections;
    }

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public ExifBean getExif() {
        return exif;
    }

    public void setExif(ExifBean exif) {
        this.exif = exif;
    }
}
