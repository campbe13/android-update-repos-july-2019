package ca.campbell.httpexamplepost;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


/*
 * HttpURLConnectionExamplePOST
 * 
 * This app sends a URL uses HttpURLConnection class + POST
 *
 * to download a JSON result and display some of it in a jsonResult
 * I don't parse the JSON, I leave that up to you  
 * remember: object = {}  array = []  
 * 
 * The api used is api.edamam.com, using Java 6 org.json 
 * http://www.json.org/java/
 * note GSON is nicer to use if you want
 * https://sites.google.com/site/gson/gson-user-guide
 * For the API info and key  check www.edamam.com
 * 	example
 * 	curl -d @recipe.json -H "Content-Type: application/json" "https://api.edamam.com/api/nutrition-details?app_id=${YOUR_APP_ID}&app_key=${YOUR_APP_KEY}"
 * 	test with
 * 	curl -d @recipe.json -H "Content-Type: application/json" "https://api.edamam.com/api/nutrition-details?app_id=${e6501390}&app_key=${71b785fe9091261dbe192db334d77dbd}"
 *
 * LAB-LAB-LAB-LAB:
 * If you are looking at this code in order to do the lab the following must be done:
 * 
 * 1.  TODO Fix the Show Certs, it crashes, look in the log and discover why, then fix it!
 * 2.  TODO Deconstruct the JSON returned.  Using either GSON or the JSON classes and read the number of calories,
 *     display the number of calories and what ever other info you want.
 * 
 * 3.  TODO (Optional)  the ingr is an array, modify the app to read several ingredients then get the returned info
 *     for that "recipe".
 */
public class HttpsExamplePOST extends Activity {
    private static final String TAG = "HttpURLPOST";
    private static final int NETIOBUFFER = 1024;
    /*
     * Strings used for the api http://www.edamam.com
     *
     *  n.b. do not hard code an api key it should be in a config file
     *
     *  This is my key and my appid
     *  GET YOUR OWN if you will be using this in your app
     *  For the purposes of the lab you can use mine, I think it will not exceed quota.
     */
    /* 2014 key
    private static final String APPKEY = "71b785fe9091261dbe192db334d77dbd";
	private static final String APPID = "e6501390";
	*/
    /// 2015 key, still good in 2016
    private static final String APPKEY = "77397e154a68c54cb1380e268edffa84";
    private static final String APPID = "b3f7ba07";
    // api url
    private static final String APIURL = "https://api.edamam.com/api/nutrient-info";

    private String urlString, jsonData;
    private EditText foodIngr;
    private TextView jsonResult, ingr;
	/* 
	 * you may use these classes to build & deconstruct JSON
	private JSONObject foodJson;
	private JSONArray ingrJson;
	 */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_example);

        foodIngr = (EditText) findViewById(R.id.food);
        jsonResult = (TextView) findViewById(R.id.jsonResult);
        ingr = (TextView) findViewById(R.id.ingr);

        // check: encode only the parms, POST data and URL are ok
        try {
            urlString = APIURL + "?extractOnly&app_id="
                    + URLEncoder.encode(APPID, "UTF-8")
                    + "&app_key=" + URLEncoder.encode(APPKEY, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            jsonResult.setText("Unable to encode params for URL, contact developer");
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "API URL: " + urlString);
    }

    /*
     * When user clicks Load it Button, we use the data and
     * execute an AsyncTask to do the download. Before
     * attempting to fetch the URL, makes sure that there is a network
     * connection.
     */
    public void clickHandler(View view) {
        // Gets the data to look up  from the UI's text field.
        String foodInfo = foodIngr.getText().toString();
        if (foodInfo.isEmpty())
            foodInfo = "1 tablespoon Butter";
        // this api requires json data via post for the food request
        jsonData = "{ \"title\": \"Check Food\", "
                + " \"ingr\": [  \"" + foodInfo + "\" ] }";

        ingr.setText("Food: " + foodInfo);

        // first check to see if we can get on the network
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // invoke the AsyncTask to do the dirty work.
            new DownloadFoodData().execute(urlString, jsonData);
        } else {
            jsonResult.setText("No network connection available.");
        }
    } // clickHandler()

	/*
	 * Uses AsyncTask to create a task away from the main UI thread. This task
	 * takes a URL string and uses it to create an HttpsUrlConnection. Once the
	 * connection has been established, the AsyncTask downloads the contents of
	 * the webpage via an an InputStream. The InputStream is converted into a
	 * string, which is displayed in the UI by the AsyncTask's onPostExecute
	 * method.
	 */

    private class DownloadFoodData extends AsyncTask<String, Void, String> {

        // onPreExecute log some info
        // runs in calling thread (in UI thread)
        protected void onPreExecute() {
            Log.d(TAG, "url " + urlString);
            Log.d(TAG, "json " + jsonData);

        }

        // onPostExecute displays the results of the AsyncTask.
        // runs in calling thread (in UI thread)
        protected void onPostExecute(String result) {
            jsonResult.setText(result);
        }

        @Override
        // runs in background (not in UI thread)
        protected String doInBackground(String... params) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadData(params);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid. " + e.getMessage();
            }
        }
    } // AsyncTask DownloadFoodData()

	/*
	 * Given a URL, establishes an HttpUrlConnection and retrieves the web page
	 * content as a InputStream, which it returns as a string.
	 */

    private String downloadData(String... params) throws IOException {
        InputStream is = null;
        OutputStream out;
        String contentAsString = "";
        int response;
        URL url;
        HttpsURLConnection conn = null;

        byte[] bytes = params[1].getBytes("UTF-8");
        Integer bytesLeng = bytes.length;


        try {
            url = new URL(params[0]);
        } catch (MalformedURLException e) {
            Log.d(TAG, e.getMessage());
            return "ERROR call the developer: " + e.getMessage();
        }
        try {
            // create and open the connection
            conn = (HttpsURLConnection) url.openConnection();

            // output = true, uploading POST data
            // input = true, downloading response to POST
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            // conn.setFixedLengthStreamingMode(params[1].getBytes().length);
            // send body unknown length
            //					conn.setChunkedStreamingMode(0);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000 /* milliseconds */);

            conn.setRequestProperty("Content-Type",
                    "application/json; charset=UTF-8");
            // set length of POST data to send
            conn.addRequestProperty("Content-Length", bytesLeng.toString());

            //send the POST out
            out = new BufferedOutputStream(conn.getOutputStream());

            out.write(bytes);
            out.flush();
            out.close();

            // logCertsInfo(conn);

            // now get response
            response = conn.getResponseCode();

			/*	
			 *  check the status code HTTP_OK = 200 anything else we didn't get what
			 *  we want in the data.
			 */
            if (response != HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "Server returned: " + response + " aborting read.");
                return "Server returned: " + response + " aborting read.";
            }
            is = conn.getInputStream();
            contentAsString = readIt(is);
            return contentAsString;

        } finally {

            // Make sure that the Reader is closed after the app is finished using it.
            if (is != null)
                try {
                    is.close();
                } catch (IOException ignore) { /* ignore */ }
            //* Make sure the connection is closed after the app is finished using it.
            if (conn != null)
                try {

                    conn.disconnect();
                } catch (IllegalStateException ignore) { /* ignore  */ }

        }
    } // downLoadData()

    /*
    * Reads stream from HTTP connection and converts it to a String.
    *
    *  We use a BufferedInputStream to read the data from the http connection InputStream
    *  into our buffer  (NETIOBUFFER bytes at a time, I often choose 1KiB for
    *  probably small datastreams.)
    *
    *  We then use a ByteArrayOutputStream + DataOutputStream.writer to collect the data
    *  and write it to a byte array.
    *  The reason this is done is that we do not know the length of the incoming data
    *  So the OutputStream components allow us to put data into and grow the buffer.
    *
    *  It is unfortunate that in Java everything is a pointer but we do not have access
    *  to the underlying structures.  If this is done in c or c++ we allocate, manage,
    *  grow and release the buffers as we need them.
    *
    *  In java using these classes has that effect.  It is not necessarily efficient,
    *  a better solution may be needed for large data exchange.
    */
    public String readIt(InputStream stream) throws IOException {
        int bytesRead, totalRead = 0;
        byte[] buffer = new byte[NETIOBUFFER];

        // for data from the server
        BufferedInputStream bufferedInStream = new BufferedInputStream(stream);
        // to collect data in our output stream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream writer = new DataOutputStream(byteArrayOutputStream);

        // read the stream until end
        while ((bytesRead = bufferedInStream.read(buffer)) != -1) {
            writer.write(buffer);
            totalRead += bytesRead;
        }
        writer.flush();
        Log.d(TAG, "Bytes read: " + totalRead
                + "(-1 means end of reader so max of)");

        return new String(byteArrayOutputStream.toString());
    } // readIt()

	 /*
	 * Click Listener for the Show Certs button
	 * It will connect to the website then get the Certificate data to display and log
	 * This will crash, look at the log to see why, then fix it.
	 */

    public void showCerts(View v) {
        URL url = null;
        HttpsURLConnection conn = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.d(TAG, e.getMessage());
            jsonResult.setText("ERROR call the developer: " + e.getMessage());
            return;
        }
        if (url != null) {

            try {
                conn = (HttpsURLConnection) url.openConnection();
                jsonResult.setText(logCertsInfo(conn));
            } catch (IOException e) {
                Log.d(TAG, e.getMessage());
                jsonResult.setText("ERROR call the developer: " + e.getMessage());
            }
            if (conn != null)
                try {
                    //* Make sure the connection is closed after the app is finished using it.
                    conn.disconnect();
                } catch (IllegalStateException ignore) { /* ignore  */ }

        }
    } //showCerts

    private String logCertsInfo(HttpsURLConnection conn) {
        String line = "";
        if (conn == null)
            return "No connection";
        try {
            line += "Response Code : " + conn.getResponseCode() + "\n";
            Log.d(TAG, "Response Code : " + conn.getResponseCode());
            line += "Response Code : " + conn.getCipherSuite() + "\n";
            Log.d(TAG, "Cipher Suite : " + conn.getCipherSuite());
            line += "See log for certificates.";

            Certificate[] certs = conn.getServerCertificates();
            for (Certificate cert : certs) {
                Log.d(TAG, "Cert Type : " + cert.getType());
                Log.d(TAG, "Cert Hash Code : " + cert.hashCode());
                Log.d(TAG, "Cert Public Key Algorithm : "
                        + cert.getPublicKey().getAlgorithm());
                Log.d(TAG, "Cert Public Key Format : "
                        + cert.getPublicKey().getFormat());
            }
            Log.d(TAG, "End Certs");
        } catch (SSLPeerUnverifiedException e) {
            Log.d(TAG, e.getMessage());
            line += e.getMessage();
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            line += e.getMessage();
        }
        return line;
    } // logCertsInfo()

} // MainActivity class