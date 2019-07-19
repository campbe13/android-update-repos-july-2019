package ca.campbell.asynctasklab;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Time;

/**
 * proof of concept code for lab 10 using AsyncTask
 * they have not yet done net i/ so this is a simple
 * thread that they can work on.
 *
 * Similar to a timer, the background thread sleeps
 * @ each second the UI thread is notified
 *
 * @author pmcampbell
 * @version 2018-11-12
 *
 * TODO: update it so the AsyncTask code checks for cancellation  (log it & end the bg task)
 * TODO: update the UI so that the blinking frequency  & limit are input & passed to the asynctask (current is 1  / 40 seconds, use  as default)
 * TODO: change start button when a thread is running it cancels the thread & starts it with the new values
 * TODO: cancel the background thread on state change (ex rotate) & back button
 */
public class MainActivity extends Activity {

    private final String TAG = "PISHPOSH";
    ThreadAsyncTask thread = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View v) {
        Log.d(TAG, "start button hit");

        thread = new ThreadAsyncTask();
        thread.execute();
    } // onClick()

    public  class ThreadAsyncTask extends AsyncTask<Void, Void, Void> {
        static final int SECONDS = 40;
        Button b1, b2;

        // runs on background thread
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "Swapping for "+SECONDS);
            int i = 0;

            for (i = 0; i < SECONDS; i++) {
                // pause 500 milliseconds
                SystemClock.sleep(500);
                if (i % 2 == 0) {
                    /*
                     *  AsyncTask.publishProgress()
                     *  Invoked from within doInBacground() only
                     *  to publish updates on the UI thread
                     *  while the background thread is still running
                     */

                    // update UI with progress every second
                    publishProgress();
                }
            }
            return null;
        }
        protected void onProgressUpdate(Void... voids) {
            Log.d(TAG, "progress update");
            swapColors();
        }
        protected void swapColors() {
            Button b1 = (Button) findViewById(R.id.b1);
            Button b2 = (Button) findViewById(R.id.b2);
            if (b1.getText().toString()  == getResources().getString(R.string.pish) ) {
                b1.setText(R.string.posh);
                b1.setBackgroundColor(getResources().getColor(R.color.poshColour));
                b2.setText(R.string.pish);
                b2.setBackgroundColor(getResources().getColor(R.color.pishColour));
            } else {
                b2.setText(R.string.posh);
                b2.setBackgroundColor(getResources().getColor(R.color.poshColour));
                b1.setText(R.string.pish);
                b1.setBackgroundColor(getResources().getColor(R.color.pishColour));
            }
        }
        @Override
        protected void onCancelled() {
            Log.d(TAG, "BG Thread Canceled");
        }
        protected void onPreExecute(Void voids) {
            Log.d(TAG, "pre execute");
        }

        protected void onPostExecute(Void voids) {
            super.onPostExecute(voids);
            ((TextView)findViewById(R.id.tv)).setText("Finished");
        }
    } // AsyncTask class
}