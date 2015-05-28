package com.videoswipper.app;

/**
 * Created by Saboor Salaam on 6/13/2014.
 */
public class VimeoVideo {



    String id, title, date ,description, user_url, thumbnail, video_url;
    int duration,num_likes, num_plays;

    public VimeoVideo(String id, String title, String date, String description, String user_url, String thumbnail, String video_url, int duration, int num_likes, int num_plays) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.user_url = user_url;
        this.thumbnail = thumbnail;
        this.video_url = video_url;
        this.duration = duration;
        this.num_likes = num_likes;
        this.num_plays = num_plays;
    }

    public VimeoVideo() {
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser_url() {
        return user_url;
    }

    public void setUser_url(String user_url) {
        this.user_url = user_url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getNum_likes() {
        return num_likes;
    }

    public void setNum_likes(int num_likes) {
        this.num_likes = num_likes;
    }

    public int getNum_plays() {
        return num_plays;
    }

    public void setNum_plays(int num_plays) {
        this.num_plays = num_plays;
    }
}
