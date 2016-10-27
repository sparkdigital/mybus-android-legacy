package org.globant.mybus.fragments;

import java.util.List;
import java.util.NoSuchElementException;

import org.globant.data.DBSettings;
import org.globant.models.Setting;
import org.globant.mybus.R;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.globant.roboneckUI.base.BaseFragment;

public class SettingsFragment extends BaseFragment
{

	CheckBox chkShowFavorites, chkShowStations;

	DBSettings sDBA;

	List<Setting> list;

	@Override
	public void onRefresh()
	{

	}

	@Override
	public int getFragmentLayoutForCreateView()
	{
		return R.layout.fragment_settings;
	}

	@Override
	public void onCreatedNeckView(View inflatedView)
	{
		disableRefreshSwipe();
		sDBA = new DBSettings(getBaseActivity());
		chkShowFavorites = (CheckBox) inflatedView
				.findViewById(R.id.chkShowFavorites);
		chkShowStations = (CheckBox) inflatedView
				.findViewById(R.id.chkShowStations);
		load();
	}

	private void load()
	{
		sDBA.ReadableMode();

		list = sDBA.getList();

		try
		{
			Setting s = list.iterator().next();

			if (s.getShowFavorites() == 1)
			{
				chkShowFavorites.setChecked(true);
			}
			else
			{
				chkShowFavorites.setChecked(false);
			}

			if (s.getShowStations() == 1)
			{
				chkShowStations.setChecked(true);
			}
			else
			{
				chkShowStations.setChecked(false);
			}
		}
		catch (NoSuchElementException ns)
		{
			Toast.makeText(getBaseActivity(), "Set your user preferences now!",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onStop()
	{
		super.onStop();
		save();
	}

	private void save()
	{
		int favs = 0, stations = 0;

		if (chkShowFavorites.isChecked())
		{
			favs = 1;
		}

		if (chkShowStations.isChecked())
		{
			stations = 1;
		}

		sDBA.WritableMode();

		if (list.isEmpty())
		{
			sDBA.Insert(favs, stations);
		}
		else
		{
			sDBA.Update(list.iterator().next().getId(), favs, stations);
		}

		sDBA.Close();
	}

}
