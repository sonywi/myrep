package com.gatotkacacomics.lab.myboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gatotkacacomics.lab.imageloader.ImageLoader;
import com.gatotkacacomics.lab.myboard.models.Category;
import com.gatotkacacomics.lab.myboard.models.Data;
import com.gatotkacacomics.lab.myboard.models.ProfileImage;
import com.gatotkacacomics.lab.myboard.models.Urls;
import com.gatotkacacomics.lab.myboard.models.User;
import com.gatotkacacomics.lab.myboard.tools.Utils;


/**
 * Created by sonywi on 01/11/2016.
 */
public class DetailActivity extends AppCompatActivity {

    private ViewHolder mHolder;
    private ImageLoader mImageLoader;

    /**
     * holder class
     */
    private class ViewHolder {
        private ImageView imgProfile;
        private TextView tvProfile;
        private ImageView imgContent;
        private ProgressBar pbContent;
        private ImageView imgContentRefresh;
        private TextView tvCategory;
        private Button btnDownload;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setup view component
        mHolder = new ViewHolder();
        mHolder.imgProfile = (ImageView) findViewById(R.id.img_profile);
        mHolder.tvProfile = (TextView) findViewById(R.id.tv_profile);
        mHolder.imgContent = (ImageView) findViewById(R.id.img_content);
        mHolder.pbContent = (ProgressBar) findViewById(R.id.pb_content);
        mHolder.imgContentRefresh = (ImageView) findViewById(R.id.img_content_refresh);
        mHolder.tvCategory = (TextView) findViewById(R.id.tv_category);
        mHolder.btnDownload = (Button) findViewById(R.id.btn_download);

        mImageLoader = new ImageLoader();
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("data_id");
        final Data data = Utils.database.get(id);


        // --- component user profile
        User user = data.getUser();
        ProfileImage profileImage = user.getProfile_image();
        String url_user_profileImage = profileImage.getSmall();
        mImageLoader.DisplayImage(url_user_profileImage, mHolder.imgProfile, null);

        mHolder.tvProfile.setText(user.getName());

        // --- component image content
        Urls urls = data.getUrls();
        String url_content = urls.getRegular();
        mHolder.imgContent.setBackgroundColor(Color.parseColor(data.getColor()));

        // --- set temporal image content while its loading ---
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = width * data.getHeight() / data.getWidth();
        Log.i("DetailActivity", "width:" + width + " height:" + height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mHolder.imgContent.setImageBitmap(bitmap);

        mImageLoader.DisplayImage(url_content, mHolder.imgContent, mHolder.pbContent);
        if (mHolder.pbContent.getVisibility() == View.VISIBLE) {
            mHolder.pbContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mImageLoader.cancelDownload();
                }
            });
        }


        // --- component category
        String result = "";
        for (int a=0; a<data.getCategories().size(); a++) {
            if (data.getCategories().size() < 2) {
                Category category = data.getCategories().get(a);
                result = category.getTitle();
            } else {
                Category category = data.getCategories().get(a);
                result += category.getTitle();
                result += ", ";
            }
        }
        mHolder.tvCategory.setText(result);

        // --- download button
        mHolder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Urls urls = data.getUrls();

                Uri uriUrl = Uri.parse(urls.getRaw());
                Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Log.i("TES", "Back!!!");
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
