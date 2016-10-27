package org.globant.mybus.fragments;

import org.globant.mybus.R;
import org.globant.mybus.activities.MapActivity;
import org.globant.mybus.adapters.MarkerOptionsAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MarkerOptions_FragDialog extends DialogFragment implements OnItemClickListener{
	
	//FOR ORIGIN OR DESTINY
	public static final int NORMAL_MODE = 0;
	public static final int SIMPLE_MODE = 1;
	
	private Activity activitycore;
	private ListView lv_options;
	private MarkerOptionsAdapter adapter;
	private int mode;

	public MarkerOptions_FragDialog(){/*Empty constructor*/}
	
	private void setActivityFather(Activity father){
		activitycore = father;
	}
	
	public static MarkerOptions_FragDialog getInstance(Activity act,int mode){
		MarkerOptions_FragDialog fd = new MarkerOptions_FragDialog();
		Bundle bun = new Bundle();
		bun.putInt("mode", mode);
		fd.setArguments(bun);
		fd.setActivityFather(act);
		return fd;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragdialog_markoptions, container,false);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		mode = getArguments().getInt("mode");  //NORMAL MODE OR SIMPLE MODE
		
		lv_options = (ListView)view.findViewById(R.id.lv_options);
		adapter = new MarkerOptionsAdapter(activitycore,mode);
		lv_options.setAdapter(adapter);
		lv_options.setOnItemClickListener(this);
		
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
		case 0:  //Reselect
				if(((MapActivity)activitycore).getFlagState() == MapActivity.FLAGSTATEORIGIN){
					((MapActivity)activitycore).RemoveOrigin();
				}else
					if(((MapActivity)activitycore).getFlagState() == MapActivity.FLAGSTATEDESTINY){
						((MapActivity)activitycore).RemoveDestiny();
					}	
				this.dismiss();
			break;
		case 1: //Favorites
				FragmentManager fm = ((MapActivity)activitycore).getSupportFragmentManager();
				NameFavorite_FragDialog nffd = NameFavorite_FragDialog.getInstance();
		        nffd.show(fm, "nffd");
		        this.dismiss();
			break;

		default:
			break;
		}
	}

	
}