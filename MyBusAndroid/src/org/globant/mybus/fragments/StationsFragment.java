package org.globant.mybus.fragments;

import java.util.ArrayList;
import java.util.List;

import org.globant.models.Station;
import org.globant.mybus.Constants;
import org.globant.mybus.LocationTools;
import org.globant.mybus.R;
import org.globant.mybus.activities.Locator;
import org.globant.mybus.activities.MapActivity;
import org.globant.mybus.adapters.StationsAdapter;
import org.globant.mybus.requests.StationsRequest;
import org.globant.mybus.responses.StationResponse;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.globant.roboneck.requests.BaseNeckRequestListener;
import com.globant.roboneckUI.base.BaseFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.octo.android.robospice.persistence.exception.SpiceException;

public class StationsFragment extends BaseFragment implements
		GooglePlayServicesClient.OnConnectionFailedListener,
		GooglePlayServicesClient.ConnectionCallbacks, LocationListener
{
	static public int ID_STATION = 1;

	private static SupportMapFragment mapFragment;
	Locator locator;
	protected StationsRequest stationsRequest;
	// Button btnShowOnMap;
	StationsAdapter adapter;
	ListView lstStations;
	// DBSystem_locations slDBA;
	private ProgressBar loadingbar_map, loadingbar_list;

	// "Dis 'ere fieldz be used on dat new lokayshun fing, boss."
	private static GoogleMap mMap;
	private LatLng latlng;
	private LocationRequest lr;
	private LocationClient lc;

	public LatLng getCurrentLocation()
	{
		LatLng point = LocationTools.CurrentLocation(getBaseActivity());

		if (latlng == null && point == null)
		{
			point = new LatLng(Constants.CAM_START_LATITUDE,
					Constants.CAM_START_LONGITUDE);
		}
		else if (point == null)
		{
			point = latlng;
		}

		return point;
	}

	@Override
	public int getFragmentLayoutForCreateView()
	{
		return R.layout.fragment_stations;
	}

	public GoogleMap getMapInstance()
	{
		if (mapFragment == null || mMap == null)
		{
			mapFragment = (SupportMapFragment) getActivity()
					.getSupportFragmentManager().findFragmentById(R.id.map);

			mMap = mapFragment.getMap();
		}

		return mMap;
	}

	public void hideProgressList()
	{
		if (loadingbar_list != null) loadingbar_list.setVisibility(View.GONE);
	}

	public void hideProgressMap()
	{
		if (loadingbar_map != null) loadingbar_map.setVisibility(View.GONE);
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();

		if (mMap != null)
		{
			try
			{
				getActivity()
						.getSupportFragmentManager()
						.beginTransaction()
						.remove(getActivity().getSupportFragmentManager()
								.findFragmentById(R.id.map)).commit();
				mMap = null;
			}
			catch (Exception e)
			{

			}
		}

	}

	@Override
	public void onRefresh()
	{
	}

	@Override
	public void onCreatedNeckView(View inflatedView)
	{
		disableRefreshSwipe();
		// slDBA = new DBSystem_locations(getBaseActivity());

		locator = (Locator) getBaseActivity();

		loadingbar_map = (ProgressBar) inflatedView
				.findViewById(R.id.loadingbar_map);
		loadingbar_list = (ProgressBar) inflatedView
				.findViewById(R.id.loadingbar_list);

		lstStations = (ListView) inflatedView.findViewById(R.id.list);

		/**
		 * Stuff until next blue comment block is used by new fused location
		 * provider. Source: stackoverflow.com/questions/18532581
		 */

		lr = LocationRequest.create();
		lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		lc = new LocationClient(this.getBaseActivity().getApplicationContext(),
				this, this);
		lc.connect();

		/**
		 * End of new fused location provider stuff.
		 */
		
		stationsRequest = new StationsRequest(getCurrentLocation());

		spiceManager.executeCacheRequest(stationsRequest,
				new StationsListener());

		getBaseActivity().setTitle(R.string.closest_recharge_points);

		try
		{
			setUpMapIfNeeded();

		}
		catch (Exception e)
		{
			e.printStackTrace();
			// initializeMap();
		}
	}

	public void startMapActivity(int mode)
	{
		Intent intent = new Intent(this.getBaseActivity(), MapActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("request_type", mode);
		bundle.putParcelableArrayList("Stations",
				(ArrayList<Station>) adapter.getItems());
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private boolean setUpMapIfNeeded()
	{
		boolean success = true;

		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null)
		{

			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getBaseActivity()
					.getSupportFragmentManager().findFragmentById(R.id.map))
					.getMap();

			if (mMap != null)
			{
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
						getCurrentLocation(), 14)); // 15

				// Check if we were successful in obtaining the map.
				if (mMap != null)
				{
					setUpMap();
				}
				else
				{
					success = false;
					int isEnabled = GooglePlayServicesUtil
							.isGooglePlayServicesAvailable(getBaseActivity());
					if (isEnabled != ConnectionResult.SUCCESS)
					{
						GooglePlayServicesUtil.getErrorDialog(isEnabled,
								getBaseActivity(), 0);
					}
				}
			}
		}
		return success;
	}

	private void setUpMap()
	{
		if (mMap != null)
		{
			mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
			{
				@Override
				public void onInfoWindowClick(Marker arg0)
				{
					// Log.i(TAG, "User will now navigate to Wikipedia's page: "
					// + marker.getTitle());
					// startActivity(new Intent(Intent.ACTION_VIEW, Uri
					// .parse(wikipediaService.getPageUrl(marker.getTitle()))));
				}
			});
		}
	}

	private class StationsListener extends
			BaseNeckRequestListener<StationResponse>
	{

		@Override
		public void onRequestException(SpiceException ex)
		{
			hideProgressMap();
			hideProgressList();
			ex.printStackTrace();
		}

		@Override
		public void onRequestSuccessful(StationResponse response)
		{
			hideProgressMap();
			hideProgressList();
			List<Station> l = new ArrayList<Station>();

			l.addAll(response.results);

			adapter = new StationsAdapter(getBaseActivity(),
					R.layout.list_station_row, l);

			lstStations.setAdapter(adapter);

			if (mMap != null)
			{
				placeMarkers();
			}

		}

		@Override
		public void onRequestError(
				com.globant.roboneck.requests.BaseNeckRequestException.Error error)
		{
			hideProgressMap();
			hideProgressList();
			Toast.makeText(getBaseActivity(), error.getMessage().toString(),
					Toast.LENGTH_SHORT).show();
		}

		public MarkerOptions createMarkerOption(LatLng point, String title,
				String snippet, boolean draggable, float alpha, int id_drawable)
		{
			if (point.latitude >= Constants.MARK_MIN_LATITUDE
					&& point.latitude <= Constants.MARK_MAX_LATITUDE)
			{
				if (point.longitude >= Constants.MARK_MIN_LONGITUDE
						&& point.longitude <= Constants.MARK_MAX_LONGITUDE)
				{
					MarkerOptions marker = new MarkerOptions().position(point)
							.title(title).snippet(snippet).draggable(draggable)
							.alpha(alpha);

					if (id_drawable != 0) // If load a dreawable or not
					{
						marker.icon(BitmapDescriptorFactory
								.fromResource(id_drawable));
					}
					return marker;
				}
			}

			Toast.makeText(getBaseActivity(),
					getString(R.string.PuntoFueraDeRango), Toast.LENGTH_SHORT)
					.show();

			return null; // Return null if its out of range
		}

		private void placeMarkers()
		{
			if (mMap != null)
			{
				mMap.setMyLocationEnabled(false);
				ArrayList<Station> stations = (ArrayList<Station>) adapter
						.getItems();

				MarkerOptions mypos = createMarkerOption(getCurrentLocation(),
						getString(R.string.Yo),
						getString(R.string.MiPosicionActual), false, (float) 1,
						R.drawable.marker_icon_me);

				for (int i = 0; i < stations.size(); i++)
				{
					Station stat = stations.get(i);

					MarkerOptions markop = createMarkerOption(stat.getLatLng(),
							stat.getAddress(),
							"Horario: " + stat.getAvailability(), false,
							(float) 1, R.drawable.puntodecarga);

					mMap.addMarker(markop);
				}
				Marker mark = mMap.addMarker(mypos);
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
						getCurrentLocation(), 14));
				mark.showInfoWindow();
			}
		}
	}

	/**
	 * METHODS BELOW ARE USED BY NEW FUSED LOCATION PROVIDER STUFF!
	 */

	@Override
	public void onLocationChanged(Location location)
	{
		latlng = new LatLng(location.getLatitude(), location.getLongitude());
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlng, 14);
		mMap.animateCamera(update);
	}

	@Override
	public void onConnected(Bundle arg0)
	{
		lc.requestLocationUpdates(lr, this);
	}

	@Override
	public void onDisconnected()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0)
	{
		// TODO Auto-generated method stub

	}
}