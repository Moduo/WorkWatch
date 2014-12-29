package nl.swiftdevelopment.workwatch.db;

import nl.swiftdevelopment.workwatch.models.Category;
import android.content.ContentValues;
import android.content.Context;

public class CategoryDataSource extends DataSource{

	public CategoryDataSource(Context context) {
		super(context);
	}

	
	public Category create(Category cat){
		ContentValues values = new ContentValues();
		values.put(WatchesDBOpenHelper.CATEGORY_COLUMN_TITLE, cat.getTitle());
		values.put(WatchesDBOpenHelper.CATEGORY_COLUMN_TOTALTIME, cat.getTotalTime());
		long insertId = database.insert(WatchesDBOpenHelper.TABLE_CATEGORY, null, values);
		cat.setDbId(insertId);
		return cat;
	}
}
