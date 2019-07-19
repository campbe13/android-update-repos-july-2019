package cs518.sample.usecalendarcontentprovider;
/*
 * Uses the Calendar Content Provider
 * needs the following in the manifest
 *  <uses-permission android:name="android.permission.READ_CALENDAR" />
 *  If you add writing functionality you will also need:
 *  <uses-permission android:name="android.permission.WRITE_CALENDAR" />
 *     
 *  following 
 *  http://developer.android.com/guide/topics/providers/content-providers.html
 *  http://developer.android.com/guide/topics/providers/calendar-provider.html
 *  This is a simple implementation I leave it to you to implement 
 *  a CursorAdapter and an AdapterView and and other functionality.
 */
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Calendars;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView tView = (TextView) findViewById(R.id.calendarview);
		Cursor cursor = getCalendar();

		while (cursor.moveToNext() ) {
			String displayName = cursor.getString(cursor
					.getColumnIndex(Calendars.NAME));
			tView.append("Name: ");
			tView.append(displayName);
			tView.append("\n");
		}
		cursor.close();
	}
	private Cursor getCalendar() {
		// Run query, read all calendars on device 
		Uri uri = Calendars.CONTENT_URI;
		String[] projection = new String[] { Calendars._ID,
				Calendars.NAME, 
				Calendars.ACCOUNT_NAME,
				Calendars.ACCOUNT_TYPE};

		String selection = Calendars.VISIBLE + " = '"
				+ ("1") + "'";
		String[] selectionArgs = null;

		String sortOrder = Calendars._ID 
				+ " COLLATE LOCALIZED ASC";
		/**
		 * Not using SQLiteDatabawe.query(table, ...)
		 * using ContentResolver.query(uri, ...)
		 */
		return getContentResolver().query(uri, projection, selection, selectionArgs,
				sortOrder);
	}

}
