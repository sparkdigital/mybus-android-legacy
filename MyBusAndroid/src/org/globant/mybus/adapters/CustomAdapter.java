package org.globant.mybus.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class CustomAdapter<T> extends BaseAdapter
{
	protected Activity activity;
	protected List<T> items;
	protected static LayoutInflater inflater = null;
	protected int layoutResID;

	public CustomAdapter(Activity a, int layoutResID)
	{
		this.activity = a;

		this.layoutResID = layoutResID;

		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}
	
	public CustomAdapter()
	{
		super();
	}

	@Override
	public int getCount()
	{
		return this.items.size();
	}

	@Override
	public Object getItem(int position)
	{
		return this.items.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}
	
	public List<T> getItems()
	{
		return items;
	}

	abstract protected void onItemClickedEvent();
}