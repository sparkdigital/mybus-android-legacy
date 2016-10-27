package org.globant.mybus.adapters;

import org.globant.data.DBLocations;
import org.globant.models.Location;
import org.globant.mybus.R;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LocationsAdapter extends CustomAdapter<Location>
{
	public LocationsAdapter(Activity a, int layoutResID)
	{
		super(a, layoutResID);

		populateList();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;

		ViewHolder Holder = null;

		// final int id = items.get(position).getId();

		final String address = items.get(position).getStreet();

		final double lat = items.get(position).getLatitude();

		final double lng = items.get(position).getLongitude();

		if (rowView == null)
		{
			rowView = inflater.inflate(layoutResID, parent, false);

			Holder = new ViewHolder();

			Holder.locationPanel = (LinearLayout) rowView
					.findViewById(R.id.locationPanel);

			Holder.locationPanel.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View arg0)
				{
					onItemClickedEvent();
				}
			});

			Holder.txtType = (TextView) rowView.findViewById(R.id.txtType);

			Holder.txtCoords = (TextView) rowView.findViewById(R.id.txtCoords);

			Holder.txtAddress = (TextView) rowView
					.findViewById(R.id.txtAddress);

			Holder.txtDistance = (TextView) rowView
					.findViewById(R.id.txtDistance);

			rowView.setTag(Holder);
		}
		else
		{
			rowView = convertView;

			Holder = ((ViewHolder) rowView.getTag());
		}

		Holder.txtAddress.setText(address);

		Holder.txtCoords.setText(lat + ", " + lng);

		return rowView;
	}

	protected void onItemClickedEvent()
	{
		
	}
	
	protected void populateList()
	{
		DBLocations locDBAdapter = new DBLocations(this.activity);
	
		locDBAdapter.ReadableMode();
	
		this.items = locDBAdapter.getList();
	}

	
	static public class ViewHolder
	{
		LinearLayout locationPanel;

		TextView txtType, txtCoords, txtAddress, txtDistance;
	}

}
