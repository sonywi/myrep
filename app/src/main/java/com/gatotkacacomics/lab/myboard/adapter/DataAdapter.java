package com.gatotkacacomics.lab.myboard.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gatotkacacomics.lab.imageloader.ImageLoader;
import com.gatotkacacomics.lab.myboard.R;
import com.gatotkacacomics.lab.myboard.models.Data;
import com.gatotkacacomics.lab.myboard.models.ProfileImage;
import com.gatotkacacomics.lab.myboard.models.Urls;
import com.gatotkacacomics.lab.myboard.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Adapter clas for board item
 * Created by sonywi on 01/11/2016.
 */

public class DataAdapter extends BaseAdapter {

    private Context mContext;
    private List mObjects = new ArrayList();
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;


    /**
     * constructor
     * @param context
     * @param dataList
     */
    public DataAdapter(Context context, List dataList) {
        this.mContext = context;
        this.mObjects = dataList;
        this.mInflater = (LayoutInflater.from(context));

        this.mImageLoader = new ImageLoader();
    }


    /**
     * holder class
     */
    public class ViewHolder {
        private TextView tvDate;
        private ImageView imgContent;
        private ProgressBar pbContent;
        private ImageView imgContentRefresh;
        private ImageView imgLike;
        private TextView tvLike;
        private ImageView imgProfile;
        private TextView tvProfile;
        private Button btnDownload;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Object getItem(int i) {
        return mObjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int pos, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        final int position = pos;
        final ViewHolder holder;


        // setup view component
        if (view == null) {
            view = mInflater.inflate(R.layout.board_item, viewGroup, false);
            holder = new ViewHolder();

            holder.tvDate = (TextView) view.findViewById(R.id.tv_date);
            holder.imgContent = (ImageView) view.findViewById(R.id.img_content);
            holder.pbContent = (ProgressBar) view.findViewById(R.id.pb_content);
            holder.imgContentRefresh = (ImageView) view.findViewById(R.id.img_content_refresh);
            holder.tvLike = (TextView) view.findViewById(R.id.tv_like);
            holder.imgLike = (ImageView) view.findViewById(R.id.img_like);
            holder.imgProfile = (ImageView) view.findViewById(R.id.img_profile);
            holder.tvProfile = (TextView) view.findViewById(R.id.tv_profile);
            holder.btnDownload = (Button) view.findViewById(R.id.btn_download);

            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        // get data by position
        final Data data = (Data) getItem(pos);
        Log.i("DataAdapter", "Data : " + data.toString());


        // --- date component ---
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MMM, dd yyyy");
        try {
            date = format.parse(data.getCreated_at());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvDate.setText(format.format(date));
        // --- end of date component ---


        final Urls urls = data.getUrls();
        final String url_content = urls.getSmall();
        holder.imgContent.setBackgroundColor(Color.parseColor(data.getColor()));

        // --- set temporal image content while its loading ---
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = width * data.getHeight() / data.getWidth();
        Log.i("DataAdapter", "width:" + width + " height:" + height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        holder.imgContent.setImageBitmap(bitmap);
        holder.imgContentRefresh.setVisibility(View.GONE);
        mImageLoader.DisplayImage(url_content, holder.imgContent, holder.pbContent);

        // cancel download image
        holder.pbContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imgContentRefresh.setVisibility(View.VISIBLE);
                holder.pbContent.setVisibility(View.GONE);
                mImageLoader.cancelDownload();
            }
        });

        // retry download image
        holder.imgContentRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imgContentRefresh.setVisibility(View.GONE);
                holder.pbContent.setVisibility(View.VISIBLE);
                mImageLoader.DisplayImage(url_content, holder.imgContent, holder.pbContent);
            }
        });



        // --- like component ---
        if (data.isLiked_by_user()) {
            holder.imgLike.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_favorite_black_18dp));
            holder.imgLike.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
        } else {
            holder.imgLike.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_favorite_border_black_18dp));
            holder.imgLike.setColorFilter(ContextCompat.getColor(mContext, R.color.colorTextSecondary));
        }

        holder.tvLike.setText(String.valueOf(data.getLikes()));

        // when image liked clicked
        holder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("CLICKED", "item liked pos:" + position);
                int like = data.getLikes();
                boolean isLiked = data.isLiked_by_user();
                if (isLiked) {
                    like--;
                    data.setLiked_by_user(false);
                    data.setLikes(like);

                    // change visual view of like image
                    holder.imgLike.setImageDrawable(mContext.getDrawable(R.drawable.ic_favorite_border_black_18dp));
                    holder.imgLike.setColorFilter(ContextCompat.getColor(mContext, R.color.colorTextSecondary));
                    holder.tvLike.setText(String.valueOf(like));
                } else {
                    like++;
                    data.setLiked_by_user(true);
                    data.setLikes(like);

                    // change visual view of like image
                    holder.imgLike.setImageDrawable(mContext.getDrawable(R.drawable.ic_favorite_black_18dp));
                    holder.imgLike.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
                    holder.tvLike.setText(String.valueOf(like));
                }
            }
        });
        // --- end of like component ---


        // --- user profile component
        User user = data.getUser();
        ProfileImage profileImage = user.getProfile_image();
        String url_user_profileImage = profileImage.getSmall();
        mImageLoader.DisplayImage(url_user_profileImage, holder.imgProfile, null);

        // TODO: when profile picture clicked
        // implement later
//        holder.imgProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // TODO: show detail profile info in new activity
//                Log.i("CLICKED", "item pp pos:" + position);
//            }
//        });

        holder.tvProfile.setText(user.getName());
        // --- end of user profile component


        // --- download button component
        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uriUrls = Uri.parse(urls.getRaw());
                Intent intent = new Intent(Intent.ACTION_VIEW, uriUrls);
                mContext.startActivity(intent);
            }
        });
        // --- end of download button component

        return view;
    }
}
