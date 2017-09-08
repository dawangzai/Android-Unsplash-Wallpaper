package com.wangzai.lovesy.core.net.bean;

import java.util.List;

public class UserBean {
    private String id;
    private String username;
    private String name;
    private String first_name;
    private String last_name;
    private String portfolio_url;
    private String bio;
    private String location;
    private int total_likes;
    private int total_photos;
    private int total_collections;
    private ProfileImageBean profile_image;
    private List<PhotoBean> photos;
    private LinksBean links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPortfolio_url() {
        return portfolio_url;
    }

    public void setPortfolio_url(String portfolio_url) {
        this.portfolio_url = portfolio_url;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(int total_likes) {
        this.total_likes = total_likes;
    }

    public int getTotal_photos() {
        return total_photos;
    }

    public void setTotal_photos(int total_photos) {
        this.total_photos = total_photos;
    }

    public int getTotal_collections() {
        return total_collections;
    }

    public void setTotal_collections(int total_collections) {
        this.total_collections = total_collections;
    }

    public ProfileImageBean getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(ProfileImageBean profile_image) {
        this.profile_image = profile_image;
    }

    public LinksBean getLinks() {
        return links;
    }

    public void setLinks(LinksBean links) {
        this.links = links;
    }

    public List<PhotoBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoBean> photos) {
        this.photos = photos;
    }
}
