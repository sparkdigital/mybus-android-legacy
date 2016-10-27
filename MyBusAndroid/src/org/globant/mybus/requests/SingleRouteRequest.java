package org.globant.mybus.requests;

import java.util.Map;

import org.globant.mybus.responses.RouteResponseSimple;

import android.util.Pair;

import com.globant.roboneck.requests.BaseNeckHttpRequest;
import com.globant.roboneck.requests.BaseNeckRequestException.Error;
import com.google.gson.Gson;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * REQUIRES A DEDICATED LISTENER IMPLEMENTING
 * PENDINGREQUESTLISTENER<RouteResponseSimple> ON THE FRAGMENT USING IT!
 * 
 * @author emiliano.desantis
 * 
 */
public class SingleRouteRequest extends
		BaseNeckHttpRequest<RouteResponseSimple, RouteResponseSimple>
{
	private String coreUrl = "http://www.mybus.com.ar/api/v1/SingleRoadApi.php";

	private String key = "94a08da1fecbb6e8b46990538c7b50b2";

	private int idLine, direction, stop1, stop2;
	
	//private long cacheExpirationTime = DurationInMillis.ALWAYS_RETURNED;

	public SingleRouteRequest(int idLine, int direction, int stop1, int stop2)
	{
		super(RouteResponseSimple.class);

		this.idLine = idLine;
		this.direction = direction;
		this.stop1 = stop1;
		this.stop2 = stop2;
	}

	public SingleRouteRequest()
	{
		super(RouteResponseSimple.class);
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
		//return cacheExpirationTime;
	}
	
	

	@Override
	protected String getBody()
	{
		/*
		 * idline=1 &direction=0 &stop1=24 &stop2=39
		 * &tk=94a08da1fecbb6e8b46990538c7b50b2
		 */
		return String.format("idline=%s" + "&direction=%s" + "&stop1=%s"
				+ "&stop2=%s" + "&tk=%s", idLine, direction, stop1, stop2, key);
	}

	@Override
	protected String getUrl()
	{
		return this.coreUrl;
	}

	@Override
	protected Method getMethod()
	{
		return Method.POST;
	}

	@Override
	protected RouteResponseSimple getRequestModel(RouteResponseSimple response)
	{
		return response;
	}

	@Override
	protected Map<String, String> getHeaders()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Pair<String, String>[] getQueryParameters()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isLogicError(RouteResponseSimple response)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Error processError(int httpStatus, RouteResponseSimple response,
			String responseBody)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected RouteResponseSimple processContent(String responseBody)
	{
		RouteResponseSimple gson = new Gson().fromJson(responseBody,
				RouteResponseSimple.class);

		return gson;
	}
}
