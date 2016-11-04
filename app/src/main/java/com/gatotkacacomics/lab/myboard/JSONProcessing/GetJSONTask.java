package com.gatotkacacomics.lab.myboard.JSONProcessing;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class to get JSON by url
 * Created by sonywi on 01/11/2016.
 */

public class GetJSONTask extends AsyncTask<String, Void, JSONObject> {

    public interface Listener {
        void onSuccess(JSONObject result);
        void onError();
    }

    private Listener mListener;


    /**
     * constructor
     * @param listener
     */
    public GetJSONTask(Listener listener) {
        this.mListener = listener;
    }


    @Override
    protected void onPreExecute() {

    }


    @Override
    protected JSONObject doInBackground(String... strings) {

        JSONObject result = null;
        String str_json = "";

        try {
            Log.i("GETJSON", "try to connect");
            URL url = new URL(strings[0]);

            Log.i("GETJSON", "url=" + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);

            InputStream is = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            sb.append("{\n\"Data\":");
            String inputString;
            while ((inputString = br.readLine()) != null) {
                sb.append(inputString + "\n");
            }
            sb.append("}");

            str_json = sb.toString();
            str_json = str_json.replaceAll("\\u0026","&");
            Log.i("GETJSON", "String result=" + str_json);

            conn.disconnect();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("GETJSON", "cannot connect");
        }


        try {
            result = new JSONObject(str_json);
        } catch (JSONException e) {
            Log.e("GETJSON", "error parsing data " + e.toString());
        }

        return result;
    }


    @Override
    protected void onPostExecute(JSONObject json) {
        if (json == null) {
            mListener.onError();
        } else {
            mListener.onSuccess(json);
        }
    }
}
