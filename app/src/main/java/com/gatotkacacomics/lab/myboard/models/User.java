package com.gatotkacacomics.lab.myboard.models;


/**
 * Created by sonywi on 01/11/2016.
 */

public class User {
    private String mId;
    private String mUsername;
    private String mName;
    private ProfileImage mProfile_image;
    private Links mLinks;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public ProfileImage getProfile_image() {
        return mProfile_image;
    }

    public void setProfile_image(ProfileImage profile_image) {
        this.mProfile_image = profile_image;
    }

    public Links getLinks() {
        return mLinks;
    }

    public void setLinks(Links links) {
        this.mLinks = links;
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + mId + '\'' +
                ", username='" + mUsername + '\'' +
                ", name='" + mName + '\'' +
                ", profile_image=" + mProfile_image.toString() +
                ", links=" + mLinks.toString() +
                '}';
    }
}
