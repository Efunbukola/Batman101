package SignUpViewPager;


import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import app.cedar.cedarconnect.R;

/**
 * Created by Saboor Salaam on 3/1/2015.
 */

public class SignUpScreenPagerFragment extends Fragment {

    private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
    FragmentManager fm;
    ProgressBar pb;
    ImageView cover;


    public SignUpScreenPagerFragment newInstance(int position) {

        SignUpScreenPagerFragment signUpScreenPagerFragment = new SignUpScreenPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        signUpScreenPagerFragment.setArguments(bundle);
        return signUpScreenPagerFragment;
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int position = getArguments().getInt("position");

        if (rootView == null) {
            switch(position){
                case 0:
                    rootView = inflater.inflate(R.layout.sign_up_screen1, container, false);
                    break;
                case 1:
                    rootView = inflater.inflate(R.layout.sign_up_screen2, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.sign_up_screen3, container, false);
                    break;
                default:
                    rootView = inflater.inflate(R.layout.sign_up_screen_error, container, false);
                    break;
            }



/*
        if(rootView.findViewById(R.id.blurb_title) != null) {
            TextView blurb_title_text_view = (TextView) rootView.findViewById(R.id.blurb_title);
            Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/coolvetica.ttf");
            blurb_title_text_view.setTypeface(typeFace);
        }
*/


        }

        return rootView;

    }
}

