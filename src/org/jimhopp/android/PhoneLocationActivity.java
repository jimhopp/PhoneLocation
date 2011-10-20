package org.jimhopp.android;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.widget.EditText;
import android.widget.TextView;


public class PhoneLocationActivity extends Activity {
	
	TextView latG, latN,
	         lonG, lonN,
	         altG, altN,
	         accuracyG, accuracyN,
	         statusG, statusN,
	         timeG, timeN,
	         speedG, speedN,
	         providerG, providerN,
	         distance;
	
	Location lastKnownGPS, lastKnownNetwork;
	
	final String NO_LOC_DATA = "no location data";

	
	void updateGPS(Location loc) {
	    lastKnownGPS = loc;
		latG.setText(String.valueOf(loc.getLatitude()));
        lonG.setText(String.valueOf(loc.getLongitude()));
        altG.setText(loc.hasAltitude() ? String.valueOf(loc.getAltitude()) : "n/a");
        accuracyG.setText(String.valueOf(loc.getAccuracy()));
        speedG.setText(loc.hasSpeed() ? String.valueOf(loc.getSpeed()) : "n/a");
        providerG.setText(loc.getProvider());
        Time tm = new Time();
    	tm.set(loc.getTime());
	   	Time now = new Time();
	   	now.setToNow();
	   	long delta = now.normalize(true) - tm.normalize(true);
        timeG.setText(tm.format("%H:%M:%S") + " / " + 
    			DateUtils.formatElapsedTime(delta) + " / " + String.valueOf(delta));
    	updateDistance();
	}
	
	void updateGPSUnknown() {
        latG.setText(NO_LOC_DATA);
        lonG.setText(NO_LOC_DATA);
        altG.setText(NO_LOC_DATA);
        accuracyG.setText(NO_LOC_DATA);
        speedG.setText(NO_LOC_DATA);
        providerG.setText(NO_LOC_DATA);
        timeG.setText(NO_LOC_DATA);
        }
	
	void updateNetwork(Location loc) {
        lastKnownNetwork = loc;
		latN.setText(String.valueOf(loc.getLatitude()));
        lonN.setText(String.valueOf(loc.getLongitude()));
        altN.setText(loc.hasAltitude() ? String.valueOf(loc.getAltitude()) : "n/a");
        accuracyN.setText(String.valueOf(loc.getAccuracy()));
        speedN.setText(loc.hasSpeed() ? String.valueOf(loc.getSpeed()) : "n/a");
        providerN.setText(loc.getProvider());
        Time tm = new Time();
    	tm.set(loc.getTime());
    	Time now = new Time();
    	now.setToNow();
    	long delta = now.normalize(true) - tm.normalize(true);
    	timeN.setText(tm.format("%H:%M:%S") + " / " +
    			DateUtils.formatElapsedTime(delta) + " / " + String.valueOf(delta));
    	updateDistance();
	}
	
	void updateNetworkUnknown() {
        latN.setText(NO_LOC_DATA);
        lonN.setText(NO_LOC_DATA);
        altN.setText(NO_LOC_DATA);
        accuracyN.setText(NO_LOC_DATA);
        speedN.setText(NO_LOC_DATA);
        providerN.setText(NO_LOC_DATA);
        timeN.setText(NO_LOC_DATA);
        }

	void updateDistance() {
    	if (lastKnownGPS != null && lastKnownNetwork != null)
    		distance.setText(String.valueOf(lastKnownGPS.distanceTo(lastKnownNetwork)));
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        latG = (TextView)findViewById(R.id.latitude);
        lonG = (TextView)findViewById(R.id.longitude);
        altG = (TextView)findViewById(R.id.altitude);
        accuracyG = (TextView)findViewById(R.id.accuracy);
        statusG = (TextView)findViewById(R.id.status);
        timeG = (TextView)findViewById(R.id.time);
        speedG = (TextView)findViewById(R.id.speed);
        providerG = (TextView)findViewById(R.id.provider);
        
        latN = (TextView)findViewById(R.id.latitudeN);
        lonN = (TextView)findViewById(R.id.longitudeN);
        altN = (TextView)findViewById(R.id.altitudeN);
        accuracyN = (TextView)findViewById(R.id.accuracyN);
        statusN = (TextView)findViewById(R.id.statusN);
        timeN = (TextView)findViewById(R.id.timeN);
        speedN = (TextView)findViewById(R.id.speedN);
        providerN = (TextView)findViewById(R.id.providerN);
        distance = (TextView)findViewById(R.id.distance);
       
        LocationListener locationGPS = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) { updateGPS(location); }
			@Override
			public void onProviderDisabled(String providerName) { 
				statusG.setText("Provider "+ providerName + " disabled");
			}
			@Override
			public void onProviderEnabled(String providerName) {
				statusG.setText("Provider "+ providerName + " enabled");
			}
			@Override
			public void onStatusChanged(String providerName, int providerStatus,
					Bundle extras) {
				statusG.setText("Provider "+ providerName + " has status " + providerStatus);
			}
        };

        LocationListener locationNetwork = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) { updateNetwork(location); }
			@Override
			public void onProviderDisabled(String providerName) { 
				statusN.setText("Provider "+ providerName + " disabled");
			}
			@Override
			public void onProviderEnabled(String providerName) {
				statusN.setText("Provider "+ providerName + " enabled");
			}
			@Override
			public void onStatusChanged(String providerName, int providerStatus,
					Bundle extras) {
				statusN.setText("Provider "+ providerName + " has status " + providerStatus);
			}
        };
        
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        
        lastKnownGPS = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (lastKnownGPS != null) { updateGPS(lastKnownGPS); }
        else { updateGPSUnknown(); }
       
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000l, Float.valueOf("50.0"),
        	locationGPS);
        
        lastKnownNetwork = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (lastKnownNetwork != null) { updateNetwork(lastKnownNetwork); }
        else { updateNetworkUnknown(); }
       
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000l, Float.valueOf("50.0"),
        	locationNetwork);
    }
}