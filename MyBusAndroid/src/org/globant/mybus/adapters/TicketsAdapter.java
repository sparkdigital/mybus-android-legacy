package org.globant.mybus.adapters;

import org.globant.data.DBTickets;
import org.globant.models.Ticket;
import org.globant.mybus.R;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TicketsAdapter extends CustomAdapter<Ticket>
{
	DBTickets tDBA;

	int _id, pos;

	public TicketsAdapter(Activity a, int layoutResID)
	{
		super(a, layoutResID);

		tDBA = new DBTickets(this.activity);

		populateList();
	}

	private void populateList()
	{
		tDBA.ReadableMode();

		this.items = tDBA.getList();

		tDBA.Close();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;

		ViewHolder Holder = null;

		final String price = String.valueOf(items.get(position).getPrice());

		final String description = items.get(position).getDescription();
		
//		final int _id = items.get(position).getId();

		if (rowView == null)
		{
			rowView = inflater.inflate(layoutResID, parent, false);

			Holder = new ViewHolder();

			Holder.panel = (LinearLayout) rowView.findViewById(R.id.panel);

			Holder.panel.setBackgroundColor(Color.parseColor("#6b6b6b"));

			Holder.txtPrice = (TextView) rowView.findViewById(R.id.txtPrice);

//			if (price.equals(Constants.BASE_TICKET_PRICE) && description.equals("Local (Todas las líneas)") && position == 0)
//			if (position == 0)
//			if(_id == 1)
//			{
//				Holder.panel.setBackgroundColor(Color.parseColor("#008e6f"));
//			}
//			else
//			{
//				Holder.panel.setBackgroundColor(Color.parseColor("#6b6b6b"));
//			}

			Holder.txtDescription = (TextView) rowView
					.findViewById(R.id.txtDetail);

			rowView.setTag(Holder);
		}
		else
		{
			rowView = convertView;

			Holder = ((ViewHolder) rowView.getTag());
		}

		Holder.txtDescription.setText(description);

		Holder.txtPrice.setText("$ " + price);

		return rowView;
	}

	@Override
	protected void onItemClickedEvent()
	{
	}

	static public class ViewHolder
	{
		LinearLayout panel;

		TextView txtPrice, txtDescription;
	}
}
