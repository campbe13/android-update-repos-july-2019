package course.labs.activitylab;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class ActivityTwo extends Activity {

	// string for logcat documentation
	private final static String TAG = "Lab-ActivityTwo";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two);
		
		//Log cat print out
		Log.i(TAG, "onCreate called");
		
	}
}
