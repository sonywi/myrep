package com.gatotkacacomics.lab.imageloader;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.gatotkacacomics.lab.imageloader.models.PreparedImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Class to manage memory cache
 * Created by sonywi on 02/11/2016.
 */
public class MemoryCache {

    // cache bitmap map
    private Map<String, Bitmap> cache = Collections.synchronizedMap(new LinkedHashMap<String, Bitmap>(10, 1.5f, true));

    // cache imageView map
    public Map<ImageView, String> imageViewMap = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());

    // create list to sort image by most recently used
    private List mOrder = new ArrayList();

    // current allocated size
    private long mCacheSize = 0;

    // max memory allocated size, set default to 50mb
    private long mLimit = 50000000;


    /**
     * constructor
     */
    public MemoryCache() {
        // use 25% of heap size
        setLimit(Runtime.getRuntime().maxMemory()/4);
    }


    /**
     * set max allocation size of memory cache
     * @param new_limit
     */
    public void setLimit(long new_limit) {
        mLimit = new_limit;
        Log.i("ImageLoader", "MemoryCche will use up to " + mLimit/1024./1024.+"MB");
    }


    /**
     * check if imageView reused
     * @param image
     * @return
     */
    public boolean imageViewReused(PreparedImage image) {
        String tag = imageViewMap.get(image.imageView);
        if (tag == null || !tag.equals(image.url)) {
            return true;
        }

        return false;
    }


    /**
     * get bitmap from cache map
     * @param id
     * @return
     */
    public Bitmap get(String id) {
        Log.i("MemoryCache.get", "try to get bitmap from memory cache");

        try {
            if (!cache.containsKey(id)) {
                Log.i("MemoryCache.get", "bitmap not found");
                return null;
            }

            Log.i("MemoryCache.get", "bitmap found in cache");
            mOrder.remove(id);
            mOrder.add(id);
            Log.i("MemoryCache.get", "url to move:" + id);
            return cache.get(id);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * put bitmap to cache
     * @param id
     * @param bitmap
     */
    public void put(String id, Bitmap bitmap) {
        try {
            if (cache.containsKey(id)) {
                mCacheSize -= getSizeInBytes(cache.get(id));
            }

            Log.i("MemoryCache.put", "url to add:" + id);
            cache.put(id, bitmap);
            mOrder.add(id);
            mCacheSize += getSizeInBytes(bitmap);

            Log.i("MemoryCache.put", "cacheSize=" + mCacheSize + " length=" + cache.size());
            if (mCacheSize > mLimit) {
                Log.i("MemoryCache.put", "over limit size, delete some image");
                Iterator<Map.Entry<String, Bitmap>> iter = cache.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, Bitmap> entry = iter.next();
                    mCacheSize -= getSizeInBytes(entry.getValue());
                    iter.remove();

                    // remove object at order list
                    mOrder.remove(entry.getKey());
                    Log.i("MemoryCache.put", "url to delete:" + entry.getKey());
                    if (mCacheSize <= mLimit) break;
                }

                Log.i("ImageLoader.put", "after delete some image, length=" + cache.size());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    /**
     * get bitmap size in bytes
     * @param bitmap
     * @return
     */
    private long getSizeInBytes(Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }

        return bitmap.getRowBytes() * bitmap.getHeight();
    }


    /**
     * clear image cache
     */
    public void clear() {
        try {
            mOrder.clear();
            cache.clear();
            imageViewMap.clear();
            mCacheSize = 0;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
