package cs518.sample.database;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
/** 
 * DataBaseActivity.class
 * 
 * This is the main Activity , as indicated in the AndroidManifest.xml
 * It is used to populate and display the contents of a simple display
 * It uses a DBHelper class for the sqlite database
 * 
 * Wherever strings constants are used inline "like this" you should use
 * the values directories, the same with any integers, arrays, dimensions or colour constants.
 * 
 * This code creates a LinearLayout programmatically for each database record
 * returned by the cursor. This is to illustrate how it works, a better
 * solution would be to either
 * 1.  Inflate an XML template for use with each database record
 * 2.  Use a ListView and a CursorAdapter
 * 
 * This code implements and options menu programmatically. 
 * Each menu item click will fire an intent.
 *
 * TODO modify to use an AsyncTask
 * TODO create version using Room persistence lib instead of sqlite lib calls
 *
 */
public class DatabaseActivity extends Activity implements OnClickListener {

	// My DBHelper class
	private static DBHelper dbh;
	public static final int SHOW_AS_ACTION_IF_ROOM = 1;
	// used as a reference to the XML main LinearLayout;
	private LinearLayout ll;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView tvMain = (TextView) findViewById(R.id.tv_main);
		/*
		 * Setting a TextView with text formated using html tags Normally this
		 * would be in a string.xml and assigned using R.string.stringname it is
		 * here for illustration.
		 */
		tvMain.setText(Html
				.fromHtml("Welcome to my <b>Database</b> application.<br> "
						+ "Below you can see all the students <i>registered</i> in the database. <br><br>"
						+ "If it is empty then there are no records in the database.<hr>"
						+ "If nothing shows in the ActionBar above then <br>press the <b>MENU</b> button."
						+ "That will give you options."));

		// instantiate the DBHelper class
		dbh = DBHelper.getDBHelper(this);

		// there is a layout within a ScrollView in XML we will add to it.
		ll = (LinearLayout) findViewById(R.id.mainLayout);
		/*
		 * The helper returns a cursor, which is a set of database records.
		 */
		Cursor cursor = dbh.getGrades();

		/*
		 * loop through the set returned by the cursor The cursor starts before
		 * the first result row, so on the first iteration this moves to the
		 * first result if it exists. If the cursor is empty, or the last row
		 * has already been processed, then the loop exits neatly. You may also
		 * use a combination of cursor.moveToFirst(), cursor.isAfterLast(),
		 * cursor.moveToNext() ...
		 */
		while (cursor.moveToNext()) {
			/*
			 * We loop through the set and use the fields we programmatically
			 * create as UI Views Each record is within a LinearLayout,
			 * horizontally: <LinearLayout> <TextView> <Button> </LinearLayout>
			 * 
			 * This example is to illustrate how to do it without a ListView +
			 * CursorAdapter. There will be other example(s) using a ListView +
			 * CursorAdapter.
			 */

			/*
			 * Set up the LinearLayout to display the data in the cursor we are
			 * creating all of the Views (UI components) programmatically then
			 * populating them with data from the cursor
			 */
			LinearLayout newLL = new LinearLayout(this);
			LayoutParams newLP = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			LayoutParams tvLP = new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, (float)3);
			LayoutParams btLP = new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, (float)1);
			newLL.setLayoutParams(newLP);
			newLL.setOrientation(LinearLayout.HORIZONTAL);

			// TextView
			TextView tv = new TextView(this);
			tv.setLayoutParams(tvLP);

			// get the id of the DB record
			int id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID));
			// set the DB record id as the TextView id (it does not have to be
			// unique)
			// we do the same thing with the Button
			tv.setId(id);

			// get the data from the db record
			String firstName = cursor.getString(cursor
					.getColumnIndex(DBHelper.COLUMN_FIRST_NAME));
			String lastName = cursor.getString(cursor
					.getColumnIndex(DBHelper.COLUMN_LAST_NAME));
			String className = cursor.getString(cursor
					.getColumnIndex(DBHelper.COLUMN_CLASS));
			String grade = cursor.getString(cursor
					.getColumnIndex(DBHelper.COLUMN_GRADE));

			String toWrite = firstName + " " + lastName + " - " + className
					+ " - " + grade;

			// add the data to the view
			tv.setText(toWrite);

			// add the TextView to the LinearLayout
			newLL.addView(tv);

			// Add a button for each record, after the TextView
			Button btn = new Button(this);
			btn.setBackgroundResource(R.color.Red);
			btn.setPadding(0, 0, 0, 0);

			btn.setText("Delete");
			// set the Button id as the DB record id (it does not have to be
			// unique)
			// we do the same thing with the TextView
			btn.setId(id);
			btn.setLayoutParams(btLP);

			btn.setOnClickListener(this);
			// add the Button to the LinearLayout
			newLL.addView(btn);

			/*
			 * add the LinearLayout we just constructed to the main 
			 * LinearLayout instantiated from XML through setContentView()
			 */
			ll.addView(newLL);
		}
		cursor.close();
	}

	@Override
	/*
	 * This is an options menu. There are two
	 * items programmatically added to the menu:
	 * 	 add student fires an intent to invoke AddStudent class 
	 * 	 show 30% fires an intent to invoke Thirty class
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

	/* 
	 * This Activity implements OnClickListener
	 * All button events have been assigned this so will execute this onClick code
	 *  
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View view) {
		// Get the id from the view
		// We set the id to match the database id when we created the Views
		int id = view.getId();
		// delete the record associated with the id
		dbh.deleteStudent(id);
		// remove view
		ll.removeView(view);
		// forces redraw when main thread goes idle.  i.e. schedules a redraw after other work
		// so it seems out of sync does not immediately redraw 
		ll.invalidate();
	}
}