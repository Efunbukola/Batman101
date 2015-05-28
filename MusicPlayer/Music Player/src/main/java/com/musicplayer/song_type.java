package com.musicplayer;

/**
 * Created by Saboor Salaam on 5/27/2014.
 */
public class song_type {

    public song_type() {
        rating = 'N';
        name = "No name";
    }


    char rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getRating() {
        return rating;
    }

    public void setRating(char rating) {
        this.rating = rating;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public song_type(char rating, String artist, String name) {
        this.rating = rating;
        this.artist = artist;
        this.name = name;
    }

    String artist, name;
}
