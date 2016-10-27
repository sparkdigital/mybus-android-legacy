package org.globant.mybus.fragments;

import org.globant.mybus.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LoadingProgressFragment extends Fragment{
	
	private String infoText;
	private TextView tv_info;
	
	public LoadingProgressFragment(){/*Empty constructor*/}
	
	public static LoadingProgressFragment getInstance(String message){
		LoadingProgressFragment lpf = new LoadingProgressFragment();
		Bundle bun = new Bundle();
		bun.putString("message", message);
		lpf.setArguments(bun);
		return lpf;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_loadingprogress, container,false);
		
		infoText = getArguments().getString("message","nothing to show");
		tv_info = (TextView)view.findViewById(R.id.tv_info);
		tv_info.setText(infoText);
		
		return view;
	}

}
