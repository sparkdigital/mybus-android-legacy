package org.globant.mybus.fragments;

import java.util.ArrayList;
import java.util.List;

import org.globant.models.RoutePoint;
import org.globant.mybus.R;
import org.globant.mybus.adapters.PointsAdapter;
import org.globant.mybus.requests.MultipleRouteRequest;
import org.globant.mybus.responses.RouteResponse;
import org.globant.mybus.responses.RouteResponseCompound;
import org.globant.mybus.responses.RouteResponseSimple;

import android.view.View;
import android.widget.ListView;

import com.globant.roboneck.requests.BaseNeckRequestException.Error;
import com.globant.roboneck.requests.BaseNeckRequestListener;
import com.globant.roboneckUI.base.BaseFragment;
import com.octo.android.robospice.persistence.exception.SpiceException;

public class BusRoutesFragment extends BaseFragment
{
	PointsAdapter adapter;

	ListView lstPoints;

	@Override
	public void onRefresh()
	{

	}

	@Override
	public int getFragmentLayoutForCreateView()
	{
		return R.layout.fragment_routes;
	}

	@Override
	public void onCreatedNeckView(View inflatedView)
	{
		disableRefreshSwipe();

		lstPoints = (ListView) inflatedView.findViewById(R.id.list);

		MultipleRouteRequest multipleRouteRequest = new MultipleRouteRequest(2,
				5, 0, 0, 107, 114, 92, 129);

		spiceManager.executeCacheRequest(multipleRouteRequest,
				new CompoundRouteListener());

		// SingleRouteRequest singleRouteRequest = new SingleRouteRequest(1, 0,
		// 24, 39);
		//
		// spiceManager.executeCacheRequest(singleRouteRequest,
		// new SimpleRouteListener());

	}

	@SuppressWarnings("unused")
	private class RouteListener extends BaseNeckRequestListener<RouteResponse>
	{
		@Override
		public void onRequestError(Error error)
		{
			hideProgress();
		}

		@Override
		public void onRequestSuccessful(RouteResponse response)
		{
			hideProgress();

			if (response instanceof RouteResponseSimple)
			{
				List<RoutePoint> l = ((RouteResponseSimple) response)
						.getRoutePoints();

				adapter = new PointsAdapter(getBaseActivity(),
						R.layout.list_point_row, l);
			}
			else if (response instanceof RouteResponseCompound)
			{
				ArrayList<RoutePoint> l = new ArrayList<RoutePoint>();

				l.addAll(((RouteResponseCompound) response).getRoute1Points());
				l.addAll(((RouteResponseCompound) response).getRoute2Points());

				adapter = new PointsAdapter(getBaseActivity(),
						R.layout.list_point_row, l);
			}

			lstPoints.setAdapter(adapter);
		}

		@Override
		public void onRequestException(SpiceException exception)
		{
			hideProgress();
			exception.printStackTrace();
		}
	}

	// TODO Determine the actual worth of trying to implement this.
	/*
	 * private class RouteListenerOld PendingRequestListener<RouteResponse> {
	 * 
	 * public void onRequestFailure(SpiceException ex) { hideProgress();
	 * ex.printStackTrace(); }
	 * 
	 * public void onRequestSuccess(RouteResponse response) { hideProgress();
	 * 
	 * if (response instanceof RouteResponseSimple) { List<RoutePoint> l =
	 * ((RouteResponseSimple) response) .getRoutePoints();
	 * 
	 * adapter = new PointsAdapter(getBaseActivity(), R.layout.list_point_row,
	 * l); } else if (response instanceof RouteResponseCompound) {
	 * ArrayList<RoutePoint> l = new ArrayList<RoutePoint>();
	 * 
	 * l.addAll(((RouteResponseCompound) response).getRoute1Points());
	 * l.addAll(((RouteResponseCompound) response).getRoute2Points());
	 * 
	 * adapter = new PointsAdapter(getBaseActivity(), R.layout.list_point_row,
	 * l); }
	 * 
	 * // loadResults(l, response);
	 * 
	 * // TODO Code a new adapter for this // adapter = new
	 * StationsAdapter(getBaseActivity(), // R.layout.list_station_row, l);
	 * 
	 * lstPoints.setAdapter(adapter); }
	 * 
	 * public void onRequestNotFound() { hideProgress(); }
	 * 
	 * }
	 */

	@SuppressWarnings("unused")
	private class SimpleRouteListener extends
			BaseNeckRequestListener<RouteResponseSimple>
	{
		@Override
		public void onRequestException(SpiceException ex)
		{
			hideProgress();
			ex.printStackTrace();
		}

		@Override
		public void onRequestSuccessful(RouteResponseSimple response)
		{
			hideProgress();

			List<RoutePoint> l = response.getRoutePoints();

			// loadResults(l, response);

			adapter = new PointsAdapter(getBaseActivity(),
					R.layout.list_point_row, l);

			lstPoints.setAdapter(adapter);
		}

		@Override
		public void onRequestError(Error error)
		{
			hideProgress();
		}
	}

	private class CompoundRouteListener extends
			BaseNeckRequestListener<RouteResponseCompound>
	{
		@Override
		public void onRequestException(SpiceException exception)
		{
			hideProgress();
		}

		@Override
		public void onRequestSuccessful(RouteResponseCompound response)
		{
			hideProgress();

			ArrayList<RoutePoint> l = new ArrayList<RoutePoint>();

			l.addAll(response.getRoute1Points());
			l.addAll(response.getRoute2Points());

			adapter = new PointsAdapter(getBaseActivity(),
					R.layout.list_point_row, l);

			lstPoints.setAdapter(adapter);
		}

		@Override
		public void onRequestError(Error error)
		{
			hideProgress();
		}

		// private void loadResults(List<RoutePoint> l,
		// RouteResponseCompound response)
		// {
		// adapter = new PointsAdapter(getBaseActivity(),
		// R.layout.list_point_row, response.getRoutePoints());
		// }
	}

}
