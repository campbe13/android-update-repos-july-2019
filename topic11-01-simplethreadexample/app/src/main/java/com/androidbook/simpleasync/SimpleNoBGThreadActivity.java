package com.androidbook.simpleasync;

/*
 * This class is the an example I coded, it does not use a separate thread
 * I expect it to cause an ANR
 * 
 */
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;
import android.widget.Toast;

public class SimpleNoBGThreadActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.count);
		TextView tv = (TextView) findViewById(R.id.counter);
		// Start counting on the main UI thread
		Toast.makeText(this, "no bg thread", Toast.LENGTH_LONG).show();
		int i = 0;
		tv.setText(Integer.toString(i) + "% Complete!");
		while (i < 100) {
			// pause 1000 milliseconds
			SystemClock.sleep(500);
			i++;

			if (i % 5 == 0) 
				// update UI with progress every 5%

				tv.setText(Integer.toString(i) + "% Complete!");
			}
		tv.setText("Count Complete! Counted to " + Integer.toString(i));
	}
}  // class SimpleNoBGThread