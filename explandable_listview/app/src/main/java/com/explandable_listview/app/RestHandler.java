package com.explandable_listview.app;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saboor Salaam on 6/6/2014.
 */
public class RestHandler {

    static DefaultHttpClient httpClient;

    RestHandler()
    {
        httpClient = new DefaultHttpClient();
    }


    public String httpGet(String url)
    {
        String jsonString = "Error bad url";
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = null;
        try {

            httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            jsonString = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }


    public String getChannelID(String jsonString) {

        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String id = "";

        JSONArray jsonArray = null;
        try {
            jsonArray = json.getJSONArray("items");
            JSONObject videoItem = jsonArray.getJSONObject(0);

            id = videoItem.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  id;
    }

    public List<VimeoVideo> makeVimeoList(String jsonString)
    {

        List<VimeoVideo> videos = new ArrayList<VimeoVideo>();

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        VimeoVideo v;

        //some of these dont have stats_num_of_likes!!!****************
        try{
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject videoItem = jsonArray.getJSONObject(i);

                String id = videoItem.getString("id");

                String title = videoItem.getString("title");
                String description = videoItem.getString("description");
                String date = videoItem.getString("upload_date");


                String thumb = videoItem.getString("thumbnail_large");

                String user_url = videoItem.getString("user_url");

                //int num_likes = videoItem.getInt("stats_number_of_likes");
                //int num_plays = videoItem.getInt("stats_number_of_plays");
                int duration = videoItem.getInt("duration");



                String video_url = "http://player.vimeo.com/video/" + id + "?autoplay=1";


                v = new VimeoVideo();
                v.setId(id);
                v.setTitle(title);
                v.setDate(date);
                v.setDescription(description);
                v.setUser_url(user_url);
                v.setThumbnail(thumb);
                v.setDuration(duration);
                //v.setNum_likes(num_likes);
                //v.setNum_plays(num_plays);
                v.setVideo_url(video_url);

                videos.add(v);
            }
            } catch (JSONException e) {
            e.printStackTrace();
        }
       return videos;
    }


    public List<YouTubeVideo> makeYouTubeList(String jsonString)
    {
        List<YouTubeVideo> videos = new ArrayList<YouTubeVideo>();

        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = null;
        try {
            jsonArray = json.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //loop through JSON document and add each video to a list of videos
        try{

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject videoItem = jsonArray.getJSONObject(i);
                String id = videoItem.getJSONObject("id").getString("videoId");
                String date = videoItem.getJSONObject("snippet").getString("publishedAt");
                String title = videoItem.getJSONObject("snippet").getString("title");
                String description = videoItem.getJSONObject("snippet").getString("description");
                String thumb = videoItem.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                String c_title = videoItem.getJSONObject("snippet").getString("channelTitle");
                videos.add(new YouTubeVideo(id, date, title, description,thumb,c_title));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return videos;
    }


}
