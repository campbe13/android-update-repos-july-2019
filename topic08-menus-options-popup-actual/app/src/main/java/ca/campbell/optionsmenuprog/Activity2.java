package ca.campbell.optionsmenuprog;

import android.media.Image;
import android.os.Bundle;
import android.app.Activity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity2 extends Activity {
private static final String TAG = "OPTMENU2";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// using the same layout as MainActivity with a few tweaks
		TextView tv = (TextView) findViewById(R.id.instrs);
		tv.setText(R.string.sound_credits);
		tv.setAutoLinkMask(Linkify.ALL);
		tv.setLinksClickable(true);

		tv = (TextView) findViewById(R.id.instrs2);
		tv.setText(R.string.instractivity2);

		ImageView iv = (ImageView) findViewById(R.id.imageButton);
		iv.setClickable(false);

		Button bt = (Button) findViewById(R.id.button);
		bt.setVisibility(View.INVISIBLE);
	}

}
