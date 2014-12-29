package nl.swiftdevelopment.workwatch.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataSource {

	protected static final String LOGTAG = "database";
	
	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	
	public DataSource(Context context) {
		dbhelper = new WatchesDBOpenHelper(context);
	}

	public void open() {
		Log.i(LOGTAG, "Database opened");
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		Log.i(LOGTAG, "Database closed");
		dbhelper.close();
	}
}
