package org.globant.mybus.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.globant.data.DBLocations;
import org.globant.models.RoutePoint;
import org.globant.models.Station;
import org.globant.mybus.Constants;
import org.globant.mybus.LocationTools;
import org.globant.mybus.R;
import org.globant.mybus.dialogs.NewAppDialog;
import org.globant.mybus.fragments.FiltersFragment;
import org.globant.mybus.fragments.InfoRoutesFragment;
import org.globant.mybus.fragments.LoadingProgressFragment;
import org.globant.mybus.fragments.MarkerOptions_FragDialog;
import org.globant.mybus.requests.AddressRequest;
import org.globant.mybus.requests.GeolocationRequest;
import com.google.android.gms.maps.SupportMapFragment;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.globant.roboneck.common.NeckSpiceManager;
import com.globant.roboneck.requests.BaseNeckRequestListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.octo.android.robospice.persistence.exception.SpiceException;

public class MapActivity extends FragmentActivity implements OnInfoWindowClickListener {

	// ----- MAP ACTIVITY MODES TO OPEN ----
	public static final int SEARCH_MODE = 0;
	public static final int BUSROUTES_MODE = 1;
	public static final int RECARGESTATIONS_MODE = 2;
	// ----- FLAG STATE MODES -----
	public static final int FLAGSTATEORIGIN = 0;
	public static final int FLAGSTATEDESTINY = 1;

	// ------CAMERA START--------------
	public static final double CAM_START_LONGITUDE = -57.553307;
	public static final double CAM_START_LATITUDE = -38.010481;

	private GoogleMap googlemap;
	private Activity activity = this;
	private Bundle bun_request; // bun.getInt("request_type") 0:busqueda / 1:recorrido colectivos /2:estaciones de recarga
	private boolean isloading = false;  //false at start!
	private Marker markOrigin, markDestiny, markline1start, markline1finish, markline2start, markline2finish;
	private Polyline polylineOne = null, polylineTwo = null;

	private int FlagEstateOriginDestiny = FLAGSTATEORIGIN; // si flag es 0:esta en modo seleccion origen / si es 1: esta en modo seleccion destino
	private FiltersFragment filt_frag;
	private FrameLayout frameLayoutInfo;
	protected NeckSpiceManager spiceManager = new NeckSpiceManager();
	
	protected boolean markerswheresearched = false;
	protected String shadowtextviewOrigin;
	protected String shadowtextviewDestination;
	// ------ STATION IMPORT FROM FRAGMENT STUFF ------
	protected ArrayList<Station> stations;
	
	// Diferentes modos de abrir el mapa: BUSQUEDA / RECORRIDO DE COLECTIVOS (EN
	// ELLOS CAMBIA SI SE PUEDE O NO TOCAR EL MAPA Y EL FRAGMENT QUE CONTIENEN
	// SOBRE ELLOS)

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_map);	
		
		bun_request = getIntent().getExtras();
		if (bun_request == null)
			bun_request.putInt("request_type", MapActivity.SEARCH_MODE); // Anti-dummies protection
		
		//--- load framelayout info reference ---
		frameLayoutInfo = (FrameLayout)findViewById(R.id.fl_info);

		try {
			// Loading map
			InitializeMap();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(googlemap != null){
			
			googlemap.setMyLocationEnabled(false);
			googlemap.getUiSettings().setMyLocationButtonEnabled(false);
			googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Constants.CAM_START_LATITUDE, Constants.CAM_START_LONGITUDE), 14)); 
			
			if (bun_request.getInt("request_type") == SEARCH_MODE) { // NORMAL SEARCH
				// --- loading fragment ---
				filt_frag = new FiltersFragment();
				LoadFragment(filt_frag);
				
				googlemap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
					@Override
					public void onMapClick(LatLng point) { // clicking on the map
						if (FlagEstateOriginDestiny == 0) { // Origin Mode!
							if (markOrigin == null){
								CreateReplaceMarkerOrigin(point, getString(R.string.Origin));  //This method will be asynchronous
							}
						} else if (FlagEstateOriginDestiny == 1) { // Destiny Mode!
							if (markDestiny == null){
								CreateReplaceMarkerDestiny(point, getString(R.string.Destination));	
							}
						}
					}
				});
				
				googlemap.setOnInfoWindowClickListener(this);
				googlemap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() { // when i touch the marker, the flagstate will change
							@Override
							public boolean onMarkerClick(Marker mark) {
								if (ExistMarkOrigin() == true) {
									if (mark.getId().equals(markOrigin.getId())) {
										ChangingFlagState(FLAGSTATEORIGIN);
										return false;
									}
								}
								if (ExistMarkDestiny() == true)
									if (mark.getId().equals(markDestiny.getId())) {
										ChangingFlagState(FLAGSTATEDESTINY);
										return false;
									}
								return false;
							}
						});
				
			} else if (bun_request.getInt("request_type") == BUSROUTES_MODE) { // BUS ROUTES MODE
	
				
				// TODO
	
			}else if (bun_request.getInt("request_type") == RECARGESTATIONS_MODE){ //RECARGE STATIONS MODE
				
				googlemap.setMyLocationEnabled(false);
				stations = bun_request.getParcelableArrayList("Stations");
				Toast.makeText(this, getString(R.string.mode_recharge_stations), Toast.LENGTH_SHORT).show();
				MarkerOptions mypos = CreateMarkerOption(CurrentLocation(), getString(R.string.Yo), getString(R.string.MiPosicionActual), false, (float)1, R.drawable.marker_icon_me);
				for(int i=0;i < stations.size(); i++){
					Station stat = stations.get(i);
					MarkerOptions markop = CreateMarkerOption(stat.getLatLng(), stat.getAddress(), getString(R.string.recharge_point_schedule)+stat.getAvailability(), false, (float)1, 0);
					googlemap.addMarker(markop);
				}			
				googlemap.addMarker(mypos);
				
			}
		}
		
		NewAppDialog newAppDialog = new NewAppDialog();
		newAppDialog.show(getFragmentManager(), "");
//		getActionBar().hide();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	   if ( keyCode == KeyEvent.KEYCODE_MENU ) {
	       // perform your desired action here
	       // return 'true' to prevent further propagation of the key event
	       return true;
	   }
	   // let the system handle all other key events
	   return super.onKeyDown(keyCode, event);
	}

	//----- LOADING A MARKER -------
	public void Loading(){ this.isloading = true;}
	public void NoLoading(){ this.isloading = false;}
	public boolean IsLoading(){ return this.isloading;}
	
	public boolean ExistMarkOrigin() {
		if (markOrigin != null)
			return true;
		else
			return false;
	}

	public boolean ExistMarkDestiny() {
		if (markDestiny != null)
			return true;
		else
			return false;
	}

	public LatLng getLatLongMarkOrigin() {
		return new LatLng(markOrigin.getPosition().latitude,
				markOrigin.getPosition().longitude);
	}

	public LatLng getLatLngMarkDestiny() {
		return new LatLng(markDestiny.getPosition().latitude,
				markDestiny.getPosition().longitude);
	}
	
	public String getSnippetMarkOrigin(){ return markOrigin.getSnippet();}  //the snippet contains the marker's address
	public String getSnippetMarkDestiny(){ return markDestiny.getSnippet();}

	public int getFlagState() {
		return FlagEstateOriginDestiny;
	}
	
	public boolean FlagMarkersSearched(){ return markerswheresearched; }
	public void MarkersSearchedTrue(){ markerswheresearched = true; }
	public void MarkersSearchedFalse(){ markerswheresearched = false; }
	

	// ------------ MANUAL REMOVE -------------
	public void RemoveOrigin() {
		markOrigin.remove();
		markOrigin = null;
		filt_frag.Edit_text_origin(""); // Empty the edittext origin
	}

	public void RemoveDestiny() {
		markDestiny.remove();
		markDestiny = null;
		filt_frag.Edit_text_destiny(""); // Empty the edittext destiny
		if (ExistMarkOrigin() == false)
			ChangingFlagState(FLAGSTATEORIGIN);
	}

	// ------------------------------------------

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
				this.finish();
			break;
		case R.id.item_map_show:
				ScreenManager.ShowMapActivity(this, MapActivity.SEARCH_MODE);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void InitializeMap() {
		if (googlemap == null) {
			//googlemap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			googlemap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			// check if map is created successfully or not
			if (googlemap == null)
				Toast.makeText(getApplicationContext(),
						getString(R.string.map_failure_warning), Toast.LENGTH_SHORT)
						.show();
			else
				googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Constants.CAM_START_LATITUDE, Constants.CAM_START_LONGITUDE), 14));    /*CameraUpdateFactory.newLatLngZoom(CurrentLocation()*/
		}
	}

	public LatLng CurrentLocation() { // Return the user's current location
		LatLng point = LocationTools.CurrentLocation(this);
		if(point == null)
			point = new LatLng(CAM_START_LATITUDE, CAM_START_LONGITUDE); //Default location!!
		
		return point;
	}

	public void MoveCameraTo(double lat, double lng) { // Move camera to
														// latitude & longitude
		CameraPosition camPosition = googlemap.getCameraPosition();
		if (!((Math.floor(camPosition.target.latitude * 100) / 100) == (Math.floor(lat * 100) / 100) && (Math.floor(camPosition.target.longitude * 100) / 100) == (Math.floor(lng * 100) / 100))) {
			googlemap.getUiSettings().setScrollGesturesEnabled(false);
			googlemap.animateCamera(
					CameraUpdateFactory.newLatLng(new LatLng(lat, lng)),
					new GoogleMap.CancelableCallback() {
						@Override
						public void onFinish() {
							googlemap.getUiSettings().setScrollGesturesEnabled(
									true);
						}

						@Override
						public void onCancel() {
							googlemap.getUiSettings().setAllGesturesEnabled(
									true);
						}
					});
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		InitializeMap();
	}

	public MarkerOptions CreateMarkerOption(LatLng point, String title,String snippet, boolean draggable, float alpha, int id_drawable) {
		if (point.latitude >= Constants.MARK_MIN_LATITUDE
				&& point.latitude <= Constants.MARK_MAX_LATITUDE)
			if (point.longitude >= Constants.MARK_MIN_LONGITUDE
					&& point.longitude <= Constants.MARK_MAX_LONGITUDE) {
				MarkerOptions marker = new MarkerOptions()
						.position(point)
						.title(title)
						.snippet(snippet)
						.draggable(draggable)
						.alpha(alpha);
				if( id_drawable != 0) //If load a dreawable or not
						marker.icon(BitmapDescriptorFactory.fromResource(id_drawable));
				
				return marker;
			}
		Toast.makeText(activity, getString(R.string.PuntoFueraDeRango),
				Toast.LENGTH_SHORT).show();
		return null; // Return null if its out of range
	}

	// -------------Markers Factory ------------------------------
	public void CreateReplaceMarkerOrigin(LatLng point, String title) { // Title for custom title of favorite place
		
		if(IsLoading() == false){
			Loading();
			AddressRequest addressreq = new AddressRequest(point);
			ShowProgressof(getString(R.string.marker_loading));
			spiceManager.executeCacheRequest(addressreq, new AddressListener(this,point,title,0));
			MarkersSearchedFalse(); //change the searched flat to false
		}
	}

	public void CreateReplaceMarkerDestiny(LatLng point, String title) { // Title for custom title of favorite place  //this will be asynchronized
		if(IsLoading() == false){
			Loading();
			AddressRequest addressreq = new AddressRequest(point);
			ShowProgressof(getString(R.string.marker_loading));
			spiceManager.executeCacheRequest(addressreq, new AddressListener(this,point,title,1));
			MarkersSearchedFalse(); //change the searched flat to false
		}		
	}
	
	private class AddressListener extends BaseNeckRequestListener<String>
	{

		protected Activity activitycore;
		protected LatLng point;

		@SuppressWarnings("unused")
		protected String title;
		
		protected int origin_destiny; //0:origin,1:destiny
		
		public AddressListener(Activity act,LatLng point,String title,int orig_dest){ 
			activitycore = act;
			this.point = point;
			this.title = title;
			this.origin_destiny = orig_dest;
		}
		
		@Override
		public void onRequestException(SpiceException ex)
		{
			ClearInfoFragment();
			NoLoading();
			ex.printStackTrace();
			Toast.makeText(activitycore, ex.getMessage(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onRequestSuccessful(String address)
		{
			ClearInfoFragment();
			NoLoading();
			try {
				if (address != null) { // Point have something!
					if(origin_destiny == 0){  //Origin mode
						CreateReplaceMarkerOrigin(point, address, getString(R.string.Originlocation));
					} else{  //Destiny Mode
						CreateReplaceMarkerDestiny(point, address, getString(R.string.Destinationlocation));
					}
				}
			} catch (Exception e) {
				Toast.makeText(activitycore, e.getMessage().toString(),Toast.LENGTH_SHORT).show();
			}		
		}

		@Override
		public void onRequestError(
				com.globant.roboneck.requests.BaseNeckRequestException.Error error)
		{
			ClearInfoFragment();
			NoLoading();
			Toast.makeText(activitycore, error.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	

	// ----------------------------------------------------------------

	public void ChangeCurrentMarker(LatLng point, String title) { // Used by my actual position, takes address from server
		if (FlagEstateOriginDestiny == FLAGSTATEORIGIN) {
			CreateReplaceMarkerOrigin(point, title);
		} else if (FlagEstateOriginDestiny == FLAGSTATEDESTINY) {
			CreateReplaceMarkerDestiny(point, title);
		}
	}
	
	public void ChangeCurrentMarker(LatLng point, String title, String address){ //overload method, its have an address, no server request!
		if (FlagEstateOriginDestiny == FLAGSTATEORIGIN) {
			CreateReplaceMarkerOrigin(point, address, title);
		} else if (FlagEstateOriginDestiny == FLAGSTATEDESTINY) {
			CreateReplaceMarkerDestiny(point, address, title);
		}
	}
	
	public void CreateReplaceMarkerOrigin(LatLng point,String address,String title){  //Method used when you have address
		final MarkerOptions marker = CreateMarkerOption(point, title,getString(R.string.Originlocation), false, (float) 0.8, R.drawable.markerorigen);
		if (marker != null) { // If position its in the range
			if (markOrigin != null)
				markOrigin.remove();
			try { 
				markOrigin = googlemap.addMarker(marker);
				markOrigin.setSnippet(address);			
				if(filt_frag.Edit_text_origin_empty()) 
					filt_frag.Edit_text_origin(address);
				else
					markOrigin.setSnippet(filt_frag.Edit_text_origin());
				MoveCameraTo(point.latitude, point.longitude);
				if(ExistMarkOrigin())
					markOrigin.showInfoWindow();
				MarkersSearchedFalse(); //change the searched flat to false
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(activity, getString(R.string.server_connection_failure_notice),
						Toast.LENGTH_SHORT).show();
			}
		}else{ 
			Toast.makeText(this, getString(R.string.PuntoFueraDeRango), Toast.LENGTH_SHORT).show();
			}
	}
	public void CreateReplaceMarkerDestiny(LatLng point,String address,String title){ //Method used when you have address
		final MarkerOptions marker = CreateMarkerOption(point, title,
				"Destiny location", false, (float) 0.8,
				R.drawable.markerdestino);
		if (marker != null) { // If position its in the range
			if (markDestiny != null)
				markDestiny.remove();
			try { 
				markDestiny = googlemap.addMarker(marker);
				markDestiny.setSnippet(address);
				if(filt_frag.Edit_text_destination_empty())
					filt_frag.Edit_text_destiny(address);
				else
					markDestiny.setSnippet(filt_frag.Edit_text_destination());
				shadowtextviewDestination = address;
				MoveCameraTo(point.latitude, point.longitude);
				if(ExistMarkDestiny())
					markDestiny.showInfoWindow();
				MarkersSearchedFalse(); //change the searched flat to false
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(activity, R.string.server_connection_failure_notice,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onInfoWindowClick(Marker marker) {

		if (markOrigin != null)
			if (marker.getId().equals(markOrigin.getId())) {
				if (getFlagState() != FLAGSTATEORIGIN)
					ChangingFlagState(FLAGSTATEORIGIN);	
				if(ExistInFavorites(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude)))
					LaunchFragDialogMarkOptions(MarkerOptions_FragDialog.SIMPLE_MODE);
				else
					LaunchFragDialogMarkOptions(MarkerOptions_FragDialog.NORMAL_MODE);
				return;
			}

		if (markDestiny != null)
			if (marker.getId().equals(markDestiny.getId())) {
				if (getFlagState() != FLAGSTATEDESTINY)
					ChangingFlagState(FLAGSTATEDESTINY);
				if(ExistInFavorites(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude)))
					LaunchFragDialogMarkOptions(MarkerOptions_FragDialog.SIMPLE_MODE);
				else
					LaunchFragDialogMarkOptions(MarkerOptions_FragDialog.NORMAL_MODE);
				return;
			}

		// Possible expansion in the future
	}

	public void LoadFragment(Fragment frag) {
		ScreenManager.showFragInFrameLayout(this,frag, R.id.fl_filters);
	}

	public void LaunchFragDialogMarkOptions(int mode) {
		FragmentManager fm = this.getSupportFragmentManager();
		MarkerOptions_FragDialog mofd = MarkerOptions_FragDialog.getInstance(this,mode);
		mofd.setRetainInstance(true);
		mofd.show(fm, "mofd");
	}
	
	public boolean ExistInFavorites(LatLng point){
		DBLocations dbl = new DBLocations(this);
		dbl.ReadableMode();
		List<org.globant.models.Location> l_locs = dbl.getListFavorites();
		for(int i=0; i< l_locs.size(); i++){
			if(l_locs.get(i).getLatitude() == point.latitude && l_locs.get(i).getLongitude() == point.longitude){ //Same point!
				return true;
			}
		}
		return false;
	}

	public void ChangingFlagState(int flagstate) { // If Flagstate's activity is
													// changed, then the
													// fragment changes our
													// layout visibility!
		FlagEstateOriginDestiny = flagstate;
		if (flagstate == FLAGSTATEORIGIN) // CHANGE FRAGMENT LAYOUT VISIBLE
			filt_frag.Layout_Visible(FiltersFragment.LAYOUTORIGIN);
		else
			//FLAGSTATEDESTINY
			filt_frag.Layout_Visible(FiltersFragment.LAYOUTDESTINY);
	}	
	
	
	public void AddressByTextview(String address) { // Create or move the marker to the address point!
		try {
			GeneratePointFromAddress(address);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	private void GeneratePointFromAddress(String address) throws Exception {  //-----------------------------------------------------------------------------
		
		GeolocationRequest georeq = new GeolocationRequest(address);
		ShowProgressof(getString(R.string.BuscandoDireccion));
		spiceManager.executeCacheRequest(georeq, new GeoListener(this));
			
	}
		
	private class GeoListener extends BaseNeckRequestListener <LatLng>
	{

		protected Activity activitycore;
		
		public GeoListener(Activity act){ activitycore = act; }
		
		@Override
		public void onRequestException(SpiceException ex)
		{
			ClearInfoFragment();
			ex.printStackTrace();
			Toast.makeText(activitycore, ex.getMessage(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onRequestSuccessful(LatLng point)
		{
			ClearInfoFragment();
			try {
				if (point != null) { // Point have something!
					if (FlagEstateOriginDestiny == FLAGSTATEORIGIN) {
						ChangeCurrentMarker(point, getString(R.string.Origin));
					} else if (FlagEstateOriginDestiny == FLAGSTATEDESTINY) {
						ChangeCurrentMarker(point, getString(R.string.Destination));
					}
				} else
					Toast.makeText(activitycore, getString(R.string.queried_address_failure),Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(activitycore, e.getMessage().toString(),Toast.LENGTH_SHORT).show();
			}
			
		}

		@Override
		public void onRequestError(
				com.globant.roboneck.requests.BaseNeckRequestException.Error error)
		{
			ClearInfoFragment();
			Toast.makeText(activitycore, error.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	/*Simple Route*/
	public void DrawRoute(List<RoutePoint> listPoints,String color,String busname,String streetstart,String streetfinish,double totaldistance,double traveltime){
		
		CleanBusMarks();
		PolylineOptions poly = new PolylineOptions();
		Iterator<RoutePoint> I = listPoints.iterator();
		while (I.hasNext()){
			RoutePoint rp = I.next();
			poly.add(rp.getLatLong());
			if(rp.getIsWaypoint() == false)
				googlemap.addMarker(CreateMarkerOption(rp.getLatLong(), getString(R.string.bus_stop), rp.getAddress(), false, (float)1, R.drawable.paradacomun));

		}
		if(polylineOne != null)
			polylineOne.remove();
		
		int idcol = Color.parseColor("#"+color);
		poly.color(idcol);
		
		RoutePoint firstpoint = listPoints.get(0);
		RoutePoint lastpoint = listPoints.get(listPoints.size() - 1);
		MarkerOptions in = CreateMarkerOption(firstpoint.getLatLong(), getString(R.string.bus_line)+busname, streetstart+"\n"+getString(R.string.line_start), false, (float)0.8, R.drawable.paradaorigen);
		MarkerOptions out = CreateMarkerOption(lastpoint.getLatLong(), getString(R.string.bus_line)+busname, streetfinish+"\n"+getString(R.string.line_end), false, (float)0.8, R.drawable.paradadestino);
		markline1start = googlemap.addMarker(in);
		markline1finish = googlemap.addMarker(out);
		polylineOne = googlemap.addPolyline(poly);
		
	
		InfoRoutesFragment irf = InfoRoutesFragment.getInstance(busname,idcol, String.valueOf(totaldistance), String.valueOf(traveltime),streetstart,streetfinish);
		AddFragToInfoFl(irf);
	}
	
	/*Compound Route*/
	public void DrawRoute(List<RoutePoint> routeOne, List<RoutePoint> routeTwo, String colorOne, String colorTwo,String busname1,String busname2
							,String streetstartbus1,String streetfinishbus1,String streetstartbus2,String streetfinishbus2,double totaldistance,double traveltime){
		
		CleanBusMarks();
		if(polylineOne != null)
			polylineOne.remove();
		if(polylineTwo != null)
			polylineTwo.remove();

		PolylineOptions polyOne = new PolylineOptions();	
		
		Iterator<RoutePoint> iteratorOne = routeOne.iterator();	
		while (iteratorOne.hasNext()){
			RoutePoint rp = iteratorOne.next();
			polyOne.add(rp.getLatLong());
			if(rp.getIsWaypoint() == false)
				googlemap.addMarker(CreateMarkerOption(rp.getLatLong(), getString(R.string.bus_stop), rp.getAddress(), false, (float)1, R.drawable.paradacomun));

		}	
		int idcol1 = Color.parseColor("#"+colorOne);
		polyOne.color(idcol1);		
		
		PolylineOptions polyTwo = new PolylineOptions();
		
		Iterator<RoutePoint> iteratorTwo = routeTwo.iterator();
		while (iteratorTwo.hasNext()){
			RoutePoint rp = iteratorTwo.next();
			polyTwo.add(rp.getLatLong());
			if(rp.getIsWaypoint() == false)
				googlemap.addMarker(CreateMarkerOption(rp.getLatLong(), getString(R.string.bus_stop), rp.getAddress(), false, (float)1, R.drawable.paradacomun));
		}
		int idcol2 = Color.parseColor("#"+colorTwo);
		polyTwo.color(idcol2);
		
		RoutePoint firstpointr1 = routeOne.get(0);
		RoutePoint lastpointr1 = routeOne.get(routeOne.size() - 1);
		RoutePoint firstpointr2 = routeTwo.get(0);
		RoutePoint lastpointr2 = routeTwo.get(routeTwo.size() - 1);
		MarkerOptions in1 = CreateMarkerOption(firstpointr1.getLatLong(), getString(R.string.bus_line)+busname1, streetstartbus1+"\n"+getString(R.string.line_start), false, (float)0.8, R.drawable.paradaorigen);
		MarkerOptions out1 = CreateMarkerOption(lastpointr1.getLatLong(), getString(R.string.bus_line)+busname1, streetfinishbus1+"\n"+getString(R.string.line_end), false, (float)0.8, R.drawable.markerorigentrans01);
		MarkerOptions in2 = CreateMarkerOption(firstpointr2.getLatLong(), getString(R.string.bus_line)+busname2, streetstartbus2+"\n"+getString(R.string.line_start), false, (float)0.8, R.drawable.markerorigentrans02);
		MarkerOptions out2 = CreateMarkerOption(lastpointr2.getLatLong(), getString(R.string.bus_line)+busname2, streetfinishbus2+"\n"+getString(R.string.line_end), false, (float)0.8, R.drawable.paradadestino);
		markline1start = googlemap.addMarker(in1);
		markline1finish = googlemap.addMarker(out1);
		markline2start = googlemap.addMarker(in2);
		markline2finish = googlemap.addMarker(out2);
		
		polylineOne = googlemap.addPolyline(polyOne);
		polylineTwo = googlemap.addPolyline(polyTwo);
		
		
		InfoRoutesFragment irf = InfoRoutesFragment.getInstance(busname1,idcol1, busname2,idcol2, String.valueOf(totaldistance), String.valueOf(traveltime),
																streetstartbus1,streetfinishbus1,streetstartbus2,streetfinishbus2);
		AddFragToInfoFl(irf);
	}
	
	
	public void CleanBusMarks() { //remove marks and polylines!
		markline1start = null;
		markline1finish = null;
		markline2start = null;
		markline2finish = null;
		polylineOne = null;
		polylineTwo = null;
		ClearInfoFragment();
		googlemap.clear();
		markOrigin = googlemap.addMarker(CreateMarkerOption(new LatLng(markOrigin.getPosition().latitude, markOrigin.getPosition().longitude),
				markOrigin.getTitle(), markOrigin.getSnippet(), false, (float)0.8, R.drawable.markerorigen));
		markDestiny = googlemap.addMarker(CreateMarkerOption(new LatLng(markDestiny.getPosition().latitude, markDestiny.getPosition().longitude),
				markDestiny.getTitle(), markDestiny.getSnippet(), false, (float)0.8, R.drawable.markerdestino));
	}
	 
	@Override
	protected void onStart() {
		spiceManager.start(this);
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		spiceManager.shouldStop();
		super.onStop();
	}
	
	public void ShowProgressof(String message){
		LoadingProgressFragment lpf = LoadingProgressFragment.getInstance(message);
		ScreenManager.showFragInFrameLayout(this, lpf, R.id.fl_info);
	}
	public void ClearInfoFragment(){
		frameLayoutInfo.removeAllViews();
	}
	public void AddFragToInfoFl(Fragment frag){
		ScreenManager.showFragInFrameLayout(this, frag, R.id.fl_info);
	}
	
	public void SwitchPointers(){
		MarkerOptions markoriginoptaux = CreateMarkerOption(markOrigin.getPosition(), markOrigin.getTitle(), markOrigin.getSnippet(), false, (float)0.8, R.drawable.markerorigen);
		googlemap.clear();
		markOrigin = googlemap.addMarker(CreateMarkerOption(markDestiny.getPosition(), markDestiny.getTitle(), markDestiny.getSnippet(), false, (float)0.8, R.drawable.markerorigen));
		filt_frag.Edit_text_origin(markOrigin.getSnippet());
		markDestiny = googlemap.addMarker(CreateMarkerOption(markoriginoptaux.getPosition(), markoriginoptaux.getTitle(), markoriginoptaux.getSnippet(), false, (float)0.8, R.drawable.markerdestino));
		filt_frag.Edit_text_destiny(markDestiny.getSnippet());
		MarkersSearchedFalse();
		ClearInfoFragment();
	}

	public String getShadowtextviewOrigin() {
		return shadowtextviewOrigin;
	}

	public void setShadowtextviewOrigin(String shadowtextviewOrigin) {
		this.shadowtextviewOrigin = shadowtextviewOrigin;
	}

	public String getShadowtextviewDestination() {
		return shadowtextviewDestination;
	}

	public void setShadowtextviewDestination(String shadowtextviewDestination) {
		this.shadowtextviewDestination = shadowtextviewDestination;
	}

	
}