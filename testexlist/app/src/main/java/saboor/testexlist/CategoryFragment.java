package saboor.testexlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Saboor Salaam on 8/17/2014.
 */
public class CategoryFragment extends Fragment {

        FragmentManager fm;
        List<channel> mChannels;
        category_lv_adapter category_lv_adapter;

        public CategoryFragment newInstance(category category) {

            category_fragment cf = new category_fragment();
            String name = category.getName();
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            cf.setArguments(bundle);
            return cf;
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.category_fragment, container, false);

            String name = "";
            name = (String) getArguments().getString("name");
            ListView listView = (ListView) rootView.findViewById(R.id.listView);


            mChannels = new ParseDBCommunicator(getActivity()).getAllChannelsOfACategory(name, new ParseDBCommunicator.connectionFailedListener() {
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


            category_lv_adapter = new category_lv_adapter(getActivity(), mChannels);
            listView.setAdapter(category_lv_adapter);
            return rootView;
        }
}
