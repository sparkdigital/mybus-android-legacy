package org.globant.models;

import com.google.gson.annotations.SerializedName;

public class CompoundResult extends Result
{
	@SerializedName("FirstBusLineName")
	public String busLine1;
	
	@SerializedName("IdFirstBusLine")
	public int line1Id;
	
	@SerializedName("FirstBusLineDirection")
	public int line1Direction;
	
	@SerializedName("FirstBusLineColor")
	public String line1Color;

	@SerializedName("SecondBusLineName")
	public String busLine2;
	
	@SerializedName("IdSecondBusLine")
	public int line2Id;
	
	@SerializedName("SecondBusLineDirection")
	public int line2Direction;
	
	@SerializedName("SecondBusLineColor")
	public String line2Color;
	
	@SerializedName("FirstLineStartBusStopNumber")
	public int line1StartBusStopId;
	
	@SerializedName("FirstLineStartBusStopStreet")
	public String line1StartStreet;
	
	@SerializedName("FirstLineStartBusStopStreetNumber")
	public int line1StartStreetNumber;
	
	@SerializedName("FirstLineStartBusStopLat")
	public double line1StartLat;
	
	@SerializedName("FirstLineStartBusStopLng")
	public double line1StartLng;

	@SerializedName("FirstLineDestinationBusStopNumber")
	public int line1EndBusStopId;
	
	@SerializedName("FirstLineDestinatioBusStopStreet")
	public String line1EndStreet;
	
	@SerializedName("FirstLineDestinatioBusStopStreetNumber")
	public int line1EndStreetNumber;
	
	@SerializedName("FirstLineDestinatioBusStopLat")
	public double line1EndLat;
	
	@SerializedName("FirstLineDestinatioBusStopLng")
	public double line1EndLng;

	@SerializedName("SecondLineStartBusStopNumber")
	public int line2StartBusStopId;
	
	@SerializedName("SecondLineStartBusStopStreet")
	public String line2StartStreet;
	
	@SerializedName("SecondLineStartBusStopStreetNumber")
	public int line2StartStreetNumber;
	
	@SerializedName("SecondLineStartBusStopLat")
	public double line2StartLat;
	
	@SerializedName("SecondLineStartBusStopLng")
	public double line2StartLng;

	@SerializedName("SecondLineDestinationBusStopNumber")
	public int line2EndBusStopId;
	
	@SerializedName("SecondLineDestinationBusStopStreet")
	public String line2EndStreet;
	
	@SerializedName("SecondLineDestinationBusStopStreetNumber")
	public int line2EndStreetNumber;
	
	@SerializedName("SecondLineDestinationBusStopLat")
	public double line2EndLat;
	
	@SerializedName("SecondLineDestinationBusStopLng")
	public double line2EndLng;
	
	@SerializedName("FirstLineStartBusStopDistance")
	public String line1StartDistance;
	
	@SerializedName("SecondLineDestinationBusStopDistance")
	public String line2EndDistance;
	
	@SerializedName("CombinationDistance")
	public String combinedDistance;

	@Override
	public int getType() {
		return 1;
	}

	public String getBusLine1() {
		return busLine1;
	}

	public void setBusLine1(String busLine1) {
		this.busLine1 = busLine1;
	}

	public int getLine1Id() {
		return line1Id;
	}

	public void setLine1Id(int line1Id) {
		this.line1Id = line1Id;
	}

	public int getLine1Direction() {
		return line1Direction;
	}

	public void setLine1Direction(int line1Direction) {
		this.line1Direction = line1Direction;
	}

	public String getLine1Color() {
		return line1Color;
	}

	public void setLine1Color(String line1Color) {
		this.line1Color = line1Color;
	}

	public String getBusLine2() {
		return busLine2;
	}

	public void setBusLine2(String busLine2) {
		this.busLine2 = busLine2;
	}

	public int getLine2Id() {
		return line2Id;
	}

	public void setLine2Id(int line2Id) {
		this.line2Id = line2Id;
	}

	public int getLine2Direction() {
		return line2Direction;
	}

	public void setLine2Direction(int line2Direction) {
		this.line2Direction = line2Direction;
	}

	public String getLine2Color() {
		return line2Color;
	}

	public void setLine2Color(String line2Color) {
		this.line2Color = line2Color;
	}

	public int getLine1StartBusStopId() {
		return line1StartBusStopId;
	}

	public void setLine1StartBusStopId(int line1StartBusStopId) {
		this.line1StartBusStopId = line1StartBusStopId;
	}

	public String getLine1StartStreet() {
		return line1StartStreet;
	}

	public void setLine1StartStreet(String line1StartStreet) {
		this.line1StartStreet = line1StartStreet;
	}

	public int getLine1StartStreetNumber() {
		return line1StartStreetNumber;
	}

	public void setLine1StartStreetNumber(int line1StartStreetNumber) {
		this.line1StartStreetNumber = line1StartStreetNumber;
	}

	public double getLine1StartLat() {
		return line1StartLat;
	}

	public void setLine1StartLat(double line1StartLat) {
		this.line1StartLat = line1StartLat;
	}

	public double getLine1StartLng() {
		return line1StartLng;
	}

	public void setLine1StartLng(double line1StartLng) {
		this.line1StartLng = line1StartLng;
	}

	public int getLine1EndBusStopId() {
		return line1EndBusStopId;
	}

	public void setLine1EndBusStopId(int line1EndBusStopId) {
		this.line1EndBusStopId = line1EndBusStopId;
	}

	public String getLine1EndStreet() {
		return line1EndStreet;
	}

	public void setLine1EndStreet(String line1EndStreet) {
		this.line1EndStreet = line1EndStreet;
	}

	public int getLine1EndStreetNumber() {
		return line1EndStreetNumber;
	}

	public void setLine1EndStreetNumber(int line1EndStreetNumber) {
		this.line1EndStreetNumber = line1EndStreetNumber;
	}

	public double getLine1EndLat() {
		return line1EndLat;
	}

	public void setLine1EndLat(double line1EndLat) {
		this.line1EndLat = line1EndLat;
	}

	public double getLine1EndLng() {
		return line1EndLng;
	}

	public void setLine1EndLng(double line1EndLng) {
		this.line1EndLng = line1EndLng;
	}

	public int getLine2StartBusStopId() {
		return line2StartBusStopId;
	}

	public void setLine2StartBusStopId(int line2StartBusStopId) {
		this.line2StartBusStopId = line2StartBusStopId;
	}

	public String getLine2StartStreet() {
		return line2StartStreet;
	}

	public void setLine2StartStreet(String line2StartStreet) {
		this.line2StartStreet = line2StartStreet;
	}

	public int getLine2StartStreetNumber() {
		return line2StartStreetNumber;
	}

	public void setLine2StartStreetNumber(int line2StartStreetNumber) {
		this.line2StartStreetNumber = line2StartStreetNumber;
	}

	public double getLine2StartLat() {
		return line2StartLat;
	}

	public void setLine2StartLat(double line2StartLat) {
		this.line2StartLat = line2StartLat;
	}

	public double getLine2StartLng() {
		return line2StartLng;
	}

	public void setLine2StartLng(double line2StartLng) {
		this.line2StartLng = line2StartLng;
	}

	public int getLine2EndBusStopId() {
		return line2EndBusStopId;
	}

	public void setLine2EndBusStopId(int line2EndBusStopId) {
		this.line2EndBusStopId = line2EndBusStopId;
	}

	public String getLine2EndStreet() {
		return line2EndStreet;
	}

	public void setLine2EndStreet(String line2EndStreet) {
		this.line2EndStreet = line2EndStreet;
	}

	public int getLine2EndStreetNumber() {
		return line2EndStreetNumber;
	}

	public void setLine2EndStreetNumber(int line2EndStreetNumber) {
		this.line2EndStreetNumber = line2EndStreetNumber;
	}

	public double getLine2EndLat() {
		return line2EndLat;
	}

	public void setLine2EndLat(double line2EndLat) {
		this.line2EndLat = line2EndLat;
	}

	public double getLine2EndLng() {
		return line2EndLng;
	}

	public void setLine2EndLng(double line2EndLng) {
		this.line2EndLng = line2EndLng;
	}

	public String getLine1StartDistance() {
		return line1StartDistance;
	}

	public void setLine1StartDistance(String line1StartDistance) {
		this.line1StartDistance = line1StartDistance;
	}

	public String getLine2EndDistance() {
		return line2EndDistance;
	}

	public void setLine2EndDistance(String line2EndDistance) {
		this.line2EndDistance = line2EndDistance;
	}

	public String getCombinedDistance() {
		return combinedDistance;
	}

	public void setCombinedDistance(String combinedDistance) {
		this.combinedDistance = combinedDistance;
	}
}
