package saboor.testexlist;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class favorites_activity extends FragmentActivity {

    ParseDBCommunicator parseDBCommunicator;
    ViewPager mPager;
    database_handler db;
    List<Video> mVideos;
    channel_vp_adapter mFavorites_VP_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorities_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        ParseDBCommunicator pb = new ParseDBCommunicator(this);



        db = new database_handler(this);

        mVideos = db.getAllLocalFavorites();
        //mVideos = new ArrayList<Video>();
        mPager = (ViewPager) findViewById(R.id.pager);

        mFavorites_VP_Adapter = new channel_vp_adapter(getSupportFragmentManager());
        mFavorites_VP_Adapter.setList(mVideos);
        Log.d("Size", "Retrieved***: " + mVideos.size() + " videos");
        mPager.setAdapter(mFavorites_VP_Adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favorities_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
