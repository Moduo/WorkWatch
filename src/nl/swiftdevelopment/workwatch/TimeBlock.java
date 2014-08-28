package nl.swiftdevelopment.workwatch;


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
		return "0:00:00";//time.getCurrentTime();
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
	
	

}
