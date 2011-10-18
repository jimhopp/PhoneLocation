package org.jimhopp.android;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.EditText;


public class PhoneLocationActivity extends Activity {
	
	//CurrentLocationModel model = new CurrentLocationModel();
	//CurrentLocationView view;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //view = new CurrentLocationView(this, model);
        //model.start();
        
        final EditText lat = (EditText)findViewById(R.id.latitude);
        final EditText lon = (EditText)findViewById(R.id.longitude);
        
        LocationListener location = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				lat.setText(String.valueOf(location.getLatitude()));
				lon.setText(String.valueOf(location.getLongitude()));
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
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
        	
        };
        
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location lastKnown = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lat.setText(String.valueOf(lastKnown.getLatitude()));
        lon.setText(String.valueOf(lastKnown.getLongitude()));
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000l, Float.valueOf("50.0"), location);
    }
}