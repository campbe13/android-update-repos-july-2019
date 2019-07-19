package com.androidbook.simpleasync;
/**
 * This class is the example from the book chapter you have on moodle
 * From Android Wireless Application Development Volume II Chapter 1
 * 
 * Uses Android AsyncTask
 **/
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;
import android.widget.Toast;

public class SimpleAsyncActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count);

        TextView msg = (TextView) findViewById(R.id.text);
        msg.setText(R.string.asynctaskthread);
        Toast.makeText(this, "AsyncTask thread", Toast.LENGTH_LONG).show();
        // Start counting off the main UI thread
        // each thread must be a new instance of the class
        CountingTask tsk = new CountingTask();
        tsk.execute();
    }
/*
 * CountingTask is an AsyncTask
 * so it implements the lifecycle callback methods
 * 
 */
    private class CountingTask extends AsyncTask<Void, Integer, Integer> {

   //     CountingTask() {}
    	// AsyncTask.doInBackground 
        @Override
        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
         * 
         * the only method that runs in the background thread
         * 
         * this is where we do the long running computation or I/O
         */
        protected Integer doInBackground(Void... unused) {

            int i = 0;

            while (i < 100) {
            	// pause 500 milliseconds
                SystemClock.sleep(500);
                i++;

                if (i % 5 == 0) {
                	/*
                	 *  AsyncTask.publishProgress()
                	 *  Invoked from within doInBacground() only 
                	 *  to publish updates on the UI thread
                	 *  while the background thread is still running
                	 */
                    // update UI with progress every 5%
                	
                    publishProgress(i);
                }
            }

            return i;
        }
        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onProgressUpdate(java.lang.Object[])
         * 
         * runs on the UI thread (so I can update the views)
         * 
         * invoked after publishProgress() is run in the background thread
         * parameter(s) is/are the value from publishProgress()
         */
        protected void onProgressUpdate(Integer... progress) {
            TextView tv = (TextView) findViewById(R.id.counter);
            tv.setText(progress[0] + "% Complete!");
        }
        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         *  
         * runs on the UI thread (so I can update the views)
         * 
         * invoked when doInBackground() ends
         * parameter is the value returned by doInBackgroun()
         */
        protected void onPostExecute(Integer result) {
            TextView tv = (TextView) findViewById(R.id.counter);
            tv.setText("Count Complete! Counted to " + result.toString());
        }
        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         * 
         * runs on the UI thread (so I can update the views)
         * 
         * invoked before doInBackground() 
         * no parameters
         * not implemented for this app
         */
        protected void onPreExecute() {
        	// set up the task here
        	
        }
    } // class CountingTask
}