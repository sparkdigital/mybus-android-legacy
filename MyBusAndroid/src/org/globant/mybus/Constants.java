package org.globant.mybus;

public class Constants {

	public static final boolean TEST = false;

	public static final String DOMAIN = (TEST) ? "http://www.testing.mybus.com.ar/api/v1/" : "http://www.mybus.com.ar/api/v1/";
	public static final String GMAPS_STATIC_API = "http://maps.googleapis.com/maps/api/staticmap";
	
//	public static final String GMAPS_GEOCODE_API = "http://maps.google.com/maps/api/geocode/json?address=%s&sensor=false";
	public static final String GMAPS_GEOCODE_API = "http://maps.google.com/maps/api/geocode/json";
	public static final String ACTUAL_CITY = "Mar del Plata";
	
	public static final String MYBUS_KEY = "783df9f2ee5c11c66439f6cacbb220b6"; //"94a08da1fecbb6e8b46990538c7b50b2";

	public static final String BASE_TICKET_PRICE = "3.97";
	
	// ------CAMERA START--------------
	public static final double CAM_START_LONGITUDE = -57.553307;
	public static final double CAM_START_LATITUDE = -38.010481;
	// ------ MARKER RANGES -----------
	public static final double MARK_MAX_LATITUDE = -37.910819;
	public static final double MARK_MIN_LATITUDE = -38.184234;
	public static final double MARK_MAX_LONGITUDE = -57.518988;
	public static final double MARK_MIN_LONGITUDE = -57.682635;
}