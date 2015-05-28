package beta.veed.com.testgridviewpager;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class DraggableGridViewPagerTestActivity extends Activity {

    private static final String TAG = "DraggableGridViewPagerTestActivity";

    private DraggableGridViewPager mDraggableGridViewPager;
    private Button mAddButton;
    private Button mRemoveButton;

    private ArrayAdapter<String> mAdapter;
    int width;
    int height;

    private int mGridCount;
    RelativeLayout.LayoutParams mImageViewLayoutParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.draggable_grid_view_pager_test);
        mDraggableGridViewPager = (DraggableGridViewPager) findViewById(R.id.draggable_grid_view_pager);
        //mAddButton = (Button) findViewById(R.id.add);
        //mRemoveButton = (Button) findViewById(R.id.remove);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;


        mAdapter = new ArrayAdapter<String>(this, 0) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final String text = getItem(position);
                if (convertView == null) {
                    convertView = (View) getLayoutInflater().inflate(R.layout.draggable_grid_item, null);
                }

                TextView channel_title = (TextView) convertView.findViewById(R.id.textView);
                ImageView cover = (ImageView) convertView.findViewById(R.id.imageView);
                mImageViewLayoutParams = new RelativeLayout.LayoutParams(height/3, width/3);
                //cover.setLayoutParams(mImageViewLayoutParams);




                return convertView;
            }

        };
        mDraggableGridViewPager.setAdapter(mAdapter);
        mDraggableGridViewPager.setOnPageChangeListener(new DraggableGridViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.v(TAG, "onPageScrolled position=" + position + ", positionOffset=" + positionOffset
                        + ", positionOffsetPixels=" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "onPageSelected position=" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged state=" + state);
            }
        });
        mDraggableGridViewPager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //showToast(((TextView) view).getText().toString());
            }
        });
        mDraggableGridViewPager.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //showToast(((TextView) view).getText().toString() + " long clicked!!!");
                return true;
            }
        });
        mDraggableGridViewPager.setOnRearrangeListener(new DraggableGridViewPager.OnRearrangeListener() {
            @Override
            public void onRearrange(int oldIndex, int newIndex) {
                Log.i(TAG, "OnRearrangeListener.onRearrange from=" + oldIndex + ", to=" + newIndex);
                String item = mAdapter.getItem(oldIndex);
                mAdapter.setNotifyOnChange(false);
                mAdapter.remove(item);
                mAdapter.insert(item, newIndex);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}

