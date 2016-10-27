package org.globant.mybus.requests;

import java.util.Map;

import org.globant.mybus.Constants;
import org.globant.mybus.responses.NexusResponse;
import org.globant.mybus.responses.NexusResponseCompound;
import org.globant.mybus.responses.NexusResponseSimple;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Pair;

import com.globant.roboneck.requests.BaseNeckHttpRequest;
import com.globant.roboneck.requests.BaseNeckRequestException.Error;
import com.google.gson.Gson;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Named after the API it consumes. Refer to client documentation.
 */
public class NexusRequest extends
		BaseNeckHttpRequest<NexusResponse, NexusResponse> {

	private static final String PATH = "NexusApi.php";
		
	double lat0, lng0, lat1, lng1;

	public NexusRequest(double lat0, double long0, double lat1, double long1) {
		super(NexusResponse.class);

		this.lat0 = lat0;
		this.lat1 = lat1;
		this.lng0 = long0;
		this.lng1 = long1;
	}


	@Override
	public Object getCachekey() {
		return getUrl();
	}

	@Override
	public long getCacheExpirationTime() {
		return DurationInMillis.ALWAYS_RETURNED;
	}
	
	@Override
	protected String getBody() {
		// Format: formEncoding
		return String.format("lat0=%s&lng0=%s&lat1=%s&lng1=%s&tk=%s", lat0,
				lng0, lat1, lng1, Constants.MYBUS_KEY);
	}

	@Override
	protected Map getHeaders() {
		// return (Hashmap with key);
		return null;
	}

	@Override
	protected String getUrl() {
		return Constants.DOMAIN.concat(PATH);
	}

	@Override
	protected Method getMethod() {
		// return Method.GET;
		return Method.POST;
	}

	@Override
	protected Pair<String, String>[] getQueryParameters() {
		// TODO Figure out how to use this
		return null;
	}

	@Override
	protected boolean isLogicError(NexusResponse response) {
		// TODO Figure out how to use this
		return false;
	}

	@Override
	protected NexusResponse getRequestModel(NexusResponse response) {
		// TODO Figure out how to use this
		return response;
	}

	@Override
	protected Error processError(int httpStatus, NexusResponse response,
			String responseBody) {
		// TODO Figure out how to use this
		return null;
	}

	@Override
	protected NexusResponse processContent(String responseBody) {
		JSONObject jsonObject = new JSONObject();

		NexusResponse gson = new NexusResponse();

		try {
			jsonObject = new JSONObject(responseBody);

			int Type = jsonObject.getInt("Type");

			switch (Type) {
			case 0: {
				gson = new Gson().fromJson(responseBody,
						NexusResponseSimple.class);

				break;
			}
			case 1: {
				gson = new Gson().fromJson(responseBody,
						NexusResponseCompound.class);

				break;
			}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return gson;
	}

}
