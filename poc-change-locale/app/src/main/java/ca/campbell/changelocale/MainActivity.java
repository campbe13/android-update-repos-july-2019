package ca.campbell.changelocale;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * proof of concept
 * change locale dynamically in android
 *
 * this code is very basic and does not consider the changes
 * for api > 25
 *
 * user beware
 *
 * @author tricia
 * @version 2018-09-22
 */

public class MainActivity extends Activity {
    private LocaleMgmt localeMgr;
    private static final String TAG = "LOCALE";
    //android.os.Build.VERSION_CODES
    private static final String DEVICE_INFO  = "PID "+ android.os.Process.myPid()
            + " Manufacturer " + Build.MANUFACTURER
            + " Model " + Build.MODEL
            + " Build Vers "+ Build.VERSION.RELEASE
            + " SDK "+Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "start");

        localeMgr = new LocaleMgmt(this);

        // force french on startup
        localeMgr.setNewLocale(this,"fr");
        setContentView(R.layout.activity_main);
        setUI();
    }
   public void changeLang(View view) {

       String languageSwap;
       if (view.getTag().toString().isEmpty())
           Toast.makeText(this, "View tag empty unable to swap language", Toast.LENGTH_SHORT).show();

       languageSwap = view.getTag().toString();
       Log.d(TAG, "in changeLang()" + languageSwap);
       localeMgr.setNewLocale(this,languageSwap);
       this.setContentView(R.layout.activity_main);
       Log.d(TAG, "new lang "+languageSwap);
       setUI();
    }
    protected void setUI() {

        ((TextView) findViewById(R.id.tv1)).setText(DEVICE_INFO);

        ((TextView) findViewById(R.id.tv2)).setText("Country " +
                localeMgr.getCountry() +
                " Language " + localeMgr.getLanguage());

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
         super.onConfigurationChanged(newConfig);
         Log.d(TAG, "onConfigurationChanged()");
         setContentView(R.layout.activity_main);
    }
}
