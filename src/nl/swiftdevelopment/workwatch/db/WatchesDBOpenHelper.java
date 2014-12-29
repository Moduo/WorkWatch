package nl.swiftdevelopment.workwatch.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WatchesDBOpenHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "database";

	private static final String DATABASE_NAME = "workwatch.db";
	private static final int DATABASE_VERSION = 1;

	/******************* Category table setup ********************/
	public static final String TABLE_CATEGORY = "category";
	public static final String CATEGORY_COLUMN_ID = "categoryId";
	public static final String CATEGORY_COLUMN_TITLE = "title";
	public static final String CATEGORY_COLUMN_TOTALTIME = "totalTime";

	private static final String TABLE_CATEGORY_CREATE = 
			"CREATE TABLE " + TABLE_CATEGORY + " (" 
			+ CATEGORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ CATEGORY_COLUMN_TITLE + " TEXT, " 
			+ CATEGORY_COLUMN_TOTALTIME + " TEXT " 
			+ ")";

	/******************* Time table setup ********************/
	public static final String TABLE_TIME = "time";
	public static final String TIME_COLUMN_ID = "timeId";
	public static final String TIME_COLUMN_ISRUNNING = "isRunning";
	public static final String TIME_COLUMN_STARTDATETIME = "startDateTime";
	public static final String TIME_COLUMN_AMOUNT = "amount";

	private static final String TABLE_TIME_CREATE = 
			"CREATE TABLE " + TABLE_TIME + " (" 
			+ TIME_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ TIME_COLUMN_ISRUNNING + " INTEGER, "
			+ TIME_COLUMN_STARTDATETIME + " DATETIME, "
			+ TIME_COLUMN_AMOUNT + " VARCHAR(8)" 
			+ ")";

	/******************* TimeBlock table setup ********************/
	public static final String TABLE_TIMEBLOCK = "timeBlock";
	public static final String TIMEBLOCK_COLUMN_TITLE = "title";

	private static final String TABLE_TIMEBLOCK_CREATE = 
			"CREATE TABLE " + TABLE_TIMEBLOCK + " (" 
			+ TIME_COLUMN_ID + " INTEGER, " 
			+ CATEGORY_COLUMN_ID	+ " INTEGER, " 
			+ TIMEBLOCK_COLUMN_TITLE + " TEXT, "
			+ "FOREIGN KEY (" + TIME_COLUMN_ID + ") REFERENCES " + TABLE_TIME + "(" + TIME_COLUMN_ID + ") ON DELETE CASCADE,"
			+ "FOREIGN KEY (" + TIME_COLUMN_ID + ") REFERENCES " + TABLE_TIME + "(" + TIME_COLUMN_ID + ") ON DELETE CASCADE,"
			+ ")";

	/**
	 * Foreign key support must be enabled before using it in SQLite 3
	 */
	private static final String ENABLE_CONSTRAINTS = "PRAGMA foreign_keys = ON";

	public WatchesDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Foreign keys feature must be enabled before using it in SQLite 3
		db.execSQL(ENABLE_CONSTRAINTS);

		// Create all the tables
		db.execSQL(TABLE_CATEGORY_CREATE);
		db.execSQL(TABLE_TIME_CREATE);
		db.execSQL(TABLE_TIMEBLOCK_CREATE);

		Log.i(LOGTAG, "Table has been created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop all the tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMEBLOCK);

		// and recreate them.
		onCreate(db);

		Log.i(LOGTAG, "Database has been upgraded from " + oldVersion + " to "
				+ newVersion);
	}

}
