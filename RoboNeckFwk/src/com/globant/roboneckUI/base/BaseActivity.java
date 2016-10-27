package com.globant.roboneckUI.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseActivity extends ActionBarActivity
{

	public abstract String getActivityTitle();

	protected abstract int getActionBarDrawableId();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		getSupportActionBar().setIcon(getActionBarDrawableId());
	}

	public void onResume()
	{
		super.onResume();
		getSupportActionBar().setTitle(getActivityTitle());
	}
}
