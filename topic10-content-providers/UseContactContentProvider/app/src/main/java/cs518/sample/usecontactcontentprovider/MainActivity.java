package cs518.sample.usecontactcontentprovider;
/*
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
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView contactView = (TextView) findViewById(R.id.tv);
		Cursor cursor = getContacts();
		
		contactView.append("Number of Contacts: " + cursor.getCount() + " (Showing first 50)\n");
		
		int count=50;  // limit to first 50 contacts
		while (cursor.moveToNext() && (count-- > 0) ) {
			String displayName = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
			contactView.append("Name: ");
			contactView.append(displayName);
			// get phone numbers for this contact
			String contactId =
					cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			String phoneNum = getPhoneNum(contactId);


			contactView.append(" Phone1: ");
			contactView.append(phoneNum);
			contactView.append("\n");
		}
		cursor.close();
	}

	private Cursor getContacts() {
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
		/**
		 * Not using SQLiteDatabawe.query(table, ...)
		 * using ContentResolver.query(uri, ...)
		 */
		return getContentResolver().query(uri, projection, selection, selectionArgs,
				sortOrder);
	}
	private String getPhoneNum(String contactId) {		
		String number;
		Uri uri = Phone.CONTENT_URI;
		String[] projection = null;
		String selection = Phone.CONTACT_ID  + " = '"
				+ contactId + "'";
		String[] selectionArgs = null;
		String sortOrder = null;

		Cursor phones = getContentResolver().query(uri, projection, selection, selectionArgs,
				sortOrder);

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

}
