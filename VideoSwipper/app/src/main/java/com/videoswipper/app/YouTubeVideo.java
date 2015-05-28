package com.videoswipper.app;

/**
 * Created by Saboor Salaam on 6/6/2014.
 */
public class YouTubeVideo {

    String videoId, datePublished, videoTitle, videoDescription, videoThumbnail, channelTitle ;

    public YouTubeVideo(String videoId, String datePublished, String videoTitle, String videoDescription, String videoThumbnail, String channelTitle) {
        this.videoId = videoId;
        this.datePublished = datePublished;
        this.videoTitle = videoTitle;
        this.videoDescription = videoDescription;
        this.videoThumbnail = videoThumbnail;
        this.channelTitle = channelTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
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
