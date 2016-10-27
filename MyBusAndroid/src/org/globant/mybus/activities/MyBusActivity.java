package org.globant.mybus.activities;

import org.globant.mybus.LocationTools;
import org.globant.mybus.R;
import org.globant.mybus.fragments.LeftMenuFragment;
import org.globant.mybus.fragments.PricesFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.globant.roboneckUI.base.BaseLeftMenuActivity;
import com.google.android.gms.maps.model.LatLng;

public class MyBusActivity extends BaseLeftMenuActivity implements Locator
{
	private LatLng curLoc;

	public LatLng getCurLoc()
	{
		return curLoc;
	}

	public void setCurLoc(LatLng curLoc)
	{
		this.curLoc = curLoc;
	}

	@Override
	protected int getActionBarDrawableId()
	{
		return R.drawable.ic_bar;
	}

	@Override
	public String getActivityTitle()
	{
		return "Tarifas"; 		// "MyBus"
	}

	@Override
	protected Fragment getLeftMenuFragment()
	{
		return new LeftMenuFragment();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setCurLoc(getCurrentLocation());
		setContentView(R.layout.activity_main);
		
		getSupportFragmentManager().beginTransaction()
				.add(R.id.container, new PricesFragment()).commit();

		StartMapActivity(MapActivity.SEARCH_MODE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_map_show:
		{
			StartMapActivity(MapActivity.SEARCH_MODE);
			return true;
		}
		default:
		{
			return super.onOptionsItemSelected(item);
		}
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();

		if (getCurrentLocation() != getCurLoc())
		{
			setCurLoc(getCurrentLocation());
		}
	}

	public void StartMapActivity(int mode)
	{
		ScreenManager.ShowMapActivity(this, MapActivity.SEARCH_MODE);
	}

	public LatLng getCurrentLocation()
	{
		try
		{
			return LocationTools.CurrentLocation(this);
		}
		catch(NullPointerException n)
		{
			Toast.makeText(getBaseContext(), "Cannot resolve current location!", Toast.LENGTH_SHORT).show();
			return null;
		}
	}

	@Override
	public LatLng getCachedLocation()
	{
		return getCurLoc();
	}

	@Override
	public void cacheLocation()
	{
		this.curLoc = getCurrentLocation(); 
	}

}
