package com.flipper.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Saboor Salaam on 5/29/2014.
 */
public class ScreenSlidePageFragment extends Fragment {

    TextView f;
    public ScreenSlidePageFragment newInstance(int position)
    {
        ScreenSlidePageFragment s = new ScreenSlidePageFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString("EXTRA_MESSAGE", position + "");
        s.setArguments(bdl);
        return s;
    }

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragement_screen_slide_page, container, false);

        String message = getArguments().getString("EXTRA_MESSAGE");
        TextView r = (TextView)rootView.findViewById(R.id.tv);
        r.setText(message);
        return rootView;
    }
}
