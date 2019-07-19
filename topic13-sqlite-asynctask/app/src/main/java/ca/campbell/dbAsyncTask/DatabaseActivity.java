package ca.campbell.dbAsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
/*
 * Note: this is the same code that was used in the previous
 * SQLite + CursorAdapter + Listview example
 * It has been modified to put some of the DB tasks in a background thread
 * using AsyncTask
 * AddStudent: InsertDataTask
 * DatabaseActivity: GetDataTask
 * 
 * I leave it as an exercise for you students to code this better. 
 * It is meant to be an example of how to use AsyncTask with SQLite db access
 */
import android.widget.Toast;

/**
 * DataBaseActivity.class
 * <p>
 * This is the main Activity , as indicated in the AndroidManifest.xml
 * It is used to populate and display the contents of a simple display
 * It uses a DBHelper class for the sqlite database
 * <p>
 * Wherever strings constants are used inline "like this" you should use
 * the values directories, the same with any integers, arrays, dimensions or colour constants.
 * <p>
 * This code uses a ListView with multiple TextViews and a SimpleCursorAdapter
 * Look at the code SQLite-simple-db to see how to do the same thing
 * but completely programmatically.
 * This is more efficient and more future proof than implementing it yourself
 * <p>
 * This code implements an options menu programmatically.
 * Each menu item click will fire an intent.
 * <p>
 * Note this Activity performs queries on the UI thread, this is not a
 * good practice and can lead to ANR, lagging display etc.
 * Soon we will look at ways to avoid long running code on the main UI thread.
 **/
public class DatabaseActivity extends Activity {

    // My DBHelper class
    private static DBHelper dbh;
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;
    private SimpleCursorAdapter sca;
    GetDataTask getData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // the projection (fields from the database that we want to use)
        String[] from = {DBHelper.COLUMN_ID, DBHelper.COLUMN_FIRST_NAME,
                DBHelper.COLUMN_LAST_NAME, DBHelper.COLUMN_CLASS,
                DBHelper.COLUMN_GRADE};
        // matching fields on the layout to be used with the adapter
        int[] to = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5};
        setContentView(R.layout.activity_main);

        dbh = populateDB();
        ListView lv = (ListView) findViewById(R.id.list);
        /*
         * public SimpleCursorAdapter (Context context, int layout, Cursor c,
		 * String[] from, int[] to, int flags)
		 * 
		 * -layout resource identifier of a layout file that defines the views
		 * for this list item. The layout file should include at least those
		 * named views defined in "to"
		 * 
		 * -c The database cursor. Can be null if the cursor is not available
		 * yet.
		 * 
		 * -from A list of column names representing the data to bind to the UI.
		 * Can be null if the cursor is not available yet.
		 * 
		 * -to The views that should display column in the "from" parameter.
		 * These should all be TextViews. The first N views in this list are
		 * given the values of the first N columns in the from parameter. Can be
		 * null if the cursor is not available yet.
		 * 
		 * -flags Flags used to determine the behavior of the adapter, as per
		 * CursorAdapter(Context, Cursor, int).
		 */
        /*
		 * The helper returns a cursor, which is a set of database records.
		 */
        Cursor init = dbh.getGrades();
        sca = new SimpleCursorAdapter(this, R.layout.grade_row, init, from,
                to, 0);

        lv.setAdapter(sca);
        lv.setOnItemClickListener(deleteThis);
        refreshView();
        // don't close the cursor, it is used by the adapter
        // cursor.close()
    }

    public void onResume() {
        super.onResume();
        logIt("onResume()");
        // if this is not here, the view is not refreshed after adding
        refreshView();
    }

    public void refreshView() {
        getData = new GetDataTask();
        getData.execute();
    }

    private DBHelper populateDB() {

        logIt("populateDB()");
        DBHelper dbh = DBHelper.getDBHelper(this);
        Cursor init = dbh.getGrades(2);
        if (init == null || init.getCount() == 0)
            dbh.populateDB();
        return dbh;

    }

    /*
     * This is an Item Click Listener, for use with the ListView When an item in
     * the list is clicked, the corresponding database record is deleted.
     */
    public OnItemClickListener deleteThis = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Cursor temp = (Cursor) parent.getItemAtPosition(position);
            for (int i = 0; i < 5; i++)
                logIt(i + "  " + temp.getString(i));
            // CursorAdapter matches id to database record _id
            logIt(" id:" + id);
            // first column is id getInt gets column data as int
            int idDB = temp.getInt(0);
            logIt("_id: " + idDB);
            dbh.deleteStudent((int) id);
            refreshView();
        }
    }; // deleteThis

    @Override
	/*
	 * This is an options menu. There are two items programmatically added to
	 * the menu: add student fires an intent to invoke AddStudent class show 30%
	 * fires an intent to invoke Thirty class
	 * 
	 * Since we are just firing intents with our menu items there is no need to
	 * override onOptionsItemSelected()
	 */
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Set up the intents for each button
        Intent add = new Intent(this, AddStudentActivity.class);
        Intent thirty = new Intent(this, ThirtyActivity.class);

        // add a menu items with the Intents, should use strings.xml for
        // "Add Student" & "Show 30s"
		/*
		 * This could be done through XML but since we are directly firing an
		 * intent this is more explicit
		 */
        menu.add("Add Student").setIntent(add)
                .setShowAsAction(SHOW_AS_ACTION_IF_ROOM);
        menu.add("Show 30s").setIntent(thirty)
                .setShowAsAction(SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    public void logIt(String msg) {
        final String TAG = "DBCURSOR";
        Log.d(TAG, msg);
    }

    private class GetDataTask extends AsyncTask<Void, Void, Cursor> {
        private final ProgressDialog dialog = new ProgressDialog(DatabaseActivity.this);

        protected void onPreExecute() {
            this.dialog.setMessage("Getting data...");
            this.dialog.show();
        }

        protected Cursor doInBackground(Void... args) {
            return dbh.getGrades();
        }

        protected void onPostExecute(Cursor cursor) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            // have the adapter use the new cursor
            sca.changeCursor(cursor);
            // have the adapter tell the observers
            sca.notifyDataSetChanged();
        }
    } // GetDataTask class (AsyncTask)
}