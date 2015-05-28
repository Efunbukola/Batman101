package saboor.testexlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Saboor Salaam on 10/3/2014.
 */

public class favorites_fragment extends Fragment {

    ParseDBCommunicator parseDBCommunicator;
    ViewPager mPager;
    database_handler db;
    List<Video> mVideos;
    channel_vp_adapter mFavorites_VP_Adapter;

    public favorites_fragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.favorites_fragment, container, false);

        ParseDBCommunicator pb = new ParseDBCommunicator(getActivity());



        db = new database_handler(getActivity());

        mVideos = db.getAllLocalFavorites();
        //mVideos = new ArrayList<Video>();
        mPager = (ViewPager) rootView.findViewById(R.id.pager);

        mFavorites_VP_Adapter = new channel_vp_adapter(getFragmentManager());
        mFavorites_VP_Adapter.setList(mVideos);
        Log.d("Size", "Retrieved***: " + mVideos.size() + " videos");
        mPager.setAdapter(mFavorites_VP_Adapter);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}

