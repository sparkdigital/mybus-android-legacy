package org.globant.mybus.adapters;

import java.util.List;

import org.globant.models.CompoundResult;
import org.globant.models.Result;
import org.globant.models.SimpleResult;
import org.globant.mybus.R;

import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

// TODO Remember what Iñaki said: if we need flags or instanceofs, then there's a way to solve the issue via inheritance.
public class ResultsAdapter extends BaseAdapter {
	
	//---------
	public static final int SIMPLE_RESULT = 0;
	public static final int COMPOUND_RESULT = 1;
	//--------
	
	private Activity activitycore;
	private List<Result> list_res;
	private LatLng origin,destination;
	private int flagsimpleorcompound = SIMPLE_RESULT;  //0:simple -- 1:compound

	public ResultsAdapter(Activity act, List<Result> lr){
		activitycore = act;
		list_res = lr;
		
		//Filtrar info si es simple o compuesta
		if(list_res.get(0) instanceof SimpleResult)
			flagsimpleorcompound = SIMPLE_RESULT;
		else
			if(list_res.get(0) instanceof CompoundResult)
				flagsimpleorcompound = COMPOUND_RESULT;
	}

	@Override
	public int getCount() {
		return list_res.size();
	}

	@Override
	public Object getItem(int position) {
		return list_res.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View rowview, ViewGroup viewgroup) {
		View view = rowview;
		
		if(flagsimpleorcompound == SIMPLE_RESULT){ //Simple view
			SimpleHolder holder = null;
			if(view == null){
				holder = new SimpleHolder();
				LayoutInflater ltinflate = (LayoutInflater)activitycore.getSystemService(activitycore.LAYOUT_INFLATER_SERVICE);
				view = ltinflate.inflate(R.layout.list_simpleresult_row, viewgroup,false);
				
				holder.tv_busline = (TextView)view.findViewById(R.id.tv_busline);
				holder.tv_distance = (TextView)view.findViewById(R.id.tv_distance);
				view.setTag(holder);
			}else{
				holder = (SimpleHolder)view.getTag();
			}
			
			SimpleResult result = (SimpleResult)list_res.get(position);
			holder.tv_busline.setText(result.BusLineName);  //busname		
			holder.tv_busline.setTextColor(Color.parseColor("#"+result.BusLineColor));  ///////////////////////////////////////////////
			if(result.StartBusStopDistanceToOrigin.equals("") == false){
				String dist = result.StartBusStopDistanceToOrigin.replaceAll(",", ".");
				double distance = Double.parseDouble(dist);
				distance = distance/100;
				if(distance < 1)
					holder.tv_distance.setText("Distancia a pie: menos de 1 cuadra");  //walk distance
				else{
					String[] c = (String.valueOf(distance)).split("[.]");
					holder.tv_distance.setText("Distancia a pie: "+c[0]+" cuadras");  //walk distance
				}
			}
			
		}else{ //Compound view
			CompoundHolder holder = null;
			if(view == null){
				holder = new CompoundHolder();
				LayoutInflater ltinflate = (LayoutInflater)activitycore.getSystemService(activitycore.LAYOUT_INFLATER_SERVICE);
				view = ltinflate.inflate(R.layout.list_compoundresult_row, viewgroup,false);
				
				holder.tv_bus1 = (TextView)view.findViewById(R.id.tv_bus1);
				holder.tv_bus2 = (TextView)view.findViewById(R.id.tv_bus2);
				view.setTag(holder);
			}else{
				holder = (CompoundHolder)view.getTag();
			}
			
			CompoundResult result = (CompoundResult)list_res.get(position);
			holder.tv_bus1.setText(result.busLine1);
			holder.tv_bus1.setTextColor(Color.parseColor("#"+result.line1Color));
			holder.tv_bus2.setText(result.busLine2);
			holder.tv_bus2.setTextColor(Color.parseColor("#"+result.line2Color));
			
		}
		
		return view;
	}
	
	private class SimpleHolder{
		TextView tv_busline,tv_distance;
	}
	
	private class CompoundHolder{
		TextView tv_bus1,tv_bus2;
	}
	
	
	public int getFlagsimpleorcompound() {
		return flagsimpleorcompound;
	}

}
