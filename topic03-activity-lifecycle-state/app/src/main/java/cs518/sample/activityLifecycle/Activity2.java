package cs518.sample.activityLifecycle;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/*
 * In Activity2.java we use the instanceStateBundle to maintain the counter information
 * In MyActivityLifeCycleActivity.java we do not save any state. 
 * note the run time differences, when/is the counter reset ?
 * 
 */
public class Activity2 extends Activity {
	protected int counter = 0;

	public static final String TAG = "LIFECYC2";

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate() ");
		// using the same layout as in the main activity
		setContentView(R.layout.main);
		// change the message
		((TextView) findViewById(R.id.message)).setText(R.string.messageactivity2);
		// don't use these buttons on 2nd activity
		Button button = (Button) findViewById(R.id.activityButton);
		button.setVisibility(View.GONE);
		button = (Button) findViewById(R.id.dialogueButton);
		button.setVisibility(View.GONE);
		
		// restore savedInstanceState here or in onRestoreInstanceState(Bundle)
		/*
		if (savedInstanceState != null) {
			counter = savedInstanceState.getInt("counter");
		}
		*/
		Button killButton = (Button) findViewById(R.id.killButton);
		killButton.setOnClickListener(new OnClickListener() {
			// @Override
			public void onClick(View v) {
				Log.d(TAG, "finish()");
				finish();
			}
		});

		Button countButton = (Button) findViewById(R.id.countButton);
		// don't use the button in the
		countButton.setOnClickListener(new OnClickListener() {
			// @Override
			public void onClick(View v) {
				Log.d(TAG, "counting");
				counter++;
				updateCounter();
			}
		});

	} // onCreate()
	
	public void rotateScreen(View view) {
		if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		else
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	} // rotateScreen()

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy()");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart()");
	}

	// State methods
	// we don't need to keep the state of EditText etc if we use them,
	// all Views with an id are saved by the superclass in
	// the instance bundle automatically by Android
	// if called it will be run before onStop()
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstanceState()");
		outState.putInt("counter", counter);
	}

	// only called if activity killed
	// restore savedInstanceState here or in onCreate(Bundle)
	protected void onRestoreInstanceState(Bundle inState) {
		super.onRestoreInstanceState(inState);
		Log.d(TAG, "onRestoreInstanceState()");
		// restore savedInstanceState here or in onCreate(Bundle)
		counter = inState.getInt("counter");
		updateCounter();
	}

	private void updateCounter() {
		TextView textView = (TextView) findViewById(R.id.counterValue);
		textView.setText("Actual counter: " + counter);
	}

}
