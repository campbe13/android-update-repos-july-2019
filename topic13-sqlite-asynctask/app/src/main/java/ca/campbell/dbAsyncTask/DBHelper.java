package ca.campbell.dbAsyncTask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* 
 * DBHelper.class
 * is an SQLiteOpenHelper class
 * It is a simple implementation, if you use it use it as a base only you must add a lot of functionality
 * It creates a database and implements some of   the CRUD methods
 */
public class DBHelper extends SQLiteOpenHelper {
    /*
	 * All of the fields here define the database This example is a simple class
	 * so there are no other fields
	 */

    public static final String TAG = "DAO";
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

    // Database creation raw SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_GRADES + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_FIRST_NAME
            + " text not null, " + COLUMN_LAST_NAME + " text not null, "
            + COLUMN_CLASS + " text not null, " + COLUMN_GRADE
            + " integer not null" + ");";
    // static instance to share DBHelper
    private static DBHelper dbh = null;

    /**
     * Constructor
     * <p>
     * super.SQLiteOpenHelper: public SQLiteOpenHelper (Context context, String
     * name, SQLiteDatabase.CursorFactory factory, int version)
     * <p>
     * "Create a helper object to create, open, and/or manage a database."
     * Remember the database is not actually created or opened until one of
     * getWritableDatabase() or getReadableDatabase() is called.
     * <p>
     * constructor is private to prevent direct instantiation only getDBHelper()
     * can be called and it will create or return existing
     */
    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * getDBHelper(Context)
     * <p>
     * The static factory method getDBHelper make's sure that only one database
     * helper exists across the app's lifecycle
     * <p>
     * A factory is an object for creating other objects. It is an abstraction
     * of a constructor.
     */
    public static DBHelper getDBHelper(Context context) {
		/*
		 * Use the application context, which will ensure that you don'
		 * accidentally leak an Activity's context. See this article for more
		 * information: http://bit.ly/6LRzfx
		 */
        if (dbh == null) {
            dbh = new DBHelper(context.getApplicationContext());
        }
        return dbh;
    } // getDBHelper()

    /*
     * onCreate()
     *
     * SQLiteOpenHelper lifecycle method used when we first create the database,
     * in this case we create an empty database. You may want to populate your
     * database with data when you create it, this is app dependent.
     *
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
        createNewDB(database);
    }
	/*
	 * createNewDB()
	 *
	 * invoked  when db is to be created or recreated, uses the constant
	 * sql create statement
	 *
	 */

    private void createNewDB(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }
     /*
	 * populateDB()
	 *
	 * insert a few dummy records
	 *
	 */

    public void populateDB() {
        getWritableDatabase();
        Log.d(DBHelper.class.getName(), "populate db");
        insertNewStudent("Grace", "Hopper", "Intro to CS", 95);
        insertNewStudent("Montse", "Maritxalar", "Intro to CS", 97);
        insertNewStudent("Finn", "Human", "Intro to CS", 30);
        insertNewStudent("Jake", "Dog", "Intro to CS", 30);
    }

    /*
     * onUpgrade()
     *
     * SQLiteOpenHelper lifecycle method used when the database is upgraded to a
     * different version, this one is simple, it drops then recreates the
     * database, you loose all of the data This is not necessarily what you will
     * want to do :p
     *
     * @see
     * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
     * .SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADES);
        createNewDB(db);
        populateDB();
    }

    /*
     * onOpen()
     *
     * SQLiteOpenHelper lifecycle method called when the database has been opened
     * @see
     * android.database.sqlite.SQLiteOpenHelper#onOpen(android.database.sqlite
     * .SQLiteDatabase)on
     */
    public void onOpen(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(DBHelper.class.getName(), "onOpen()");
    } // onOpen()

    /**
     * insertNewStudent()
     * Using ContentValues insert a record in the database
     * CR*U*D Update
     *
     * @param firstName student's given name
     * @param lastName  student's family namme
     * @param className name of the class for which the grade applies
     * @param grade     integer value of the class grade (on 100)
     * @return the return code from the insert
     **/
    public long insertNewStudent(String firstName, String lastName,
                                 String className, int grade) {
        Log.d(DBHelper.class.getName(), "insertNewStudent()");
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME, firstName);
        cv.put(COLUMN_LAST_NAME, lastName);
        cv.put(COLUMN_CLASS, className);
        cv.put(COLUMN_GRADE, grade);

        long code = getWritableDatabase().insert(TABLE_GRADES, null, cv);
        return code;
    }

    /*
     * getGrades()
     *
     * C*R*UD Retrieve
     *
     * returns a Cursor of *all* database records
     */
    public Cursor getGrades() {
        Log.d(DBHelper.class.getName(), "select *");
        return getReadableDatabase().query(TABLE_GRADES, null, null, null,
                null, null, null);
    }

    /**
     * getGrades()
     * C*R*UD Retrieve
     * <p>
     * returns a Cursor of *all* database records
     *
     * @param int limit db limit for query
     */
    public Cursor getGrades(int limit) {
        Log.d(DBHelper.class.getName(), "limit" + limit);
        return getReadableDatabase().query(TABLE_GRADES, null, null, null,
                null, null, Integer.toString(limit));
    }

    /*
     * deleteStudent()
     *
     * CRU*D* Delete
     *
     * @param id database id field
     */
    public void deleteStudent(int id) {
        getWritableDatabase().delete(TABLE_GRADES, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
    }

    /*
     * get30()
     *
     * C*R*UD Retrieve
     *
     * method written to return the set of student records who's grade is
     * exactly 30 for demonstration purposes, not necessarily a useful method
     */
    public Cursor get30() {
        return getWritableDatabase().query(TABLE_GRADES, null,
                COLUMN_GRADE + "=?", new String[]{"30"}, null, null, null);
    }
}