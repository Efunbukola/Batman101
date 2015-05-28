package saboor.testexlist;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saboor Salaam on 10/3/2014.
 */
public class home_fragment extends android.support.v4.app.Fragment {


    List<channel> mChannels;
    private int mPhotoSize, mPhotoSpacing;
    ParseDBCommunicator parseDBCommunicator;
    movableGridAdapter MGA;
    DraggableGridViewPager gridView;
    private int draggedIndex = -1;
    PointF start = new PointF();
    PointF mid = new PointF();
    TextView rh;
    int width;
    database_handler db;
    Display display;


    public home_fragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        parseDBCommunicator = new ParseDBCommunicator(getActivity());

        List<category> list = parseDBCommunicator.getAllCategories(new ParseDBCommunicator.connectionFailedListener() {
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





        db = new database_handler(getActivity());
        db.addChannel("1341");
        db.addChannel("filmschool");



        display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        int height = size.y;

        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        gridView = (DraggableGridViewPager) rootView.findViewById(R.id.draggable_grid_view_pager);
        mChannels = new ArrayList<channel>();
        mChannels = db.getAllLocalChannels();


        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
        swipeView.setColorScheme(android.R.color.holo_blue_dark, android.R.color.holo_orange_light, android.R.color.holo_green_light, android.R.color.holo_green_light);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");

                ( new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mChannels = db.getAllLocalChannels();
                        Log.d("Current page: ",gridView.getCurrentItem()+ "");
                        final int current_item = gridView.getCurrentItem();
                        if (MGA.isDataSetNew(mChannels))
                        {
                            MGA.set(mChannels);
                            gridView.setAdapter(MGA, new DraggableGridViewPager.adapterSetListener() {
                                @Override
                                public void onAdapterSet() {
                                    gridView.setCurrentItem(current_item);
                                }
                            });


                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Sorry nothing new :(", Toast.LENGTH_SHORT).show();
                        }

                        swipeView.setRefreshing(false);
                    }
                }, 2000);
            }
        });



        //db.printDB();
        Log.d("Size of list", "Size of list: " + mChannels.size());

        MGA = new movableGridAdapter(mChannels , getActivity(), 2, height/3);


        gridView.setAdapter(MGA, new DraggableGridViewPager.adapterSetListener() {
            @Override
            public void onAdapterSet() {

                progressBar.setVisibility(View.GONE);
                gridView.setVisibility(View.VISIBLE);

            }
        });



       gridView.setOnPageChangeListener(new DraggableGridViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //showToast(((TextView) view).getText().toString());
                Intent intent = new Intent(getActivity(), inside_channel.class);

                Field a = null;
                try {
                    a = MGA.getItem(position).getClass().getField("channel_id");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                String _id = "error";

                try {
                    _id = (String) a.get(MGA.getItem(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                Field b = null;
                try {
                    b = MGA.getItem(position).getClass().getField("channel_name");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                String name = "error";
                try {
                    name = (String) b.get(MGA.getItem(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }



                intent.putExtra("channel_id", _id);
                intent.putExtra("channel_name", name);
                startActivity(intent);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //showToast(((TextView) view).getText().toString() + " long clicked!!!");
                // gridView.startEditMode();
                gridView.deleteModeOn();//********************************************************^&(*&
                MGA.setDeleteMode(true);

                return true;
            }
        });


        gridView.setOnRearrangeListener(new DraggableGridViewPager.OnRearrangeListener() {
            @Override
            public void onRearrange(int oldIndex, int newIndex) {

                db.saveOrder(MGA.getChannels());
            }
        });

        return rootView;
    }


}