package org.globant.models;

public class BusStop extends Location
{
	private int busStopNumber;
	
	private double distance;

	public BusStop() {}
	
	public BusStop(int id, double latitude, double longitude, String street, int search_count, String last_search,
			int iscritical, int isfavorite, String observations, double distance, int busStopNumber)
	{
		super(id, latitude, longitude, street, search_count,
				last_search, iscritical, isfavorite, observations);
		
		this.setDistance(distance);
		this.setBusStopNumber(busStopNumber);
	}

	public double getDistance()
	{
		return distance;
	}

	public void setDistance(double distance)
	{
		this.distance = distance;
	}

	public int getBusStopNumber()
	{
		return busStopNumber;
	}

	public void setBusStopNumber(int busStopNumber)
	{
		this.busStopNumber = busStopNumber;
	}

}
