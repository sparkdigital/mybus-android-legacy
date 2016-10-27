package org.globant.mybus.adapters;

import org.globant.data.DBLocations;
import org.globant.models.Location;
import org.globant.mybus.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FavoritesAdapter extends CustomAdapter<Location>
{
	DBLocations dbloc;

	int _id, pos;

	public FavoritesAdapter(Activity a, int layoutResID)
	{
		super(a, layoutResID);

		dbloc = new DBLocations(this.activity);

		populateList();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;

		ViewHolder Holder = null;

		// final int id = items.get(position).getId();

		final String address = items.get(position).getStreet();

		final String name = items.get(position).getObservations();

		// final double lat = items.get(position).getLatitude();
		//
		// final double lng = items.get(position).getLongitude();

		if (rowView == null)
		{
			rowView = inflater.inflate(layoutResID, parent, false);

			Holder = new ViewHolder();

			Holder.locationPanel = (LinearLayout) rowView
					.findViewById(R.id.locationPanel);

			Holder.locationPanel
					.setBackgroundColor(Color.parseColor("#6b6b6b"));

			Holder.txtName = (TextView) rowView.findViewById(R.id.txtName);

			Holder.txtAddress = (TextView) rowView
					.findViewById(R.id.txtAddress);

			rowView.setTag(Holder);
		}
		else
		{
			rowView = convertView;

			Holder = ((ViewHolder) rowView.getTag());
		}

		Holder.txtAddress.setText(address);

		Holder.txtName.setText(name);

		return rowView;
	}

	@Override
	protected void onItemClickedEvent()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				activity);

		// set title
		alertDialogBuilder.setTitle(R.string.confirm);

		// set dialog message
		alertDialogBuilder.setMessage(R.string.deletion_confirmation)
				.setCancelable(false)
				.setPositiveButton("Sí", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						// if this button is clicked, close
						// current activity
						// activity.this.finish();
						deleteRow(_id, pos);

						dialog.dismiss();
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	public void deleteRow(final int position, final int id)
	{

		dbloc.WritableMode();

		dbloc.Delete(id);

		dbloc.Close();

		items.remove(position);

		notifyDataSetChanged();
	}

	private void populateList()
	{
		dbloc.ReadableMode();

		this.items = dbloc.getListFavorites();

		dbloc.Close();
	}

	static public class ViewHolder
	{
		LinearLayout locationPanel;

		TextView txtName, txtAddress;
	}

}
