package org.globant.mybus.fragments;

import org.globant.models.DrawerItem;
import org.globant.mybus.R;
import org.globant.mybus.adapters.DrawerItemCustomAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.globant.roboneckUI.base.BaseLeftMenuActivity;

public class LeftMenuFragment extends Fragment
{
	TextView socialAction;
	TextView loginRegisterAction;
	TextView chekinAction;

	FragmentManager fm;

	BaseLeftMenuActivity baseActivity;

	private String[] mNavigationDrawerItemTitles;

	private ListView mDrawerList;
	
	int currentFragment = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View baseview = inflater.inflate(R.layout.fragment_leftmenu, container,
				false);

		baseActivity = (BaseLeftMenuActivity) getActivity();

		mNavigationDrawerItemTitles = getResources().getStringArray(
				R.array.navigation_drawer_items_array);

		mDrawerList = (ListView) baseview.findViewById(R.id.drawerList);

		DrawerItem[] drawerItem = new DrawerItem[3];

		drawerItem[0] = new DrawerItem(mNavigationDrawerItemTitles[0]);
		drawerItem[1] = new DrawerItem(mNavigationDrawerItemTitles[1]);
//		drawerItem[3] = new DrawerItem(mNavigationDrawerItemTitles[3]);
		drawerItem[2] = new DrawerItem(mNavigationDrawerItemTitles[2]);

		DrawerItemCustomAdapter drawerAdapter = new DrawerItemCustomAdapter(
				this.getActivity(), R.layout.drawer_item_row, drawerItem);

		mDrawerList.setAdapter(drawerAdapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		fm = getActivity().getSupportFragmentManager();

		return baseview;
	}

	public class DrawerItemClickListener implements
			ListView.OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			selectItem(position);
		}

		private void selectItem(int position)
		{
			Fragment fragment = null;

			switch (position)
			{
			case 0:
				if (currentFragment != 0)
				{
					fragment = new FavoritesFragment();
					currentFragment = 0;
				}
				break;
			case 1:
				if (currentFragment != 1)
				{
					fragment = new StationsFragment();
					currentFragment = 1;					
				}
				break;
			case 2:
				if (currentFragment != 2)
				{
					fragment = new PricesFragment();
					currentFragment = 2;
				}
				break;

			default:
				break;
			}

			baseActivity.closeDrawer();

			if (fragment != null)
			{
				fm.beginTransaction().replace(R.id.container, fragment)
						.commit();

				mDrawerList.setItemChecked(position, true);
				mDrawerList.setSelection(position);
				getActivity().getActionBar().setTitle(
						mNavigationDrawerItemTitles[position]);

//				baseActivity.closeDrawer();
			}
			else
			{
				Log.e("MainActivity", "Error in creating fragment");
			}
		}
	}

}
