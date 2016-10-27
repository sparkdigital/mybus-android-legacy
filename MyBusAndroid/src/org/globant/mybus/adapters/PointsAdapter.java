package org.globant.mybus.adapters;

import java.util.List;

import org.globant.models.RoutePoint;
import org.globant.mybus.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PointsAdapter extends CustomAdapter<RoutePoint>
{

	public PointsAdapter(Activity a, int layoutResID)
	{
		this.activity = a;

		this.layoutResID = layoutResID;

		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public PointsAdapter(Activity a, int layoutResID, List<RoutePoint> points)
	{
		super(a, layoutResID);
		
		this.items = points;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;
		
		ViewHolder Holder = null;
		
		final String address = items.get(position).getAddress();

		final double lat = items.get(position).getLat();
		
		final double lng = items.get(position).getLng();

		if (rowView == null)
		{
			rowView = inflater.inflate(layoutResID, parent, false);

			Holder = new ViewHolder();
		
			Holder.txtAddress = (TextView) rowView.findViewById(R.id.txtType);

			Holder.txtCoords = (TextView) rowView.findViewById(R.id.txtCoords);

			rowView.setTag(Holder);
		}
		else
		{
			rowView = convertView;

			Holder = ((ViewHolder) rowView.getTag());
		}
		
		Holder.txtAddress.setText(address);

		Holder.txtCoords.setText(lat + ", " + lng);
		
		// MUST NOT RETURN NULLS HERE!
		// SOURCE: http://stackoverflow.com/questions/2307688/crash-in-listview-at-abslistview-obtainview-for-listactivity
		return rowView;
	}

	@Override
	protected void onItemClickedEvent()
	{

	}

	static public class ViewHolder
	{
		LinearLayout pointPanel;

		TextView txtAddress, txtCoords;
	}
}
