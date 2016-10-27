package org.globant.mybus.adapters;

import java.util.List;

import org.globant.models.BusStop;
import org.globant.mybus.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.globant.roboneckUI.base.BaseActivity;

public class BusesAdapter extends CustomAdapter<BusStop>
{
	/**
	 * Old constructor, used primarily for testing.
	 */
	public BusesAdapter(Activity a, int layoutResID)
	{
		this.activity = a;

		this.layoutResID = layoutResID;

//		items = new ArrayList<Location>();
//		
//		items.add(new Location(0, -38.009456, -57.540766, "Brown", "1599", 0, "", 0, 0, ""));
//		items.add(new Location(0, -38.062141, -57.552150, "Puan", "2244", 0, "", 0, 0, ""));
//
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public BusesAdapter(BaseActivity baseActivity, int listStationRow,
			List<BusStop> buses)
	{
		super(baseActivity, listStationRow);

		this.items = buses;
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
		
		final double distance = items.get(position).getDistance();

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
		
		Holder.txtDistance.setText(String.valueOf(distance));

		return rowView;
	}
	
	protected void onItemClickedEvent()
	{
		
	}

	static public class ViewHolder
	{
		LinearLayout locationPanel;

		TextView txtType, txtCoords, txtAddress, txtDistance;
	}
}
