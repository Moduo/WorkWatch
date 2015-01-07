package nl.swiftdevelopment.workwatch.models;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "TimeBlocks")
public class TimeBlock extends Model {

	@Column(name = "Time")
	private Time time;

	@Column(name = "Title")
	private String title;

	@Column(name = "Category")
	private Category category;

	public static ArrayList<TimeBlock> listOfTimeBlocks = new ArrayList<TimeBlock>();

	public TimeBlock() {
		super();
	}

	/**
	 * @param time
	 * @param title
	 */
	public TimeBlock(Time time, String title, Category category) {
		super();
		this.time = time;
		this.title = title;
		this.category = category;
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

	/**
	 * Get's a TimeBlock from the DB with it's ID.
	 * 
	 * @param id
	 *            The ID of the TimeBlock.
	 * @return a TimeBlock object.
	 */
	public static TimeBlock get(Long id) {
		return new Select().from(TimeBlock.class).where("id = ?", id)
				.executeSingle();
	}

	/**
	 * 
	 * @param category
	 *            The category of which you want to get all the TimeBlocks from.
	 * @return A list of TimeBlocks.
	 */
	public static ArrayList<TimeBlock> getAll(Category category) {
		return new Select().from(TimeBlock.class)
				.where("Category = ?", category.getId()).execute();
	}

	/*
	 * public void resetIdsOfTimers(){ int i = 0; for(TimeBlock tb :
	 * Category.listOfCategoryTimeBlocks.get(category.getId())){
	 * tb.time.setId(i); i++; } }
	 */

}
