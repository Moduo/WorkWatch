package nl.swiftdevelopment.workwatch.models;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import nl.swiftdevelopment.workwatch.R;
import nl.swiftdevelopment.workwatch.WatchOverviewActivity;

@Table(name = "Time")
public class Time extends Model {

    @Column(name = "isRunning")
    private boolean isRunning;

    @Column(name = "startDateTime")
    private Date startDateTime;

    @Column(name = "seconds")
    private int seconds;

    private String amount;
    private final static DecimalFormat numberFormatter = new DecimalFormat("00");
    protected WatchOverviewActivity context;
    private View view;
    private Timer timer;

    //The position of the timeblock in the gridview
    int position;

    GridLayout layout;

    public Time() {
        super();
    }

    public Time(Context context, int position, View view) {
        this.context = (WatchOverviewActivity) context;
        this.position = position;
        this.view = view;

        start();

    }


    public void setAmount(String amount) {
        this.amount = amount;
    }

    public View getView() {
        return this.view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setContext(Context context) {
        this.context = (WatchOverviewActivity) context;
    }

    /**
     * This method gets the Time from the database by its
     *
     * @param id The ID of the Time obj (which is the same as its parent ID (the TimeBlock))
     * @return A time object
     */
    public static Time getTimeById(long id) {
        Time time = new Select()
                .from(Time.class)
                .where("Id = ?", id)
                .executeSingle();

        time.setAmount(time.getCurrentTime());


        if (time.isRunning) {
            time.resume();
        }

        return time;
    }

    /**
     * Rewrite the time of the textview with the current time of this object.
     */
    private void rewriteTextView() {
        try {
            // Get current TimeBlock and start setting the text on
            // the TextView each second.
            TextView blockTime = (TextView) context.gridView.getChildAt(position)
                    .findViewById(R.id.block_time);
            blockTime.setText(amount);

            Log.d("ID", "" + view.getId());
        } catch (Exception e) {
            Log.d("NEW", "NPE");
        }
    }

    /**
     * Display or hide the pause button as overlay for the time block.
     *
     * @param bool True == display <br />
     *             False == hide
     */
    private void displayPause(boolean bool) {
        if (bool) {
            // Display pause img as overlay on the timeblock
            layout = (GridLayout) this.context.gridView.getChildAt(position)
                    .findViewById(R.id.overlay);
            layout.setVisibility(View.VISIBLE);
        } else {
            // Remove pause overlay
            layout = (GridLayout) this.context.gridView.getChildAt(position)
                    .findViewById(R.id.overlay);
            layout.setVisibility(View.INVISIBLE);
        }
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public String getCurrentTime() {
        Date date = null;

        try {
            date = DateDiff.substractDates(new Date(), startDateTime);
        } catch (Exception e) {

        }

        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String time = df.format(date);
        Log.d("TIME", "Current time: " + time);
        return time;
    }

    private int getCurrentSeconds() {
        String timeStr = getCurrentTime();

        //Break the timeStr up in hours, mins, secs
        int hours = Integer.parseInt(timeStr.substring(0, 2)),
                minutes = Integer.parseInt(timeStr.substring(3, 5)),
                seconds = Integer.parseInt(timeStr.substring(6, 8));

        return (hours * 3600) + (minutes * 60) + seconds;
    }


    public boolean isRunning() {
        return isRunning;
    }

    public void start() {
        //Start the counting
        timer = new Timer();
        timer.schedule(new UpdateUITask(), 0, 1000);

        //Set isRunning to true and set the startDateTime to the current sysdate
        isRunning = true;
        startDateTime = new Date();


    }

    public void stop() {
        if (isRunning) {
            isRunning = false;

            //Stop the counting
            timer.cancel();
            timer.purge();

            //Set the current time
            this.seconds = getCurrentSeconds();

            //Set the startDateTime to null, because it's not running anymore.
            startDateTime = null;
        }


        displayPause(true);
    }

    public void resume() {
        if (!isRunning) {
            // Declare new timer
            this.timer = new Timer();

            isRunning = true;
            startDateTime = new Date();

            // Devide the current time into hours, minutes and seconds
            int hours = Integer.parseInt(amount.substring(0, 2));
            int minutes = Integer.parseInt(amount.substring(3, 5));
            int seconds = Integer.parseInt(amount.substring(6, 8));

            timer.schedule(new UpdateUITask(hours, minutes, seconds), 0, 1000);

            displayPause(false);
        } else {
            System.out.println("Timer is already running.");
        }
    }

    public class UpdateUITask extends TimerTask {

        int nSeconds;
        int nMinutes;
        int nHours;

        public UpdateUITask() {
            nSeconds = 0;
            nMinutes = 0;
            nHours = 0;
        }

        /**
         * Call this constructor if you want to resume a timer that is paused.
         *
         * @param s for the seconds in the timer
         * @param m for the minutes in the timer
         * @param h for the hours in the timer
         */
        public UpdateUITask(int h, int m, int s) {
            this.nSeconds = s;
            this.nMinutes = m;
            this.nHours = h;
        }

        @Override
        public void run() {

            nSeconds++;

            if (nSeconds == 60) {
                nMinutes++;
                nSeconds = 0;
            }
            if (nMinutes == 60) {
                nHours++;
                nMinutes = 0;
            }

            amount = numberFormatter.format(nHours) + ":" + numberFormatter.format(nMinutes) + ":"
                    + numberFormatter.format(nSeconds);
            Log.d("a", amount);

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rewriteTextView();
                }
            });
        }
    }
}
