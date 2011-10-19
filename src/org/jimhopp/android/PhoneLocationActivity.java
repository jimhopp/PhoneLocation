package org.jimhopp.android;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.EditText;
import android.widget.TextView;


public class PhoneLocationActivity extends Activity {
	
	TextView lat;
	TextView lon;
	TextView alt;
	TextView accuracy;
	TextView status;
	TextView time;
	TextView speed;
	TextView provider;
	
	final String NO_LOC_DATA = "no location data";

	
	void updateView(Location loc) {
		lat.setText(String.valueOf(loc.getLatitude()));
        lon.setText(String.valueOf(loc.getLongitude()));
        alt.setText(String.valueOf(loc.getAltitude()));
        accuracy.setText(String.valueOf(loc.getAccuracy()));
        speed.setText(String.valueOf(loc.getSpeed()));
        provider.setText(loc.getProvider());
        Time tm = new Time();
    	tm.set(loc.getTime());
    	time.setText(tm.format2445());
	}
	
	void updateViewUnknown() {
        lat.setText(NO_LOC_DATA);
        lon.setText(NO_LOC_DATA);
        alt.setText(NO_LOC_DATA);
        accuracy.setText(NO_LOC_DATA);
        speed.setText(NO_LOC_DATA);
        provider.setText(NO_LOC_DATA);
        time.setText(NO_LOC_DATA);
        }
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        lat = (TextView)findViewById(R.id.latitude);
        lon = (TextView)findViewById(R.id.longitude);
        alt = (TextView)findViewById(R.id.altitude);
        accuracy = (TextView)findViewById(R.id.accuracy);
        status = (TextView)findViewById(R.id.status);
        time = (TextView)findViewById(R.id.time);
        speed = (TextView)findViewById(R.id.speed);
        provider = (TextView)findViewById(R.id.provider);


       
        LocationListener location = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				updateView(location);
			}

			@Override
			public void onProviderDisabled(String providerName) {
				status.setText("Provider "+ providerName + " disabled");
			}

			@Override
			public void onProviderEnabled(String providerName) {
				status.setText("Provider "+ providerName + " enabled");
			}

			@Override
			public void onStatusChanged(String providerName, int providerStatus,
					Bundle extras) {
				status.setText("Provider "+ providerName + " has status " + providerStatus);
			}
        	
        };
        
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location lastKnown = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (lastKnown != null) { updateView(lastKnown); }
        else { updateViewUnknown(); }
       
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000l, Float.valueOf("50.0"), location);
    }
}