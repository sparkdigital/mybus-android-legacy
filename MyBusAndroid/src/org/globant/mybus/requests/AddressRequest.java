package org.globant.mybus.requests;

import java.util.Map;

import org.globant.mybus.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Pair;

import com.globant.roboneck.requests.BaseNeckHttpRequest;
import com.globant.roboneck.requests.BaseNeckRequestException.Error;
import com.google.android.gms.maps.model.LatLng;
import com.octo.android.robospice.persistence.DurationInMillis;

//URL: http://maps.googleapis.com/maps/api/geocode/json?latlng="+ lat + "," + lon + &sensor=true"

public class AddressRequest extends BaseNeckHttpRequest<String, String>{

	private String lat,lon;
	
	public AddressRequest(LatLng point) {
		super(String.class);
		lat = String.valueOf(point.latitude);
		lon = String.valueOf(point.longitude);
	}

	@Override
	public Object getCachekey() {
		return getUrl();
	}

	@Override
	public long getCacheExpirationTime() {
		return DurationInMillis.ALWAYS_EXPIRED;
	}

	@Override
	protected String getBody() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<String, String> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getUrl() {
		return Constants.GMAPS_GEOCODE_API + "?"
				+ "latlng=" + lat + "," + lon + "&"
				+ "sensor=true";
	}

	@Override
	protected com.globant.roboneck.requests.BaseNeckHttpRequest.Method getMethod() {
		return Method.GET;
	}

	@Override
	protected String processContent(String responseBody) {
		String address = "";
		try
		{
			JSONObject jsonObject = new JSONObject(responseBody);
			
			String streetNumber = ((JSONArray)jsonObject.get("results")).
								getJSONObject(0).getString("formatted_address");
								//.getJSONObject(1).getString("short_name");
			
			String[] part1 = streetNumber.split(",");
			String[] parts2 = part1[0].split("-");  //its woooorrrk!!! Martín Miguel de Güemes 2701-2799, Mar del Plata, Buenos Aires Province, Argentina
		    address = parts2[0];

			return address;
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return address;
	}

	@Override
	protected boolean isLogicError(String response) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getRequestModel(String response) {
		return response;
	}

	@Override
	protected Error processError(int httpStatus, String response,
			String responseBody) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Pair<String, String>[] getQueryParameters() {
//		@SuppressWarnings("unchecked")
//		Pair<String, String> pairs[] = new Pair[2];
//
//		pairs[0] = new Pair<String, String>("latlng", lat+","+lon);
//		pairs[1] = new Pair<String, String>("sensor", "true");
//
//		return pairs;
		return null;
	}

}
