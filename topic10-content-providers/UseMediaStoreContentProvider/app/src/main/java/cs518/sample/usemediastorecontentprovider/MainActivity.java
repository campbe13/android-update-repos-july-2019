package cs518.sample.usemediastorecontentprovider;
/*
 * Uses the MediaStore Content Provider
 * needs the following in the manifest
 *  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 *  <uses-permission android:name="android.permission.READ_INTERNAL_STORAGE" />
 *  follow your book
 *  This is a simple implementation I leave it to you to implement 
 *  a CursorAdapter and an AdapterView and and other functionality.
 */
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Cursor cursor;
		String counts;
		int count=50;
		TextView tView = (TextView) findViewById(R.id.calendarview);
		
		cursor = getAudio(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
		counts = "External audio files: " + cursor.getCount() + " (Showing 50)\n";
		tView.append(counts);
		
		while (cursor.moveToNext() && count-- > 0 ) {
			String displayName = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.TITLE));
			tView.append("Name: ");
			tView.append(displayName);
			String lengSeconds = (cursor.getInt(cursor
					.getColumnIndex(MediaStore.Audio.Media.DURATION)) / 1000) + "s" ;
			tView.append(" Seconds: ");
			tView.append(lengSeconds);
			tView.append("\n");
		}
		cursor.close();
		
		cursor = getAudio(MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
		counts = "Internal audio files: " + cursor.getCount() + " (Showing 50)\n";
		tView.append(counts);
        count=50;
		while (cursor.moveToNext() && count-- > 0 ) {
			String displayName = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.TITLE));
			tView.append("Name: ");
			tView.append(displayName);
			String lengSeconds = (cursor.getInt(cursor
					.getColumnIndex(MediaStore.Audio.Media.DURATION)) / 1000) + "s" ;
			tView.append(" Seconds: ");
			tView.append(lengSeconds);
			tView.append("\n");
		}
		cursor.close();
	}
	private Cursor getAudio(Uri uri) {
		// Run query, read all audio files on 
		String[] projection = new String[] { 
				MediaStore.Audio.Media.TITLE, 
				MediaStore.Audio.Media.DURATION };
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = null;
		/**
		 * Not using SQLiteDatabawe.query(table, ...)
		 * using ContentResolver.query(uri, ...)
		 */
		return getContentResolver().query(uri, projection, selection, selectionArgs,
				sortOrder);
	}

}
