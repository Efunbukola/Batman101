package com.test_vertical_view_pager.app;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * Created by Saboor Salaam on 6/12/2014.
 */
public class simple_frag extends Fragment {

    public simple_frag newInstance(int position)
    {
        simple_frag sp = new simple_frag();
        Bundle bundle = new Bundle();
        bundle.putString("index", position + "");
        sp.setArguments(bundle);
        return sp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.sample_frag, container, false);

        TextView text = (TextView) rootView.findViewById(R.id.textView);
        text.setText((String) getArguments().getString("index"));
        final ViewFlipper vf = (ViewFlipper) rootView.findViewById(R.id.viewFlipper);
        Button click = (Button) rootView.findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vf.showNext();
            }
        });

        return rootView;
    }
}
