package Extras;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import app.cedar.cedarconnect.R;

/**
 * Created by Saboor Salaam on 3/10/2015.
 */
public class MainListDialogFragment extends DialogFragment{
    String title, body, company,date, location;
    public static int TYPE_EVENT = 0;
    public static int TYPE_JOB= 1;
    int type = 999;
    Number salary = 0;

    public MainListDialogFragment(){

        this.title = "";
        this.body = "";
        this.company = "";
        this.date = "";
        this.location = "";

    }

    public void instantiate(String title, String body, String company, String date, String location, int type) {
        this.title = title;
        this.body = body;
        this.company = company;
        this.date = date;
        this.location = location;
        this.type = type;

    }

    public void instantiate(String title, String body, String company, String date, String location, Number salary, int type) {
        this.title = title;
        this.body = body;
        this.company = company;
        this.date = date;
        this.location = location;
        this.type = type;
        this.salary = salary;

    }


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;

        View view = inflater.inflate(R.layout.main_list_fragment_layout, container);



        TextView title_text_view = (TextView) view.findViewById(R.id.title_text_view);
        title_text_view.setText(title);

        ImageView exit_button = (ImageView) view.findViewById(R.id.exit_button);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if(type == TYPE_EVENT) {
            MainListEventAdapter mainListEventAdapter = new MainListEventAdapter(body, company, location, date, getActivity());
            ListView listView = (ListView) view.findViewById(R.id.info_list_view);
            listView.setAdapter(mainListEventAdapter);
        }
        if(type == TYPE_JOB){
            MainListJobAdapter mainListJobAdapter = new MainListJobAdapter(body, company, location, date, salary, getActivity());
            ListView listView = (ListView) view.findViewById(R.id.info_list_view);
            listView.setAdapter(mainListJobAdapter);
        }

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        setRetainInstance(true);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

        super.onDismiss(dialog);
    }

}

