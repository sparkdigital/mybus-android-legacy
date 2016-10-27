package org.globant.mybus.requests;

import java.util.Map;

import org.globant.mybus.Constants;
import org.globant.mybus.responses.StationResponse;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Pair;

import com.globant.roboneck.requests.BaseNeckHttpRequest;
import com.globant.roboneck.requests.BaseNeckRequestException.Error;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.octo.android.robospice.persistence.DurationInMillis;

public class StationsRequest extends
		BaseNeckHttpRequest<StationResponse, StationResponse>
{
	
	private static final String PATH = "RechargeCardPointApi.php";
	
	private double lat, lng;

	public StationsRequest(LatLng loc)
	{
		super(StationResponse.class);
		
		this.lat = loc.latitude;
		this.lng = loc.longitude;
	}
	
	@Override
	public Object getCachekey()
	{
		return getUrl();
	}

	@Override
	public long getCacheExpirationTime()
	{
//		return DurationInMillis.ALWAYS_EXPIRED;
		return DurationInMillis.ALWAYS_RETURNED;
	}

	@Override
	protected String getBody()
	{
		return String.format("lat=%s" +
				"&lng=%s" +
				"&ra=5" +	// This parameter is disabled!
				"&tk=%s", lat, lng, Constants.MYBUS_KEY);
	}

	@Override
	protected Map<String, String> getHeaders()
	{
		return null;
	}

	@Override
	protected String getUrl()
	{
		return Constants.DOMAIN.concat(PATH);
	}

	@Override
	protected com.globant.roboneck.requests.BaseNeckHttpRequest.Method getMethod()
	{
		return Method.POST;
	}

	@Override
	protected Pair<String, String>[] getQueryParameters()
	{
		return null;
	}

	@Override
	protected StationResponse getRequestModel(StationResponse response)
	{
		return response;
	}

	@Override
	protected boolean isLogicError(StationResponse response)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Error processError(int httpStatus, StationResponse response,
			String responseBody)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected StationResponse processContent(String responseBody)
	{
		@SuppressWarnings("unused")
		JSONObject jsonObject = new JSONObject();
		
		StationResponse gson = new StationResponse();

		try
		{
			jsonObject = new JSONObject(responseBody);
			
			gson = new Gson().fromJson(responseBody, StationResponse.class);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		return gson;
	}
}