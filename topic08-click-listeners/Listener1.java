// 1 setting OnClickListener(s) dynamically
// xml
<Button
    android:id="@+id/button1"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    />
// code, often in onCreate()
{

	// get a handle on the button object
	bt = (Button) findViewById(R.id.button);
	// set the text on the button object
	bt.setText("BUTTON");

	// separate declaration of new OnClickListener
	bt.setOnClickListener(usingContext);
}
// code  interface as a type
private OnClickListener usingContext = 	new OnClickListener() {
	public void onClick(View v) {
		// react to event code goes here
	}
}; // usingContext()
