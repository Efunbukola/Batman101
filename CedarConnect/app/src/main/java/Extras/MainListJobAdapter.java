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
public class MainListJobAdapter extends BaseAdapter {

    final static int NUM_INFO_CATEGORIES = 5;
    private static final int TYPE_WHO = 0;
    public static final int TYPE_DESC= 1;
    private static final int TYPE_DATE = 2;
    private static final int TYPE_LOCATION = 3;
    private static final int TYPE_SALARY = 4;
    private static final int TYPE_ERROR = 5;
    private Context thisContext;

    String body = "";
    String company = "";
    String date = "";
    String location = "" ;
    Number salary = 0;

    public MainListJobAdapter(String body, String company, String location, String date, Number salary, Context thisContext) {
        this.body = body;
        this.company = company;
        this.location = location;
        this.date = date;
        this.thisContext = thisContext;
        this.salary = salary;
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
            case TYPE_WHO:
                return TYPE_WHO;
            case TYPE_DESC:
                return TYPE_DESC;
            case TYPE_DATE:
                return TYPE_DATE;
            case TYPE_LOCATION:
                return TYPE_LOCATION;
            case TYPE_SALARY:
                return TYPE_SALARY;
            default:
                return TYPE_ERROR;
        }
    }




    public View getView(int position, View convertView, ViewGroup parent) {


        View vi=convertView;

        switch (getItemViewType(position)) {
            case TYPE_SALARY:
                if(convertView==null)
                    vi = LayoutInflater.from(thisContext).inflate(R.layout.job_list_view_salary, null);
                TextView salary = (TextView) vi.findViewById(R.id.salary_text_view);
                salary.setText(salary.toString());
                break;

            case TYPE_WHO:
                if(convertView==null)
                    vi = LayoutInflater.from(thisContext).inflate(R.layout.job_list_view_who, null);
                TextView who = (TextView) vi.findViewById(R.id.company_text_view);
                who.setText(company);
                break;
            case TYPE_DATE:
                if(convertView==null)
                    vi = LayoutInflater.from(thisContext).inflate(R.layout.job_list_view_date, null);
                TextView when = (TextView) vi.findViewById(R.id.date_text_view);
                when.setText(date);
                break;
            case TYPE_LOCATION:
                if(convertView==null)
                    vi = LayoutInflater.from(thisContext).inflate(R.layout.job_list_view_location, null);
                TextView where = (TextView) vi.findViewById(R.id.location_text_view);
                where.setText(location);
                break;
            case TYPE_DESC:
                if(convertView==null)
                    vi = LayoutInflater.from(thisContext).inflate(R.layout.job_list_view_desc, null);
                TextView desc = (TextView) vi.findViewById(R.id.des_text_view);
                desc.setText(body);
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


