package com.musicplayer;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Saboor Salaam on 5/28/2014.
 */
public class song_list {

    public song_list() {
        list = new ArrayList<song_type>();
    }
    public boolean add_song(song_type s)
    {
        Iterator< song_type > it = list.iterator();
        while(it.hasNext())
        {
            if(s.name.equals(it.next().name))
            {
                return false;
            }
        }

        list.add(s);
        return true;
    }

    ArrayList<song_type> list;
}
