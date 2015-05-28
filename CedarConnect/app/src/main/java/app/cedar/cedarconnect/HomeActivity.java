package app.cedar.cedarconnect;

import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseUser;

import Extras.HomeMainListFragment;
import Extras.HomeUserEventsFragment;


public class HomeActivity extends ActionBarActivity {

    final String[] data ={"one","two","three"};
    final String[] fragments ={
            "com.example.navigationdrawer.FragmentOne",
            "com.example.navigationdrawer.FragmentTwo",
            "com.example.navigationdrawer.FragmentThree"};

    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView navList = (ListView) findViewById(R.id.listView);
        navList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.list_item, data));

        HomeMainListFragment homeMainListFragment  = new HomeMainListFragment();
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, homeMainListFragment)
                .commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //getSupportFragmentManager().saveFragmentInstanceState(getSupportFragmentManager().getFragment(null, "main_list_dialog"));
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.my_events) {
            HomeUserEventsFragment homeUserEventsFragment = new HomeUserEventsFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, homeUserEventsFragment)
                    .commit();
            setTitle("My events");
            return true;
        }

        if (id == R.id.newsfeed) {
            HomeMainListFragment homeMainListFragment  = new HomeMainListFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, homeMainListFragment)
                    .commit();
            setTitle("Cedar Connect");
            return true;
        }

        if (id == R.id.sign_out) {

            ParseUser.logOut();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed(){}
}
