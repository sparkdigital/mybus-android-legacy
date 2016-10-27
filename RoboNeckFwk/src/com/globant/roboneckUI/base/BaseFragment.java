package com.globant.roboneckUI.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.globant.roboneck.R;
import com.globant.roboneck.common.NeckSpiceManager;
import com.globant.roboneck.common.NeckSpiceManager.ProgressListener;

public abstract class BaseFragment extends Fragment implements
		OnRefreshListener, ProgressListener
{

	protected SwipeRefreshLayout syncProgressBar;
	protected NeckSpiceManager spiceManager = new NeckSpiceManager();

	protected boolean isShowingProgress;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		spiceManager = new NeckSpiceManager(this);
		if (savedInstanceState != null)
		{
			isShowingProgress = savedInstanceState
					.getBoolean("isShowingProgress");
		}
	}

	@Override
	public void onStart()
	{
		spiceManager.start(this.getActivity());
		super.onStart();
	}

	@Override
	public void onStop()
	{
		spiceManager.shouldStop();
		super.onStop();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		if (isShowingProgress)
		{
			showProgress();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		outState.putBoolean("isShowingProgress", isShowingProgress);
		super.onSaveInstanceState(outState);
	}

	/**
	 * Shows {@link SwipeRefreshLayout} on top of the screen. Override this
	 * method to provide additional actions but remember to call super.
	 */
	protected void showProgress()
	{
		isShowingProgress = true;
		syncProgressBar.setRefreshing(isShowingProgress);
	}

	/**
	 * Hides {@link SwipeRefreshLayout}.Override this method to provide
	 * additional actions but remember to call super.
	 */
	protected void hideProgress()
	{
		isShowingProgress = false;
		syncProgressBar.setRefreshing(isShowingProgress);
	}

	/**
	 * Call this on your fragment's {@link #onActivityCreated(Bundle)} if you
	 * want to disable swipe refresh gesture.
	 */
	protected void disableRefreshSwipe()
	{
		syncProgressBar.setEnabled(false);
	}

	/**
	 * Enables swipe refresh gesture.
	 */
	protected void enableSwipeGesture()
	{
		syncProgressBar.setEnabled(true);
	}

	@Override
	public final View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState)
	{
		ViewGroup baseView = (ViewGroup) inflater.inflate(R.layout.fragment_base, container,
				false);
		
		View instanceView = inflater.inflate(getFragmentLayoutForCreateView(),
				baseView, true);
		
		syncProgressBar = (SwipeRefreshLayout) baseView
				.findViewById(R.id.swipe_container);
		
		syncProgressBar.setOnRefreshListener(this);
		
		onCreatedNeckView(instanceView);
		
		return baseView; //instanceView;
	}

	public abstract int getFragmentLayoutForCreateView();

	public abstract void onCreatedNeckView(View inflatedView);

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		// syncProgressBar.setColorScheme(R.color.category2, R.color.category3,
		// R.color.category4, R.color.category5);
	}

	protected BaseActivity getBaseActivity()
	{
		try
		{
			return (BaseActivity) getActivity();
		}
		catch (ClassCastException ex)
		{
			throw new ClassCastException(
					"Base fragments must be attached to BaseActivities");
		}
	}

	@Override
	public void onShowProgress()
	{
		showProgress();
	}

}
