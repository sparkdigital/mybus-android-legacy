package org.globant.mybus.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Formatter.BigDecimalLayoutForm;

import org.globant.data.DBLocations;
import org.globant.models.Location;
import org.globant.mybus.R;
import org.globant.mybus.adapters.BusesAdapter.ViewHolder;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FavoritesAdapterFD extends CustomAdapter<Location>{
	
	public FavoritesAdapterFD(Activity activity,int layoutresID, List<Location> list_loc_fav){
		super(activity, layoutresID);		
		this.items = list_loc_fav;
	}
	
	@Override
	public View getView(int position, View convertview, ViewGroup parent) {
		View view = convertview;
		ViewHolder Holder = null;
		
		if (view == null)
		{
			view = inflater.inflate(layoutResID, parent, false);
			Holder = new ViewHolder();
			Holder.setTv_name((TextView)view.findViewById(R.id.tv_name));
			Holder.setTv_address((TextView)view.findViewById(R.id.tv_address));
			view.setTag(Holder);
		}
		else
		{
			view = convertview;
			Holder = ((ViewHolder) view.getTag());
		}

		Holder.getTv_name().setText(items.get(position).getObservations().toString());
		Holder.getTv_address().setText(items.get(position).getStreet());

		return view;
	}

	@Override
	protected void onItemClickedEvent() {
		// TODO Auto-generated method stub		
	}

	
	static public class ViewHolder
	{
		TextView tv_name,tv_address;

		public TextView getTv_name() {
			return tv_name;
		}

		public void setTv_name(TextView tv_name) {
			this.tv_name = tv_name;
		}

		public TextView getTv_address() {
			return tv_address;
		}

		public void setTv_address(TextView tv_address) {
			this.tv_address = tv_address;
		}
		
	}
	
	public List<Location> getList(){
		return items;
	}
	
}
