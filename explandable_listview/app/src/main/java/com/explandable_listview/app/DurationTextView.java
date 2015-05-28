package com.explandable_listview.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by Saboor Salaam on 6/16/2014.
 */
public class DurationTextView extends TextView {

    private String template = "Duration: <strong>%s</strong>";
    float size;

    public DurationTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.TemplateTextView);
        template = attributes.getString(
                R.styleable.TemplateTextView_template);
        size = Float.parseFloat(attributes.getString(
                R.styleable.TemplateTextView_howbig));
        if (template == null || !template.contains("%s")) {
            template = "%s";
        }
        attributes.recycle();


    }

    public void setDuration(float duration) {
        int durationInMinutes = Math.round(duration / 60);
        int hours = durationInMinutes / 60;
        int minutes = durationInMinutes % 60;

        String hourText = "";
        String minuteText = "";



        if (hours > 0) {
            hourText = hours + (hours == 1 ? " hour " : " hours ");
        }
        if (minutes > 0) {
            minuteText = minutes + (minutes == 1 ? " minute" : " minutes");
        }
        if (hours == 0 && minutes == 0) {
            minuteText = "less than 1 minute";
        }

        String durationText = String.format(template, hourText + minuteText);
        setText(Html.fromHtml(durationText), BufferType.SPANNABLE);
        setTextSize(TypedValue.COMPLEX_UNIT_PT
                ,size);
    }
}