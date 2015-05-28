package saboor.testexlist;

import android.content.Context;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saboor Salaam on 7/30/2014.
 */
public class GridRefresher {

    List<channel> newChannels = new ArrayList<channel>();
    ParseDBCommunicator parseDBCommunicator;
    GridStateListener gsl;


    public void setGridStateCallBack(GridStateListener gridStateCallBack)
    {
        this.gsl = gridStateCallBack;
    }


    public void refreshData(List<Object> oldChannels, Context mContext)
    {

        parseDBCommunicator = new ParseDBCommunicator(mContext);
        Thread client = new Thread(new Runnable() {
            public void run() {
                newChannels = parseDBCommunicator.getChannelsWithVideos(new ParseDBCommunicator.connectionFailedListener() {
                    @Override
                    public void onConnectionFailed() {

                    }

                    @Override
                    public void onConnectionSuccessful() {

                    }

                    @Override
                    public void onCannotConnectToParse() {

                    }
                });
            }
        });
        client.start();
        //wait for background thread to finish
        try {
            client.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Boolean needsupdating = false;



        for(int i = 0; i < newChannels.size(); i++)
        {
            for(int z = 0; z < oldChannels.size(); z++)
            {

                Field a = null;
                try {
                    a = oldChannels.get(z).getClass().getField("channel_id");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                String id = "error";

                try {
                    id = (String) a.get(oldChannels.get(z));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }



                if (newChannels.get(i).channel_id == id)
                {

                    Field c = null;
                    try {
                        c = oldChannels.get(z).getClass().getField("vid1thumb");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    String thumb = null;

                    try {
                        thumb = (String) c.get(oldChannels.get(z));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    //*****************************************************

                    Field d = null;
                    try {
                        d = oldChannels.get(z).getClass().getField("vid2thumb");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    String thumb2 = null;

                    try {
                        thumb2 = (String) d.get(oldChannels.get(z));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }



                    if((newChannels.get(i).getVid1thumb() != thumb) ||
                            (newChannels.get(i).getVid1thumb() != thumb2) ||
                            (newChannels.get(i).getVid2thumb() != thumb2) ||
                            (newChannels.get(i).getVid2thumb() != thumb)){
                        if(gsl != null) {
                            gsl.onGridNeedsUpdating();
                        }
                        needsupdating = true;
                    }
                }
            }

        }

        if(!needsupdating && gsl != null)
        {
            gsl.onGridIsUpToDate();
        }

    }


    public interface GridStateListener
    {
        void onGridIsUpToDate();
        void onGridNeedsUpdating();
    }
}
