package nl.swiftdevelopment.workwatch.models;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tom on 09-01-2015.
 */
public class DateDiff {

    public static Date substractDates(Date startDate, Date endDate) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


        Date result = null;

        DateTime dt1 = new DateTime(startDate);
        DateTime dt2 = new DateTime(endDate);

        System.out.print(Days.daysBetween(dt1, dt2).getDays() + " days, ");
        System.out.print(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
        System.out.print(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");
        System.out.print(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.");

        //Minus one hour, because when Date is converted to DateTime, it add one hour.
        result = new Date(Seconds.secondsBetween(dt2, dt1).minus(Seconds.seconds(3600)).getSeconds() * 1000);


        return result;
    }
}
