package com.androidbook.simpleasync;
/**
 * This class is the example from the book chapter you have on moodle
 * From Android Wireless Application Development Volume II Chapter 1
 * 
 * Uses Java Threads
 * Meant to contrast with AsyncTask do not implement in your code.
 * Must be managed by programmer.
 *
 **/
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;
import android.widget.Toast;

public class SimpleThreadActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count);

        final TextView tv = (TextView) findViewById(R.id.counter);
        TextView msg = (TextView) findViewById(R.id.text);
        msg.setText(R.string.javathread);
        Toast.makeText(this, "java thread", Toast.LENGTH_LONG).show();
        // instantiate the Thread with a Runnable()
        new Thread(new Runnable() {
        	// start the thread
            public void run() {

                int i = 0;

                while (i < 100) {
                    SystemClock.sleep(500);
                    i++;

                    final int curCount = i;
                    if (curCount % 5 == 0) {
                    	/*
                    	 * View.post(Runnable) causes
                    	 * the Runnable to be added to the message queue.
                    	 * The runnable will be run on the user interface thread
                    	 */
                        tv.post(new Runnable() {
                            public void run() {
                                // update UI with progress every 5%
                                tv.setText(curCount + "% Complete!");
                            }
                        });
                    }
                }

                tv.post(new Runnable() {
                    public void run() {
                        tv.setText("Count Complete!");
                    }
                });
            }

        }).start();

    }

}