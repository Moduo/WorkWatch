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

    /**
     * This method adds seconds to a java.util.Date object.
     * @param date The date you want to add seconds to
     * @param seconds The seconds you want to add
     * @return The java.util.Date with the added seconds.
     */
    public static Date addSeconds(Date date, int seconds){
        //Convert the Date object to DateTime
        DateTime dt = new DateTime(date);

        //Add the Seconds to the DateTime object
        dt.plusSeconds(seconds);

        return dt.toDate();
    }
}
