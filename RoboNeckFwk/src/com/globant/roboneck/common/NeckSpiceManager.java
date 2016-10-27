package com.globant.roboneck.common;

import android.util.Log;

import com.globant.roboneck.requests.BaseNeckHttpRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * {@link SpiceManager} that uses {@link BaseNeckHttpRequest} and simplifies
 * cache.
 * 
 * @author federico.perez
 * 
 */
public class NeckSpiceManager extends SpiceManager {

	private ProgressListener progressListener;

	public NeckSpiceManager() {
		super(NeckService.class);
	}

	public NeckSpiceManager(ProgressListener progressListener) {
		super(NeckService.class);
		this.progressListener = progressListener;
	}

	/**
	 * Executes a cached request.
	 * 
	 * @param request
	 *            The request type.
	 * @param requestListener
	 *            Callback for result.
	 */
	public <T, R> void executeCacheRequest(BaseNeckHttpRequest<T, R> request, PendingRequestListener<T> requestListener) {
		super.execute(request, request.getCachekey(), request.getCacheExpirationTime(), requestListener);
	}
	
	public <T, R> void forceExecuteRequest(BaseNeckHttpRequest<T, R> request, PendingRequestListener<T> requestListener) {
		super.execute(request, request.getCachekey(), DurationInMillis.ALWAYS_EXPIRED, requestListener);
	}

	/**
	 * Executes a cached request showing progress bar.
	 * 
	 * @param request
	 *            The request type.
	 * @param requestListener
	 *            Callback for result.
	 */
	public <T, R> void executeCacheRequestWithProgress(BaseNeckHttpRequest<T, R> request,
			PendingRequestListener<T> requestListener) {
		if (progressListener != null) {
			progressListener.onShowProgress();
		} else {
			Log.w(NeckSpiceManager.class.getCanonicalName(),
					"Progress request was called but no ProgressListener was provided!");
		}
		super.execute(request, request.getCachekey(), request.getCacheExpirationTime(), requestListener);
	}

	/**
	 * Gets result from cache for a certain request.
	 * 
	 * @param request
	 *            The request to search in the cache.
	 * 
	 * @param requestListener
	 *            Callback for result.
	 */
	public <T, R> void getResultFromCache(BaseNeckHttpRequest<T, R> request, RequestListener<T> requestListener) {
		super.getFromCache(request.getResultType(), request.getCachekey(), request.getCacheExpirationTime(),
				requestListener);
	}

	/**
	 * Adds listener for pending requests due to orientation changes or leaving
	 * the application.
	 * 
	 * @param request
	 *            The request to listen.
	 * @param requestListener
	 *            Callback for result.
	 */
	public <T, R> void addListenerIfPending(BaseNeckHttpRequest<T, R> request, PendingRequestListener<T> requestListener) {
		super.addListenerIfPending(request.getResultType(), request.getCachekey(), requestListener);
	}

	public interface ProgressListener {
		public void onShowProgress();
	}
}
