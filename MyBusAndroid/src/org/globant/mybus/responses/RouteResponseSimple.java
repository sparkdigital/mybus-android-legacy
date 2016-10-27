package org.globant.mybus.responses;

//import java.util.List;
//
//import org.globant.models.RoutePoint;
//
//import com.google.gson.annotations.SerializedName;
//
//public class RouteResponseSimple //extends RouteResponseBase
//{
//	public int Type;
//
//	@SerializedName("Route1")
//	public List<RoutePoint> route;
//}

import java.util.ArrayList;
import java.util.List;

import org.globant.models.RoutePoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RouteResponseSimple extends RouteResponse
{
	/**
* 
*/
	@SerializedName("Route1")
	@Expose
	private List<RoutePoint> routePoints = new ArrayList<RoutePoint>();
	/**
* 
*/
	@SerializedName("Type")
	@Expose
	private Integer type;

	/**
* 
*/
	public List<RoutePoint> getRoutePoints()
	{
		return routePoints;
	}

	/**
* 
*/
	public void setRoutePoints(List<RoutePoint> routePoints)
	{
		this.routePoints = routePoints;
	}

	/**
* 
*/
	public Integer getType()
	{
		return type;
	}

	/**
* 
*/
	public void setType(Integer type)
	{
		this.type = type;
	}

}