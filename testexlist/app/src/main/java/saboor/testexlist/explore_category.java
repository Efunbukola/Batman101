package saboor.testexlist;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

public class explore_category extends Activity {

    List<channel> mChannels;
    category_lv_adapter category_lv_adapter;
    String category_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_category);

        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            category_name = extras.getString("name");
        }


        ListView listView = (ListView) findViewById(R.id.listView);


        mChannels = new ParseDBCommunicator(this).getAllChannelsOfACategory(category_name, new ParseDBCommunicator.connectionFailedListener() {
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


        category_lv_adapter = new category_lv_adapter(this, mChannels);
        listView.setAdapter(category_lv_adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.explore_category, menu);
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

