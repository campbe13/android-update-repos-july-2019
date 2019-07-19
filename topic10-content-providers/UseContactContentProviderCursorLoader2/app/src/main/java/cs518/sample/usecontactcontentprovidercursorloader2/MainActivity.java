package cs518.sample.usecontactcontentprovidercursorloader2;
/*
 * This is the base code from UseContactContentProvider
 * It has been modified 
 * 1. to use  a Loader and the associated lifecycle methods
 * 2. to use a notification of progress in the Action Bar
 * 
 * Original code: 
 * Uses the Contacts Content Provider 
 * needs the following in the manifest
 *     <uses-permission android:name="android.permission.READ_CONTACTS"/>
 *  following 
 *  http://developer.android.com/guide/topics/providers/content-providers.html
 *  http://developer.android.com/guide/topics/providers/contacts-provider.html
 *  http://www.vogella.com/tutorials/AndroidSQLite/article.html#tutorialusecp_example
 *  
 *  This is a simple implementation I leave it to you to implement 
 *  a CursorAdapter and an AdapterView and and other functionality.
 */
import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.TextView;


public class MainActivity extends Activity 
implements LoaderManager.LoaderCallbacks<Cursor> {
	public static final String CHANNELID = "CHANNEL1";
    // for notifier
	int id = 1;
	NotificationManager notifMan;
	Builder builder;
	// for UI
	TextView tView; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tView  = (TextView) findViewById(R.id.tv);
		
		setProgressNotification();
		/* 
		 * prepare the loader, if it's first run it will create
		 * if we're being refreshed it will reconnect
		 */
		/*
		 * initLoader()
		 * Parameters
		 * id	A unique identifier for this loader. Can be whatever you want. 
		 *     	Identifiers are scoped to a particular LoaderManager instance.
		 * args	Optional arguments to supply to the loader at construction. 
		 * 		If a loader already exists (a new one does not need to be created),
		 * 		this parameter will be ignored and the last arguments continue to be used.
		 * callback	 Interface the LoaderManager will call to report about changes in the state of the loader. 
		 */
		getLoaderManager().initLoader(0, null, (LoaderCallbacks<Cursor>) this);
	}

	/**
	 * onCreateLoader()
	 * 
	 * Lifecycle method for the Loader Instantiate and return a new Loader for the given ID.
	 * 
	 * @param id
	 * @param args
	 * @return Loader<Cursor>
	 */
	public  Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return getContacts(); 
	}
	private Loader<Cursor> getContacts() {
		// Run query
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[] { 
				ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME };

		String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
				+ ("1") + "'";
		String[] selectionArgs = null;

		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
				+ " COLLATE LOCALIZED ASC";
		/*
		 * CursorLoader "is a loader that queries the ContentResolver and returns a Cursor. 
		 * This class implements the Loader protocol in a standard way for querying cursors,
		 * building on AsyncTaskLoader to perform the cursor query on a background thread so 
		 * that it does not block the application's UI."
		 */


		return 	new CursorLoader(this,uri, projection, selection, selectionArgs, sortOrder);
		// return loader.loadInBackground();
		//return getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
	}
	/**
	 * onLoadFinished
	 * 
	 * Lifecycle method for the loader Called when a previously created loader has finished its load.
	 * 
	 * @param loader
	 * @param cursor
	 */
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// read the cursor
		tView.append("Number of Contacts: " + cursor.getCount());
		while (cursor.moveToNext()) {
			String displayName = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
			tView.append("\nName: ");
			tView.append(displayName);
			// get phone numbers for this contact
			String contactId =
					cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			String phoneNum = getPhoneNum(contactId);
			tView.append(" Phone1: ");
			tView.append(phoneNum);
			tView.append("\n");
		}
		endProgressNotification();
	}
	/**
	 * onLoadReset()
	 * 
	 * Lifecycle method for the loader Called when a previously created loader is being reset, 
	 * thus making its data unavailable.
	 * 
	 * @param loader
	 * @param cursor
	 */

	public void onLoaderReset(Loader<Cursor> loader) {
		// Here is where I would reset the cursor adapter.
		// This is called when the last Cursor provided to onLoadFinished()
		// above is about to be closed.  We need to make sure we are no
		// longer using it.
		// 	cursoradapter.swapCursor(null);
	}


	private String getPhoneNum(String contactId) {		
		String number;
		Uri uri = Phone.CONTENT_URI;
		String[] projection = null;
		String selection = Phone.CONTACT_ID  + " = '"
				+ contactId + "'";
		String[] selectionArgs = null;
		String sortOrder = null;
		CursorLoader loader = 
				new CursorLoader(this,uri, projection, selection, selectionArgs, sortOrder);
		Cursor phones =  loader.loadInBackground();
		//Cursor phones = getContentResolver().query(uri, projection, selection, selectionArgs,sortOrder);

		// returns false if cursor empty 
		if (phones.moveToFirst()) {
			number = phones.getString(phones.getColumnIndex(Phone.NUMBER));
			int type = phones.getInt(phones.getColumnIndex(Phone.TYPE));
			switch (type) {
			case Phone.TYPE_HOME:
				number += " home";
				break;
			case Phone.TYPE_MOBILE:
				number += " mobile";
				break;
			case Phone.TYPE_WORK:
				number += " work";
				break;
			}
		} else {
			number = "none";
		}
		phones.close();
		return number;
	}
	/*
	 *  hese two progess methods set up, start then end a progress indicator
	 *  in the notification bar
	 *  1. set up progress bar
	 *  2. start the progress bar
	 */
	private void setProgressNotification() { 
		// 1. set up progress bar as notification
		notifMan  =
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		builder = new NotificationCompat.Builder(this, CHANNELID);
		// AutoCancel true means it disappears on click, but we need pending intent
		builder.setContentTitle("Reading Contacts")
		.setContentText("db i/o in progress")
		.setSmallIcon(R.drawable.notification)
		.setAutoCancel(true); 
		// intent that's fired by the notification cancel	         
		builder.setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0));

		// Sets the progress indicator to a max value, the
		// current completion percentage, and "determinate" state:
		// true= continuous indicator, no need to update
		// false= update the indicator with progress as you go
		// 2.  start the progress notification
		builder.setProgress(100, 0, true);
		notifMan.notify(id, builder.build());
	}
	// 3.  finish progress notification
	private void endProgressNotification() {

		builder.setContentText("i/o complete").setProgress(0, 0, false);
		notifMan.notify(id, builder.build());
	}
}