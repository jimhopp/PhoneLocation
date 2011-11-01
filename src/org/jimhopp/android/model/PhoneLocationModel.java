package org.jimhopp.android.model;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class PhoneLocationModel {
	//maintains time and current location
	//has callbacks for new fixes, time update
	//calculates age of fixes
	//notifies view to update fields?
	
	public Location lastLocGPS;
	public Location lastLocNetwork;
	
	
	public PhoneLocationModel(LocationManager lm) {
		
		LocationListener locationGPS = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) { updateGPS(location); }
			@Override
			public void onProviderDisabled(String providerName) { }
			@Override
			public void onProviderEnabled(String providerName) { }
			@Override
			public void onStatusChanged(String providerName, int providerStatus,
					Bundle extras) { }
        };
        updateGPS(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER));

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000l, Float.valueOf("10.0"),
        	locationGPS);
        
		LocationListener locationNetwork = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) { updateNetwork(location); }
			@Override
			public void onProviderDisabled(String providerName) { }
			@Override
			public void onProviderEnabled(String providerName) { }
			@Override
			public void onStatusChanged(String providerName, int providerStatus,
					Bundle extras) { }
        };
        updateNetwork(lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000l, Float.valueOf("10.0"),
        	locationNetwork);
	}
	
	public Location getGPSLocation() { return lastLocGPS; }
	
	public Location getNetworkLocation() { return lastLocNetwork; }
	void updateGPS(Location loc) {
		lastLocGPS = loc;
	}
	
	void updateNetwork(Location loc) {
		lastLocNetwork = loc;
	}
}
