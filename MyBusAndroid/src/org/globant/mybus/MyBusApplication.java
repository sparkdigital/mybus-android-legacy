package org.globant.mybus;

import com.globant.roboneck.NeckApp;

public class MyBusApplication extends NeckApp
{

	private static MyBusApplication INSTANCE;

	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		INSTANCE = this;
		
//		FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/CALIBRI.TTF");
//		FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/CALIBRI.TTF");
//		FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/CALIBRI.TTF");		
	}

	public static MyBusApplication getInstance()
	{
		return INSTANCE;
	}
}