package ca.campbell.httpexample;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

/*
 * HttpURLConnectionExample
 * 
 * This app sends a URL uses HttpURLConnection class + GET
 * to download a web page and display some of it in a TextView
 * 
 */
public class HttpExample extends Activity {
	private static final int NETIOBUFFER = 1024;

	private static final String TAG = "HttpURLConn";
	private EditText urlText;
	private TextView textView;
    private ScrollView scrollv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http_example);

		urlText = (EditText) findViewById(R.id.myURL);
		textView = (TextView) findViewById(R.id.tv);
		scrollv = (ScrollView) findViewById(R.id.scrollv);
		scrollv.setVisibility(View.GONE);
	}

	/*
	 * When user clicks button, executes an AsyncTask to do the download. Before
	 * attempting to fetch the URL, makes sure that there is a network
	 * connection.
	 */
	public void myClickHandler(View view) {
		// Gets the URL from the UI's text field.
		String stringUrl = urlText.getText().toString();
		final String HTTP = "http://", HTTPS="https://";
		int leng= HTTP.length()-1;
		scrollv.setVisibility(View.VISIBLE);
		if (stringUrl.isEmpty()) {
			textView.setText("Gimmey some URLz pleaze.");

		} else {
			// HTTPURLConnection will not redirect http->https https->http
			// so check, swap or add http
			if (stringUrl.substring(0, HTTPS.length()-1) != HTTPS )
				if (stringUrl.substring(0, leng) != HTTP)
					stringUrl = "https://" + stringUrl;
				else
					// swap https for http
					stringUrl = "https://" + stringUrl.substring(leng, stringUrl.length()-1  );
			Log.d(TAG, "url"+stringUrl);
			// first check to see if we can get on the network
			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				// invoke the AsyncTask to do the dirtywork.
				new DownloadWebpageText().execute(stringUrl);
				// text is set in DownloadWebpageText().onPostExecute()
			} else {
				textView.setText("No network connection available.");
			}
		}
	} // myClickHandler()

	/*
	 * Uses AsyncTask to create a task away from the main UI thread. This task
	 * takes a URL string and uses it to create an HttpUrlConnection. Once the
	 * connection has been established, the AsyncTask downloads the contents of
	 * the webpage via an an InputStream. The InputStream is converted into a
	 * string, which is displayed in the UI by the AsyncTask's onPostExecute
	 * method.
	 */

	private class DownloadWebpageText extends AsyncTask<String, Void, String> {

		// onPostExecute displays the results of the AsyncTask.
		// runs in calling thread (in UI thread)
		// we don't give the buffer to the UI thread until we have the whole thing
		// previous examples used a global variable for string buffer
		// so it was available as it filled.
		//  here the data is not available until it's done reading
		protected void onPostExecute(String result) {
			textView.setText(result);
		}

		@Override
		// runs in background (not in UI thread)
		protected String doInBackground(String... urls) {
			// params comes from the execute() call: params[0] is the url.
			try {
				return downloadUrl(urls[0]);
			} catch (IOException e) {
				Log.e(TAG, "exception thrown by download" );
				return "Unable to retrieve web page. URL may be invalid.";
			}
		}
	} // AsyncTask DownloadWebpageText()

	/*
	 * Given a URL, establishes an HttpUrlConnection and retrieves the web page
	 * content as a InputStream, which it returns as a string.
	 */

	private String downloadUrl(String myurl) throws IOException {
		InputStream is = null;
		// Only read the first 500 characters of the retrieved
		// web page content.
		// int len = MAXBYTES;
		HttpURLConnection conn = null;
		URL url = new URL(myurl);
		try {
			// create and open the connection
			conn = (HttpURLConnection) url.openConnection();

			/*
			 * set maximum time to wait for stream read read fails with
			 * SocketTimeoutException if elapses before connection established
			 * 
			 * in milliseconds
			 * 
			 * default: 0 - timeout disabled
			 */
			conn.setReadTimeout(10000);
			/*
			 * set maximum time to wait while connecting connect fails with
			 * SocketTimeoutException if elapses before connection established
			 * 
			 * in milliseconds
			 * 
			 * default: 0 - forces blocking connect timeout still occurs but
			 * VERY LONG wait ~several minutes
			 */
			conn.setConnectTimeout(15000 /* milliseconds */);
			/*
			 * HTTP Request method defined by protocol
			 * GET/POST/HEAD/POST/PUT/DELETE/TRACE/CONNECT
			 * 
			 * default: GET
			 */
			conn.setRequestMethod("GET");
			// specifies whether this connection allows receiving data
			conn.setDoInput(true);
			// Starts the query
			conn.connect();

			int response = conn.getResponseCode();
			Log.d(TAG, "Server returned: " + response + " aborting read.");

			/*
			 *  check the status code HTTP_OK = 200 anything else we didn't get what
			 *  we want in the data.
			 */
			if (response != HttpURLConnection.HTTP_OK)
				return "Server returned: " + response + " aborting read.";

			// get the stream for the data from the website
			is = conn.getInputStream();
			// read the stream, returns String
			return readIt(is);
		} catch (IOException e) {
			Log.e(TAG, "IO exception in bg" );
			Log.getStackTraceString(e);
			throw e;
		} finally {
			/*
			 * Make sure that the InputStream is closed after the app is
			 * finished using it.
			 * Make sure the connection is closed after the app is finished using it.
			 */

			if (is != null) {
				try {
					is.close();
				} catch (IOException ignore) {
				}
				if (conn != null)
					try {
						conn.disconnect();
					} catch (IllegalStateException ignore ) {
					}
			}
		}
	} // downloadUrl()

	//
	/*
	 * Reads stream from HTTP connection and converts it to a String.
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
	public String readIt(InputStream stream) throws IOException,
			UnsupportedEncodingException {
			int bytesRead, totalRead=0;
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

} // Activity class