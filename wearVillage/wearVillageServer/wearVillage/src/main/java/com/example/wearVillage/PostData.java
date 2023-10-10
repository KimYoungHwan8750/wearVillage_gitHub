package com.example.wearVillage;

import jakarta.persistence.Entity;
import lombok.ToString;

@ToString
public class PostData {
    private String postId;
    private String postWriterId;
    private String postSubtitle;
    private String postPrice;
    private String postRentDefaultPrice;
    private String postRentDayPrice;
    private String postTagTop;
    private String postTagMiddle;
    private String postMapInfo;
    private String postText;
    private String postDate;
    private String postModifyDate;
    private String postThumbnailUrl;

    public String getPostId() {
        return this.postId;
    }

    public String getPostWriterId() {
        return this.postWriterId;
    }

    public String getPostSubtitle() {
        return this.postSubtitle;
    }

    public String getPostPrice() {
        return this.postPrice;
    }

    public String getPostRentDefaultPrice() {
        return this.postRentDefaultPrice;
    }

    public String getPostRentDayPrice() {
        return this.postRentDayPrice;
    }

    public String getPostTagTop() {
        return this.postTagTop;
    }

    public String getPostTagMiddle() {
        return this.postTagMiddle;
    }

    public String getPostMapInfo() {
        return this.postMapInfo;
    }

    public String getPostText() {
        return this.postText;
    }

    public String getPostDate() {
        return this.postDate;
    }

    public String getPostModifyDate() {
        return this.postModifyDate;
    }

    public String getPostThumbnailUrl() {
        return this.postThumbnailUrl;
    }



    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setPostWriterId(String postWriterId) {
        this.postWriterId = postWriterId;
    }

    public void setPostSubtitle(String postSubtitle) {
        this.postSubtitle = postSubtitle;
    }

    public void setPostPrice(String postPrice) {
        this.postPrice = postPrice;
    }

    public void setPostRentDefaultPrice(String postRentDefaultPrice) {
        this.postRentDefaultPrice = postRentDefaultPrice;
    }

    public void setPostRentDayPrice(String postRentDayPrice) {
        this.postRentDayPrice = postRentDayPrice;
    }

    public void setPostTagTop(String postTagTop) {
        this.postTagTop= postTagTop;
    }
    public void setPostTagMiddle(String postTagMiddle) {
        this.postTagMiddle = postTagMiddle;
    }
    public void setPostMapInfo(String postMapInfo) {
        this.postMapInfo= postMapInfo;
    }

    public void setPostText(String postText) {
        this.postText =  postText ;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate ;
    }

    public void setPostModifyDate(String postModifyDate) {
        this.postModifyDate = postModifyDate;
    }

    public void setPostThumbnailUrl(String postThumbnailUrl) {
        this.postThumbnailUrl = postThumbnailUrl;
    }
}