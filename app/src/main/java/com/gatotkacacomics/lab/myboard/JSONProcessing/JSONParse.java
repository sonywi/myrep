package com.gatotkacacomics.lab.myboard.JSONProcessing;

import android.util.Log;


import com.gatotkacacomics.lab.myboard.models.Category;
import com.gatotkacacomics.lab.myboard.models.Data;
import com.gatotkacacomics.lab.myboard.models.Links;
import com.gatotkacacomics.lab.myboard.models.ProfileImage;
import com.gatotkacacomics.lab.myboard.models.Urls;
import com.gatotkacacomics.lab.myboard.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to parse JSON and construct objects
 * Created by sonywi on 01/11/2016.
 */

public class JSONParse {

    public static List parse(JSONObject json) {
        List result = new ArrayList();

        try {
            JSONArray jsonArray = json.getJSONArray("Data");

            for (int a=0; a<jsonArray.length(); a++) {
                // get one json object
                JSONObject json_data = jsonArray.getJSONObject(a);

                Data data = new Data();
                data.setId(json_data.getString("id"));
                data.setCreated_at(json_data.getString("created_at"));
                data.setWidth(json_data.getInt("width"));
                data.setHeight(json_data.getInt("height"));
                data.setColor(json_data.getString("color"));
                data.setLikes(json_data.getInt("likes"));
                data.setLiked_by_user(json_data.getBoolean("liked_by_user"));

                JSONObject json_user = json_data.getJSONObject("user");
                User user = new User();
                user.setId(json_user.getString("id"));
                user.setUsername(json_user.getString("username"));
                user.setName(json_user.getString("name"));

                    JSONObject json_profile_image = json_user.getJSONObject("profile_image");
                    ProfileImage profileImage = new ProfileImage();
                    profileImage.setSmall(json_profile_image.getString("small"));
                    profileImage.setMedium(json_profile_image.getString("medium"));
                    profileImage.setLarge(json_profile_image.getString("large"));
                    user.setProfile_image(profileImage);

                    JSONObject json_links = json_user.getJSONObject("links");
                    Links links_user = new Links();
                    links_user.setSelf(json_links.getString("self"));
                    links_user.setHtml(json_links.getString("html"));
                    links_user.setPhotos(json_links.getString("photos"));
                    links_user.setLikes(json_links.getString("likes"));
                    user.setLinks(links_user);

                data.setUser(user);

                JSONObject json_urls = json_data.getJSONObject("urls");
                Urls urls = new Urls();
                urls.setRaw(json_urls.getString("raw"));
                urls.setFull(json_urls.getString("full"));
                urls.setRegular(json_urls.getString("regular"));
                urls.setSmall(json_urls.getString("small"));
                urls.setThumb(json_urls.getString("thumb"));
                data.setUrls(urls);

                List<Category> categories = new ArrayList<Category>();
                JSONArray jarr_categories = json_data.getJSONArray("categories");
                for (int b=0; b<jarr_categories.length(); b++) {
                    JSONObject json_category = jarr_categories.getJSONObject(b);

                    Category category = new Category();
                    category.setId(json_category.getInt("id"));
                    category.setTitle(json_category.getString("title"));
                    category.setPhoto_count(json_category.getInt("photo_count"));

                        json_links = json_category.getJSONObject("links");
                        Links links_category = new Links();
                        links_category.setSelf(json_links.getString("self"));
                        links_category.setPhotos(json_links.getString("photos"));
                        category.setLinks(links_category);

                    categories.add(category);
                }
                data.setCategories(categories);

                json_links = json_data.getJSONObject("links");
                Links links_data = new Links();
                links_data.setSelf(json_links.getString("self"));
                links_data.setHtml(json_links.getString("html"));
                links_data.setDownload(json_links.getString("download"));
                data.setLinks(links_data);

                result.add(data);
            }

            Log.i("JSONParse", "success parsing json. list size=" + result.size());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONParse", "error parsing json");
        }

        return result;
    }
}
