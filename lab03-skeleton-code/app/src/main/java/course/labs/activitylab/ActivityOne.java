package course.labs.activitylab;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ActivityOne extends Activity {

		// string for logcat documentation
		private final static String TAG = "Lab-ActivityOne";

		// lifecycle counts
		//TODO: Create 7 counter variables, each corresponding to a different one of the lifecycle callback methods.
		//TODO:  increment the variables' values when their corresponding lifecycle methods get called.
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_one);
			
			//Log cat print out
			Log.i(TAG, "onCreate called");
			
			//TODO: update the appropriate count variable & update the view
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.activity_one, menu);
			return true;
		}
		
		// lifecycle callback overrides
		
		@Override
		public void onStart(){
			super.onStart();

			//Log cat print out
			Log.i(TAG, "onStart called");
			//TODO:  update the appropriate count variable & update the view
		}

	    // TODO: implement 5 missing lifecycle callback methods

	    // Note:  if you want to use a resource as a string you must do the following
	    //  getResources().getString(R.string.stringname)   returns a String.

		@Override
		public void onSaveInstanceState(Bundle savedInstanceState){
			//TODO:  save state information with a collection of key-value pairs & save all  count variables
		}


		public void launchActivityTwo(View view) {
			startActivity(new Intent(this, ActivityTwo.class));
		}
		

}
