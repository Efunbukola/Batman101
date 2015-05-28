package com.app.cedar.cedarappbeta;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Schedule_activity extends Activity implements WeekView.MonthChangeListener,
        WeekView.EventClickListener, WeekView.EventLongPressListener {

private static final int TYPE_DAY_VIEW = 1;
private static final int TYPE_THREE_DAY_VIEW = 2;
private static final int TYPE_WEEK_VIEW = 3;
private int mWeekViewType = TYPE_THREE_DAY_VIEW;
private WeekView mWeekView;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    ActionBar bar = getActionBar();
    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0033")));
    getActionBar().setDisplayShowHomeEnabled(false);


        setContentView(R.layout.activity_schedule_activity);
    this.overridePendingTransition(
            R.anim.in_from_left, R.anim.out_to_right);




    // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);
        }


@Override
public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule_activity, menu);
        return true;
        }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
        case R.id.action_today:
        mWeekView.goToToday();
        return true;
        case R.id.action_day_view:
        if (mWeekViewType != TYPE_DAY_VIEW) {
        item.setChecked(!item.isChecked());
        mWeekViewType = TYPE_DAY_VIEW;
        mWeekView.setNumberOfVisibleDays(1);

        // Lets change some dimensions to best fit the view.
        mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
        mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        }
        return true;
        case R.id.action_three_day_view:
        if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
        item.setChecked(!item.isChecked());
        mWeekViewType = TYPE_THREE_DAY_VIEW;
        mWeekView.setNumberOfVisibleDays(3);

        // Lets change some dimensions to best fit the view.
        mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
        mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        }
        return true;
        case R.id.action_week_view:
        if (mWeekViewType != TYPE_WEEK_VIEW) {
        item.setChecked(!item.isChecked());
        mWeekViewType = TYPE_WEEK_VIEW;
        mWeekView.setNumberOfVisibleDays(7);

        // Lets change some dimensions to best fit the view.
        mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        }
        return true;
        }

        return super.onOptionsItemSelected(item);
        }

@Override
public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        List<WeekViewEvent> events = databaseHandler.getEventsByYearAndMonth(newYear, newMonth);
        return events;
        }



private String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
        }

@Override
public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
        }

@Override
public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
        }
        }