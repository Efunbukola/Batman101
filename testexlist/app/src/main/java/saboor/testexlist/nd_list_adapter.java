package saboor.testexlist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Saboor Salaam on 9/30/2014.
 */
public class nd_list_adapter extends BaseAdapter {

        private Context context;
        private List<category> mCategories;

        public nd_list_adapter(Context context, List<category> navDrawerItems){
            this.context = context;
            this.mCategories = navDrawerItems;
        }

        @Override
        public int getCount() {
            return mCategories.size();
        }

        @Override
        public Object getItem(int position) {
            return mCategories.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater)
                        context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.nd_list_item, null);
            }


            TextView name = (TextView) convertView.findViewById(R.id.name);

           name.setText(mCategories.get(position).getName());

            return convertView;
        }
}
