# topic12-net net i/o HTTP GET
Sample code that opens an HTTP connection (socket) to a 
a website api over TLS so using HTTPS 
It needs an api key, an api appid POST data is constructed
for the request, uses <http://www.edamam.com> api 

n.b. HTTP POST is more secure as you do not expose data in the URL
as you do with HTTP GET
The app retrieves the JSON response  which
is displayed as text in the UI, normally this would be
parsed and used otherwise in the UI.

Uses 
* ConnectivityManager and NetworkInfo
* AsyncTask 
* URLEncoder
* HttpURLConnection for POST
* BufferedInputStream
* ByteArrayOutputStream
* DataOutputStream

## app HttpUrlConnection
Use of logcat to watch the logging
```
adb logcat -s HttpURLPOST
```
