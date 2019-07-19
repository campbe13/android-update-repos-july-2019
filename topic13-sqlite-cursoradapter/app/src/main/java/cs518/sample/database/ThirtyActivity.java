package cs518.sample.database;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/* 
 * This is a very simple  Activity
 * It is used to populate and display the contents of a simple display
 * It uses a DBHelper class for the sqlite database
 * It uses the get30() DBHelper method for a subset of data
 * The onCreate() and onClick() are very similar to DatabaseActivity.class
 * I copied it for simplicity.
 */
public class ThirtyActivity extends Activity implements OnClickListener {
	private DBHelper dbh;
	private LinearLayout ll;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * This activity is using the same layout as DataBaseActivity You
		 * usually do not do this, I did it for simplicity.
		 */
		setContentView(R.layout.activity_main);

		TextView tvMain = (TextView) findViewById(R.id.tv_main);
		tvMain.setText(R.string.thirtytitle);

		ll = (LinearLayout) findViewById(R.id.mainLayout);

		dbh = DBHelper.getDBHelper(this);

		Cursor cursor = dbh.get30();

		while (cursor.moveToNext()) {

			TextView tv = new TextView(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, (float)2);
			tv.setLayoutParams(lp);

			LinearLayout newLL = new LinearLayout(this);
			LayoutParams newLP = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			newLL.setLayoutParams(newLP);
			newLL.setOrientation(LinearLayout.HORIZONTAL);

			int id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID));
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

			tv.setId(id);

			tv.setText(toWrite);

			newLL.addView(tv);

			Button btn = new Button(this);
			btn.setBackgroundColor(getResources().getColor(R.color.Red));
			btn.setText(R.string.delete);
			btn.setId(id);
			lp.weight = (float)1; 
			btn.setLayoutParams(lp);

			btn.setOnClickListener(this);

			newLL.addView(btn);

			ll.addView(newLL);

		}
		cursor.close();
	}

	/*
	 * This Activity implements OnClickListener All button events have been
	 * assigned this so will execute this onClick code
	 *
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
		// forces redraw when main thread goes idle. i.e. schedules a redraw
		// after other work
		// so it seems out of sync does not immediately redraw
		ll.invalidate();
	}
}