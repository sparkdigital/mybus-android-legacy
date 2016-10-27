package org.globant.mybus.fragments;

import org.globant.mybus.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class InfoText_FragDialog extends DialogFragment{

	private TextView tv_upbus1,tv_timewalk,tv_busn1,tv_timebus1,tv_downbus1,tv_timewalk2,tv_upbus2,tv_busn2,tv_timebus2,tv_downbus2;
	private int mode = 0; //0:simple mode,1:compound mode
	
	public InfoText_FragDialog(){/*Empty constructor*/}
	
	public static InfoText_FragDialog getInstance(String busname1,String addressupbus1,String addressdownbus1){
		InfoText_FragDialog itfd = new InfoText_FragDialog();
		Bundle bun = new Bundle();
		bun.putInt("mode", 0);
		bun.putString("busname1", busname1);
		bun.putString("addressupbus1", addressupbus1);
		bun.putString("addressdownbus1", addressdownbus1);
		itfd.setArguments(bun);
		return itfd;
	}
	
	public static InfoText_FragDialog getInstance(String busname1,String busname2,String addressupbus1,String addressupbus2,String addressdownbus1,String addressdownbus2){
		InfoText_FragDialog itfd = new InfoText_FragDialog();
		Bundle bun = new Bundle();
		bun.putInt("mode", 1);
		bun.putString("busname1", busname1);
		bun.putString("busname2", busname2);
		bun.putString("addressupbus1", addressupbus1);
		bun.putString("addressupbus2", addressupbus2);
		bun.putString("addressdownbus1", addressdownbus1);
		bun.putString("addressdownbus2", addressdownbus2);
		itfd.setArguments(bun);
		return itfd;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mode = getArguments().getInt("mode");
		View view;
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		if(getArguments() != null){
			if(getArguments().getInt("mode") == 0){
				view = inflater.inflate(R.layout.fragdialog_infotextsimple, container,false);
			}else{
				view = inflater.inflate(R.layout.fragdialog_infotextcompound, container,false);
				tv_busn2 = (TextView)view.findViewById(R.id.tv_busn2);
				tv_upbus2 = (TextView)view.findViewById(R.id.tv_upbus2);
				tv_downbus2 = (TextView)view.findViewById(R.id.tv_downbus2);
//				tv_timewalk2 = (TextView)view.findViewById(R.id.tv_timewalk2);
//				tv_timebus2 = (TextView)view.findViewById(R.id.tv_timebus2);
				tv_busn2.setText(getArguments().getString("busname2"));
				tv_upbus2.setText(getString(R.string.CaminarHasta)+" "+getArguments().getString("addressupbus2"));
				tv_downbus2.setText(getString(R.string.Descender)+" "+getArguments().getString("addressdownbus2"));
				//timewalk!
				//timebus!
			}
			tv_busn1 = (TextView)view.findViewById(R.id.tv_busn1);
			tv_upbus1 = (TextView)view.findViewById(R.id.tv_upbus1);
			tv_downbus1 = (TextView)view.findViewById(R.id.tv_downbus1);
//			tv_timewalk = (TextView)view.findViewById(R.id.tv_timewalk);
//			tv_timebus1 = (TextView)view.findViewById(R.id.tv_timebus1);
			tv_busn1.setText(getArguments().getString("busname1"));
			tv_upbus1.setText(getString(R.string.CaminarHasta)+" "+getArguments().getString("addressupbus1"));
			tv_downbus1.setText(getString(R.string.Descender)+" "+getArguments().getString("addressdownbus1"));
			//timewalk!
			//timebus!!
			
			return view;
		}
		return null;
	}
	
}
