package com.globant.roboneck.requests;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;

import android.net.Uri;
import android.util.Pair;

import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;

public abstract class BaseNeckHttpRequest<T, R> extends OkHttpSpiceRequest<T> {

	protected static final String ENCODING = "UTF-8";

	protected enum Method {
		GET, POST, PUT, DELETE
	}

	public BaseNeckHttpRequest(Class<T> clazz) {
		super(clazz);
	}

	public abstract Object getCachekey();

	public abstract long getCacheExpirationTime();

	protected abstract String getBody();

	protected abstract Map<String, String> getHeaders();

	protected abstract String getUrl();

	protected abstract Method getMethod();

	protected abstract R processContent(String responseBody);

	protected abstract boolean isLogicError(R response);

	protected abstract T getRequestModel(R response);

	protected abstract BaseNeckRequestException.Error processError(int httpStatus, R response, String responseBody);

	/**
	 * Method for retrieval of parameters required by query.
	 * 
	 * ALL PARAMETERS *SHOULD* BE PASSED AS BRUTE, UNENCODED STRINGS!
	 */
	protected abstract Pair<String, String>[] getQueryParameters();

	// Override this method if more configuration is needed for the Connection
	protected HttpURLConnection getConnection(URI uri) throws MalformedURLException {
		HttpURLConnection conn = getOkHttpClient().open(uri.toURL());
		if (getHeaders() != null) {
		   for (String header : getHeaders().keySet()) {
			   conn.addRequestProperty(header, getHeaders().get(header));
		   }
		}
		return conn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T loadDataFromNetwork() throws BaseNeckRequestException {
		try {
			Uri.Builder uriBuilder = Uri.parse(getUrl()).buildUpon();

			Pair<String, String>[] params = getQueryParameters();
			if (params != null) {
				for (Pair<String, String> pair : params) {

					uriBuilder.appendQueryParameter(URLEncoder.encode(pair.first, ENCODING),
							URLEncoder.encode(pair.second, ENCODING));
				}
			}

			URI uri;
			uri = new URI(uriBuilder.build().toString());

			HttpURLConnection connection = getConnection(uri);

			switch (getMethod()) {
			case GET:
				connection.setRequestMethod("GET");
				break;
			case POST:
				connection.setRequestMethod("POST");
				String body = getBody();
				if (body != null && !body.isEmpty()) {
					connection.getOutputStream().write(body.getBytes(ENCODING));
				}
				break;
			case DELETE:
			case PUT:
				throw new NotImplementedException("Method not yet implemented");
			default:
				break;
			}

			BaseNeckRequestException.Error e = null;
			R r = null;
			T t = null;

			InputStream in = null;
			int responseCode = connection.getResponseCode();
			if (responseCode >= 400) {
				in = connection.getErrorStream();
			} else {
				in = connection.getInputStream();
			}

			String body = IOUtils.toString(in, ENCODING);

			if (responseCode == 200) {
				r = processContent(body);
				if (isLogicError(r)) {
					e = processError(responseCode, r, body);
				} else {
					t = getRequestModel(r);
				}
			} else if (responseCode >= 400 && responseCode <= 600) {
				setRetryPolicy(null);
				e = processError(responseCode, null, body);
			} else {
				e = processError(responseCode, null, body);
			}

			if (in != null) {
				in.close();
			}

			if (e != null) {
				return (T) e;
			}

			return t;

		} catch (Exception e1) {
			throw new BaseNeckRequestException(e1);
		}
	}

}
