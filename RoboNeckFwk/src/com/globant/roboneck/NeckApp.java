package com.globant.roboneck;

import android.app.Application;

import com.globant.roboneck.common.NeckPersister;

public abstract class NeckApp extends Application {

	private NeckPersister persister;

	protected static NeckApp instance;

	@Override
	public void onCreate() {
		super.onCreate();
		persister = createPersister();
		instance = this;
		int stringId = getApplicationInfo().labelRes;
		Logger.setTag(getString(stringId));
	}

	public NeckPersister getPersister() {
		return persister;
	}

	protected NeckPersister createPersister() {
		return new NeckPersister(2048);
	}

	public static NeckApp getInstance() {
		return instance;
	}

}
