package com.gatotkacacomics.lab.myboard.models;

/**
 * Created by sonywi on 01/11/2016.
 */

public class Urls {
    private String mRaw;
    private String mFull;
    private String mRegular;
    private String mSmall;
    private String mThumb;

    public String getRaw() {
        return mRaw;
    }

    public void setRaw(String raw) {
        this.mRaw = raw;
    }

    public String getFull() {
        return mFull;
    }

    public void setFull(String full) {
        this.mFull = full;
    }

    public String getRegular() {
        return mRegular;
    }

    public void setRegular(String regular) {
        this.mRegular = regular;
    }

    public String getSmall() {
        return mSmall;
    }

    public void setSmall(String small) {
        this.mSmall = small;
    }

    public String getThumb() {
        return mThumb;
    }

    public void setThumb(String thumb) {
        this.mThumb = thumb;
    }

    @Override
    public String toString() {
        return "Urls{" +
                "raw='" + mRaw + '\'' +
                ", full='" + mFull + '\'' +
                ", regular='" + mRegular + '\'' +
                ", small='" + mSmall + '\'' +
                ", thumb='" + mThumb + '\'' +
                '}';
    }
}
