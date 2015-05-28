package Extras;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Saboor Salaam on 3/22/2015.
 */
public class ItemsSorter {

    Date from;

    public List<Object> sortItems(final List<Object> objects)
    {
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
        df.setTimeZone(TimeZone.getTimeZone("EST"));

        from = null; // get time now
        try {
            from = df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        final DateCalculator dateCalculator = new DateCalculator();

        Collections.sort(objects, new Comparator<Object>() {

            public int compare(Object object1, Object object2) {
                //date1

                Field field = null;
                try {
                    field = object1.getClass().getField("date");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                String date1 = "error";

                try {
                    date1 = (String) field.get(object1);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                //date2

                try {
                    field = object2.getClass().getField("date");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                String date2 = "error";

                try {
                    date2 = (String) field.get(object2);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                Date to = null;
                try {
                    to = df.parse(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date to2 = null;
                try {
                    to2 = df.parse(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                double time_until_event1 = dateCalculator.difference(to, from).months * 1000 + dateCalculator.difference(to, from).days * 100 + dateCalculator.difference(to, from).hours * 10 + dateCalculator.difference(to, from).minutes * .1;
                double time_until_event2 = dateCalculator.difference(to2, from).months * 1000 + dateCalculator.difference(to2, from).days * 100 + dateCalculator.difference(to2, from).hours * 10 + dateCalculator.difference(to2, from).minutes * .1;


                return Double.valueOf(time_until_event1).compareTo(time_until_event2);
            }
        });
        return objects;
    }
}
