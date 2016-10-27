package org.globant.mybus.fragments;

import org.globant.mybus.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InfoRoutesFragment extends Fragment implements View.OnClickListener{

	private TextView tv_busname1,tv_busname2,tv_time,tv_km;
	private LinearLayout layout_master;
	private int mode = 0;  //0:simple,1:compound  (layout mode opened)
	
	public InfoRoutesFragment(){/*Empty constructor*/}
	
	public static InfoRoutesFragment getInstance(String namebus,int colorb1,String kilom,String t,String addressup,String addressdown){
		InfoRoutesFragment irf = new InfoRoutesFragment();
		Bundle bun = new Bundle();
		bun.putInt("mode", 0);
		bun.putString("namebus1", namebus);
		bun.putString("time", t);
		bun.putString("kilom", kilom);
		bun.putString("addressup", addressup);
		bun.putString("addressdown", addressdown);
		bun.putInt("colorb1", colorb1);
		irf.setArguments(bun);
		return irf;
	}
	
	public static InfoRoutesFragment getInstance(String namebus1,int colorb1,String namebus2,int colorb2,String kilom,String t,String addressup1,String addressdown1,
																			String addressup2,String addressdown2){
		InfoRoutesFragment irf = new InfoRoutesFragment();
		Bundle bun = new Bundle();
		bun.putInt("mode", 1);
		bun.putString("namebus1", namebus1);
		bun.putString("namebus2", namebus2);
		bun.putString("time", t);
		bun.putString("kilom", kilom);
		bun.putInt("colorb1", colorb1);
		bun.putInt("colorb2", colorb2);
		bun.putString("addressup1", addressup1);
		bun.putString("addressdown1", addressdown1);
		bun.putString("addressup2", addressup2);
		bun.putString("addressdown2", addressdown2);
		irf.setArguments(bun);
		return irf;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mode = getArguments().getInt("mode");
		View view;
		if(getArguments() != null){
			if( getArguments().getInt("mode") == 0 ){
				view = inflater.inflate(R.layout.fragment_inforoutsimple, container,false);
			}else{
				view = inflater.inflate(R.layout.fragment_inforoutcompound, container,false);
				tv_busname2 = (TextView)view.findViewById(R.id.tv_busname2);
				tv_busname2.setText(getArguments().getString("namebus2"));
				tv_busname2.setTextColor(getArguments().getInt("colorb2"));
			}
			tv_busname1 = (TextView)view.findViewById(R.id.tv_upbus1);
			tv_time = (TextView)view.findViewById(R.id.tv_time);
			tv_km = (TextView)view.findViewById(R.id.tv_km);
			tv_busname1.setText(getArguments().getString("namebus1"));
			tv_busname1.setTextColor(getArguments().getInt("colorb1"));
			String[] parts =  getArguments().getString("time").split("[.]");	
			tv_time.setText(parts[0]);
			tv_km.setText(getArguments().getString("kilom"));
			layout_master = (LinearLayout)view.findViewById(R.id.layout_master);
			layout_master.setOnClickListener(this);
		return view;
		}
	return null;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == layout_master.getId()){
			FragmentManager fm = getActivity().getSupportFragmentManager();
			InfoText_FragDialog itfd;
			if(mode == 0){
				itfd = InfoText_FragDialog.getInstance(tv_busname1.getText().toString(),
																			getArguments().getString("addressup"),getArguments().getString("addressdown"));	
			}
			else{
				itfd = InfoText_FragDialog.getInstance(tv_busname1.getText().toString(),
																		tv_busname2.getText().toString(),
																		getArguments().getString("addressup1"), getArguments().getString("addressup2"),
																		getArguments().getString("addressdown1"), getArguments().getString("addressdown2"));
			}
	        itfd.setRetainInstance(true);
	        itfd.show(fm, "ffd");
		}
	}
	
}
