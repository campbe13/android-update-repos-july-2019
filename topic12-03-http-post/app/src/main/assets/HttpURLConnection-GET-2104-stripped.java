// before this I check my  connection & decide which I will use (wifi, mobile, bluetooth... )

private String downloadUrl(String myurl) throws IOException {
		InputStream is = null;
		
		int response, len = 500;
		HttpURLConnection conn = null;

		URL url = new URL(myurl);
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.connect();
			response = conn.getResponseCode();
			if (response != HttpURLConnection.HTTP_OK) {
				Log.d(TAG, "Server returned: " 	+ response + " aborting read.");
				return "Server returned: " + response + " aborting read.";
			}
			is = conn.getInputStream();
			String contentAsString = readIt(is, len);
			return contentAsString;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignore) { /* ignore */	}
				if (conn != null)
					try {
						conn.disconnect();
					} catch (IllegalStateException ignore ) { /* ignore */	}
			}
		}
	} // downloadUrl()
