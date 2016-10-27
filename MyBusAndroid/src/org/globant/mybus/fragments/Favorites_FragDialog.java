package org.globant.mybus.fragments;

import org.globant.data.DBLocations;
import org.globant.models.Location;
import org.globant.mybus.R;
import org.globant.mybus.activities.MapActivity;
import org.globant.mybus.adapters.FavoritesAdapterFD;

import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Favorites_FragDialog extends DialogFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

	private Activity activitycore;
	private ListView lv_favorites;
	private FavoritesAdapterFD adapter;
	
	public Favorites_FragDialog(){ /*Empty constructor*/}
	
	public void setActivityFather(Activity act){
		activitycore = act;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragdialog_favorites, container, false);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		lv_favorites = (ListView)view.findViewById(R.id.lv_favorites);	
		DBLocations dbloc = new DBLocations(activitycore);
		dbloc.ReadableMode();		
		adapter = new FavoritesAdapterFD(activitycore, R.layout.list_favoritefd_row,dbloc.getListFavorites());
		dbloc.Close();
		lv_favorites.setAdapter(adapter);
		lv_favorites.setOnItemClickListener(this);
		lv_favorites.setOnItemLongClickListener(this);
		
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Location fav = adapter.getList().get(position);
		LatLng point = new LatLng(fav.getLatitude(), fav.getLongitude());
		((MapActivity)activitycore).ChangeCurrentMarker(point, fav.getObservations(), fav.getStreet());
		this.dismiss();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,long id) {

		Location fav = adapter.getList().get(position);
		DBLocations dbl = new DBLocations(activitycore);
		dbl.WritableMode();
		dbl.Delete(fav.getId());
		dbl.Close();
		adapter.getList().remove(position);
		adapter.notifyDataSetChanged();
		return true;
	}
	


}
