package ca.campbell.networkcheckstatus;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/*
 * This app is intended to be an example of checking network connectivity.
 * It does not do any network access, though this is the normal next step.
 * 
 * When using networking it is important to verify that you have an active
 * connected network connection before doing any network activity.
 * 
 * Since it checks connectivity but does no network access it does not need
 * to be done in an AsyncTask.  Any network access (data over the network)
 *  must be done in a background thread.
 *
 * Ref
 * https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
 *
 * Permissions required in AndroidMainifest.xml:
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE">
 * </uses-permission>
 */
public class MainActivity extends Activity {
	private final static String TAG = "NETCHK";
	private TextView tv2, tv3;
	private boolean networkIsUp = false;
	NetworkInfo networkInfo;
	ConnectivityManager connMgr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
	}

	public void checkStatusButton(View view) {
		checkStatus();
	}

	/*
	 * checkStatus() using an instance of ConnectivityManager via the
	 * Connectivity Service which is an Android Service. Checks if network is
	 * live, sets networkIsUp depending on network state.
	 * 
	 * Connectivity Manager Class
	 * 
	 * gives access to the state of network connectivity. Can be used to notify
	 * apps when connectivity changes
	 * 
	 * -monitor network connections (wi-fi, GPRS, UMTs, etc
	 * 
	 * -send broadcast intents when connectivity changes
	 * 
	 * -trys to failover to another net when connectivity lost
	 * 
	 * -api for network state connectivity
	 * 
	 * ConnectivityManager.getActiveNetworkInfo()
	 * 
	 * returns details about the current active default network (NetworkInfo)
	 * null if no default network ALWAYS check isConnected() before initiating
	 * network traffic
	 * 
	 * requires perm: android.permission.ACCESS_NETWORK_STATE
	 * 
	 * NetworkInfo class
	 * 
	 * gives access information about the status of a network interface
	 * connection
	 */
	public void checkStatus() {
		connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// getActiveNetworkInfo() each time as the network may swap as the
		// device moves
		if (connMgr != null ) {
			networkInfo = connMgr.getActiveNetworkInfo();
			// ALWAYS check isConnected() before initiating network traffic
			if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
				tv2.setText("Network is connected or connecting");
				networkIsUp = true;
			} else {
				tv2.setText("No network connectivity");
				networkIsUp = false;
			}
		} else {
			tv2.setText("No network manager service");
			networkIsUp = false;
		}
	} // checkStatus()

	public void checkEverything(View view) {
		String message = null;
		State state = null;
		checkStatus();
		if (networkIsUp) {
			// deprecated in API 28  (released 2018-08-06)
			// TODO in future use instead ConnectivityManager.NetworkCallback API
			state = networkInfo.getState();
		}
		if (state == null) {
			tv2.setText("Unable to get state info");
		} else {
			switch (networkInfo.getState()) {
			case CONNECTED:
				message = "Connected";
				break;
			case CONNECTING:
				message = "Connecting";
				break;
			case DISCONNECTED:
				message = "Disconnected";
				break;
			case DISCONNECTING:
				message = "Disconnecting";
				break;
			case SUSPENDED:
				message = "Suspened";
				break;
			case UNKNOWN:
				message = "Unknown";
				break;
			default:
				message = "No valid State found";
				break;
			}

			tv2.setText("State: "+message);
		}

	} // checkEverything()

	public void checkEverythingDetailed(View view) {
		String message = null;
		State state = null;
		checkStatus();
		if (networkIsUp) {
			state = networkInfo.getState();
		}
		if (state == null) {
			tv2.setText("Unable to get state info");
		} else {
			switch (networkInfo.getDetailedState()) {
			case IDLE:
				message = "Idle";
				break;
			case CONNECTED:
				message = "Connected";
				break;
			case CONNECTING:
				message = "Connecting";
				break;
			case SCANNING:
				message = "Scanning: searching for an access point";
				break;
			case DISCONNECTED:
				message = "Disconnected";
				break;
			case DISCONNECTING:
				message = "Connected";
				break;
			case AUTHENTICATING:
				message = "Authenticating";
				break;
			case BLOCKED:
				message = "Block";
				break;
			case OBTAINING_IPADDR:
				message = "awaiting DHCP response";
				break;
			case FAILED:
				message = "Failed";
				break;
			case VERIFYING_POOR_LINK:
				message = "Link has poor connectivity";
				break;
			default:
				message = "No valid State found";
				break;
			}
			// Ex types WIFI, Bluetooth, Mobile...
			String typeName = networkInfo.getTypeName();
			message = message + "\n Network Type " + typeName;
			if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				// subtype applies only to type mobile
				// see TelephonyManager class for enum of types
				String subtypeName = networkInfo.getSubtypeName();
				message = message + "\n Network Subtype: " + subtypeName;
			}
			String extraInfo = networkInfo.getExtraInfo();
			message = message + "\n Extra Info " + extraInfo;
			tv2.setText("Detailed State: \n " + message);
		}
		tv3.setText("Available \n " + getAvailablilty());
	} // checkEverythingDetailed()

	/*
	*   More types found here
	*   https://developer.android.com/reference/android/net/ConnectivityManager.html
	*/
	private String getAvailablilty() {
		boolean wifiConn, mobileConn, genNetConn;
		NetworkInfo netInfo;
		String msg;
		// get an instance of ConnectivityManager
		connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		// check if wifi is available
		// allows connection to WLAN
		// uses radio frequency bands
		// IEEE 802.11(a and a variants, b, g, n)
		netInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (netInfo != null) {
			wifiConn = netInfo.isAvailable();
		} else {
			wifiConn = false;
		}
		Log.d(TAG, "Wifi available: " + wifiConn);
		msg = "Wifi available: " + wifiConn;
		// check if mobile is available
		// mobile telephony
		// mobile phones connect to a cellular network of base stations which are
		// then connected to the PSTN  (public switched telephone network)
		// Comm between cell site and phone include GSM CDMA AND TDMA
		netInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (netInfo != null) {
			mobileConn = netInfo.isAvailable();
		} else {
			mobileConn = false;
		}
		msg += "\n Mobile available: " + mobileConn;
		Log.d(TAG, "Mobile available: " + mobileConn);
		// check if bluetooth is available
		// named after Harold Bluetooth Danish king 10th century
		// IEEE 802.15.1
		// short distance wireless comm, developed by Ericcson (~1994)
		netInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);
		if (netInfo != null) {
			genNetConn = netInfo.isAvailable();
		} else {
			genNetConn = false;
		}
		msg += "\n BlueTooth available: " + genNetConn;
		Log.d(TAG, "BlueTooth available: " + genNetConn);
		// check if WiMAX is available
		// Worldwide Interoperability for Microwave Accesss
		// IEEE 802.16
		// provides multiple physical and media access control options
		netInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
		if (netInfo != null) {
			genNetConn = netInfo.isAvailable();
		} else {
			genNetConn = false;
		}
		msg += "\n WiMax available: " + genNetConn;
		Log.d(TAG, "WiMax available: " + genNetConn);
		// check if Ethernet is available
		// Physically wired network using Cat5/6 cables with RJ45 connectors
		// unlikely on a mobile device
		// IEEE 802.3
		// Ethernet developed at Xerox PARC (~1973)
		netInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
		if (netInfo != null) {
			genNetConn = netInfo.isAvailable();
		} else {
			genNetConn = false;
		}
		msg += "\n Ethernet available: " + genNetConn;
		Log.d(TAG, "Ethernet available: " + genNetConn);
		return(msg);
	}

} // MainActivity
