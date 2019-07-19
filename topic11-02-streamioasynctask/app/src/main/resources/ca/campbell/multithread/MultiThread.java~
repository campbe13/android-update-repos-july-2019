package cs534.sample.multithread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/*
 * This code is an example of doing I/O in a background thread
 * using AsyncTask.  This example is atypical as it only uses the
 * background thread by overriding doInBackground()
 * 
 * remember AsyncTask is good for fairly short running tasks
 * anything long running Java Threads are better.
 * 
 *  The I/O done here is to read a file from the Android SD card
 *  path /sdcard
 *  
 *  To run this code: 
 *  
 *  1 create a small file: 
 *  a. shell into the device  adb shell
 *  b. ls / >  /sdcard/ascii.txt
 *  c. run the app
 *  d. watch the log adb logcat -s MTHREAD
 *  note that the I/O will probably finish before you can page through any of it
 *  
 *  2 create a large file: 
 *  a. shell into the device  adb shell
 *  b. ls -lR / >  /sdcard/ascii.txt
 *  c. run the app
 *  d. watch the log adb logcat -s MTHREAD
 *  note that the I/O will probably continue in the background 
 *  as you are paging through the file 
 *  
 */
public class MultiThread extends Activity implements OnClickListener {
	private boolean aboutTVoff = true;
	private TextView tv, tv2;
	private String data = "";
	private Time today;
	private final String FN = "/sdcard/ascii.txt";
	private final String TAG = "MTHREAD";
	int start = 0;
	Button button, button2;
	GetDataAsync gdAsync = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multi_thread);
		button = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button.setOnClickListener(this);
		button2.setOnClickListener(this);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv = (TextView) findViewById(R.id.textView1);
		today = new Time(Time.getCurrentTimezone());
		today.setToNow();
	}

	/*
	 * This activity implements OnClickListener so here in onClick we check to
	 * see which button was pushed there are only 2. 
	 * 
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		Log.d(TAG, "data leng "+data.length() + " start " + start);
		if (v.getId() == R.id.button1) {
			// if thread still running cancel before new thread
			// I only want 1 background thread
			if (gdAsync != null) 
				gdAsync.cancel(true);
			gdAsync = new GetDataAsync();
			gdAsync.execute(FN);
			start = 0;   // start at beginning of buffer
			if (data.isEmpty()) {
				tv.setText("finished " + today.format("%F: %T") + "\nno data");
			} else {
				String str = "finished " + today.format("%F: %T") + "\n "
						+ data.substring(start, data.length() >= 100 ? 100 	: data.length());
				tv.setText(str);
				button2.setVisibility(View.VISIBLE);
			}
		} else {
			// 2nd button  
			if (data.length() > (start + 100)) {
				start += 100;
				int remaining = data.length() - start;
				tv.setText("finished " + today.format("%F: %T") + "\n "
						+ data.substring(start, remaining  >= 100 ? start + 100 : start + remaining));
			} else {
				tv.setText("End of Data");
			}

		}
	} // onClick()

	/*
	 * Simple form of AsyncTask We do only the background task, none of the
	 * callback methods that run on the UI are used.
	 * 
	 * The only parameter is the file name.
	 * 
	 * progress and result are not used hence Void
	 */
	public class GetDataAsync extends AsyncTask<String, Void, Void> {
		// runs on background thread
		@Override
		protected Void doInBackground(String... fn) {
			Log.d(TAG, "Reading: " + fn[0]);
			getData(fn[0]);
			return null;
		}
		// runs on main ui thread
		@Override
		protected void onCancelled() {
			Log.d(TAG, "BG Thread Canceled");
		}
	} // AsyncTask class

	/**
	 * getData() This method opens a FileInputStream, then uses a BufferedReader
	 * to read the data in line by line catches file not found & loading
	 * exceptions
	 */
	private void getData(String fn) {
		String line = null;
		data = "";
		try {
			Log.d(TAG, "getData() " + fn);
			FileInputStream fis = new FileInputStream (new File(fn)); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			// check if canceled: 
			// getData is run from an AsyncTask 
			// which can be canceled by outside forces
			while (!gdAsync.isCancelled() && (line = reader.readLine()) != null) {
				data += line + "\n";
				Log.d(TAG, "line:  " + line);
			}
			reader.close();
			fis.close();
			Log.d(TAG, "File successfully loaded.");
		} catch (FileNotFoundException e) {
			Log.d(TAG, "Error file not found: " + e.getLocalizedMessage());
			data = "Error file not found: " + e.getLocalizedMessage();
		} catch (IOException e) {
			Log.d(TAG, "Error loading file: " + e.getLocalizedMessage());
			data = "Error loading file: " + e.getLocalizedMessage();
			e.printStackTrace();
		}
	} // getData()

	/*
	 * User presses back key, abort the background task if running
	 *  
	 * (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (gdAsync != null)
			gdAsync.cancel(true);
	}
	/*
	 * If we're ending the task , abort the background task if running,
	 * and not already canceled.
	 *  
	 * (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	public void onStop() {
		super.onStop();
		if (gdAsync != null && !gdAsync.isCancelled()) {
			gdAsync.cancel(true);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_single_thread, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.about:
			if (aboutTVoff) {
				tv2.setVisibility(View.VISIBLE);
				aboutTVoff = false;
			} else {
				tv2.setVisibility(View.GONE);
			}
			return true;
		default:
			return false;
		}
	}
} // Activity class
