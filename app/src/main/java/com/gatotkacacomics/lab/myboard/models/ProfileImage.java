package com.gatotkacacomics.lab.myboard.models;

/**
 * Created by sonywi on 01/11/2016.
 */

public class ProfileImage {
    private String mSmall;
    private String mMedium;
    private String mLarge;

    public String getSmall() {
        return mSmall;
    }

    public void setSmall(String small) {
        this.mSmall = small;
    }

    public String getMedium() {
        return mMedium;
    }

    public void setMedium(String medium) {
        this.mMedium = medium;
    }

    public String getLarge() {
        return mLarge;
    }

    public void setLarge(String large) {
        this.mLarge = large;
    }

    @Override
    public String toString() {
        return "ProfileImage{" +
                "small='" + mSmall + '\'' +
                ", medium='" + mMedium + '\'' +
                ", large='" + mLarge + '\'' +
                '}';
    }
}
