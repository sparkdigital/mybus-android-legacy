package org.globant.mybus.requests;

import java.util.Map;

import org.globant.mybus.responses.RouteResponseCompound;

import android.util.Pair;

import com.globant.roboneck.requests.BaseNeckHttpRequest;
import com.globant.roboneck.requests.BaseNeckRequestException.Error;
import com.google.gson.Gson;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * REQUIRES A DEDICATED LISTENER IMPLEMENTING
 * PENDINGREQUESTLISTENER<RouteResponseCompound> ON THE FRAGMENT USING IT!
 * 
 * @author emiliano.desantis
 * 
 */
public class MultipleRouteRequest extends
		BaseNeckHttpRequest<RouteResponseCompound, RouteResponseCompound>
{
	private String coreUrl = "http://www.mybus.com.ar/api/v1/CombinedRoadApi.php";

	private String key = "94a08da1fecbb6e8b46990538c7b50b2";

	private int idLine1, idLine2, direction1, direction2, l1Stop1, l1Stop2,
			l2Stop1, l2Stop2;

	public MultipleRouteRequest(int idLine1, int idLine2, int direction1,
			int direction2, int l1Stop1, int l1Stop2, int l2Stop1, int l2Stop2)
	{
		super(RouteResponseCompound.class);

		this.idLine1 = idLine1;
		this.idLine2 = idLine2;
		this.direction1 = direction1;
		this.direction2 = direction2;
		this.l1Stop1 = l1Stop1;
		this.l1Stop2 = l1Stop2;
		this.l2Stop1 = l2Stop1;
		this.l2Stop2 = l2Stop2;
	}

	public MultipleRouteRequest()
	{
		super(RouteResponseCompound.class);
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
		// idline1=2&idline2=5&direction1=0&direction2=0&L1stop1=107&L1stop2=114&L2stop1=92&L2stop2=129&tk=94a08da1fecbb6e8b46990538c7b50b2
		return String
				.format("idline1=%s" + "&idline2=%s" + "&direction1=%s"
						+ "&direction2=%s" + "&L1stop1=%s" + "&L1stop2=%s"
						+ "&L2stop1=%s" + "&L2stop2=%s" + "&tk=%s", 
						idLine1, idLine2, direction1, direction2, l1Stop1, 
						l1Stop2, l2Stop1, l2Stop2, key);
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
	protected RouteResponseCompound getRequestModel(
			RouteResponseCompound response)
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
	protected boolean isLogicError(RouteResponseCompound response)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Error processError(int httpStatus,
			RouteResponseCompound response, String responseBody)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected RouteResponseCompound processContent(String responseBody)
	{
		RouteResponseCompound gson = new Gson().fromJson(responseBody,
				RouteResponseCompound.class);

		return gson;
	}
}
