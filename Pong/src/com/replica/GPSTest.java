package com.replica;

import android.app.ListActivity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

public class GPSTest extends ListActivity {
	
	private LocationManager locationManager;
	private String bestProvider;
	private Location location;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Get the location manager
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		bestProvider = locationManager.getBestProvider(criteria, false);
		location = locationManager.getLastKnownLocation(bestProvider);
	}

	public Location returnLocation() {
		return (location);
	}
}
