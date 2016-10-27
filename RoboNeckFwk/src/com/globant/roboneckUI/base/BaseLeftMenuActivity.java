package com.globant.roboneckUI.base;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.globant.roboneck.R;

public abstract class BaseLeftMenuActivity extends BaseActivity
{

	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayShowHomeEnabled(true);

		getSupportActionBar().setHomeButtonEnabled(true);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null)
		{
			getSupportFragmentManager().beginTransaction()
					.add(R.id.left_menu_fragment, getLeftMenuFragment())
					.commit();
		}
	}

	protected abstract Fragment getLeftMenuFragment();

	@Override
	public void onResume()
	{
		super.onResume();
	}

	@Override
	public void onPause()
	{
		super.onPause();
	}

	@Override
	public void setContentView(int layoutResId)
	{

		super.setContentView(R.layout.activity_base_navigation);

		drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);

		getLayoutInflater().inflate(layoutResId,
				(ViewGroup) findViewById(R.id.activity_content));

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, 0, 0)
		{
			public void onDrawerClosed(View view)
			{
			}

			public void onDrawerOpened(View drawerView)
			{

			}
		};

		drawerToggle.syncState();

		drawerLayout.setDrawerListener(drawerToggle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);

		drawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			if (drawerLayout
					.isDrawerOpen(findViewById(R.id.left_menu_fragment)))
			{
				closeDrawer();
			}
			else
			{
				openDrawer();
			}
			return true;
		default:
			break;
		}

		drawerToggle.syncState();

		return super.onOptionsItemSelected(item);
	}

	public DrawerLayout getDrawerLayout()
	{
		return drawerLayout;
	}

	public ActionBarDrawerToggle getDrawerToggle()
	{
		return drawerToggle;
	}

	public void closeDrawer()
	{
		drawerLayout.closeDrawer(findViewById(R.id.left_menu_fragment));
	}

	public void openDrawer()
	{
		drawerLayout.openDrawer(findViewById(R.id.left_menu_fragment));
	}
}
