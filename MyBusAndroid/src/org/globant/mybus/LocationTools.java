package org.globant.mybus;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;

public class LocationTools {

	public static LatLng CurrentLocation(Activity act) { // Return the user's current location
		
		LatLng point_loc = null;
		LocationManager mLocationManager = (LocationManager)act.getSystemService(Context.LOCATION_SERVICE);
		List<String> matchingProviders = mLocationManager.getProviders(true);
		for(String provider : matchingProviders )
		{
			Location location = mLocationManager.getLastKnownLocation(provider);
			if(location != null){
				point_loc = new LatLng(location.getLatitude(), location.getLongitude());
			}
		}
		return point_loc;
	}
	
}
