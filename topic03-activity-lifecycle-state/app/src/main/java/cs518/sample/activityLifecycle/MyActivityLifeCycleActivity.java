package cs518.sample.activityLifecycle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * In Activity2.java we use the instanceStateBundle to maintain the counter information
 * In MyActivityLifeCycleActivity.java we do not save any state. 
 * note the run time differences, when/is the counter reset ?
 * @author PMCampbell
 * @version 2016
 */

public class MyActivityLifeCycleActivity extends Activity {
	protected int counter = 0;

	public static final String TAG = "LIFECYC";

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate() ");
		setContentView(R.layout.main);


		// I am adding the listeners programmatically, 
		// this is done via an anonymous inner class

		// This can also be done via the android:onclick attribute
		// A couple of buttons are coded  here to show you an example of programmatic
		// implemenatation
		Button killButton = (Button) findViewById(R.id.killButton);
		killButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG, "finish()");
				finish();
			}
		});

		Button countButton = (Button) findViewById(R.id.countButton);
		countButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG, "counting");
				counter++;
				TextView textView = (TextView) findViewById(R.id.counterValue);
				textView.setText("Actual counter: " + counter);
			}
		});

		Button activityButton = (Button) findViewById(R.id.activityButton);
		activityButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG, "fire intent");
				Intent launchOtherScreen = new Intent(getApplicationContext(),
						Activity2.class);
				startActivity(launchOtherScreen);
			}
		});

		// Button dialogueButton = (Button) findViewById(R.id.dialogueButton);
		// The listener for this Button  is the set  via the android:onclick attribute
		// see launchDialogue
		// so we don't need a reference to it unless we are changing something.
	
	}   //onCreate()
	
	public void launchDialogue(View view)  {

		Log.d(TAG, "launch alert dialogue");
		AlertDialog.Builder builder = 
				new AlertDialog.Builder(this);

		// setting Message and only 1 button
		// can set 3 buttons pos, neg, neutral
		// can set other attributes 
		builder.setMessage(R.string.dialog_message)
			.setPositiveButton(R.string.dialog_ok, 
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// user clicked button
								}
		});
		// Create the AlertDialog object and return it
		AlertDialog dialog = builder.create();
		dialog.show();
	} // launchDialogue()
	
	public void rotateScreen(View view) {
		if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
	       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		else 
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}  //rotateScreen()

	// the below lifecycle methods are implemented in order to log their execution
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

}  //MyActivityLifecycle