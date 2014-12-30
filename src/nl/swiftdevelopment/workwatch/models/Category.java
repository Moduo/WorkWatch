package nl.swiftdevelopment.workwatch.models;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Categories")
public class Category extends Model {

	@Column(name = "Title")
	private String title;

	@Column(name = "TotalTime")
	private String totalTime;
	
	public List<TimeBlock> timeBlocks(){
		return getMany(TimeBlock.class, "Category");
	}
	
//	public static ArrayList<Category> listOfCategories = new ArrayList<Category>();

//	public static ArrayList<ArrayList<TimeBlock>> listOfCategoryTimeBlocks = new ArrayList<ArrayList<TimeBlock>>();

	public Category(){
		
	}
	
	public Category(String name) {
		super();
		this.title = name;
		this.totalTime = "00:00:00";
//		Category.listOfCategories.add(this);
	}

	public String getTitle() {
		return title;
	}



	// This one needs to be modified so data comes from the database or it must
	// come from a long not an int.
/*	public static Category getCategory(int id) {
		return Category.listOfCategories.get(id);
	}*/

	public String getTotalTime() {
		return totalTime;
	}
	
	/**
	 * Get all the Categories from the DB.
	 * @return A list of Categories.
	 */
	public static ArrayList<Category> getAll() {
	    return new Select()
	        .from(Category.class)
	        .execute();
	}
	
	public static Category get(int id){
		
		return new Select().from(Category.class).where("id = ?", id).executeSingle();
	}

	@Override
	public String toString() {
		return "Category [title=" + title + ", totalTime=" + totalTime + "]";
	}
	
	
	
}
