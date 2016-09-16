package com.liewjuntung.travelcompanion.models.pixabay;

import com.google.gson.annotations.SerializedName;

/**
 * Popular Movie App
 * Created by jtlie on 9/16/2016.
 */

public class Hit {

    /**
     * id : 195893
     * pageURL : https://pixabay.com/en/blossom-bloom-flower-yellow-close-195893/
     * type : photo
     * tags : blossom, bloom, flower
     * previewURL : https://pixabay.com/static/uploads/photo/2013/10/15/09/12/flower-195893_150.jpg
     * previewWidth : 150
     * previewHeight : 84
     * webformatURL : https://pixabay.com/get/35bbf209db8dc9f2fa36746403097ae226b796b9e13e39d2_640.jpg
     * webformatWidth : 640
     * webformatHeight : 360
     * imageWidth : 4000
     * imageHeight : 2250
     * views : 7671
     * downloads : 6439
     * favorites : 1
     * likes : 5
     * comments : 2
     * user_id : 48777
     * user : Josch13
     * userImageURL : https://pixabay.com/static/uploads/user/2013/11/05/02-10-23-764_250x250.jpg
     */

    @SerializedName("id")
    private int id;
    @SerializedName("pageURL")
    private String pageURL;
    @SerializedName("type")
    private String type;
    @SerializedName("tags")
    private String tags;
    @SerializedName("previewURL")
    private String previewURL;
    @SerializedName("previewWidth")
    private int previewWidth;
    @SerializedName("previewHeight")
    private int previewHeight;
    @SerializedName("webformatURL")
    private String webformatURL;
    @SerializedName("webformatWidth")
    private int webformatWidth;
    @SerializedName("webformatHeight")
    private int webformatHeight;
    @SerializedName("imageWidth")
    private int imageWidth;
    @SerializedName("imageHeight")
    private int imageHeight;
    @SerializedName("views")
    private int views;
    @SerializedName("downloads")
    private int downloads;
    @SerializedName("favorites")
    private int favorites;
    @SerializedName("likes")
    private int likes;
    @SerializedName("comments")
    private int comments;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("user")
    private String user;
    @SerializedName("userImageURL")
    private String userImageURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public int getPreviewWidth() {
        return previewWidth;
    }

    public void setPreviewWidth(int previewWidth) {
        this.previewWidth = previewWidth;
    }

    public int getPreviewHeight() {
        return previewHeight;
    }

    public void setPreviewHeight(int previewHeight) {
        this.previewHeight = previewHeight;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }

    public int getWebformatWidth() {
        return webformatWidth;
    }

    public void setWebformatWidth(int webformatWidth) {
        this.webformatWidth = webformatWidth;
    }

    public int getWebformatHeight() {
        return webformatHeight;
    }

    public void setWebformatHeight(int webformatHeight) {
        this.webformatHeight = webformatHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }
}
