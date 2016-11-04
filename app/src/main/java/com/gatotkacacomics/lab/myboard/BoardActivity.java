package com.gatotkacacomics.lab.myboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gatotkacacomics.lab.imageloader.ImageLoader;
import com.gatotkacacomics.lab.myboard.JSONProcessing.GetJSONTask;
import com.gatotkacacomics.lab.myboard.JSONProcessing.JSONParse;
import com.gatotkacacomics.lab.myboard.adapter.DataAdapter;
import com.gatotkacacomics.lab.myboard.models.Data;
import com.gatotkacacomics.lab.myboard.tools.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Main activity of myboard application
 * Created by sonywi on 01/11/2016.
 */

public class BoardActivity extends AppCompatActivity
        implements GetJSONTask.Listener{

    /**
     * holder class
     */
    public class ViewHolder {
        private LinearLayout layout_not_connect;
        private ProgressBar progressBar;
        private ListView listView;

        private TextView textView;
    }


    private DataAdapter mAdapter;
    private List mListData = new ArrayList();
    private ViewHolder mHolder;
    private String mJson_url = "http://pastebin.com/raw/wgkJgazE";
//    String json_url = "http://md5.jsontest.com/";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setup view component
        setContentView(R.layout.activity_board);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mHolder = new ViewHolder();
        mHolder.layout_not_connect = (LinearLayout) findViewById(R.id.layout_not_connect);
        mHolder.progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mHolder.listView = (ListView) findViewById(R.id.list_item);
        mHolder.textView = (TextView) findViewById(R.id.texttest);

        mHolder.layout_not_connect.setVisibility(View.GONE);
        mHolder.progressBar.setVisibility(View.VISIBLE);
        mHolder.listView.setVisibility(View.GONE);

        // get json
        new GetJSONTask(this).execute(mJson_url);
    }

    @Override
    public void onSuccess(JSONObject result) {
        mHolder.textView.setText(result.toString());
        mHolder.layout_not_connect.setVisibility(View.GONE);
        mHolder.progressBar.setVisibility(View.GONE);
        mHolder.listView.setVisibility(View.VISIBLE);

        mListData = JSONParse.parse(result);
        Log.i("JSONParse", "listdata.size=" + mListData.size());
        for (int a=0; a<mListData.size(); a++) {
            Data data = (Data)mListData.get(a);
            Utils.database.put(data.getId(), data);
            Log.i("ACTIVITY", "data : "+ data.toString());
        }
        mHolder.textView.setText(String.valueOf(mListData.size()));

        mAdapter = new DataAdapter(this, mListData);
        mHolder.listView.setAdapter(mAdapter);

        // TODO: when item clicked, enter new activity to show detail
        // implement later
//        holder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Data data = (Data) listData.get(position);
//
//                Intent intent = new Intent(BoardActivity.this, DetailActivity.class);
//                intent.putExtra("data_id", data.getId());
//                startActivityForResult(intent, 0);
//            }
//        });
    }

    @Override
    public void onError() {
        mHolder.textView.setText("not connected");
        mHolder.layout_not_connect.setVisibility(View.VISIBLE);
        mHolder.progressBar.setVisibility(View.GONE);
        mHolder.listView.setVisibility(View.GONE);
    }


    /**
     * when button clicked
     * @param view
     */
    public void onClickSecond(View view) {
        switch (view.getId()) {
//            case R.id.switch_to_main :
//                Intent intent = new Intent(BoardActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//                break;
            case R.id.img_refresh :
                mHolder.layout_not_connect.setVisibility(View.GONE);
                mHolder.progressBar.setVisibility(View.VISIBLE);
                mHolder.listView.setVisibility(View.GONE);
                new GetJSONTask(this).execute(mJson_url);
                break;
            case R.id.btn_clear :
                ImageLoader imageLoader = new ImageLoader();
                imageLoader.clearCache();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
}
