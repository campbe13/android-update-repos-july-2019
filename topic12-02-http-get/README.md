# topic12-net i/o http GET
Sample code that opens an HTTP connection (socket) to a 
web page, then retrieves the content which
is displayed as text in the UI, normally this would be
displayed in  a WebView

Uses 
* ConnectivityManager and NetworkInfo
* AsyncTask 
* HttpURLConnection for get
* BufferedInputStream
* ByteArrayOutputStream
* DataOutputStream
## app HttpUrlConnection
Note: input URL cannot be https:

Use of logcat to watch the logging
```
adb logcat -s HttpURLConn
```
