package nl.swiftdevelopment.workwatch;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Time {
	private final static DecimalFormat numberFormatter = new DecimalFormat("00");
	private String amount;
	protected MainActivity context;
	private int id;
	private View view;
	private Timer timer;
	private boolean isRunning;

	public Time(Context context, int id, View view) {
		this.context = (MainActivity) context;
		this.id = id;
		this.view = view;
		start();
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
	}

	public void resume(String curTime) {
		if (isRunning == false) {
			// Declare new timer
			this.timer = new Timer();

			isRunning = true;
			// Devide the current time into hours, minutes and seconds
			int hours = Integer.parseInt(curTime.substring(0, 1));
			int minutes = Integer.parseInt(curTime.substring(2, 4));
			int seconds = Integer.parseInt(curTime.substring(5, 7));

			timer.schedule(new UpdateUITask(hours, minutes, seconds), 0, 1000);
		}else{
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
					try {
						
						TextView blockTime = (TextView) context.gridView.getChildAt(id).findViewById(R.id.block_time);
						blockTime.setText(amount);
						
						Log.d("ID", "" + view.getId());
					} catch (Exception e) {
						Log.d("NEW", "NPE");
					}
				}
			});
		}
	}
}
