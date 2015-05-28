package ParseClasses;

/**
 * Created by Saboor Salaam on 7/3/2014.
 */

public class Channel {
    public String channel_name, channel_id, cover_thumb, cover_title;
    public int size;

    public Channel(String channel_name, String channel_id, String cover_thumb, String cover_title, int size) {
        this.channel_name = channel_name;
        this.channel_id = channel_id;
        this.cover_thumb = cover_thumb;
        this.cover_title = cover_title;
        this.size = size;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getCover_thumb() {
        return cover_thumb;
    }

    public void setCover_thumb(String cover_thumb) {
        this.cover_thumb = cover_thumb;
    }

    public String getCover_title() {
        return cover_title;
    }

    public void setCover_title(String cover_title) {
        this.cover_title = cover_title;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}