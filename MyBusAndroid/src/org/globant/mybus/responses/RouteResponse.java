package org.globant.mybus.responses;

public class RouteResponse
{
	public double TotalDistance, TravelTime, ArrivalTime;

	public RouteResponse()
	{
		super();
	}

	public double getTotalDistance()
	{
		return TotalDistance;
	}

	public void setTotalDistance(double totalDistance)
	{
		TotalDistance = totalDistance;
	}

	public double getTravelTime()
	{
		return TravelTime;
	}

	public void setTravelTime(double travelTime)
	{
		TravelTime = travelTime;
	}

	public double getArrivalTime()
	{
		return ArrivalTime;
	}

	public void setArrivalTime(double arrivalTime)
	{
		ArrivalTime = arrivalTime;
	}

}
