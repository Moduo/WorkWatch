package nl.swiftdevelopment.workwatch.db;

import nl.swiftdevelopment.workwatch.models.Time;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class TimeDataSource extends DataSource {

	private static final String[] allColumns = {
			WatchesDBOpenHelper.TIME_COLUMN_ID,
			WatchesDBOpenHelper.TIME_COLUMN_ISRUNNING,
			WatchesDBOpenHelper.TIME_COLUMN_STARTDATETIME,
			WatchesDBOpenHelper.TIME_COLUMN_AMOUNT };

	public TimeDataSource(Context context) {
		super(context);
	}

	public Time create(Time time) {
		ContentValues values = new ContentValues();
		values.put(WatchesDBOpenHelper.TIME_COLUMN_AMOUNT,
				time.getCurrentTime());
		values.put(WatchesDBOpenHelper.TIME_COLUMN_ISRUNNING, time.isRunning());
		values.put(WatchesDBOpenHelper.TIME_COLUMN_STARTDATETIME, time
				.getStartDateTime().toString());

		long insertId = database.insert(WatchesDBOpenHelper.TABLE_TIME, null,
				values);
		time.setDbId(insertId);

		return time;
	}

	public Time getTime(int id) {
		Time time;
		
		Cursor cursor = database.query(WatchesDBOpenHelper.TABLE_TIME,
				allColumns, WatchesDBOpenHelper.TIME_COLUMN_ID + " = " + id,
				null, null, null, null);
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			time = new Time(context, id, view)
		}
		return time;
	}
}
