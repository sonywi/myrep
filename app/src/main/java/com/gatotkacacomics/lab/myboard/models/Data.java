package com.gatotkacacomics.lab.myboard.models;

import java.util.List;

/**
 * Created by sonywi on 01/11/2016.
 */

public class Data {
    private String mId;
    private String mCreated_at;
    private int mWidth;
    private int mHeight;
    private String mColor;
    private int mLikes;
    private boolean mLiked_by_user;
    private User mUser;
    private List mCurrent_user_collections;
    private Urls mUrls;
    private List<Category> mCategories;
    private Links mLinks;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getCreated_at() {
        return mCreated_at;
    }

    public void setCreated_at(String created_at) {
        this.mCreated_at = created_at;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        this.mColor = color;
    }

    public int getLikes() {
        return mLikes;
    }

    public void setLikes(int likes) {
        this.mLikes = likes;
    }

    public boolean isLiked_by_user() {
        return mLiked_by_user;
    }

    public void setLiked_by_user(boolean liked_by_user) {
        this.mLiked_by_user = liked_by_user;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    public List getCurrent_user_collections() {
        return mCurrent_user_collections;
    }

    public void setCurrent_user_collections(List current_user_collections) {
        this.mCurrent_user_collections = current_user_collections;
    }

    public Urls getUrls() {
        return mUrls;
    }

    public void setUrls(Urls urls) {
        this.mUrls = urls;
    }

    public List<Category> getCategories() {
        return mCategories;
    }

    public void setCategories(List<Category> categories) {
        this.mCategories = categories;
    }

    public Links getLinks() {
        return mLinks;
    }

    public void setLinks(Links links) {
        this.mLinks = links;
    }


    @Override
    public String toString() {
        return "Data{" +
                "id='" + mId + '\'' +
                ", created_at='" + mCreated_at + '\'' +
                ", width=" + mWidth +
                ", height=" + mHeight +
                ", color='" + mColor + '\'' +
                ", likes=" + mLikes +
                ", liked_by_user=" + mLiked_by_user +
                ", user=" + mUser.toString() +
                ", current_user_collections=" + mCurrent_user_collections +
                ", urls=" + mUrls.toString() +
                ", categories=" + mCategories +
                ", links=" + mLinks.toString() +
                '}';
    }
}
