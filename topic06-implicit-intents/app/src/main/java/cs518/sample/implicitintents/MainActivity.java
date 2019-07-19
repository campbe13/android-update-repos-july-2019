package cs518.sample.implicitintents;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
/**
 * Sample code for Implicit Intents
 * Implicit means the OS uses the URI to determine the 
 * activity that is launched. 
 * for other intents and other options with the intents used here
 * see https://developer.android.com/guide/components/intents-common
 *
 *  @author Tricia
 *
 */
public class MainActivity extends Activity {
	private Spinner spinner;
	public static final String TAG = "IMPLICIT";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.intents, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	public void intentSelected(View view) {
		int position = spinner.getSelectedItemPosition();
		Intent intent = null;
		switch (position) {
		case 0:
			Log.d(TAG, "action view + data http uri");
			intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://www.smbc-comics.com/"));
			break;
		case 1:
			Log.d(TAG, "action call + data tel uri");
			// number entered and dialer started
			intent = new Intent(Intent.ACTION_CALL,
					Uri.parse("tel:(514)123-4567"));
			break;
		case 2:
			Log.d(TAG, "action dial + data tel uri");
			// number entered, waits for user to send
			intent = new Intent(Intent.ACTION_DIAL,
					Uri.parse("tel:(514)123-4567"));
			break;
		case 3:
			Log.d(TAG, "action view + data geo uri");
			// won't work on avd without installing some software
			// test on real device
			// geo:latitude, longitude, altitude;u=uncertainty
			intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("geo:45.4897,-73.5878?z=10"));
			break;
		case 4:
			Log.d(TAG, "action view + data geo uri");
			intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("geo:0,0?q=Paris"));
			break;
		case 5:
			Log.d(TAG, "action media image cap");
			intent = new Intent("android.media.action.IMAGE_CAPTURE");
			break;
		case 6:
			Log.d(TAG, "action view + data content uri");
			intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("content://contacts/people/"));
			break;
		case 7:
			Log.d(TAG, "action view + content geo uri");
			intent = new Intent(Intent.ACTION_EDIT,
					Uri.parse("content://contacts/people/1"));
			break;
		case 8:
			Log.d(TAG, "action alarm set timer + special data");
				intent = new Intent(AlarmClock.ACTION_SET_TIMER)
						.putExtra(AlarmClock.EXTRA_MESSAGE,"Power Nap timer" )
						.putExtra(AlarmClock.EXTRA_LENGTH, 900 )
						.putExtra(AlarmClock.EXTRA_SKIP_UI,true );
				break;

		}

		if (intent.resolveActivity(getPackageManager()) != null) {
				startActivity(intent);
			} else {
				Toast.makeText(this, R.string.activity_error,
						Toast.LENGTH_LONG).show();
			}

		}
	}