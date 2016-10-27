package org.globant.mybus.activities;

import com.google.android.gms.maps.model.LatLng;

public interface Locator
{
	public void cacheLocation();
	public LatLng getCachedLocation();
	public LatLng getCurrentLocation();
}
