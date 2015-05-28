package saboor.testexlist;

/**
 * Created by Saboor Salaam on 7/3/2014.
 */

public class channel {
    public String channel_name, channel_id, vid1thumb, vid2thumb, cover_title, cover2_title;
    public int size;


    public channel(String channel_name, String channel_id, String vid1thumb, String vid2thumb, String cover_title, String cover2_title, int size) {
        this.channel_name = channel_name;
        this.channel_id = channel_id;
        this.vid1thumb = vid1thumb;
        this.vid2thumb = vid2thumb;
        this.cover_title = cover_title;
        this.cover2_title = cover2_title;
        this.size = size;
    }

    public channel() {
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

    public String getVid1thumb() {
        return vid1thumb;
    }

    public void setVid1thumb(String vid1thumb) {
        this.vid1thumb = vid1thumb;
    }

    public String getVid2thumb() {
        return vid2thumb;
    }

    public void setVid2thumb(String vid2thumb) {
        this.vid2thumb = vid2thumb;
    }

    public String getCover_title() {
        return cover_title;
    }

    public void setCover_title(String cover_title) {
        this.cover_title = cover_title;
    }

    public String getCover2_title() {
        return cover2_title;
    }

    public void setCover2_title(String cover2_title) {
        this.cover2_title = cover2_title;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
