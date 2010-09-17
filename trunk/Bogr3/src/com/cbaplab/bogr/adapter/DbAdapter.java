package com.cbaplab.bogr.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cbaplab.bogr.Constants;
import com.cbaplab.bogr.model.Quote;

public class DbAdapter {
	private static final String TAG = "DbAdapter";

	static final String DATABASE_NAME = Constants.BASE_FILE_NAME;
	private static final int DATABASE_VERSION = 2;

	private static final String QUOTE_TABLE_NAME = "quotes";
	public static final String QUOTE_ID = "_id";
	public static final String QUOTE_TYPE = "qtype";
	public static final String QUOTE_SOURCE_TYPE = "qstype";
	public static final String QUOTE_TITLE = "title";
	public static final String QUOTE_BODY = "body";
	public static final String QUOTE_PUBDATE = "pubdate";
	public static final String QUOTE_CREATED_DATE = "createddate";

	public static final int COL_QUOTE_ID = 0;
	public static final int COL_QUOTE_TYPE = 1;
	public static final int COL_QUOTE_SOURCE_TYPE = 2;
	public static final int COL_QUOTE_TITLE = 3;
	public static final int COL_QUOTE_BODY = 4;
	public static final int COL_QUOTE_PUBDATE = 5;
	public static final int COL_QUOTE_CREATED_DATE = 6;

	private SQLiteDatabase db;
	private final Context context;
	private DbHelper dbHelper;

	public DbAdapter(Context _context) {
		if (Log.isLoggable(Constants.LOG_TAG_MAIN, Log.DEBUG))
			Log.d(TAG, "BogrDbAdapter");
		context = _context;
		dbHelper = new DbHelper(context);
	}

	public DbAdapter open() throws SQLException {
		if (Log.isLoggable(Constants.LOG_TAG_MAIN, Log.DEBUG))
			Log.d(TAG, "open()");
		try {
			if (Log.isLoggable(Constants.LOG_TAG_MAIN, Log.DEBUG))
				Log.d(TAG, "open().getWritableDatabase");
			db = dbHelper.getWritableDatabase();
		} catch (Exception ex) {
			if (Log.isLoggable(Constants.LOG_TAG_MAIN, Log.DEBUG))
				Log.d(TAG, "open().getReadableDatabase");
			db = dbHelper.getReadableDatabase();
		}
		return this;
	}

	public void close() {
		if (Log.isLoggable(Constants.LOG_TAG_MAIN, Log.DEBUG))
			Log.d(TAG, "close");
		db.close();
	}

	public long insert(Quote _quote) {
		ContentValues contentValues = new ContentValues();
		Long now = Long.valueOf(System.currentTimeMillis());
		contentValues.put(QUOTE_TYPE, _quote.getType());
		contentValues.put(QUOTE_SOURCE_TYPE, _quote.getType());
		contentValues.put(QUOTE_TITLE, _quote.getTitle().toString());
		contentValues.put(QUOTE_BODY, _quote.getBody().toString());
		contentValues.put(QUOTE_PUBDATE, _quote.getPubDate().toString());
		contentValues.put(QUOTE_CREATED_DATE, now);
		return db.insert(QUOTE_TABLE_NAME, null, contentValues);
	}

	public Boolean delete(long _id) {
		if (Log.isLoggable(Constants.LOG_TAG_MAIN, Log.DEBUG))
			Log.d(TAG, "delete(" + _id + ")");
		return db.delete(QUOTE_TABLE_NAME, QUOTE_ID + "=" + _id, null) > 0;
	}

	public Boolean deleteAll() {
		if (Log.isLoggable(Constants.LOG_TAG_MAIN, Log.DEBUG))
			Log.d(TAG, "deleteAll()");
		return db.delete(QUOTE_TABLE_NAME, null, null) > 0;
	}

	public Boolean deleteAll(int _type) {
		if (Log.isLoggable(Constants.LOG_TAG_MAIN, Log.DEBUG))
			Log.d(TAG, "deleteAll(" + _type + ")");
		return db.delete(QUOTE_TABLE_NAME, QUOTE_TYPE + "=" + _type, null) > 0;
	}

	public Cursor fetchAll() {
		if (Log.isLoggable(Constants.LOG_TAG_MAIN, Log.DEBUG))
			Log.d(TAG, "fetchAll()");
		return db.query(QUOTE_TABLE_NAME, new String[] { QUOTE_ID, QUOTE_TYPE,
				QUOTE_TITLE, QUOTE_BODY, QUOTE_PUBDATE, QUOTE_CREATED_DATE },
				null, null, null, null, null);
	}

	public Cursor fetchAll(int _type) {
		if (Log.isLoggable(Constants.LOG_TAG_MAIN, Log.DEBUG))
			Log.d(TAG, "fetchAll(" + _type + ")");
		return db.query(QUOTE_TABLE_NAME, new String[] { QUOTE_ID, QUOTE_TYPE,
				QUOTE_TITLE, QUOTE_BODY, QUOTE_PUBDATE, QUOTE_CREATED_DATE },
				QUOTE_TYPE + "=" + _type, null, null, null, null); // QUOTE_TITLE+" DESC"
	}

	public Cursor fetchItem(long _id) throws SQLException {
		if (Log.isLoggable(Constants.LOG_TAG_MAIN, Log.DEBUG))
			Log.d(TAG, "fetchItem(" + _id + ")");
		Cursor mCursor = db.query(true, QUOTE_TABLE_NAME, new String[] {
				QUOTE_ID, QUOTE_TYPE, QUOTE_TITLE, QUOTE_BODY, QUOTE_PUBDATE,
				QUOTE_CREATED_DATE }, QUOTE_ID + "=" + _id, null, null, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	public Boolean isExist(String _title, int _type) {

		Cursor c = db.query(true, QUOTE_TABLE_NAME, new String[] { QUOTE_ID, },
				"(" + QUOTE_TITLE + "='" + _title + "') AND (" + QUOTE_TYPE
						+ "=" + _type + ")", null, null, null, null, null);
		int count = c.getCount();
		c.close();
		return count > 0;
	}

	private static class DbHelper extends SQLiteOpenHelper {
		DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			if (Log.isLoggable(Constants.LOG_TAG_MAIN, Log.DEBUG))
				Log.d(TAG, "DbHelper");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			if (Log.isLoggable(Constants.LOG_TAG_MAIN, Log.DEBUG))
				Log.d(TAG, "DbHelper.onCreate");
			db.execSQL("CREATE TABLE " + QUOTE_TABLE_NAME + " (" + QUOTE_ID
					+ " INTEGER PRIMARY KEY," + QUOTE_TYPE + " INTEGER,"
					+ QUOTE_SOURCE_TYPE + " INTEGER," + QUOTE_TITLE + " TEXT,"
					+ QUOTE_BODY + " TEXT," + QUOTE_PUBDATE + " TEXT,"
					+ QUOTE_CREATED_DATE + " INTEGER" + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (Log.isLoggable(Constants.LOG_TAG_MAIN, Log.DEBUG))
				Log.d(TAG, "DbHelper.onUpgrade(" + oldVersion + ","
						+ newVersion + ")");
			if (Log.isLoggable(Constants.LOG_TAG_MAIN, Log.DEBUG))
				Log.w(TAG, "Upgrading database from version " + oldVersion
						+ " to " + newVersion
						+ ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + QUOTE_TABLE_NAME);
			onCreate(db);
		}
	}

}