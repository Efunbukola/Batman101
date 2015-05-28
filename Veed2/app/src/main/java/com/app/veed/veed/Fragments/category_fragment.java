package com.app.veed.veed.Fragments;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.app.veed.veed.Adapters.CategoryGridViewAdapter;
import com.app.veed.veed.ParseClasses.Category;
import com.app.veed.veed.ParseClasses.Channel;
import com.app.veed.veed.ParseClasses.ParseDBCommunicator;
import com.app.veed.veed.R;

import java.util.List;

/**
 * Created by Saboor Salaam on 8/17/2014.
 */
public class category_fragment extends Fragment {

        FragmentManager fragmentManager;
        List<Channel> channels;
        CategoryGridViewAdapter categoryGridViewAdapter;
        Context context;
        int itemHeight;
        int itemWidth;

        public category_fragment newInstance(Category category, int itemHeight,int itemWidth, Context context) {

            category_fragment category_fragment = new category_fragment();
            String name = category.getName();
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putInt("itemHeight", itemHeight);
            bundle.putInt("itemWidth", itemWidth);
            category_fragment.setArguments(bundle);
            this.context = context;
            return category_fragment;


        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.category_fragment_layout, container, false);

            String name = "";
            name = (String) getArguments().getString("name");
            itemHeight = (int) getArguments().getInt("itemHeight");
            itemWidth = (int) getArguments().get("itemWidth");

            GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
            //gridView.setIn


            channels = new ParseDBCommunicator(getActivity()).getAllChannelsOfACategory(name, new ParseDBCommunicator.connectionFailedListener() {
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

            Log.d("ItemHeight", "Final inherited item height CF " + itemHeight);
            categoryGridViewAdapter = new CategoryGridViewAdapter(getActivity(), channels, itemHeight, itemWidth);
            gridView.setAdapter(categoryGridViewAdapter);
            return rootView;
        }
}
