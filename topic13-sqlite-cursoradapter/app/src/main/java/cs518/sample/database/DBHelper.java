package cs518.sample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** 
 * DBHelper.class
 * is an SQLiteOpenHelper class
 * It is a simple implementation, if you use it use it as a base only you must add a lot of functionality
 * It implements  all of the CRUD methods
 */
public class DBHelper extends SQLiteOpenHelper {
	public static final String TAG = "DBHELP";
	/*
	 * All of the fields here define the database This example is a simple class
	 * so there are no other fields
	 */

	// table name
	public static final String TABLE_GRADES = "grades";
	// database field names (COLUMN_
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_FIRST_NAME = "firstName";
	public static final String COLUMN_LAST_NAME = "secondName";
	public static final String COLUMN_CLASS = "class";
	public static final String COLUMN_GRADE = "grade";
	// file name
	private static final String DATABASE_NAME = "grades.db";
	// if the version number is increased the onUpdate() will be called
	private static final int DATABASE_VERSION = 1;
	// static instance to share DBHelper
	private static DBHelper dbh = null;
	// Database creation raw SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_GRADES + "( " + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_FIRST_NAME
			+ " text not null, " + COLUMN_LAST_NAME + " text not null, "
			+ COLUMN_CLASS + " text not null, " + COLUMN_GRADE
			+ " integer not null" + ");";

	/**
	 * Constructor
	 * 
	 * super.SQLiteOpenHelper: public SQLiteOpenHelper (Context context, String
	 * name, SQLiteDatabase.CursorFactory factory, int version)
	 * 
	 * "Create a helper object to create, open, and/or manage a database."
	 * Remember the database is not actually created or opened until one of
	 * getWritableDatabase() or getReadableDatabase() is called.
	 * 
	 * constructor is private to prevent direct instantiation only getDBHelper()
	 * can be called and it will create or return existing
	 */
	private DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * getDBHelper(Context)
	 * 
	 * The static factory method getDBHelper makes sure that only one database
	 * helper exists across the app's lifecycle
	 * @param Context
	 * @return DBHelper
	 */
	public static DBHelper getDBHelper(Context context) {
		/*
		 * Use the application context, which will ensure that you don't
		 * accidentally leak an Activity's context. See this article for more
		 * information: http://bit.ly/6LRzfx
		 */
		if (dbh == null) {
			dbh = new DBHelper(context.getApplicationContext());
			Log.i(TAG, "getDBHelper, dbh == null");
		}
		Log.i(TAG, "getDBHelper()");
		return dbh;
	} // getDBHelper()

	/**
	 * onCreate()
	 * 
	 * SQLiteOpenHelper lifecycle method used when we first create the database,
	 * in this case we create an empty database. You may want to populate your
	 * database with data when you create it, this is app dependent.
	 * @param SQLiteDatabase
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase database) {
		/*
		 * this is one of the few places where it is not an horrific idea to use
		 * raw SQL.
		 */
		database.execSQL(DATABASE_CREATE);
		Log.i(TAG, "onCreate()");
	}

	/**
	 * onUpgrade()
	 * 
	 * SQLiteOpenHelper lifecycle method used when the database is upgraded to a
	 * different version, this one is simple, it drops then recreates the
	 * database, you loose all of the data This is not necessarily what you will
	 * want to do :p
	 * @param SQLiteDatabase
	 * @param oldVersion old version number
	 * @param newVersion new version number
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, DBHelper.class.getName() + "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADES);
		onCreate(db);
		Log.i(TAG, "onUpgrade()");
	}
	/**
	 * onOpen()
	 * 
	 * SQLiteOpenHelper lifecycle method called when we open the database,
	 * @param SQLiteDatabase
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onOpen(android.database.sqlite.SQLiteDatabase)
	 * .SQLiteDatabase)
	 */
	@Override
	public void onOpen(SQLiteDatabase database) {
		Log.i(TAG, "onOpen()");
	}
	/**
	 * insertNewStudent()
	 * 
	 * *C*RUD create
	 * 
	 * @param firstName student's given name
	 * @param lastName student's family name
	 * @param className name of the class for which the grade applies
	 * @param grad integer value of the class grade (on 100)
	 * @return the return code from the insert
	 * @see SQLiteDatabase.insert()
	 * Using ContentValues insert a record in the database
	 */
	public long insertNewStudent(String firstName, String lastName,
			String className, int grade) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_FIRST_NAME, firstName);
		cv.put(COLUMN_LAST_NAME, lastName);
		cv.put(COLUMN_CLASS, className);
		cv.put(COLUMN_GRADE, grade);

		long code = getWritableDatabase().insert(TABLE_GRADES, null, cv);
       
		return code;
	}

	/**
	 * getGrades()
	 * 
	 * C*R*UD Retrieve
	 * @return a Cursor of *all* database records
	 * 
	 */
	public Cursor getGrades() {
		return getReadableDatabase().query(TABLE_GRADES, null, null, null,
				null, null, null);
	}

	/**
	 * deleteStudent()
	 * 
	 * CRU*D* Delete
	 * 
	 * @param id database id field
	 * 
	 */
	public void deleteStudent(int id) {
		getWritableDatabase().delete(TABLE_GRADES, COLUMN_ID + "=?",
				new String[] { String.valueOf(id) });
	}

	/**
	 * get30()
	 * 
	 * C*R*UD Retrieve
	 * 
	 * method written to return the set of student records who's grade is
	 * exactly 30 for demonstration purposes, not necessarily a useful method
	 * @return a Cursor of *all* database records
	 * 
	 */
	public Cursor get30() {
		return getReadableDatabase().query(TABLE_GRADES, null,
				COLUMN_GRADE + "=?", new String[] { "30" }, null, null, null);
	}
	/**
	 * SQLiteDatabase.getWriteableDatabase()
	 * Throws SQLiteException if the db cannot be opened for writing
	 * 
	 * first time it is called the db will be opened and the following lifecycle methods will be called
	 * SQLiteDatabase.onCreate()   (when db is created for the first time)
	 * SQLiteDatabase.onUpgrade()   
	 * SQLiteDatabase.onOpen()	  
	 * 
	 * If the open is successful the db is cached so it is ok to call this method every time you need to write.
	 * However be sure to call close() when you no longer need the database.
	 * 
	 * Warning both methods may take a long time to return, 
	 *   so you should not call it from the application main thread
	 *   we will use small dbs until we can cover threading in android.
	 */
	
	/**
	 * SQLiteDatabase.getReadableDatabase()
	 * Throws SQLiteException if the db cannot be opened
	 * 
	 *  same object returned by getWriteableDatabase() unless the database needs to be opened read only
	 *  due to some problem, such as full disk.  
	 *  
	 *  returns: a database object valid until getWritableDatabase() or close() is called.
	 * 
	 */
}