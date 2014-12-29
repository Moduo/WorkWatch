package nl.swiftdevelopment.workwatch.models;


import java.text.DecimalFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import nl.swiftdevelopment.workwatch.R;
import nl.swiftdevelopment.workwatch.WatchOverviewActivity;
import nl.swiftdevelopment.workwatch.R.id;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class Time {
	private final static DecimalFormat numberFormatter = new DecimalFormat("00");
	private String amount;
	protected WatchOverviewActivity context;
	private int id;
	private long dbId;
	private View view;
	private Timer timer;
	private boolean isRunning;
	private Date startDateTime;
	GridLayout layout;

	public Time(Context context, int id, View view) {
		this.context = (WatchOverviewActivity) context;
		this.id = id;
		this.view = view;
		startDateTime = new Date();
		start();

	}

	/**
	 * This method is strictly for resetting id's only. When removing a
	 * TimeBlock from the GridView, this method is used to reset the ID's of the
	 * timers for a proper id/position ratio.
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
		rewriteTextView();

		// Check if timer is running to set the pause button overlay for the
		// time block.
		if (isRunning() == true) {
			displayPause(false);
		}else{
			displayPause(true);
		}
	}

	public int getId(){
		return id;
	}
	
	
	
	public long getDbId() {
		return dbId;
	}

	public void setDbId(long dbId) {
		this.dbId = dbId;
	}

	/**
	 * Rewrite the time of the textview with the current time of this object.
	 */
	private void rewriteTextView() {
		try {
			// Get current TimeBlock and start setting the text on
			// the TextView each second.
			TextView blockTime = (TextView) context.gridView.getChildAt(id)
					.findViewById(R.id.block_time);
			blockTime.setText(amount);

			Log.d("ID", "" + view.getId());
		} catch (Exception e) {
			Log.d("NEW", "NPE");
		}
	}

	/**
	 * Display or hide the pause button as overlay for the time block.
	 * @param bool True == display <br />False == hide
	 */
	private void displayPause(boolean bool) {
		if (bool == true) {
			// Display pause img as overlay on the timeblock
			layout = (GridLayout) this.context.gridView.getChildAt(id)
					.findViewById(R.id.overlay);
			layout.setVisibility(View.VISIBLE);
		} else {
			// Remove pause overlay
			layout = (GridLayout) this.context.gridView.getChildAt(id)
					.findViewById(R.id.overlay);
			layout.setVisibility(View.INVISIBLE);
		}
	}

	public Date getStartDateTime(){
		return startDateTime;
	}
	
	public String getCurrentTime() {
		return amount;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void start() {
		timer = new Timer();
		timer.schedule(new UpdateUITask(), 0, 1000);
		isRunning = true;
	}

	public void stop() {
		isRunning = false;
		timer.cancel();
		timer.purge();

		displayPause(true);
	}

	public void resume() {
		if (isRunning == false) {
			// Declare new timer
			this.timer = new Timer();

			isRunning = true;
			// Devide the current time into hours, minutes and seconds
			int hours = Integer.parseInt(amount.substring(0, 1));
			int minutes = Integer.parseInt(amount.substring(2, 4));
			int seconds = Integer.parseInt(amount.substring(5, 7));

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
		 * @param s
		 *            for the seconds in the timer
		 * @param m
		 *            for the minutes in the timer
		 * @param h
		 *            for the hours in the timer
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

			amount = nHours + ":" + numberFormatter.format(nMinutes) + ":"
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
