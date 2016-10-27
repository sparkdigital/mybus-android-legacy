package org.globant.mybus.adapters;

import java.util.List;

import org.globant.models.Station;
import org.globant.mybus.R;
import org.globant.mybus.adapters.CustomAdapter;

import com.globant.roboneckUI.base.BaseActivity;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StationsAdapter extends CustomAdapter<Station>
{

	public StationsAdapter(BaseActivity baseActivity, int listStationRow,
			List<Station> stations)
	{
		super(baseActivity, listStationRow);

		this.items = stations;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;

		ViewHolder Holder = null;

		final String address = items.get(position).getAddress();

		final String schedule = items.get(position).getAvailability();

		final double distance = items.get(position).getDistance();

		if (rowView == null)
		{
			rowView = inflater.inflate(layoutResID, parent, false);

			Holder = new ViewHolder();

			Holder.locationPanel = (LinearLayout) rowView
					.findViewById(R.id.locationPanel);

			Holder.locationPanel
				.setBackgroundColor(Color.parseColor("#6b6b6b"));

			Holder.locationPanel.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View arg0)
				{
					onItemClickedEvent();
				}
			});

			Holder.txtSchedule = (TextView) rowView
					.findViewById(R.id.txtSchedule);

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

		Holder.txtSchedule.setText("Horario: " + schedule);

		Holder.txtDistance.setText("Distancia: " + String.valueOf(distance)
				+ " metros");

		return rowView;
	}

	static public class ViewHolder
	{
		LinearLayout locationPanel;

		TextView txtSchedule, txtAddress, txtDistance;
	}

	@Override
	protected void onItemClickedEvent()
	{

	}
}
