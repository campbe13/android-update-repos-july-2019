package ca.campbell.bestwebbrowser;

import android.content.pm.PackageInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.webkit.WebView;

//import java.net.URI;
import android.net.Uri;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="BESTB";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
        } catch (RuntimeException e)  {
            // most likely to get an InflateException on a WebView
            // later devices  have No WebView installed due to vulnerabilities
            // TODO: seek another option
            Log.d(TAG, "missing package");
            setContentView(R.layout.error);
            return;
        }

        Uri uri = (Uri)getIntent().getData();
        PackageInfo webViewPackageInfo = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            webViewPackageInfo = WebView.getCurrentWebViewPackage();
            Log.d(TAG, "WebView version: " + webViewPackageInfo.versionName);

        }

        WebView wv = (WebView)findViewById(R.id.wv);
        if ( uri == null)
           uri = Uri.parse("https://xkcd.org");
        Log.d(TAG, uri.toString());
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl(uri.toString());
        }
}
