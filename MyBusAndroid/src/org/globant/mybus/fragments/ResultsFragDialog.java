package org.globant.mybus.fragments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.globant.models.CompoundResult;
import org.globant.models.Result;
import org.globant.models.RoutePoint;
import org.globant.models.SimpleResult;
import org.globant.mybus.R;
import org.globant.mybus.activities.MapActivity;
import org.globant.mybus.adapters.PointsAdapter;
import org.globant.mybus.adapters.ResultsAdapter;
import org.globant.mybus.requests.MultipleRouteRequest;
import org.globant.mybus.requests.NexusRequest;
import org.globant.mybus.requests.SingleRouteRequest;
import org.globant.mybus.responses.NexusResponse;
import org.globant.mybus.responses.NexusResponseCompound;
import org.globant.mybus.responses.NexusResponseSimple;
import org.globant.mybus.responses.RouteResponseCompound;
import org.globant.mybus.responses.RouteResponseSimple;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.globant.roboneck.common.NeckSpiceManager;
import com.globant.roboneck.common.NeckSpiceManager.ProgressListener;
import com.globant.roboneck.requests.BaseNeckRequestListener;
import com.google.android.gms.maps.model.LatLng;
import com.octo.android.robospice.persistence.exception.SpiceException;

public class ResultsFragDialog extends DialogFragment implements
		AdapterView.OnItemClickListener, OnRefreshListener, ProgressListener {

	private Activity activitycore;
	private ListView lv_results;
	private ResultsAdapter adapter;
	protected NeckSpiceManager spiceManager = new NeckSpiceManager();
	private NexusRequest nx;
	protected SwipeRefreshLayout syncProgressBar;
	protected boolean isShowingProgress;
	public PointsAdapter pointsAdapter;
	private Result result;
	protected ProgressBar progressBar;
	protected LinearLayout layout_loadingroute;
	protected TextView tv_noresults;
	protected RelativeLayout relative_base;

	public ResultsFragDialog() { /* Empty constructor */
	}

	private void setActivityFather(Activity act) {
		activitycore = act;
	}

	public static ResultsFragDialog getInstance(Activity act) {
		ResultsFragDialog rfd = new ResultsFragDialog();
		rfd.setActivityFather(act);
		return rfd;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragdialog_results, container,false);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		relative_base = (RelativeLayout)view.findViewById(R.id.relative_base);
		tv_noresults = (TextView)view.findViewById(R.id.tv_noresults);
		layout_loadingroute = (LinearLayout)view.findViewById(R.id.layout_loadingroute);
		progressBar = (ProgressBar)view.findViewById(R.id.pb_loading);
		lv_results = (ListView) view.findViewById(R.id.lv_results);

		LatLng org = ((MapActivity) activitycore).getLatLongMarkOrigin();
		LatLng des = ((MapActivity) activitycore).getLatLngMarkDestiny();

		nx = new NexusRequest(org.latitude, org.longitude, des.latitude,des.longitude); // give 2 points
		if(((MapActivity)activitycore).FlagMarkersSearched() == true){
			spiceManager.executeCacheRequest(nx, new StationsListener()); //Use cache memory!  (Adapter of listview is generated here!)
		}else{
			showProgress();
			spiceManager.forceExecuteRequest(nx, new StationsListener()); //Forced request!! no cache memory!  (Adapter of listview is generated here!)
		}
		lv_results.setOnItemClickListener(this);
		((MapActivity)activitycore).MarkersSearchedTrue();  //change flag searched to true!
		
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//TODO
		if(adapter.getFlagsimpleorcompound() == ResultsAdapter.SIMPLE_RESULT){
			SimpleResult sr = (SimpleResult)adapter.getItem(position);
			result = sr;
			SingleRouteRequest srq = new SingleRouteRequest(sr.getIdBusLine(), sr.getBusLineDirection(), sr.getStartBusStopNumber(), sr.getDestinationBusStopNumber());
			ShowLoading();
			spiceManager.executeCacheRequest(srq, new SimpleRouteListener());
		} else
			if(adapter.getFlagsimpleorcompound() == ResultsAdapter.COMPOUND_RESULT){
				CompoundResult cr = (CompoundResult)adapter.getItem(position);
				result = cr;
				MultipleRouteRequest mrq = new MultipleRouteRequest(cr.getLine1Id(), cr.getLine2Id(), cr.getLine1Direction(),
																	cr.getLine2Direction(), cr.getLine1StartBusStopId(), cr.getLine1EndBusStopId(),
																	cr.getLine2StartBusStopId(), cr.getLine2EndBusStopId());
				ShowLoading();
				spiceManager.executeCacheRequest(mrq, new CompoundRouteListener());
			}
	}

	private class StationsListener extends BaseNeckRequestListener<NexusResponse> {

		@Override
		public void onRequestException(SpiceException ex) {
			hideProgress();
			ex.printStackTrace();
		}

		@Override
		public void onRequestSuccessful(NexusResponse response) {
			hideProgress();
			List<Result> l = loadResults(response);
			if (l.isEmpty() == false) { // Have results
				adapter = new ResultsAdapter(activitycore, l);
				lv_results.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				lv_results.setVisibility(View.VISIBLE);
			}else{ //No results!
				NoResults();
			}
		}

		@Override
		public void onRequestError(
				com.globant.roboneck.requests.BaseNeckRequestException.Error error)
		{
			hideProgress();
			Toast.makeText(activitycore, "NOT FOUND", Toast.LENGTH_SHORT).show();
		}

		private List<Result> loadResults(NexusResponse response) {

			List<Result> ls = new ArrayList<Result>();

			if (response instanceof NexusResponseSimple) {
				NexusResponseSimple nrs = (NexusResponseSimple) response;

				Iterator<SimpleResult> I = nrs.Results.iterator();

				while (I.hasNext()) {
					SimpleResult s = I.next();

					ls.add(s);
				}

			} else if (response instanceof NexusResponseCompound) {
				NexusResponseCompound nrc = (NexusResponseCompound) response;

				Iterator<CompoundResult> I = nrc.Results.iterator();

				while (I.hasNext()) {
					CompoundResult c = I.next();

					ls.add(c);
				}
			}
			return ls;
		}
	}

	private class SimpleRouteListener extends
			BaseNeckRequestListener<RouteResponseSimple> {
		@Override
		public void onRequestException(SpiceException ex) {
			hideProgress();
			HideLoading();
			ex.printStackTrace();
		}

		@Override
		public void onRequestSuccessful(RouteResponseSimple response) {
			hideProgress();
			HideLoading();
			List<RoutePoint> l = response.getRoutePoints();
			((MapActivity)activitycore).DrawRoute(l,((SimpleResult)result).getBusLineColor(),((SimpleResult)result).getBusLineName(),
													((SimpleResult)result).getStartBusStopStreetName()+" "+((SimpleResult)result).getStartBusStopStreetNumber(),
													((SimpleResult)result).getDestinatioBusStopStreetName()+" "+((SimpleResult)result).getDestinatioBusStopStreetNumber(),response.getTotalDistance(),response.getTravelTime());
			Dismiss();
		}

		@Override
		public void onRequestError(
				com.globant.roboneck.requests.BaseNeckRequestException.Error error)
		{
			hideProgress();
			HideLoading();
			Toast.makeText(activitycore, error.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	private class CompoundRouteListener extends 
		BaseNeckRequestListener<RouteResponseCompound> {
		@Override
		public void onRequestException(SpiceException ex) {
			hideProgress();
			HideLoading();
			ex.printStackTrace();
		}

		@Override
		public void onRequestSuccessful(RouteResponseCompound response) {
			hideProgress();
			HideLoading();
			ArrayList<RoutePoint> lOne = new ArrayList<RoutePoint>();
			ArrayList<RoutePoint> lTwo = new ArrayList<RoutePoint>();

			lOne.addAll(response.getRoute1Points());
			lTwo.addAll(response.getRoute2Points());
			((MapActivity)activitycore).DrawRoute(lOne,lTwo,((CompoundResult)result).line1Color,((CompoundResult)result).line2Color,
															((CompoundResult)result).busLine1,((CompoundResult)result).busLine2,
															((CompoundResult)result).getLine1StartStreet()+" "+((CompoundResult)result).getLine1StartStreetNumber(),
															((CompoundResult)result).getLine1EndStreet()+" "+((CompoundResult)result).getLine1EndStreetNumber(),
															((CompoundResult)result).getLine2StartStreet()+" "+((CompoundResult)result).getLine2StartStreetNumber(),
															((CompoundResult)result).getLine2EndStreet()+" "+((CompoundResult)result).getLine2EndStreetNumber(),
															response.getTotalDistance(),response.getTravelTime());
			Dismiss();
		}

		@Override
		public void onRequestError(
				com.globant.roboneck.requests.BaseNeckRequestException.Error error)
		{
			hideProgress();
			HideLoading();
			Toast.makeText(activitycore, error.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onStart() {
		spiceManager.start(activitycore);
		super.onStart();
	}

	@Override
	public void onStop() {
		spiceManager.shouldStop();
		super.onStop();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isShowingProgress) {
			showProgress();
		}
	}

	protected void showProgress() {
		isShowingProgress = true;
		progressBar.setVisibility(View.VISIBLE);
	}

	protected void hideProgress() {
		isShowingProgress = false;
		progressBar.setVisibility(View.GONE);
	}

	@Override
	public void onShowProgress() {
		showProgress();
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
	}

	public void Dismiss(){
		this.dismiss();
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
	}
	
	public void ShowLoading(){
		layout_loadingroute.setVisibility(View.VISIBLE);
	}
	public void HideLoading(){
		layout_loadingroute.setVisibility(View.GONE);
	}
	
	public void NoResults(){
		tv_noresults.setVisibility(View.VISIBLE);
	}
}
