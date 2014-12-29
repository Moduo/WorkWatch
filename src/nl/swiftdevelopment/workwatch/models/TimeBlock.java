package nl.swiftdevelopment.workwatch.models;

import java.util.ArrayList;


public class TimeBlock {
	
	private Time time;
	private String title;
	private Category category;
	public static ArrayList<TimeBlock> listOfTimeBlocks = new ArrayList<TimeBlock>();
	
	/**
	 * @param time
	 * @param title
	 */
	public TimeBlock(Time time, String title, Category category) {
		super();
		this.time = time;
		this.title = title;
		this.category = category;
		Category.listOfCategoryTimeBlocks.get(category.getId()).add(this);
	}
	public Time getTime() {
		return time;
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
	
	public Category getCategory() {
		return category;
	}
	public void resetIdsOfTimers(){
		int i = 0;
		for(TimeBlock tb : Category.listOfCategoryTimeBlocks.get(category.getId())){
			tb.time.setId(i);
			i++;
		}
	}

}
