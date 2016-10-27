package org.globant.mybus.adapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.globant.mybus.R;
import org.globant.mybus.adapters.FavoritesAdapterFD.ViewHolder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MarkerOptionsAdapter extends BaseAdapter{
	
	private Activity activitycore;
	private List<String> opciones; 
	private int mode;
	
	/*
	 * String [] strings = new String [] {"0", "1" };
List<String> stringList = new ArrayList<String>(Arrays.asList(strings)); //new ArrayList is only needed if you absolutely need an ArrayList
	 * 
	 */
	public MarkerOptionsAdapter(Activity act,int mode){
		activitycore = act;
		this.mode = mode;
		String[] strings = activitycore.getResources().getStringArray(R.array.mark_options_array);
		opciones = new ArrayList<String>(Arrays.asList(strings));
	}
	
	@Override
	public int getCount() {
		if(mode == 1)
			return opciones.size()-1;
		else
			return opciones.size();
	}

	@Override
	public Object getItem(int position) {
		return opciones.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View rowview, ViewGroup viewgroup) {
		View view = rowview;
		viewholder holder = null;
		
		if (view == null){
			holder = new viewholder();
			LayoutInflater ltinflate = (LayoutInflater)activitycore.getSystemService(activitycore.LAYOUT_INFLATER_SERVICE);
			view = ltinflate.inflate(R.layout.list_markoptions_row, viewgroup, false);
			
			holder.tv_opt = (TextView)view.findViewById(R.id.tv_opt);
			holder.iv_icon1 = (ImageView)view.findViewById(R.id.iv_icon1);
			
			view.setTag(holder);
		}else{
			holder = (viewholder)view.getTag();
		}
		
		holder.tv_opt.setText(opciones.get(position).toString());  //adding text!
		
		switch (position) { //adding image url!
		case 0:
				holder.iv_icon1.setImageDrawable(activitycore.getResources().getDrawable(R.drawable.reselect));
			break;
		case 1:
				holder.iv_icon1.setImageDrawable(activitycore.getResources().getDrawable(R.drawable.favoritestardark));
			break;
		default:
			break;
		}
		
		
		return view;
	}
	
	static class viewholder{
		TextView tv_opt;
		ImageView iv_icon1,iv_icon2;
	}

}
