package com.app.veed.veed.ParseClasses;

/**
 * Created by Saboor Salaam on 6/6/2014.
 */
public class Video {

    String videoId, datePublished, videoTitle, videoDescription, videoThumbnail, channelTitle, type ;

    public Video(String videoId, String datePublished, String videoTitle, String videoDescription, String videoThumbnail, String channelTitle, String type) {
        this.videoId = videoId;
        this.datePublished = datePublished;
        this.videoTitle = videoTitle;
        this.videoDescription = videoDescription;
        this.videoThumbnail = videoThumbnail;
        this.channelTitle = channelTitle;
        this.type = type;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }
}
