package org.globant.mybus.adapters;

import java.util.List;

import org.globant.models.Bus;
import org.globant.mybus.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class BusLineAdapter extends CustomAdapter<Bus>
{

	public BusLineAdapter(Activity a, int layoutResID)
	{
		this.activity = a;

		this.layoutResID = layoutResID;

		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public BusLineAdapter(Activity a, int layoutResID, List<Bus> buses)
	{
		super(a, layoutResID);
		
		this.items = buses;
	}

	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;
		
		ViewHolder Holder = null;
		
		final String lineNumber = items.get(position).getName();
		
		final String color = items.get(position).getColor();
		
		if (rowView == null)
		{
			rowView = inflater.inflate(layoutResID, parent, false);
			
			Holder = new ViewHolder();
			
			Holder.txtNumber = (TextView) rowView.findViewById(R.id.txtNumber);
			
			Holder.chkSelected = (CheckBox) rowView.findViewById(R.id.chkSelected);
			
			rowView.setTag(Holder);
		}
		else
		{
			rowView = convertView;
			
			Holder = ((ViewHolder) rowView.getTag());
		}
		
		Holder.txtNumber.setText(lineNumber);
		
		Holder.txtNumber.setTextColor(Color.parseColor("#" + color));
		
		// TODO Figure a way to store whether the checkbox is checked on the holder
		
		return rowView;
	}

	@Override
	protected void onItemClickedEvent()
	{

	}

	static public class ViewHolder
	{
		TextView txtNumber;
		
		CheckBox chkSelected;
		
//		ImageView imgView;
	}
}