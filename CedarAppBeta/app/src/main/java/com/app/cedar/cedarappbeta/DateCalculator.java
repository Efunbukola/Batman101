package com.app.cedar.cedarappbeta;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Saboor Salaam on 12/4/2014.
 */
public class DateCalculator {


    public TimeSpan difference(Date later, Date earlier) {
        TimeSpan v = new TimeSpan();


        if(later == null || earlier == null)
        {
            return new TimeSpan(0,0,0,0);
        }

        /* Add months until we go past the target, then go back one. */
        while (calculateOffset(earlier, v).compareTo(later) <= 0) {
            v.months++;
        }
        v.months--;

        /* Add days until we go past the target, then go back one. */
        while (calculateOffset(earlier, v).compareTo(later) <= 0) {
            v.days++;
        }
        v.days--;

        /* Add hours until we go past the target, then go back one. */
        while (calculateOffset(earlier, v).compareTo(later) <= 0) {
            v.hours++;
        }
        v.hours--;
        while (calculateOffset(earlier, v).compareTo(later) <= 0) {
            v.minutes++;
        }
        v.minutes--;

        return v;
    }

    public Date calculateOffset(Date start, TimeSpan offset) {
        Calendar c = new GregorianCalendar();

        c.setTime(start);

        c.add(Calendar.MONTH, offset.months);
        c.add(Calendar.DAY_OF_YEAR, offset.days);
        c.add(Calendar.HOUR, offset.hours);
        c.add(Calendar.MINUTE, offset.minutes);

        return c.getTime();
    }
}

