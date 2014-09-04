package nl.swiftdevelopment.workwatch;

import java.util.ArrayList;


public class TimeBlock {
	
	Time time;
	String title;
	/**
	 * @param time
	 * @param title
	 */
	public TimeBlock(Time time, String title) {
		super();
		this.time = time;
		this.title = title;
	}
	public String getTime() {
		return time.getCurrentTime();
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public static void resetIdsOfTimes(ArrayList<TimeBlock> listOfBlocks){
		int i = 0;
		for(TimeBlock tb : listOfBlocks){
			tb.time.setId(i);
			i++;
		}
	}

}
