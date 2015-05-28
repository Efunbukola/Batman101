package com.app.cedar.cedarappbeta;

/**
 * Created by Saboor Salaam on 12/4/2014.
 */

public class TimeSpan {
    public int months;
    public int days;
    public int hours;
    public int minutes;

    public TimeSpan() {
        months = 0;
        days = 0;
        hours = 0;
        minutes = 0;
    }

    public TimeSpan(int months, int days, int hours, int minutes) {
        this.months = months;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
    }
}