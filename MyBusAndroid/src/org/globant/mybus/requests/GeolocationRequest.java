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

public class GeolocationRequest extends BaseNeckHttpRequest<LatLng, LatLng>
{

	private String targetAddress;

	public GeolocationRequest(String parameter)
	{
		super(LatLng.class);

		this.targetAddress = parameter + ", " + Constants.ACTUAL_CITY;
	}

	@Override
	public Object getCachekey()
	{
		return getUrl();
	}

	@Override
	public long getCacheExpirationTime()
	{
		return DurationInMillis.ALWAYS_EXPIRED;
	}

	@Override
	protected String getBody()
	{
		// "Suppressed on Iñaki's orders, sir."

		// String t = targetAddress.replaceAll(" ", "%20");
		//
		// String url = Constants.GMAPS_GEOCODE_API + t + "&sensor=false";
		//
		// return url;
		return null;
	}

	@Override
	protected Map<String, String> getHeaders()
	{
		return null;
	}

	@Override
	protected String getUrl()
	{
		// Original code

		// return Constants.GMAPS_GEOCODE_API // + "?" Mistakenly added?
		// + targetAddress.replaceAll(" ", "%20") + "&sensor=false";

		// First incremental fix
		// return String.format(Constants.GMAPS_GEOCODE_API,
		// targetAddress.replaceAll(" ", "%20"));

		// Second incremental fix (discarded in favor of using getQueryParameters() )
		// try
		// {
		// return String.format(Constants.GMAPS_GEOCODE_API,
		// URLEncoder.encode(targetAddress, "utf-8")); //
		// encode(targetAddress));
		// }
		// catch (UnsupportedEncodingException e)
		// {
		// e.printStackTrace();
		//
		// return null;
		// }

		return Constants.GMAPS_GEOCODE_API;
	}

	@Override
	protected Method getMethod()
	{
		return Method.GET;
	}

	@Override
	protected Pair<String, String>[] getQueryParameters()
	{
		@SuppressWarnings("unchecked")
		Pair<String, String> pairs[] = new Pair[2];

		pairs[0] = new Pair<String, String>("address", targetAddress);
		pairs[1] = new Pair<String, String>("sensor", "false");

		return pairs;
	}

	@Override
	protected LatLng getRequestModel(LatLng response)
	{
		return response;
	}

	@Override
	protected boolean isLogicError(LatLng response)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Error processError(int httpStatus, LatLng response,
			String responseBody)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected LatLng processContent(String responseBody)
	{
		try
		{
			JSONObject jsonObject = new JSONObject(responseBody);

			double longitude = ((JSONArray) jsonObject.get("results"))
					.getJSONObject(0).getJSONObject("geometry")
					.getJSONObject("location").getDouble("lng");

			double latitude = ((JSONArray) jsonObject.get("results"))
					.getJSONObject(0).getJSONObject("geometry")
					.getJSONObject("location").getDouble("lat");

			return new LatLng(latitude, longitude);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}