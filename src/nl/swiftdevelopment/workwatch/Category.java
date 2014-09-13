package nl.swiftdevelopment.workwatch;

import java.util.ArrayList;

public class Category {
	private String title, totalTime;
	private int id;
	public static ArrayList<Category> listOfCategories = new ArrayList<Category>();

	public static ArrayList<ArrayList<TimeBlock>> listOfCategoryTimeBlocks = new ArrayList<ArrayList<TimeBlock>>();
	public Category(String name, int id){
		super();
		this.title = name;
		this.id = id;
		Category.listOfCategories.add(this);
	}
	public String getTitle(){
		return title;
	}
	public int getId(){
		return id;
	}
	public static Category getCategory(int id){
		return Category.listOfCategories.get(id);
	}
}
