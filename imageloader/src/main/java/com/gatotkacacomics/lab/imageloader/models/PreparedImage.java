package com.gatotkacacomics.lab.imageloader.models;

import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by sonywi on 02/11/2016.
 */
public class PreparedImage {

    public String url;
    public ImageView imageView;
    public ProgressBar progressBar;


    /**
     * constructor
     * @param u
     * @param i
     * @param p
     */
    public PreparedImage(String u, ImageView i, ProgressBar p) {
        url = u;
        imageView = i;
        progressBar = p;
    }
}
