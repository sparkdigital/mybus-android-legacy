package org.globant.mybus.fragments;

import org.globant.data.DBLocations;
import org.globant.mybus.R;
import org.globant.mybus.activities.MapActivity;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class NameFavorite_FragDialog extends DialogFragment implements OnClickListener {
	
	private EditText et_namefav;
	private ImageButton ib_addfav;

	public NameFavorite_FragDialog(){/*Empty constructor*/}
	
	
	public static NameFavorite_FragDialog getInstance(){
		NameFavorite_FragDialog nffd = new NameFavorite_FragDialog();
		return nffd;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragdialog_favoritename, container, false);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		et_namefav = (EditText)view.findViewById(R.id.et_namefav);
		ib_addfav = (ImageButton)view.findViewById(R.id.ib_addfav);
		ib_addfav.setOnClickListener(this);

		//Show keyboard!
		/*InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(et_namefav, InputMethodManager.SHOW_IMPLICIT);*/
		
		/*InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et_origin.getWindowToken(), 0);*/

		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(et_namefav, InputMethodManager.SHOW_IMPLICIT);
		super.onResume();
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == ib_addfav.getId()) {

			if (et_namefav.getText().toString().length() > 2) {
				LatLng ll = new LatLng(0, 0);
				String address = new String();
				int flag = 0;

				DBLocations dbl = new DBLocations(getActivity());
				dbl.WritableMode();

				if (((MapActivity) getActivity()).getFlagState() == MapActivity.FLAGSTATEORIGIN && ((MapActivity) getActivity()).ExistMarkOrigin() == true) { // Origin marker
					ll = ((MapActivity) getActivity()).getLatLongMarkOrigin();
					address = ((MapActivity) getActivity()).getSnippetMarkOrigin();
					flag = 1;
				} else {// Destiny marker
					if (((MapActivity) getActivity()).getFlagState() == MapActivity.FLAGSTATEDESTINY
							&& ((MapActivity) getActivity()).ExistMarkDestiny() == true) {
						ll = ((MapActivity) getActivity()).getLatLngMarkDestiny();
						address = ((MapActivity) getActivity()).getSnippetMarkDestiny();
						flag = 1;
					}
				}
				if (flag == 1) {
					dbl.Insert(ll.latitude, ll.longitude, address,0, 0, 1, et_namefav.getText().toString());
					Toast.makeText(getActivity(), getString(R.string.new_favorite_added),Toast.LENGTH_SHORT).show();
					dismiss();
				}
				dbl.Close();
			} else
				Toast.makeText(getActivity(),getString(R.string.char_min),Toast.LENGTH_SHORT).show();
		}
	}
		
}
