package com.example.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StatusActivity extends Activity implements OnClickListener, LocationListener {

	private static final String TAG = "StatusActivity";
	Button buttonUp;
	EditText editUp;
	LocationManager locationManager;
	Location location;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        
        buttonUp = (Button)findViewById(R.id.buttonUpdate);
        editUp = (EditText)findViewById(R.id.editTextUpdate);
        
        buttonUp.setOnClickListener(this);
        
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }


	@Override
	public void onClick(View v) {
		
		final String strUpdate = editUp.getText().toString();
		
		new PostToTwitter().execute(strUpdate);
	}
    
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 1000, this);
	}

	class PostToTwitter extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... args) {
			String result;
			try {
				Twitter tw = YambaApp.getTwitter();
				tw.setStatus(args[0]);
				result = "Success";
				Log.d(TAG, result);
			} catch( TwitterException e ) {
				result = "Died";
				Log.d(TAG, result, e);
				result = "Failed post";
			}
			return result;
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
	}

	// -- Location Listener callbacks --
	
	@Override
	public void onLocationChanged(Location arg0) {
		
		location = arg0;
		Log.d(TAG, "onLocationChanged " + location.toString());
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
