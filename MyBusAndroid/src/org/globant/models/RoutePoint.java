package org.globant.models;

//import com.google.gson.annotations.SerializedName;
//
//public class RoutePoint //extends RouteResponseBase
//{
//	@SerializedName("StopId")
//	public int stopId;
//	
//	public double Lat, Lng;
//	
//	public String Address;
//	
//	public boolean isWaypoint;
//}

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutePoint {

	/**
* 
*/
	@SerializedName("Address")
	@Expose
	private String address;
	/**
* 
*/
	@SerializedName("Lat")
	@Expose
	private Double lat;
	/**
* 
*/
	@SerializedName("Lng")
	@Expose
	private Double lng;
	/**
* 
*/
	@SerializedName("StopId")
	@Expose
	private Integer stopId;
	/**
* 
*/
	@Expose
	private Boolean isWaypoint;

	/**
* 
*/
	public String getAddress() {
		return address;
	}

	/**
* 
*/
	public void setAddress(String address) {
		this.address = address;
	}

	/**
* 
*/
	public Double getLat() {
		return lat;
	}

	/**
* 
*/
	public void setLat(Double lat) {
		this.lat = lat;
	}

	/**
* 
*/
	public Double getLng() {
		return lng;
	}

	/**
* 
*/
	public void setLng(Double lng) {
		this.lng = lng;
	}

	/**
* 
*/
	public Integer getStopId() {
		return stopId;
	}

	/**
* 
*/
	public void setStopId(Integer stopId) {
		this.stopId = stopId;
	}

	/**
* 
*/
	public Boolean getIsWaypoint() {
		return isWaypoint;
	}

	/**
* 
*/
	public void setIsWaypoint(Boolean isWaypoint) {
		this.isWaypoint = isWaypoint;
	}

	public LatLng getLatLong(){
		return new LatLng(getLat(), getLng());
	}
}