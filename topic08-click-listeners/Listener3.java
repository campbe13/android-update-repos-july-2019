// 3 setting OnClickListener(s) dynamically
// xml
<Button
    android:id="@+id/button1"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    />

// code 
{
	// get a handle on the button object
	bt = (Button) findViewById(R.id.button1);
	// set the text on the button object
	bt.setText("BUTTON");
			
	// anonymous inner class OnClickListener
	bt.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				// react to event code goes here
			}
		});
}