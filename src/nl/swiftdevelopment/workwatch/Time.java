package nl.swiftdevelopment.workwatch;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

public class Time {
	private final static DecimalFormat numberFormatter = new DecimalFormat("00");
	private String amount;
	protected MainActivity context;
	private int id;

	public Time(Context context){
		this.context = (MainActivity) context;
		start();
	}
	
	
	public String getCurrentTime() {
		return amount;
	}

	public void start() {
		Timer timer = new Timer();
		timer.schedule(new UpdateUITask(), 0, 1000);

	}

	public void pause() {

	}

	public void resume(String curTime) {

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
	                	try{
	                    TextView timeLabel = (TextView) context.findViewById(R.id.block_time);
	                    timeLabel.setText(amount);
	                	}catch(Exception e){
	                		Log.d("NEW", "NPE");
	                	}
	                }
	            });
		}
	}
}
