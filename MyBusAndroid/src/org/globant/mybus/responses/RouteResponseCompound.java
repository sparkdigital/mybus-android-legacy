package org.globant.mybus.responses;

import java.util.ArrayList;
import java.util.List;

import org.globant.models.RoutePoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 * Constructed using the following online tools:
 * http://www.jsonschema2pojo.org/
 * http://www.jsonschema.net/#
 */

public class RouteResponseCompound extends RouteResponse
{

	/**
* 
*/
	@SerializedName("IdBusLine1")
	@Expose
	private String idBusLine1;
	/**
* 
*/
	@SerializedName("IdBusLine2")
	@Expose
	private String idBusLine2;
	/**
* 
*/
	@SerializedName("Route1")
	@Expose
	private List<RoutePoint> route1Points = new ArrayList<RoutePoint>();
	/**
* 
*/
	@SerializedName("Route2")
	@Expose
	private List<RoutePoint> route2Points = new ArrayList<RoutePoint>();
	/**
* 
*/
	@SerializedName("Type")
	@Expose
	private Integer type;

	/**
* 
*/
	public String getIdBusLine1()
	{
		return idBusLine1;
	}

	/**
* 
*/
	public void setIdBusLine1(String idBusLine1)
	{
		this.idBusLine1 = idBusLine1;
	}

	/**
* 
*/
	public String getIdBusLine2()
	{
		return idBusLine2;
	}

	/**
* 
*/
	public void setIdBusLine2(String idBusLine2)
	{
		this.idBusLine2 = idBusLine2;
	}

	/**
* 
*/
	public List<RoutePoint> getRoute1Points()
	{
		return route1Points;
	}

	/**
* 
*/
	public void setRoute1Points(List<RoutePoint> route1Points)
	{
		this.route1Points = route1Points;
	}

	/**
* 
*/
	public List<RoutePoint> getRoute2Points()
	{
		return route2Points;
	}

	/**
* 
*/
	public void setRoute2Points(List<RoutePoint> route2Points)
	{
		this.route2Points = route2Points;
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

//import java.util.List;
//
//import org.globant.models.RoutePoint;
//
//import com.google.gson.annotations.SerializedName;
//
//public class RouteResponseCompound extends RouteResponseBase
//{
//	@SerializedName("IdBusLine1")
//	public int idLine1;
//	
//	@SerializedName("IdBusLine2")
//	public int idLine2;
//	
//	@SerializedName("Route1")
//	public List<RoutePoint> routeOne;
//	
//	@SerializedName("Route2")
//	public List<RoutePoint> routeTwo;
//}
