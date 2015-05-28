package com.explandable_listview.app;

/**
 * Created by Saboor Salaam on 6/15/2014.
 */
public class channel_square {

    int image_id;
    String channel_name;

    public channel_square(int image_id, String channel_name) {
        this.image_id = image_id;
        this.channel_name = channel_name;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }
}
