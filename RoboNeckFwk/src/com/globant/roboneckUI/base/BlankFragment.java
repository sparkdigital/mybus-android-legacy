package com.globant.roboneckUI.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BlankFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		TextView v = new TextView(getActivity());
		
//		container.addView(v);
		
		v.setText("Arreglo chancho");
		
		return v;
//		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
