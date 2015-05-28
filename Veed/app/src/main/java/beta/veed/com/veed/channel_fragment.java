package beta.veed.com.veed;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Saboor Salaam on 8/17/2014.
 */
public class channel_fragment extends Fragment {

    FragmentManager fm;
    int bar_value = 50;
    float angle = 0.0f;

    float x;

    public channel_fragment newInstance(channel channel, Integer color) {

        channel_fragment channel_fragment = new channel_fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("size", channel.getSize());
        bundle.putString("name", channel.getChannel_name());
        bundle.putString("thumb", channel.getVid1thumb());
        bundle.putInt("color", color);
        bundle.putString("id", channel.getChannel_id());
        bundle.putString("cover_title", channel.getCover_title());
        bundle.putString("cover2_title", channel.getCover2_title());
        channel_fragment.setArguments(bundle);
        return channel_fragment;
    }

    String name = "";
    String thumb = "";

    String cover_title = "";
    String cover2_title = "";
    String id = "";
    Integer size = 0;
    Integer color = 000000;
    ImageView cover;
    ProgressBar progressBar;
    boolean done = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.layout_channel_fragment, container, false);

        name = (String) getArguments().getString("name");
        size = (Integer) getArguments().getInt("size");
        color = (Integer) getArguments().getInt("color");
        thumb = (String) getArguments().getString("thumb");
        id = (String) getArguments().getString("id");
        cover_title = (String) getArguments().getString("cover_title");
        cover2_title = (String) getArguments().getString("cover2_title");

        final RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.holder);
        //final TextView _name = (TextView) rootView.findViewById(R.id.name);
        final TextView _size = (TextView) rootView.findViewById(R.id.size);
        final ImageView open = (ImageView) rootView.findViewById(R.id.open);
        final ImageView cover = (ImageView) rootView.findViewById(R.id.channel_cover);
        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        final TextView title = (TextView) rootView.findViewById(R.id.cover_title);

        //_name.setText(name);
        _size.setText(size.toString());
        title.setText(cover_title);





       Picasso.with(getActivity()).load(thumb).centerCrop().fit().into(cover, new Callback() {
            @Override
            public void onSuccess() {
                cover.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });



        relativeLayout.setBackgroundColor(color);


        _size.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent intent = new Intent(getActivity(), Inside_channel.class);

                intent.putExtra("channel_id", id);
                intent.putExtra("channel_name", name);
                startActivity(intent);

                return false;
            }
        });





        cover.setOnTouchListener(new View.OnTouchListener() {


            float endpoint = 0;
            float startPoint = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        startPoint = event.getX();
                        //System.out.println("Action down,..."+event.getX());


                        x = event.getX();

                        ObjectAnimator rotate =  ObjectAnimator.ofFloat(open, View.ROTATION, -1080);
                        rotate.setInterpolator(new AccelerateInterpolator());
                        rotate.setDuration(800);
                        rotate.start();


                        //cover_copy.setVisibility(View.VISIBLE);



                    }

                    break;
                    case MotionEvent.ACTION_MOVE: {

                        Log.d("Rotation", "The object is rotated: " + open.getRotationX());


                        float diffX = 0f;

                                if( event.getX() > x)
                                {
                                    diffX = event.getX() - x;
                                }else
                                {
                                    diffX = x - event.getX();
                                    //rotateview

                                    Log.d("Moved by", "Moved left by: " + diffX);

                                    //ObjectAnimator rotate =  ObjectAnimator.ofFloat(_size, View.ROTATION, angle - diffX * 10 );
                                    //rotate.setInterpolator(new AccelerateInterpolator());
                                    //rotate.setDuration(100);
                                    //rotate.start();
                                    angle = angle - diffX * 10;
                                }

                        //x = event.getX();


                    }
                    break;
                    case MotionEvent.ACTION_UP: {


                        //cover_copy.setVisibility(View.INVISIBLE);

                        ObjectAnimator rotate_reverse =  ObjectAnimator.ofFloat(open, View.ROTATION, 0);
                        rotate_reverse.setInterpolator(new DecelerateInterpolator());
                        rotate_reverse.setDuration(700);
                        rotate_reverse.start();






                        endpoint = event.getX();
                        if (endpoint > startPoint + startPoint / 4) {
                            Log.d("Swipe", "Right swipe");

                        } else if (endpoint < startPoint - startPoint / 4) {
                            Log.d("Swipe", "Left swipe");
                            Intent intent = new Intent(getActivity(), Inside_channel.class);
                            intent.putExtra("channel_id", id);
                            intent.putExtra("channel_name", name);
                                startActivity(intent);

                        }

                    }
                    break;

                }
                return true;
            }
        });
        return rootView;
    }

    public class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float paramFloat) {
            return Math.abs(paramFloat - 1f);
        }
    }
}



