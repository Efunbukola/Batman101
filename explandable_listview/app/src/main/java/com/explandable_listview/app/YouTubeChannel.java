package com.explandable_listview.app;

/**
 * Created by Saboor Salaam on 6/17/2014.
 */
public class YouTubeChannel {
    String name;
    String id;

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    String last_updated;

    public YouTubeChannel(String name, String id, String last_updated) {
        this.name = name;
        this.id = id;
        this.last_updated = last_updated;
    }

    public YouTubeChannel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
