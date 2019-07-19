// 2 setting OnClickListener(s) dynamically
// xml
<Button
    android:id="@+id/button1"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    />
// code
{
	// get a handle on the button object
	bt = (Button) findViewById(R.id.button);
	// set the text on the button object
	bt.setText("BUTTON");

	// class MyClickListener implements OnClickListener
	bt.setOnClickListener(new MyClickListener());
}

// a private class implements member class OnClickListener
private class MyClickListener implements OnClickListener {
	public void onClick(View v) {
			// react to event code goes here
		}
	}
