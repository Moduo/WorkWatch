package nl.swiftdevelopment.workwatch.db;

import java.util.ArrayList;

import nl.swiftdevelopment.workwatch.models.Time;
import nl.swiftdevelopment.workwatch.models.TimeBlock;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class TimeBlockDataSource extends DataSource{
	
	private static final String[] allColumns = {
		WatchesDBOpenHelper.TIME_COLUMN_ID,
		WatchesDBOpenHelper.CATEGORY_COLUMN_ID,
		WatchesDBOpenHelper.TIMEBLOCK_COLUMN_TITLE
	};

	public TimeBlockDataSource(Context context){
		super(context);
	}
	
	public TimeBlock create(TimeBlock timeBlock){
		ContentValues values = new ContentValues();
		values.put(WatchesDBOpenHelper.TIME_COLUMN_ID, timeBlock.getTime().getId());
		values.put(WatchesDBOpenHelper.CATEGORY_COLUMN_ID, timeBlock.getCategory().getId());
		values.put(WatchesDBOpenHelper.TIMEBLOCK_COLUMN_TITLE, timeBlock.getTitle());
		
		database.insert(WatchesDBOpenHelper.TABLE_TIMEBLOCK, null, values);
		
		return timeBlock;
	}
	
	public ArrayList<TimeBlock> getTimeBlocksFromCategory(int id){
		ArrayList<TimeBlock> listOfTimeBlocks = new ArrayList<TimeBlock>();
		Cursor cursor = database.query(WatchesDBOpenHelper.TABLE_TIMEBLOCK, allColumns, allColumns[1] + " = " + id, null, null, null, null);
		
		if(cursor.getCount() > 0){
			while(cursor.moveToNext()){
				Time time = TimeDataSource.getTime();
				TimeBlock tb = new TimeBlock(time, title, category)
				listOfTimeBlocks.add(tb);
			}
		}
		
		return listOfTimeBlocks;
	}
}
