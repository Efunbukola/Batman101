package Extras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import app.cedar.cedarconnect.R;

/**
 * Created by Saboor Salaam on 3/10/2015.
 */
public class MainListEventAdapter extends BaseAdapter {

    final static int NUM_INFO_CATEGORIES = 4;
    private static final int TYPE_WHO = 0;
    public static final int TYPE_WHAT= 1;
    private static final int TYPE_WHEN = 2;
    private static final int TYPE_WHERE = 3;
    private static final int TYPE_ERROR = 4;
    private Context thisContext;

    String body = "";
    String company = "";
    String date = "";
    String location = "" ;

    public MainListEventAdapter(String body, String company, String location, String date, Context thisContext) {
        this.body = body;
        this.company = company;
        this.location = location;
        this.date = date;
        this.thisContext = thisContext;
    }

    @Override
    public int getCount() {
        return NUM_INFO_CATEGORIES;
    }

    @Override
    public int getViewTypeCount() {
        return NUM_INFO_CATEGORIES+1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case TYPE_WHAT:
                return TYPE_WHAT;
            case TYPE_WHO:
                return TYPE_WHO;
            case TYPE_WHEN:
                return TYPE_WHEN;
            case TYPE_WHERE:
                return TYPE_WHERE;
            default:
                return TYPE_ERROR;
        }
    }




    public View getView(int position, View convertView, ViewGroup parent) {




        View vi=convertView;

        switch (getItemViewType(position)) {
            case TYPE_WHAT:
                if(convertView==null)
                    vi = LayoutInflater.from(thisContext).inflate(R.layout.event_list_view_what, null);
                TextView what = (TextView) vi.findViewById(R.id.des_text_view);
                what.setText(body);
                break;

            case TYPE_WHO:
                if(convertView==null)
                    vi = LayoutInflater.from(thisContext).inflate(R.layout.even_list_view_who, null);
                TextView who = (TextView) vi.findViewById(R.id.company_text_view);
                //who.setText(company);
                break;
            case TYPE_WHEN:
                if(convertView==null)
                    vi = LayoutInflater.from(thisContext).inflate(R.layout.event_list_view_when, null);
                TextView when = (TextView) vi.findViewById(R.id.date_text_view);
                when.setText(date);
                break;
            case TYPE_WHERE:
                if(convertView==null)
                    vi = LayoutInflater.from(thisContext).inflate(R.layout.event_list_view_where, null);
                TextView where = (TextView) vi.findViewById(R.id.location_text_view);
                //where.setText(company);
                break;
            case TYPE_ERROR:
                if(convertView==null)
                    vi = LayoutInflater.from(thisContext).inflate(R.layout.event_list_view_item_error, null);
                break;
            default:
                vi = LayoutInflater.from(thisContext).inflate(R.layout.event_list_view_where, null);;
                break;
        }

        return vi;
    }
}


