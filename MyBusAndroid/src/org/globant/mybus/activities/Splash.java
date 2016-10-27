package org.globant.mybus.activities;

import org.globant.mybus.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class Splash extends Activity
{
	/** Duration of wait **/
	private final int SPLASH_DISPLAY_LENGTH = 1000;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle)
	{
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		
		super.onCreate(icicle);
		setContentView(R.layout.splash);
		
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				Intent mainIntent = new Intent(Splash.this, MyBusActivity.class);
				Splash.this.startActivity(mainIntent);
				Splash.this.finish();
			}
		}, SPLASH_DISPLAY_LENGTH);
	}
}
