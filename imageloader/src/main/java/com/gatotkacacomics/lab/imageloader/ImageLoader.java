package com.gatotkacacomics.lab.imageloader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.gatotkacacomics.lab.imageloader.models.PreparedImage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class to image load processing
 * Created by sonywi on 02/11/2016.
 */
public class ImageLoader {

    private MemoryCache mMemoryCache = new MemoryCache();
    ExecutorService mExecutorService;
    private Future mFuture;


    /**
     * constructor
     */
    public ImageLoader() {
        mExecutorService = Executors.newFixedThreadPool(5);
    }


    /**
     * set size of memory cache
     * @param limit
     */
    public void setLimit(long limit) {
        Log.i("ImageLoader.setLimit", "setup memory cache limit=" + limit);
        mMemoryCache.setLimit(limit);
    }


    /**
     * main process
     * @param url
     * @param imageView
     */
    public void DisplayImage(String url, ImageView imageView, ProgressBar progressBar) {
        Log.i("ImageLoader.DispImage", "start process");
        mMemoryCache.imageViewMap.put(imageView, url);

        // get bitmap from memory cache
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap != null) {
            Log.i("ImageLoader.DispImage", "bitmap already exist, set image from cache");
            imageView.setImageBitmap(bitmap);
//            imageView.setVisibility(View.VISIBLE);
            if (progressBar != null) progressBar.setVisibility(View.GONE);
        } else {
            Log.i("ImageLoader.DispImage", "bitmap doesn't exist, download image");
//            imageView.setVisibility(View.GONE);
            if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
            queueImage(url, imageView, progressBar);
        }
    }


    /**
     * process to download image
     * @param url
     * @param imageView
     */
    private void queueImage(String url, ImageView imageView, ProgressBar progressBar) {
        Log.i("ImageLoader.queueImage", "prepare to execute download image");
        PreparedImage p = new PreparedImage(url, imageView, progressBar);
        mFuture = mExecutorService.submit(new Loader(p));
    }


    /**
     * to cancel download process
     */
    public void cancelDownload() {
        Log.i("ImageLoader.cancel", "cancel download for task " + mFuture.toString());
        mFuture.cancel(true);
    }



    /**
     * inner class to process load image from cache
     */
    class Loader implements Runnable {

        PreparedImage preparedImage;


        /**
         * constructor
         * @param p
         */
        Loader(PreparedImage p) {
            preparedImage = p;
        }


        @Override
        public void run() {
            if (mMemoryCache.imageViewReused(preparedImage)) {
                Log.i("Loader.run", "imageView reused");
                return;
            }

            Bitmap bmp = getBitmap(preparedImage.url);
            mMemoryCache.put(preparedImage.url, bmp);

            if (mMemoryCache.imageViewReused(preparedImage)) {
                Log.i("Loader.run", "imageView reused");
                return;
            }

            BitmapDisplayer bd = new BitmapDisplayer(bmp, preparedImage);
            Activity a = (Activity) preparedImage.imageView.getContext();
            a.runOnUiThread(bd);
        }
    }


    /**
     * inner class to display image to view
     */
    class BitmapDisplayer implements Runnable {

        private Bitmap bitmap;
        private PreparedImage preparedImage;


        /**
         * constructor
         * @param b
         * @param p
         */
        BitmapDisplayer(Bitmap b, PreparedImage p) {
            bitmap = b;
            preparedImage = p;
        }


        @Override
        public void run() {
            if (mMemoryCache.imageViewReused(preparedImage)) {
                return;
            }

            if (bitmap != null) {
//                preparedImage.imageView.setVisibility(View.VISIBLE);
                if (preparedImage.progressBar != null) preparedImage.progressBar.setVisibility(View.GONE);
                preparedImage.imageView.setImageBitmap(bitmap);
            }
        }
    }


    /**
     * get bitmap to display
     * @param url
     * @return
     */
    private Bitmap getBitmap(String url) {
        Log.i("ImageLoader.getBitmap", "try to get bitmap");

        Bitmap b = mMemoryCache.get(url);
        if (b != null) {
            Log.i("ImageLoader.getBitmap", "bitmap exist, return bitmap file");
            return b;
        }

        try {
            Log.i("ImageLoader.getBitmap", "download image file...");
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);

            bitmap = BitmapFactory.decodeStream((InputStream) imageUrl.getContent());
            return bitmap;
        } catch (Throwable t) {
            t.printStackTrace();

            if (t instanceof OutOfMemoryError) {
                mMemoryCache.clear();
            }
            return null;
        }
    }


    /**
     * clear memory cache
     */
    public void clearCache() {
        Log.i("ImageLoader", "clear memory cache");
        mMemoryCache.clear();
    }
}
