package nl.swiftdevelopment.workwatch.models;

import java.util.ArrayList;

public class Category {
	private String title, totalTime;
	private int id;
	private long dbId;
	public static ArrayList<Category> listOfCategories = new ArrayList<Category>();

	public static ArrayList<ArrayList<TimeBlock>> listOfCategoryTimeBlocks = new ArrayList<ArrayList<TimeBlock>>();
	public Category(String name, int id){
		super();
		this.title = name;
		this.id = id;
		totalTime = "00:00:00";
		Category.listOfCategories.add(this);
	}
	public String getTitle(){
		return title;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	
	
	
	public long getDbId() {
		return dbId;
	}
	public void setDbId(long dbId) {
		this.dbId = dbId;
	}
	//This one needs to be modified so data comes from the database or it must come from a long not an int.
	public static Category getCategory(int id){
		return Category.listOfCategories.get(id);
	}
	public String getTotalTime(){
		return totalTime;
	}
}
