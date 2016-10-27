package org.globant.models;

/*
 * SOURCES:
 * http://www.javacodegeeks.com/2011/01/android-json-parsing-gson-tutorial.html
 * http://albertattard.blogspot.com.ar/2009/06/practical-example-of-gson.html
 */
public class SimpleResult extends Result
{
	public int IdBusLine, BusLineDirection, StartBusStopNumber,
			StartBusStopStreetNumber, DestinationBusStopNumber,
			DestinatioBusStopStreetNumber;
	public String BusLineName, BusLineColor, StartBusStopStreetName,
			DestinatioBusStopStreetName, StartBusStopDistanceToOrigin,
			DestinatioBusStopDistanceToDestination;
	public double StartBusStopLat, StartBusStopLng, DestinationBusStopLat,
			DestinatioBusStopLng;

	public SimpleResult()
	{
		super();
	}

	@Override
	public int getType() {
		return 0; 
	}

	public int getIdBusLine() {
		return IdBusLine;
	}

	public void setIdBusLine(int idBusLine) {
		IdBusLine = idBusLine;
	}

	public int getBusLineDirection() {
		return BusLineDirection;
	}

	public void setBusLineDirection(int busLineDirection) {
		BusLineDirection = busLineDirection;
	}

	public int getStartBusStopNumber() {
		return StartBusStopNumber;
	}

	public void setStartBusStopNumber(int startBusStopNumber) {
		StartBusStopNumber = startBusStopNumber;
	}

	public int getStartBusStopStreetNumber() {
		return StartBusStopStreetNumber;
	}

	public void setStartBusStopStreetNumber(int startBusStopStreetNumber) {
		StartBusStopStreetNumber = startBusStopStreetNumber;
	}

	public int getDestinationBusStopNumber() {
		return DestinationBusStopNumber;
	}

	public void setDestinationBusStopNumber(int destinationBusStopNumber) {
		DestinationBusStopNumber = destinationBusStopNumber;
	}

	public int getDestinatioBusStopStreetNumber() {
		return DestinatioBusStopStreetNumber;
	}

	public void setDestinatioBusStopStreetNumber(int destinatioBusStopStreetNumber) {
		DestinatioBusStopStreetNumber = destinatioBusStopStreetNumber;
	}

	public String getBusLineName() {
		return BusLineName;
	}

	public void setBusLineName(String busLineName) {
		BusLineName = busLineName;
	}

	public String getBusLineColor() {
		return BusLineColor;
	}

	public void setBusLineColor(String busLineColor) {
		BusLineColor = busLineColor;
	}

	public String getStartBusStopStreetName() {
		return StartBusStopStreetName;
	}

	public void setStartBusStopStreetName(String startBusStopStreetName) {
		StartBusStopStreetName = startBusStopStreetName;
	}

	public String getDestinatioBusStopStreetName() {
		return DestinatioBusStopStreetName;
	}

	public void setDestinatioBusStopStreetName(String destinatioBusStopStreetName) {
		DestinatioBusStopStreetName = destinatioBusStopStreetName;
	}

	public String getStartBusStopDistanceToOrigin() {
		return StartBusStopDistanceToOrigin;
	}

	public void setStartBusStopDistanceToOrigin(String startBusStopDistanceToOrigin) {
		StartBusStopDistanceToOrigin = startBusStopDistanceToOrigin;
	}

	public String getDestinatioBusStopDistanceToDestination() {
		return DestinatioBusStopDistanceToDestination;
	}

	public void setDestinatioBusStopDistanceToDestination(
			String destinatioBusStopDistanceToDestination) {
		DestinatioBusStopDistanceToDestination = destinatioBusStopDistanceToDestination;
	}

	public double getStartBusStopLat() {
		return StartBusStopLat;
	}

	public void setStartBusStopLat(double startBusStopLat) {
		StartBusStopLat = startBusStopLat;
	}

	public double getStartBusStopLng() {
		return StartBusStopLng;
	}

	public void setStartBusStopLng(double startBusStopLng) {
		StartBusStopLng = startBusStopLng;
	}

	public double getDestinationBusStopLat() {
		return DestinationBusStopLat;
	}

	public void setDestinationBusStopLat(double destinationBusStopLat) {
		DestinationBusStopLat = destinationBusStopLat;
	}

	public double getDestinatioBusStopLng() {
		return DestinatioBusStopLng;
	}

	public void setDestinatioBusStopLng(double destinatioBusStopLng) {
		DestinatioBusStopLng = destinatioBusStopLng;
	}


}
