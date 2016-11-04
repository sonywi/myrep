package com.gatotkacacomics.lab.myboard.models;

/**
 * Created by sonywi on 01/11/2016.
 */

public class Category {
    private int mId;
    private String mTitle;
    private int mPhoto_count;
    private Links mLinks;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public int getPhoto_count() {
        return mPhoto_count;
    }

    public void setPhoto_count(int photo_count) {
        this.mPhoto_count = photo_count;
    }

    public Links getLinks() {
        return mLinks;
    }

    public void setLinks(Links links) {
        this.mLinks = links;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + mId +
                ", title='" + mTitle + '\'' +
                ", photo_count=" + mPhoto_count +
                ", links=" + mLinks.toString() +
                '}';
    }
}
