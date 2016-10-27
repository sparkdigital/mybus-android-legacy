package org.globant.mybus.fragments;

import org.globant.mybus.R;
import org.globant.mybus.activities.MapActivity;

import android.R.layout;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.globant.roboneckUI.base.BaseFragment;
import com.google.android.gms.maps.model.LatLng;

public class FiltersFragment extends BaseFragment implements View.OnClickListener
{
	
	public static final int LAYOUTORIGIN = 0;
	public static final int LAYOUTDESTINY = 1;
	
	private LinearLayout layout_origin,layout_destiny;
	private EditText et_origin,et_destiny;
	private ImageButton b_back_1,b_back_2,b_confirm_origin,b_confirm_destiny,b_favorites,b_gps,b_switch;
	private TextView tv_orig_dest;
	private LinearLayout layout_orig_dest,layout_main_filter;
	private FragmentActivity activity;

	@Override
	public void onRefresh()
	{

	}

	/**
	 * ALWAYS uses an integer identifying for the fragment it will handle.
	 */
	@Override
	public int getFragmentLayoutForCreateView()
	{
		// TODO Auto-generated method stub
		return R.layout.fragment_filters;
	}

	/**
	 * Called after getFragmentLayoutForCreateView() 	
	 */
	@Override
	public void onCreatedNeckView(View inflatedView)
	{
		View view = inflatedView;
		this.disableRefreshSwipe();
		//---- Initialization -------------
		activity = getActivity();
		layout_main_filter = (LinearLayout)view.findViewById(R.id.layout_main_filter);
		b_switch = (ImageButton)view.findViewById(R.id.b_switch);
		layout_orig_dest = (LinearLayout)view.findViewById(R.id.layout_orig_dest);
		layout_origin = (LinearLayout)view.findViewById(R.id.Layout_origin);
		layout_destiny = (LinearLayout)view.findViewById(R.id.Layout_destiny);
		et_origin = (EditText)view.findViewById(R.id.et_origin);
		et_destiny = (EditText)view.findViewById(R.id.et_destiny);
		tv_orig_dest = (TextView)view.findViewById(R.id.tv_orig_dest);
		b_back_1 = (ImageButton)view.findViewById(R.id.b_back_1);
		b_back_2 = (ImageButton)view.findViewById(R.id.b_back_2);
		b_confirm_origin = (ImageButton)view.findViewById(R.id.b_confirm_origin);
		b_confirm_destiny = (ImageButton)view.findViewById(R.id.b_confirm_destiny);
		b_favorites = (ImageButton)view.findViewById(R.id.b_favorites);
		b_gps = (ImageButton)view.findViewById(R.id.b_gps);
		b_back_1.setOnClickListener(this);
		b_back_2.setOnClickListener(this);
		b_confirm_origin.setOnClickListener(this);
		b_confirm_destiny.setOnClickListener(this);
		b_favorites.setOnClickListener(this);
		b_gps.setOnClickListener(this);
		b_switch.setOnClickListener(this);
		layout_main_filter.setOnClickListener(this);
		//-----------------------------------
		
	}
	
	//----- Layouts visibility  ---------------------------
	public void Layout_Visible(int layoutvisible){   //LAYOUTORIGIN OR LAYOUTDESTINY CONSTANTS
		if(layoutvisible == 0){
			layout_origin.setVisibility(View.VISIBLE);
			layout_destiny.setVisibility(View.INVISIBLE);
			layout_orig_dest.setBackgroundResource(R.layout.statusmode_origin);
			tv_orig_dest.setText(R.string.Origin);
		}
		else{
			layout_origin.setVisibility(View.INVISIBLE);
			layout_destiny.setVisibility(View.VISIBLE);
			layout_orig_dest.setBackgroundResource(R.layout.statusmode_destination);
			tv_orig_dest.setText(R.string.Destination);
		}
	}
	//-----------------Change EditText contain--------------
	public void Edit_text_origin(String originaddress){
		et_origin.setText(originaddress);
	}
	public void Edit_text_destiny(String destinyaddress){
		et_destiny.setText(destinyaddress);
	}
	public boolean Edit_text_origin_empty(){ 
		if(et_origin.getText().toString().equals(""))
			return true;
		else
			return false;
	}
	public boolean Edit_text_destination_empty(){ 
		if(et_destiny.getText().toString().equals(""))
			return true;
		else
			return false;
	}
	public String Edit_text_origin(){
		return et_origin.getText().toString();
	}
	public String Edit_text_destination(){
		return et_destiny.getText().toString();
	}
	
	@Override
	public void onClick(View v) {
		
		if(v.getId() == b_back_1.getId())
			activity.finish(); //finish or change?
		else
			if(v.getId() == b_back_2.getId())
				((MapActivity)activity).ChangingFlagState(MapActivity.FLAGSTATEORIGIN);
			else
				if( v.getId() == b_confirm_origin.getId() ){ //Confirm Origin!
					if( et_origin.getText().toString().length() >= 4 ){
						if( ((MapActivity)activity).ExistMarkOrigin() == true ){
							if(((MapActivity)activity).getSnippetMarkOrigin().equals(et_origin.getText().toString())) //SAME ADDRESS
								((MapActivity)activity).ChangingFlagState(MapActivity.FLAGSTATEDESTINY);
							else{
								((MapActivity)activity).AddressByTextview( et_origin.getText().toString() );
								InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(et_origin.getWindowToken(), 0);
							}
						}
						else{
							((MapActivity)activity).AddressByTextview( et_origin.getText().toString() );
							InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(et_origin.getWindowToken(), 0);
						}
					}else
						Toast.makeText(activity, getString(R.string.correct_value_origin) , Toast.LENGTH_SHORT).show();
				}else
					if( v.getId() == b_confirm_destiny.getId() ){  //Confirm Destiny!
						if( et_destiny.getText().toString().length() >= 4){
							if(((MapActivity)activity).ExistMarkDestiny() == true){
								if(((MapActivity)activity).getSnippetMarkDestiny().equals(et_destiny.getText().toString())){  //ARENT SAME ADDRESS
									if(((MapActivity)activity).ExistMarkOrigin() == true && ((MapActivity)activity).ExistMarkDestiny() == true){
										GenerateResults(); //Start Search!
									}
									else
										Toast.makeText(activity, getString(R.string.org_dest_must_completed) , Toast.LENGTH_SHORT).show();
								}else{
									((MapActivity)activity).AddressByTextview( et_destiny.getText().toString() );
									InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
									imm.hideSoftInputFromWindow(et_destiny.getWindowToken(), 0);
								}
							}
							else{
								((MapActivity)activity).AddressByTextview( et_destiny.getText().toString() );
								InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(et_destiny.getWindowToken(), 0);
							}
											
						}else
							Toast.makeText(activity, getString(R.string.correct_value_destination) , Toast.LENGTH_SHORT).show();
					}else
						if( v.getId() == b_gps.getId() ){ //Touch GPS button
							LatLng locat = ((MapActivity)activity).CurrentLocation();
							((MapActivity)activity).ChangeCurrentMarker(locat, getString(R.string.MiPosicionActual));
						}else
							if( v.getId() == b_favorites.getId() ){ //Touch favorite button
								FragmentManager fm = activity.getSupportFragmentManager();
								Favorites_FragDialog ffd = new Favorites_FragDialog();
								ffd.setActivityFather(activity);
						        ffd.setRetainInstance(true);
						        ffd.show(fm, "ffd");
							}else
								if(v.getId() == b_switch.getId()){ //Switch button
									if(((MapActivity)activity).ExistMarkOrigin() == true && ((MapActivity)activity).ExistMarkDestiny() == true){
										((MapActivity)activity).SwitchPointers();
										Toast.makeText(activity, getString(R.string.switch_complete),Toast.LENGTH_SHORT).show();
									}else
										Toast.makeText(activity, getString(R.string.complete_markers),Toast.LENGTH_SHORT).show();
								}
	}
	
	
	public void GenerateResults(){
		//Show Buses list!
		FragmentManager fm = activity.getSupportFragmentManager();
		ResultsFragDialog rfd = ResultsFragDialog.getInstance(activity);
        rfd.show(fm, "rfd");
	}
}
