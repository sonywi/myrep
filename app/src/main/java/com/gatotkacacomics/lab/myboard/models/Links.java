package com.gatotkacacomics.lab.myboard.models;

/**
 * Created by sonywi on 01/11/2016.
 */

public class Links {
    private String mSelf;
    private String mHtml;
    private String mDownload;
    private String mPhotos;
    private String mLikes;

    public String getSelf() {
        return mSelf;
    }

    public void setSelf(String self) {
        this.mSelf = self;
    }

    public String getHtml() {
        return mHtml;
    }

    public void setHtml(String html) {
        this.mHtml = html;
    }

    public String getDownload() {
        return mDownload;
    }

    public void setDownload(String download) {
        this.mDownload = download;
    }

    public String getPhotos() {
        return mPhotos;
    }

    public void setPhotos(String photos) {
        this.mPhotos = photos;
    }

    public String getLikes() {
        return mLikes;
    }

    public void setLikes(String likes) {
        this.mLikes = likes;
    }

    @Override
    public String toString() {
        return "Links{" +
                "self='" + mSelf + '\'' +
                ", html='" + mHtml + '\'' +
                ", download='" + mDownload + '\'' +
                ", photos='" + mPhotos + '\'' +
                ", likes='" + mLikes + '\'' +
                '}';
    }
}
