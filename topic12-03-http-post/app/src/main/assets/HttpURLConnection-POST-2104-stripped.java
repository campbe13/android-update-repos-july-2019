// before this I check my  connection & decide which I will use (wifi, mobile, bluetooth... )

	private String downloadData(String... params) throws IOException {
		InputStream is = null;
		OutputStream out;
		String contentAsString ="";
		int response;
		URL url;
		HttpsURLConnection conn = null;
		
		try {			
			url =  new URL(params[0]);
		} catch (MalformedURLException e) {
			Log.d(TAG, e.getMessage());
			return "ERROR call the developer: " + e.getMessage();
		}
		
		byte[] bytes = params[1].getBytes("UTF-8");
		Integer bytesLeng = bytes.length;
		
		try {
			conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestProperty("Content-Type", 
					"application/json; charset=UTF-8");
			conn.addRequestProperty("Content-Length", bytesLeng.toString());

			out = new BufferedOutputStream(conn.getOutputStream());
			out.write(bytes);
			out.flush();
			out.close();

			response = conn.getResponseCode();
			if (response != HttpURLConnection.HTTP_OK)
				return "Server returned: " + response + " aborting read.";
			is = conn.getInputStream();
			contentAsString = readIt(is);
			return contentAsString;
		} finally {
			if (is != null) 
				try {
					is.close();  
				} catch (IOException ignore) { /* ignore */	}
			if (conn != null)
				try {
					conn.disconnect();
				} catch (IllegalStateException ignore ) { /* ignore  */ }
		}
	}
	